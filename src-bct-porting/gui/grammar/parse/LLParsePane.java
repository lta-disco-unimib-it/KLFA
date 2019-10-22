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
import grammar.parse.LLParseTable;
import gui.environment.GrammarEnvironment;
import gui.tree.SelectNodeDrawer;

import javax.swing.JComponent;
import javax.swing.JTable;

/**
 * This is a parse pane for LL grammars.
 * 
 * @author Thomas Finley
 */

public class LLParsePane extends ParsePane {
    /**
     * Instantiaes a new LL parse pane.
     * @param environment the grammar environment
     * @param grammar the augmented grammar
     * @param table the LL parse table
     */
    public LLParsePane(GrammarEnvironment environment, Grammar grammar,
		       LLParseTable table) {
	super(environment, grammar);
	this.table = new LLParseTable(table) {
		public boolean isCellEditable(int r,int c) {return false;}
	    };
	initView();
    }

    /**
     * Inits a parse table.
     * @return a table to hold the parse table
     */
    protected JTable initParseTable() {
	tablePanel = new LLParseTablePane(table);
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

    /**
     * Inits a new tree panel.  This overriding adds a selection node
     * drawer so certain nodes can be highlighted.
     * @return a new display for the parse tree
     */
    protected JComponent initTreePanel() {
	treeDrawer.setNodeDrawer(nodeDrawer);
	return super.initTreePanel();
    }

    /** The parse table. */
    final LLParseTable table;
    /** The parse table panel. */
    LLParseTablePane tablePanel;
    /** The controller object. */
    protected LLParseController controller = new LLParseController(this);
    /** The selection node drawer. */
    SelectNodeDrawer nodeDrawer = new SelectNodeDrawer();
}
