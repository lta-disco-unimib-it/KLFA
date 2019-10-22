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
 
package gui.sim.multiple;

import gui.GrowableTableModel;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

import automata.Automaton;
import automata.turing.TuringMachine;

/**
 * The <CODE>InputTableModel</CODE> is a table specifically used for
 * the input of multiple inputs for simulation in an automaton.
 * 
 * @author Thomas Finley
 */

public class InputTableModel extends GrowableTableModel {
    /**
     * This instantiates an <CODE>InputTableModel</CODE>.
     * @param automaton the automaton that we're inputting stuff for
     */
    public InputTableModel(Automaton automaton) {
	super(inputsForMachine(automaton)+1);
    }

    /**
     * This instantiates a copy of the <CODE>InputTableModel</CODE>.
     * @param model the model to copy
     */
    public InputTableModel(InputTableModel model) {
	super(model);
    }

    /**
     * Initializes the contents of a new array to be all blank strings.
     */
    protected Object[] initializeRow(int row) {
	Object[] nr = super.initializeRow(row);
	Arrays.fill(nr, "");
	return nr;
    }

    /**
     * This returns the name of the columns.  In the input table
     * model, each column is titled "Input #", where # is replaced
     * with the number of the column (e.g. Input 1, then Input 2),
     * unless there is only one input column, in which case the single
     * input column is just called input.  The last column is always
     * reserved for the result.
     * @param column the number of the column to get the name for
     * @return the name of the column
     */
    public String getColumnName(int column) {
	if (column == getColumnCount()-1) return "Result";
	if (getColumnCount() == 2) return "Input";
	return "Input "+(column+1);
    }

    /**
     * This returns an array of the inputs for the table.  The input
     * is organized by arrays of arrays of strings.  The first index
     * of the array is the row of the input.  The second index of the
     * array is the particular 
     * @return an array of inputs, the first index corresponds
     * directly to the row, the second to the column
     */
    public String[][] getInputs() {
	String[][] inputs = new String[getRowCount()-1][getColumnCount()-1];
	for (int r=0; r<inputs.length; r++)
	    for (int c=0; c<inputs[r].length; c++)
		inputs[r][c] = (String) getValueAt(r,c);
	return inputs;
    }

    /**
     * This returns if a cell is editable.  In this model, a cell is
     * editable if it's anything other than the last column, which is
     * where the results are reported.
     * @param row the row to check for editableness
     * @param column the column to check for editableness
     * @return by default this returns <CODE>true</CODE> if this is
     * any column other than the last column; in that instance this
     * returns <CODE>false</CODE>
     */
    public boolean isCellEditable(int row, int column) {
	return column != getColumnCount()-1;
    }

    /**
     * Returns the number of inputs needed for this type of automaton.
     * @param automaton the automaton to pass in
     * @return the number of input strings needed for this automaton;
     * e.g., 2 for a two tape turing machine, 1 for most anything else
     */
    public static int inputsForMachine(Automaton automaton) {
	return automaton instanceof TuringMachine ? 
	    ((TuringMachine) automaton).tapes() : 1;
    }

    /**
     * This returns the cached table model for an automaton of this
     * type.  It is desirable that automatons, upon asking to run
     * input, should be presented with the same data in the same table
     * since multiple inputs tables are oft used to test the same sets
     * of input on different automaton's again and again.  In the
     * event that there are multiple models active, this method
     * returns the last table model that was modified.  If there have
     * been no applicable table models cached yet, then a blank table
     * model is created.
     * @param automaton the automaton to get a model for
     * @return a copy of the model that was last edited with the
     * correct number of inputs for this automaton
     */
    public static InputTableModel getModel(Automaton automaton) {
	InputTableModel model = (InputTableModel) INPUTS_TO_MODELS
	    .get(new Integer(inputsForMachine(automaton)));
	if (model != null) {
	    model = new InputTableModel(model);
	    // Clear out the results column.
	    for (int i=0; i<(model.getRowCount()-1); i++)
		model.setResult(i, "");
	} else {
	    model = new InputTableModel(automaton);
	}
	model.addTableModelListener(LISTENER);
	return model;
    }

    /**
     * Sets the result string for a particular row.
     * @param row the row to set the result of
     * @param result the result to put in the result column
     */
    public void setResult(int row, String result) {
	setValueAt(result, row, getColumnCount()-1);
    }

    /** The static table model listener for caching. */
    private final static TableModelListener LISTENER =
	new TableModelListener() {
	    public void tableChanged(TableModelEvent event) {
		InputTableModel model = (InputTableModel) event.getSource();
		Integer inputs = new Integer(model.getColumnCount()-1);
		INPUTS_TO_MODELS.put(inputs, model);
	    }
	};
    /** The map of number of inputs (stored as integers) to input
     * table models. */
    private final static Map INPUTS_TO_MODELS = new HashMap();
}
