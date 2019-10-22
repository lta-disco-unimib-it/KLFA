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
package it.unimib.disco.lta.alfa.inferenceEngines;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileNotFoundException;

import automata.fsa.FiniteStateAutomaton;

import grammarInference.Log.ConsoleLogger;
import it.unimib.disco.lta.alfa.inferenceEngines.KBehaviorEngine;
import it.unimib.disco.lta.alfa.inferenceEngines.Test;
import it.unimib.disco.lta.alfa.testUtils.ArtifactsProvider;

/**
 * This BUG affected BCT, we added this test to check if also kLFA is affected
 * 
 * @author Fabrizio Pastore
 *
 */
public class Bug149 extends Test {

	
	/**
	 * Empty trace at the begin of the file
	 */
	@org.junit.Test
	public void testEmptyBegin() throws FileNotFoundException{
		File file = ArtifactsProvider.getBugFile("149/1.txt");
		
		KBehaviorEngine engine = new KBehaviorEngine(
				2,
				2,
				true,
				"end",
				new ConsoleLogger(5)); 
		
		FiniteStateAutomaton fsa = engine.inferFSAfromFile(file.getAbsolutePath());
		assertEquals( 3, fsa.getStates().length );
		assertEquals( 3, fsa.getTransitions().length );
		
		
	}
	

	
	/**
	 * Empty trace in the middle of a file with 3 traces
	 */
	@org.junit.Test
	public void testEmptyMiddle() throws FileNotFoundException{
		File file = ArtifactsProvider.getBugFile("149/2.txt");
		
		KBehaviorEngine engine = new KBehaviorEngine(
				2,
				2,
				true,
				"end",
				new ConsoleLogger(5)); 
		
		FiniteStateAutomaton fsa = engine.inferFSAfromFile(file.getAbsolutePath());
		assertEquals( 3, fsa.getStates().length );
		assertEquals( 3, fsa.getTransitions().length );
		
		
	}
	
	/**
	 * Empty trace at the end of a file with 3 traces
	 */
	@org.junit.Test
	public void testEmptyEnd3Traces() throws FileNotFoundException{
		File file = ArtifactsProvider.getBugFile("149/3.txt");
		
		KBehaviorEngine engine = new KBehaviorEngine(
				2,
				2,
				true,
				"end",
				new ConsoleLogger(5)); 
		
		FiniteStateAutomaton fsa = engine.inferFSAfromFile(file.getAbsolutePath());
		assertEquals( 3, fsa.getStates().length );
		assertEquals( 3, fsa.getTransitions().length );
		
		
	}
	
	/**
	 * Empty trace at the end of a file with 2 traces
	 */
	@org.junit.Test
	public void testEmptyEnd2Traces() throws FileNotFoundException{
		File file = ArtifactsProvider.getBugFile("149/4.txt");
		
		KBehaviorEngine engine = new KBehaviorEngine(
				2,
				2,
				true,
				"end",
				new ConsoleLogger(5)); 
		
		FiniteStateAutomaton fsa = engine.inferFSAfromFile(file.getAbsolutePath());
		assertEquals( 3, fsa.getStates().length );
		assertEquals( 3, fsa.getTransitions().length );
		
		
	}
	
	/**
	 * 2 Empty traces in the middle of the file
	 */
	@org.junit.Test
	public void testTwoEmptyMiddle() throws FileNotFoundException{
		File file = ArtifactsProvider.getBugFile("149/5.txt");
		
		KBehaviorEngine engine = new KBehaviorEngine(
				2,
				2,
				true,
				"end",
				new ConsoleLogger(5)); 
		
		FiniteStateAutomaton fsa = engine.inferFSAfromFile(file.getAbsolutePath());
		assertEquals( 3, fsa.getStates().length );
		assertEquals( 3, fsa.getTransitions().length );
		
		
	}
}
