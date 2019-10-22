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
 
package gui.editor;

import gui.viewer.AutomatonPane;
import automata.State;
import automata.Transition;
import automata.vdg.VDGTransition;

/**
 * This is a transition creator for variable dependency graphs.
 * 
 * @author Thomas Finley
 */

public class VDGTransitionCreator extends TransitionCreator {
    /**
     * Instantiates a transition creator.
     * @param parent the parent object that any dialogs or windows
     * brought up by this creator should be the child of
     */
    public VDGTransitionCreator(AutomatonPane parent) {
	super(parent);
    }

    /**
     * Creates a transition with user interaction and returns it.
     * @return returns the variable dependency transition
     */
    public Transition createTransition(State from, State to) {
	VDGTransition t = new VDGTransition(from, to);
	getParent().getDrawer().getAutomaton().addTransition(t);
	return null;
    }

    /**
     * Edits a given transition.  Ideally this should use the same
     * interface as that given by <CODE>createTransition</CODE>.
     * @param transition the transition to edit
     * @return <CODE>false</CODE> if the user decided to not edit a
     * transition, <CODE>true</CODE> if the edit was "approved"
     */
    public boolean editTransition(Transition transition) {
	return false;
    }
}
