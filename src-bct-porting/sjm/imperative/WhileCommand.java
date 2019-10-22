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
 * This command mimics a normal "while" loop, executing
 * a command in a loop, so long as some condition holds
 * true.
 * 
 * @author Steven J. Metsker
 *
 * @version 1.0
 */
public class WhileCommand extends Command {
	protected BooleanTerm condition;
	protected Command command;
/**
 * Construct a "while" command from the given condition
 * and command.
 *
 * @param condition   the condition to check each time
 *                    before executing the body
 *
 * @param command   the command to repeatedly execute
 */
public WhileCommand(BooleanTerm condition, Command command) {
	this.condition = condition;
	this.command = command;
}
/**
 * Execute this "while" command. This means repeatedly
 * checking the condition, and executing command from
 * this object's command, so long as the condition is true.
 */
public void execute() {
	while (((Boolean) condition.eval()).booleanValue()) {
		command.execute();
	}
}
/**
 * Returns a string description of this while command.
 *
 * @return   a string description of this while command
 */
public String toString() {
	return "while" + 
		"(" + condition + ")" + 
		"{" + command + "}";
}
}
