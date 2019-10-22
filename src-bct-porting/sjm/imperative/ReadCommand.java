package sjm.imperative;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

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
 * This command, when executed, reads in a string and
 * assigns it to a supplied variable.
 *
 * @author Steven J. Metsker
 *
 * @version 1.0
 */
public class ReadCommand extends Command {
	protected Variable variable;
	protected BufferedReader reader;
/**
 * Construct a "read" command to read a value, assigning
 * it to the supplied variable. 
 *
 * This constructor sets the command to read from <code>
 * System.in</code>.
 *
 * @param Variable   the variable to assign to
 */
public ReadCommand(Variable variable) {
	this(variable, System.in);
}
/**
 * Construct a "read" command to read a value from the
 * supplied reader, assigning the value to the supplied 
 * variable.
 *
 * @param Variable   the variable to assign to
 *
 * @param BufferedReader   where to read from
 */
public ReadCommand(Variable variable, BufferedReader reader) {
	this.variable = variable;
	this.reader = reader;
}
/**
 * Construct a "read" command to read a value from the
 * supplied input stream, assigning the value to the supplied 
 * variable.
 *
 * @param Variable   the variable to assign to
 *
 * @param InputStream   where to read from
 */
public ReadCommand(Variable variable, InputStream in) {
	this(
		variable, 
		new BufferedReader(new InputStreamReader(in)));
}
/**
 * Read in a string from this object's input reader, and
 * assign the string to this object's variable.
 */
public void execute() {
	String s;
	try {
		s = reader.readLine();
	} catch (IOException e) {
		s = "";
	}
	Evaluation e = new Evaluation(variable, new Atom(s));
	AssignmentCommand ac = new AssignmentCommand(e);
	ac.execute();
}
/**
 * Returns a string description of this read command.
 *
 * @return   a string description of this read command
 */
public String toString() {
	return "read(" + variable.name + ")";
}
}
