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

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.ref.Reference;
import java.lang.ref.WeakReference;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import regular.ExpressionChangeEvent;
import regular.ExpressionChangeListener;
import regular.RegularExpression;

/**
 * The editor pane for a regular expression allows the user to change
 * the regular expression.
 * 
 * @author Thomas Finley
 */

public class EditorPane extends JPanel {
    /**
     * Instantiates a new editor pane for a given regular expression.
     * @param expression the regular expression
     */
    public EditorPane(RegularExpression expression) {
	//super(new BorderLayout());
	this.expression = expression;
	field.setText(expression.asString());
	field.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent event) {
		    updateExpression();
		} });
	field.getDocument().addDocumentListener(new DocumentListener() {
		public void insertUpdate(DocumentEvent e) {
		    updateExpression();
		}
		public void removeUpdate(DocumentEvent e) {
		    updateExpression();
		}
		public void changedUpdate(DocumentEvent e) {
		    updateExpression();
		}
	    });
	setLayout(new GridBagLayout());
	GridBagConstraints c = new GridBagConstraints();
	c.fill = GridBagConstraints.BOTH;
	c.weightx = 1.0;
	c.gridwidth = GridBagConstraints.REMAINDER;

	add(new JLabel("Edit the regular expression below:"), c);
	add(field, c);
    }

    /**
     * This is called when the regular expression should be updated to
     * accord with the field.
     */
    private void updateExpression() {
	expression.change(ref);
    }

    /** The regular expression. */
    private RegularExpression expression;
    /** The field where the expression is displayed and edited. */
    private JTextField field = new JTextField("");

    /**
     * The expression change listener for a regular expression detects
     * if there are changes in the environment, and if so, changes the
     * display. */
    private ExpressionChangeListener listener =
	new ExpressionChangeListener() {
	    public void expressionChanged(ExpressionChangeEvent e) {
	    field.setText(e.getExpression().asString());
	} };
    
    /** The reference object. */
    private Reference ref = new WeakReference(null) {
	    public Object get() {
		return field.getText();
	    } };
}
