package it.unimib.disco.lta.alfa.eventsDetection.slct;

import static org.junit.Assert.*;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import it.unimib.disco.lta.alfa.testUtils.ArtifactsProvider;

import org.junit.Test;

public class SlctJavaTest {

	@Test
	public void testGetRules() throws SlctRunnerException {
		File file = ArtifactsProvider.getUnitTestFile("slct/F1.log");
		
		SlctJava slct = new SlctJava(2);
		
		List<String> rules = slct.getRules(file);
		
		Collections.sort(rules);
		
		List<String> expected = new ArrayList<String>();
		expected.add("A B E");
		expected.add("A B * * E");
		
		Collections.sort(expected);
		
		
		assertEquals(expected, rules);
	}

}
