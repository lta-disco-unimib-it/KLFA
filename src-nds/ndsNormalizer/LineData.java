package ndsNormalizer;

public abstract class LineData {

	private String data;

	public LineData( String data ){
		this.data = data;
	}
	
	public String getData() {
		return data;
	}
	
	public String toString(){
		return getName()+data;
	}

	public abstract String getName();
	
}
