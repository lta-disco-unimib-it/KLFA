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
