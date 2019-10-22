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
 * An AxiomSource is a provider of axioms.
 * <p>
 * Within the package sjm.engine, the only provider of
 * axioms is Program. The AxiomSource interface
 * allows other types of object to provide axioms,
 * specifically objects that act as databases and
 * provide lots of facts.
 *
 * @author Steven J. Metsker
 *
 * @version 1.0  
 */
public interface AxiomSource {
/**
 * Returns all the axioms from a source.
 *
 * @return all the axioms from a source
 */
AxiomEnumeration axioms();
/**
 * Returns an enumeration of axioms. The parameter
 * specifies the structure that is trying to prove itself.
 * The implementor of this method can ignore this, or
 * use it as an index.
 * <p>
 * An axiom has a chance of serving to prove the
 * structure only if the axiom begins with a structure
 * that matches the input structure with regard to its
 * functor and its number or terms (or "arity"). An implementor
 * can put this point to good purpose, only returning
 * axioms that have some chance of providing a proof.
 *
 * @param Structure the structure that is trying to prove
 *                  itself
 *
 * @return a collection of axioms
 */
AxiomEnumeration axioms(Structure s);
}
