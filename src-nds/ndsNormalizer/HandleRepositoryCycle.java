package ndsNormalizer;


import tracePreprocessor.ValueTransformerRelativeToInstantiationAndLimited;

public class HandleRepositoryCycle implements ElementRepository<HandleData> {
	private ValueTransformerRelativeToInstantiationAndLimited keyDispenser;
	
	public HandleRepositoryCycle(int limit) {
		 keyDispenser = new ValueTransformerRelativeToInstantiationAndLimited( "_h", limit);
	}

	public HandleData get(String value) {
		String key = keyDispenser.getTransformedValue(value);
		return new HandleData( key );
	}

	public void nextCycle() {
		keyDispenser.newLevel();
	}

}
