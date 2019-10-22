package it.unimib.disco.lta.alfa.dataTransformation;


public class ValueTransformerNone implements ValueTransformer {

	public String getTransformedValue(String data) {
		return "";
	}

	public void reset() {
		
	}
	
	public String getTransformedValue(String value, String[] line) {
		return getTransformedValue(value);
	}

	public void back() throws UnsupportedOperationException {
		throw new UnsupportedOperationException();	
	}
}
