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
 
package gui.environment;

import grammar.Grammar;
import grammar.lsystem.LSystem;
import gui.editor.EditorPane;
import gui.environment.tag.EditorTag;
import gui.environment.tag.PermanentTag;
import gui.environment.tag.Tag;
import gui.grammar.GrammarInputPane;
import gui.lsystem.LSystemInputPane;

import java.io.Serializable;

import javax.swing.JOptionPane;

import regular.RegularExpression;
import automata.Automaton;

/**
 * The <CODE>EnvironmentFactory</CODE> creates environments for some
 * predefined types of objects with an editing pane already added.
 * For situations where a new <CODE>Environment</CODE> must be created
 * with customized trimmings, this sort of factory will be
 * inappropriate.  The intended use is for <CODE>Environment</CODE>s
 * opened in a file, or created in a new file action, or some other
 * such action.
 * 
 * @author Thomas Finley
 */

public class EnvironmentFactory {
    /**
     * Returns a new environment with an editor pane.
     * @param object the object that this environment will edit
     * @return a new environment for the passed in object, ready to be
     * edited, or <CODE>null</CODE> if no environment could be defined
     * for this object
     */
    public static Environment getEnvironment(Serializable object) {
	if (object instanceof Automaton) {
	    Automaton aut = (Automaton) object;
	    Environment env = new AutomatonEnvironment(aut);
	    EditorPane editor = new EditorPane(aut);
	    env.add(editor, EDITOR_NAME, EDITOR_PERMANENT_TAG);
	    return env;
	} else if (object instanceof Grammar) {
	    Grammar grammar = (Grammar) object;
	    GrammarInputPane input = new GrammarInputPane(grammar);
	    Environment env = new GrammarEnvironment(input);
	    // Set up the pane for the input pane.
	    env.add(input, EDITOR_NAME, EDITOR_PERMANENT_TAG);
	    return env;
	} else if (object instanceof RegularExpression) {
	    RegularExpression re = (RegularExpression) object;
	    gui.regular.EditorPane editor = new gui.regular.EditorPane(re);
	    Environment env = new RegularEnvironment(re);
	    env.add(editor, EDITOR_NAME, EDITOR_PERMANENT_TAG);
	    return env;
	} else if (object instanceof LSystem) {
	    LSystem lsystem = (LSystem) object;
	    LSystemInputPane lsinput = new LSystemInputPane(lsystem);
	    Environment env = new LSystemEnvironment(lsinput);
	    env.add(lsinput, EDITOR_NAME, EDITOR_PERMANENT_TAG);
	    return env;
	} else {
	    JOptionPane.showMessageDialog
		(null, "Unknown type "+object.getClass()+" read!",
		 "Bad Type", JOptionPane.ERROR_MESSAGE);
	    // Nothing else yet.
	    return null;
	}
    }

    /** A class for an editor, which in most applications one will
     * want both permanent and marked as an editor. */
    private static class EditorPermanentTag
	implements EditorTag, PermanentTag { };
    /** An instance of such an editor permanant tag. */
    private static final Tag EDITOR_PERMANENT_TAG = new EditorPermanentTag();
    /** The name for editor panes. */
    private static final String EDITOR_NAME = "Editor";
}
