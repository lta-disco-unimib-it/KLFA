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
 
package gui.grammar.convert;

import grammar.Grammar;
import grammar.Production;
import gui.event.SelectionEvent;
import gui.event.SelectionListener;

import javax.swing.ButtonGroup;
import javax.swing.JTable;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

/**
 * The <CODE>GrammarViewer</CODE> is a class for the graphical
 * non-editable viewing of grammars, with an extra field for a
 * checkbox to indicate that a production has been "processed," though
 * what exactly that means is left to the context in which this
 * component is used.
 * 
 * @author Thomas Finley
 */

public class GrammarViewer extends JTable {
    /**
     * Instantiates a new <CODE>GrammarViewer</CODE>.
     * @param grammar the grammar to display in this view
     */
    public GrammarViewer(Grammar grammar) {
	setModel(new GrammarTableModel());
	this.grammar = grammar;
	//setLayout(new BorderLayout());
	Production[] prods = grammar.getProductions();
	data = new Object[prods.length][2];
	Object[] columnNames = {"Production", "Created"};

	for (int i=0; i<prods.length; i++) {
	    data[i][0] = prods[i];
	    data[i][1] = Boolean.FALSE;
	    productionToRow.put(prods[i], new Integer(i));
	}
	DefaultTableModel model = (DefaultTableModel) getModel();
	model.setDataVector(data, columnNames);

	// Set the listener to the selectedness.
	getSelectionModel().addListSelectionListener(listSelectListener);
    }

    /**
     * Returns the <CODE>Grammar</CODE> that this
     * <CODE>GrammarViewer</CODE> displays.
     * @return this viewer's grammar
     */
    public Grammar getGrammar() {
	return grammar;
    }

    /**
     * Adds a selection listener to this grammar viewer.  The listener
     * will receive events whenever the selection changes.
     * @param listener the selection listener to add
     */
    public void addSelectionListener(SelectionListener listener) {
	selectionListeners.add(listener);
    }

    /**
     * Removes a selection listener from this grammar viewer.
     * @param listener the selection listener to remove
     */
    public void removeSelectionListener(SelectionListener listener) {
	selectionListeners.remove(listener);
    }

    /**
     * Distributes a selection event.
     */
    protected void distributeSelectionEvent() {
	java.util.Iterator it = selectionListeners.iterator();
	while (it.hasNext()) {
	    SelectionListener listener = (SelectionListener) it.next();
	    listener.selectionChanged(EVENT);
	}
    }

    /**
     * Returns the currently selected productions.
     * @return the currently selected productions
     */
    public Production[] getSelected() {
	int[] rows = getSelectedRows();
	Production[] selected = new Production[rows.length];
	for (int i=0; i<rows.length; i++)
	    selected[i] = (Production) data[rows[i]][0];
	return selected;
    }

    /**
     * Sets the indicated production as either checked or unchecked
     * appropriately.
     * @param production the production to set the "checkyness" for
     * @param checked <CODE>true</CODE> if the production should be
     * marked as checked, <CODE>false</CODE> if unchecked
     */
    public void setChecked(Production production, boolean checked) {
	Integer r = (Integer) productionToRow.get(production);
	if (r == null) return;
	int row = r.intValue();
	Boolean b = checked ? Boolean.TRUE : Boolean.FALSE;
	data[row][1] = b;
	((DefaultTableModel)getModel()).setValueAt(b, row, 1);
    }

    /** The grammar to display. */
    private Grammar grammar;
    /** The button group. */
    private ButtonGroup bgroup = new ButtonGroup();
    /** The data of the table. */
    private Object[][] data;
    /** The mapping of productions to a row (rows stored as Integer). */
    private java.util.Map productionToRow = new java.util.HashMap();

    /** The selection event. */
    private SelectionEvent EVENT = new SelectionEvent(this);
    /** The set of selection listeners. */
    private java.util.Set selectionListeners = new java.util.HashSet();

    private ListSelectionListener listSelectListener =
	new ListSelectionListener() {
	    public void valueChanged(ListSelectionEvent e) {
		distributeSelectionEvent();
	    }
	};

    /**
     * The model for this table.
     */
    private class GrammarTableModel extends DefaultTableModel {
	public boolean isCellEditable(int row, int column) {
	    return false;
	}
	
	public Class getColumnClass(int columnIndex) {
	    if (columnIndex == 1) return Boolean.class;
	    return super.getColumnClass(columnIndex);
	}
    }
}
