package it.unimib.disco.lta.alfa.frequentItemsetsAnalysis;

import java.util.List;
import java.util.Set;

public class Itemset<T> {
	

	private Set<T> items;
	private int frequence;

	public Itemset(Set<T> items,int frequence){
		this.items = items;
		this.frequence = frequence;
	}

	public Set<T> getItems() {
		return items;
	}

	public int getFrequence() {
		return frequence;
	}
}
