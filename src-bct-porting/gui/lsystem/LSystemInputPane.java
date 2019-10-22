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
 
package gui.lsystem;

import grammar.Production;
import grammar.UnboundGrammar;
import grammar.lsystem.LSystem;
import gui.HighlightTable;
import gui.grammar.GrammarInputPane;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

/**
 * The <CODE>LSystemInputPane</CODE> is a pane used to input and
 * display the textual representation of an L-system.
 * 
 * @author Thomas Finley
 */

public class LSystemInputPane extends JPanel {
    /**
     * Instantiates an empty <CODE>LSystemInputPane</CODE>.
     */
    public LSystemInputPane() {
	this(SYSTEM);
    }

    /**
     * Instantiates an <CODE>LSystemInputPane</CODE> for a given
     * <CODE>LSystem</CODE>.
     * @param lsystem the lsystem to display
     */
    public LSystemInputPane(LSystem lsystem) {
	super(new BorderLayout());
	initializeStructures(lsystem);
	initializeListener();
	initializeView();
    }

    /**
     * Initializes the data structures and the subviews.
     * @param lsystem the L-system to initialize the views on
     */
    private void initializeStructures(LSystem lsystem) {
	// Create the axiom text field.
	axiomField = new JTextField(listAsString(lsystem.getAxiom()));
	// Create the grammar view that holds replacement productions.
	Set replacements = lsystem.getSymbolsWithReplacements();
	Iterator it = replacements.iterator();
	UnboundGrammar g = new UnboundGrammar();
	while (it.hasNext()) {
	    String symbol = (String) it.next();
	    java.util.List[] r = lsystem.getReplacements(symbol);
	    for (int i=0; i<r.length; i++) {
		Production p = new Production(symbol, listAsString(r[i]));
		g.addProduction(p);
	    }
	}
	productionInputPane = new GrammarInputPane(g);
	// Create the parameter table model.
	parameterModel = new ParameterTableModel(lsystem.getValues());
	// We may as well use this as our cached system.
	cachedSystem = lsystem;
    }

