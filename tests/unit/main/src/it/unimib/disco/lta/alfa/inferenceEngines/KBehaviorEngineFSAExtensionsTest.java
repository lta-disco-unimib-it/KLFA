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
import java.util.List;

import junit.framework.TestCase;

import org.junit.After;
import org.junit.Before;

import automata.fsa.FiniteStateAutomaton;



/**
 * This test class checks that KBehavior algorithm records properly to the engine the extensions performed
 * 
 *  
 * Possible extensions can be:
 * 	Final state
 * 	Branch
 * 	Tail
 * 
 * 	They change in:
 * 		position
 * 		length	(Branch,Tail)
 * 
 * 
 * Case: Final state
 * 	Position
 * 		0
 * 		1
 * 		K-1
 * 		K
 * 		K+1
 * 		K+2
 * 		END-1
 * 		END
 * 
 * Case: Branch
 * 	Position
 * 		0
 * 		1
 * 		K-1
 * 		K
 * 		K+1
 * 		K+2
 * 		END-1
 * 		END
 * 
 *  Destination
 *  	Same	[IF !0L][SINGLE]
 *  	Other	[SINGLE]
 * 
 * 	Length
 * 		0   [PROPERTY OL]
 * 		1	[SINGLE]
 * 		K-1	[SINGLE]
 * 		K
 * 		K+1 [SINGLE]
 * 		>K	[SINGLE][PROPERTY >K]
 * 	
 *  Unseen recursion
 * 		No	[IF >K]
 * 		1 level		[IF >K]
 * 
 * 
 * Case: Tail
 * 	Position
 * 		0
 * 		1
 * 		K-1
 * 		K
 * 		K+1
 * 		K+2
 * 		END-1
 * 		END
 * 
 * 	Length
 * 		1	[SINGLE]
 * 		K-1	[SINGLE]
 * 		K
 * 		K+1 [SINGLE]
 * 		>K	[SINGLE][PROPERTY >K]
 * 	
 *  Unseen recursion
 * 		No	[IF >K]
 * 		1 level		[IF >K]
 *  
 * @author Fabrizio Pastore fabrizio.pastore AT gmail.com
 *
 */
public class KBehaviorEngineFSAExtensionsTest extends TestCase{
	protected KBehaviorEngine engine;
	
	@Before
	public void setUp() throws Exception {
		engine = new KBehaviorEngine( 4, 4, true, "none", new ConsoleLogger(grammarInference.Log.Logger.infoLevel) );
	}

	@After
	public void tearDown() throws Exception {

	}

	
	
	/**
	 * No changes
	 * @throws FileNotFoundException 
	 * 
	 */
	public void testNoChanges() throws FileNotFoundException{
		File trace = ArtifactsProvider.getFile("unit/main/artifacts/inferenceEngine/traceCorrect.txt");
		FiniteStateAutomaton fsa = FSAProvider.getSimpleFSA();
		
		
		
		
		engine.setRecordExtensions(true);
		
		engine.inferFSAfromFile(trace.getAbsolutePath(), fsa);
		
		List<FSAExtension> changeData = engine.getFSAExtensions();
		
		assertEquals(0,changeData.size());
		
	}
	
	/**
	 * Final 1
	 * @throws FileNotFoundException 
	 * 
	 */
	public void testAddFinal1() throws FileNotFoundException{
		File trace = ArtifactsProvider.getFile("unit/main/artifacts/inferenceEngine/traceFinal1.txt");
		FiniteStateAutomaton fsa = FSAProvider.getSimpleFSA();
		
		
		
		 
		engine.setRecordExtensions(true);
		
		engine.inferFSAfromFile(trace.getAbsolutePath(), fsa);
		
		List<FSAExtension> changeData = engine.getFSAExtensions();
		
		assertEquals(1,changeData.size());
		FSAExtension change = changeData.get(0);
		
		assertEquals("q1",change.getStartState().getName());
		
		assertEquals(0+1,change.getStartPosition());
		
		List<String> as = new ArrayList<String>();
		as.add("A");
		
		assertEquals(as,change.getAnomalousSequence());
	}
	
	/**
	 * Final K-1
	 * @throws FileNotFoundException 
	 * 
	 */
	public void testAddFinal2() throws FileNotFoundException{
		File trace = ArtifactsProvider.getFile("unit/main/artifacts/inferenceEngine/traceFinal2.txt");
		FiniteStateAutomaton fsa = FSAProvider.getSimpleFSA();
		
		
		
		 
		engine.setRecordExtensions(true);
		
		engine.inferFSAfromFile(trace.getAbsolutePath(), fsa);
		
		List<FSAExtension> changeData = engine.getFSAExtensions();
		
		assertEquals(1,changeData.size());
		FSAExtension change = changeData.get(0);
		
		assertEquals("q3",change.getStartState().getName());
		
		assertEquals(2+1,change.getStartPosition());
		
		List<String> as = new ArrayList<String>();
		as.add("C");
		assertEquals(as,change.getAnomalousSequence());
	}
	
