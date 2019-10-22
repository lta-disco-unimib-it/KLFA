package dfmaker.core;

public class ProgramPoint {
	public static int ENTRY_POINT = 0;
	public static int EXIT_POINT = 1;
	public static int OBJECT_POINT = 1;
	
	private int programPointType;
	private String programPointValue = null;
	
	/**
	 * @param programPointType
	 */
	ProgramPoint(int programPointType, String programPointValue) {
		this.programPointType = programPointType;
		this.programPointValue = programPointValue; 
	}
	
	/**
	 * @return Program point type.
	 */
	public int getProgramPointType() {
		return this.programPointType;
	}
	
	/**
	 * @return Program point value
	 */
	public String getProgramPointValue() {
		return this.programPointValue;
	}

}
