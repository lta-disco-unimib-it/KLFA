/*******************************************************************************
 *    Copyright 2019 Fabrizio Pastore, Leonardo Mariani
 *   
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 *******************************************************************************/
package it.unimib.disco.lta.alfa.parametersAnalysis;

import it.unimib.disco.lta.alfa.dataTransformation.ValueTransformerGlobal;
import it.unimib.disco.lta.alfa.dataTransformation.ValueTransformerRelativeToAccess;
import it.unimib.disco.lta.alfa.dataTransformation.ValueTransformerRelativeToInstantiation;
import it.unimib.disco.lta.alfa.dataTransformation.ValueTransformerSame;
import it.unimib.disco.lta.alfa.logging.Logger;
import it.unimib.disco.lta.alfa.utils.MathUtil;
import tools.violationsAnalyzer.MultiHashMap;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.SortedMap;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.Vector;
import java.util.Map.Entry;
import java.util.regex.Pattern;



import com.sun.org.apache.xalan.internal.xsltc.compiler.util.MultiHashtable;

public class RuleStatistics {

	public static class ParameterStat{
		private HashMap<String,Integer> stats= new HashMap<String, Integer>();
		private ArrayList<Integer> ri = new ArrayList<Integer>();
		private ArrayList<Integer> ra = new ArrayList<Integer>();
		private ArrayList<Integer> go = new ArrayList<Integer>();
		private ArrayList<Integer> same = new ArrayList<Integer>();
		private ValueTransformerRelativeToAccess ravt = new ValueTransformerRelativeToAccess("");
		private ValueTransformerRelativeToInstantiation rivt = new ValueTransformerRelativeToInstantiation("");
		private ValueTransformerSame samevt = new ValueTransformerSame();
		private ValueTransformerGlobal govt = new ValueTransformerGlobal("");
		private MultiHashMap lines = new MultiHashMap();
		private boolean countLines;
		
		public ParameterStat(boolean countLines){
			this.countLines = countLines;
		}
		
		/**
		 * Add a value to the parameter.
		 * The line count is used to calculate the distance between the occurrencies of a same parameter value
		 *  
		 * @param value
		 * @param lineCount
		 */
		public void add(String value, int lineCount){
			Logger.entering(this.getClass().getCanonicalName(),"add(String,int)");
			//System.out.println(value+" "+lineCount);
			
			Integer val = stats.get(value);
			if ( val == null ){
				val = 1;
			} else {
				val += 1;
			}
			stats.put(value, val);
			if ( countLines ){
				
				lines.put(value,lineCount);
				same.add(Integer.valueOf(samevt.getTransformedValue(value).hashCode()));
				ri.add(Integer.valueOf(rivt.getTransformedValue(value)));
				ra.add(Integer.valueOf(ravt.getTransformedValue(value)));
				go.add(Integer.valueOf(govt.getTransformedValue(value)));
			}
		}
		
		/**
		 * Return the number of occurrencies of a parameter
		 * @return
		 */
		public int getTotalOccurrencies(){
			int count=0;
			for( Integer v : stats.values() ){
				count += v;
			}
			return count;
		}
		
		/**
		 * Returns the mean of the occurrencies of the different parameter values
		 * 
		 * @return
		 */
		public double getOccurrenciesMean(){
			return (double)getTotalOccurrencies()/stats.size();
		}

		/**
		 * Returns the different values that this parameter assumes
		 * @return
		 */
		public Set<String> getValues() {
			return stats.keySet();
		}
		
		/**
		 * Return the average of the distance between distinct occurrencies of parameter values
		 * 
		 * @return
		 */
		public double getDistanceMean(){
			
			
			int dists[]=getDistances();
			
			return MathUtil.getMean(dists);
		}
		
		
		private double[] getDistancesFirstMean() {
			Set pars = lines.keySet();
			
			
			double[] res = new double[lines.size()];
			
			int elementCount = -1;
			
			Iterator parsIt = pars.iterator();
			while( parsIt.hasNext()  ){
				elementCount++;
				
				String par = (String) parsIt.next();
				Vector<Integer> els = (Vector<Integer>)lines.get(par);
				
				
				
				if ( els.size() <= 1 ){
					res[elementCount]=0.0;
					continue;
				}
				
				int dists[] = new int[els.size()-1];
				int first = els.get(0);
				for ( int i = 1; i < els.size(); ++i ){
					int el = els.get(i);
					dists[i-1]=(el-first);
				}
				
				
				
				res[elementCount]=MathUtil.getMean(dists);
			}
			
			return res;
		}
		
