package it.unimib.disco.lta.alfa.dataTransformation;


public class ValueTransformerSame implements ValueTransformer {
	
	public String getTransformedValue(String data) {
		return data;
	}

	public void reset() {
		
	}
	
	public String getTransformedValue(String value, String[] line) {
		return getTransformedValue(value);
	}

	public void back() throws UnsupportedOperationException {
		//throw new UnsupportedOperationException();	
	}
	
	public String toString() {
		return "";
	}
}
