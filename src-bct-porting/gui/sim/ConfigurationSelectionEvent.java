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