    /**
     * Lays out the subviews in this view.
     */
    private void initializeView() {
	// Create the view for the axiom text field.
	JPanel axiomView = new JPanel(new BorderLayout());
	axiomView.add(new JLabel("Axiom: "), BorderLayout.WEST);
	axiomView.add(axiomField, BorderLayout.CENTER);
	add(axiomView, BorderLayout.NORTH);
	// Create the view for the grammar pane and the rest.
	parameterTable = new HighlightTable(parameterModel);
	JScrollPane scroller = new JScrollPane(parameterTable);
	Dimension bestSize=new Dimension(400,200);
	/*parameterTable.setPreferredSize(bestSize);
	productionInputPane.getTable().setPreferredSize(bestSize);*/
	productionInputPane.setPreferredSize(bestSize);
	scroller.setPreferredSize(bestSize);
	JSplitPane split = new JSplitPane
	    (JSplitPane.VERTICAL_SPLIT, productionInputPane, scroller);
	add(split, BorderLayout.CENTER);
	// Finally, show the grid.
	parameterTable.setShowGrid(true);
	parameterTable.setGridColor(Color.lightGray);
	
	scroller.setVerticalScrollBarPolicy
	    (JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

	/*final JComboBox box = new JComboBox
	    ((String[]) Renderer.ASSIGN_WORDS.toArray(new String[0]));
	box.setLightWeightPopupEnabled(false);
	scroller.setCorner(JScrollPane.UPPER_RIGHT_CORNER, box);
	box.addItemListener(new ItemListener() {
		public void itemStateChanged(ItemEvent e) {
		    if (e.getStateChange() != e.SELECTED) return;
		    String s = (String) e.getItem();
		    box.setSelectedIndex(-1); // No selection!
		    setEditing(s);
		}
		});*/
	
	final JPopupMenu menu = new JPopupMenu();
	ActionListener listener = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
		    setEditing(e.getActionCommand());
		} };
	String[] words = 
	    (String[]) Renderer.ASSIGN_WORDS.toArray(new String[0]);
	for (int i=0; i<words.length; i++) {
	    menu.add(words[i]).addActionListener(listener);
	}
	JPanel c = new JPanel();
	c.addMouseListener(new MouseAdapter() {
		public void mousePressed(MouseEvent e) {
		    menu.show((Component)e.getSource(), e.getPoint().x,
			      e.getPoint().y);
		} });
	scroller.setCorner(JScrollPane.UPPER_RIGHT_CORNER, c);
    }

    /**
     * Creates the listener to update the edited-ness.
     */
    public void initializeListener() {
	axiomField.getDocument().addDocumentListener(new DocumentListener() {
		public void changedUpdate(DocumentEvent e) {
		    fireLSystemInputEvent();
		}
		public void removeUpdate(DocumentEvent e) {
		    fireLSystemInputEvent();
		}
		public void insertUpdate(DocumentEvent e) {
		    fireLSystemInputEvent();
		}
	    });
	TableModelListener tml = new TableModelListener() {
		public void tableChanged(TableModelEvent e) {
		    fireLSystemInputEvent();
		}
	    };
	parameterModel.addTableModelListener(tml);
	productionInputPane.getTable().getModel().addTableModelListener(tml);
    }

    /**
     * Given a list of objects, this converts it to a space delimited
     * string.
     * @param list the list to convert to a string
     * @return a string containing the elements of the list
     */
    public static String listAsString(java.util.List list) {
	Iterator it = list.iterator();
	if (!it.hasNext()) return "";
	StringBuffer sb = new StringBuffer();
	sb.append(it.next());
	while (it.hasNext()) {
	    sb.append(' ');
	    sb.append(it.next());
	}
	return sb.toString();
    }

    /**
     * Returns the L-system this pane displays.
     * @return the L-system this pane displays
     */
    public LSystem getLSystem() {
	// Make sure we're not editing anything.
	if (productionInputPane.getTable().getCellEditor() != null)
	    productionInputPane.getTable().getCellEditor().stopCellEditing();
	if (parameterTable.getCellEditor() != null)
	    parameterTable.getCellEditor().stopCellEditing();
	// Do we already have a cached copy?
	try {
	    if (cachedSystem == null)
		cachedSystem=new LSystem
		    (axiomField.getText(),
		     productionInputPane.getGrammar(UnboundGrammar.class),
		     parameterModel.getParameters());
	} catch (IllegalArgumentException e) {
	    JOptionPane.showMessageDialog
		(this, e.getMessage(), "L-System Error",
		 JOptionPane.ERROR_MESSAGE);
	}
	return cachedSystem;
    }

    /**
     * Adds an L-system input listener.
     * @param listener the listener to start sending change events to
     */
    public void addLSystemInputListener(LSystemInputListener listener) {
	lSystemInputListeners.add(listener);
    }

    /**
     * Removes an L-system input listener.
     * @param listener the listener to stop sending change events to
     */
    public void removeLSystemInputListener(LSystemInputListener listener) {
	lSystemInputListeners.remove(listener);
    }

    /**
     * Fires a notification to listeners that the L-system was changed.
     */
    protected void fireLSystemInputEvent() {
	cachedSystem = null;
	Iterator it = lSystemInputListeners.iterator();
	while (it.hasNext())
	    ((LSystemInputListener) (it.next())).lSystemChanged(reusedEvent);
    }

    /**
     * This will edit the value for a particular parameter in the
     * parameter table.  If no such value exists yet it shall be
     * created.  The value field in the table shall be edited.
     * @param item the key of the value we want to edit
     */
    private void setEditing(String item) {
	int i;
	for (i=0; i<parameterModel.getRowCount(); i++)
	    if (parameterModel.getValueAt(i,0).equals(item)) break;
	if (i==parameterModel.getRowCount()) // We need to create it.
	    parameterModel.setValueAt(item, --i, 0);
	int column = parameterTable.convertColumnIndexToView(1);
	parameterTable.editCellAt(i, column);
	parameterTable.requestFocus();
    }

    /** An empty L-system. */
    private static final LSystem SYSTEM = new LSystem();
    /** The axiom text field. */
    private JTextField axiomField;
    /** The production view. */
    private GrammarInputPane productionInputPane;
    /** The parameter table model. */
    private ParameterTableModel parameterModel;
    /** The parameter table view. */
    private HighlightTable parameterTable;
    /** The set of input listeners. */
    private Set lSystemInputListeners = new HashSet();
    /** The event reused in firing off the notifications. */
    private LSystemInputEvent reusedEvent = new LSystemInputEvent(this);
    /** The cached L-system.  Firing an L-S input event invalidates this. */
    private LSystem cachedSystem = null;
}
