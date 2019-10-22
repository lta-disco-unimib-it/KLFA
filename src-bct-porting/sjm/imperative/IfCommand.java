package sjm.imperative;

import sjm.engine.BooleanTerm;
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
 * This command mimics a normal "if" statement, such as:
 *
 * <blockquote><pre>
 *
 *     if (x > 7) {
 *         // body to execute if condition is true
 *     } else {
 *         // body to execute if condition is false
 *     }
 * </pre></blockquote>
 *
 * @author Steven J. Metsker
 *
 * @version 1.0
 */
public class IfCommand extends Command {
	protected BooleanTerm condition;
	protected Command ifCommand;
	protected Command elseCommand;
/**
 * Construct an "if" command from the given condition and
 * command.
 *
 * @param condition   the condition to check
 *
 * @param ifCommand   the command to execute if the 
 *                    condition evaluates to true
 */
public IfCommand(BooleanTerm condition, Command ifCommand) {
	this.condition = condition;
	this.ifCommand = ifCommand;
	this.elseCommand = new NullCommand();
}
/**
 * Construct an "if" command from the given condition and
 * command.
 *
 * @param condition   the condition to check
 *
 * @param ifCommand   the command to execute if the 
 *                    condition evaluates to true
 *
 * @param elseCommand   the command to execute if the 
 *                      condition evaluates to false
 */
public IfCommand(
	BooleanTerm condition, Command ifCommand, 
	Command elseCommand) {
		
	this.condition = condition;
	this.ifCommand = ifCommand;
	this.elseCommand = elseCommand;
}
/**
 * Execute this "if" command. Evaluate the condition. If it
 * is true, execute this object's <code>ifCommand</code>.
 * Otherwise, execute the <code>elseCommand</code>, which 
 * may be a <code>NullCommand</code> object.
 */
public void execute() {
	Boolean b = (Boolean) condition.eval();
	if (b.booleanValue()) {
		ifCommand.execute();
	} else {
		elseCommand.execute();
	}
}
/**
 * Returns a string description of this if command.
 *
 * @return   a string description of this if command
 */
public String toString() {
	return 
		"if"   + "(" + condition + ")" 
		       + "{" + ifCommand + "}" +
		"else" + "{" + elseCommand + "}";
}
}
