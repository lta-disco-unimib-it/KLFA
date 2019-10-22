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
