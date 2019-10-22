package it.unimib.disco.lta.alfa.parametersAnalysis;

import java.util.ArrayList;
import java.util.HashMap;

public class ClusterTransformationRulesAnalyzer {
	private HashMap<String,TransformationRulesAnalysis> clustersAnalysis = new HashMap<String, TransformationRulesAnalysis>();
	
	public void addParameters(Cluster cluster, ArrayList<String> par) {
		TransformationRulesAnalysis analyzer = getClusterAnalyzer(cluster);
		analyzer.addParameters(par);
	}

	private TransformationRulesAnalysis getClusterAnalyzer(Cluster cluster) {
		TransformationRulesAnalysis res = clustersAnalysis.get(cluster.getSignature());
		if ( res == null ){
			res = new TransformationRulesAnalysis();
			clustersAnalysis.put(cluster.getSignature(), res);
		}
		return res;
	}

	public void newTrace(){
		for ( TransformationRulesAnalysis analyzer : clustersAnalysis.values() ){
			analyzer.newTrace();
		}
	}
}