	/**
	 * K=3
	 * @throws FileNotFoundException 
	 * 
	 */
	public void testAddFinal3() throws FileNotFoundException{
		File trace = ArtifactsProvider.getFile("unit/main/artifacts/inferenceEngine/traceFinal3.txt");
		FiniteStateAutomaton fsa = FSAProvider.getSimpleFSA();
		
		
		
		 
		engine.setRecordExtensions(true);
		
		engine.inferFSAfromFile(trace.getAbsolutePath(), fsa);
		
		List<FSAExtension> changeData = engine.getFSAExtensions();
		
		assertEquals(1,changeData.size());
		
		FSAExtension change = changeData.get(0);
		
		assertEquals("q0",change.getStartState().getName());
		
		assertEquals(0+1,change.getStartPosition());
		
		
		assertEquals(0,change.getAnomalousSequence().size());
	}
	
	/**
	 * Final in K
	 * @throws FileNotFoundException 
	 * 
	 */
	public void testAddFinal4() throws FileNotFoundException{
		File trace = ArtifactsProvider.getFile("unit/main/artifacts/inferenceEngine/traceFinal4.txt");
		FiniteStateAutomaton fsa = FSAProvider.getSimpleFSA();
		
		
		
		 
		engine.setRecordExtensions(true);
		
		engine.inferFSAfromFile(trace.getAbsolutePath(), fsa);
		
		List<FSAExtension> changeData = engine.getFSAExtensions();
		
		assertEquals(1,changeData.size());
		
		FSAExtension change = changeData.get(0);
		
		assertEquals("q4",change.getStartState().getName());
		
		assertEquals(3+1,change.getStartPosition());
		
		List<String> as = new ArrayList<String>();
		as.add("D");
		assertEquals(as,change.getAnomalousSequence());
	}
	
	/**
	 * Final K+1
	 * @throws FileNotFoundException 
	 * 
	 */
	public void testAddFinal5() throws FileNotFoundException{
		File trace = ArtifactsProvider.getFile("unit/main/artifacts/inferenceEngine/traceFinal5.txt");
		FiniteStateAutomaton fsa = FSAProvider.getSimpleFSA();
		
		
		
		 
		engine.setRecordExtensions(true);
		
		engine.inferFSAfromFile(trace.getAbsolutePath(), fsa);
		
		List<FSAExtension> changeData = engine.getFSAExtensions();
		
		assertEquals(1,changeData.size());
		
		FSAExtension change = changeData.get(0);
		
		assertEquals("q5",change.getStartState().getName());
		
		assertEquals(4+1,change.getStartPosition());
		
		List<String> as = new ArrayList<String>();
		as.add("E");
		assertEquals(as,change.getAnomalousSequence());
	}
	
	/**
	 * Final K+2
	 * @throws FileNotFoundException 
	 * 
	 */
	public void testAddFinal6() throws FileNotFoundException{
		File trace = ArtifactsProvider.getFile("unit/main/artifacts/inferenceEngine/traceFinal6.txt");
		FiniteStateAutomaton fsa = FSAProvider.getSimpleFSA();
		
		
		
		 
		engine.setRecordExtensions(true);
		
		engine.inferFSAfromFile(trace.getAbsolutePath(), fsa);
		
		List<FSAExtension> changeData = engine.getFSAExtensions();
		
		assertEquals(1,changeData.size());
		
		FSAExtension change = changeData.get(0);
		
		assertEquals("q6",change.getStartState().getName());
		
		assertEquals(5+1,change.getStartPosition());
		
		List<String> as = new ArrayList<String>();
		as.add("F");
		assertEquals(as,change.getAnomalousSequence());
	}
	
