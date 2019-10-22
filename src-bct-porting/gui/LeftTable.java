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
 
package gui;

import java.awt.Color;

import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;

/**
 * This table is specifically for those tables where the leftmost
 * column is an identifier for the row, i.e., it should not truly be
 * considered data of the table.
 * 
 * @author Thomas Finley
 */

public class LeftTable extends HighlightTable {
    public LeftTable() {
	initView();
    }
    public LeftTable(TableModel model) {
	super(model);
	initView();
    }

    /**
     * Makes the leftmost column's data cells have renderers the same
     * as the table column headers.
     */
    private void initView() {
	setGridColor(Color.lightGray);
	TableColumn column = getColumnModel().getColumn(0);
	//column.setCellRenderer(column.getHeaderRenderer());
	DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
	renderer.setBackground(new Color(200,200,200));
	column.setCellRenderer(renderer);
    }
}
