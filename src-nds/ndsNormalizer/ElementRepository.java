package ndsNormalizer;

public interface ElementRepository<T extends LineData> {
	
	public void nextCycle();
	
	public T get(String value);

}
