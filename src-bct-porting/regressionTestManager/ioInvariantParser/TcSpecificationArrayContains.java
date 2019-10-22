package regressionTestManager.ioInvariantParser;

import regressionTestManager.tcSpecifications.TcSpecification;

/**
 * This class represent a case in wich an array must contain the given element at least one time
 * 
 * It is used for example to expand one of with array ( parameter[0].myArray[] one of { 2, 3, 4}.
 * In this case in fact we want to rerun test for all the possible values that array elements can assume.
 * 
 * @author Fabrizio Pastore fabrizio.pastore AT gmail.com
 *
 */
public class TcSpecificationArrayContains implements TcSpecification {
	private Variable array;
	private Object element;
	
	/**
	 * Constructot
	 * 
	 * @param array	the array variable
	 * @param element	the element that the variable must contain
	 * 
	 */
	public TcSpecificationArrayContains(Variable array, Object element) {
		this.array = array;
		this.element = element;
	}
	
}
