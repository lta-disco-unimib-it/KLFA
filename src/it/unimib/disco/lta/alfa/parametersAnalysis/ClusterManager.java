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
