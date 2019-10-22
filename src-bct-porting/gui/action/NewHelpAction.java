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

import java.awt.event.ActionEvent;

/**
 * The <CODE>NewHelpAction</CODE> is an extension of the
 * <CODE>HelpAction</CODE> that, whenever an action is received, puts
 * up the help code for the {@link gui.action.NewAction}.
 * 
 * @author Thomas Finley
 */

public class NewHelpAction extends HelpAction {
    /**
     * Instantiates an <CODE>EnvironmentHelpAction</CODE>.
     * @param environment the environment that this help action will
     * get the current panel from
     */
    public NewHelpAction() {
	
    }

    /**
     * Displays help according to the current display of the
     * automaton.
     * @param event the action event
     */
    public void actionPerformed(ActionEvent event) {
	displayHelp(NewAction.class);
    }
}
