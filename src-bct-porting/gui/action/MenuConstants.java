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

import java.awt.event.InputEvent;

/**
 * The <TT>MenuConstants</TT> class is a simple class for retrieving
 * information universally important to menu items.
 * 
 * @author Thomas Finley
 */

public class MenuConstants {
    /**
     * This is a singleton class; the information present is universal.
     */
    private MenuConstants() { }

    /**
     * Returns the main mask for menu items.  The main mask is the
     * mask of keys that are held down to typically invoke a menu
     * item.  This varies from platform to platform.  On Windows it's
     * the control key, and on everything else (presumably either Mac
     * OS or some other Unix based system) it's meta (on the MacOS
     * this is interpreted as the command key).
     * @return the main modifier for menu items
     */
    public static int getMainMenuMask() {
	return MAIN_MENU_MASK;
    }

    /**
     * Initializes the value for the main menu mask.
     */
    private static void initMainMenuMask() {
	String s = System.getProperty("os.name");
	if ((s.lastIndexOf("Windows") != -1) ||
	    (s.lastIndexOf("windows") != -1))
	    MAIN_MENU_MASK = InputEvent.CTRL_MASK;
	else
	    MAIN_MENU_MASK = InputEvent.META_MASK;
    }

    /** The main mask for keystrokes in a menu. */
    private static int MAIN_MENU_MASK;

    /**
     * The static initializer initializes the information present in
     * this class.
     */
    static {
	initMainMenuMask();
    }
}
