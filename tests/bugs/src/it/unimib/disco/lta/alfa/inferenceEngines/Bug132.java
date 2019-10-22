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

import it.unimib.disco.lta.alfa.inferenceEngines.FSAExtensionsRecorder.FSAExtension;
import it.unimib.disco.lta.alfa.testUtils.ArtifactsProvider;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import automata.fsa.FiniteStateAutomaton;

import grammarInference.Log.ConsoleLogger;
import junit.framework.TestCase;

public class Bug132 extends TestCase {

	public void testBug() throws FileNotFoundException, ClassNotFoundException, IOException{
		KBehaviorEngine engine = new KBehaviorEngine(2,2,true," none",new ConsoleLogger(0));
		
		File existingFSA = ArtifactsProvider.getFile("bugs/artifacts/bug132.training.ser");
		File failTrace = ArtifactsProvider.getFile("bugs/artifacts/bug132.checking.trace");
		engine.setRecordExtensions(true);
		FiniteStateAutomaton newFSA = engine.inferFSAfromFile(failTrace.getAbsolutePath(), existingFSA.getAbsolutePath());
		Iterator<FSAExtension> changeDataIt = engine.getFSAExtensions().iterator();
		FSAExtension changeData = changeDataIt.next();
		assertEquals("Tail", changeData.getExtensionType());
		assertEquals(2, changeData.getStartPosition());
		
		//This failed in Bug132, throws a NoSuchElementException
		changeData = changeDataIt.next();
		assertEquals("Branch", changeData.getExtensionType());
		assertEquals(2, changeData.getStartPosition());
		assertEquals(8, changeData.getLogLine());
		
		
	}
}
