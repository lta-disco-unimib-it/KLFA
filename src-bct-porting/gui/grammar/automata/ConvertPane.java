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
