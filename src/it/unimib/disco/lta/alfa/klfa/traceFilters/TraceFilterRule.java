package it.unimib.disco.lta.alfa.klfa.traceFilters;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.regex.Pattern;

public abstract class TraceFilterRule implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private HashMap<Integer, Pattern> matchingRules = new HashMap<Integer, Pattern>();
	private String name = "";
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public TraceFilterRule( ){ 
	}
	
	public TraceFilterRule( String name ){ 
	}
	
	public void putColumnRules( HashMap<Integer,String> matchingRules ){
		for ( Entry<Integer,String> entry : matchingRules.entrySet() ){
			matchingRules.put( entry.getKey(), entry.getValue() );
		}
	}
	
	public void putColumnRule( int colNumber, String regex ){
		matchingRules.put(colNumber, Pattern.compile(regex));
	}
	
	
	public boolean match(String[] cols) {
		for ( int i = 0; i < cols.length; ++i ){
			Pattern rulePattern = matchingRules.get(i);
			if ( rulePattern == null ){
				continue;
			}
			if ( ! rulePattern.matcher(cols[i]).matches() ){
				return false;
			}
		}
		return true;
	}
	
	/**
	 * Returns true if the following line, splitted by cols can be accepted.
	 * 
	 * @param cols
	 * @return
	 */
	public abstract boolean accept( String cols[] );
	
	public abstract boolean reject( String cols[] );

	public HashMap<Integer, Pattern> getColumnRules() {
		return matchingRules;
	}

	public List<Entry<Integer, Pattern>> getColumnRulesSorted() {
		List<Entry<Integer,Pattern>> entries = new ArrayList<Entry<Integer,Pattern>>(matchingRules.size());
		entries.addAll(matchingRules.entrySet());
		Collections.sort(entries, new Comparator<Entry<Integer,Pattern>>(){

			public int compare(Entry<Integer, Pattern> o1,
					Entry<Integer, Pattern> o2) {
				return o1.getKey() - o2.getKey();
			}
			
		});
		return entries;
	}
	
}
