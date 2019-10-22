package it.unimib.disco.lta.alfa.klfa;

import static org.junit.Assert.*;

import grammarInference.Log.ConsoleLogger;
import it.unimib.disco.lta.alfa.inferenceEngines.KBehaviorEngine;
import it.unimib.disco.lta.alfa.klfa.AnomaliesAnalyzer.ExpectedTransitionPath;
import it.unimib.disco.lta.alfa.klfa.utils.componentAnalysis.ComponentTracesFile;
import it.unimib.disco.lta.alfa.testUtils.ArtifactsProvider;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import automata.Transition;
import automata.fsa.FiniteStateAutomaton;


public class AnomaliesAnalyzerTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testGetExpectedBehaviors() throws IOException {
		File traceDir = ArtifactsProvider.getFile("unit/main/artifacts/tools/kLFAEngine/");
		ComponentTracesFile tf = new ComponentTracesFile(traceDir, "C1", "componentFail_");
		tf.clear();
		File correctTrace = ArtifactsProvider.getFile("unit/main/artifacts/tools/kLFAEngine/component1.trace");
		KBehaviorEngine kbehavior = new KBehaviorEngine(2,2,true,"none",new ConsoleLogger(0));
		FiniteStateAutomaton fsa = kbehavior.inferFSAfromFile(correctTrace.getAbsolutePath());
		
		
		
		String[] failTrace = "A#B#C#E#D#F".split("#");
		for ( int i = 0; i < failTrace.length; ++i ){
			tf.addToken(failTrace[i], i);
		}
		tf.closeTrace(failTrace.length);
		tf.close();
		
		AnomaliesAnalyzer aa = new AnomaliesAnalyzer();
		
		Transition expectedTransition = null;
		for ( Transition t : fsa.getTransitions() ){
			if ( t.getDescription().equals("D") ){
				expectedTransition = t; 
			}
		}
		
		List<ExpectedTransitionPath> eb = aa.getExpectedBehaviors(new Transition[]{expectedTransition}, tf, 4);
		for ( ExpectedTransitionPath e : eb  ){
			System.out.println(e.getTransitionPath().getTransitionsString());
		}
		assertEquals(2, eb.size());
		
	}

}
