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
 
package gui.grammar.parse;

import grammar.Grammar;
import gui.SplitPaneFactory;
import gui.environment.GrammarEnvironment;
import gui.grammar.GrammarTable;
import gui.grammar.GrammarTableModel;
import gui.tree.DefaultTreeDrawer;
import gui.tree.LeafNodePlacer;
import gui.tree.TreePanel;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.AbstractAction;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.table.DefaultTableModel;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;

/**
 * The parse pane is an abstract class that defines the interface
 * common between parsing panes.
 * 
 * @author Thomas Finley
 */

abstract class ParsePane extends JPanel {
    /**
     * Instantiates a new parse pane.  This will not place components.
     * A call to {@link #initView} by a subclass is necessary.
     * @param grammar the grammar that is being parsed
     */
    public ParsePane(GrammarEnvironment environment, Grammar grammar) {
	super(new BorderLayout());
	this.grammar = grammar;
	this.environment = environment;
    }

    /**
     * Initializes the GUI.
     */
    protected void initView() {
	treePanel = initTreePanel();

	// Sets up the displays.
	JComponent pt = initParseTable();
	JScrollPane parseTable = pt == null ? null : 
	    new JScrollPane(initParseTable());
	JScrollPane grammarTable = new JScrollPane(initGrammarTable(grammar));

	treeDerivationPane.add(initTreePanel(), "0");
	derivationPane = new JScrollPane(initDerivationTable());
	treeDerivationPane.add(derivationPane, "1");
	bottomSplit = SplitPaneFactory.createSplit
	    (environment, true, 0.3, grammarTable, treeDerivationPane);
	topSplit = SplitPaneFactory.createSplit
	  (environment, true, 0.4, parseTable, initInputPanel());
	mainSplit = SplitPaneFactory.createSplit
	    (environment, false, 0.3, topSplit, bottomSplit);
	add(mainSplit, BorderLayout.CENTER);
	add(statusDisplay, BorderLayout.SOUTH);
    }

    /**
     * Initializes a table for the grammar.
     * @param grammar the grammar
     * @return a table to display the grammar
     */
    protected GrammarTable initGrammarTable(Grammar grammar) {
	grammarTable = new GrammarTable(new GrammarTableModel(grammar) {
		public boolean isCellEditable(int r, int c) {return false;}
	    });
	return grammarTable;
    }

    /**
     * Returns the interface that holds the input area.
     */
    protected JPanel initInputPanel() {
	JPanel bigger = new JPanel(new BorderLayout());
	JPanel panel = new JPanel();
	GridBagLayout gridbag = new GridBagLayout();
	GridBagConstraints c = new GridBagConstraints();
	panel.setLayout(gridbag);
	
	c.fill = GridBagConstraints.BOTH;

	c.weightx = 0.0;
	panel.add(new JLabel("Input"), c);
	c.weightx = 1.0;
	c.gridwidth = GridBagConstraints.REMAINDER;
	panel.add(inputField, c);
	inputField.addActionListener(startAction);
	//c.weightx = 0.0;
	//JButton startButton = new JButton(startAction);
	//panel.add(startButton, c);
	
	c.weightx = 0.0;
	c.gridwidth = 1;
	panel.add(new JLabel("Input Remaining"), c);
	c.weightx = 1.0;
	c.gridwidth = GridBagConstraints.REMAINDER;
	inputDisplay.setEditable(false);
	panel.add(inputDisplay, c);

	c.weightx = 0.0;
	c.gridwidth = 1;
	panel.add(new JLabel("Stack"), c);
	c.weightx = 1.0;
	c.gridwidth = GridBagConstraints.REMAINDER;
	stackDisplay.setEditable(false);
	panel.add(stackDisplay, c);
	
	bigger.add(panel, BorderLayout.CENTER);
	bigger.add(initInputToolbar(), BorderLayout.NORTH);

	return bigger;
    }

    /**
     * Returns the choices for the view.
     * @return an array of strings for the choice of view
     */
    protected String[] getViewChoices() {
	return new String[] {"Noninverted Tree", "Inverted Tree",
				 "Derivation Table"};
    }

