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
 
package gui.lsystem;

import java.util.EventListener;

/**
 * An interface that those interested in user change of an L-system
 * should listen to.
 * @see gui.lsystem.LSystemInputEvent
 * @see gui.lsystem.LSystemInputPane
 */

public interface LSystemInputListener extends EventListener {
    /**
     * Registers with the listener that the user has changed the
     * L-system.
     * @param event the event
     */
    public void lSystemChanged(LSystemInputEvent event);
}
