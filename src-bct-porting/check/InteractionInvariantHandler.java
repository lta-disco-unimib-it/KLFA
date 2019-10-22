package check;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.PrintStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Map;

import modelsFetchers.ModelsFetcher;
import modelsFetchers.ModelsFetcherException;
import modelsFetchers.ModelsFetcherFactoy;

import recorders.RecorderException;
import recorders.RecorderFactory;
import recorders.ViolationsRecorder;
import sun.security.action.GetLongAction;
import util.BctFileLogger;

import conf.EnvironmentalSetter;

import automata.State;
import automata.fsa.FSAConfiguration;
import automata.fsa.FSAStepWithClosureSimulator;
import automata.fsa.FiniteStateAutomaton;

public class InteractionInvariantHandler {

	private static Hashtable<String,FiniteStateAutomaton> automata = new Hashtable<String, FiniteStateAutomaton>();
	
	private static Map threadMap = new Hashtable();

	private static FiniteStateAutomaton nullAutomaton = new FiniteStateAutomaton();

	private static ArrayList nullConfiguration = new ArrayList();

	private static String nullString = "";

	public interface MethodCallType {
		public final String IN = "in";

		public final String OUT = "out";

		public String getExtension();
	}

	private static final class EnterMethod implements MethodCallType {

		public String getExtension() {
			return "in";
		}
	}

	private static final class ExitMethod implements MethodCallType {

		public String getExtension() {
			return "out";
		}
	}

	public static final MethodCallType ENTER = new EnterMethod();

	public static final MethodCallType EXIT = new ExitMethod();

	private static void _initThreadStack(long threadID, String signature) {
		
		Stack stack = new Stack();
		try {
			
		
			FiniteStateAutomaton f = getFSA( signature );
			
			FSAConfiguration startConfiguration = new FSAConfiguration(f
					.getInitialState(), null, null, null);
			ArrayList startConfigurations = new ArrayList();
			startConfigurations.add(startConfiguration);
			stack.push(signature);
			stack.push(startConfigurations);
			stack.push(f);
		} catch (Exception e) {
			stack.push(nullString);
			stack.push(nullConfiguration);
			stack.push(nullAutomaton);
		}
		
			threadMap.put(new Long(threadID), stack);
			return;
		
	}

	private static FiniteStateAutomaton getFSA(String signature) throws ModelsFetcherException {
		//System.out.println("BCTLOG getFSA : "+signature);
		if ( automata.contains(signature) ){
			return automata.get(signature);
		}
		ModelsFetcher mf = ModelsFetcherFactoy.getModelsFetcher();
		FiniteStateAutomaton f = mf.getInteractionModel(signature);
		automata.put(signature, f);
		return f;
	}

	private static void _checkCurrentTransition(Stack s, String signature) {
		FiniteStateAutomaton f = null;
		ArrayList previousConfigurations = null;
		ArrayList reachedConfigurations = null;

		f = (FiniteStateAutomaton) s.pop();
		previousConfigurations = (ArrayList) s.pop();
		String prevMethod = (String) s.pop();
		reachedConfigurations = new ArrayList();

		if (f == nullAutomaton) {
			//System.out.println("NullAutomaton extracted. Current transition is "+ signature);
		} else {
			FSAStepWithClosureSimulator simulator = new FSAStepWithClosureSimulator(
					f);

			for (int i = 0; i < previousConfigurations.size(); i++) {
				FSAConfiguration previousConfiguration = (FSAConfiguration) previousConfigurations
						.get(i);
				previousConfiguration.setUnprocessedInput(signature);
				reachedConfigurations.addAll(simulator.stepConfiguration(previousConfiguration));
			}
			if (reachedConfigurations.isEmpty()) {
				logViolation(prevMethod, f, previousConfigurations, signature, ENTER );
			}
		}
		s.push(prevMethod);
		s.push(reachedConfigurations);
		s.push(f);
	}

