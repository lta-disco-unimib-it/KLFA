package it.unimib.disco.lta.alfa.frequentItemsetsAnalysis;

import it.unimib.disco.lta.alfa.parametersAnalysis.Cluster;

import java.util.ArrayList;
import java.util.List;


public class ItemsetClusterGenerator<T>  {

	public ArrayList<Cluster<T>> getClusters(List<Itemset<T>> itemsets) {
		ArrayList<Cluster<T>> clusters = new ArrayList<Cluster<T>>();
		ArrayList<Cluster<T>> finalClusters = new ArrayList<Cluster<T>>();
		for ( Itemset<T> itemset : itemsets ){
			Cluster<T> c = new Cluster<T>();
			c.addAll(itemset.getItems());
			clusters.add(c);
			
		}
		
		int oldClusterSize = Integer.MAX_VALUE;

		while ( clusters.size() < oldClusterSize ){
			finalClusters.add(clusters.get(0));
			for ( int i = 1; i < clusters.size(); ++i ){
				Cluster<T> cluster = clusters.get(i);
				mergeToSimilar(finalClusters,cluster);
			}
			oldClusterSize = clusters.size();
			clusters = finalClusters;
			finalClusters = new ArrayList<Cluster<T>>();
		}
		
		return clusters;
	}

	private void mergeToSimilar(ArrayList<Cluster<T>> finalClusters,
			Cluster<T> cluster) {
		Cluster<T> selected = null;
		
		for ( Cluster<T> finalCluster : finalClusters ){
			if ( finalCluster.intersect(cluster).size() > 0 ){
				selected = finalCluster;
				break;
			}
		}
		
		
		if ( selected == null ){
			//the passed cluster is not similar to any othe one, ad to the list of clusters
			finalClusters.add(cluster);
		} else {
			//we found a similar cluster, mix the two
			finalClusters.remove(selected);
			Cluster<T> merged = selected.union(cluster);
			finalClusters.add(merged);
		}
	}

}
