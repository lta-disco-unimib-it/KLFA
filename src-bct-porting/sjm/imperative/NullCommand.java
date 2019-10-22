package sjm.imperative;

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
 * This command does nothing, which can simplify coding
 * in some cases. For example, an "if" command with no 
 * given "else" uses a <code>NullCommand</code> for its else 
 * command.
 * 
 * @author Steven J. Metsker
 *
 * @version 1.0
 */
public class NullCommand extends Command {
/**
 * Does nothing.
 */
public void execute() {
}
/**
 * Returns a string description of this null command.
 *
 * @return   a string description of this null command
 */
public String toString() {
	return "NullCommand";
}
}
