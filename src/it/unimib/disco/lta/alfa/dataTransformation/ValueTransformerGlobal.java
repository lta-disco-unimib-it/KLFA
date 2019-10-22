package it.unimib.disco.lta.alfa.dataTransformation;
import java.util.Map;
import java.util.Map.Entry;

/**
 * This ValueTranformer associate different symbols to different values.
 * It is useful to find patterns of values in different traces
 * EG
 * if this transformer is called on the sequence:
 * A B C C D E A B C C F G 
 * 
 * it returns:
 * 0 1 2 2 3 4 0 1 2 2 5 6
 * 
 * Another transformer of the same type called on the sequence :
 * 
 * G H W W Q R G H W W Z N
 * 
 * will produce
 * 0 1 2 2 3 4 0 1 2 2 5 6
 * 
 * Permitting to reveal a common behavior.
 * 
 * @author Fabrizio Pastore fabrizio.pastore AT gmail.com
 *
 */
public class ValueTransformerGlobal implements ValueTransformer {
	private HistoryHashMap<String,String> keys = new HistoryHashMap<String, String>(); 
	private String prefix;
	private boolean changed = true;
	
	public ValueTransformerGlobal( String prefix ){
		this.prefix = prefix;
	}

	public String getTransformedValue( String data ) {
		if ( keys.containsKey(data) ){
			changed = false;
			return keys.get(data);
		} else {
			String key = new String ( prefix+keys.size() );
			changed = true;
			keys.put( data, key );
			return key;
		}
	}

	public void reset() {
		keys = new HistoryHashMap<String, String>();
	}

	public String getTransformedValue(String value, String[] line) {
		return getTransformedValue(value);
	}

	public Map<String,String> getKeys(){
		return keys;
	}

	public void addKeys(Map<String, String> keys) {
		this.keys.putAll(keys);
	}

	public void back() throws UnsupportedOperationException {
		if ( changed ){
			keys.revertToOldState();
			changed = false;
		}
	}
	
	/**
	 * This method returns the original value
	 * @param transformedValue
	 */
	public String getOriginalValue( String transformedValue){
		for ( Entry<String,String> entry : keys.entrySet() ){
			if ( entry.getValue().equals(transformedValue) ){
				return entry.getKey();
			}
		}
		return null;
	}
}
