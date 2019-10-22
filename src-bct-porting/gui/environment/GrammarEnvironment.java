/* -- JFLAP 4.0 --
 *
 * Copyright information:
 *
 * Susan H. Rodger, Thomas Finley
 * Computer Science Department
 * Duke University
 * April 24, 2003
 * Supported by National Science Foundation DUE-9752583.
 *
 * Copyright (c) 2003
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms are permitted
 * provided that the above copyright notice and this paragraph are
 * duplicated in all such forms and that any documentation,
 * advertising materials, and other materials related to such
 * distribution and use acknowledge that the software was developed
 * by the author.  The name of the author may not be used to
 * endorse or promote products derived from this software without
 * specific prior written permission.
 * THIS SOFTWARE IS PROVIDED ``AS IS'' AND WITHOUT ANY EXPRESS OR
 * IMPLIED WARRANTIES, INCLUDING, WITHOUT LIMITATION, THE IMPLIED
 * WARRANTIES OF MERCHANTIBILITY AND FITNESS FOR A PARTICULAR PURPOSE.
 */
 
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
