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

import grammar.Grammar;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import javax.swing.table.AbstractTableModel;

/**
 * This is a table model for user definition of the first and follow
 * sets for a grammar.  The first column are the variable names.  The
 * second column are the first sets, the third column the follow sets.
 * 
 * @author Thomas Finley
 */

public class FirstFollowModel extends AbstractTableModel {
    /**
     * Instantiates a new <CODE>FirstFollowModel</CODE>.
     * @param grammar the grammar for the first follow model, from
     * which the variables are extracted for the first column
     */
    public FirstFollowModel(Grammar grammar) {
	variables = grammar.getVariables();
	Arrays.sort(variables);
	terminals = grammar.getTerminals();
	
	firstSets = new String[variables.length];
	followSets = new String[variables.length];
	Arrays.fill(firstSets, "");
	Arrays.fill(followSets, "");
    }

    /**
     * Retrives the first sets as shown in the table.
     * @return the first sets, a map from all single symbols A in the grammar
     * to the set of symbols that represent FIRST(A)
     */
    public Map getFirst() {
	return null;
    }

    /**
     * Retrieves the follow sets as shown in the table.
     * @return the follow sets, a map from all single variables A in
     * the grammar to the set of symbols that represent FOLLOW(A)
     */
    public Map getFollow() {
	return null;
    }

    /**
     * Set if the user can edit the first sets at this time.
     * @param canEdit <CODE>true</CODE> if editing is allowed
     */
    public void setCanEditFirst(boolean canEdit) {
	canEditColumn[1] = canEdit;
    }

    /**
     * Set if the user can edit the follow sets at this time.
     * @param canEdit <CODE>true</CODE> if editing is allowed
     */
    public void setCanEditFollow(boolean canEdit) {
	canEditColumn[2] = canEdit;
    }

    /**
     * There are as many rows as there are variables.
     * @return the number of variables
     */
    public int getRowCount() {
	return variables.length;
    }

    /**
     * There are always three columns for the variables, the first
     * sets, and the follow sets.
     * @return 3
     */
    public int getColumnCount() {
	return 3;
    }

    /**
     * Returns the name of a particular column.
     * @param column the index of a column to get the name for
     */
    public String getColumnName(int column) {
	return COLUMN_NAMES[column];
    }

    /**
     * Returns the value at each column.
     * @param row the row to get data for
     * @param column the column to get data for
     * @return the data for this column
     */
    public Object getValueAt(int row, int column) {
	switch (column) {
	case 0: return variables[row];
	case 1: return firstSets[row];
	case 2: return followSets[row];
	}
	return null;
    }

    /**
     * Returns if a table cell can be edited.
     * @param row the row of the cell
     * @param column the column of the cell
     */
    public boolean isCellEditable(int row, int column) {
	return canEditColumn[column];
    }

    /**
     * Returns the character set at a particular location.
     * @param row the row to get the set for; this will be a set for
     * the variable at this row
     * @param column the column to get the set for, which will be 1
     * for first and 2 for follow
     */
    public Set getSet(int row, int column) {
	String s = (String) getValueAt(row,column);
	Set set = new TreeSet();
	for (int i=0; i<s.length(); i++) {
	    if (s.charAt(i)=='!') {
		set.add("");
		continue;
	    }
	    set.add(s.substring(i,i+1));
	}
	return set;
    }

    /**
     * Sets the value at each column.
     * @param value the new value
     * @param row the row to change
     * @param column the column to change
     */
    public void setValueAt(Object value, int row, int column) {
	switch (column) {
	case 0:
	    variables[row] = (String) value;
	    break;
	case 1:
	    firstSets[row] = (String) value;
	    break;
	case 2:
	    followSets[row] = (String) value;
	    break;
	}
    }

    /**
     * Sets the set at a particular cell.
     * @param set the set for the cell
     * @param row the row index of the cell to set the set for
     * @param column the column index of the cell to set the set for
     */
    public void setSet(Set set, int row, int column) {
	StringBuffer sb = new StringBuffer();
	Iterator it = set.iterator();
	while (it.hasNext()) {
	    String element = (String) it.next();
	    if (element.length() == 0) element = "!";
	    sb.append(element);
	}
	setValueAt(sb.toString(), row, column);
    }

    /** The variables. */
    private String[] variables;
    /** The terminals. */
    private String[] terminals;
    /** The user defined first sets strings. */
    private String[] firstSets;
    /** The user defined follow sets strings. */
    private String[] followSets;
    /** The permissions to edit each column. */
    private boolean[] canEditColumn = new boolean[] {false, false, false};

    /** The lambda string. */
    public static String LAMBDA = "\u03BB";
    /** The names of columns. */
    //public static String[] COLUMN_NAMES = {"Variable", "First", "Follow"};
    public static String[] COLUMN_NAMES = {" ", "First", "Follow"};
}
