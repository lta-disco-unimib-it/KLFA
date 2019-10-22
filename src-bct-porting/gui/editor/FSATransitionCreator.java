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
 
package gui.editor;

import gui.viewer.AutomatonPane;

import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;

import automata.State;
import automata.Transition;
import automata.fsa.FSATransition;

/**
 * This is a transition creator for finite state automata.
 * 
 * @author Thomas Finley
 */

public class FSATransitionCreator extends TableTransitionCreator {
    /**
     * Instantiates a transition creator.
     * @param parent the parent object that any dialogs or windows
     * brought up by this creator should be the child of
     */
    public FSATransitionCreator(AutomatonPane parent) {
	super(parent);
    }

    /**
     * Initializes a new empty transition.
     * @param from the from state
     * @param to to too state
     */
    protected Transition initTransition(State from, State to) {
	return new FSATransition(from, to, "");
    }

    /**
     * Creates a new table model.
     * @param transition the transition to create the model for
     */
    protected TableModel createModel(Transition transition) {
	final FSATransition t = (FSATransition) transition;
	return new AbstractTableModel() {
		public Object getValueAt(int row, int column) {
		    return s;
		}
		public void setValueAt(Object o, int r, int c) {
		    s = (String) o;
		}
		public boolean isCellEditable(int r, int c) {return true;}
		public int getRowCount() {return 1;}
		public int getColumnCount() {return 1;}
		public String getColumnName(int c) {return "Label";}
		String s = t.getLabel();
	    };
    }

    /**
     * Modifies a transition according to what's in the table.
     */
    public boolean modifyTransition(Transition transition, TableModel model) {
	String s = (String) model.getValueAt(0,0);
	FSATransition t = (FSATransition) transition;
	try {
	    t.setLabel(s);
	} catch (IllegalArgumentException e) {
	    reportException(e);
	    return false;
	}
	return true;
    }
}
