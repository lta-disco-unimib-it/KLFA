package jvmMon.preprocessors;

import it.unimib.disco.lta.alfa.dataTransformation.ValueTransformer;


public class ValueTransformerExcConcurrent implements ValueTransformer {

	public String getTransformedValue(String data) {
		if ( data != null ){
			if ( data.contains("Interrupted") || 
					data.contains("Thread") )
				return data;
		}
		return null;
	}

	public void reset() {
		
	}

	public String getTransformedValue(String value, String[] line) {
		// TODO Auto-generated method stub
		return getTransformedValue(value);
	}

	public void back() throws UnsupportedOperationException {
		throw new UnsupportedOperationException();
	}

}
