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

public class ClusterManager<E> {
	ArrayList<Cluster<E>> clusters = new ArrayList<Cluster<E>>();
	
	public void add(E left, E rigth) {
		Cluster<E> leftC = getCluster(left);
		Cluster<E> rigthC = getCluster(rigth);
		if ( leftC != rigthC ){
			Cluster<E> mergedCluster = leftC.union(rigthC);
			clusters.remove(leftC);
			clusters.remove(rigthC);
			clusters.add(mergedCluster);
		}
	}

	private Cluster<E> getCluster(E e) {
		for( Cluster<E> cluster: clusters ){
			if( cluster.contains(e) ){
				return cluster;
			}
		}
		Cluster<E> cluster = new Cluster<E>();
		cluster.add(e);
		clusters.add(cluster);
		return cluster;
	}

	public ArrayList<Cluster<E>> getClusters() {
		return clusters;
	}
	
	

}
