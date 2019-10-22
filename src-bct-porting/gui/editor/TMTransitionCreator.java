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
 
package gui.editor;

import gui.viewer.AutomatonPane;

import java.awt.Component;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ActionMap;
import javax.swing.DefaultCellEditor;
import javax.swing.InputMap;
import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.KeyStroke;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;

import automata.State;
import automata.Transition;
import automata.turing.TMTransition;
import automata.turing.TuringMachine;

/**
 * This is the creator of transitions in turing machines.
 * 
 * @author Thomas Finley
 */

public class TMTransitionCreator extends TableTransitionCreator {
    /**
     * Instantiates a new <CODE>TMTransitionCreator</CODE>.
     * @param parent the parent of whatever dialogs/windows get
     * brought up by this creator
     */
    public TMTransitionCreator(AutomatonPane parent) {
	super(parent);
	machine = (TuringMachine) parent.getDrawer().getAutomaton();
    }

    /**
     * Initializes a new empty transition.
     * @param from the from state
     * @param to to too state
     */
    protected Transition initTransition(State from, State to) {
	String[] readWrite = new String[machine.tapes()];
	for (int i=0; i<readWrite.length; i++) readWrite[i] = "";
	String[] direction = new String[machine.tapes()];
	for (int i=0; i<direction.length; i++) direction[i] = "R";
	Transition t =
	    new TMTransition(from, to, readWrite, readWrite, direction);
	return t;
    }

    /**
     * Given a transition, returns the arrays the editing table model needs.
     * @param transition the transition to build the arrays for
     * @return the arrays for the editing table model
     */
    private String[][] arraysForTransition(TMTransition transition) {
	String[][] s = new String[machine.tapes()][3];
	for (int i=machine.tapes()-1; i>=0; i--) {
	    s[i][0] = transition.getRead(i);
	    s[i][1] = transition.getWrite(i);
	    if (s[i][0].equals(TMTransition.BLANK)) s[i][0] = "";
	    if (s[i][1].equals(TMTransition.BLANK)) s[i][1] = "";
	    s[i][2] = transition.getDirection(i);
	}
	return s;
    }

    /**
     * Creates a new table model.
     * @param transition the transition to create the model for
     * @return a table model for the transition
     */
    protected TableModel createModel(Transition transition) {
	final TMTransition t = (TMTransition) transition;
	return new AbstractTableModel() {
		public Object getValueAt(int row, int column) {
		    return s[row][column];
		}
		public void setValueAt(Object o, int r, int c) {
		    s[r][c] = (String) o;
		}
		public boolean isCellEditable(int r, int c) {return true;}
		public int getRowCount() {return machine.tapes();}
		public int getColumnCount() {return 3;}
		public String getColumnName(int c) {return name[c];}
		String s[][] = arraysForTransition(t);
		String name[] = {"Read", "Write", "Direction"};
	    };
    }

    /**
     * Creates the table.
     */
    protected JTable createTable(Transition transition) {
	JTable table = super.createTable(transition);
	TableColumn directionColumn = table.getColumnModel().getColumn(2);
	directionColumn.setCellEditor(new DefaultCellEditor(BOX) {
		public Component getTableCellEditorComponent
		    (JTable table, Object value, boolean isSelected,
		     int row, int column) {
		    final JComboBox c = (JComboBox)
			super.getTableCellEditorComponent
			(table, value, isSelected, row, column);
		    InputMap imap = c.getInputMap();
		    ActionMap amap = c.getActionMap();
		    Object o = new Object();
		    amap.put(o, CHANGE_ACTION);
		    for (int i=0; i<STROKES.length; i++)
			imap.put(STROKES[i], o);
		    return c;
		} });
	return table;
    }

    /**
     * Modifies a transition according to what's in the table.
     */
    public boolean modifyTransition(Transition transition, TableModel model) {
	TMTransition t = (TMTransition) transition;
	try {
	    for (int i=0; i<machine.tapes(); i++) {
		String read = (String) model.getValueAt(i,0);
		String write = (String) model.getValueAt(i,1);
		String dir = (String) model.getValueAt(i,2);
		t.setRead(read, i);
		t.setWrite(write, i);
		t.setDirection(dir, i);
	    }
	} catch (IllegalArgumentException e) {
	    reportException(e);
	    return false;
	}
	return true;
    }

    /** The Turing machine. */
    private TuringMachine machine;
    /** The directions. */
    private static final String[] DIRS = new String[] {"R", "S", "L"};
    /** The direction field combo box. */
    private static final JComboBox BOX = new JComboBox(DIRS);
    /** The array of keystrokes for the direction field. */
    private static final KeyStroke[] STROKES;
    /** The action for the strokes for the direction field. */
    private static final Action CHANGE_ACTION =
	new AbstractAction() {
	    public void actionPerformed(ActionEvent e) {
		JComboBox box = (JComboBox) e.getSource();
		box.setSelectedItem(e.getActionCommand().toUpperCase());
	    } };

    static {
	STROKES = new KeyStroke[DIRS.length];
	for (int i=0; i<STROKES.length; i++)
	    STROKES[i] = KeyStroke.getKeyStroke("shift "+DIRS[i]);
    }
}
