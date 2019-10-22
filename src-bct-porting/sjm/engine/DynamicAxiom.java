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
 * A DynamicAxiom is an axiom (that is, either a fact
 * or a rule) that a structure can consult to prove itself. 
 *
 * @author Steven J. Metsker
 *
 * @version 1.0  
 */
public interface DynamicAxiom {
/**
 * Return the first structure of this dynamic axiom.
 *
 * @return the first structure of this dynamic axiom
 */
Structure head();
/**
 * Return the tail of this dynamic axiom.
 *
 * @return the tail of this dynamic axiom. This tail
 *         is the part of the dynamic that still needs to
 *         prove itself.
 */
DynamicRule resolvent();
}
