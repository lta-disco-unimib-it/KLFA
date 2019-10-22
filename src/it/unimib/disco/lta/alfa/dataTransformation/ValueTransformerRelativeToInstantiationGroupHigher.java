package it.unimib.disco.lta.alfa.dataTransformation;

import java.util.ArrayList;
import java.util.List;

public class ValueTransformerRelativeToInstantiationGroupHigher implements ValueTransformer{

	private int limit;



	public ValueTransformerRelativeToInstantiationGroupHigher(String prefix,int limit) {
		this.prefix = prefix;
		this.limit = -1*Math.abs(limit);
		// TODO Auto-generated constructor stub
	}

	private List<String> keys = new ArrayList<String>(); 
	private String prefix;
	

	
	public String getTransformedValue(String data) {
		if ( keys.contains(data) ){
			int index = keys.indexOf(data);
			int relative = (index-keys.size());
			if ( relative < limit )
				return prefix+"<"+limit;
			return prefix+relative;
		}
		keys.add(data);
		return prefix+"0";
	}



	public void reset() {
		keys = new ArrayList<String>();
	}
	
	public String getTransformedValue(String value, String[] line) {
		return getTransformedValue(value);
	}
	
	public void back() throws UnsupportedOperationException {
		throw new UnsupportedOperationException();	
	}
}
