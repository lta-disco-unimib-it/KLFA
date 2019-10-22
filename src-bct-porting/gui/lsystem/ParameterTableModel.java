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
 
package gui.lsystem;

import gui.GrowableTableModel;

import java.util.Iterator;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * A mapping of parameters to values.
 * 
 * @author Thomas Finley
 */

public class ParameterTableModel extends GrowableTableModel {
    /**
     * Constructs an empty parameter table model.
     */
    public ParameterTableModel() {
	super(2);
    }

    /**
     * Constructs a parameter table model out of the map.
     * @param parameters the mapping of parameter names to parameter
     * objects
     */
    public ParameterTableModel(Map parameters) {
	this();
	Iterator it = parameters.entrySet().iterator();
	int i=0;
	while (it.hasNext()) {
	    Map.Entry entry = (Map.Entry) it.next();
	    setValueAt(entry.getKey(), i, 0);
	    setValueAt(entry.getValue(), i, 1);
	    i++;
	}
    }

    /**
     * Initializes a row.  In this object, a row is two empty strings.
     * @return an array with two empty strings
     */
    public Object[] initializeRow(int row) {
	return new Object[]{"", ""};
    }

    /**
     * Returns the mapping of names of parameters.
     * @return the mapping from parameter names to parameters (i.e.,
     * map of contents of the left column to contents of the right
     * column)
     */
    public SortedMap getParameters() {
	TreeMap map = new TreeMap();
	for (int i=0; i<getRowCount()-1; i++) {
	    Object o = getValueAt(i, 0);
	    if (o.equals("")) continue;
	    map.put(o, getValueAt(i, 1));
	}
	return map;
    }

    /**
     * Values in the table are editable.
     * @param row the row index
     * @param column the column index
     * @return <CODE>true</CODE> always
     */
    public boolean isCellEditable(int row, int column) {
	return true;
    }
    
    
    /**
     * Returns the column name.
     * @param column the index of the column
     * @return the name of a particular column
     */
    public String getColumnName(int column) {
	return column == 0 ? "Name" : "Parameter";
    } 
}
