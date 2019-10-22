package it.unimib.disco.lta.alfa.utils;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class MathUtilTest {

	@Test
	public void testGetMedian() {
		
		
		assertEquals(0, MathUtil.getMedian(new int[0]) );
		
		int[] values = {1,2,3};
		assertEquals(2, MathUtil.getMedian(values) );
		
		values = new int[]{1,2,2,3};
		assertEquals(2, MathUtil.getMedian(values) );
		
		values = new int[]{1,2,3,3};
		assertEquals(2.5, MathUtil.getMedian(values) );
		

		values = new int[]{7};
		assertEquals(7, MathUtil.getMedian(values) );
		
		values = new int[]{7,9};
		assertEquals(8, MathUtil.getMedian(values) );
		
		//check if works with values not sorted
		values = new int[]{9,5,2,4,5};
		assertEquals(5, MathUtil.getMedian(values) );
		
		//double
		double[] dvalues = new double[]{1.4,2.3,2.6,3.2};
		assertEquals((2.3+2.6)/2, MathUtil.getMedian(dvalues) );
		
		
	}



}