	/**
	 * Final FINAL-1
	 * @throws FileNotFoundException 
	 * 
	 */
	public void testAddFinal7() throws FileNotFoundException{
		File trace = ArtifactsProvider.getFile("unit/main/artifacts/inferenceEngine/traceFinal7.txt");
		FiniteStateAutomaton fsa = FSAProvider.getSimpleFSA();
		
		
		
		 
		engine.setRecordExtensions(true);
		
		engine.inferFSAfromFile(trace.getAbsolutePath(), fsa);
		
		List<FSAExtension> changeData = engine.getFSAExtensions();
		
		assertEquals(1,changeData.size());
		
		FSAExtension change = changeData.get(0);
		
		assertEquals("q12",change.getStartState().getName());
		
		assertEquals(11+1,change.getStartPosition());
	
		List<String> as = new ArrayList<String>();
		as.add("N");
		assertEquals(as,change.getAnomalousSequence());
	}
	
	
	//Branch
	/**
	 * Branch K 0
	 * @throws FileNotFoundException 
	 * 
	 */
	public void testAddBranch1() throws FileNotFoundException{
		File trace = ArtifactsProvider.getFile("unit/main/artifacts/inferenceEngine/traceBranch1.txt");
		FiniteStateAutomaton fsa = FSAProvider.getSimpleFSA();
		
		
		
		 
		engine.setRecordExtensions(true);
		
		engine.inferFSAfromFile(trace.getAbsolutePath(), fsa);
		
		List<FSAExtension> changeData = engine.getFSAExtensions();
		
		assertEquals(1,changeData.size());
		
		FSAExtension change = changeData.get(0);
		
		assertEquals("q0",change.getStartState().getName());
		
		assertEquals(0,change.getStartPosition());
		
		List<String> as = new ArrayList<String>();
		as.add("X");
		as.add("Y");
		as.add("Z");
		as.add("T");
		assertEquals(as,change.getAnomalousSequence());
	}
	
	
	/**
	 * Branch K position 1
	 * @throws FileNotFoundException 
	 * 
	 */
	public void testAddBranch2() throws FileNotFoundException{
		File trace = ArtifactsProvider.getFile("unit/main/artifacts/inferenceEngine/traceBranch2.txt");
		FiniteStateAutomaton fsa = FSAProvider.getSimpleFSA();
		
		
		
		 
		engine.setRecordExtensions(true);
		
		engine.inferFSAfromFile(trace.getAbsolutePath(), fsa);
		
		List<FSAExtension> changeData = engine.getFSAExtensions();
		
		assertEquals(1,changeData.size());
		
		FSAExtension change = changeData.get(0);
		
		assertEquals("q1",change.getStartState().getName());
		
		assertEquals(1,change.getStartPosition());
		
		List<String> as = new ArrayList<String>();
		as.add("X");
		as.add("Y");
		as.add("Z");
		as.add("T");
		assertEquals(as,change.getAnomalousSequence());
	}
	
	/**
	 * Branch K position K-1
	 * @throws FileNotFoundException 
	 * 
	 */
	public void testAddBranch3() throws FileNotFoundException{
		File trace = ArtifactsProvider.getFile("unit/main/artifacts/inferenceEngine/traceBranch3.txt");
		FiniteStateAutomaton fsa = FSAProvider.getSimpleFSA();
		
		
		
		 
		engine.setRecordExtensions(true);
		
		engine.inferFSAfromFile(trace.getAbsolutePath(), fsa);
		
		List<FSAExtension> changeData = engine.getFSAExtensions();
		
		assertEquals(1,changeData.size());
		
		FSAExtension change = changeData.get(0);
		
		assertEquals("q3",change.getStartState().getName());
		
		assertEquals(3,change.getStartPosition());
		
		List<String> as = new ArrayList<String>();
		as.add("X");
		as.add("Y");
		as.add("Z");
		as.add("T");
		assertEquals(as,change.getAnomalousSequence());
	}
	
	/**
	 * Branch K position K
	 * @throws FileNotFoundException 
	 * 
	 */
	public void testAddBranch4() throws FileNotFoundException{
		File trace = ArtifactsProvider.getFile("unit/main/artifacts/inferenceEngine/traceBranch4.txt");
		FiniteStateAutomaton fsa = FSAProvider.getSimpleFSA();
		
		
		
		 
		engine.setRecordExtensions(true);
		
		engine.inferFSAfromFile(trace.getAbsolutePath(), fsa);
		
		List<FSAExtension> changeData = engine.getFSAExtensions();
		
		assertEquals(1,changeData.size());
		
		FSAExtension change = changeData.get(0);
		
		assertEquals("q4",change.getStartState().getName());
		
		assertEquals(4,change.getStartPosition());
		
		List<String> as = new ArrayList<String>();
		as.add("X");
		as.add("Y");
		as.add("Z");
		as.add("T");
		assertEquals(as,change.getAnomalousSequence());
	}
	
	/**
	 * Branch K position K+1
	 * @throws FileNotFoundException 
	 * 
	 */
	public void testAddBranch5() throws FileNotFoundException{
		File trace = ArtifactsProvider.getFile("unit/main/artifacts/inferenceEngine/traceBranch5.txt");
		FiniteStateAutomaton fsa = FSAProvider.getSimpleFSA();
		
		
		
		 
		engine.setRecordExtensions(true);
		
		engine.inferFSAfromFile(trace.getAbsolutePath(), fsa);
		
		List<FSAExtension> changeData = engine.getFSAExtensions();
		
		assertEquals(1,changeData.size());
		
		FSAExtension change = changeData.get(0);
		
		assertEquals("q5",change.getStartState().getName());
		
		assertEquals(5,change.getStartPosition());
		
		List<String> as = new ArrayList<String>();
		as.add("X");
		as.add("Y");
		as.add("Z");
		as.add("T");
		assertEquals(as,change.getAnomalousSequence());
	}
	
	/**
	 * Branch K position END-1
	 * @throws FileNotFoundException 
	 * 
	 */
	public void testAddBranch6() throws FileNotFoundException{
		File trace = ArtifactsProvider.getFile("unit/main/artifacts/inferenceEngine/traceBranch6.txt");
		FiniteStateAutomaton fsa = FSAProvider.getSimpleFSA();
		
		
		
		 
		engine.setRecordExtensions(true);
		
		engine.inferFSAfromFile(trace.getAbsolutePath(), fsa);
		
		List<FSAExtension> changeData = engine.getFSAExtensions();
		
		assertEquals(1,changeData.size());
		
		FSAExtensionBranch change = (FSAExtensionBranch) changeData.get(0);
		
		assertEquals("q12",change.getStartState().getName());
		
		assertEquals(12,change.getStartPosition());
		
		List<String> as = new ArrayList<String>();
		as.add("X");
		as.add("Y");
		as.add("Z");
		as.add("T");
		assertEquals(as,change.getAnomalousSequence());
		
		assertEquals("q9",change.getToState().getName());
	}
	
