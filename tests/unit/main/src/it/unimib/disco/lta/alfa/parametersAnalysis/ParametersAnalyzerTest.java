package it.unimib.disco.lta.alfa.parametersAnalysis;

import static org.junit.Assert.*;

import it.unimib.disco.lta.alfa.parametersAnalysis.AnalysisResult;
import it.unimib.disco.lta.alfa.parametersAnalysis.ParametersAnalyzer;
import it.unimib.disco.lta.alfa.parametersAnalysis.RuleStatisticExceptionNoParameters;
import it.unimib.disco.lta.alfa.parametersAnalysis.RuleStatistics;
import it.unimib.disco.lta.alfa.parametersAnalysis.RulesStatistics;
import it.unimib.disco.lta.alfa.parametersAnalysis.TraceData;
import it.unimib.disco.lta.alfa.testUtils.ArtifactsProvider;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import junit.framework.TestCase;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;


public class ParametersAnalyzerTest extends TestCase {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	/**
	 * Test the case in which we have two parameters, 2 actions and 1 component.
	 * Both the two parameters actions match.
	 * 
	 * @throws IOException
	 * @throws RuleStatisticExceptionNoParameters
	 */
	@Test
	public void testAnalyze1() throws IOException, RuleStatisticExceptionNoParameters {
		File file = ArtifactsProvider.getFile("unit/main/artifacts/oneComponent.csv");
		
		assertTrue(file.exists());
		
		ParametersAnalyzer pa = new ParametersAnalyzer();
		pa.setSignatureElements(new int[]{0,1});
		
		AnalysisResult ar = pa.analyze(file);
		ArrayList<TraceData> tracesData = ar.getTracesDatas();
		
		assertEquals(1,tracesData.size());
		
		TraceData traceData = tracesData.get(0);
		
		RulesStatistics rss = traceData.getRulesStatistics();
		assertEquals(2,rss.getStatistics().size());
		
		RuleStatistics rs = rss.get("C_R1");
		
		assertEquals("C_R1", rs.getRuleName() );
		
		RuleStatisticsTest.evaluateRS1(rs, false);
		
		rs = rss.get("C_R2");
		
		assertEquals("C_R2", rs.getRuleName() );
		
		RuleStatisticsTest.evaluateRS2(rs, false);
		
	}

	/**
	 * Many rules many components
	 * @throws IOException
	 * @throws RuleStatisticExceptionNoParameters
	 */
	@Test
	public void testAnalyze2() throws IOException, RuleStatisticExceptionNoParameters {
		File file = ArtifactsProvider.getFile("unit/main/artifacts/manyComponents.csv");
		
		assertTrue(file.exists());
		
		ParametersAnalyzer pa = new ParametersAnalyzer();
		pa.setSignatureElements(new int[]{0,1});
		
		AnalysisResult ar = pa.analyze(file);
		ArrayList<TraceData> tracesData = ar.getTracesDatas();
		
		assertEquals(1,tracesData.size());
		
		TraceData traceData = tracesData.get(0);
		
		RulesStatistics rss = traceData.getRulesStatistics();
		
		assertEquals(3,rss.getStatistics().size());
		
		RuleStatistics rs = rss.get("C1_R4");
		
		assertEquals("C1_R4", rs.getRuleName() );
		
		RuleStatisticsTest.evaluateRS4(rs,false);
		
		rs = rss.get("C2_R7");
		
		assertEquals("C2_R7", rs.getRuleName() );
		
		RuleStatisticsTest.evaluateRS7(rs,false);
		
		rs = rss.get("C3_R8");
		
		assertEquals("C3_R8", rs.getRuleName() );
		
		RuleStatisticsTest.evaluateRS8(rs,false);
	}
	
	@Test
	public void testAnalyze3() throws IOException, RuleStatisticExceptionNoParameters {
		File file = ArtifactsProvider.getFile("unit/main/artifacts/manyComponentsParametersMissing.csv");
		
		assertTrue(file.exists());
		
		ParametersAnalyzer pa = new ParametersAnalyzer();
		pa.setSignatureElements(new int[]{0,1});
		
		AnalysisResult ar = pa.analyze(file);
		ArrayList<TraceData> tracesData = ar.getTracesDatas();
		
		assertEquals(1,tracesData.size());
		
		TraceData traceData = tracesData.get(0);
		
		RulesStatistics rss = traceData.getRulesStatistics();
		
		assertEquals(3,rss.getStatistics().size());
		
		RuleStatistics rs = rss.get("C1_R9");
		
		assertEquals("C1_R9", rs.getRuleName() );
		
		RuleStatisticsTest.evaluateRS9(rs,false);
		
		rs = rss.get("C2_R7");
		
		assertEquals("C2_R7", rs.getRuleName() );
		
		RuleStatisticsTest.evaluateRS7(rs,false);
		
		rs = rss.get("C3_R8");
		
		assertEquals("C3_R8", rs.getRuleName() );
		
		RuleStatisticsTest.evaluateRS8(rs,false);
	}
	
}
