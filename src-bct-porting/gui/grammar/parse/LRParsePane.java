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
 
package gui.grammar.parse;

import grammar.Grammar;
import grammar.parse.LRParseTable;
import gui.environment.GrammarEnvironment;

import javax.swing.JTable;

/**
 * This is a parse pane for LR grammars.
 * 
 * @author Thomas Finley
 */

public class LRParsePane extends ParsePane {
    /**
     * Instantiaes a new LR parse pane.
     * @param environment the grammar environment
     * @param grammar the augmented grammar
     * @param table the LR parse table
     */
    public LRParsePane(GrammarEnvironment environment, Grammar grammar,
		       LRParseTable table) {
	super(environment, grammar);
	this.table = new LRParseTable(table) {
		public boolean isCellEditable(int r,int c) {return false;}
	    };
	initView();
    }

    /**
     * Inits a parse table.
     * @return a table to hold the parse table
     */
    protected JTable initParseTable() {
	tablePanel = new LRParseTablePane(table);
	return tablePanel;
    }

    /**
     * This method is called when there is new input to parse.
     * @param string a new input string
     */
    protected void input(String string) {
	controller.initialize(string);
    }

    /**
     * This method is called when the step button is pressed.
     */
    protected void step() {
	controller.step();
    }

    /** The parse table. */
    final LRParseTable table;
    /** The parse table panel. */
    LRParseTablePane tablePanel;
    /** The controller object. */
    protected LRParseController controller = new LRParseController(this);
}
