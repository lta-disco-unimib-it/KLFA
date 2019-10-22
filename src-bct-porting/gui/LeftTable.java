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
