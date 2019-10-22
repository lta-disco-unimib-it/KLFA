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
 
package gui.action;

import grammar.Grammar;
import grammar.UnrestrictedGrammar;
import gui.environment.EnvironmentFrame;
import gui.environment.GrammarEnvironment;
import gui.environment.Universe;
import gui.environment.tag.CriticalTag;
import gui.grammar.transform.ChomskyPane;

import java.awt.event.ActionEvent;

/**
 * This is a simple test action for grammars.
 * 
 * @author Thomas Finley
 */

public class GrammarTestAction extends GrammarAction {
    /**
     * Instantiates a new <CODE>GrammarOutputAction</CODE>.
     * @param environment the grammar environment
     */
    public GrammarTestAction(GrammarEnvironment environment) {
	super("Grammar Test", null);
	this.environment = environment;
	this.frame = Universe.frameForEnvironment(environment);
    }

    /**
     * Performs the action.
     */
    public void actionPerformed(ActionEvent e) {
	Grammar g = environment.getGrammar(UnrestrictedGrammar.class);
	if (g == null) return;
	ChomskyPane cp = new ChomskyPane(environment, g);
	environment.add(cp, "Test", new CriticalTag() {});
	environment.setActive(cp);
    }

    /** The grammar environment. */
    private GrammarEnvironment environment;
    /** The frame for the grammar environment. */
    private EnvironmentFrame frame;
}
