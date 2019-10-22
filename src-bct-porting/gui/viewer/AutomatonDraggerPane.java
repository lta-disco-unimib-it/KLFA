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
 
package gui.viewer;

import gui.editor.ArrowNontransitionTool;
import automata.Automaton;

/**
 * This is the same as an automaton pane, except that it allows the
 * user to drag states around.  This is used particularly in
 * situations where the placement of states may not be to a users
 * liking, i.e. displaying of DFAs with a random placement of some
 * states.
 * 
 * @author Thomas Finley
 */

public class AutomatonDraggerPane extends AutomatonPane {
    /**
     * Instantiates the automaton dragger pane.
     * @param drawer the automaton drawer
     */
    public AutomatonDraggerPane(AutomatonDrawer drawer) {
	super(drawer);
	init();
    }

    /**
     * Instantiates the automaton dragger pane.
     * @param drawer the automaton drawer
     * @param boolean whether or not to adapt the size of the view
     */
    public AutomatonDraggerPane(AutomatonDrawer drawer, boolean adapt) {
	super(drawer, adapt);
	init();
    }

    /**
     * Instantiates the automaton dragger pane.
     * @param automaton the automaton to draw
     */
    public AutomatonDraggerPane(Automaton automaton) {
	super(automaton);
	init();
    }
    
    /**
     * Adds what allows dragging.
     */
    private void init() {
	ArrowNontransitionTool t =
	    new ArrowNontransitionTool(this, getDrawer());
	addMouseListener(t);
	addMouseMotionListener(t);
    }
}