	private static void logViolation(String method, FiniteStateAutomaton fsa,
			ArrayList configurations, String invokedMethod, MethodCallType call) {

		
		ViolationsRecorder recorder = RecorderFactory.getViolationRecorder();
		
		if (fsa.equals(nullAutomaton))
			return;
		
		CurrentViolationsMaintainer vm = CurrentViolationsMaintainer.getInstance();

		//If this fsa for the current method execution was already violated do not report the violation
		if ( vm.isFSAViolated(fsa) ){
			return;
		}
		
		vm.fsaViolated(fsa);
		
		State state[];
		if (configurations.size() <= 0) {
			state = new State[1];
			state[0] = fsa.getInitialState();
		} else {
			state = new State[configurations.size()];
			for (int i = 0; i < configurations.size(); i++) {
				FSAConfiguration configuration = (FSAConfiguration) configurations
						.get(i);
				state[i] = configuration.getCurrentState();
			}
		}
		try {
			if (call.getExtension().equals(MethodCallType.IN)) {
				recorder.recordInteractionViolation(method,invokedMethod,state,ViolationsRecorder.InteractionViolationType.illegalTransition, Thread.currentThread().getStackTrace());
			} else {
				recorder.recordInteractionViolation(method,invokedMethod,state,ViolationsRecorder.InteractionViolationType.nonFinalState, Thread.currentThread().getStackTrace());
			}
		} catch ( RecorderException e ) {
			//TODO: add a logger
			System.err.println("Error recording interaction violation");
		}

	}

	private static void printStackTrace(StackTraceElement[] stack) {
		boolean foundJoinPoint = false;
		System.out.println("***TRACE:");
		for (int i = 0; i < stack.length; i++) {
			if ((foundJoinPoint == false)
					&& ((stack[i].getClassName().indexOf("___AW_JoinPoint") != -1) || (stack[i]
							.getClassName().indexOf("$_AW_$") != -1))) {
				foundJoinPoint = true;
			}
			if ((foundJoinPoint == true)
					&& (stack[i].getMethodName().indexOf("$_AW_$") == -1)
					&& (stack[i].getClassName().indexOf("___AW_JoinPoint") == -1)) {
				System.out.println(stack[i].getClassName() + "."
						+ stack[i].getMethodName() + ":"
						+ stack[i].getLineNumber());
			}
		}
	}

	private static void _loadNextFsa(Stack s, String signature) {
		try {
			
			FiniteStateAutomaton f = getFSA(signature);
			
			FSAConfiguration startConfiguration = new FSAConfiguration(f
					.getInitialState(), null, null, null);
			ArrayList startConfigurations = new ArrayList();
			startConfigurations.add(startConfiguration);
			s.push(signature);
			s.push(startConfigurations);
			s.push(f);
		} catch (Exception e) {
			s.push(nullString);
			s.push(nullConfiguration);
			s.push(nullAutomaton);
		}
	}

	public static void processCallEnter( long threadID,	String signature) {
//		BctFileLogger.getInstance().log("#"+threadID+"ENTER "+signature);
//		for ( StackTraceElement element : Thread.currentThread().getStackTrace() )
//			System.out.println("#"+threadID+"STACK +"+element.getClassName()+"."+element.getMethodName()+"."+element.getLineNumber());
//		
		
		Stack s = (Stack) threadMap.get(new Long(threadID));
		if (s == null)
			_initThreadStack(threadID, signature);
		else {
			_checkCurrentTransition(s, signature);
			_loadNextFsa(s, signature);
		}
	}

	public static void processCallExit( long threadID, String signature) {
//		BctFileLogger.getInstance().log("#"+threadID+"EXIT "+signature);
//		for ( StackTraceElement element : Thread.currentThread().getStackTrace() )
//			System.out.println("#"+threadID+"STACK +"+element.getClassName()+"."+element.getMethodName()+"."+element.getLineNumber());
//		
		
		Stack s = (Stack) threadMap.get( Long.valueOf(threadID));

		FiniteStateAutomaton fsa = (FiniteStateAutomaton) s.pop();
		ArrayList previousConfigurations = (ArrayList) s.pop();
		String method = (String) s.pop();

		//FIXME: was not used, check if checking phas enow is correct
//		FSAStepWithClosureSimulator it.unimib.disco.lta.conFunkHealer.simulator = new FSAStepWithClosureSimulator(fsa);

		boolean isFinalState = false;

		for (int i = 0; i < previousConfigurations.size(); i++) {

			FSAConfiguration configuration = (FSAConfiguration) previousConfigurations
					.get(i);
			if (fsa.isFinalState(configuration.getCurrentState()))
				isFinalState = true;
		}

		if (!isFinalState) {
			logViolation(method, fsa, previousConfigurations, signature, EXIT );
		}

		if (s.isEmpty()) {
			threadMap.remove(new Long(threadID));
		}
		
		CurrentViolationsMaintainer.getInstance().executionFinished(fsa);
	}
}