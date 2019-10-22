/*******************************************************************************
 *    Copyright 2019 Fabrizio Pastore, Leonardo Mariani
 *   
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 *******************************************************************************/
package theoremProver;

public class SimplifyResult {
	private String programOutput = null;
	private int result;
	
	/**
	 * Result value if the input file does not exist
	 */
	public static final int UNABLE_TO_OPEN_FILE = -3;
	/**
	 * Some syntax error in the input file
	 */
	public static final int SYNTAX_ERROR = -2;
	/**
	 * Generic bad input
	 */
	public static final int BAD_INPUT = -1;
	/**
	 * The formula is successfully executed and its result is invalid
	 */
	public static final int INVALID = 0;
	/**
	 * The formula is successfully executed and its result is valid
	 */
	public static final int VALID = 1;
		
	/**
	 * Contains the result of a single formula evaluetion. A formula result has
	 * the following form:
	 * 
	 * Counterexample:
	 *   context:
	 *     (AND
	 *       (EQ (select value this) -9)
	 *     )
	 *     
	 * 1: Invalid.
	 * 
	 * @param programOutput contains the contuer example, e.g.,
	 * Counterexample: context: (AND (EQ (select value this) -9) )
	 * @param result contains the formula evaluation result, e.g.,
	 * -  1 = Valid
	 * -  0 = Invalid
	 * - -1 = Bad input
	 * - -2 = Syntax error
	 * - -3 = Unable to open file
	 */
	public SimplifyResult(String programOutput, int result) {
		super();
		this.programOutput = programOutput;
		this.result = result;
	}

	/**
	 * The literal output returned by the Simplify execution
	 * @return
	 */
	public String getProgramOutput() {
		return programOutput;
	}
	
	/**
	 * The numeric coded output returned by the Simplify execution
	 * @return
	 */
	public int getResult() {
		return result;
	}
}
