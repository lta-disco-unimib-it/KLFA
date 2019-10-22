package it.unimib.disco.lta.alfa.eventsDetection.slct;

import java.io.Serializable;
import java.util.regex.Pattern;

public class SlctPattern implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String id;
	private Pattern pattern;

	public SlctPattern(String id, Pattern pattern ){
		this.id = id;
		this.pattern = pattern;
	}

	public Pattern getPattern() {
		return pattern;
	}

	public String getId() {
		return id;
	}




}
