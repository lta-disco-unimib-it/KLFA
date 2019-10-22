package sjm.imperative;

import java.io.PrintWriter;

import sjm.engine.Term;
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
 * This command, when executed, prints out the
 * value of a term provided in the constructor.
 *
 * @author Steven J. Metsker
 *
 * @version 1.0
 */
public class PrintlnCommand extends Command {
	protected Term term;
	protected PrintWriter out;
/**
 * Construct a "print" command to print the supplied term.
 *
 * @param Term   the term to print
 */
public PrintlnCommand(Term term) {
	this(term, new PrintWriter(System.out));
}
/**
 * Construct a "print" command to print the supplied term,
 * printing to the supplied <code>PrintWriter</code> object. 
 *
 * @param Term   the term to print
 *
 * @param PrintWriter   where to print
 */
public PrintlnCommand(Term term, PrintWriter out) {
	this.term = term;
	this.out = out;
}
/**
 * Print the value of this object's term onto
 * the output writer.
 */
public void execute() {
	out.print(term.eval() + "\n");
	out.flush();
}
/**
 * Returns a string description of this print command.
 *
 * @return   a string description of this print command
 */
public String toString() {
	return "println(" + term + ")";
}
}
