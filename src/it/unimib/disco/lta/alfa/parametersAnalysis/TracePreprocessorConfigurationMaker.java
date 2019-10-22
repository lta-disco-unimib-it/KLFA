package it.unimib.disco.lta.alfa.parametersAnalysis;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TracePreprocessorConfigurationMaker {
	enum configurations {RI,RA,GO, SAME};

	private static interface ConfigurationCreator{
		public void fillSuggestedConfigurations(List<List<String>> preprocessingRules,
				Map<String, String> transformerRulesMap, ClusterStatistics clusterStatistics,
				RTableExporter exporter);
	}
	
	private static class SuggestedConfigurationCreator implements ConfigurationCreator {
		public void fillSuggestedConfigurations(List<List<String>> preprocessingRules,
				Map<String,String> transformerRules, ClusterStatistics clusterStatistics,
				RTableExporter exporter){
			Cluster<RuleParameter> cluster = clusterStatistics.getCluster();
			
			double ri = clusterStatistics.getRiMedian();
			double ra = clusterStatistics.getRaMedian();
			double go = clusterStatistics.getGoMedian();
			double same = clusterStatistics.getSameMedian();
			
			configurations selected = null;
			
			
			
			
			if ( ri < 10 || ra < 10 || go < 10 || same < 10 ){
				if ( same < ri && same < ra && same < go ) {
					selected = configurations.SAME;
				} else if ( ra <= ri && ra <= go && ra <= same ) {
					selected = configurations.RA;
				} else if ( ri <= ra && ri <= go && ri <= same ){
					selected  = configurations.RI;	
				} else if ( go <= ri && go <= ra && go <= same ) {
					selected = configurations.GO;
				}
			} 
				

			if ( selected == configurations.RI ){
				preprocessingRules.addAll(getPreprocessingRule(cluster, exporter));
				transformerRules.put(cluster.getSignature(),getTransformerRulesRI(cluster,exporter));
			} else if ( selected == configurations.RA) {
				preprocessingRules.addAll(getPreprocessingRule(cluster, exporter));
				transformerRules.put(cluster.getSignature(),getTransformerRulesRA(cluster,exporter));
			} else if ( selected == configurations.GO) {
				preprocessingRules.addAll(getPreprocessingRule(cluster, exporter));
				transformerRules.put(cluster.getSignature(),getTransformerRulesGO(cluster,exporter));
			} else if ( selected == configurations.SAME) {
				preprocessingRules.addAll(getPreprocessingRule(cluster, exporter));
				transformerRules.put(cluster.getSignature(),getTransformerRulesSAME(cluster,exporter));
			}
			
			
//			if (	//RI 
//				clusterStatistics.getDistinctValuesMean() > 1 &&
//				clusterStatistics.getDistinctValuesDistanceMedianMean() < 10 && //FIXME: this is not very good
//				clusterStatistics.getDistinctValuesDistanceMedianMean() * 2 > clusterStatistics.getDistinctValuesDistanceStdDevMean() //A value appears in a pattern and then is no longer used 
//			){
//				preprocessingRules.addAll(getPreprocessingRule(cluster, exporter));
//				transformerRules.put(cluster.getSignature(),getTransformerRulesRI(cluster,exporter));
//			} else if ( //RA
//				clusterStatistics.getDistinctValuesMean() > 2 && 
//				clusterStatistics.getDistinctValuesDistanceMedianMean() > 1 &&
//				clusterStatistics.getValuesNumberOnValuesDistanceMedian() > 1 &&
//				clusterStatistics.getDistinctValuesDistanceMeanMean() > clusterStatistics.getDistinctValuesDistancesThirdQuartileMean() &&
//				clusterStatistics.getDistinctValuesDistanceMedianMean() * 10 > clusterStatistics.getDistinctValuesDistancesThirdQuartileMean()
//			) {
//				preprocessingRules.addAll(getPreprocessingRule(cluster, exporter));
//				transformerRules.put(cluster.getSignature(),getTransformerRulesRA(cluster,exporter));
//			} //FIXME: add GO
			
			transformerRules.put("SAME","SAME");
		}

		
	}
	
	private static class CompleteConfigurationCreator implements ConfigurationCreator {
		public void fillSuggestedConfigurations(List<List<String>> preprocessingRules,
				Map<String,String> transformerRules, ClusterStatistics clusterStatistics,
				RTableExporter exporter){
			Cluster<RuleParameter> cluster = clusterStatistics.getCluster();



			preprocessingRules.addAll(getPreprocessingRule(cluster, exporter));
			transformerRules.put(cluster.getSignature(),getTransformerRulesInciomplete(cluster,exporter));

		}
	}
	

	public TracePreprocessorConfiguration getSuggestedConfigurations(List<ClusterStatistics> clustersStats, RTableExporter tableExporter) {
		return getConfigurations( new SuggestedConfigurationCreator(), clustersStats, tableExporter);
	}
	
	public TracePreprocessorConfiguration getCompleteConfigurations(List<ClusterStatistics> clustersStats, RTableExporter tableExporter) {
		return getConfigurations( new CompleteConfigurationCreator(), clustersStats, tableExporter);
	}
	
	private TracePreprocessorConfiguration getConfigurations(ConfigurationCreator creator, List<ClusterStatistics> clustersStats, RTableExporter tableExporter) {
		List<List<String>> preprocessingRules = new ArrayList<List<String>>();
		Map<String,String> transformerRulesMap = new HashMap<String,String>();
		
		
		for( ClusterStatistics clusterStatistics : clustersStats ){
			creator.fillSuggestedConfigurations(preprocessingRules,transformerRulesMap,clusterStatistics, tableExporter);
		}
		
		preprocessingRules.add(getGenericRule());
		transformerRulesMap.put("SAME", "SAME");
		
		
		
		
		
		TracePreprocessorConfiguration tpc = new TracePreprocessorConfiguration( transformerRulesMap, preprocessingRules );
		return tpc;
	}
	
	private static String getTransformerRulesGO(Cluster<RuleParameter> cluster,
			RTableExporter exporter) {
		return "GO";
	}

	private static String getTransformerRulesRA(Cluster<RuleParameter> cluster,
			RTableExporter exporter) {
		return "RA";
	}

	private static String getTransformerRulesRI(
			Cluster<RuleParameter> cluster, RTableExporter exporter) {
		return "RI";
	}
	
	private static String getTransformerRulesSAME(
			Cluster<RuleParameter> cluster, RTableExporter exporter) {
		return "SAME";
	}
	
	private static String getTransformerRulesInciomplete(
			Cluster<RuleParameter> cluster, RTableExporter exporter) {
		return "";
	}

	public static List<List<String>> getPreprocessingRule (Cluster<RuleParameter> cluster, RTableExporter exporter ){
		List<List<String>> preprocessingRules = new ArrayList<List<String>>();
		for ( RuleParameter p : cluster.getElements()){
			String ruleSignature = p.getRuleName();
			String[] signatureElements = exporter.getSignatureElements(ruleSignature);
			
			ArrayList<String> ruleLine = new ArrayList<String>();
			
			ruleLine.add( getParsedExpression(signatureElements[0]) ); //component expression
			ruleLine.add( getParsedExpression(signatureElements[1]) ); //action expression
			ruleLine.add("0:SAME");	//component name transformer
			ruleLine.add("1:SAME"); //action name transformer
			ruleLine.add( 2+p.getParameterPos() +":"+cluster.getSignature());	//parameter transformer
			
			preprocessingRules.add(ruleLine);
		}
		return preprocessingRules;
	}
	
	private static String getParsedExpression(String key) {
		return key.replace(")", "\\)").replace("(", "\\(").replace("[", "\\[").replace("]", "\\]");
	}
	
	public static List<String> getGenericRule(){
		List<String> rule = new ArrayList<String>();
		rule.add(".*");
		rule.add(".*");
		rule.add("0:SAME");
		rule.add("1:SAME");
		return rule;
	}

}
