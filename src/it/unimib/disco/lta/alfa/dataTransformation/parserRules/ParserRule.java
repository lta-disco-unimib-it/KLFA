package it.unimib.disco.lta.alfa.dataTransformation.parserRules;

import it.unimib.disco.lta.alfa.dataTransformation.ValueTransformer;
import it.unimib.disco.lta.alfa.logging.Logger;

import java.util.Collection;
import java.util.HashMap;



public class ParserRule {
	
	private String componentExp;
	private String eventExp;
	private final String separator = "_";
	private HashMap<Integer,ValueTransformer> keyDispenser = new HashMap<Integer, ValueTransformer>();
	private int eventPos;
	private int componentPos;
	private boolean replaceSkippedElementsWithDash = false;
	
	

	/**
	 * Returns true if this rule replace skipped elements with a dash.
	 * 
	 * @return
	 */
	public boolean isReplaceSkippedElementsWithDash() {
		return replaceSkippedElementsWithDash;
	}

	/**
	 * Set true if the elements not processed by the rule must be replaced with a dash.
	 *  
	 * @param addDashForSkippedElements
	 */
	public void setReplaceSkippedElementsWithDash(boolean addDashForSkippedElements) {
		this.replaceSkippedElementsWithDash = addDashForSkippedElements;
	}

	public ParserRule ( int componentPos, int eventPos, String componentExp, String actionExp ){
		this.componentExp = componentExp;
		this.eventExp = actionExp;
		this.componentPos = componentPos;
		this.eventPos = eventPos;
	}
	
	public void setValueTrasformer( Integer position, ValueTransformer transformer ){
		Logger.finer("Keydispenser setting value transformer "+transformer);
		keyDispenser.put(position, transformer);
	}
	
	public Collection<ValueTransformer> getValueTransformers(){
		return keyDispenser.values();
	}
	
	public boolean hasValueTransformers(){
		return keyDispenser.size() > 0;
	}
	
	public int getValueTransformersNumber(){
		return keyDispenser.size();
	}
	
	public String processSingleElement(String lineElements[],int idx) throws ParserRuleException{
		ValueTransformer t = keyDispenser.get(idx);
		if ( t == null ){
			throw new ParserRuleException("No rule to process element at position "+idx);
		}
		String value = t.getTransformedValue(lineElements[idx],lineElements);
		return value;
	}
	
	public String process( String lineElements[] ){
		StringBuffer result = new StringBuffer();
		for ( int i = 0; i < lineElements.length; ++i){
			ValueTransformer t = keyDispenser.get(i);
			if ( t == null ){
				if ( replaceSkippedElementsWithDash ){
					if ( result.length() > 0 )
						result.append(separator);
					result.append("-");
				} 
				continue;
				
			}
			
			if ( result.length() > 0 )
				result.append(separator);
			String value = t.getTransformedValue(lineElements[i],lineElements);
			
			result.append(value);
		}
		return result.toString();
	}
	
	public boolean match ( String lineElements[] ){
		if ( ! lineElements[componentPos].matches(componentExp) )
			return false;
		return lineElements[eventPos].matches(eventExp);
	}

	public void reset() {
		Logger.entering(ParserRule.class.getCanonicalName(), "reset()" );
		for ( ValueTransformer t : keyDispenser.values() ){
			t.reset();
		}
	}
	
	public void back() {
		for(ValueTransformer t : keyDispenser.values()) {
			t.back();
		}
	}

	public String getMatchingElements(String[] lineElements) {
		StringBuffer result = new StringBuffer();
		for ( int i = 0; i < lineElements.length; ++i){
			ValueTransformer t = keyDispenser.get(i);
			if ( t == null )
				continue;
			if ( result.length() > 0 )
				result.append(separator);
			String value = lineElements[i];
			result.append(value);
		}
		return result.toString();
	}

}
