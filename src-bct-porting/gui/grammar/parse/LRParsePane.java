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
