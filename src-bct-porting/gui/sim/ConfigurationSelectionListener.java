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

import java.util.EventListener;

/**
 * An interface that those interested in changes in selection of the
 * configuration pane should implement.
 * @see gui.sim.ConfigurationSelectionEvent
 * @see gui.sim.ConfigurationPane#addSelectionListener
 */

public interface ConfigurationSelectionListener extends EventListener {
    /**
     * Registers with the listener that an event has occurred.
     * @param event the event
     */
    public void configurationSelectionChange
	(ConfigurationSelectionEvent event);
}
