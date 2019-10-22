package it.unimib.disco.lta.alfa.parametersAnalysis;


import static org.junit.Assert.*;

import it.unimib.disco.lta.alfa.eventsDetection.slct.SlctPattern;
import it.unimib.disco.lta.alfa.parametersAnalysis.AnalysisResult;
import it.unimib.disco.lta.alfa.parametersAnalysis.Cluster;
import it.unimib.disco.lta.alfa.parametersAnalysis.ClusterStatistics;
import it.unimib.disco.lta.alfa.parametersAnalysis.ClusterStatisticsGenerator;
import it.unimib.disco.lta.alfa.parametersAnalysis.ParametersAnalyzer;
import it.unimib.disco.lta.alfa.parametersAnalysis.ParametersClustersGenerator;
import it.unimib.disco.lta.alfa.parametersAnalysis.RTableExporter;
import it.unimib.disco.lta.alfa.parametersAnalysis.RuleParameter;
import it.unimib.disco.lta.alfa.parametersAnalysis.TraceData;
import it.unimib.disco.lta.alfa.testUtils.ArtifactsProvider;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

import junit.framework.TestCase;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.Assert.*;


public class ClusterStatisticsGeneratorTest  extends TestCase{

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}
	
	/**
	 * Test calculate statistics in case there is only one cluster and values occur just one time
	 * @throws IOException
	 */
	@Test
	public void testCalculateStatistics_OneOccurrence() throws IOException{
		
		
		File file = ArtifactsProvider.getFile("unit/main/artifacts/oneComponent.csv");
		ParametersAnalyzer pa = new ParametersAnalyzer();
		AnalysisResult ar = pa.analyze(file);
		ArrayList<TraceData> tracesData = ar.getTracesDatas();
		RTableExporter rte = pa.getExporter();

		ParametersClustersGenerator pcg = new ParametersClustersGenerator(tracesData, 0.75, 1);
		Set<Cluster<RuleParameter>> clusters = pcg.getAllClusters();
		
		ArrayList<Cluster<RuleParameter>> list = new ArrayList<Cluster<RuleParameter>>();
		
		ClusterStatisticsGenerator csg = new ClusterStatisticsGenerator();
		
		ArrayList<SlctPattern> slctPatterns = new ArrayList<SlctPattern>();
		slctPatterns.add(new SlctPattern("R1",Pattern.compile("a1 (.*) (.*)")) );
		slctPatterns.add(new SlctPattern("R2",Pattern.compile("a2 (.*) (.*)")) );
		
		List<ClusterStatistics> clustersStats = csg.calculateClustersStatistics(clusters, file, rte, tracesData, slctPatterns);
		
		assertEquals(1,clustersStats.size());
		
		
		
		
	}
	
	public void testTwoTracesEquals() throws IOException{
		File file = ArtifactsProvider.getFile("unit/main/artifacts/twoComponentsManyClustersTraces.csv");
		
		assertTrue(file.exists());
		
		ParametersAnalyzer pa = new ParametersAnalyzer();
		pa.setSignatureElements(new int[]{0,1});
		
		AnalysisResult ar = pa.analyze(file);
		ArrayList<TraceData> tracesData = ar.getTracesDatas();
		
		ParametersClustersGenerator pcg = new ParametersClustersGenerator(tracesData, 0.75, 1);
		Set<Cluster<RuleParameter>> clusters = pcg.getClusters();
		
		ArrayList<SlctPattern> slctPatterns = new ArrayList<SlctPattern>();
		slctPatterns.add(new SlctPattern("C1_R1",Pattern.compile("a1 (.*) (.*)")) );
		slctPatterns.add(new SlctPattern("C2_R2",Pattern.compile("a2 (.*) (.*)")) );
		
		
		ClusterStatisticsGenerator csg = new ClusterStatisticsGenerator();
		List<ClusterStatistics> stats = csg.calculateClustersStatistics(clusters, file, pa.getExporter(), tracesData, slctPatterns);
		
		assertEquals(2 , stats.size() );
		
		ClusterStatistics stat = stats.get(0);
		checkStat( stat,0 );
	}

	private void checkStat(ClusterStatistics stat, 
			int distinctValues
			) {
//		assertEquals( distinctValues, stat.getDistinctValues() );
//		assertEquals( distanceMeanMean , stat.getDistinctValuesDistanceMeanMean() );
//		distanceMeanStdDev , stat.getDistinctValuesDistanceMeanStdDev();
//		distanceMedianMean,  stat.getDistinctValuesDistanceMedianMean();
//		distanceMedianStdDev, stat.getDistinctValuesDistancesMedianStdDev();
//		distanceStdDevMean, stat.getDistinctValuesDistanceStdDevMean();
//		distanceStdDevStdDev, stat.getDistinctValuesDistanceStdDevStdDev();
//		distinctValuesMean, stat.getDistinctValuesMean();
//		distinctValuesOccurrenciesMean , stat.getDistinctValuesOccurrenciesMean();
//		distinctValuesOccurrenciesStdDev, stat.getDistinctValuesOccurrenciesStdDev();
//		stat.getDistinctValuesStdDev();
	}
}
