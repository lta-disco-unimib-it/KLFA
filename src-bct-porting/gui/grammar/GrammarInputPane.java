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
 
package gui.grammar;

import grammar.Grammar;
import grammar.cfg.ContextFreeGrammar;
import grammar.reg.RegularGrammar;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.table.TableColumn;

/**
 * The <CODE>GrammarInputPane</CODE> is a pane that is used for the
 * input and graphical display of a grammar.
 * 
 * @author Thomas Finley
 */

public class GrammarInputPane extends JPanel {
    /**
     * Instantiates an empty <CODE>GrammarInputPane</CODE>.
     */
    public GrammarInputPane() {
	model = new GrammarTableModel();
	initView();
    }

    /**
     * Instantiates a <CODE>GrammarInputPane</CODE> that holds and
     * displays the indicated grammar.
     * @param grammar the grammar to display
     */
    public GrammarInputPane(Grammar grammar) {
	model = new GrammarTableModel(grammar);
	initView();
    }

    /**
     * This is a constructor helper function that initializes the
     * view.
     */
    private void initView() {
	// Set up the table.
	table = new GrammarTable(model);
	table.getTableHeader().setReorderingAllowed(false);
	TableColumn lhs = table.getColumnModel().getColumn(0);
	TableColumn arrows = table.getColumnModel().getColumn(1);
	lhs.setMaxWidth(100);
	lhs.setMinWidth(20);
	arrows.setMaxWidth(30);
	arrows.setMinWidth(30);
	table.getColumnModel().getColumn(1).setPreferredWidth(30);
	table.setShowGrid(true);
	table.setGridColor(Color.lightGray);
	
	// Put the table in this pane.
	setLayout(new BorderLayout());
	add(new JScrollPane(table), BorderLayout.CENTER);
    }

    /**
     * Returns the grammar that has been defined through this
     * <CODE>GrammarInputPane</CODE>.  This method returns a grammar
     * of the type <CODE>ContextFreeGrammar</CODE>.
     * @return the grammar defined by this input pane, or
     * <CODE>null</CODE> if an error occurred
     */
    public Grammar getGrammar() {
	return getGrammar(ContextFreeGrammar.class);
    }

    /**
     * Returns the grammar that has been defined through this
     * <CODE>GrammarInputPane</CODE>, where the grammar is an instance
     * of the class passed into this function.
     * @param grammarClass the type of grammar that is passed in
     * @return a grammar of the variant returned by this grammar
     * @throws IllegalArgumentException if the grammar class passed in
     * could not be instantiated with an empty constructor, or is not
     * even a subclass of <CODE>Grammar</CODE>.
     */
    public Grammar getGrammar(Class grammarClass) {
	return table.getGrammar(grammarClass);
    }

    /**
     * Returns the grammar that has been defined through this
     * <CODE>GrammarInputPane</CODE>.
     * @return the grammar defined by this input pane, or
     * <CODE>null</CODE> if an error occurred
     */
    public RegularGrammar getRegularGrammar() {
	return (RegularGrammar) getGrammar(RegularGrammar.class);
    }

    /**
     * Returns the table.
     * @return the table where the productions are edited
     */
    public GrammarTable getTable() {
	return table;
    }

    /** The table where the productions are edited. */
    private GrammarTable table;
    /** The model for the table. */
    private GrammarTableModel model;
}
