package it.unimib.disco.lta.alfa.dataTransformation;

import java.util.ArrayList;
import java.util.List;



/**
 * This ValueTransformer replaces each attribute value with a number that indicates how many attribute values
 * never observed before have been detected from the first appearance of the value to be rewritten.
 * If the value was never seen before "0" is returned
 * 
 * If called on this sequence:
 * 
 * A A B C D A D A C E F G D G D F A B C F C F B 
 * 
 * it will produce a sequence:
 * 
 * 0 1 0 0 0 4 1 4 2 0 0 0 4 1 4 2 7 6 5 1 5 1 6
 * 
 * @author Fabrizio Pastore fabrizio.pastore AT gmail.com
 *
 */
public class ValueTransformerRelativeToInstantiation implements ValueTransformer {
	private List<String> keys = new ArrayList<String>();
	private int lastAdded = -1;
	private String prefix;
	
	public ValueTransformerRelativeToInstantiation( String prefix ){
		this.prefix = prefix;
	}
	
	public String getTransformedValue(String data) {
		if ( keys.contains(data) ){
			lastAdded = -1;
			int index = keys.indexOf(data);
			int relative = -1*(index-keys.size());
			return prefix+relative;
		}
		lastAdded = keys.size();
		keys.add(data);
		return prefix+"0";
	}

	protected void reset ( String data ){
		if ( keys.contains(data) ){
			int index = keys.indexOf(data);
			keys.add(index, null);
		}
	}

	public void reset() {
		keys = new ArrayList<String>();

	}
	
	public String getTransformedValue(String value, String[] line) {
		return getTransformedValue(value);
	}

	public void back() {
		if(lastAdded == -1) {
			return;
		}
		else {
			keys.remove(lastAdded);
		}
	}
}
