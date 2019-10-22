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
 
package gui.editor;

import gui.viewer.AutomatonDrawer;
import gui.viewer.AutomatonPane;

import java.awt.event.MouseEvent;
    
/**
 * This extension of the arrow tool does not allow the editing of an
 * automaton, even for moving states, but does allow for the changing
 * of certain view options (displaying of labels and things of that
 * nature).
 * 
 * @author Thomas Finley
 */

public class ArrowDisplayOnlyTool extends ArrowNontransitionTool {
    /**
     * Instantiates a new <CODE>ArrowDisplayOnlyTool</CODE>.
     * @param view the view the automaton is drawn in
     * @param drawer the automaton drawer
     */
    public ArrowDisplayOnlyTool(AutomatonPane view, AutomatonDrawer drawer) {
	super(view, drawer);
    }

    /**
     * We don't want anything happening when the mouse is dragged.
     * This method simply returns without doing anything.
     * @param event the dragging mouse event
     */
    public void mouseDragged(MouseEvent event) {
	return;
    }
}