		private double[] getDistancesFirstMedian() {
			Set pars = lines.keySet();
			
			
			double[] res = new double[lines.size()];
			
			int elementCount = -1;
			
			Iterator parsIt = pars.iterator();
			while( parsIt.hasNext()  ){
				elementCount++;
				
				String par = (String) parsIt.next();
				Vector<Integer> els = (Vector<Integer>)lines.get(par);
				
				
				
				if ( els.size() <= 1 ){
					res[elementCount]=0.0;
					continue;
				}
				
				int dists[] = new int[els.size()-1];
				int first = els.get(0);
				for ( int i = 1; i < els.size(); ++i ){
					int el = els.get(i);
					dists[i-1]=(el-first);
				}
				
				
				
				res[elementCount]=MathUtil.getMedian(dists);
			}
			
			return res;
		}
		
		/**
		 * Returns the distances between equal values, if values occurs only one time returns 0 as distance.
		 * 
		 * @return
		 */
		private int[] getDistances() {
			Set pars = lines.keySet();
			int totDist = 0;
			int distsCount=0;
			
			ArrayList<Integer> dists = new ArrayList<Integer>();
			
			Iterator parsIt = pars.iterator();
			while( parsIt.hasNext()  ){
				String par = (String) parsIt.next();
				Vector<Integer> els = (Vector<Integer>)lines.get(par);
				
				if ( els.size() <= 1 ){
					dists.add(0);
					continue;
				}
				
				int last = els.get(0);
				for ( int i = 1; i < els.size(); ++i ){
					distsCount++;
					int el = els.get(i);
					dists.add(el-last);
					last = el;
				}
				
				
			}
//			System.out.println("=========");
//			for ( Double d : dists ){
//				System.out.println(d);
//			}
			
//			if ( dists.size() == 0 ){
//				return new double[]{0.0};
//			}
			
			int a[] = new int[dists.size()];
			int i=0;
			for ( Integer val : dists ){
				a[i]=val;
				++i;
			}
			return a;
		}

		/**
		 * Return the median of the distance between distinct occurrencies of parameter values
		 * @return
		 */
		public double getDistanceMedian(){
			int dists[]=getDistances();
			return MathUtil.getMedian(dists);
		}

		/**
		 * Return the standard deviation of the distance between distinct occurrencies of parameter values
		 * @return
		 */
		public double getDistanceStdDev() {
			int dists[]=getDistances();
			return MathUtil.getStdDev(dists);
		}

		/**
		 * Return the median of the distance between distinct occurrencies of parameter values
		 * @return
		 */
		public double getDistancePercentile(int p){
			int dists[]=getDistances();
			return MathUtil.getPercentile(dists,p);
		}

		public double getDistanceFirstMean() {
			double[] distsFirst = getDistancesFirstMean();
			return MathUtil.getMean(distsFirst);
		}
		
		public double getDistanceFirstMedian() {
			double[] distsFirst = getDistancesFirstMedian();
			return MathUtil.getMean(distsFirst);
		}

		public double getRIMedian() {
//			int values[] = new int[ri.size()];
//			int i=0;
//			for ( Integer val : ri ){
//				values[i++]=val;
//			}
//			return MathUtil.getMedian(values);
			return RuleStatisticsUtils.getMinumSymbolToReachPercatage(ri, 0.5);
		}
		
		public double getRAMedian() {
//			int values[] = new int[ra.size()];
//			int i=0;
//			for ( Integer val : ra ){
//				values[i++]=val;
//			}
//			return MathUtil.getMedian(values);
			return RuleStatisticsUtils.getMinumSymbolToReachPercatage(ra, 0.5);
		}

		public double getGOMedian() {
//			int values[] = new int[go.size()];
//			int i=0;
//			for ( Integer val : go ){
//				values[i++]=val;
//			}
//			
//
//			return MathUtil.getMedian(values);
			return RuleStatisticsUtils.getMinumSymbolToReachPercatage(same, 0.5);
		}

		
		

		public double getSameMedian() {
			return RuleStatisticsUtils.getMinumSymbolToReachPercatage(same, 0.5);
		}
	}
	
