package sjm.engine;

import java.util.Enumeration;
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
 * A ProgramEnumerator returns the axioms of a program,
 * one at a time.
 *
 * @author Steven J. Metsker
 *
 * @version 1.0  
 */
public class ProgramEnumerator implements AxiomEnumeration {
	protected Enumeration e;
/**
 * Construct an enumeration of the given program.
 * 
 * @param Program the program to enumerate over
 * 
 */
public ProgramEnumerator(Program p) {
	e = p.axioms.elements();
}
/**
 * Tests if this enumeration contains more axioms.
 *
 * @return  <code>true</code> if the program this enumeration
 *          is constructed for contains more axioms, and 
 *          <code>false</code> otherwise.
 */
public boolean hasMoreAxioms() {
	return e.hasMoreElements();
}
/**
 * Returns the next axiom of this enumeration.
 *
 * @return the next axiom of this enumeration.
 */
public Axiom nextAxiom() {
	return (Axiom) e.nextElement();
}
}
