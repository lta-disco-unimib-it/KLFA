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
 * In practice, an Axiom is either a fact or a rule, the two 
 * types of objects that can appear in a program. More 
 * precisely, an Axiom has a head structure with which a 
 * consulting structure can unify, and an Axiom can produce 
 * a ProvableAxiom. 
 * <p>
 * Facts are simply true, and return themselves as 
 * DynamicAxioms. To prove itself, a Rule needs to 
 * create a DynamicAxiom that can attempt to prove 
 * itself against an axiom source.  
 *
 * @author Steven J. Metsker
 *
 * @version 1.0  
 */
public interface Axiom {
/**
 * Return an axiom that a consulting structure can use
 * to prove itself.
 *
 * @return an axiom that a consulting structure can use
 *         to prove itself.
 */
DynamicAxiom dynamicAxiom(AxiomSource as);
/**
 * Return the first structure of this axiom.
 *
 * @return the first structure of this axiom
 */
Structure head();
}
