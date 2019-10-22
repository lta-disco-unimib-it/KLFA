package it.unimib.disco.lta.alfa.dataTransformation;

public class ValueTransformerReplace implements ValueTransformer {

	private String replacement;

	public ValueTransformerReplace( String stringToReplace ){
		this.replacement = stringToReplace;
	}
	
	public String getTransformedValue(String value) {
		// TODO Auto-generated method stub
		return replacement;
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
