package regressionTestManager.ioInvariantParser;

public class Variable {
	private String name;
	private String programPoint;
	
	/**
	 * Create a new Variable object containing information on a variable name.
	 * The program point must be in complete form: 
	 * 	package.Class.methdo(paramaters):::ENTER
	 * 	or
	 * 	package.Class.methdo(paramaters):::EXIT1
	 * 
	 * Variable name must be like:
	 * 	parameter[num].inspectors
	 * 
	 * @param programPoint	program point the variable refers to
	 * @param variableName	name of the variable
	 */
	public Variable ( String programPoint, String variableName ){
		this.programPoint = programPoint;
		this.name = variableName;
	}

	public String getName() {
		return name;
	}
	
	public String toString(){
		return programPoint+"."+name;
	}
	
	public boolean equals( Object o ){
		if ( o == this )
			return true;
		if ( o instanceof Variable ){
			Variable rhs = (Variable) o;
			return ( programPoint.equals( rhs.programPoint ) && 
					name.equals(rhs.name) );
		}
		return false;
	}

	public String getProgramPoint() {
		return programPoint;
	}

	/**
	 * Return if a variable refers to an array, A variable name refers to an array when ends with "]"
	 * 
	 * @return
	 */
	public boolean isArray() {
		return name.endsWith("]");
	}

	public void setProgramPoint(String programPoint) {
		this.programPoint = programPoint;
	}
	
}
