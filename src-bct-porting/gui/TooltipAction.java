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
 
package gui;

import javax.swing.AbstractAction;

/**
 * This is a subclass of abstract action that allows one to set
 * the tooltip text from the constructor.
 * 
 * @author Thomas Finley
 */

public abstract class TooltipAction extends AbstractAction {
    public TooltipAction(String name, String tooltip) {
	super(name);
	putValue(SHORT_DESCRIPTION, tooltip);
    }

    /**
     * Sets the tool tip description.
     * @param tip the new tool tip
     */
    public void setTip(String tip) {
	if (tip==null) {
	    putValue(SHORT_DESCRIPTION, tip);
	    return;
	}
	if (tip.equals(getValue(SHORT_DESCRIPTION))) return;
	putValue(SHORT_DESCRIPTION, tip);
    }
    
    /**
     * Gets the tool tip description.
     * @return the tool tip for this action
     */
    public String getTip() {
	return (String) getValue(SHORT_DESCRIPTION);
    }
}
