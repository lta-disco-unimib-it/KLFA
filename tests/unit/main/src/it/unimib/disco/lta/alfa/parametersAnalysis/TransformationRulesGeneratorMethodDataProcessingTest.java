package it.unimib.disco.lta.alfa.parametersAnalysis;

import grammarInference.Record.Trace;
import grammarInference.Record.kbhParser;
import it.unimib.disco.lta.alfa.parametersAnalysis.TransformationRulesGenerator;
import it.unimib.disco.lta.alfa.testUtils.ArtifactsProvider;
import it.unimib.disco.lta.alfa.testUtils.LogTraceAnalyzerDriverUtil;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;



import it.unimib.disco.lta.alfa.klfa.LogTraceAnalyzer;
import junit.framework.TestCase;

/**
 * This class checks if the Transformation rules generator works correctly when csv generated from BCT are used 
 * 
 * @author Fabrizio Pastore
 *
 */
public class TransformationRulesGeneratorMethodDataProcessingTest extends TestCase {

	public void testProcessData() throws TransformationRulesGeneratorException, IOException, RuleStatisticsException{
		
		File dataFile = ArtifactsProvider.getUnitTestFile("parametersAnalysis/transformationRulesGeneratorMethodDataProcessing/normalizedMethodData.csv");
		File preprocessingRulesFile = ArtifactsProvider.getUnitTestFile("parametersAnalysis/transformationRulesGeneratorMethodDataProcessing/preprocessingRules.txt");
		File transformationRulesFile = ArtifactsProvider.getUnitTestFile("parametersAnalysis/transformationRulesGeneratorMethodDataProcessing/transformationRules.txt");
		
		
		ParametersAnalyzer parametersAnalyzer = new ParametersAnalyzer();
		
		AnalysisResult parametersAnalysis = parametersAnalyzer.analyze(dataFile);
		ArrayList<TraceData> tracesData = parametersAnalysis.getTracesDatas();
		
		TraceData traceData = tracesData.get(0);
		RulesStatistics rulesStats = traceData.getRulesStatistics();
		RuleStatistics rulesStat = rulesStats.get("pack.Processor.main()_pack.Processor.process((Ljava.lang.String;Ljava.lang.String;Ljava.lang.String;)V)");
		assertEquals(17, rulesStat.getTotalEvents() );
		
		List<String> parameters = new ArrayList<String>(); 
		parameters.addAll(rulesStat.getParameterValues(0));
		Collections.sort(parameters,new Comparator<String>() {

			public int compare(String o1, String o2) {
				return Integer.valueOf(o1.substring(10)) - Integer.valueOf(o2.substring(10));
			}
		});
		
		for ( int i = 1; i < 18; i++ ){
			assertEquals("Useless Id"+i,parameters.get(i-1));
		}
		
		parameters = new ArrayList<String>(); 
		parameters.addAll(rulesStat.getParameterValues(1));
		
		Collections.sort(parameters,new Comparator<String>() {

			public int compare(String o1, String o2) {
				return Integer.valueOf(o1.substring(9)) - Integer.valueOf(o2.substring(9));
			}
		});
		
		for ( int i = 1; i < 18; i++ ){
			assertEquals("A string "+i,parameters.get(i-1));
		}
		
		ParametersClustersGenerator pcg = new ParametersClustersGenerator(parametersAnalysis.getTracesDatas(), 0.75, 0);
		pcg.setDontGroup(false);
		Set<Cluster<RuleParameter>> allClusters = pcg.getAllClusters();
		
		Set<String> signatures = new HashSet<String>();
		
		for ( Cluster<RuleParameter> cluster : allClusters ){
			signatures.add( cluster.getSignature() );
		}
		
		assertEquals(7,signatures.size());
		
		//process internal
		
		assertTrue(signatures.contains("pack.Processor.processInternal((Ljava.lang.String;Ljava.lang.String;Ljava.lang.String;)V)_()_0"+
				"-"+
		"pack.Processor.processInternal((Ljava.lang.String;Ljava.lang.String;Ljava.lang.String;)V)_java.lang.Set.add(Ljava.lang.Object;)_0"));

		assertTrue(signatures.contains("pack.Processor.processInternal((Ljava.lang.String;Ljava.lang.String;Ljava.lang.String;)V)_()_1"));

		
		//process
		assertTrue(signatures.contains("pack.Processor.process((Ljava.lang.String;Ljava.lang.String;Ljava.lang.String;)V)_()_1" +
				"-" +
				"pack.Processor.process((Ljava.lang.String;Ljava.lang.String;Ljava.lang.String;)V)_pack.Processor.processInternal((Ljava.lang.String;Ljava.lang.String;Ljava.lang.String;)V)_0"));
		
		assertTrue(signatures.contains("pack.Processor.process((Ljava.lang.String;Ljava.lang.String;Ljava.lang.String;)V)_()_0"));
		assertTrue(signatures.contains("pack.Processor.process((Ljava.lang.String;Ljava.lang.String;Ljava.lang.String;)V)_pack.Processor.processInternal((Ljava.lang.String;Ljava.lang.String;Ljava.lang.String;)V)_1"));
	
		//main
		
		assertTrue(signatures.contains("pack.Processor.main()_pack.Processor.process((Ljava.lang.String;Ljava.lang.String;Ljava.lang.String;)V)_0"));
		
		assertTrue(signatures.contains("pack.Processor.main()_pack.Processor.process((Ljava.lang.String;Ljava.lang.String;Ljava.lang.String;)V)_1"));
		
		TransformationRulesGenerator rulesGenerator = new TransformationRulesGenerator(dataFile);
		rulesGenerator.setDontGroup(false);
		rulesGenerator.setClustersSupport(0);
		
		
		TracePreprocessorConfiguration configs = rulesGenerator.getSuggestedConfigurations();
		Map<String, String> transformesMap = configs.getTransformersMap();

		assertEquals(8, transformesMap.size());
		assertEquals("SAME",transformesMap.get("SAME"));
		
		//main
		assertEquals("RA",transformesMap.get("pack.Processor.main()_pack.Processor.process((Ljava.lang.String;Ljava.lang.String;Ljava.lang.String;)V)_0"));
		assertEquals("RA",transformesMap.get("pack.Processor.main()_pack.Processor.process((Ljava.lang.String;Ljava.lang.String;Ljava.lang.String;)V)_1"));
		
		//process
		assertEquals("RA",transformesMap.get("pack.Processor.process((Ljava.lang.String;Ljava.lang.String;Ljava.lang.String;)V)_()_0"));
		
		assertEquals("RA",transformesMap.get("pack.Processor.process((Ljava.lang.String;Ljava.lang.String;Ljava.lang.String;)V)_()_1" +
				"-" +
				"pack.Processor.process((Ljava.lang.String;Ljava.lang.String;Ljava.lang.String;)V)_pack.Processor.processInternal((Ljava.lang.String;Ljava.lang.String;Ljava.lang.String;)V)_0"));
		assertEquals("RA",transformesMap.get("pack.Processor.process((Ljava.lang.String;Ljava.lang.String;Ljava.lang.String;)V)_pack.Processor.processInternal((Ljava.lang.String;Ljava.lang.String;Ljava.lang.String;)V)_1"));
		
		//process internal
		
		assertEquals("RA",transformesMap.get("pack.Processor.processInternal((Ljava.lang.String;Ljava.lang.String;Ljava.lang.String;)V)_()_0"+
		"-"+
		"pack.Processor.processInternal((Ljava.lang.String;Ljava.lang.String;Ljava.lang.String;)V)_java.lang.Set.add(Ljava.lang.Object;)_0"));
		
		assertEquals("RA",transformesMap.get("pack.Processor.processInternal((Ljava.lang.String;Ljava.lang.String;Ljava.lang.String;)V)_()_1"));
		
		
		
		rulesGenerator.exportSuggestedPreprocessingRulesConfigurationToFile(preprocessingRulesFile);
		
		rulesGenerator.exportSuggestedTranformersConfigurationToFile(transformationRulesFile);
		
		
		String modelsDirPrefix = "parametersAnalysis/transformationRulesGeneratorMethodDataProcessing/models/";
		File modelsDir = ArtifactsProvider.getNonexistentUnitTestFile(modelsDirPrefix);
		
		LogTraceAnalyzer.main(LogTraceAnalyzerDriverUtil.createTraceAnalyzerArgsTrainingComponentLevel(modelsDir, transformationRulesFile, preprocessingRulesFile, dataFile));
		
		File trainingTrace = ArtifactsProvider.getUnitTestFile(modelsDirPrefix+"pack.Processor.main().trace");
		
		assertTrue(trainingTrace.exists());
		
		kbhParser parser = new kbhParser(trainingTrace.getAbsolutePath());

		Iterator<Trace> tIt = parser.getTraceIterator();


		for( int traceCounter = 1; traceCounter < 3; traceCounter++ ){
			assertTrue(tIt.hasNext());
			Trace trace = tIt.next();
			assertNotNull(trace);

			Iterator<String> sit = trace.getSymbolIterator();
			for ( int i = 0; i < 17; i++ ){
				String expected = "pack.Processor.main()_pack.Processor.process((Ljava.lang.String;Ljava.lang.String;Ljava.lang.String;)V)" +
				"__0"+"__0)";
				String msg = "pack.Processor.main() Trace"+traceCounter+" Problem with entry "+i;
				assertTrue(msg,sit.hasNext());
				assertEquals(msg,expected, sit.next() );
				
			}
			assertFalse(sit.hasNext());
			
			for( int skip = 1; skip < 35; skip++ ){
				trace = tIt.next();
				String msg = "pack.Processor.main() Trace"+skip;
				assertNotNull(msg,trace);
				
				sit = trace.getSymbolIterator();
				assertEquals(0,trace.getLength());
				assertFalse(msg+" has sysmbols",sit.hasNext());
			}
			
		}
		assertFalse(tIt.hasNext());
		
		//
		//
		//
		
		trainingTrace = ArtifactsProvider.getUnitTestFile(modelsDirPrefix+"pack.Processor.process((Ljava.lang.String;Ljava.lang.String;Ljava.lang.String;)V).trace");
		assertTrue(trainingTrace.exists());
		
		parser = new kbhParser(trainingTrace.getAbsolutePath());
		tIt = parser.getTraceIterator();
	
		//first Trace is the main Trace
		for( int execution = 1; execution < 3; execution++ ){



			Trace trace;
			Iterator<String> sit;
			
			for( int traceCounter = 1; traceCounter < 18; traceCounter++ ){
				assertTrue(tIt.hasNext());
				trace = tIt.next();
				assertNotNull(trace);

				sit = trace.getSymbolIterator();
				assertTrue("Trace "+traceCounter,sit.hasNext());
				
				String expected = "pack.Processor.process((Ljava.lang.String;Ljava.lang.String;Ljava.lang.String;)V)" +
				"_" +
				"()" +
				"__0__0)";
				assertEquals("Trace "+traceCounter+": problem with element "+1,expected, sit.next() );

				expected = "pack.Processor.process((Ljava.lang.String;Ljava.lang.String;Ljava.lang.String;)V)" +
				"_" +
				"pack.Processor.processInternal((Ljava.lang.String;Ljava.lang.String;Ljava.lang.String;)V)" +
				"__1__0)";
				assertEquals("Trace "+traceCounter+": problem with element "+2,expected, sit.next() );

				//processInternal trace
				trace = tIt.next();
				assertNotNull(trace);
				sit = trace.getSymbolIterator();
				assertEquals(0,trace.getLength());

				assertFalse("Trace has sysmbols",sit.hasNext());

			}
			
			
			if ( execution > 1 ){
				break;
			} 
			//main trace => empty for process
			
			assertTrue(tIt.hasNext());
			trace = tIt.next();
			assertNotNull(trace);
			sit = trace.getSymbolIterator();
			assertEquals(0,trace.getLength());
			String msg = "Execution "+execution+" trace ";
			assertFalse(msg+" has sysmbols",sit.hasNext());
		}
		assertFalse(tIt.hasNext());

		trainingTrace = ArtifactsProvider.getUnitTestFile(modelsDirPrefix+"pack.Processor.processInternal((Ljava.lang.String;Ljava.lang.String;Ljava.lang.String;)V).trace");
		assertTrue(trainingTrace.exists());
		
		
		parser = new kbhParser(trainingTrace.getAbsolutePath());
		tIt = parser.getTraceIterator();
		
		//first Trace is the main Trace
		for( int execution = 1; execution < 3; execution++ ){



			Trace trace;
			Iterator<String> sit;
			
			for( int traceCounter = 1; traceCounter < 18; traceCounter++ ){
				assertTrue(tIt.hasNext());
				trace = tIt.next();
				assertNotNull(trace);

				sit = trace.getSymbolIterator();
				assertTrue("Execution "+execution+" Trace "+traceCounter,sit.hasNext());
				
				String expected = "pack.Processor.processInternal((Ljava.lang.String;Ljava.lang.String;Ljava.lang.String;)V)" +
				"_" +
				"()" +
				"__0__0)";
				assertEquals("Trace "+traceCounter+": problem with element "+1,expected, sit.next() );

				expected = "pack.Processor.processInternal((Ljava.lang.String;Ljava.lang.String;Ljava.lang.String;)V)" +
				"_" +
				"java.lang.Set.add(Ljava.lang.Object;)" +
				"__1)";
				assertEquals("Trace "+traceCounter+": problem with element "+2,expected, sit.next() );

				if ( execution > 1 && traceCounter == 17 ){
					break;
				}
				
				//process trace
				trace = tIt.next();
				assertNotNull(trace);
				sit = trace.getSymbolIterator();
				assertEquals(0,trace.getLength());

				assertFalse("Trace has sysmbols",sit.hasNext());

			}
			
			if ( execution > 1 ){
				break;
			}
			
			//main trace
			trace = tIt.next();
			assertNotNull(trace);
			sit = trace.getSymbolIterator();
			assertEquals(0,trace.getLength());

			assertFalse("Trace has sysmbols",sit.hasNext());
			
		}
		assertFalse(tIt.hasNext());
		
	}
}
