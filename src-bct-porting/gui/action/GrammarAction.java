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

import grammar.Grammar;

import javax.swing.Icon;

/**
 * The <CODE>GrammarAction</CODE> is the general action that various
 * controllers for operators on grammars should subclass.  The only
 * real change from the <CODE>RestrictedAction</CODE> is that by
 * default the <CODE>.isAcceptable</CODE> method now only returns true
 * if the object is an instance of <CODE>Grammar</CODE>.
 * 
 * @see grammar.Grammar
 * 
 * @author Thomas Finley
 */

public abstract class GrammarAction extends RestrictedAction {
    /**
     * Instantiates a new <CODE>GrammarAction</CODE>.
     * @param string a string description
     * @param icon the optional icon, or <CODE>null</CODE> if there is
     * to be no icon associated with this action
     */
    public GrammarAction(String string, Icon icon) {
	super(string, icon);
    }

    /**
     * Given an object, determine if this grammar action is able to be
     * applied to that object based on its class.  By default, this
     * method returns <CODE>true</CODE> if this object is an instance
     * of <CODE>Grammar</CODE>.
     * @param object the object to test for "applicability"
     * @return <CODE>true</CODE> if this action should be available to
     * an object of this type, <CODE>false</CODE> otherwise.
     */
    public static boolean isApplicable(Object object) {
	return object instanceof Grammar;
    }
}