    /**
     * Returns the tool bar for the main user input panel.
     * @return the tool bar for the main user input panel
     */
    protected JToolBar initInputToolbar() {
	JToolBar toolbar = new JToolBar();
	toolbar.add(startAction);
	stepAction.setEnabled(false);
	toolbar.add(stepAction);

	// Set up the view customizer controls.
	toolbar.addSeparator();

	final JComboBox box = new JComboBox(getViewChoices());
	box.setSelectedIndex(0);
	ActionListener listener = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
		    changeView((String) box.getSelectedItem());
		} };
	box.addActionListener(listener);
	toolbar.add(box);
	return toolbar;
    }

    /**
     * Changes the view.
     * @param name the view button name that was pressed
     */
    private void changeView(String name) {
	if (name.equals("Noninverted Tree")) {
	    treeDerivationLayout.first(treeDerivationPane);
	    treeDrawer.setInverted(false);
	    treePanel.repaint();
	} else if (name.equals("Inverted Tree")) {
	    treeDerivationLayout.first(treeDerivationPane);
	    treeDrawer.setInverted(true);
	    treePanel.repaint();
	} else if (name.equals("Derivation Table")) {
	    treeDerivationLayout.last(treeDerivationPane);
	}
    }

    /**
     * Inits a parse table.
     * @return a table to hold the parse table
     */
    protected abstract JTable initParseTable();

    /**
     * Inits a new tree panel.
     * @return a new display for a parse tree
     */
    protected JComponent initTreePanel() {
	treeDrawer.hideAll();
	treeDrawer.setNodePlacer(new LeafNodePlacer());
	return treePanel;
    }

    /**
     * Inits a new derivation table.
     * @return a new display for the derivation of the parse
     */
    protected JTable initDerivationTable() {
	JTable table = new JTable(derivationModel);
	table.setGridColor(Color.lightGray);
	return table;
    }

    /**
     * This method is called when there is new input to parse.
     * @param string a new input string
     */
    protected abstract void input(String string);

    /**
     * This method is called when the step button is pressed.
     */
    protected abstract void step();

    /** The label that displays the remaining input. */
    JTextField inputDisplay = new JTextField();
    /** The label that displays the stack. */
    JTextField stackDisplay = new JTextField();
    /** The label that displays the current status of the parse. */
    JLabel statusDisplay = new JLabel("Input a string to begin.");
    /** The input text field. */
    JTextField inputField = new JTextField();

    /** The grammar being displayed. */
    Grammar grammar;
    /** The display for the grammar. */
    GrammarTable grammarTable;
    /** The environment. */
    GrammarEnvironment environment;

    /** The action for the stepping control. */
    AbstractAction stepAction = new AbstractAction("Step") {
	    public void actionPerformed(ActionEvent e) {
		step();
	    } };
    /** The action for the start control. */
    AbstractAction startAction = new AbstractAction("Start") {
	    public void actionPerformed(ActionEvent e) {
		input(inputField.getText());
	    } };
    
    /** A default tree drawer. */
    DefaultTreeDrawer treeDrawer = new DefaultTreeDrawer
	(new DefaultTreeModel(new DefaultMutableTreeNode())) {
	    protected Color getNodeColor(TreeNode node) {
		return node.isLeaf() ? LEAF : INNER;
	    }
	    private final Color INNER = new Color(100,200,120),
		LEAF = new Color(255,255,100);
	};
    /** A default tree display. */
    JComponent treePanel = new TreePanel(treeDrawer);
    /** The table model for the derivations. */
    DefaultTableModel derivationModel =
	new DefaultTableModel(new String[] {"Production", "Derivation"},0) {
	    public boolean isCellEditable(int r, int c) { return false; } };
    /** The split views. */
    JSplitPane mainSplit, topSplit, bottomSplit;

    /** The card layout. */
    CardLayout treeDerivationLayout = new CardLayout();
    /** The derivation/parse tree view. */
    JPanel treeDerivationPane = new JPanel(treeDerivationLayout);

    /** The derivation view. */
    JScrollPane derivationPane;
}
