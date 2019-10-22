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
 
package gui.grammar.automata;

import gui.SplitPaneFactory;
import gui.editor.ArrowDisplayOnlyTool;
import gui.environment.AutomatonEnvironment;
import gui.grammar.GrammarTable;
import gui.viewer.AutomatonPane;
import gui.viewer.SelectionDrawer;

import java.awt.BorderLayout;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;

import automata.Automaton;

/**
 * This <CODE>ConvertPane</CODE> exists for the user to convert an
 * automaton to a grammar.
 * 
 * @author Thomas Finley
 */

public class ConvertPane extends JPanel {
    /**
     * Instantiates a new <CODE>ConvertPane</CODE>.
     */
    public ConvertPane(AutomatonEnvironment environment,
		       Automaton automaton) {
	super(new BorderLayout());
	drawer = new SelectionDrawer(automaton);
	automatonPane = new AutomatonPane(drawer);
	JSplitPane split = SplitPaneFactory.
	    createSplit(environment, true, 0.6, automatonPane,
			new JScrollPane(table));
	automatonPane.addMouseListener(new ArrowDisplayOnlyTool
				       (automatonPane,
					automatonPane.getDrawer()));
	add(split, BorderLayout.CENTER);
    }

    /**
     * Returns the <CODE>AutomatonPane</CODE> that does the drawing.
     * @return the <CODE>AutomatonPane</CODE> that does the drawing
     */
    public AutomatonPane getAutomatonPane() {
	return automatonPane;
    }

    /**
     * Returns the <CODE>SelectionDrawer</CODE> for the automaton
     * pane.
     * @return the <CODE>SelectionDrawer</CODE>
     */
    public SelectionDrawer getDrawer() {
	return drawer;
    }

    /**
     * Returns the <CODE>GrammarTable</CODE> where the grammar is
     * being built.
     * @return the <CODE>GrammarTable</CODE>
     */
    public GrammarTable getTable() {
	return table;
    }

    /** The automaton pane. */
    private AutomatonPane automatonPane;
    /** The grammar table. */
    private GrammarTable table =
	new GrammarTable(new gui.grammar.GrammarTableModel() {
		public boolean isCellEditable(int r, int c) {return false;}
	    });
    /** The drawer for the automaton. */
    private SelectionDrawer drawer;
}
