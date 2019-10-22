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
 * automaton aside from the moving about of states.
 * 
 * @author Thomas Finley
 */

public class ArrowNontransitionTool extends ArrowTool {
    /**
     * Instantiates a new <CODE>ArrowNontransitionTool</CODE>.
     * @param view the view the automaton is drawn in
     * @param drawer the automaton drawer
     */
    public ArrowNontransitionTool(AutomatonPane view, AutomatonDrawer drawer) {
	super(view, drawer);
    }
    
    /**
     * On a mouse click, this simply returns, 
     * @param event the mouse event
     */
    public void mouseClicked(MouseEvent event) {
	return;
	/*if (event.getClickCount() == 2) return;
	  super.mouseClicked(event);*/
    }
    
    protected boolean shouldShowStatePopup() {
	return false;
    }
}
