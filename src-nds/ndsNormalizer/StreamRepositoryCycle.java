package ndsNormalizer;

import tracePreprocessor.ValueTransformerRelativeToInstantiationAndLimited;



public class StreamRepositoryCycle implements ElementRepository {
	private ValueTransformerRelativeToInstantiationAndLimited keyDispenser;
	
	public StreamRepositoryCycle( int level ){
		 keyDispenser = new ValueTransformerRelativeToInstantiationAndLimited( "STREAM", level );
	}
	public LineData get(String value) {
		String key = keyDispenser.getTransformedValue(value);
		return new HandleData( key );
	}

	public void nextCycle() {
		keyDispenser.newLevel();
	}

}
