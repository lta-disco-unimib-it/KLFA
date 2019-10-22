package it.unimib.disco.lta.alfa.eventsDetection.slct;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LogMessageDataExtractor {
	private Pattern dataPattern;
	private Pattern componentPattern;
	private boolean dontSplitComponents = false;
	private String globalComponentName = "GLOBAL";
	
	public LogMessageDataExtractor(Pattern dataPattern) {
		this.dataPattern = dataPattern;
	}
	
	
	public boolean isDontSplitComponents() {
		return dontSplitComponents;
	}


	public void setDontSplitComponents(boolean dontSplitComponents) {
		this.dontSplitComponents = dontSplitComponents;
	}


	public String getGlobalComponentName() {
		return globalComponentName;
	}


	public void setGlobalComponentName(String globalComponentName) {
		this.globalComponentName = globalComponentName;
	}


	public String getLineData(String line) {
		
		Matcher m = dataPattern.matcher(line);
		if(m.find()){
			int groupC = m.groupCount();
			StringBuffer result = new StringBuffer();
			
			for ( int i = 0; i < groupC; ++i ){
				
				result.append(m.group(i+1));
				
			}
			//System.out.println("LD: "+result.toString());
			return result.toString().trim();
		}
		return null;
	}
	
	public String getComponentName(String line) {
		if ( dontSplitComponents ){
			return globalComponentName ;
		}
		
		Matcher m = componentPattern.matcher(line);
		if(m.find()){
			
			int groupC = m.groupCount();
			//System.out.println("Groups "+groupC);
			StringBuffer result = new StringBuffer();
			for ( int i = 0; i < groupC; ++i ){
				result.append(m.group(i+1));
			}
			
			return result.toString();
		}
		System.out.println("NO COMPONENT "+line);
		return null;
	}
	

	public Pattern getDataPattern() {
		return dataPattern;
	}

	public void setDataPattern(Pattern dataPattern) {
		this.dataPattern = dataPattern;
	}

	public Pattern getComponentPattern() {
		return componentPattern;
	}

	public void setComponentPattern(Pattern componentPattern) {
		this.componentPattern = componentPattern;
	}
	
	
}