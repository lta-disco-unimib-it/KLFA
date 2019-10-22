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
 
package gui.lsystem;

import java.util.EventObject;

/**
 * This event is given to listeners of an L-system input pane who are
 * interested when the input system registers a change.
 * @see gui.lsystem.LSystemInputPane
 * @see gui.lsystem.LSystemInputListener
 * 
 * @author Thomas Finley
 */

public class LSystemInputEvent extends EventObject {
    /**
     * Instantiates a new <CODE>LSystemInputEvent</CODE>.
     * @param input the <CODE>LSystemInputPane</CODE> that was edited
     */
    public LSystemInputEvent(LSystemInputPane input) {
	super(input);
    }

    /**
     * Returns the <CODE>LSystemInputPane</CODE> that generated this event.
     * @return the <CODE>LSystemInputPane</CODE> that generated this event
     */
    public LSystemInputPane getInputPane() {
	return (LSystemInputPane) getSource();
    }
}
