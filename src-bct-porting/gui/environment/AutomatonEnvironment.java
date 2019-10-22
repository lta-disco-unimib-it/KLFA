/* -- JFLAP 4.0 --
 *
 * Copyright information:
 *
 * Susan H. Rodger, Thomas Finley
 * Computer Science Department
 * Duke University
 * April 24, 2003
 * Supported by National Science Foundation DUE-9752583.
 *
 * Copyright (c) 2003
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms are permitted
 * provided that the above copyright notice and this paragraph are
 * duplicated in all such forms and that any documentation,
 * advertising materials, and other materials related to such
 * distribution and use acknowledge that the software was developed
 * by the author.  The name of the author may not be used to
 * endorse or promote products derived from this software without
 * specific prior written permission.
 * THIS SOFTWARE IS PROVIDED ``AS IS'' AND WITHOUT ANY EXPRESS OR
 * IMPLIED WARRANTIES, INCLUDING, WITHOUT LIMITATION, THE IMPLIED
 * WARRANTIES OF MERCHANTIBILITY AND FITNESS FOR A PARTICULAR PURPOSE.
 */
 
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
