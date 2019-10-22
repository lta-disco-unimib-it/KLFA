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
