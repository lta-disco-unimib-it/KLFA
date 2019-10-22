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

import gui.environment.RegularEnvironment;
import gui.environment.tag.CriticalTag;
import gui.regular.ConvertToAutomatonPane;

import java.awt.event.ActionEvent;

import javax.swing.JOptionPane;

/**
 * This class initiates the conversion of a regular expression to a
 * nondeterministic finite state automaton.
 * 
 * @author Thomas Finley
 */

public class REToFSAAction extends RegularAction {
    /**
     * Instantiates a <CODE>REToFSAAction</CODE>.
     * @param environment the environment which is home to the
     * regular expression to convert
     */
    public REToFSAAction(RegularEnvironment environment) {
	super("Convert to NFA", null, environment);
    }

    /**
     * This begins the process of converting a regular expression to
     * an NFA.
     * @param event the event to process
     */
    public void actionPerformed(ActionEvent event) {
	//JFrame frame = Universe.frameForEnvironment(environment);
	try {
	    getExpression().asCheckedString();
	} catch (UnsupportedOperationException e) {
	    JOptionPane.showMessageDialog
		(getEnvironment(), e.getMessage(),
		 "Illegal Expression", JOptionPane.ERROR_MESSAGE);
	    return;
	}
	ConvertToAutomatonPane pane =
	    new ConvertToAutomatonPane(getEnvironment());
	getEnvironment().add(pane, "Convert RE to NFA", new CriticalTag() {});
	getEnvironment().setActive(pane);
    }
}

