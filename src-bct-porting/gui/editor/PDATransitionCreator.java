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

import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;

import automata.State;
import automata.Transition;
import automata.pda.PDATransition;

/**
 * This is the creator of transitions in push down automata.
 * 
 * @author Thomas Finley
 */

public class PDATransitionCreator extends TableTransitionCreator {
    /**
     * Instantiates a new <CODE>PDATransitionCreator</CODE>.
     * @param parent the parent of whatever dialogs/windows get
     * brought up by this creator
     */
    public PDATransitionCreator(AutomatonPane parent) {
	super(parent);
    }

    /**
     * Initializes a new empty transition.
     * @param from the from state
     * @param to to too state
     */
    protected Transition initTransition(State from, State to) {
	return new PDATransition(from, to, "", "", "");
    }

    /**
     * Creates a new table model.
     * @param transition the transition to create the model for
     * @return a table model for the transition
     */
    protected TableModel createModel(Transition transition) {
	final PDATransition t = (PDATransition) transition;
	return new AbstractTableModel() {
		public Object getValueAt(int row, int column) {
		    return s[column];
		}
		public void setValueAt(Object o, int r, int c) {
		    s[c] = (String) o;
		}
		public boolean isCellEditable(int r, int c) {return true;}
		public int getRowCount() {return 1;}
		public int getColumnCount() {return 3;}
		public String getColumnName(int c) {return NAME[c];}
		String s[] = new String[] 
		{t.getInputToRead(), t.getStringToPop(), t.getStringToPush()};
	    };
    }
    private static final String NAME[] = {"Read", "Pop", "Push"};

    /**
     * Modifies a transition according to what's in the table.
     */
    public boolean modifyTransition(Transition transition, TableModel model) {
	String input = (String) model.getValueAt(0,0);
	String pop = (String) model.getValueAt(0,1);
	String push = (String) model.getValueAt(0,2);
	PDATransition t = (PDATransition) transition;
	try {
	    t.setInputToRead(input);
	    t.setStringToPop(pop);
	    t.setStringToPush(push);
	} catch (IllegalArgumentException e) {
	    reportException(e);
	    return false;
	}
	return true;
    }
}
