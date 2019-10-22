package it.unimib.disco.lta.alfa.inferenceEngines;


import grammarInference.Log.ConsoleLogger;

import it.unimib.disco.lta.alfa.inferenceEngines.FSAExtensionsRecorder.FSAExtension;
import it.unimib.disco.lta.alfa.inferenceEngines.FSAExtensionsRecorder.FSAExtensionBranch;
import it.unimib.disco.lta.alfa.inferenceEngines.FSAExtensionsRecorder.FSAExtensionTail;
import it.unimib.disco.lta.alfa.testUtils.ArtifactsProvider;
import it.unimib.disco.lta.alfa.testUtils.FSAProvider;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;

import junit.framework.TestCase;

import org.junit.After;
import org.junit.Before;

import automata.fsa.FiniteStateAutomaton;


/**
 * This class tests the ChangesRecorder when applied to KBehaviorEngineVisitCount
 * @author Fabrizio Pastore fabrizio.pastore AT gmail.com
 *
 */
public class KBehaviorEngineSearchNeighbourhoodChangesRecorderTest extends KBehaviorEngineFSAExtensionsTest{

	@Before
	public void setUp() throws Exception {
		engine = new KBehaviorEngineSearchNeighbourhood( 4, 4, true, "none", new ConsoleLogger(grammarInference.Log.Logger.debuginfoLevel) );
	}

	@After
	public void tearDown() throws Exception {

	}

	
	
}
