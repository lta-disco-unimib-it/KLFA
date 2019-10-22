package it.unimib.disco.lta.alfa.preprocessing.parametersAnalysis;


import it.unimib.disco.lta.alfa.eventsDetection.slct.SlctRunner;
import it.unimib.disco.lta.alfa.eventsDetection.slct.SlctRunnerException;
import it.unimib.disco.lta.alfa.testUtils.ArtifactsProvider;

import java.io.File;
import java.util.List;
import java.util.regex.Pattern;

import junit.framework.TestCase;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;


public class Bug105 extends TestCase {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}
	
	@Test
	public void testSlctRunnerResult() throws SlctRunnerException{
		File file = ArtifactsProvider.getFile("bugs/artifacts/bug105.log");
		
		SlctRunner r = new SlctRunner(new File("."));
		r.setSupportPercentage(0.4);
		r.setIntersectionEnabled(false);
		List<Pattern> rules = r.getRules(file);
		
		for ( Pattern rule : rules ){
			System.out.println(rule);
		}
		
	}

}
