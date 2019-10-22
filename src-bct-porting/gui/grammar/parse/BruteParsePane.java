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
import grammar.parse.BruteParser;
import grammar.parse.BruteParserEvent;
import grammar.parse.BruteParserListener;
import gui.environment.GrammarEnvironment;
import gui.tree.SelectNodeDrawer;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JToolBar;
import javax.swing.Timer;
import javax.swing.tree.TreeNode;

/**
 * This is a brute force parse pane.
 * 
 * @author Thomas Finley
 */

public class BruteParsePane extends ParsePane {
    /**
     * Instantiates a new brute force parse pane.
     * @param environment the grammar environment
     * @param grammar the augmented grammar
     */
    public BruteParsePane(GrammarEnvironment environment, Grammar grammar) {
	super(environment, grammar);
	initView();
    }

    /**
     * Inits a parse table.
     * @return a table to hold the parse table
     */
    protected JTable initParseTable() {
	return null;
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
	//startButton.addActionListener(listener);
	//panel.add(startButton, c);

	panel.add(progress, c);

	bigger.add(panel, BorderLayout.CENTER);
	bigger.add(initInputToolbar(), BorderLayout.NORTH);
	
	return bigger;
    }

    /**
     * Returns a toolbar for the parser.
     * @return the toolbar for the parser
     */
    protected JToolBar initInputToolbar() {
	JToolBar tb = super.initInputToolbar();
	tb.add(new JButton(pauseResumeAction),1);
	return tb;
    }

    /**
     * This method is called when there is new input to parse.
     * @param string a new input string
     */
    protected void input(String string) {
	if (parser != null) {
	    parser.pause();
	}
	try {
	    parser = BruteParser.get(grammar, string);
	} catch (IllegalArgumentException e) {
	    JOptionPane.showMessageDialog
		(this,e.getMessage(),"Bad Input",JOptionPane.ERROR_MESSAGE);
	    return;
	}
	final Timer timer = new Timer(10, new ActionListener() {
		public void actionPerformed(ActionEvent e) {
		    String nodeCount =
			"Nodes generated: "+parser.getTotalNodeCount()+"("+
			parser.getConsiderationNodeCount()+")";
		    progress.setText("Parser running.  "+nodeCount);
		}
	    });
	parser.addBruteParserListener(new BruteParserListener() {
		public void bruteParserStateChange(BruteParserEvent e) {
		    synchronized(e.getParser()) {
		    String nodeCount = e.getParser().getTotalNodeCount()+
			" nodes generated.";
		    String status = null;
		    switch (e.getType()) {
		    case BruteParserEvent.START:
			pauseResumeAction.setEnabled(true);
			pauseResumeAction.putValue(Action.NAME, "Pause");
			timer.start();
			status = "Parser started.";
			break;
		    case BruteParserEvent.REJECT:
			pauseResumeAction.setEnabled(false);
			timer.stop();
			status = "String rejected.";
			break;
		    case BruteParserEvent.PAUSE:
			timer.stop();
			pauseResumeAction.putValue(Action.NAME, "Resume");
			status = "Parser paused.";
			break;
		    case BruteParserEvent.ACCEPT:
			pauseResumeAction.setEnabled(false);
			stepAction.setEnabled(true);
			timer.stop();
			status = "String accepted!";
			break;
		    }
		    if (parser.isFinished()) parser = null;
		    progress.setText(status + "  " + nodeCount);

		    if (!e.isAccept()) {
			// Rejected!
			treePanel.setAnswer(null);
			treePanel.repaint();
			stepAction.setEnabled(false);
			statusDisplay.setText("Try another string.");
			return;
		    }
		    TreeNode node = e.getParser().getAnswer();
		    do {
			node = node.getParent();
		    } while (node != null);
		    }
		    statusDisplay.setText("Press step to show derivations.");
		    treePanel.setAnswer(e.getParser().getAnswer());
		    treePanel.repaint();
		}

	    });
	parser.start();
    }

    /**
     * Returns the choices for the view.
     * @return an array of strings for the choice of view
     */
    protected String[] getViewChoices() {
	return new String[] {"Noninverted Tree", "Derivation Table"};
    }

    /**
     * This method is called when the step button is pressed.
     */
    protected void step() {
	//controller.step();
	if (treePanel.next()) stepAction.setEnabled(false);
	treePanel.repaint();
    }

    /**
     * Inits a new tree panel.  This overriding adds a selection node
     * drawer so certain nodes can be highlighted.
     * @return a new display for the parse tree
     */
    protected JComponent initTreePanel() {
	return treePanel;
    }

    /** The tree pane. */
    UnrestrictedTreePanel treePanel = new UnrestrictedTreePanel(this);
    /** The selection node drawer. */
    SelectNodeDrawer nodeDrawer = new SelectNodeDrawer();
    /** The progress bar. */
    JLabel progress = new JLabel(" ");
    /** The current parser object. */
    BruteParser parser = null;
    /** The pause/resume action. */
    Action pauseResumeAction = new AbstractAction("Pause") {
	    public void actionPerformed(ActionEvent e) {
		synchronized(parser) {
		    if (parser == null) return;
		    if (parser.isActive()) parser.pause();
		    else parser.start();
		}
	    }
	};
}
