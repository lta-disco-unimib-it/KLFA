package sjm.imperative;

import sjm.engine.Atom;
import sjm.engine.Evaluation;
import sjm.engine.Variable;
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
 
/**
 * This class holds an <code>sjm.engine.Evaluation</code> 
 * object, and executes it upon receiving an <code>execute
 * </code> command.
 * 
 * @author Steven J. Metsker
 *
 * @version 1.0
 */
public class AssignmentCommand extends Command {
	protected Evaluation evaluation;
/**
 * Construct an <code>Assignment</code> command from the
 * given <code>Evaluation</code>.
 */
public AssignmentCommand(Evaluation evaluation) {
	this.evaluation = evaluation; 
}
/**
 * Execute the assignment. Evaluate the evaluation's second
 * term, and retrieve the variable from the evaluation's 
 * first term. Unbind the variable, so that it can unify, 
 * and unify the variable with the second term.
 */
public void execute() {
	
	/* Note: we can only unbind the variable after 
	 * evaluating the first term. Not doing this
	 * would create a defect with i := i + 1 */

	Object o = evaluation.terms()[1].eval();
	Variable v = (Variable) evaluation.terms()[0];
	v.unbind();
	v.unify(new Atom(o));
}
/**
 * Returns a string representation of this command.
 *
 * @return   a string representation of this command
 */
public String toString() {
	return evaluation.toString();
}
}
