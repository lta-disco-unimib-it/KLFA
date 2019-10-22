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
 
package gui.environment;

import automata.Automaton;
import automata.event.AutomataStateEvent;
import automata.event.AutomataStateListener;
import automata.event.AutomataTransitionEvent;
import automata.event.AutomataTransitionListener;

public class AutomatonEnvironment extends Environment {
    /**
     * Instantiates an <CODE>AutomatonEnvironment</CODE> for the given
     * automaton.  By default this method will set up an environment
     * with an editor pane for this automaton.
     * @param automaton the automaton to set up an environment for
     * @see gui.editor.EditorPane
     */
    public AutomatonEnvironment(Automaton automaton) {
	super(automaton);
	Listener listener = new Listener();
	automaton.addStateListener(listener);
	automaton.addTransitionListener(listener);
    }

    /**
     * Returns the automaton that this environment manages.
     * @return the automaton that this environment manages
     */
    public Automaton getAutomaton() {
	return (Automaton) super.getObject();
    }

    /**
     * The transition and state listener for an automaton detects if
     * there are changes in the environment, and if so, sets the dirty
     * bit.
     */
    private class Listener
	implements AutomataStateListener, AutomataTransitionListener {
	public void automataTransitionChange(AutomataTransitionEvent e) {
	    setDirty();
	}
	public void automataStateChange(AutomataStateEvent e) {
	    setDirty();
	}
    }
}
