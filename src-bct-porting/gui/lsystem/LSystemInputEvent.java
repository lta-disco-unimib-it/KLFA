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
