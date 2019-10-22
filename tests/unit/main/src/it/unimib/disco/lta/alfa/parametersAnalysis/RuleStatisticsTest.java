package it.unimib.disco.lta.alfa.parametersAnalysis;

import static org.junit.Assert.*;
import junit.framework.TestCase;
import it.unimib.disco.lta.alfa.parametersAnalysis.RuleStatisticExceptionNoParameters;
import it.unimib.disco.lta.alfa.parametersAnalysis.RuleStatistics;
import it.unimib.disco.lta.alfa.parametersAnalysis.RuleStatisticsException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;



public class RuleStatisticsTest extends TestCase  {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}



	/**
	 * Test a RuleStatistics with two parameters with values occurring just one time
	 * @throws RuleStatisticsException
	 */
	@Test
	public void testManyParameters() throws RuleStatisticsException{
		RuleStatistics rs = RuleStatisticsStubsCreator.getRS1();
		
		evaluateRS1(rs, true);
	}
	
	/**
	 * Two parameters with different occurrencies
	 * 
	 * @throws RuleStatisticsException
	 */
	@Test
	public void testManyParametersDistinct() throws RuleStatisticsException{
		RuleStatistics rs = RuleStatisticsStubsCreator.getRS7();
		
		evaluateRS7(rs, true);
		
		rs = RuleStatisticsStubsCreator.getRS8();
		evaluateRS8(rs, true);
		
	}
	
	/**
	 * Tests the case in which one parameter sometimes is missing
	 * @throws RuleStatisticsException
	 */
	@Test
	public void testManyParametersMissing() throws RuleStatisticsException{
		RuleStatistics rs = RuleStatisticsStubsCreator.getRS9();
		
		evaluateRS9(rs, true);
		
		
		
	}



	@Test
	public void testManyParametersNoCount() throws RuleStatisticsException{
		RuleStatistics rs = RuleStatisticsStubsCreator.getRS1_NoCount("C");
		
		evaluateRS1(rs, false);
	}
	
	public static void evaluateRS1( RuleStatistics rs, boolean checkDistances ) throws RuleStatisticExceptionNoParameters{
		
		if ( checkDistances ){
			assertEquals("", 5, rs.getTotalEvents());

			int[] expectedDistances = new int[]{2,2,2,2};
			assertArrayEquals(expectedDistances, rs.getEventDistances());

			assertEquals("Event distances mean", 2.0, rs.getEventDistancesMean() );

			assertEquals("Event distances median", 2.0, rs.getEventDistancesMedian() );

			assertEquals("Event distances stdev", 0.0, rs.getEventDistancesStdDev() );


			assertDoubleArrayEquals("Identical values distance mean", new double[]{0.0,0.0}, rs.getParametersDistanceAvg() );

			assertDoubleArrayEquals("Identical values distance median", new double[]{0.0,0.0}, rs.getParametersDistanceMedian() );

			assertDoubleArrayEquals("Identical values distance std dev", new double[]{0.0,0.0}, rs.getParametersDistanceStdDev() );

		}
		
		assertEquals(2, rs.getParametersNumber());
		
		
		assertDoubleArrayEquals("Distinct values occurrencies mean",new double[]{1.0,1.0}, rs.getParametersOccurrenciesMean());
		
		
		assertArrayEquals( new int[]{5,5}, rs.getParametersTotalOccurrencies());
		
	}


	public static void evaluateRS2( RuleStatistics rs, boolean checkDistances ) throws RuleStatisticExceptionNoParameters{
		evaluateRS1(rs, checkDistances);
	}
	
	/**
	 * Test A RS with values occurring 3 times in the same way
	 * @throws RuleStatisticsException
	 */
	@Test
	public void testManyParameters2() throws RuleStatisticsException{
		RuleStatistics rs = RuleStatisticsStubsCreator.getRS3();
		
		assertEquals("", 15, rs.getTotalEvents());
		
		int[] expectedDistances = new int[]{2,2,2,2,2,2,2,2,2,2,2,2,2,2};
		org.junit.Assert.assertArrayEquals("Event distances",expectedDistances, rs.getEventDistances());
		
		assertEquals("Event distances mean", 2.0, rs.getEventDistancesMean() );
		
		assertEquals("Event distances median", 2.0, rs.getEventDistancesMedian() );
		
		assertEquals("Event distances stdev", 0.0, rs.getEventDistancesStdDev() );
		
		
		assertDoubleArrayEquals("Identical values distance mean", new double[]{10.0,10.0}, rs.getParametersDistanceAvg() );
		
		assertDoubleArrayEquals("Identical values distance median", new double[]{10.0,10.0}, rs.getParametersDistanceMedian() );
		
		assertDoubleArrayEquals("Identical values distance std dev", new double[]{0.0,0.0}, rs.getParametersDistanceStdDev() );
		
		assertEquals(2, rs.getParametersNumber());
		
		double expectedOccurrencies[] = new double[]{3.0,3.0};
		assertDoubleArrayEquals("Distinct values occurrencies mean",expectedOccurrencies, rs.getParametersOccurrenciesMean());
		
		int expectedTotalOccurrencies[] = new int[]{15,15};
		assertArrayEquals( expectedTotalOccurrencies, rs.getParametersTotalOccurrencies());
		
	}

	/**
	 * Test parameters with values that occurr in different ways
	 * 
	 * @throws RuleStatisticsException
	 */
	@Test
	public void testManyParameters3() throws RuleStatisticsException{
		RuleStatistics rs = RuleStatisticsStubsCreator.getRS4();
		
		evaluateRS4(rs, true);
		
		
	}
	
	private static void assertDoubleArrayEquals(String string, double[] expected,
			double[] actual) {
		Double[] expectedObj = getObjectArray(expected);
		
		Double[] actualObj = getObjectArray(actual);
		
		assertArrayEquals(expectedObj,actualObj);
		
	}

	private static void assertArrayEquals(Object[] expectedObj, Object[] actualObj) {
		if ( expectedObj.length != actualObj.length ){
			fail("Expected "+expectedObj.length+" found "+actualObj.length);
		}
		
		for ( int i = 0 ; i < expectedObj.length; ++i ){
			if ( ! expectedObj[i].equals(actualObj[i]) ){
				fail("Expected "+expectedObj[i]+" found "+actualObj[i]);
			}
		}
	}
	
	
	private static void assertArrayEquals(int[] expectedObj, int[] actualObj) {
		if ( expectedObj.length != actualObj.length ){
			fail("Expected "+expectedObj.length+" found "+actualObj.length);
		}
		
		for ( int i = 0 ; i < expectedObj.length; ++i ){
			if ( expectedObj[i] != actualObj[i] ){
				fail("Expected "+expectedObj[i]+" found "+actualObj[i]);
			}
		}
	}

	private static Double[] getObjectArray(double[] primitiveArray) {
		Double[] objectArray = new Double[primitiveArray.length];
		for( int i = 0; i < primitiveArray.length; ++i ){
			objectArray[i] = Double.valueOf(primitiveArray[i]);
		}
		return objectArray;
	}

	public static void evaluateRS4(RuleStatistics rs, boolean checkDistances) throws RuleStatisticExceptionNoParameters {
		
		if ( checkDistances ){
			assertEquals("", 12, rs.getTotalEvents());

			int[] expectedDistances = new int[]{1,1,1, 2,1,1,1, 2,1,1,1};
			org.junit.Assert.assertArrayEquals("Event distances",expectedDistances, rs.getEventDistances());

			assertEquals("Event distances mean", 1.5, rs.getEventDistancesMean() );

			assertEquals("Event distances median", 1.0, rs.getEventDistancesMedian() );

			assertEquals("Event distances stdev", 0.0, rs.getEventDistancesStdDev() );


			assertDoubleArrayEquals("Identical values distance mean", new double[]{2.2,2.2}, rs.getParametersDistanceAvg() );

			assertDoubleArrayEquals("Identical values distance median", new double[]{1.0,1.0}, rs.getParametersDistanceMedian() );

			assertDoubleArrayEquals("Identical values distance std dev", new double[]{1.6,1.6}, rs.getParametersDistanceStdDev() );

		}
		
		assertEquals(2, rs.getParametersNumber());
		
		double expectedOccurrencies[] = new double[]{6.0,6.0};
		assertDoubleArrayEquals("Distinct values occurrencies mean",expectedOccurrencies, rs.getParametersOccurrenciesMean());
		
		int expectedTotalOccurrencies[] = new int[]{12,12};
		assertArrayEquals( expectedTotalOccurrencies, rs.getParametersTotalOccurrencies());
		
		
	}

	public static void evaluateRS7(RuleStatistics rs, boolean checkDistances) throws RuleStatisticExceptionNoParameters {
		if ( checkDistances ){
			assertEquals("", 2, rs.getTotalEvents());

			
			org.junit.Assert.assertArrayEquals("Event distances", new int[]{5}, rs.getEventDistances());

			assertEquals("Event distances mean", 5.0, rs.getEventDistancesMean() );

			assertEquals("Event distances median", 5.0, rs.getEventDistancesMedian() );

			assertEquals("Event distances stdev", 0.0, rs.getEventDistancesStdDev() );


			assertDoubleArrayEquals("Identical values distance mean", new double[]{5.0,0.0}, rs.getParametersDistanceAvg() );

			assertDoubleArrayEquals("Identical values distance median", new double[]{5.0,0.0}, rs.getParametersDistanceMedian() );

			assertDoubleArrayEquals("Identical values distance std dev", new double[]{0.0,0.0}, rs.getParametersDistanceStdDev() );

		}
		
		assertEquals(2, rs.getParametersNumber());
		
		
		assertDoubleArrayEquals("Distinct values occurrencies mean",new double[]{2.0,1.0}, rs.getParametersOccurrenciesMean());
		
		int expectedTotalOccurrencies[] = new int[]{2,2};
		assertArrayEquals( expectedTotalOccurrencies, rs.getParametersTotalOccurrencies());
		
		
		
	}

	public static void evaluateRS8(RuleStatistics rs, boolean checkDistances) throws RuleStatisticExceptionNoParameters {
		if ( checkDistances ){
			assertEquals("", 2, rs.getTotalEvents());

			
			assertArrayEquals( new int[]{1}, rs.getEventDistances());

			assertEquals("Event distances mean", 1.0, rs.getEventDistancesMean() );

			assertEquals("Event distances median", 1.0, rs.getEventDistancesMedian() );

			assertEquals("Event distances stdev", 0.0, rs.getEventDistancesStdDev() );


			assertDoubleArrayEquals("Identical values distance mean", new double[]{1.0,0.0}, rs.getParametersDistanceAvg() );

			assertDoubleArrayEquals("Identical values distance median", new double[]{1.0,0.0}, rs.getParametersDistanceMedian() );

			assertDoubleArrayEquals("Identical values distance std dev", new double[]{0.0,0.0}, rs.getParametersDistanceStdDev() );

		}
		
		assertEquals(2, rs.getParametersNumber());
		
		
		assertDoubleArrayEquals("Distinct values occurrencies mean",new double[]{2.0,1.0}, rs.getParametersOccurrenciesMean());
		
		int expectedTotalOccurrencies[] = new int[]{2,2};
		assertArrayEquals( expectedTotalOccurrencies, rs.getParametersTotalOccurrencies());
		
	}
	

	public static void evaluateRS9(RuleStatistics rs, boolean checkDistances) throws RuleStatisticExceptionNoParameters {
		if ( checkDistances ){
			assertEquals("", 12, rs.getTotalEvents());

			int[] expectedDistances = new int[]{1,1,1, 2,1,1,1, 2,1,1,1};
			org.junit.Assert.assertArrayEquals("Event distances",expectedDistances, rs.getEventDistances());

			assertEquals("Event distances mean", 1.5, rs.getEventDistancesMean() );

			assertEquals("Event distances median", 1.0, rs.getEventDistancesMedian() );

			assertEquals("Event distances stdev", 0.0, rs.getEventDistancesStdDev() );


			assertDoubleArrayEquals("Identical values distance mean", new double[]{2.2,7.0}, rs.getParametersDistanceAvg() );

			assertDoubleArrayEquals("Identical values distance median", new double[]{1.0,6.0}, rs.getParametersDistanceMedian() );

			assertDoubleArrayEquals("Identical values distance std dev", new double[]{1.6,Math.sqrt(14.0/3.0)}, rs.getParametersDistanceStdDev() );

		}
		
		assertEquals(2, rs.getParametersNumber());
		
		
		assertDoubleArrayEquals("Distinct values occurrencies mean",new double[]{6.0,2.0}, rs.getParametersOccurrenciesMean());
		
		int expectedTotalOccurrencies[] = new int[]{12,5};
		assertArrayEquals( expectedTotalOccurrencies, rs.getParametersTotalOccurrencies());
	}

}
