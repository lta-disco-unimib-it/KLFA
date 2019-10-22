package it.unimib.disco.lta.alfa.dataTransformation;

public class ValueTransformerHash implements ValueTransformer {

	public String getTransformedValue(String value) {
		return String.valueOf(value.hashCode());
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
