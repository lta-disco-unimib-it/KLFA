package it.unimib.disco.lta.alfa.parametersAnalysis;


import it.unimib.disco.lta.alfa.parametersAnalysis.ParametersIntersection;
import it.unimib.disco.lta.alfa.parametersAnalysis.RuleParameter;
import it.unimib.disco.lta.alfa.parametersAnalysis.RuleStatisticExceptionNoParameters;
import it.unimib.disco.lta.alfa.parametersAnalysis.RuleStatistics;
import it.unimib.disco.lta.alfa.parametersAnalysis.RuleStatisticsUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import junit.framework.TestCase;

import org.junit.After;
import org.junit.Before;

public class RuleStatisticsUtilsTest extends TestCase {

	
	
	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	
	public void testIntersections() throws RuleStatisticExceptionNoParameters{
		RuleStatistics left = RuleStatisticsStubsCreator.getRS1();
		RuleStatistics right = RuleStatisticsStubsCreator.getRS2();
		Set<String> intersections = RuleStatisticsUtils.getIntersections(left, 0, right, 0);
	
		assertEquals("Different rule, no intersections",0,intersections.size());
		
		intersections = RuleStatisticsUtils.getIntersections(left, 0, right, 1);
		assertEquals("Different rule, intersections",left.getParametersTotalOccurrencies()[0],intersections.size());
		
		intersections = RuleStatisticsUtils.getIntersections(left, 1, right, 0);
		assertEquals("Different rule, intersections",left.getParametersTotalOccurrencies()[0],intersections.size());
	}
	
	public void testIntersectionsCollection_Many(){
		List<RuleStatistics> stats = new ArrayList<RuleStatistics>();
		RuleStatistics rs1 = RuleStatisticsStubsCreator.getRS1();
		RuleStatistics rs2 = RuleStatisticsStubsCreator.getRS2();
		
		stats.add(rs1);
		stats.add(rs2);
		
		ArrayList<ParametersIntersection> intersections = RuleStatisticsUtils.getIntersections(stats, 0.75, 1);

		assertEquals(2, intersections.size());
		
		ParametersIntersection pis = intersections.get(0);
		RuleParameter rp1 = pis.getRuleParameter1();
		assertEquals(rs1.getRuleName(), rp1.getRuleName() );
		assertEquals(0, rp1.getParameterPos() );
		RuleParameter rp2 = pis.getRuleParameter2();
		assertEquals(rs2.getRuleName(), rp2.getRuleName() );
		assertEquals(1, rp2.getParameterPos() );
		
		
		pis = intersections.get(1);
		rp1 = pis.getRuleParameter1();
		assertEquals(rs1.getRuleName(), rp1.getRuleName() );
		assertEquals(1, rp1.getParameterPos() );
		rp2 = pis.getRuleParameter2();
		assertEquals(rs2.getRuleName(), rp2.getRuleName() );
		assertEquals(0, rp2.getParameterPos() );
	}
	
	public void testIntersectionsCollection_None(){
		List<RuleStatistics> stats = new ArrayList<RuleStatistics>();
		RuleStatistics rs1 = RuleStatisticsStubsCreator.getRS1();
		RuleStatistics rs6 = RuleStatisticsStubsCreator.getRS6();
		
		stats.add(rs1);
		stats.add(rs6);
		
		ArrayList<ParametersIntersection> intersections = RuleStatisticsUtils.getIntersections(stats, 0.75, 1);

		assertEquals(0, intersections.size());
		
	}

	/**
	 * None because Intersection < 75%
	 */
	public void testIntersectionsCollection_None2(){
		List<RuleStatistics> stats = new ArrayList<RuleStatistics>();
		RuleStatistics rs1 = RuleStatisticsStubsCreator.getRS1();
		RuleStatistics rs5 = RuleStatisticsStubsCreator.getRS5();
		
		stats.add(rs1);
		stats.add(rs5);
		
		ArrayList<ParametersIntersection> intersections = RuleStatisticsUtils.getIntersections(stats, 0.75, 1);

		assertEquals(0, intersections.size());
		
	}
	
	/**
	 * None because Intersection < 75%
	 */
	public void testIntersectionsCollection_Many2(){
		List<RuleStatistics> stats = new ArrayList<RuleStatistics>();
		RuleStatistics rs1 = RuleStatisticsStubsCreator.getRS6();
		RuleStatistics rs2 = RuleStatisticsStubsCreator.getRS5();
		
		stats.add(rs1);
		stats.add(rs2);
		
		ArrayList<ParametersIntersection> intersections = RuleStatisticsUtils.getIntersections(stats, 0.75, 1);

		assertEquals(2, intersections.size());
		
		ParametersIntersection pis = intersections.get(0);
		RuleParameter rp1 = pis.getRuleParameter1();
		assertEquals(rs1.getRuleName(), rp1.getRuleName() );
		assertEquals(0, rp1.getParameterPos() );
		RuleParameter rp2 = pis.getRuleParameter2();
		assertEquals(rs2.getRuleName(), rp2.getRuleName() );
		assertEquals(0, rp2.getParameterPos() );
		
		
		pis = intersections.get(1);
		rp1 = pis.getRuleParameter1();
		assertEquals(rs1.getRuleName(), rp1.getRuleName() );
		assertEquals(1, rp1.getParameterPos() );
		rp2 = pis.getRuleParameter2();
		assertEquals(rs2.getRuleName(), rp2.getRuleName() );
		assertEquals(1, rp2.getParameterPos() );
		
	}
}
