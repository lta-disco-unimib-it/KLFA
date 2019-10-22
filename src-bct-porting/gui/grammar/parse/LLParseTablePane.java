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