	/**
	 * Branch K position END
	 * @throws FileNotFoundException 
	 * 
	 */
	public void testAddBranch7() throws FileNotFoundException{
		File trace = ArtifactsProvider.getFile("unit/main/artifacts/inferenceEngine/traceBranch7.txt");
		FiniteStateAutomaton fsa = FSAProvider.getSimpleFSA();
		
		
		
		 
		engine.setRecordExtensions(true);
		
		engine.inferFSAfromFile(trace.getAbsolutePath(), fsa);
		
		List<FSAExtension> changeData = engine.getFSAExtensions();
		
		assertEquals(1,changeData.size());
		
		FSAExtensionBranch change = (FSAExtensionBranch) changeData.get(0);
		
		assertEquals("q13",change.getStartState().getName());
		
		assertEquals(13,change.getStartPosition());
		
		List<String> as = new ArrayList<String>();
		as.add("X");
		as.add("Y");
		as.add("Z");
		as.add("T");
		assertEquals(as,change.getAnomalousSequence());
		
		assertEquals("q9",change.getToState().getName());
	}
	
	/**
	 * Branch 0 1
	 * @throws FileNotFoundException 
	 * 
	 */
	public void testAddBranch9() throws FileNotFoundException{
		File trace = ArtifactsProvider.getFile("unit/main/artifacts/inferenceEngine/traceBranch9.txt");
		FiniteStateAutomaton fsa = FSAProvider.getSimpleFSA();
		
		
		
		 
		engine.setRecordExtensions(true);
		
		engine.inferFSAfromFile(trace.getAbsolutePath(), fsa);
		
		List<FSAExtension> changeData = engine.getFSAExtensions();
		
		assertEquals(1,changeData.size());
		
		FSAExtensionBranch change = (FSAExtensionBranch) changeData.get(0);
		
		assertEquals("q1",change.getStartState().getName());
		
		assertEquals(1,change.getStartPosition());
		
		List<String> as = new ArrayList<String>();
		as.add("F");
		
		assertEquals(as,change.getAnomalousSequence());
		
		assertEquals ( 0, change.getTraceLen());
		
		assertEquals("q5",change.getToState().getName());
	}
	
	/**
	 * Branch 0 K-1
	 * @throws FileNotFoundException 
	 * 
	 */
	public void testAddBranch10() throws FileNotFoundException{
		File trace = ArtifactsProvider.getFile("unit/main/artifacts/inferenceEngine/traceBranch10.txt");
		FiniteStateAutomaton fsa = FSAProvider.getSimpleFSA();
		
		
		 
		engine.setRecordExtensions(true);
		
		engine.inferFSAfromFile(trace.getAbsolutePath(), fsa);
		
		List<FSAExtension> changeData = engine.getFSAExtensions();
		
		assertEquals(1,changeData.size());
		
		FSAExtensionBranch change = (FSAExtensionBranch) changeData.get(0);
		
		assertEquals("q3",change.getStartState().getName());
		
		assertEquals(3,change.getStartPosition());
		
		List<String> as = new ArrayList<String>();
		as.add("F");
		
		assertEquals(as,change.getAnomalousSequence());
		
		assertEquals ( 0, change.getTraceLen());
		
		assertEquals("q5",change.getToState().getName());
	}
	

	/**
	 * Branch 0 K
	 * @throws FileNotFoundException 
	 * 
	 */
	public void testAddBranch11() throws FileNotFoundException{
		File trace = ArtifactsProvider.getFile("unit/main/artifacts/inferenceEngine/traceBranch11.txt");
		FiniteStateAutomaton fsa = FSAProvider.getSimpleFSA();
		
		 
		engine.setRecordExtensions(true);
		
		engine.inferFSAfromFile(trace.getAbsolutePath(), fsa);
		
		List<FSAExtension> changeData = engine.getFSAExtensions();
		
		assertEquals(1,changeData.size());
		
		FSAExtensionBranch change = (FSAExtensionBranch) changeData.get(0);
		
		assertEquals("q4",change.getStartState().getName());
		
		assertEquals(4,change.getStartPosition());
		
		List<String> as = new ArrayList<String>();
		as.add("F");
		
		assertEquals(as,change.getAnomalousSequence());
		
		assertEquals ( 0, change.getTraceLen());
		
		assertEquals("q5",change.getToState().getName());
	}
	
