package it.unimib.disco.lta.alfa.preprocessing.parametersAnalysis;


import static org.junit.Assert.*;

import it.unimib.disco.lta.alfa.eventsDetection.slct.Slct;
import it.unimib.disco.lta.alfa.eventsDetection.slct.SlctRunner;
import it.unimib.disco.lta.alfa.eventsDetection.slct.SlctRunnerException;
import it.unimib.disco.lta.alfa.testUtils.ArtifactsProvider;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import junit.framework.TestCase;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;


public class Bug104 extends TestCase {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testSlctRunnerResult() throws SlctRunnerException{
		File file = ArtifactsProvider.getFile("bugs/artifacts/bug104.log");
		
		Slct r = new SlctRunner(new File("."));
		r.setSupportPercentage(0.4);
		List<Pattern> rulesPatterns = r.getRules(file);
		System.out.println("rules");
		List<String> rules = new ArrayList<String>();
		for ( Pattern rule : rulesPatterns ){
			rules.add(rule.pattern());
		}
		
		assertEquals(13, rules.size());
		assertTrue(rules.contains("stop Stopping"));
		assertTrue(rules.contains("start Force random number initialization (.*)"));
		assertTrue(rules.contains("doUnload Unloading persisted sessions"));
		assertTrue(rules.contains("doUnload Unloading complete"));
		assertTrue(rules.contains("doUnload Unloading 1 sessions"));
		assertTrue(rules.contains("doUnload Unloading 0 sessions"));
		assertTrue(rules.contains("doUnload Saving persisted sessions to SESSIONS.ser"));
		assertTrue(rules.contains("doUnload Expiring 1 persisted sessions"));
		assertTrue(rules.contains("doUnload Expiring 0 persisted sessions"));
		assertTrue(rules.contains("doLoad No persisted data file found"));
		assertTrue(rules.contains("doLoad Loading persisted sessions from SESSIONS.ser"));
		assertTrue(rules.contains("doLoad Creating custom object input stream for class loader"));
		assertTrue(rules.contains("doLoad (.*) (.*) persisted sessions"));
	}
}
