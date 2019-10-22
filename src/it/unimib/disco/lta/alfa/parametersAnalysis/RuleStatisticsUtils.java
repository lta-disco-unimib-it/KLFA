package it.unimib.disco.lta.alfa.parametersAnalysis;

import it.unimib.disco.lta.alfa.logging.Logger;
import tools.violationsAnalyzer.MultiHashMap;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.Vector;
import java.util.Map.Entry;



public class RuleStatisticsUtils {

	private static int parametersLimit = 20;
	private static boolean parametersFilteringEnabled = false;

	public static double getMinumSymbolToReachPercatage(ArrayList<Integer> values, double part){
		HashMap<Integer,Integer> map = new HashMap<Integer, Integer>();
		
		for ( Integer val : values ){
			Integer count = map.get(val);
			if ( count == null ){
				count = 0;
			}
			map.put(val, ++count);
		}
		
		MultiHashMap mt = new MultiHashMap();
		for ( Entry<Integer,Integer> e : map.entrySet() ){
			mt.put(e.getValue(), e.getKey());
		}
		
		ArrayList<Integer> occurencies = new ArrayList<Integer>();
		occurencies.addAll(mt.keySet());
		Collections.sort(occurencies);
		Collections.reverse(occurencies);
		
//		System.out.println("Counting ");
//		for ( Integer occurrence : occurencies){
//			
//			Vector<Integer> symbols = (Vector<Integer>) mt.get(occurrence);
//			System.out.print( "\n"+occurrence+": " );
//			for ( Integer symbol : symbols ){
//				System.out.print( " "+symbol );
//			}
//			System.out.println("");
//		}
		
		
		int counter=0;
		int totalOcc = 0;
		for ( Integer occurrence : occurencies){
			
			Vector<Integer> symbols = (Vector<Integer>) mt.get(occurrence);
			
			for ( Integer symbol : symbols ){
				counter++;
				totalOcc += occurrence;
				if ( totalOcc >= part*values.size() ){
					return counter;
				}
			}
			
		}
		return counter;
	}
	
	public static Set<RuleParameter> getRulesToDiscard(Collection<RuleStatistics> stats){
		HashSet<RuleParameter> toDiscard = new HashSet<RuleParameter>();
		
		for ( RuleStatistics stat : stats ){
			boolean discard = false;
			if ( discardRule(stat) )
				discard =true;
			
			for( int i = 0; i  < stat.getParametersNumber(); ++i ){
				if ( discard || discardParameter(stat,i) ){
					RuleParameter rp = new RuleParameter(stat.getRuleName(),i);
					toDiscard.add(rp);
				}
			}
		}
		
		if ( toDiscard.size() > 0 ){
			System.out.println("Discarding: ");
		}
		for( RuleParameter rp : toDiscard ){
			System.out.println(rp.getRuleName()+" "+rp.getParameterPos());
		}
		
		return toDiscard;
	}
	
	/**
	 * Return the relevant intersections between the different rule parameters.
	 * All possible pairs of rule parameters are considered and only the ones with an intersection higher than the 
	 * given threshold and with a support higher than the given value, i.e. with a number of values in common higher 
	 * than the given value, are considered.
	 * 
	 * @param stats						parameters statistics
	 * @param intersectionThreshold		minimum size of the intersection with respect to the smallest parameter 
	 * @param support					minimum number of elements in intersection
	 * @return
	 */
	public static ArrayList<ParametersIntersection> getIntersections(
			Collection<RuleStatistics> stats, double intersectionThreshold, int support) {
		
		ArrayList<ParametersIntersection> result = new ArrayList<ParametersIntersection>();
		
		HashSet<RuleStatistics> processed = new HashSet<RuleStatistics>();
		
		for ( RuleStatistics stat : stats ){
			processed.add(stat);
			
			if ( discardRule(stat) ){
				Logger.fine("Discarding rule "+stat.getRuleName());
				continue;
			}

			
			for ( RuleStatistics statInt : stats ){
				for( int i = 0; i  < stat.getParametersNumber(); ++i ){
					
					if ( discardParameter(stat,i) ){
						continue;
					}
					
					if(stat==statInt){
						continue;
					}
					
					if ( processed.contains(statInt)){
						continue;
					}
					
					for( int j = 0; j  < statInt.getParametersNumber(); ++j ){
						Set<String> intersections = getIntersections(stat,i,statInt,j);
						
						
						
						
						int statSize;
						try {
							statSize = stat.getParameterValues(i).size();

							int statIntSize = statInt.getParameterValues(i).size();

							int min;
							if ( statSize > statIntSize ){
								min = statIntSize;
							} else {
								min =  statSize;
							}

							int size = intersections.size();
							if ( size >= support && (double)min*intersectionThreshold <= size){
								result.add(new ParametersIntersection(new RuleParameter(stat.getRuleName(),i),new RuleParameter(statInt.getRuleName(),j),size));
							}
						} catch (RuleStatisticsException e) {
							// TODO Auto-generated catch block
							//e.printStackTrace();
						}
					}	
				}
				
			}
			
		}
		return result;
	}
	
	private static boolean discardRule(RuleStatistics stat) {
		return stat.getParametersNumber() >= parametersLimit ;
	}

	private static boolean discardParameter(RuleStatistics statInt, int i) {
		Set<String> values;

		if ( ! parametersFilteringEnabled ){
			return false;
		}
		
		try {
			values = statInt.getParameterValues(i);

			for ( String val : values ){
				if ( val.contains(" ")){
					return true;
				}
			}
		} catch (RuleStatisticsException e) {
			return true;
		}
		return false;
	}

	/**
	 * Returns the number of equals values that the parameters at the given column have in commons  
	 * @param left
	 * @param leftIndex
	 * @param right
	 * @param rigthIndex
	 * @return 
	 * @return
	 */
	public static Set<String> getIntersections(RuleStatistics left, int leftIndex,
			RuleStatistics right, int rigthIndex) {
		
		Set<String> result = new HashSet<String>();
		try {
			Set<String> leftValues = left.getParameterValues(leftIndex);
			Set<String> rigthValues = right.getParameterValues(rigthIndex);
			
			result.addAll(leftValues);
			result.retainAll(rigthValues);
		} catch (RuleStatisticsException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return result;
	}

	public static Set<RuleParameter> getRulesToDiscard(ArrayList<TraceData> tracesData) {
		ArrayList<RuleStatistics> rules = new ArrayList<RuleStatistics>();
		
		for ( TraceData td : tracesData ){
			RulesStatistics rs = td.getRulesStatistics();
			rules.addAll(rs.getStatistics());
		}
		return getRulesToDiscard(rules);
	}
	
}
