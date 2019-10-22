package it.unimib.disco.lta.alfa.tests;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;

import org.junit.After;
import org.junit.Before;

import it.unimib.disco.lta.alfa.klfa.KLFALogAnomaly;
import it.unimib.disco.lta.alfa.klfa.KLFALogAnomalyCsvExporter;
import it.unimib.disco.lta.alfa.klfa.LogTraceAnalyzer;
import it.unimib.disco.lta.alfa.klfa.KLFALogAnomaly.AnomalyType;
import it.unimib.disco.lta.alfa.testUtils.ArtifactsProvider;
import it.unimib.disco.lta.alfa.testUtils.LogTraceAnalyzerDriverUtil;

/**
 * 
 * Change type
 * 	insertion
 * 	deletion
 * 	replacement
 * 
 * #elements
 * 	1
 * 	>1 
 * 
 * Position
 * 	head
 * 	tail
 * 	middle
 * 	
 * 
 * Complete test
 * 
 * 
 * @author Fabrizio Pastore
 *
 */
public class LogTraceAnalyzerTest extends TestCase {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
		
	}

	public void test1_DeleteOneHead() throws IOException {
		
		File transformersFile = ArtifactsProvider.getFile("system/LogTraceAnalyzer/artifacts/all/transformers.txt");
		File preprocessingRulesFile = ArtifactsProvider.getFile("system/LogTraceAnalyzer/artifacts/all/preprocessingRules.txt");
		
		File trainingLog = ArtifactsProvider.getFile("system/LogTraceAnalyzer/artifacts/test1/training.csv");
		File checkingLog = ArtifactsProvider.getFile("system/LogTraceAnalyzer/artifacts/test1/checking.csv");
		
		File modelsDir = ArtifactsProvider.getNonExistentFile("system/LogTraceAnalyzer/artifacts/test1/models");
		File checkingDir = ArtifactsProvider.getNonExistentFile("system/LogTraceAnalyzer/artifacts/test1/checking");
		
		File anomaliesCsvFile = ArtifactsProvider.getNonExistentFile("system/LogTraceAnalyzer/artifacts/test1/checking/anomalies.csv");
		
		LogTraceAnalyzer.main(LogTraceAnalyzerDriverUtil.createTraceAnalyzerArgsTrainingApplicationLevel(modelsDir, transformersFile, preprocessingRulesFile, trainingLog));
		
		LogTraceAnalyzer.main(LogTraceAnalyzerDriverUtil.createTraceAnalyzerArgsCheckingApplicationLevel(modelsDir, checkingDir,  transformersFile, preprocessingRulesFile, checkingLog));
		
		KLFALogAnomalyCsvExporter exporter = new KLFALogAnomalyCsvExporter();
		
		List<KLFALogAnomaly> anomalies = exporter.importFromCsv(anomaliesCsvFile);
		
		assertEquals( 1, anomalies.size() );
		
		KLFALogAnomaly anomaly = anomalies.get(0);
		
		assertEquals( 1, anomaly.getAnomalyLine() );
		
		assertEquals( 1, anomaly.getOriginalAnomalyLine() );
		
		List<String> expectedAnomalous = new ArrayList<String>();
		expectedAnomalous.add("C_B)");
		assertEquals( expectedAnomalous, anomaly.getAnomalousEvents() );
		
		assertEquals( "q0", anomaly.getFromState() );
		
		assertEquals( "q1", anomaly.getToState() );
		
		assertEquals( 0, anomaly.getBranchLength() );
		
		List<String> expectedIncoming = new ArrayList<String>();
		expectedIncoming.add("q0)C_A)");
		assertEquals( expectedIncoming, anomaly.getExpectedIncoming() );
		
		List<String> expectedOutgoing = new ArrayList<String>();
		expectedOutgoing .add("C_A)");
		assertEquals( expectedOutgoing, anomaly.getExpectedOutgoing() );
		
		assertEquals( AnomalyType.Branch, anomaly.getAnomalyType() );
	}
	
	
	public void test2_InsertOneHead() throws IOException {
		
		File transformersFile = ArtifactsProvider.getFile("system/LogTraceAnalyzer/artifacts/all/transformers.txt");
		File preprocessingRulesFile = ArtifactsProvider.getFile("system/LogTraceAnalyzer/artifacts/all/preprocessingRules.txt");
		
		File trainingLog = ArtifactsProvider.getFile("system/LogTraceAnalyzer/artifacts/test2/training.csv");
		File checkingLog = ArtifactsProvider.getFile("system/LogTraceAnalyzer/artifacts/test2/checking.csv");
		
		File modelsDir = ArtifactsProvider.getNonExistentFile("system/LogTraceAnalyzer/artifacts/test2/models");
		File checkingDir = ArtifactsProvider.getNonExistentFile("system/LogTraceAnalyzer/artifacts/test2/checking");
		
		File anomaliesCsvFile = ArtifactsProvider.getNonExistentFile("system/LogTraceAnalyzer/artifacts/test2/checking/anomalies.csv");
		
		LogTraceAnalyzer.main(LogTraceAnalyzerDriverUtil.createTraceAnalyzerArgsTrainingApplicationLevel(modelsDir, transformersFile, preprocessingRulesFile, trainingLog));
		
		LogTraceAnalyzer.main(LogTraceAnalyzerDriverUtil.createTraceAnalyzerArgsCheckingApplicationLevel(modelsDir, checkingDir,  transformersFile, preprocessingRulesFile, checkingLog));
		
		KLFALogAnomalyCsvExporter exporter = new KLFALogAnomalyCsvExporter();
		
		List<KLFALogAnomaly> anomalies = exporter.importFromCsv(anomaliesCsvFile);
		
		assertEquals( 1, anomalies.size() );
		
		KLFALogAnomaly anomaly = anomalies.get(0);
		
		assertEquals( 1, anomaly.getAnomalyLine() );
		
		assertEquals( 1, anomaly.getOriginalAnomalyLine() );
		
		List<String> expectedAnomalous = new ArrayList<String>();
		expectedAnomalous.add("C_X)");
		assertEquals( expectedAnomalous, anomaly.getAnomalousEvents() );
		
		assertEquals( "q0", anomaly.getFromState() );
		
		assertEquals( "q0", anomaly.getToState() );
		
		assertEquals( 1, anomaly.getBranchLength() );
		
		List<String> expectedIncoming = new ArrayList<String>();
		assertEquals( expectedIncoming, anomaly.getExpectedIncoming() );
		
		List<String> expectedOutgoing = new ArrayList<String>();
		expectedOutgoing .add("C_A)");
		assertEquals( expectedOutgoing, anomaly.getExpectedOutgoing() );
		
		assertEquals( AnomalyType.Branch, anomaly.getAnomalyType() );
	}
	
	public void test3_ReplaceOneHead() throws IOException {
		
		File transformersFile = ArtifactsProvider.getFile("system/LogTraceAnalyzer/artifacts/all/transformers.txt");
		File preprocessingRulesFile = ArtifactsProvider.getFile("system/LogTraceAnalyzer/artifacts/all/preprocessingRules.txt");
		
		File trainingLog = ArtifactsProvider.getFile("system/LogTraceAnalyzer/artifacts/test3/training.csv");
		File checkingLog = ArtifactsProvider.getFile("system/LogTraceAnalyzer/artifacts/test3/checking.csv");
		
		File modelsDir = ArtifactsProvider.getNonExistentFile("system/LogTraceAnalyzer/artifacts/test3/models");
		File checkingDir = ArtifactsProvider.getNonExistentFile("system/LogTraceAnalyzer/artifacts/test3/checking");
		
		File anomaliesCsvFile = ArtifactsProvider.getNonExistentFile("system/LogTraceAnalyzer/artifacts/test3/checking/anomalies.csv");
		
		LogTraceAnalyzer.main(LogTraceAnalyzerDriverUtil.createTraceAnalyzerArgsTrainingApplicationLevel(modelsDir, transformersFile, preprocessingRulesFile, trainingLog));
		
		LogTraceAnalyzer.main(LogTraceAnalyzerDriverUtil.createTraceAnalyzerArgsCheckingApplicationLevel(modelsDir, checkingDir,  transformersFile, preprocessingRulesFile, checkingLog));
		
		KLFALogAnomalyCsvExporter exporter = new KLFALogAnomalyCsvExporter();
		
		List<KLFALogAnomaly> anomalies = exporter.importFromCsv(anomaliesCsvFile);
		
		assertEquals( 1, anomalies.size() );
		
		KLFALogAnomaly anomaly = anomalies.get(0);
		
		assertEquals( 1, anomaly.getAnomalyLine() );
		
		assertEquals( 1, anomaly.getOriginalAnomalyLine() );
		
		List<String> expectedAnomalous = new ArrayList<String>();
		expectedAnomalous.add("C_X)");
		assertEquals( expectedAnomalous, anomaly.getAnomalousEvents() );
		
		assertEquals( "q0", anomaly.getFromState() );
		
		assertEquals( "q1", anomaly.getToState() );
		
		assertEquals( 1, anomaly.getBranchLength() );
		
		List<String> expectedIncoming = new ArrayList<String>();
		expectedIncoming .add("q0)C_A)");
		assertEquals( expectedIncoming, anomaly.getExpectedIncoming() );
		
		List<String> expectedOutgoing = new ArrayList<String>();
		expectedOutgoing .add("C_A)");
		assertEquals( expectedOutgoing, anomaly.getExpectedOutgoing() );
		
		assertEquals( AnomalyType.Branch, anomaly.getAnomalyType() );
	}


	public void test4_DeleteOneMiddle() throws IOException {
		
		File transformersFile = ArtifactsProvider.getFile("system/LogTraceAnalyzer/artifacts/all/transformers.txt");
		File preprocessingRulesFile = ArtifactsProvider.getFile("system/LogTraceAnalyzer/artifacts/all/preprocessingRules.txt");
		
		File trainingLog = ArtifactsProvider.getFile("system/LogTraceAnalyzer/artifacts/test4/training.csv");
		File checkingLog = ArtifactsProvider.getFile("system/LogTraceAnalyzer/artifacts/test4/checking.csv");
		
		File modelsDir = ArtifactsProvider.getNonExistentFile("system/LogTraceAnalyzer/artifacts/test4/models");
		File checkingDir = ArtifactsProvider.getNonExistentFile("system/LogTraceAnalyzer/artifacts/test4/checking");
		
		File anomaliesCsvFile = ArtifactsProvider.getNonExistentFile("system/LogTraceAnalyzer/artifacts/test4/checking/anomalies.csv");
		
		LogTraceAnalyzer.main(LogTraceAnalyzerDriverUtil.createTraceAnalyzerArgsTrainingApplicationLevel(modelsDir, transformersFile, preprocessingRulesFile, trainingLog));
		
		LogTraceAnalyzer.main(LogTraceAnalyzerDriverUtil.createTraceAnalyzerArgsCheckingApplicationLevel(modelsDir, checkingDir,  transformersFile, preprocessingRulesFile, checkingLog));
		
		KLFALogAnomalyCsvExporter exporter = new KLFALogAnomalyCsvExporter();
		
		List<KLFALogAnomaly> anomalies = exporter.importFromCsv(anomaliesCsvFile);
		
		assertEquals( 1, anomalies.size() );
		
		KLFALogAnomaly anomaly = anomalies.get(0);
		
		assertEquals( 4, anomaly.getAnomalyLine() );
		
		assertEquals( 4, anomaly.getOriginalAnomalyLine() );
		
		List<String> expectedAnomalous = new ArrayList<String>();
		expectedAnomalous.add("C_E)");
		assertEquals( expectedAnomalous, anomaly.getAnomalousEvents() );
		
		assertEquals( "q3", anomaly.getFromState() );
		
		assertEquals( "q4", anomaly.getToState() );
		
		assertEquals( 0, anomaly.getBranchLength() );
		
		List<String> expectedIncoming = new ArrayList<String>();
		expectedIncoming.add("q3)C_D)");
		assertEquals( expectedIncoming, anomaly.getExpectedIncoming() );
		
		List<String> expectedOutgoing = new ArrayList<String>();
		expectedOutgoing .add("C_D)");
		assertEquals( expectedOutgoing, anomaly.getExpectedOutgoing() );
		
		assertEquals( AnomalyType.Branch, anomaly.getAnomalyType() );
	}
	
	
	public void test5_InsertOneMiddle() throws IOException {
		
		File transformersFile = ArtifactsProvider.getFile("system/LogTraceAnalyzer/artifacts/all/transformers.txt");
		File preprocessingRulesFile = ArtifactsProvider.getFile("system/LogTraceAnalyzer/artifacts/all/preprocessingRules.txt");
		
		File trainingLog = ArtifactsProvider.getFile("system/LogTraceAnalyzer/artifacts/test5/training.csv");
		File checkingLog = ArtifactsProvider.getFile("system/LogTraceAnalyzer/artifacts/test5/checking.csv");
		
		File modelsDir = ArtifactsProvider.getNonExistentFile("system/LogTraceAnalyzer/artifacts/test5/models");
		File checkingDir = ArtifactsProvider.getNonExistentFile("system/LogTraceAnalyzer/artifacts/test5/checking");
		
		File anomaliesCsvFile = ArtifactsProvider.getNonExistentFile("system/LogTraceAnalyzer/artifacts/test5/checking/anomalies.csv");
		
		LogTraceAnalyzer.main(LogTraceAnalyzerDriverUtil.createTraceAnalyzerArgsTrainingApplicationLevel(modelsDir, transformersFile, preprocessingRulesFile, trainingLog));
		
		LogTraceAnalyzer.main(LogTraceAnalyzerDriverUtil.createTraceAnalyzerArgsCheckingApplicationLevel(modelsDir, checkingDir,  transformersFile, preprocessingRulesFile, checkingLog));
		
		KLFALogAnomalyCsvExporter exporter = new KLFALogAnomalyCsvExporter();
		
		List<KLFALogAnomaly> anomalies = exporter.importFromCsv(anomaliesCsvFile);
		
		assertEquals( 1, anomalies.size() );
		
		KLFALogAnomaly anomaly = anomalies.get(0);
		
		assertEquals( 4, anomaly.getAnomalyLine() );
		
		assertEquals( 4, anomaly.getOriginalAnomalyLine() );
		
		List<String> expectedAnomalous = new ArrayList<String>();
		expectedAnomalous.add("C_X)");
		assertEquals( expectedAnomalous, anomaly.getAnomalousEvents() );
		
		assertEquals( "q3", anomaly.getFromState() );
		
		assertEquals( "q3", anomaly.getToState() );
		
		assertEquals( 1, anomaly.getBranchLength() );
		
		List<String> expectedIncoming = new ArrayList<String>();
		expectedIncoming.add("q2)C_C)");
		assertEquals( expectedIncoming, anomaly.getExpectedIncoming() );
		
		List<String> expectedOutgoing = new ArrayList<String>();
		expectedOutgoing .add("C_D)");
		assertEquals( expectedOutgoing, anomaly.getExpectedOutgoing() );
		
		assertEquals( AnomalyType.Branch, anomaly.getAnomalyType() );
	}
	
	public void test6_ReplaceOneMiddle() throws IOException {
		
		File transformersFile = ArtifactsProvider.getFile("system/LogTraceAnalyzer/artifacts/all/transformers.txt");
		File preprocessingRulesFile = ArtifactsProvider.getFile("system/LogTraceAnalyzer/artifacts/all/preprocessingRules.txt");
		
		File trainingLog = ArtifactsProvider.getFile("system/LogTraceAnalyzer/artifacts/test6/training.csv");
		File checkingLog = ArtifactsProvider.getFile("system/LogTraceAnalyzer/artifacts/test6/checking.csv");
		
		File modelsDir = ArtifactsProvider.getNonExistentFile("system/LogTraceAnalyzer/artifacts/test6/models");
		File checkingDir = ArtifactsProvider.getNonExistentFile("system/LogTraceAnalyzer/artifacts/test6/checking");
		
		File anomaliesCsvFile = ArtifactsProvider.getNonExistentFile("system/LogTraceAnalyzer/artifacts/test6/checking/anomalies.csv");
		
		LogTraceAnalyzer.main(LogTraceAnalyzerDriverUtil.createTraceAnalyzerArgsTrainingApplicationLevel(modelsDir, transformersFile, preprocessingRulesFile, trainingLog));
		
		LogTraceAnalyzer.main(LogTraceAnalyzerDriverUtil.createTraceAnalyzerArgsCheckingApplicationLevel(modelsDir, checkingDir,  transformersFile, preprocessingRulesFile, checkingLog));
		
		KLFALogAnomalyCsvExporter exporter = new KLFALogAnomalyCsvExporter();
		
		List<KLFALogAnomaly> anomalies = exporter.importFromCsv(anomaliesCsvFile);
		
		assertEquals( 1, anomalies.size() );
		
		KLFALogAnomaly anomaly = anomalies.get(0);
		
		assertEquals( 4, anomaly.getAnomalyLine() );
		
		assertEquals( 4, anomaly.getOriginalAnomalyLine() );
		
		List<String> expectedAnomalous = new ArrayList<String>();
		expectedAnomalous.add("C_X)");
		assertEquals( expectedAnomalous, anomaly.getAnomalousEvents() );
		
		assertEquals( "q3", anomaly.getFromState() );
		
		assertEquals( "q4", anomaly.getToState() );
		
		assertEquals( 1, anomaly.getBranchLength() );
		
		List<String> expectedIncoming = new ArrayList<String>();
		expectedIncoming .add("q3)C_D)");
		assertEquals( expectedIncoming, anomaly.getExpectedIncoming() );
		
		List<String> expectedOutgoing = new ArrayList<String>();
		expectedOutgoing .add("C_D)");
		assertEquals( expectedOutgoing, anomaly.getExpectedOutgoing() );
		
		assertEquals( AnomalyType.Branch, anomaly.getAnomalyType() );
	}

	
	
	public void test7_DeleteOneTail() throws IOException {
		
		File transformersFile = ArtifactsProvider.getFile("system/LogTraceAnalyzer/artifacts/all/transformers.txt");
		File preprocessingRulesFile = ArtifactsProvider.getFile("system/LogTraceAnalyzer/artifacts/all/preprocessingRules.txt");
		
		File trainingLog = ArtifactsProvider.getFile("system/LogTraceAnalyzer/artifacts/test7/training.csv");
		File checkingLog = ArtifactsProvider.getFile("system/LogTraceAnalyzer/artifacts/test7/checking.csv");
		
		File modelsDir = ArtifactsProvider.getNonExistentFile("system/LogTraceAnalyzer/artifacts/test7/models");
		File checkingDir = ArtifactsProvider.getNonExistentFile("system/LogTraceAnalyzer/artifacts/test7/checking");
		
		File anomaliesCsvFile = ArtifactsProvider.getNonExistentFile("system/LogTraceAnalyzer/artifacts/test7/checking/anomalies.csv");
		
		LogTraceAnalyzer.main(LogTraceAnalyzerDriverUtil.createTraceAnalyzerArgsTrainingApplicationLevel(modelsDir, transformersFile, preprocessingRulesFile, trainingLog));
		
		LogTraceAnalyzer.main(LogTraceAnalyzerDriverUtil.createTraceAnalyzerArgsCheckingApplicationLevel(modelsDir, checkingDir,  transformersFile, preprocessingRulesFile, checkingLog));
		
		KLFALogAnomalyCsvExporter exporter = new KLFALogAnomalyCsvExporter();
		
		List<KLFALogAnomaly> anomalies = exporter.importFromCsv(anomaliesCsvFile);
		
		assertEquals( 1, anomalies.size() );
		
		KLFALogAnomaly anomaly = anomalies.get(0);
		
		assertEquals( AnomalyType.FinalState, anomaly.getAnomalyType() );
		
		assertEquals( 7+1, anomaly.getAnomalyLine() );
		
		assertEquals( 7+1, anomaly.getOriginalAnomalyLine() );
		
		List<String> expectedAnomalous = new ArrayList<String>();
		expectedAnomalous.add("C_G)");
		assertEquals( expectedAnomalous, anomaly.getAnomalousEvents() );
		
		assertEquals( "q7", anomaly.getFromState() );
		
		assertEquals( null, anomaly.getToState() );
		
		assertEquals( 0, anomaly.getBranchLength() );
		
		List<String> expectedIncoming = new ArrayList<String>();
		expectedIncoming.add("q7)C_H)");
		assertEquals( expectedIncoming, anomaly.getExpectedIncoming() );
		
		List<String> expectedOutgoing = new ArrayList<String>();
		expectedOutgoing .add("C_H)");
		assertEquals( expectedOutgoing, anomaly.getExpectedOutgoing() );
		
		
	}
	
	
	public void test8_InsertOneTail() throws IOException {
		
		File transformersFile = ArtifactsProvider.getFile("system/LogTraceAnalyzer/artifacts/all/transformers.txt");
		File preprocessingRulesFile = ArtifactsProvider.getFile("system/LogTraceAnalyzer/artifacts/all/preprocessingRules.txt");
		
		File trainingLog = ArtifactsProvider.getFile("system/LogTraceAnalyzer/artifacts/test8/training.csv");
		File checkingLog = ArtifactsProvider.getFile("system/LogTraceAnalyzer/artifacts/test8/checking.csv");
		
		File modelsDir = ArtifactsProvider.getNonExistentFile("system/LogTraceAnalyzer/artifacts/test8/models");
		File checkingDir = ArtifactsProvider.getNonExistentFile("system/LogTraceAnalyzer/artifacts/test8/checking");
		
		File anomaliesCsvFile = ArtifactsProvider.getNonExistentFile("system/LogTraceAnalyzer/artifacts/test8/checking/anomalies.csv");
		
		LogTraceAnalyzer.main(LogTraceAnalyzerDriverUtil.createTraceAnalyzerArgsTrainingApplicationLevel(modelsDir, transformersFile, preprocessingRulesFile, trainingLog));
		
		LogTraceAnalyzer.main(LogTraceAnalyzerDriverUtil.createTraceAnalyzerArgsCheckingApplicationLevel(modelsDir, checkingDir,  transformersFile, preprocessingRulesFile, checkingLog));
		
		KLFALogAnomalyCsvExporter exporter = new KLFALogAnomalyCsvExporter();
		
		List<KLFALogAnomaly> anomalies = exporter.importFromCsv(anomaliesCsvFile);
		
		assertEquals( 1, anomalies.size() );
		
		KLFALogAnomaly anomaly = anomalies.get(0);
		
		assertEquals( 9, anomaly.getAnomalyLine() );
		
		assertEquals( 9, anomaly.getOriginalAnomalyLine() );
		
		List<String> expectedAnomalous = new ArrayList<String>();
		expectedAnomalous.add("C_I)");
		assertEquals( expectedAnomalous, anomaly.getAnomalousEvents() );
		
		assertEquals( "q8", anomaly.getFromState() );
		
		assertEquals( null, anomaly.getToState() );
		
		assertEquals( 1, anomaly.getBranchLength() );
		
		List<String> expectedIncoming = new ArrayList<String>();
		assertEquals( expectedIncoming, anomaly.getExpectedIncoming() );
		
		List<String> expectedOutgoing = new ArrayList<String>();
		
		assertEquals( expectedOutgoing, anomaly.getExpectedOutgoing() );
		
		assertEquals( AnomalyType.Tail, anomaly.getAnomalyType() );
	}
	
	public void test9_ReplaceOneTail() throws IOException {
		
		File transformersFile = ArtifactsProvider.getFile("system/LogTraceAnalyzer/artifacts/all/transformers.txt");
		File preprocessingRulesFile = ArtifactsProvider.getFile("system/LogTraceAnalyzer/artifacts/all/preprocessingRules.txt");
		
		File trainingLog = ArtifactsProvider.getFile("system/LogTraceAnalyzer/artifacts/test9/training.csv");
		File checkingLog = ArtifactsProvider.getFile("system/LogTraceAnalyzer/artifacts/test9/checking.csv");
		
		File modelsDir = ArtifactsProvider.getNonExistentFile("system/LogTraceAnalyzer/artifacts/test9/models");
		File checkingDir = ArtifactsProvider.getNonExistentFile("system/LogTraceAnalyzer/artifacts/test9/checking");
		
		File anomaliesCsvFile = ArtifactsProvider.getNonExistentFile("system/LogTraceAnalyzer/artifacts/test9/checking/anomalies.csv");
		
		LogTraceAnalyzer.main(LogTraceAnalyzerDriverUtil.createTraceAnalyzerArgsTrainingApplicationLevel(modelsDir, transformersFile, preprocessingRulesFile, trainingLog));
		
		LogTraceAnalyzer.main(LogTraceAnalyzerDriverUtil.createTraceAnalyzerArgsCheckingApplicationLevel(modelsDir, checkingDir,  transformersFile, preprocessingRulesFile, checkingLog));
		
		KLFALogAnomalyCsvExporter exporter = new KLFALogAnomalyCsvExporter();
		
		List<KLFALogAnomaly> anomalies = exporter.importFromCsv(anomaliesCsvFile);
		
		assertEquals( 1, anomalies.size() );
		
		KLFALogAnomaly anomaly = anomalies.get(0);
		
		assertEquals( 8, anomaly.getAnomalyLine() );
		
		assertEquals( 8, anomaly.getOriginalAnomalyLine() );
		
		List<String> expectedAnomalous = new ArrayList<String>();
		expectedAnomalous.add("C_X)");
		assertEquals( expectedAnomalous, anomaly.getAnomalousEvents() );
		
		assertEquals( "q7", anomaly.getFromState() );
		
		assertEquals( null, anomaly.getToState() );
		
		assertEquals( 1, anomaly.getBranchLength() );
		
		List<String> expectedIncoming = new ArrayList<String>();
		
		assertEquals( expectedIncoming, anomaly.getExpectedIncoming() );
		
		List<String> expectedOutgoing = new ArrayList<String>();
		expectedOutgoing .add("C_H)");
		assertEquals( expectedOutgoing, anomaly.getExpectedOutgoing() );
		
		assertEquals( AnomalyType.Tail, anomaly.getAnomalyType() );
	}
	
	
	

}
