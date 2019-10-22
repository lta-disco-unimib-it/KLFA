package conf;

public class Daikon {

	private static double confidenceLevel;
	private static String operatorFile;
	
	public static double getConfidenceLevel() {
		return confidenceLevel;
	}
	public static void setConfidenceLevel(double confidenceLevel) {
		Daikon.confidenceLevel = confidenceLevel;
	}
	public static String getOperatorFile() {
		return operatorFile;
	}
	public static void setOperatorFile(String operatorFile) {
		Daikon.operatorFile = operatorFile;
	}
}
