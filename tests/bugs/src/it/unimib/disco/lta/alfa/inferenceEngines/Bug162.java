package it.unimib.disco.lta.alfa.inferenceEngines;


import static org.junit.Assert.assertEquals;

import grammarInference.Log.ConsoleLogger;
import it.unimib.disco.lta.alfa.inferenceEngines.KBehaviorEngine;
import it.unimib.disco.lta.alfa.inferenceEngines.KBehaviorEngineSearchNeighbourhood;
import it.unimib.disco.lta.alfa.inferenceEngines.FSAExtensionsRecorder.FSAExtension;
import it.unimib.disco.lta.alfa.klfa.TraceAnalyzer;
import it.unimib.disco.lta.alfa.klfa.TraceAnalyzer.MinimizationTypes;
import it.unimib.disco.lta.alfa.testUtils.ArtifactsProvider;

import java.awt.Point;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;


import tools.fsa2xml.LazyFSALoader;
import tools.fsa2xml.LazyFSALoader.LazyFSALoaderException;


import automata.Automaton;
import automata.State;
import automata.Transition;
import automata.fsa.FSATransition;
import automata.fsa.FiniteStateAutomaton;

/**
 * FiniteStateAutomaton in fsa format not loaded correctly
 * 
 * @author fabrizio
 *
 */
public class Bug162 {

	@Test
	public void testBug() throws FileNotFoundException, LazyFSALoaderException{
		
		String file = ArtifactsProvider.getBugFile("162/JspRuntimeContext.clinit.fsa").getAbsolutePath();
		
		FiniteStateAutomaton fsa = LazyFSALoader.loadFSA( file );
		
		State state = fsa.getInitialState();
		
		System.out.println("file "+fsa.getTransitionsFromState(state)[0]);
		
		FiniteStateAutomaton clone = buildExpectedFSA();
		
		state = clone.getInitialState();
		System.out.println("fake "+clone.getTransitionsFromState(state)[0]);
		
		checkFsaEquals( clone, fsa);
		
		KBehaviorEngine engine =  new KBehaviorEngine( 2,2, true, "none", new ConsoleLogger(grammarInference.Log.Logger.infoLevel) );
		
		engine.setRecordExtensions(true);
		
		
		String trace = ArtifactsProvider.getBugFile("162/checking_JspRuntimeContext.clinit.trace").getAbsolutePath();
		
		FiniteStateAutomaton extended = engine.inferFSAfromFile(trace,fsa);
		
		assertEquals(1, engine.getFSAExtensions().size() );
		
		FSAExtension extension = engine.getFSAExtensions().get(0);
		
		assertEquals(FSAExtension.ExtensionType.Branch, extension.getExtensionType());
		
		
	}
	
	private FiniteStateAutomaton buildExpectedFSA() {
		FiniteStateAutomaton fsa = new FiniteStateAutomaton();
		State q0 = fsa.createState(new Point());
		fsa.setInitialState(q0);
		
		State q1 = fsa.createState(new Point());
		FSATransition t = new FSATransition(q0, q1, "JspRuntimeContext.clinit_javax.servlet.jsp.JspFactory.<clinit>(()V)");
		fsa.addTransition(t);
		
		State q2 = fsa.createState(new Point());
		t = new FSATransition(q1, q2, "JspRuntimeContext.clinit_org.apache.jasper.runtime.JspFactoryImpl.<clinit>(()V)");
		fsa.addTransition(t);
		
		State q3 = fsa.createState(new Point());
		t = new FSATransition(q2, q3, "JspRuntimeContext.clinit_org.apache.jasper.runtime.JspFactoryImpl.<init>(()V)");
		fsa.addTransition(t);
		
		State q4 = fsa.createState(new Point());
		t = new FSATransition(q3, q4, "JspRuntimeContext.clinit_org.apache.jasper.security.SecurityClassLoad.securityClassLoad((Ljava.lang.ClassLoader;)V)");
		fsa.addTransition(t);
		
		State q5 = fsa.createState(new Point());
		t = new FSATransition(q4, q5, "JspRuntimeContext.clinit_javax.servlet.jsp.JspFactory.setDefaultFactory((Ljavax.servlet.jsp.JspFactory;)V)");
		fsa.addTransition(t);
		
		
		fsa.addFinalState(q5);
		return fsa;
	}

	public void checkFsaEquals( FiniteStateAutomaton fsa, FiniteStateAutomaton clone ){	
		assertEquals( fsa.getInitialState().getName(), clone.getInitialState().getName() ) ;
		
		ArrayList<String> names = new ArrayList<String>();
		Map<String,List<Transition>> originalStatesOutgoingTransitions = new HashMap<String,List<Transition>>();
		for ( State s : fsa.getStates() ){
			names.add(s.getName());
			
			List<Transition> fsaTrans = new ArrayList<Transition>() ;
			
			for ( Transition cloneT : fsa.getTransitionsFromState(s) ){
				fsaTrans.add(cloneT);
			}
			originalStatesOutgoingTransitions.put(s.getName(), fsaTrans );
		}
		Collections.sort(names);
		
		
		ArrayList<String> cloneNames = new ArrayList<String>();
		for ( State s : clone.getStates() ){
			cloneNames.add(s.getName());
		}
		Collections.sort(cloneNames);
		
		assertEquals( names, cloneNames );
		
		for ( State s : clone.getStates() ){
			List<String> cloneTrans = new ArrayList<String>() ;
			
			for ( Transition cloneT : clone.getTransitionsFromState(s) ){
				cloneTrans.add(cloneT.getFromState().getName()+"-"+cloneT.getDescription()+"-"+cloneT.getToState().getName());
			}
			
			List<String> fsaSTrans = new ArrayList<String>() ;
			List<Transition> fsaTrans = originalStatesOutgoingTransitions.get(s.getName());
			for ( Transition cloneT : fsaTrans ){
				fsaSTrans.add(cloneT.getFromState().getName()+"-"+cloneT.getDescription()+"-"+cloneT.getToState().getName());
			}
			
			Collections.sort(fsaSTrans);
			
			Collections.sort(cloneTrans);
			
			assertEquals(fsaSTrans, cloneTrans);
		}
	}
}
