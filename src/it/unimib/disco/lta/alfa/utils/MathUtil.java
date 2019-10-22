package it.unimib.disco.lta.alfa.utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class MathUtil {

	public static double getMean( double[] values ){
		double total = 0;
		for ( double value : values ){
			total += value;
		}
		return total/values.length;
	}

	public static double getMean( int[] values ){
		double total = 0;
		for ( int value : values ){
			total += value;
		}
		return total/values.length;
	}
	
	public static double getVariance( double[] values ){
		double mean = getMean(values);
		double total = 0;
		for ( double value : values ){
			double diff=value-mean;
			total += diff*diff; 
		}
		return total/values.length;
	}

	public static double getVariance( int[] values ){
		double mean = getMean(values);
		double total = 0;
		for ( int value : values ){
			double diff=value-mean;
			total += diff*diff; 
		}
		return total/values.length;
	}
	
	public static double getStdDev( double[] values ){
		return Math.sqrt(getVariance(values));
	}
	
	public static double getStdDev( int[] values ){
		return Math.sqrt(getVariance(values));
	}
	
	public static double getMedian( double[] values ){
		List<Double> list = new LinkedList();
		
		
		double total = 0;
		for ( double value : values ){
			list.add(value);
		}
		
		return getMedian(list);
	}

	public static double getMedian( int[] values ){
		List<Double> list = new LinkedList();
		
		
		double total = 0;
		for ( double value : values ){
			list.add(value);
		}
		
		return getMedian(list);
	}
	
	public static double getMedian(List<Double> list){
		return getPercentile(list, 50);
	}
	
	public static double getPercentile(List<Double> list, int p){
		Collections.sort(list);
		
		double result;
		int elements = list.size();
		double percentileFactor = (double)p/100;
		
		double ppos = ( ( elements -1 ) * percentileFactor );
		int position = (int) ppos;
		
		if ( list.size() == 0 ){
			return 0;
		}
		
		if (position!=ppos){
			result = (double)(list.get(position)+list.get(position+1))/2;
		} else {
			result = list.get(position);
		}
		return result;
		
		//return list.get(medianpos);
		
		
	}

	public static double getPercentile(int[] values, int p) {
		List<Double> list = new ArrayList<Double>();
		
		
		double total = 0;
		for ( double value : values ){
			list.add(value);
		}
		
		return getPercentile(list,p);
	}
	
	public static double getPercentile(double[] values, int p) {
		List<Double> list = new ArrayList<Double>();
		
		
		double total = 0;
		for ( double value : values ){
			list.add(value);
		}
		
		return getPercentile(list,p);
	}

//	public static double getMean(
//			Collection<Integer> values) {
//		int total = 0;
//		for ( Integer n : values ){
//			total += n;
//		}
//		return (double)total/(double)values.size();
//	}

	public static double getMean(Collection<Long> values) {
		long total = 0;
		for ( Long n : values ){
			total += n;
		}
		return (double)total/(double)values.size();
	}

	
}
