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
 
package gui.regular;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import automata.Transition;

/**
 * This shows a bunch of transitions for the step of the conversion
 * when the states of the automaton are being removed one by one.  A
 * {@link gui.regular.FSAToREController} object is reported back to
 * when certain actions happen in the window.
 * 
 * @see gui.regular.FSAToREController#finalizeStateRemove
 * @see gui.regular.FSAToREController#finalize
 * 
 * @author Thomas Finley
 */

public class TransitionWindow extends JFrame {
    /**
     * Instantiates a new <CODE>TransitionWindow</CODE>.
     * @param controller the FSA to RE controller object
     */
    public TransitionWindow(FSAToREController controller) {
	super("Transitions");
	this.controller = controller;
	// Init the GUI.
	setSize(250,400);
	getContentPane().setLayout(new BorderLayout());
	getContentPane().add
	    (new JLabel("Select to see what transitions were combined."),
	     BorderLayout.NORTH);
	getContentPane().add(new JScrollPane(table), BorderLayout.CENTER);
	getContentPane().add(new JButton(new AbstractAction("Finalize") {
		public void actionPerformed(ActionEvent e) {
		    TransitionWindow.this.controller.finalizeStateRemove();
		} }), BorderLayout.SOUTH);
	// Have the listener to the transition.
	table.getSelectionModel().addListSelectionListener
	    (new ListSelectionListener() {
		    public void valueChanged(ListSelectionEvent e) {
			if (table.getSelectedRowCount()!=1) {
			    TransitionWindow.this.controller.
				tableTransitionSelected(null);
			    return;
			}
			Transition t = transitions[table.getSelectedRow()];
			TransitionWindow.this.controller.
			    tableTransitionSelected(t);;
		    }
		});
    }

    /**
     * Returns the transition this transition window displays.
     * @return the array of transitions displayed by this window
     */
    public Transition[] getTransitions() {
	return transitions;
    }

    /**
     * Sets the array of transitions the table in this window
     * displays, and shows the window.
     * @param transitions the new array of transitions
     */
    public void setTransitions(Transition[] transitions) {
	this.transitions = transitions;
	table.setModel(new TransitionTableModel(transitions));
    }
    
    /** The controller object for this window. */
    private FSAToREController controller;
    /** The array of transitions displayed. */
    private Transition[] transitions = new Transition[0];
    /** The table object that displays the transitions. */
    private JTable table = new JTable(new TransitionTableModel());
}