	/**
	 * Branch 0 K+1
	 * @throws FileNotFoundException 
	 * 
	 */
	public void testAddBranch12() throws FileNotFoundException{
		File trace = ArtifactsProvider.getFile("unit/main/artifacts/inferenceEngine/traceBranch12.txt");
		FiniteStateAutomaton fsa = FSAProvider.getSimpleFSA();
		
		 
		engine.setRecordExtensions(true);
		
		engine.inferFSAfromFile(trace.getAbsolutePath(), fsa);
		
		List<FSAExtension> changeData = engine.getFSAExtensions();
		
		assertEquals(1,changeData.size());
		
		FSAExtensionBranch change = (FSAExtensionBranch) changeData.get(0);
		
		assertEquals("q5",change.getStartState().getName());
		
		assertEquals(5,change.getStartPosition());
		
		List<String> as = new ArrayList<String>();
		as.add("H");
		
		assertEquals(as,change.getAnomalousSequence());
		
		assertEquals ( 0, change.getTraceLen());
		
		assertEquals("q7",change.getToState().getName());
	}
	
	/**
	 * Branch 0 END-1
	 * @throws FileNotFoundException 
	 * 
	 */
	public void testAddBranch13() throws FileNotFoundException{
		File trace = ArtifactsProvider.getFile("unit/main/artifacts/inferenceEngine/traceBranch13.txt");
		FiniteStateAutomaton fsa = FSAProvider.getSimpleFSA();
		
		 
		engine.setRecordExtensions(true);
		
		engine.inferFSAfromFile(trace.getAbsolutePath(), fsa);
		
		List<FSAExtension> changeData = engine.getFSAExtensions();
		
		assertEquals(1,changeData.size());
		
		FSAExtensionBranch change = (FSAExtensionBranch) changeData.get(0);
		
		assertEquals("q12",change.getStartState().getName());
		
		assertEquals(12,change.getStartPosition());
		
		List<String> as = new ArrayList<String>();
		as.add("L");
		
		assertEquals(as,change.getAnomalousSequence());
		
		assertEquals ( 0, change.getTraceLen());
		
		assertEquals("q9",change.getToState().getName());
	}
	
	/**
	 * Branch 0 END
	 * @throws FileNotFoundException 
	 * 
	 */
	public void testAddBranch14() throws FileNotFoundException{
		File trace = ArtifactsProvider.getFile("unit/main/artifacts/inferenceEngine/traceBranch14.txt");
		FiniteStateAutomaton fsa = FSAProvider.getSimpleFSA();
		
		 
		engine.setRecordExtensions(true);
		
		engine.inferFSAfromFile(trace.getAbsolutePath(), fsa);
		
		List<FSAExtension> changeData = engine.getFSAExtensions();
		
		assertEquals(1,changeData.size());
		
		FSAExtensionBranch change = (FSAExtensionBranch) changeData.get(0);
		
		assertEquals("q13",change.getStartState().getName());
		
		assertEquals(13,change.getStartPosition());
		
		List<String> as = new ArrayList<String>();
		as.add("L");
		
		assertEquals(as,change.getAnomalousSequence());
		
		assertEquals ( 0, change.getTraceLen());
		
		assertEquals("q9",change.getToState().getName());
	}
	
	/**
	 * Branch 0 0
	 * @throws FileNotFoundException 
	 * 
	 */
	public void testAddBranch15() throws FileNotFoundException{
		File trace = ArtifactsProvider.getFile("unit/main/artifacts/inferenceEngine/traceBranch15.txt");
		FiniteStateAutomaton fsa = FSAProvider.getSimpleFSA();
		
		 
		engine.setRecordExtensions(true);
		
		engine.inferFSAfromFile(trace.getAbsolutePath(), fsa);
		
		List<FSAExtension> changeData = engine.getFSAExtensions();
		
		assertEquals(1,changeData.size());
		
		FSAExtensionBranch change = (FSAExtensionBranch) changeData.get(0);
		
		assertEquals("q0",change.getStartState().getName());
		
		assertEquals(0,change.getStartPosition());
		
		List<String> as = new ArrayList<String>();
		as.add("B");
		
		assertEquals(as,change.getAnomalousSequence());
		
		assertEquals ( 0, change.getTraceLen());
		
		assertEquals("q1",change.getToState().getName());
	}
	
	/**
	 * Branch 1 K-1
	 * @throws FileNotFoundException 
	 * 
	 */
	public void testAddBranch16() throws FileNotFoundException{
		File trace = ArtifactsProvider.getFile("unit/main/artifacts/inferenceEngine/traceBranch16.txt");
		FiniteStateAutomaton fsa = FSAProvider.getSimpleFSA();
		
		 
		engine.setRecordExtensions(true);
		
		engine.inferFSAfromFile(trace.getAbsolutePath(), fsa);
		
		List<FSAExtension> changeData = engine.getFSAExtensions();
		
		assertEquals(1,changeData.size());
		
		FSAExtensionBranch change = (FSAExtensionBranch) changeData.get(0);
		
		assertEquals("q3",change.getStartState().getName());
		
		assertEquals(3,change.getStartPosition());
		
		List<String> as = new ArrayList<String>();
		as.add("X");
		
		assertEquals(as,change.getAnomalousSequence());
		
		assertEquals ( 1, change.getTraceLen());
		
		assertEquals("q3",change.getToState().getName());
	}
	
