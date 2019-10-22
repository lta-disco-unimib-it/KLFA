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

import grammar.parse.LRParseTable;

import java.util.SortedSet;

import javax.swing.DefaultCellEditor;
import javax.swing.JComboBox;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;

/**
 * This is a regular LR parse table pane, except with the added
 * ability that it can shift into a mode where those elements which
 * represent different elements of the table are selectable.
 * 
 * @author Thomas Finley
 */

public class LRParseTableChooserPane extends LRParseTablePane {
    /**
     * Instantiates a new parse table pane.
     * @param table the table model
     */
    public LRParseTableChooserPane(LRParseTable table) {
	super(table);
    }

    /**
     * Shifts this table into the non-editable choosing mode.
     */
    public void shiftMode() {
	finalEdit = true;
	LRParseTable table = getParseTable();
	// Determine which rows and columns have ambiguity.
	cellEditors = new TableCellEditor[table.getRowCount()]
	    [table.getColumnCount()];
	for (int r=0; r<table.getRowCount(); r++)
	    for (int c=1; c<table.getColumnCount(); c++) {
		cellEditors[r][c] = null;
		SortedSet set = table.getSetAt(r,c);
		if (set.size() <= 1) continue;
		table.setValueAt(set.first(), r, c);
		cellEditors[r][c] =
		    new DefaultCellEditor(new JComboBox(set.toArray()));
	    }
    }

    /**
     * Returns if a cell is editable.
     * @param row the row index
     * @param column the column index
     * @return if the indicated cell is renderable
     */
    public boolean isCellEditable(int row, int column) {
	if (!finalEdit) return super.isCellEditable(row,column);
	column = convertColumnIndexToModel(column);
	return cellEditors[row][column] != null;
    }

    /**
     * Returns a renderer for a table cell.
     * @param row the row index
     * @param column the column index
     * @return the renderer for this table cell
     */
    public TableCellRenderer getCellRenderer(int row, int column) {
	if (!finalEdit) return super.getCellRenderer(row,column);
	int mc = convertColumnIndexToModel(column);
	return cellEditors[row][mc] == null ?
	    super.getCellRenderer(row,column) : RENDERER;
    }

    /**
     * Returns the editor for a table cell.
     * @param row the row index
     * @param column the column index
     * @return the table cell editor for this cell
     */
    public TableCellEditor getCellEditor(int row, int column) {
	if (!finalEdit) return super.getCellEditor(row,column);
	int mc = convertColumnIndexToModel(column);
	return cellEditors[row][mc] == null ?
	    super.getCellEditor(row,column) : cellEditors[row][mc];
    }

    /** Are we in final edit mode yet? */
    private boolean finalEdit = false;

    /** The array of cell editors for row/column, which may be null if
	non-editable */
    private TableCellEditor[][] cellEditors;

    /** The renderer for those cells that are ambiguous. */
    private static final DefaultTableCellRenderer RENDERER =
	new DefaultTableCellRenderer();

    static {
	RENDERER.setBackground(new java.awt.Color(255,200,150));
    }
}
