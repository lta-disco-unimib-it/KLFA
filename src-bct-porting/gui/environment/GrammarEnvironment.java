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
import grammar.UnboundGrammar;
import gui.grammar.GrammarInputPane;

import java.io.Serializable;

import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

/**
 * The <CODE>GrammarEnvironment</CODE> is an environment for holding a
 * grammar.  Owing to certain eccentricities of the way that the
 * grammar is set up as a non-editable object, what is passed into the
 * environment is a <CODE>GrammarInputPane</CODE> which is then used
 * to retrieve the current grammar.
 * 
 * Unlike other environments, the object returned by the
 * <CODE>Grammar</CODE> returned by the <CODE>.getObject</CODE> method
 * will not point to the same object throughout the environment's
 * execution.
 * 
 * @see grammar.Grammar
 * @see gui.grammar.GrammarInputPane
 * 
 * @author Thomas Finley
 */

public class GrammarEnvironment extends Environment {
    /**
     * Instantiates a new <CODE>GrammarEnvironment</CODE> with the
     * given <CODE>GrammarInputPane</CODE>.
     * @param input the <CODE>GrammarInputPane</CODE>
     */
    public GrammarEnvironment(GrammarInputPane input) {
	super(null);
	this.input = input;
	input.getTable().getModel().addTableModelListener
	    (new TableModelListener() {
		    public void tableChanged(TableModelEvent event) {
			setDirty();
		    } } );
    }

    /**
     * Returns the grammar of this <CODE>GrammarEnvironment</CODE>,
     * which is retrieved from the <CODE>GrammarInputPane</CODE>'s
     * <CODE>.getGrammar</CODE> method.
     * @see gui.grammar.GrammarInputPane#getGrammar
     * @return the <CODE>Grammar</CODE> for this environment
     */
    public Serializable getObject() {
	return getGrammar(UnboundGrammar.class);
    }

    /**
     * Returns the context free grammar.
     * @see gui.grammar.GrammarInputPane#getGrammar()
     * @return the <CODE>ContextFreeGrammar</CODE> for this environment
     */
    public Grammar getGrammar() {
	return input.getGrammar();
    }

    /**
     * Returns the grammar of the specified type.
     * @param grammarClass specification of the type of grammar which
     * should be returned
     * @see gui.grammar.GrammarInputPane#getGrammar(Class)
     * @return the <CODE>Grammar</CODE> for this environment of the
     * specified type
     */
    public Grammar getGrammar(Class grammarClass) {
	return input.getGrammar(grammarClass);
    }

    /** The grammar input pane. */
    private GrammarInputPane input = null;
}