	private String ruleName;
	private SortedMap<Integer,ParameterStat> parametersStats = new TreeMap<Integer, ParameterStat>();
	//contains the number of the line in which each event occur
	private ArrayList<Integer> eventOccurrencies = new ArrayList<Integer>();
	private boolean countLines;
	
	public RuleStatistics(String rule,boolean countLines) {
		this.ruleName = rule;
		this.countLines = countLines;
	}

	public void addAll(List<String> parameters, int lineCount) {
		int i = 0;
		//System.out.println(ruleName+" "+parameters.size()+" "+lineCount);
		for( String parameter : parameters ){
			ParameterStat parameterStat = getParameterStat(i);
			parameterStat.add(parameter,lineCount);
			++i;
		}
		Logger.finest(RuleStatistics.class.getCanonicalName()+" addAll, lineCount = "+lineCount);
		eventOccurrencies.add(lineCount);
	}

	/**
	 * Returns an array with the distances between each event calculated as the difference between the line in which two consequent events have been observed
	 * 
	 * @return
	 */
	public int[] getEventDistances(){
		if ( eventOccurrencies.size() == 0 ){
			return new int[0];
		}
		
		int distances[] = new int[eventOccurrencies.size()-1];
		int last = eventOccurrencies.get(0);
		
		//System.out.print("\n\n\n[Events Lines");
		for(int i = 1; i < eventOccurrencies.size(); ++i ){
			int el = eventOccurrencies.get(i);
			distances[i-1] = el - last;
			last = el;
			//System.out.print(el+",");
		}
		//System.out.print("]\n\n\n");
		
		
		return distances;
	}
	
	/**
	 * Returns the mean of the event distances
	 * 
	 * @return
	 */
	public double getEventDistancesMean(){
		int[] distances = getEventDistances();
		return MathUtil.getMean(distances);
	}

	/**
	 * Returns the mean of the event distances
	 * 
	 * @return
	 */
	public double getEventDistancesStdDev(){
		int[] distances = getEventDistances();
		return MathUtil.getStdDev(distances);
	}
	
	/**
	 * Returns the mean of the event distances
	 * 
	 * @return
	 */
	public double getEventDistancesMedian(){
		int[] distances = getEventDistances();
		return MathUtil.getMedian(distances);
	}
	/**
	 * Get the stats for the i-th parameter of the rule
	 * 
	 * @param i
	 * @return
	 */
	private ParameterStat getParameterStat(int i) {
		ParameterStat stat = parametersStats.get(i);
		if ( stat == null ){
			stat = new ParameterStat(countLines);
			parametersStats.put(i, stat);
		}
		return stat;
	}

	/**
	 * Return the number of occurrencies of the distinct parameter columns
	 * 
	 * @return
	 * @throws RuleStatisticExceptionNoParameters 
	 */
	public int[] getParametersTotalOccurrencies() throws RuleStatisticExceptionNoParameters{
		if ( parametersStats.size() == 0 ){
			throw new RuleStatisticExceptionNoParameters();
		}
		int[] res= new int[parametersStats.size()];
		
		int i = 0;
		for( ParameterStat ps : parametersStats.values() ){
			res[i++]=ps.getTotalOccurrencies();
		}
		
		return res;
	}
	
	/**
	 * For every parameter returns the mean of the occurrencies of the distinct values it assumes
	 * @return
	 * @throws RuleStatisticExceptionNoParameters 
	 */
	public double[] getParametersOccurrenciesMean() throws RuleStatisticExceptionNoParameters{
		if ( parametersStats.size() == 0 ){
			throw new RuleStatisticExceptionNoParameters();
		}
		double[] res= new double[parametersStats.size()];
		
		int i = 0;
		for( ParameterStat ps : parametersStats.values() ){
			res[i++]=ps.getOccurrenciesMean();
		}
		
		return res;
	}
	
	
	/**
	 * For every parameter returns the median of the distance between distinct occurrencies of a same value
	 * @return
	 * @throws RuleStatisticExceptionNoParameters 
	 */
	public double[] getParametersDistanceMedian() throws RuleStatisticExceptionNoParameters{
		if ( parametersStats.size() == 0 ){
			throw new RuleStatisticExceptionNoParameters();
		}
		double[] res= new double[parametersStats.size()];
		
		int i = 0;
		for( ParameterStat ps : parametersStats.values() ){
			res[i++]=ps.getDistanceMedian();
		}
		
		return res;
	}

	
	/**
	 * For every parameter returns the mean of the distance between distinct occurrencies of a same value
	 * @return
	 * @throws RuleStatisticExceptionNoParameters 
	 */
	public double[] getParametersDistanceAvg() throws RuleStatisticExceptionNoParameters{
		if ( parametersStats.size() == 0 ){
			throw new RuleStatisticExceptionNoParameters();
		}
		double[] res= new double[parametersStats.size()];
		
		int i = 0;
		
		
		//FIXME: must be sorted
		for( ParameterStat ps : parametersStats.values() ){
			res[i++]=ps.getDistanceMean();
		}
		
		
		
		return res;
	}
	




