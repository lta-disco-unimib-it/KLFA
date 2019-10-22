package it.unimib.disco.lta.alfa.parametersAnalysis;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class Cluster<E> {

	private HashSet<E> set = new HashSet<E>();
	
	public boolean contains(E o) {
		return set.contains(o);
	}

	public boolean add(E e) {
		return set.add(e);
	}

	public boolean addAll(Collection<? extends E> c) {
		return set.addAll(c);
	}

	public boolean containsAll(Collection<?> c) {
		return set.containsAll(c);
	}

	
	
	public Cluster<E> union(Cluster<E> rhs){
		Cluster<E> result = new Cluster<E>();
		result.addAll(set);
		result.addAll(rhs.set);
		return result;
	}

	public HashSet<E> getElements() {
		HashSet<E> result = new HashSet<E>();
		result.addAll(set);
		return result;
	}

	/**
	 * Returns a new Cluster that contains the intersection of the two
	 * @param rhs
	 * @return
	 */
	public Cluster<E> intersect(Cluster<E> rhs) {
		//Intersecting elements
		HashSet<E> resultSet = new HashSet<E>();
		resultSet.addAll(set);
		resultSet.retainAll(rhs.set);
		
		
		Cluster<E> result = new Cluster<E>();
		result.addAll(resultSet);
		
		return result;
	}
	
	public int hashCode(){
		String res ="";
		for ( E e : set ){
			res += e.toString();
		}
		return res.hashCode();
	}
	
	public boolean equals(Object o ){
		if ( ! ( o instanceof Cluster) ){
			return false;
		}
		return o.hashCode() == this.hashCode();
	}

	public String getSignature() {
		StringBuffer sb = new StringBuffer();
		ArrayList<String> names = new ArrayList<String>();
		for ( E e : set ){
			names.add(e.toString());
		}
		
		Collections.sort(names);
		
		for ( int i = 0; i < names.size(); ++i ){
			if ( i > 0 ){
				sb.append("-");
			}
			sb.append(names.get(i));
		}
		
		return sb.toString().replace(",", ".").replace(":", ".");
	}

	public boolean isEmpty() {
		return set.isEmpty();
	}

	public int size() {
		return set.size();
	}
	
	public String toString(){
		StringBuffer sb = new StringBuffer();
		
		sb.append("[");
		

		for ( E el : set){

			sb.append(" ");
			sb.append(el.toString());
		}

		sb.append("]");
		
		return sb.toString();
	}
}
