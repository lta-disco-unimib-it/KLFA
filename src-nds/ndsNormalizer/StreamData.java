package ndsNormalizer;


public class StreamData extends LineData {

	public StreamData(String data) {
		super(data);
	}

	@Override
	public String getName() {
		return "STREAM";
	}

}
