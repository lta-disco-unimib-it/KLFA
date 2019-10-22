package it.unimib.disco.lta.alfa.tools;

import grammarInference.Record.Symbol;

import java.util.LinkedList;
import java.util.Queue;

public class KPath {

	private int k;
	private Queue<String> path = new LinkedList<String>();

	public KPath(int pathLen) {
		this.k = pathLen;
	}

	
	public KPath(KPath rhs){
		this.k = rhs.k;
		this.path = new LinkedList<String>(rhs.path);
	}
	
	/**
	 * Modify the current path by removing the first token stored and pushing the passed one
	 * @param symbol
	 * @return the removed token
	 */
	public String nextStep( String symbol ){
		
		path.add(symbol);
		if ( path.size() > k ){
			return path.remove();
		} else {
			return null;
		}
		
	}
	
	public boolean equals( Object o ){
		if ( o == this ) {
			return true;
		}
		
		if ( ! ( o instanceof KPath ) ){
			return false;
		}
		
		KPath passed = (KPath)o;
		
		if ( passed.k != this.k ){
			return false;
		}
		
		return passed.path.equals(this.path);
	}
	
	public int hashCode(){
		return toString().hashCode();
	}
	
	public String toString(){
		StringBuffer msgb = new StringBuffer();
		boolean first = true;
		for ( String s : path ){
			if ( first ){
				first = false;
			} else {
				msgb.append("#");
			}
			msgb.append(s);
		}
		return msgb.toString();
	}
	
	
	
}
