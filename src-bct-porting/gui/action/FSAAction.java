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

import javax.swing.Icon;

import automata.fsa.FiniteStateAutomaton;

/**
 * The <CODE>FSAAction</CODE> is the general action that various
 * controllers for operators on finite state automatons should
 * subclass.  The only real change from the
 * <CODE>RestrictedAction</CODE> is that by default the
 * <CODE>.isAcceptable</CODE> method now only returns true if the
 * object is an instance of <CODE>FiniteStateAutomaton</CODE>.
 * 
 * @author Thomas Finley
 */

public abstract class FSAAction extends RestrictedAction {
    /**
     * Instantiates a new <CODE>FSAAction</CODE>.
     * @param string a string description
     * @param icon the optional icon, or <CODE>null</CODE> if there is
     * to be no icon associated with this action
     */
    public FSAAction(String string, Icon icon) {
	super(string, icon);
    }

    /**
     * Given an object, determine if this automaton action is able to
     * be applied to that object based on its class.  By default, this
     * method returns <CODE>true</CODE> if this object is an instance
     * of <CODE>Automaton</CODE>.
     * @param object the object to test for "applicability"
     * @return <CODE>true</CODE> if this action should be available to
     * an object of this type, <CODE>false</CODE> otherwise.
     */
    public static boolean isApplicable(Object object) {
	return object instanceof FiniteStateAutomaton;
    }
}
