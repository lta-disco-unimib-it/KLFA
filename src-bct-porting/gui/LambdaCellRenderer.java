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

import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

/**
 * This is a cell renderer that displays the a specified character if
 * the quantity to display is the empty string.
 * 
 * @author Thomas Finley
 */

public class LambdaCellRenderer extends DefaultTableCellRenderer {
    /**
     * Instantiates a new lambda cell renderer with the specified
     * string to substitute for the empty string in the event that we
     * display the empty string.
     * @param string the string to display in lieu of the empty string
     */
    public LambdaCellRenderer(String string) {
	toSubstitute = string;
    }
    
    /**
     * Instantiates a new lambda cell renderer where the unicode
     * string for lambda is substituted for the empty string when
     * displaying the empty string.
     */
    public LambdaCellRenderer() {
	this("\u03BB");
    }

    /**
     * Returns the string this renderer substitutes for the empty
     * string.
     * @return the string displayed in lieu of the empty string
     */
    public final String getEmpty() {
	return toSubstitute;
    }
    
    public Component getTableCellRendererComponent
	(JTable table, Object value, boolean isSelected,
	 boolean hasFocus, int row, int column) {
	JLabel l = (JLabel) super.getTableCellRendererComponent
	    (table,value,isSelected,hasFocus,row,column);
	if (hasFocus && table.isCellEditable(row,column)) return l;
	if (!"".equals(value)) return l;
	l.setText(toSubstitute);
	return l;
    }

    /** The string to substitute for the empty string. */
    private String toSubstitute;
}
