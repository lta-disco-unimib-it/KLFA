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
 
package gui.sim;

import java.util.EventObject;

/**
 * The <CODE>ConfigurationSelectionEvent</CODE> is an event thrown by
 * a <CODE>ConfigurationPane</CODE> whenever a transition is selected
 * or deselected in that pane.  Because many transitions may be
 * selected or deselected at once, this merely registers that a change
 * has happened without registering exactly which have been changed.
 * 
 * @see gui.sim.ConfigurationSelectionListener
 * @see gui.sim.ConfigurationPane
 * 
 * @author Thomas Finley
 */

public class ConfigurationSelectionEvent extends EventObject {
    /**
     * Instantiates a new <CODE>ConfigurationSelectionEvent</CODE> object.
     * @param configurationPane the configuration pane where the
     * selection state has changed
     */
    public ConfigurationSelectionEvent(ConfigurationPane configurationPane) {
	super(configurationPane);
    }

    /**
     * Returns the configuration pane that generated this event.
     * @return the configuration pane that generated this event
     */
    public ConfigurationPane getPane() {
	return (ConfigurationPane) getSource();
    }
}
