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
 
package gui.action;

import javax.swing.AbstractAction;
import javax.swing.Icon;

/**
 * The <CODE>RestrictedAction</CODE> is the general action that
 * various controllers for operators on objects should subclass.  In
 * addition to the usual <CODE>Action</CODE> nicities, the
 * <CODE>RestrictedAction</CODE> also provides facilities for
 * determining which types of objects it may be applied to.
 * 
 * @author Thomas Finley
 */

public abstract class RestrictedAction extends AbstractAction {
    /**
     * Instantiates a new <CODE>RestrictedAction</CODE>.
     * @param string a string description
     * @param icon the optional icon, or <CODE>null</CODE> if there is
     * to be no icon associated with this action
     */
    public RestrictedAction(String string, Icon icon) {
	super(string, icon);
    }

    /**
     * Given an object, determine if this action is able to be applied
     * to that object based on its class.  By default, this method
     * simply returns <CODE>true</CODE> to indicate that any sort of
     * object is acceptable.
     * @param object the object to test for "applicability"
     * @return <CODE>true</CODE> if this action should be available to
     * an object of this type, <CODE>false</CODE> otherwise.
     */
    public static boolean isApplicable(Object object) {
	return true;
    }

    /** This is the main "menu mask" for items, for those that wish to
     * have accelerators for their items and want the "traditional"
     * platform specific enabler (e.g., command on Macintosh, control
     * on Wintel, and meta on *n[iu]x). */
    protected static int MAIN_MENU_MASK = MenuConstants.getMainMenuMask();
}
