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
