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
import gui.grammar.parse.BruteParsePane;

import java.awt.event.ActionEvent;

/**
 * This action creates a new brute force parser for the grammar.
 * 
 * @author Thomas Finley
 */

public class BruteParseAction extends GrammarAction {
    /**
     * Instantiates a new <CODE>BruteParseAction</CODE>.
     * @param environment the grammar environment
     */
    public BruteParseAction(GrammarEnvironment environment) {
	super("Brute Force Parse", null);
	this.environment = environment;
	this.frame = Universe.frameForEnvironment(environment);
    }

    /**
     * Performs the action.
     */
    public void actionPerformed(ActionEvent e) {
	Grammar g = environment.getGrammar(UnrestrictedGrammar.class);
	if (g == null) return;
	BruteParsePane bpp = 
	    new BruteParsePane(environment, g);
	environment.add(bpp, "Brute Parser", new CriticalTag() {});
	environment.setActive(bpp);
    }

    /** The grammar environment. */
    private GrammarEnvironment environment;
    /** The frame for the grammar environment. */
    private EnvironmentFrame frame;
}
