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
