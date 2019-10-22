package it.unimib.disco.lta.alfa.parametersAnalysis;


import static org.junit.Assert.*;

import it.unimib.disco.lta.alfa.parametersAnalysis.AnalysisResult;
import it.unimib.disco.lta.alfa.parametersAnalysis.Cluster;
import it.unimib.disco.lta.alfa.parametersAnalysis.ParametersAnalyzer;
import it.unimib.disco.lta.alfa.parametersAnalysis.ParametersClustersGenerator;
import it.unimib.disco.lta.alfa.parametersAnalysis.RuleParameter;
import it.unimib.disco.lta.alfa.parametersAnalysis.TraceData;
import it.unimib.disco.lta.alfa.testUtils.ArtifactsProvider;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import junit.framework.TestCase;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;


public class ParametersClustersGeneratorTest  extends TestCase{

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}
	
	
	/**
	 * Test the case in which we have many (4) single clusters and one cluster (of 2 elements)
	 * @throws IOException
	 */
	@Test
	public void testManyClusters() throws IOException{
		File file = ArtifactsProvider.getFile("unit/main/artifacts/manyComponents.csv");
		
		assertTrue(file.exists());
		
		ParametersAnalyzer pa = new ParametersAnalyzer();
		pa.setSignatureElements(new int[]{0,1});
		
		AnalysisResult ar = pa.analyze(file);
		ArrayList<TraceData> tracesData = ar.getTracesDatas();
		
		ParametersClustersGenerator pcg = new ParametersClustersGenerator(tracesData, 0.75, 1);
		Set<Cluster<RuleParameter>> clusters = pcg.getClusters();
		
		assertEquals(1, clusters.size());
		
		List<String> clustersSignatures = new ArrayList<String>();
		for ( Cluster<RuleParameter> c : clusters ){
			clustersSignatures.add(c.getSignature());
		}
		
		assertEquals(1, clusters.size());
		assertTrue(clustersSignatures.contains("C2_R7_1-C3_R8_1"));
		
		
		clusters = pcg.getSingleClusters();
		
		clustersSignatures = new ArrayList<String>();
		for ( Cluster<RuleParameter> c : clusters ){
			clustersSignatures.add(c.getSignature());
		}
		
		assertEquals(4, clusters.size());
		
		assertTrue(clustersSignatures.contains("C1_R4_0"));
		assertTrue(clustersSignatures.contains("C1_R4_1"));
		assertTrue(clustersSignatures.contains("C2_R7_0"));
		assertTrue(clustersSignatures.contains("C3_R8_0"));
		
		
		clusters = pcg.getAllClusters();
		
		clustersSignatures = new ArrayList<String>();
		for ( Cluster<RuleParameter> c : clusters ){
			clustersSignatures.add(c.getSignature());
		}
		
		assertEquals(5, clusters.size());
		
		assertTrue(clustersSignatures.contains("C1_R4_0"));
		assertTrue(clustersSignatures.contains("C1_R4_1"));
		assertTrue(clustersSignatures.contains("C2_R7_0"));
		assertTrue(clustersSignatures.contains("C3_R8_0"));
		
		assertTrue(clustersSignatures.contains("C2_R7_1-C3_R8_1"));
		
	}
	
	@Test
	public void testOneBigCluster() throws IOException{
		File file = ArtifactsProvider.getFile("unit/main/artifacts/twoComponentsManyClusters.csv");
		
		assertTrue(file.exists());
		
		ParametersAnalyzer pa = new ParametersAnalyzer();
		pa.setSignatureElements(new int[]{0,1});
		
		AnalysisResult ar = pa.analyze(file);
		ArrayList<TraceData> tracesData = ar.getTracesDatas();
		
		ParametersClustersGenerator pcg = new ParametersClustersGenerator(tracesData, 0.5, 1);
		Set<Cluster<RuleParameter>> clusters = pcg.getClusters();
		
		assertEquals(1, clusters.size());
		
		List<String> clustersSignatures = new ArrayList<String>();
		for ( Cluster<RuleParameter> c : clusters ){
			clustersSignatures.add(c.getSignature());
		}
		
		assertTrue(clustersSignatures.contains("C1_R1_0-C1_R1_1-C2_R2_0-C2_R2_1"));
		
		Set<Cluster<RuleParameter>> allClusters = pcg.getAllClusters();
		
		assertEquals(clusters, allClusters);
		
		Set<Cluster<RuleParameter>> singleClusters = pcg.getSingleClusters();
		
		assertEquals(0,singleClusters.size());
	}

	/**
	 * Test one cluster one single
	 * @throws IOException
	 */
	@Test
	public void testClusterAndSingle() throws IOException{
		File file = ArtifactsProvider.getFile("unit/main/artifacts/twoComponentsManyClusters.csv");
		
		assertTrue(file.exists());
		
		ParametersAnalyzer pa = new ParametersAnalyzer();
		pa.setSignatureElements(new int[]{0,1});
		
		AnalysisResult ar = pa.analyze(file);
		ArrayList<TraceData> tracesData = ar.getTracesDatas();
		
		ParametersClustersGenerator pcg = new ParametersClustersGenerator(tracesData, 0.75, 1);
		Set<Cluster<RuleParameter>> clusters = pcg.getClusters();
		
		assertEquals(1, clusters.size());
		
		List<String> clustersSignatures = new ArrayList<String>();
		for ( Cluster<RuleParameter> c : clusters ){
			clustersSignatures.add(c.getSignature());
		}
		
		assertTrue(clustersSignatures.contains("C1_R1_0-C1_R1_1-C2_R2_0"));
		
		
		
		Set<Cluster<RuleParameter>> singleClusters = pcg.getSingleClusters();
		
		assertEquals(1,singleClusters.size());
		
		clustersSignatures = new ArrayList<String>();
		for ( Cluster<RuleParameter> c : singleClusters ){
			clustersSignatures.add(c.getSignature());
		}
		
		assertTrue(clustersSignatures.contains("C2_R2_1"));
		
		
		HashSet<Cluster<RuleParameter>> expectedAll = new HashSet<Cluster<RuleParameter>>();
		expectedAll.addAll(clusters);
		expectedAll.addAll(singleClusters);
		
		Set<Cluster<RuleParameter>> allClusters = pcg.getAllClusters();
		
		assertEquals(expectedAll, allClusters);
		
	}
	
	/**
	 * Test one cluster one single
	 * @throws IOException
	 */
	@Test
	public void testMultipleTracesClusterAndSingle() throws IOException{
		File file = ArtifactsProvider.getFile("unit/main/artifacts/twoComponentsManyClustersTraces.csv");
		
		assertTrue(file.exists());
		
		ParametersAnalyzer pa = new ParametersAnalyzer();
		pa.setSignatureElements(new int[]{0,1});
		
		AnalysisResult ar = pa.analyze(file);
		ArrayList<TraceData> tracesData = ar.getTracesDatas();
		
		ParametersClustersGenerator pcg = new ParametersClustersGenerator(tracesData, 0.75, 1);
		Set<Cluster<RuleParameter>> clusters = pcg.getClusters();
		
		assertEquals(1, clusters.size());
		
		List<String> clustersSignatures = new ArrayList<String>();
		for ( Cluster<RuleParameter> c : clusters ){
			clustersSignatures.add(c.getSignature());
		}
		
		assertTrue(clustersSignatures.contains("C1_R1_0-C1_R1_1-C2_R2_0"));
		
		
		
		Set<Cluster<RuleParameter>> singleClusters = pcg.getSingleClusters();
		
		assertEquals(1,singleClusters.size());
		
		clustersSignatures = new ArrayList<String>();
		for ( Cluster<RuleParameter> c : singleClusters ){
			clustersSignatures.add(c.getSignature());
		}
		
		assertTrue(clustersSignatures.contains("C2_R2_1"));
		
		
		HashSet<Cluster<RuleParameter>> expectedAll = new HashSet<Cluster<RuleParameter>>();
		expectedAll.addAll(clusters);
		expectedAll.addAll(singleClusters);
		
		Set<Cluster<RuleParameter>> allClusters = pcg.getAllClusters();
		
		assertEquals(expectedAll, allClusters);
		
	}
	
	/**
	 * Test the case in which we have two clusters
	 * @throws IOException
	 */
	@Test
	public void testTwoClusters() throws IOException{
		File file = ArtifactsProvider.getFile("unit/main/artifacts/oneComponent.csv");
		
		assertTrue(file.exists());
		
		ParametersAnalyzer pa = new ParametersAnalyzer();
		pa.setSignatureElements(new int[]{0,1});
		
		AnalysisResult ar = pa.analyze(file);
		ArrayList<TraceData> tracesData = ar.getTracesDatas();
		
		ParametersClustersGenerator pcg = new ParametersClustersGenerator(tracesData, 0.75, 1);
		Set<Cluster<RuleParameter>> clusters = pcg.getClusters();
		
		assertEquals(2, clusters.size());
		
		List<String> clustersSignatures = new ArrayList<String>();
		for ( Cluster<RuleParameter> c : clusters ){
			clustersSignatures.add(c.getSignature());
		}
		
		assertTrue(clustersSignatures.contains("C_R1_0-C_R2_1"));
		assertTrue(clustersSignatures.contains("C_R1_1-C_R2_0"));
		
		
		Set<Cluster<RuleParameter>> allClusters = pcg.getAllClusters();
		
		assertEquals(clusters, allClusters);
		
		Set<Cluster<RuleParameter>> singleClusters = pcg.getSingleClusters();
		
		assertEquals(0,singleClusters.size());
		
		
	}
}
