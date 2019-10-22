package it.unimib.disco.lta.alfa.eventsDetection;

import it.unimib.disco.lta.alfa.eventsDetection.slct.SlctPattern;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SlctPatternsCollection implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private HashMap<String,List<SlctPattern>> patterns = new HashMap<String, List<SlctPattern>>();
	private List<SlctPattern> allPatterns = new ArrayList<SlctPattern>();
	
	public List<SlctPattern> getPatternsForComponent(Object key) {
		return patterns.get(key);
	}

	public List<SlctPattern> putPatternsForComponent(String key, List<SlctPattern> value) {
		//TODO: throw exc if component already defined
		allPatterns.addAll(value);
		return patterns.put(key, value);
		
	}

	public List<SlctPattern> getAllPatterns() {
		return allPatterns;
	}
}
