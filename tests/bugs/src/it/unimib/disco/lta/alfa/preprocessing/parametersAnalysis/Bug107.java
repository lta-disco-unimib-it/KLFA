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


import static org.junit.Assert.*;

import it.unimib.disco.lta.alfa.eventsDetection.EventTypesDetectorException;
import it.unimib.disco.lta.alfa.eventsDetection.slct.SlctEventsParser;
import it.unimib.disco.lta.alfa.eventsDetection.slct.SlctPattern;
import it.unimib.disco.lta.alfa.eventsDetection.slct.SlctRunnerException;
import it.unimib.disco.lta.alfa.testUtils.ArtifactsProvider;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import junit.framework.TestCase;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;


public class Bug107 extends TestCase {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testSlctRunnerResult() throws SlctRunnerException, IOException, EventTypesDetectorException{
		File log = ArtifactsProvider.getFile("bugs/artifacts/bug107.log");
		File patterns = ArtifactsProvider.getFile("bugs/artifacts/bug107.properties");
		File dst = ArtifactsProvider.getFile("bugs/artifacts/bug107.csv");
		
		if ( dst.exists()){
			assertTrue( dst.delete() );
		}
		
		assertFalse( dst.exists() );
		
		SlctEventsParser g = new SlctEventsParser();
		g.setHashOutliers(true);
		g.loadPatterns(patterns);
		g.process(log, dst);
		List<SlctPattern> r = g.getRules();

		assertEquals(0,g.getUnmatchedLines().size());
		
		BufferedReader rd = new BufferedReader( new FileReader(dst) );
		
		assertEquals("C,R0001,Start,/subsonic",rd.readLine());
		assertEquals("C,R0005,subsonic",rd.readLine());
		assertEquals("C,R0001,Configur,default Resources",rd.readLine());
		assertEquals("C,R0001,Process,standard container startup",rd.readLine());
		assertEquals("C,R0002,'null'",rd.readLine());
		assertEquals("C,R0002,'null'",rd.readLine());
		assertEquals("|",rd.readLine());
		
		assertEquals(null,rd.readLine());
		
		rd.close();
	}
}
