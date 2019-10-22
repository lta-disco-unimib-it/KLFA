package it.unimib.disco.lta.alfa.tests;

import grammarInference.Record.Trace;
import grammarInference.Record.kbhParser;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import junit.framework.TestCase;


import it.unimib.disco.lta.alfa.klfa.KLFALogAnomaly;
import it.unimib.disco.lta.alfa.klfa.KLFALogAnomalyCsvExporter;
import it.unimib.disco.lta.alfa.klfa.LogTraceAnalyzer;
import it.unimib.disco.lta.alfa.testUtils.ArtifactsProvider;
import it.unimib.disco.lta.alfa.testUtils.LogTraceAnalyzerDriverUtil;

import org.junit.Test;

public class LogTraceAnalyzerCsvParsingTest extends TestCase{

	/**
	 * =======Test definition========
	 * 
	 * element position
	 * 	0
	 * 	1
	 * 	>1
	 * 
	 * quotes
	 * 	with	[quotes]
	 * 	without
	 * 
	 * space in parameter value
	 * 	yes
	 * 	no
	 * 
	 * comma in parameter value
	 * 	yes	[if quotes]
	 * 	no
	 * 
	 * quotes in parameter value
	 * 	yes	[if quotes]
	 * 	no
	 * 
	 * 
	 * =======End test definition========
	 * 
	 * @throws IOException
	 */
	@Test
	public void test1_CsvTraces() throws IOException {
		
		File transformersFile = ArtifactsProvider.getFile("system/LogTraceAnalyzer/artifacts/csvtest1/config/transformers.txt");
		File preprocessingRulesFile = ArtifactsProvider.getFile("system/LogTraceAnalyzer/artifacts/csvtest1/config/preprocessingRules.txt");
		
		File trainingLog = ArtifactsProvider.getFile("system/LogTraceAnalyzer/artifacts/csvtest1/training.csv");
		File checkingLog = ArtifactsProvider.getFile("system/LogTraceAnalyzer/artifacts/csvtest1/checking.csv");
		
		File modelsDir = ArtifactsProvider.getNonExistentFile("system/LogTraceAnalyzer/artifacts/csvtest1/models");
		File checkingDir = ArtifactsProvider.getNonExistentFile("system/LogTraceAnalyzer/artifacts/csvtest1/checking");
		
		File anomaliesCsvFile = ArtifactsProvider.getNonExistentFile("system/LogTraceAnalyzer/artifacts/csvtest1/checking/anomalies.csv");
		
		LogTraceAnalyzer.main(LogTraceAnalyzerDriverUtil.createTraceAnalyzerArgsTrainingApplicationLevel(modelsDir, transformersFile, preprocessingRulesFile, trainingLog));
		
		
		File trainingTrace = ArtifactsProvider.getFile("system/LogTraceAnalyzer/artifacts/csvtest1/models/GLOBAL.trace");
		
		kbhParser parser = new kbhParser(trainingTrace.getAbsolutePath());
		
		Iterator<Trace> tIt = parser.getTraceIterator();
		assertTrue(tIt.hasNext());
		Trace trace = tIt.next();
		assertNotNull(trace);
		Iterator<String> sit = trace.getSymbolIterator();
		assertTrue(sit.hasNext());
		String columnSeparator = "_";
		
		assertEquals("C"+columnSeparator+"A)", sit.next() );
		assertEquals("C"+columnSeparator+"B)", sit.next() );
		assertEquals("C"+columnSeparator+"C)", sit.next() );
		assertEquals("C"+columnSeparator+"D)", sit.next() );
		assertEquals("C"+columnSeparator+"E)", sit.next() );
		assertEquals("C"+columnSeparator+"F)", sit.next() );
		assertEquals("C"+columnSeparator+"G)", sit.next() );
		assertEquals("C"+columnSeparator+"H)", sit.next() );
		assertEquals("C"+columnSeparator+"C C)", sit.next() );
		assertEquals("C"+columnSeparator+"C D"+columnSeparator+"1)", sit.next() );
		assertEquals("C"+columnSeparator+"action name"+columnSeparator+"1"+columnSeparator+"second)", sit.next() );
		assertEquals("C"+columnSeparator+"action , name"+columnSeparator+"1"+columnSeparator+"second parameter)", sit.next() );
		assertEquals("C"+columnSeparator+"an action without quotes"+columnSeparator+"1"+columnSeparator+"second parameter)", sit.next() );
		assertEquals("C"+columnSeparator+"another action name"+columnSeparator+"1"+columnSeparator+"second parameter"+columnSeparator+"parameter with escaped \""+columnSeparator+"2)", sit.next() );
		assertEquals("component name"+columnSeparator+"action2 name"+columnSeparator+"parameter one"+columnSeparator+"5)", sit.next() );
		assertEquals("component \" name"+columnSeparator+"action \" name"+columnSeparator+"parameter one"+columnSeparator+"5)", sit.next() );
		assertEquals("component \" name"+columnSeparator+"action \" name, "+columnSeparator+",parameter one"+columnSeparator+"5)", sit.next() );
		assertEquals("C"+columnSeparator+"A)", sit.next() );
		
		
		LogTraceAnalyzer.main(LogTraceAnalyzerDriverUtil.createTraceAnalyzerArgsCheckingApplicationLevel(modelsDir, checkingDir,  transformersFile, preprocessingRulesFile, checkingLog));
		
		KLFALogAnomalyCsvExporter exporter = new KLFALogAnomalyCsvExporter();
		
		List<KLFALogAnomaly> anomalies = exporter.importFromCsv(anomaliesCsvFile);
		
		assertEquals( 0, anomalies.size() );
		
		
	}
	
	
	public void test1_CsvTransformers() throws IOException {
		
		File transformersFile = ArtifactsProvider.getFile("system/LogTraceAnalyzer/artifacts/csvtest2/config/transformers.txt");
		File preprocessingRulesFile = ArtifactsProvider.getFile("system/LogTraceAnalyzer/artifacts/csvtest2/config/preprocessingRules.txt");
		
		File trainingLog = ArtifactsProvider.getFile("system/LogTraceAnalyzer/artifacts/csvtest2/training.csv");
		File checkingLog = ArtifactsProvider.getFile("system/LogTraceAnalyzer/artifacts/csvtest2/checking.csv");
		
		File modelsDir = ArtifactsProvider.getNonExistentFile("system/LogTraceAnalyzer/artifacts/csvtest2/models");
		File checkingDir = ArtifactsProvider.getNonExistentFile("system/LogTraceAnalyzer/artifacts/csvtest2/checking");
		
		File anomaliesCsvFile = ArtifactsProvider.getNonExistentFile("system/LogTraceAnalyzer/artifacts/csvtest2/checking/anomalies.csv");
		
		LogTraceAnalyzer.main(LogTraceAnalyzerDriverUtil.createTraceAnalyzerArgsTrainingApplicationLevel(modelsDir, transformersFile, preprocessingRulesFile, trainingLog));
		
		
		File trainingTrace = ArtifactsProvider.getFile("system/LogTraceAnalyzer/artifacts/csvtest2/models/GLOBAL.trace");
		
		kbhParser parser = new kbhParser(trainingTrace.getAbsolutePath());
		
		Iterator<Trace> tIt = parser.getTraceIterator();
		assertTrue(tIt.hasNext());
		Trace trace = tIt.next();
		assertNotNull(trace);
		Iterator<String> sit = trace.getSymbolIterator();
		assertTrue(sit.hasNext());
		String columnSeparator = "_";
		String parametersSeparator = "_";
		assertEquals("C aa"+columnSeparator+"action A name"+parametersSeparator+columnSeparator+"0)", sit.next() );
		assertEquals("C bu"+columnSeparator+"action B name"+parametersSeparator+columnSeparator+"0)", sit.next() );
		assertEquals("C ab"+columnSeparator+"action A name"+parametersSeparator+columnSeparator+"1)", sit.next() );
		assertEquals("C bi"+columnSeparator+"action B name"+parametersSeparator+columnSeparator+"1)", sit.next() );
		assertEquals("C ac"+columnSeparator+"action A name"+parametersSeparator+columnSeparator+"1)", sit.next() );
		assertEquals("C ad"+columnSeparator+"action A name"+parametersSeparator+columnSeparator+"0)", sit.next() );
		assertEquals("C ad"+columnSeparator+"action H name"+parametersSeparator+columnSeparator+"0)", sit.next() );
		assertEquals("C ac"+columnSeparator+"action H name"+parametersSeparator+columnSeparator+"1)", sit.next() );
		assertEquals("C ac"+columnSeparator+"action A name"+parametersSeparator+columnSeparator+"1)", sit.next() );
		
		LogTraceAnalyzer.main(LogTraceAnalyzerDriverUtil.createTraceAnalyzerArgsCheckingApplicationLevel(modelsDir, checkingDir,  transformersFile, preprocessingRulesFile, checkingLog));
		
		KLFALogAnomalyCsvExporter exporter = new KLFALogAnomalyCsvExporter();
		
		List<KLFALogAnomaly> anomalies = exporter.importFromCsv(anomaliesCsvFile);
		
		assertEquals( 1, anomalies.size() );
		
		KLFALogAnomaly anomaly = anomalies.get(0);
		assertEquals(0, anomaly.getBranchLength() );
		assertEquals( KLFALogAnomaly.AnomalyType.FinalState, anomaly.getAnomalyType());
		
		
		
	}

	
	

}
