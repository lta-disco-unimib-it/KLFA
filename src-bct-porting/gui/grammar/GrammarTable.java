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
import grammar.Production;
import gui.HighlightTable;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

/**
 * The <CODE>GrammarTable</CODE> is a simple extension to the
 * <CODE>JTable</CODE> that standardizes how grammar tables look.
 * 
 * @author Thomas Finley
 */

public class GrammarTable extends HighlightTable {
    /**
     * Instantiates a <CODE>GrammarTable</CODE> with an empty grammar.
     */
    public GrammarTable() {
	super(new GrammarTableModel());
	initView();
    }

    /**
     * Instantiates a <CODE>GrammarTable</CODE> with a given table
     * model.
     * @param model the table model for the new grammar table
     */
    public GrammarTable(GrammarTableModel model) {
	super(model);
	initView();
    }

    /**
     * Handles the highlighting of a particular row.
     * @param row the row to highlight
     */
    public void highlight(int row) {
	highlight(row, 0);
	highlight(row, 2);
    }

    /**
     * This constructor helper function customizes the view of the
     * table.
     */
    private void initView() {
	getTableHeader().setReorderingAllowed(false);
	TableColumn lhs = getColumnModel().getColumn(0);
	TableColumn arrows = getColumnModel().getColumn(1);
	lhs.setMaxWidth(100);
	lhs.setMinWidth(20);
	arrows.setMaxWidth(30);
	arrows.setMinWidth(30);
	getColumnModel().getColumn(1).setPreferredWidth(30);
	setShowGrid(true);
	setGridColor(Color.lightGray);

	getColumnModel().getColumn(2).setCellRenderer(RENDERER);
    }

    /**
     * Returns the model for this grammar table.
     * @return the grammar table model for this table
     */
    public GrammarTableModel getGrammarModel() {
	return (GrammarTableModel) super.getModel();
    }

    /**
     * Returns the grammar that has been defined through this
     * <CODE>GrammarTable</CODE>, where the grammar is an instance of
     * the class passed into this function.
     * @param grammarClass the type of grammar that is passed in
     * @return a grammar of the variant returned by this grammar, or
     * <CODE>null</CODE> if some sort of error with a production is
     * encountered
     * @throws IllegalArgumentException if the grammar class passed in
     * could not be instantiated with an empty constructor, or is not
     * even a subclass of <CODE>Grammar</CODE>.
     */
    public Grammar getGrammar(Class grammarClass) {
	Grammar grammar = null;
	try {
	    grammar = (Grammar) grammarClass.newInstance();
	} catch (NullPointerException e) {
	    throw e;
	} catch (Throwable e) {
	    throw new IllegalArgumentException
		("Bad grammar class "+grammarClass);
	}
	GrammarTableModel model = getGrammarModel();
	// Make sure we're not editing anything anymore.
	if (getCellEditor() != null)
	    getCellEditor().stopCellEditing();
	// Add the productions.
	for (int row=0; row<model.getRowCount(); row++) {
	    Production p = model.getProduction(row);
	    if (p == null) continue;
	    try {
		grammar.addProduction(p);
		if (grammar.getStartVariable() == null)
		    grammar.setStartVariable(p.getLHS());
	    } catch (IllegalArgumentException e) {
		setRowSelectionInterval(row,row);
		JOptionPane.showMessageDialog
		    (this, e.getMessage(), "Production Error",
		     JOptionPane.ERROR_MESSAGE);
		return null;
	    }
	}
	return grammar;
    }

    /** Modified to use the set renderer highlighter. */
    public void highlight(int row, int column) {
	highlight(row, column, THRG);
    }

    /**
     * The modified table cell renderer.
     */
    private static class LambdaCellRenderer extends DefaultTableCellRenderer {
	public Component getTableCellRendererComponent
	    (JTable table, Object value, boolean isSelected,
	     boolean hasFocus, int row, int column) {
	    JLabel l = (JLabel) super.getTableCellRendererComponent
		(table,value,isSelected,hasFocus,row,column);
	    if (hasFocus && table.isCellEditable(row,column)) return l;
	    if (column != 2) return l;
	    if (!value.equals("")) return l;
	    if (table.getModel().getValueAt(row,0).equals("")) return l;
	    l.setText("\u03BB");
	    return l;
	}
    }

    /** The built in highlight renderer generator, modified. */
    private static final
	gui.HighlightTable.TableHighlighterRendererGenerator THRG =
	new TableHighlighterRendererGenerator() {
	    public TableCellRenderer getRenderer(int row, int column) {
		if (renderer == null) {
		    renderer = new LambdaCellRenderer();
		    renderer.setBackground(new Color(255,150,150));
		}
		return renderer;
	    }
	    private DefaultTableCellRenderer renderer = null;
	};

    /** The lambda cell renderer. */
    private static final TableCellRenderer RENDERER = new LambdaCellRenderer();
}
