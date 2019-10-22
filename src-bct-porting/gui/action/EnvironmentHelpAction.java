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

import gui.environment.Environment;

import java.awt.event.ActionEvent;

/**
 * The <CODE>EnvironmentHelpAction</CODE> is an extension of the
 * <CODE>HelpAction</CODE> that, whenever an action is received,
 * determines what should be displayed based on the currently active
 * pane in the environment.  Basically, it simply calls
 * <CODE>HelpAction.displayHelp</CODE> on
 * <CODE>Environment.getActive</CODE>.
 * 
 * Any components in an environment that wish to have help should
 * register themselves, or preferably their <CODE>Class</CODE> objects
 * (so that it only happens once), with whatever particular webpage
 * they wish to display whenever help is activated.
 * 
 * @author Thomas Finley
 */

public class EnvironmentHelpAction extends HelpAction {
    /**
     * Instantiates an <CODE>EnvironmentHelpAction</CODE>.
     * @param environment the environment that this help action will
     * get the current panel from
     */
    public EnvironmentHelpAction(Environment environment) {
	this.environment = environment;
    }

    /**
     * Displays help according to the current display of the
     * automaton.
     * @param event the action event
     */
    public void actionPerformed(ActionEvent event) {
	displayHelp(environment.getActive());
    }

    /** The environment this help action is for. */
    private Environment environment;
}