	/**
	 * Branch K-1 K
	 * @throws FileNotFoundException 
	 * 
	 */
	public void testAddBranch17() throws FileNotFoundException{
		File trace = ArtifactsProvider.getFile("unit/main/artifacts/inferenceEngine/traceBranch17.txt");
		FiniteStateAutomaton fsa = FSAProvider.getSimpleFSA();
		
		 
		engine.setRecordExtensions(true);
		
		engine.inferFSAfromFile(trace.getAbsolutePath(), fsa);
		
		List<FSAExtension> changeData = engine.getFSAExtensions();
		
		assertEquals(1,changeData.size());
		
		FSAExtensionBranch change = (FSAExtensionBranch) changeData.get(0);
		
		assertEquals("q4",change.getStartState().getName());
		
		assertEquals(4,change.getStartPosition());
		
		List<String> as = new ArrayList<String>();
		as.add("X");
		as.add("Y");
		as.add("Z");
		
		assertEquals(as,change.getAnomalousSequence());
		
		assertEquals ( 3, change.getTraceLen());
		
		assertEquals("q4",change.getToState().getName());
	}
	
	/**
	 * Branch K-1 K+1
	 * @throws FileNotFoundException 
	 * 
	 */
	public void testAddBranch18() throws FileNotFoundException{
		File trace = ArtifactsProvider.getFile("unit/main/artifacts/inferenceEngine/traceBranch18.txt");
		FiniteStateAutomaton fsa = FSAProvider.getSimpleFSA();
		
		 
		engine.setRecordExtensions(true);
		
		engine.inferFSAfromFile(trace.getAbsolutePath(), fsa);
		
		List<FSAExtension> changeData = engine.getFSAExtensions();
		
		assertEquals(1,changeData.size());
		
		FSAExtensionBranch change = (FSAExtensionBranch) changeData.get(0);
		
		assertEquals("q5",change.getStartState().getName());
		
		assertEquals(5,change.getStartPosition());
		
		List<String> as = new ArrayList<String>();
		as.add("X");
		as.add("Y");
		as.add("Z");
		as.add("T");
		as.add("U");
		
		assertEquals(as,change.getAnomalousSequence());
		
		assertEquals ( 5, change.getTraceLen());
		
		assertEquals("q5",change.getToState().getName());
	}
	
	/**
	 * Branch >K K "recursion level 1"
	 * @throws FileNotFoundException 
	 * 
	 */
	public void testAddBranch19() throws FileNotFoundException{
		File trace = ArtifactsProvider.getFile("unit/main/artifacts/inferenceEngine/traceBranch19.txt");
		FiniteStateAutomaton fsa = FSAProvider.getSimpleFSA();
		
		 
		engine.setRecordExtensions(true);
		
		engine.inferFSAfromFile(trace.getAbsolutePath(), fsa);
		
		List<FSAExtension> changeData = engine.getFSAExtensions();
		
		assertEquals(1,changeData.size());
		
		FSAExtensionBranch change = (FSAExtensionBranch) changeData.get(0);
		
		assertEquals("q4",change.getStartState().getName());
		
		assertEquals(4,change.getStartPosition());
		
		List<String> as = new ArrayList<String>();
		as.add("X");
		as.add("Y");
		as.add("Z");
		as.add("T");
		as.add("U");
		as.add("X");
		as.add("Y");
		as.add("Z");
		as.add("T");
		assertEquals(as,change.getAnomalousSequence());
		
		assertEquals ( 9, change.getTraceLen());
		
		assertEquals("q4",change.getToState().getName());
	}
	
	//
	//TAIL
	//
	
	/**
	 * Tail K position 0
	 * @throws FileNotFoundException 
	 * 
	 */
	public void testTail1() throws FileNotFoundException{
		File trace = ArtifactsProvider.getFile("unit/main/artifacts/inferenceEngine/traceTail1.txt");
		FiniteStateAutomaton fsa = FSAProvider.getSimpleFSA();
		
		 
		engine.setRecordExtensions(true);
		
		engine.inferFSAfromFile(trace.getAbsolutePath(), fsa);
		
		List<FSAExtension> changeData = engine.getFSAExtensions();
		
		assertEquals(1,changeData.size());
		
		FSAExtensionTail change = (FSAExtensionTail) changeData.get(0);
		
		assertEquals("q0",change.getStartState().getName());
		
		assertEquals(0,change.getStartPosition());
	
		List<String> as = new ArrayList<String>();
		as.add("X");
		as.add("Y");
		as.add("Z");
		as.add("T");
		assertEquals(as,change.getAnomalousSequence());
		

	}

