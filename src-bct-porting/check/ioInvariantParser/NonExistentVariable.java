package check.ioInvariantParser;

public class NonExistentVariable {
	
	/**
	 * A non existent variable cannot be equal to another variable. If a variable does not exists
	 * it is also be not equals to another non existent one.
	 * 
	 * @param Object o the object to check if equal to
	 * @return false
	 * 
	 */
	public boolean equals ( Object o ){
		return false;
	}

	
}
