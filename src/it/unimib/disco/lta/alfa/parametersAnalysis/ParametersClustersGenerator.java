package it.unimib.disco.lta.alfa.parametersAnalysis;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ParametersClustersGenerator {

	private ArrayList<TraceData> tracesData;
	private double intersectionThreshold;
	private int support;
	private boolean done = false;
	private List<List<Cluster<RuleParameter>>> tracesClusters = new ArrayList<List<Cluster<RuleParameter>>>();
	private Set<Cluster<RuleParameter>> clusters = new HashSet<Cluster<RuleParameter>>();
	private Set<Cluster<RuleParameter>> singleClusters = new HashSet<Cluster<RuleParameter>>();;
	private boolean dontGroup = false;
	
	/**
	 * Constructor
	 * 
	 * @param tracesData
	 * @param intersectionThreshold
	 * @param support
	 */
	public ParametersClustersGenerator(ArrayList<TraceData> tracesData,
			double intersectionThreshold, int support) {
		this.tracesData = tracesData;
		this.intersectionThreshold = intersectionThreshold;
		this.support = support;
	}

	/**
	 * Returns all the generated clusters.
	 * @return
	 */
	public Set<Cluster<RuleParameter>> getAllClusters() {
		calculate();
		
		Set<Cluster<RuleParameter>> set = new HashSet<Cluster<RuleParameter>>();
		
		set.addAll(clusters);
		
		set.addAll(singleClusters);
		
		return set;
	}
	
	/**
	 * Returns cluster with just one element.
	 * 
	 * @return
	 */
	public Set<Cluster<RuleParameter>> getSingleClusters() {
		calculate();
		
		Set<Cluster<RuleParameter>> set = new HashSet<Cluster<RuleParameter>>();
		
		
		
		set.addAll(singleClusters);
		
		return set;
	}
	
	/**
	 * Returns cluster with more than one elements
	 * 
	 * @return
	 */
	public Set<Cluster<RuleParameter>> getClusters() {
		calculate();
		
		Set<Cluster<RuleParameter>> set = new HashSet<Cluster<RuleParameter>>();
		
		set.addAll(clusters);
		
		
		
		return set;
	}

	private void calculate() {
		if ( done  ){
			return;
		}
		
	
		if ( ! dontGroup ){
			generateDistinctTraceClusters();


			generateClusters();
		}
		
		generateSingletonClusters();
		
		done = true;
	}

	private void generateSingletonClusters() {
		Set<RuleParameter> ruleParameters = getRuleParameters( tracesData );
		
		Set<RuleParameter> toDiscard = RuleStatisticsUtils.getRulesToDiscard(tracesData);
		
		ruleParameters.removeAll(toDiscard);
		
		HashSet<RuleParameter> rulesWithoutCluster = getRulesNotInCluster( clusters, ruleParameters );
		
		
		singleClusters = getSingleClusters(clusters,rulesWithoutCluster);
		
	}

	private static Set<RuleParameter> getRuleParameters(
			ArrayList<TraceData> tracesData) {
		Set<RuleParameter> result = new HashSet<RuleParameter>();
		for ( TraceData td : tracesData ){
			RulesStatistics rs = td.getRulesStatistics();
			Collection<RuleStatistics> stats = rs.getStatistics();
			for ( RuleStatistics stat : stats  ){
				String ruleName = stat.getRuleName();
				for ( int i = 0; i < stat.getParametersNumber(); ++i ){
					RuleParameter ruleParameter = new RuleParameter(ruleName,i);
					result.add(ruleParameter);
				}
			}
		}
		return result;
	}
	
	private static HashSet<RuleParameter> getRulesNotInCluster(
			Set<Cluster<RuleParameter>> clusters,
			Set<RuleParameter> ruleParameters) {
		
		Set<RuleParameter> rulesWithClusters = new HashSet<RuleParameter>();
		
		for( Cluster<RuleParameter> cluster : clusters ){
			rulesWithClusters.addAll(cluster.getElements());
		}
		
		HashSet<RuleParameter> rulesWithoutCluster = new HashSet<RuleParameter>();
		rulesWithoutCluster.addAll(ruleParameters);
		
		rulesWithoutCluster.removeAll(rulesWithClusters);
		
		return rulesWithoutCluster;
	}
	
	
	private static Set<Cluster<RuleParameter>> getSingleClusters(
			Set<Cluster<RuleParameter>> clusters,
			HashSet<RuleParameter> rulesWithoutCluster) {
		Set<Cluster<RuleParameter>> result = new HashSet<Cluster<RuleParameter>>();
		
		for ( RuleParameter ruleWithoutCluster : rulesWithoutCluster ){
			Cluster<RuleParameter> c = new Cluster<RuleParameter>();
			c.add(ruleWithoutCluster);
			result.add(c);
		}
		
		return result;
	}
	
	private void generateClusters() {
		clusters = getIntersectingClusters(tracesClusters);
	}
	
	/**
	 * This method given a list containing the parameter clusters calculated over the different traces returns the 
	 * clusters that hold on all the traces
	 * 
	 * @param tracesClusters
	 * @return
	 */
	private static Set<Cluster<RuleParameter>> getIntersectingClusters(
			List<List<Cluster<RuleParameter>>> tracesClusters) {
		Set<RuleParameter> parameters = new HashSet<RuleParameter>();
		for(List<Cluster<RuleParameter>> clusterlist : tracesClusters ){
			for(Cluster<RuleParameter> cluster : clusterlist ){
				parameters.addAll(cluster.getElements());
			}
		}
		
		
		HashSet<Cluster<RuleParameter>> result = new HashSet<Cluster<RuleParameter>>();
		for ( RuleParameter parameter : parameters ){
			List<Cluster<RuleParameter>> clusters = getContainingClusters(parameter,tracesClusters);
			Cluster<RuleParameter> cluster = getIntersectingCluster(clusters);
			result.add(cluster);
		}
		
		return result;
	}

	private static Cluster<RuleParameter> getIntersectingCluster(
			List<Cluster<RuleParameter>> clusters) {
		Cluster<RuleParameter> result = clusters.get(0);
		for( int i = 1; i < clusters.size(); ++i ){
			result = result.intersect(clusters.get(i));
		}
		return result;
	}

	private static List<Cluster<RuleParameter>> getContainingClusters(RuleParameter parameter,
			List<List<Cluster<RuleParameter>>> tracesClusters) {
		ArrayList<Cluster<RuleParameter>> result = new ArrayList<Cluster<RuleParameter>>();
		
		for(List<Cluster<RuleParameter>> clusterlist : tracesClusters ){
			for(Cluster<RuleParameter> cluster : clusterlist ){
				if ( cluster.contains(parameter)){
					result.add(cluster);
				}
			}
		}
		return result;
	}

	/**
	 * For every trace generate the cluster of its parameters
	 * 
	 */
	private void generateDistinctTraceClusters() {
		
		
		for ( int traceIndex = 0; traceIndex < tracesData.size(); ++traceIndex ){
			TraceData traceData = tracesData.get(traceIndex);
			
			RulesStatistics rs = traceData.getRulesStatistics(); //statistics for current trace
			Collection<RuleStatistics> stats = rs.getStatistics(); //statistics for all the parameters
			
			
			List<ParametersIntersection> intersections = RuleStatisticsUtils.getIntersections(stats,intersectionThreshold,support );
			
			List<Cluster<RuleParameter>> clusters = getRuleParametersClusters(intersections);
			
			tracesClusters.add(clusters);
			
		}
	}


	/**
	 * Generate rule parameters clusters on the basis of rule parameters intersections
	 * 
	 * @param intersections
	 * @return 
	 */
	private static List<Cluster<RuleParameter>> getRuleParametersClusters(List<ParametersIntersection> intersections){
		ClusterManager<RuleParameter> cm = new ClusterManager<RuleParameter>();
		
		for(ParametersIntersection intersection : intersections){
			cm.add(intersection.getRuleParameter1(), intersection.getRuleParameter2());
		}
		return cm.getClusters();
	}

	public boolean isDontGroup() {
		return dontGroup;
	}

	/**
	 * Set to true if you do not want to create clusters of parameters.
	 * If set to true clusters will include only a single parameter.
	 * 
	 * @param dontGroup
	 */
	public void setDontGroup(boolean dontGroup) {
		this.dontGroup = dontGroup;
	}




	
}