	/**
	 * Tail K position 1
	 * @throws FileNotFoundException 
	 * 
	 */
	public void testTail2() throws FileNotFoundException{
		File trace = ArtifactsProvider.getFile("unit/main/artifacts/inferenceEngine/traceTail2.txt");
		FiniteStateAutomaton fsa = FSAProvider.getSimpleFSA();
		
		 
		engine.setRecordExtensions(true);
		
		engine.inferFSAfromFile(trace.getAbsolutePath(), fsa);
		
		List<FSAExtension> changeData = engine.getFSAExtensions();
		
		assertEquals(1,changeData.size());
		
		FSAExtensionTail change = (FSAExtensionTail) changeData.get(0);
		
		assertEquals("q1",change.getStartState().getName());
		
		assertEquals(1,change.getStartPosition());
		
		List<String> as = new ArrayList<String>();
		as.add("X");
		as.add("Y");
		as.add("Z");
		as.add("T");
		assertEquals(as,change.getAnomalousSequence());

	}
	
	/**
	 * Tail K position K-1
	 * @throws FileNotFoundException 
	 * 
	 */
	public void testTail3() throws FileNotFoundException{
		File trace = ArtifactsProvider.getFile("unit/main/artifacts/inferenceEngine/traceTail3.txt");
		FiniteStateAutomaton fsa = FSAProvider.getSimpleFSA();
		
		 
		engine.setRecordExtensions(true);
		
		engine.inferFSAfromFile(trace.getAbsolutePath(), fsa);
		
		List<FSAExtension> changeData = engine.getFSAExtensions();
		
		assertEquals(1,changeData.size());
		
		FSAExtensionTail change = (FSAExtensionTail) changeData.get(0);
		
		assertEquals("q3",change.getStartState().getName());
		
		assertEquals(3,change.getStartPosition());
		
		List<String> as = new ArrayList<String>();
		as.add("X");
		as.add("Y");
		as.add("Z");
		as.add("T");
		assertEquals(as,change.getAnomalousSequence());
		
	}
	
	/**
	 * Tail K position K
	 * @throws FileNotFoundException 
	 * 
	 */
	public void testTail4() throws FileNotFoundException{
		File trace = ArtifactsProvider.getFile("unit/main/artifacts/inferenceEngine/traceTail4.txt");
		FiniteStateAutomaton fsa = FSAProvider.getSimpleFSA();
		
		 
		engine.setRecordExtensions(true);
		
		engine.inferFSAfromFile(trace.getAbsolutePath(), fsa);
		
		List<FSAExtension> changeData = engine.getFSAExtensions();
		
		assertEquals(1,changeData.size());
		
		FSAExtensionTail change = (FSAExtensionTail) changeData.get(0);
		
		assertEquals("q4",change.getStartState().getName());
		
		assertEquals(4,change.getStartPosition());
		
		List<String> as = new ArrayList<String>();
		as.add("X");
		as.add("Y");
		as.add("Z");
		as.add("T");
		assertEquals(as,change.getAnomalousSequence());
		
	}
	
	/**
	 * Tail K position END-1
	 * @throws FileNotFoundException 
	 * 
	 */
	public void testTail5() throws FileNotFoundException{
		File trace = ArtifactsProvider.getFile("unit/main/artifacts/inferenceEngine/traceTail5.txt");
		FiniteStateAutomaton fsa = FSAProvider.getSimpleFSA();
		
		 
		engine.setRecordExtensions(true);
		
		engine.inferFSAfromFile(trace.getAbsolutePath(), fsa);
		
		List<FSAExtension> changeData = engine.getFSAExtensions();
		
		assertEquals(1,changeData.size());
		
		FSAExtensionTail change = (FSAExtensionTail) changeData.get(0);
		
		assertEquals("q12",change.getStartState().getName());
		
		assertEquals(12,change.getStartPosition());
	
		List<String> as = new ArrayList<String>();
		as.add("X");
		as.add("Y");
		as.add("Z");
		as.add("T");
		assertEquals(as,change.getAnomalousSequence());
		
	}
	
	/**
	 * Tail K position END
	 * @throws FileNotFoundException 
	 * 
	 */
	public void testTail6() throws FileNotFoundException{
		File trace = ArtifactsProvider.getFile("unit/main/artifacts/inferenceEngine/traceTail6.txt");
		FiniteStateAutomaton fsa = FSAProvider.getSimpleFSA();
		
		 
		engine.setRecordExtensions(true);
		
		engine.inferFSAfromFile(trace.getAbsolutePath(), fsa);
		
		List<FSAExtension> changeData = engine.getFSAExtensions();
		
		assertEquals(1,changeData.size());
		
		FSAExtensionTail change = (FSAExtensionTail) changeData.get(0);
		
		assertEquals("q13",change.getStartState().getName());
		
		assertEquals(13,change.getStartPosition());
		
		List<String> as = new ArrayList<String>();
		as.add("X");
		as.add("Y");
		as.add("Z");
		as.add("T");
		assertEquals(as,change.getAnomalousSequence());
		
	}
	
