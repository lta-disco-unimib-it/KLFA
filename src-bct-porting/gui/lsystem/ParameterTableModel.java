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
