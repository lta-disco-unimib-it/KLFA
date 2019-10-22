package sjm.engine;

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
 * A Program is a collection of rules and facts that together
 * form a logical model.
 *
 * @author Steven J. Metsker
 *
 * @version 1.0  
 */

public class Program implements AxiomSource {
	protected Vector axioms = new Vector();
/**
 * Create a new program with no axioms.
 */
public Program() {
}
/**
 * Create a new program with the given axioms.
 */
public Program(Axiom[] axioms) {
	for (int i = 0; i < axioms.length; i++) {
		addAxiom(axioms[i]);
	}
}
/**
 * Adds an axiom to this program.
 *
 * @param Axiom the axiom to add.
 */
public void addAxiom(Axiom a) {
	axioms.addElement(a);
}
/**
 * Appends all the axioms of another source to this one.
 *
 * @param   program   the source of the new axioms
 */
public void append(AxiomSource as) {
	AxiomEnumeration e = as.axioms();
	while (e.hasMoreAxioms()) {
		addAxiom(e.nextAxiom());
	}	
}
/**
 * Returns an enumeration of the axioms in this program.
 * 
 * @return an enumeration of the axioms in this program.
 */
public AxiomEnumeration axioms() {
	return new ProgramEnumerator(this);
}
/**
 * Returns an enumeration of the axioms in this program.
 * 
 * @return an enumeration of the axioms in this program.
 */
public AxiomEnumeration axioms(Structure ignored) {
	return axioms();
}
/**
 * Returns a string representation of this program. 
 *
 * @return a string representation of this program.
 */
public String toString() {
	StringBuffer buf = new StringBuffer();
	boolean haveShownALine = false;
	Enumeration e = axioms.elements();
	while (e.hasMoreElements()) {
		if (haveShownALine) {
			buf.append("\n");
		}
		buf.append(e.nextElement().toString());
		buf.append(";");
		haveShownALine = true;
	}	
	return buf.toString();
}
}
