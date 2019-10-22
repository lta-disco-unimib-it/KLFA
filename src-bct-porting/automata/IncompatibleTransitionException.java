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
 
package automata;

/**
 * This class is an exception that is thrown in the event an
 * incompatible <CODE>Transition</CODE> object is assigned to an
 * automaton.
 * @see automata.Automaton
 * @see automata.Transition
 * @see automata.Automaton#getTransitionClass
 * @see automata.Automaton#addTransition
 * @author Thomas Finley
 */

public class IncompatibleTransitionException extends RuntimeException {
    
}
