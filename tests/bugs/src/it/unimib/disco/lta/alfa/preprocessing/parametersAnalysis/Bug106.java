package it.unimib.disco.lta.alfa.preprocessing.parametersAnalysis;


import it.unimib.disco.lta.alfa.eventsDetection.AutomatedEventTypesDetector;
import it.unimib.disco.lta.alfa.eventsDetection.EventTypesDetectorException;
import it.unimib.disco.lta.alfa.eventsDetection.slct.SlctEventsParser;
import it.unimib.disco.lta.alfa.testUtils.ArtifactsProvider;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import junit.framework.TestCase;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;


public class Bug106  extends TestCase{

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testGeneration() throws IOException, EventTypesDetectorException{
		SlctEventsParser g = new SlctEventsParser();
		File file = ArtifactsProvider.getFile("bugs/artifacts/bug106.log");
		File rulesFile = ArtifactsProvider.getFile("bugs/artifacts/bug106.rules.properties");
		File dst = ArtifactsProvider.getFile("bugs/artifacts/bug106.csv");
		
		g.loadPatterns(rulesFile);
		
		g.process(file, dst);
		
	}
}
