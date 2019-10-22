package it.unimib.disco.lta.alfa.dataTransformation;

/**
 * A ValueTransformer is the core element of the data transformation phase.
 * It receives a value and returns a symbolic value according to the tranformation 
 * rule implemented.
 *   
 * @author Fabrizio Pastore fabrizio.pastore AT gmail.com
 *
 */
public interface ValueTransformer {
	
	/**
	 * This method accept a value as input and returns a value transformed according to the
	 * trasformation rule implemented.
	 * 
	 * @param value
	 * @return
	 */
	public String getTransformedValue( String value );

	/**
	 * This method accept a value and a splitted line as input and returns a value transformed according to the
	 * trasformation rule implemented.
	 * 
	 * @param value
	 * @return
	 */
	public String getTransformedValue( String value, String[] line );
	
	/**
	 * This method reset the value transformer state as if it was just created
	 */
	public void reset();
	
	/**
	 * This method reset the value transformer state as if the last operation was not performed
	 */
	public void back() throws UnsupportedOperationException;

	
	
	
}
