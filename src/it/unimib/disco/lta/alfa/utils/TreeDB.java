package it.unimib.disco.lta.alfa.utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class TreeDB<K> {
	private Node<K> root = new Node<K>(null);
	private int totalCount;
	
	public static interface Visitor<K>{
		
		public boolean visit( Node<K> node );
		
		public void visited( Node<K> node );
	};
	
	
	public static class StatsVisitor<K> implements Visitor<K> {
		private HashMap<List<K>,Long> elementsStats = new HashMap<List<K>,Long>();
		private List<K> currentVisited = new ArrayList<K>();
		
		public boolean visit(Node<K> node) {
			currentVisited.add(node.key);
			
			elementsStats.put(copyList(currentVisited),node.counter);
			
			return true;
		}

		private List<K> copyList(List<K> currentVisited2) {
			List<K> result = new ArrayList<K>();
			result.addAll(currentVisited2);
			return result;
		}

		public void visited(Node<K> node) {
			currentVisited.remove(currentVisited.size()-1);
		}

		public HashMap<List<K>, Long> getElementsStats() {
			return elementsStats;
		}
		
	}
	
	public static class Node<K>{
		private HashMap<K,Node<K>> children = new HashMap<K,Node<K>>();
		private long counter = 0;
		private K key;
		
		public Node( K key ){
			this.key = key;
		}
		
		public void add( Collection<K> sequence ){
			add(sequence.iterator());
		}
		
		private void add( Iterator<K> it ){
			++counter;
			if ( it.hasNext() ){
				K element = it.next();
				Node<K> child = children.get(element);
				if  ( child == null ){
					child = new Node<K>(element);
					children.put(element,child);
				}
				child.add(it);
			}
		}
		
		
	}
	
	public void add(Collection<K> sequence){
		totalCount++;
		root.add(sequence);
	}
	
	public HashMap<List<K>,Long> getStats(){
		StatsVisitor<K> sv = new StatsVisitor<K>();
		depthFirstVisit(sv);
		return sv.getElementsStats();
	}
	
	public void depthFirstVisit( Visitor<K> visitor ){
		depthFirstVisit(root,visitor);
	}
	
	private void depthFirstVisit( Node<K> node, Visitor<K> visitor ){
		for ( Node<K> child : node.children.values() ){
			if ( visitor.visit(child) ){
				depthFirstVisit(child,visitor);
			}
			visitor.visited(child);
		}
	}

	public int getTotalCount() {
		return totalCount;
	}
}
