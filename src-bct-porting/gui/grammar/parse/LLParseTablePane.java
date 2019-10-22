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

import grammar.parse.LLParseTable;
import gui.LeftTable;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;

/**
 * This holds a LL parse table.
 * 
 * @author Thomas Finley
 */

public class LLParseTablePane extends LeftTable {
    /**
     * Instantiates a new parse table pane for a parse table.
     * @param table the table pane's parse table
     */
    public LLParseTablePane(LLParseTable table) {
	super(table);
	this.table = table;
	setCellSelectionEnabled(true);

	for (int i=1; i<getColumnCount(); i++)
	    getColumnModel().getColumn(i).setCellRenderer(RENDERER);
    }

    /**
     * Retrieves the parse table in this pane.
     * @return the parse table in this pane
     */
    public LLParseTable getParseTable() {
	return table;
    }

    /** The parse table for this pane. */
    private LLParseTable table;

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
	    l.setText(((String) value).replace('!', '\u03BB'));
	    return l;
	}
    }

    /** Modified to use the set renderer highlighter. */
    public void highlight(int row, int column) {
	highlight(row, column, THRG);
    }

    /** The built in highlight renderer generator. */
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


    /** The sets cell renderer. */
    private static final TableCellRenderer RENDERER = new LambdaCellRenderer();
}
