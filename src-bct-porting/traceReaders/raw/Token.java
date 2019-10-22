package traceReaders.raw;

/**
 * class used to normalized interaction trace
 * 
 *
 */

public class Token {
	
	private String methodSignature = "";
	private int id;
	
	//Token for DB
	public Token (int id, String methodSignature) {
		this.id = id;
		this.methodSignature = methodSignature;
	}
	
	//Token for file
	public Token (String methodSignature) {
		this.methodSignature = methodSignature;
	}
	
	public String getMethodName() {
		return methodSignature;
	}
	
	public int getId() {
		return id;
	}

}
