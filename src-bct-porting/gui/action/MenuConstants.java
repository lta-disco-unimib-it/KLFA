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
