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

import automata.Automaton;

/**
 * This subclass of <CODE>Automaton</CODE> is specifically for a variable
 * dependency graph used in the transformation of grammars (e.g. removing
 * unit productions).
 *
 * @author Ryan Cavalcante
 */

public class VariableDependencyGraph extends Automaton {
    /**
     * Creates a variable dependency graph with no states and no
     * transitions.
     */
    public VariableDependencyGraph() {
	super();
    }

    /**
     * Returns the class of <CODE>Transition</CODE> this automaton
     * must accept.
     * @return the <CODE>Class</CODE> object for
     * <CODE>automata.vdg.VDGTransition</CODE>
     */
    protected Class getTransitionClass() {
	return automata.vdg.VDGTransition.class;
    }
}