	/**
	 * Tail 1 position 0
	 * @throws FileNotFoundException 
	 * 
	 */
	public void testTail7() throws FileNotFoundException{
		File trace = ArtifactsProvider.getFile("unit/main/artifacts/inferenceEngine/traceTail7.txt");
		FiniteStateAutomaton fsa = FSAProvider.getSimpleFSA();
		
		 
		engine.setRecordExtensions(true);
		
		engine.inferFSAfromFile(trace.getAbsolutePath(), fsa);
		
		List<FSAExtension> changeData = engine.getFSAExtensions();
		
		assertEquals(1,changeData.size());
		
		FSAExtensionTail change = (FSAExtensionTail) changeData.get(0);
		
		assertEquals("q0",change.getStartState().getName());
		
		assertEquals(0,change.getStartPosition());
		
		List<String> as = new ArrayList<String>();
		as.add("X");
		
		assertEquals(as,change.getAnomalousSequence());
		
	}
	
	/**
	 * Tail K-1 position K
	 * @throws FileNotFoundException 
	 * 
	 */
	public void testTail8() throws FileNotFoundException{
		File trace = ArtifactsProvider.getFile("unit/main/artifacts/inferenceEngine/traceTail8.txt");
		FiniteStateAutomaton fsa = FSAProvider.getSimpleFSA();
		
		 
		engine.setRecordExtensions(true);
		
		engine.inferFSAfromFile(trace.getAbsolutePath(), fsa);
		
		List<FSAExtension> changeData = engine.getFSAExtensions();
		
		assertEquals(1,changeData.size());
		
		FSAExtensionTail change = (FSAExtensionTail) changeData.get(0);
		
		assertEquals("q4",change.getStartState().getName());
		
		assertEquals(4,change.getStartPosition());
	
		List<String> as = new ArrayList<String>();
		as.add("G");
		as.add("H");
		as.add("I");
		
		assertEquals(as,change.getAnomalousSequence());
		
	}
	
	/**
	 * Tail K+1 position K
	 * @throws FileNotFoundException 
	 * 
	 */
	public void testTail9() throws FileNotFoundException{
		File trace = ArtifactsProvider.getFile("unit/main/artifacts/inferenceEngine/traceTail9.txt");
		FiniteStateAutomaton fsa = FSAProvider.getSimpleFSA();
		
		 
		engine.setRecordExtensions(true);
		
		engine.inferFSAfromFile(trace.getAbsolutePath(), fsa);
		
		List<FSAExtension> changeData = engine.getFSAExtensions();
		
		assertEquals(1,changeData.size());
		
		FSAExtensionTail change = (FSAExtensionTail) changeData.get(0);
		
		assertEquals("q4",change.getStartState().getName());
		
		assertEquals(4,change.getStartPosition());
		
		
		List<String> as = new ArrayList<String>();
		as.add("G");
		as.add("H");
		as.add("I");
		as.add("X");
		as.add("Y");
		assertEquals(as,change.getAnomalousSequence());
		
	}
	
	/**
	 * Tail >K position K recursion level 1
	 * @throws FileNotFoundException 
	 * 
	 */
	public void testTail10() throws FileNotFoundException{
		File trace = ArtifactsProvider.getFile("unit/main/artifacts/inferenceEngine/traceTail10.txt");
		FiniteStateAutomaton fsa = FSAProvider.getSimpleFSA();
		
		 
		engine.setRecordExtensions(true);
		
		engine.inferFSAfromFile(trace.getAbsolutePath(), fsa);
		
		List<FSAExtension> changeData = engine.getFSAExtensions();
		
		assertEquals(1,changeData.size());
		
		FSAExtensionTail change = (FSAExtensionTail) changeData.get(0);
		
		assertEquals("q4",change.getStartState().getName());
		
		assertEquals(4,change.getStartPosition());
	
		List<String> as = new ArrayList<String>();
		as.add("G");
		as.add("H");
		as.add("I");
		as.add("X");
		as.add("Y");
		as.add("T");
		as.add("G");
		as.add("H");
		as.add("I");
		as.add("X");
		assertEquals(as,change.getAnomalousSequence());
		
	}
	
	/**
	 * Tail >K position K recursion level 1
	 * @throws FileNotFoundException 
	 * 
	 */
	public void testTail11() throws FileNotFoundException{
		File trace = ArtifactsProvider.getFile("unit/main/artifacts/inferenceEngine/traceTail11.txt");
		FiniteStateAutomaton fsa = FSAProvider.getSimpleFSA();
		
		 
		engine.setRecordExtensions(true);
		
		engine.inferFSAfromFile(trace.getAbsolutePath(), fsa);
		
		List<FSAExtension> changeData = engine.getFSAExtensions();
		
		assertEquals(1,changeData.size());
		
		FSAExtensionTail change = (FSAExtensionTail) changeData.get(0);
		
		assertEquals("q4",change.getStartState().getName());
		
		assertEquals(4,change.getStartPosition());
		
		List<String> as = new ArrayList<String>();
		as.add("G");
		as.add("H");
		as.add("I");
		as.add("X");
		as.add("Y");
		as.add("T");
		as.add("G");
		as.add("H");
		as.add("L");
		as.add("I");
		as.add("X");
		assertEquals(as,change.getAnomalousSequence());
		
	}
	
}

