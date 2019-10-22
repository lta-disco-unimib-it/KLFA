package sjm.imperative;

import java.util.Enumeration;
import java.util.Vector;
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
 * This class contains a sequence of other commands.
 * 
 * @author Steven J. Metsker
 *
 * @version 1.0
 */
public class CommandSequence extends Command {
	protected Vector commands;
/**
 * Add a command to the sequence of commands to which this
 * object will cascade an <code>execute</code> command.
 *
 * @param Command a command to add to this command sequence
 */
public void addCommand(Command c) {
	commands().addElement(c);
}
/**
 * Lazy-initialize the <code>commands</code> vector.
 */
protected Vector commands() {
	if (commands == null) {
		commands = new Vector();
	}
	return commands;
}
/**
 * Ask each command in the sequence to <code>execute</code>.
 */
public void execute() {
	Enumeration e = commands().elements();
	while (e.hasMoreElements()) {
		Thread.yield();
		((Command) e.nextElement()).execute();
	}
}
/**
 * Returns a string description of this command sequence.
 *
 * @return   a string description of this command sequence
 */
public String toString() {
	StringBuffer buf = new StringBuffer();
	boolean needLine = false;
	Enumeration e = commands().elements();
	while (e.hasMoreElements()) {
		if (needLine) {
			buf.append("\n");
		}
		buf.append(e.nextElement());
		needLine = true;
	}
	return buf.toString();
}
}