	/**
	 * Return the number of parameters of a rule
	 * 
	 * @return
	 */
	public int getParametersNumber(){
		return parametersStats.size();
	}
	
	/**
	 * Returns the rule name
	 * @return
	 */
	public String getRuleName() {
		return ruleName;
	}
	
	/**
	 * Returns the different values that the parameter at the given position can assume
	 * @param position
	 * @return
	 * @throws RuleStatisticsException 
	 */
	public Set<String> getParameterValues(int position) throws RuleStatisticsException{
		if ( position >= parametersStats.size() ){
			throw new RuleStatisticsException("Parameter not exists");
		}
		return parametersStats.get(position).getValues();
	}

	public double[] getParametersDistanceStdDev() throws RuleStatisticExceptionNoParameters {
		if ( parametersStats.size() == 0 ){
			throw new RuleStatisticExceptionNoParameters();
		}
		double[] res= new double[parametersStats.size()];
		
		int i = 0;
		for( ParameterStat ps : parametersStats.values() ){
			res[i++]=ps.getDistanceStdDev();
		}
		
		return res;
	}

	/**
	 * Return the total number of events recorded
	 * 
	 * @return
	 */
	public int getTotalEvents(){
		return eventOccurrencies.size();
	}

	public double[] getParametersDistancePercentile(int p) throws RuleStatisticExceptionNoParameters {
		if ( parametersStats.size() == 0 ){
			throw new RuleStatisticExceptionNoParameters();
		}
		double[] res= new double[parametersStats.size()];
		
		int i = 0;
		for( ParameterStat ps : parametersStats.values() ){
			res[i++]=ps.getDistancePercentile(p);
		}
		
		return res;
	}

	public double[] getRiMedians() throws RuleStatisticExceptionNoParameters {
		if ( parametersStats.size() == 0 ){
			throw new RuleStatisticExceptionNoParameters();
		}
		double[] res= new double[parametersStats.size()];
		
		int i = 0;
		for( ParameterStat ps : parametersStats.values() ){
			res[i++]=ps.getRIMedian();
		}
		
		return res;
	}
	
	public double[] getRaMedians() throws RuleStatisticExceptionNoParameters {
		if ( parametersStats.size() == 0 ){
			throw new RuleStatisticExceptionNoParameters();
		}
		double[] res= new double[parametersStats.size()];
		
		int i = 0;
		for( ParameterStat ps : parametersStats.values() ){
			res[i++]=ps.getRAMedian();
		}
		
		return res;
	}

	/**
	 * Returns an array with the number of different symbols generated by applying GO to the different parameters.
	 * Position 0 contains the number of distinct symbols generated by applying GO to the first parameter, position 1 
	 * to the second and so on. 
	 * 
	 * @return
	 * @throws RuleStatisticExceptionNoParameters
	 */
	public double[] getGoMedians() throws RuleStatisticExceptionNoParameters {
		if ( parametersStats.size() == 0 ){
			throw new RuleStatisticExceptionNoParameters();
		}
		double[] res= new double[parametersStats.size()];
		
		int i = 0;
		for( ParameterStat ps : parametersStats.values() ){
			res[i++]=ps.getGOMedian();
		}
		
		return res;
	}

	public double[] getSameMedians() throws RuleStatisticExceptionNoParameters {
		if ( parametersStats.size() == 0 ){
			throw new RuleStatisticExceptionNoParameters();
		}
		double[] res= new double[parametersStats.size()];
		
		int i = 0;
		for( ParameterStat ps : parametersStats.values() ){
			res[i++]=ps.getSameMedian();
		}
		
		return res;
	}


}
