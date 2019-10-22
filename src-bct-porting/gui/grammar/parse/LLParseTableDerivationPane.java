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
import gui.SplitPaneFactory;
import gui.environment.GrammarEnvironment;
import gui.grammar.GrammarTable;

import java.awt.BorderLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JToolBar;

/**
 * This is the view for the derivation of a LL parse table from a
 * grammar.
 * 
 * @author Thomas Finley
 */

public class LLParseTableDerivationPane extends JPanel {
    /**
     * Instantiates a new derivation pane for a grammar environment.
     * @param environment the grammar environment
     */
    public LLParseTableDerivationPane(GrammarEnvironment environment) {
	super(new BorderLayout());
	Grammar g = environment.getGrammar();
	JPanel right = new JPanel(new BorderLayout());

	JLabel description = new JLabel();
	right.add(description, BorderLayout.NORTH);
	
	//FirstFollowModel ffmodel = new FirstFollowModel(g);
	FirstFollowTable fftable = new FirstFollowTable(g);
	fftable.getColumnModel().getColumn(0).setPreferredWidth(30);
	fftable.getFFModel().setCanEditFirst(true);
	fftable.getFFModel().setCanEditFollow(true);
	
	LLParseTable parseTableModel = new LLParseTable(g) {
		public boolean isCellEditable(int r, int c) {
		    return controller.step != LLParseDerivationController.FINISHED
			&& super.isCellEditable(r,c);
		}
	    };
	parseTable = new LLParseTablePane(parseTableModel);
	parseTable.getColumnModel().getColumn(0).setPreferredWidth(30);

	JSplitPane rightSplit = SplitPaneFactory.createSplit
	    (environment, false, 0.5, new JScrollPane(fftable),
	     new JScrollPane(parseTable));
	right.add(rightSplit, BorderLayout.CENTER);

	controller = new LLParseDerivationController
	    (g, environment, fftable, parseTable, description);

	GrammarTable table =
	    new GrammarTable(new gui.grammar.GrammarTableModel(g) {
		    public boolean isCellEditable(int r, int c) {return false;}
		});
	JSplitPane pane = SplitPaneFactory.createSplit
	    (environment, true, 0.3, table, right);
	this.add(pane, BorderLayout.CENTER);
	
	// Make the tool bar.
	JToolBar toolbar = new JToolBar();
	toolbar.add(controller.doSelectedAction);
	toolbar.add(controller.doStepAction);
	toolbar.add(controller.doAllAction);
	toolbar.addSeparator();
	toolbar.add(controller.nextAction);
	toolbar.addSeparator();
	toolbar.add(controller.parseAction);
	this.add(toolbar, BorderLayout.NORTH);
    }

    void makeParseUneditable() {
	editable = false;
	try {
	    parseTable.getCellEditor().stopCellEditing();
	} catch (NullPointerException e) { }

    }
    
    private LLParseDerivationController controller;
    private LLParseTablePane parseTable;
    private boolean editable;
}
