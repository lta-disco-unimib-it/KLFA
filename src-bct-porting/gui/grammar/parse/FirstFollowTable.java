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
import gui.LeftTable;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;

/**
 * This table is an table specifically for the
 * <CODE>FirstFollowModel</CODE> for handling user entry of first and
 * follow sets.
 * 
 * @author Thomas Finley
 */

public class FirstFollowTable extends LeftTable {
    /**
     * Instantiates a new first follow table for a grammar.
     * @param grammar the grammar to create the table
     */
    public FirstFollowTable(Grammar grammar) {
	super(new FirstFollowModel(grammar));
	model = (FirstFollowModel) getModel();

	getColumnModel().getColumn(1).setCellRenderer(RENDERER);
	getColumnModel().getColumn(2).setCellRenderer(RENDERER);

	setCellSelectionEnabled(true);
    }

    /**
     * Returns the first follow table model.
     * @return the table model
     */
    public FirstFollowModel getFFModel() {
	return model;
    }

    /** The table model. */
    private FirstFollowModel model;

    /**
     * Converts a string to a set string.
     */
    private static String getSetString(String s) {
	if (s == null) return "{ }";
	StringBuffer sb = new StringBuffer("{ ");
	for (int i=0; i<s.length(); i++) {
	    char c = s.charAt(i);
	    if (c=='!') c='\u03BB';
	    sb.append(c);
	    if (i!=s.length()-1) sb.append(',');
	    sb.append(' ');
	}
	sb.append('}');
	return sb.toString();
    }

    /**
     * The modified table cell renderer.
     */
    private static class SetsCellRenderer extends DefaultTableCellRenderer {
	public Component getTableCellRendererComponent
	    (JTable table, Object value, boolean isSelected,
	     boolean hasFocus, int row, int column) {
	    JLabel l = (JLabel) super.getTableCellRendererComponent
		(table,value,isSelected,hasFocus,row,column);
	    if (hasFocus && table.isCellEditable(row,column)) return l;
	    l.setText(getSetString((String) value));
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
		    renderer = new SetsCellRenderer();
		    renderer.setBackground(new Color(255,150,150));
		}
		return renderer;
	    }
	    private DefaultTableCellRenderer renderer = null;
	};


    /** The sets cell renderer. */
    private static final TableCellRenderer RENDERER = new SetsCellRenderer();
}
