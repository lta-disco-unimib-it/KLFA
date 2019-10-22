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
 
package automata.vdg;

import automata.State;
import automata.Transition;

/**
 * A <CODE>VDGTransition</CODE> is a <CODE>Transition</CODE> object 
 * used by Variable Dependecy Graphs (VDGs).  They have no labels.
 *
 * @author Ryan Cavalcante
 */

public class VDGTransition extends Transition {
    /**
     * Instantiates a new <CODE>VDGTransition</CODE> object.
     * @param from the state this transition comes from.
     * @param to the state this transition goes to.
     */
    public VDGTransition(State from, State to) {
	super(from,to);
    }

    /**
     * Produces a copy of this transition with new from and to states.
     * @param from the new from state
     * @param to the new to state
     * @return a copy of this transition with the new states
     */
    public Transition copy(State from, State to) {
	return new VDGTransition(from,to);
    }
    
    /**
     * Returns a string representation of this object.  This is the
     * same as the string representation for a regular transition
     * object.
     * @see automata.Transition#toString
     * @return a string representation of this object
     */
    public String toString() {
	return super.toString();
    }
}
