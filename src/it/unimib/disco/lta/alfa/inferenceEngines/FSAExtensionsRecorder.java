package it.unimib.disco.lta.alfa.inferenceEngines;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import grammarInference.Record.Trace;
import automata.Automaton;
import automata.State;
import automata.Transition;
import automata.fsa.FiniteStateAutomaton;

/**
 * This class record extensions to the FSA. It is used by the KBehaviorEngine
 * 
 *
 */
public class FSAExtensionsRecorder {
	
	boolean record = false;
	private File fileToRecord = new File("changesRecorder.log");
	private ArrayList<FSAExtension> fsaExtensions = new ArrayList<FSAExtension>();
	private int offset = 0;
	

	
	public static abstract class FSAExtension{
		
		private Trace anomalousTrace;
		private int startPosition;
		private State startState;
		private int logOffset;
		private ExtensionType extensionType;

		public static enum ExtensionType {Branch,Tail,FinalState}; 
		
		public FSAExtension( ExtensionType type , Trace anomalousTrace, int logOffset, int startPosition, State actualState) {
			this.anomalousTrace = anomalousTrace;
			this.startPosition = startPosition;
			this.startState = actualState;
			this.logOffset = logOffset;
			this.extensionType = type;
		}
		
		public ExtensionType getExtensionType(){
			return extensionType;
		}

		public Trace getAnomalousTrace() {
			return anomalousTrace;
		}
		
		public abstract int getTraceLen();

		public int getStartPosition() {
			return startPosition;
		}

		public State getStartState() {
			return startState;
		}

		public abstract List<String> getAnomalousSequence();

		public int getLogOffset() {
			return this.logOffset;
		}
		
		public int getLogLine(){
			return logOffset+startPosition;
		}
	}
	
	public static class FSAExtensionFinalState extends FSAExtension {

		public FSAExtensionFinalState(Trace anomalousTrace, int logOffset, int startPosition, State actualState) {
			super(FSAExtension.ExtensionType.FinalState,anomalousTrace, logOffset, startPosition, actualState);
		}

		@Override
		public List<String> getAnomalousSequence() {
			ArrayList<String> res = new ArrayList<String>();
			if ( getAnomalousTrace().getLength() > 0 ){
				res.add(getAnomalousTrace().getSymbol(getAnomalousTrace().getLength()-1).toString());
			}
			return res;
		}

		@Override
		public int getTraceLen() {
			return 0;
		}


	}
	
	
	
	public class FSAExtensionBranch extends FSAExtension{

		private String[] toTransitions;
		private int traceLen;
		private State toState;

		/**
		 * 
		 * @param anomalousTrace	the anomalous trace
		 * @param startPosition		start position (with respect to the global trace file)
		 * @param fromState		the state in which we are going to add the outgoing arch
		 * @param toState		the state in which we are going to add the incoming arch
		 * @param length
		 */
		public FSAExtensionBranch(Trace anomalousTrace, int logOffset, int startPosition, State fromState, State toState, int length) {
			super(FSAExtension.ExtensionType.Branch,anomalousTrace, logOffset, startPosition, fromState);
			Automaton automata = toState.getAutomaton();
			
			//gather now the transitions,  they could change during the execution
			Transition[] transitions = automata.getTransitionsToState( toState );
			this.toTransitions = new String[transitions.length];
			
			for ( int i=0;i < transitions.length; ++i ){
				toTransitions[i] = "("+transitions[i].getFromState().getName()+")"+transitions[i].getDescription();
			}
			
			this.traceLen = length;
			this.toState = toState;
		}



		@Override
		public List<String> getAnomalousSequence() {
			List<String> seq = new ArrayList<String>();
			Iterator<String> it = getAnomalousTrace().getSymbolIterator();
			while ( it.hasNext() ){
				seq.add(it.next());
			}
			return seq;
		}

		

		public int getTraceLen() {
			return traceLen;
		}

		public State getToState() {
			return toState;
		}

		

		
	}
	
	public class FSAExtensionTail extends FSAExtension{

		public FSAExtensionTail(Trace anomalousTrace, int logOffset, int startPosition, State actualState) {
			super(FSAExtension.ExtensionType.Tail,anomalousTrace, logOffset, startPosition, actualState);
		}


		@Override
		public List<String> getAnomalousSequence() {
			ArrayList<String> res = new ArrayList<String>();
			Iterator it = getAnomalousTrace().getSubTrace(getStartPosition(), getAnomalousTrace().getLength()-1).getSymbolIterator();
			while ( it.hasNext() ){
				res.add(it.next().toString());
			}
			return res;
		}


		@Override
		public int getTraceLen() {
			return getAnomalousSequence().size();
		}
	}
	
//	public static ChangesRecorder getInstance() {
//		if ( instance == null ){
//			instance = new ChangesRecorder();
//		}
//		return instance;
//	}
	
	public void setRecord( boolean record ){
		this.record = record;
	}

	/**
	 * 
	 * @param actualState			the state in which we are going to add the outgoing arch
	 * @param actualPos				the position in the current trace in which there is the sysmbol that causes the add
	 * @param t						the trace
	 * @param bpd					the behavioral pattern data
	 * @param startPositionOriginal	the position in the global trace file in which there is the symbol taht causes the add
	 */
	public void addBranch(State actualState, int actualPos, Trace t, BehavioralPatternData bpd, int startPositionOriginal) {
		
		if ( ! record ){
			return;
		}
		
		
		int end = bpd.fromTrace-1;
		if ( end < actualPos ){
			end=actualPos;
		}
		Trace anomalousTrace = t.getSubTrace(actualPos,end);
		
		
		if ( anomalousTrace.getLength() == 0 ){
			anomalousTrace = t.getSubTrace(bpd.fromTrace,bpd.fromTrace);
		}
		Automaton automata = bpd.fromState.getAutomaton();
		State toState = bpd.fromState;
		
		//System.out.println(actualPos+" "+startPositionOriginal+" "+bpd.fromTrace+" "+bpd.toTrace);
		FSAExtension changeData = new FSAExtensionBranch(anomalousTrace,offset,startPositionOriginal,actualState,bpd.fromState,bpd.fromTrace-actualPos);
		fsaExtensions.add(changeData);
	}
	
	

	public List<FSAExtension> getFSAExtensions() {
		return fsaExtensions;
	}

	public void addTail(Trace t, State fromState, int fromPos) {
		if ( ! record ){
			return;
		}
		
		
		
		FSAExtension changeData = new FSAExtensionTail(t,offset,fromPos,fromState);
		fsaExtensions.add(changeData);
	}

	public void clear() {
		fsaExtensions.clear();
		offset = 0;
	}

	public boolean isRecording() {
		return record;
	}

	public void addFinal(Trace t, State finalState,
			int pos) {
		
		if ( ! record ){
			return;
		}
		
		FSAExtension changeData = new FSAExtensionFinalState(t,offset,pos+1,finalState);
		fsaExtensions.add(changeData);
		
	}

	public void setOffset(int i) {
		this.offset=i;
	}

	public int getOffset() {
		return offset;
	}

	
}
