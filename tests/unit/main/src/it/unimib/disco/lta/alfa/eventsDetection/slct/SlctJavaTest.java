/*******************************************************************************
 *    Copyright 2019 Fabrizio Pastore, Leonardo Mariani
 *   
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 *******************************************************************************/
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
