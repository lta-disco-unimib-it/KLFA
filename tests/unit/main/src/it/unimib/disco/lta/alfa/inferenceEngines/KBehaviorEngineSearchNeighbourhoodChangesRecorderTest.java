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


import grammarInference.Log.ConsoleLogger;

import it.unimib.disco.lta.alfa.inferenceEngines.FSAExtensionsRecorder.FSAExtension;
import it.unimib.disco.lta.alfa.inferenceEngines.FSAExtensionsRecorder.FSAExtensionBranch;
import it.unimib.disco.lta.alfa.inferenceEngines.FSAExtensionsRecorder.FSAExtensionTail;
import it.unimib.disco.lta.alfa.testUtils.ArtifactsProvider;
import it.unimib.disco.lta.alfa.testUtils.FSAProvider;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;

import junit.framework.TestCase;

import org.junit.After;
import org.junit.Before;

import automata.fsa.FiniteStateAutomaton;


/**
 * This class tests the ChangesRecorder when applied to KBehaviorEngineVisitCount
 * @author Fabrizio Pastore fabrizio.pastore AT gmail.com
 *
 */
public class KBehaviorEngineSearchNeighbourhoodChangesRecorderTest extends KBehaviorEngineFSAExtensionsTest{

	@Before
	public void setUp() throws Exception {
		engine = new KBehaviorEngineSearchNeighbourhood( 4, 4, true, "none", new ConsoleLogger(grammarInference.Log.Logger.debuginfoLevel) );
	}

	@After
	public void tearDown() throws Exception {

	}

	
	
}
