package sjm.engine;

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
 * An Atom is a Structure that no terms.
 * <p>
 * For example, the following structure
 * <blockquote><pre>
 *     city(denver, 5280);
 * </pre></blockquote>
 *
 * is a fact that contains two atoms, "denver" and
 * 5280.
 *
 * @author Steven J. Metsker
 *
 * @version 1.0  
 */
public class Atom extends Fact implements ComparisonTerm {
/**
 * Contructs an atom from the specified object.
 * 
 * @param Object the functor for this atom
 */
public Atom(Object functor) {
	super(functor);
}
/**
 * Returns the functor if this structure.
 *
 * @return the functor if this structure
 */
public Object eval() {
	return functor;
}
}
