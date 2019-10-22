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

package automata.fsa;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;

import automata.Automaton;

/**
 * This subclass of <CODE>Automaton</CODE> is specifically for a definition of
 * a regular Finite State Automaton.
 * 
 * @author Thomas Finley
 */

public class FiniteStateAutomaton extends Automaton {
	
	private final static long serialVersionUID = 5861270179276660246L;
	
	/**
	 * Creates a finite state automaton with no states and no transitions.
	 */
	public FiniteStateAutomaton() {
		super();
	}

	/**
	 * Returns the class of <CODE>Transition</CODE> this automaton must
	 * accept.
	 * 
	 * @return the <CODE>Class</CODE> object for <CODE>
	 *         automata.fsa.FSATransition</CODE>
	 */
	protected Class getTransitionClass() {
		return automata.fsa.FSATransition.class;
	}

	public static FiniteStateAutomaton readSerializedFSA(String fileName)
			throws ClassNotFoundException, FileNotFoundException, IOException {
		File toBeOpened = new File(fileName);
		if (!toBeOpened.exists()) {
			throw new FileNotFoundException();
		}

		ObjectInputStream in;
		FiniteStateAutomaton fsa = null;
		in = new ObjectInputStream(new FileInputStream(toBeOpened
				.getAbsolutePath()));
		fsa = (FiniteStateAutomaton) in.readObject();

		return fsa;
	}

}