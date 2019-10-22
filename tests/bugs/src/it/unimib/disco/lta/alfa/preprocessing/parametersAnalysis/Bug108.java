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
package it.unimib.disco.lta.alfa.preprocessing.parametersAnalysis;

import it.unimib.disco.lta.alfa.eventsDetection.slct.Slct;
import it.unimib.disco.lta.alfa.eventsDetection.slct.SlctEventsParser;
import it.unimib.disco.lta.alfa.eventsDetection.slct.SlctRunner;
import it.unimib.disco.lta.alfa.eventsDetection.slct.SlctRunnerException;
import it.unimib.disco.lta.alfa.testUtils.ArtifactsProvider;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.regex.Pattern;

import junit.framework.TestCase;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;



public class Bug108  extends TestCase{

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}
	
	@Test
	public void testMatch() throws IOException, SlctRunnerException{
		File log = ArtifactsProvider.getFile("bugs/artifacts/bug108.log");
		File rules = ArtifactsProvider.getFile("bugs/artifacts/bug108.rules.properties");
		File csv = ArtifactsProvider.getNonExistentFile("bugs/artifacts/bug108.csv");
		
		
		
		
		
	}
	
	@Test
	public void testRegexGenerate() throws IOException, SlctRunnerException{
		File log = ArtifactsProvider.getFile("unit/main/artifacts/bug108_.log");
		
		
		
		
		Slct r = new SlctRunner(new File("."));
		r.setSupportPercentage(0.4);
		List<Pattern> patterns = r.getRules(log);
		for ( Pattern pattern : patterns ){
			System.out.println(pattern.pattern());
		}
	}

}
