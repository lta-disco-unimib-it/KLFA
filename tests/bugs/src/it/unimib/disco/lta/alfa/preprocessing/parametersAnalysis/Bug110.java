package it.unimib.disco.lta.alfa.preprocessing.parametersAnalysis;



import static org.junit.Assert.*;
import it.unimib.disco.lta.alfa.inferenceEngines.FSAExtensionsRecorder;
import it.unimib.disco.lta.alfa.inferenceEngines.KBehaviorEngine;
import it.unimib.disco.lta.alfa.inferenceEngines.FSAExtensionsRecorder.FSAExtension;
import it.unimib.disco.lta.alfa.testUtils.ArtifactsProvider;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import junit.framework.TestCase;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;


import automata.fsa.FiniteStateAutomaton;

public class Bug110  extends TestCase{

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}
	
	@Test
	public void testKExtension() throws FileNotFoundException, ClassNotFoundException, IOException{
		File fsaFile = ArtifactsProvider.getFile("bugs/artifacts/bug110.ser");
		File extTraceFile = ArtifactsProvider.getFile("bugs/artifacts/bug110_ext.trace");
		File kConf = ArtifactsProvider.getFile("bugs/artifacts/bug109.kbehavior.properties");
		
		KBehaviorEngine engine = new KBehaviorEngine( kConf.getAbsolutePath() );
		
		
		engine.setRecordExtensions(true);
		
		FiniteStateAutomaton fsa = engine.inferFSAfromFile( extTraceFile.getAbsolutePath() , fsaFile.getAbsolutePath() );
		
		for( FSAExtension cd : engine.getFSAExtensions() ){
			System.out.println(cd.getAnomalousSequence()+" "+cd.getExtensionType()+" "+cd.getStartState());;
		}
		
		assertEquals( 0, engine.getFSAExtensions().size() );
		
	}

}
