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
 
package gui.grammar.transform;

import grammar.Grammar;
import grammar.Production;
import gui.SplitPaneFactory;
import gui.environment.FrameFactory;
import gui.environment.GrammarEnvironment;
import gui.grammar.GrammarTable;
import gui.grammar.GrammarTableModel;

import java.awt.BorderLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.AbstractAction;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

/**
 * This is the pane where the removal of lambda productions takes
 * place.
 * 
 * @author Thomas Finley
 */

public class LambdaPane extends JPanel {
    /**
     * Instantiates a new lambda pane.
     * @param environment the grammar environment this pane will
     * belong to
     * @param grammar the grammar to do the lambda removal on
     */
    public LambdaPane(GrammarEnvironment environment, Grammar grammar) {
	this.environment = environment;
	this.grammar = grammar;
	controller = new LambdaController(this, grammar);
	initView();
    }

    /**
     * Initializes the GUI components of this pane.
     */
    private void initView() {
	super.setLayout(new BorderLayout());
	initGrammarTable();
	JPanel rightPanel = initRightPanel();
	JSplitPane mainSplit = SplitPaneFactory.createSplit
	    (environment, true, 0.4, new JScrollPane(grammarTable),
	     rightPanel);
	this.add(mainSplit, BorderLayout.CENTER);
    }
    
    /**
     * Initializes the right panel.
     */
    private JPanel initRightPanel() {
	JPanel right = new JPanel(new BorderLayout());

	// Sets the alignments.
	mainLabel.setAlignmentX(0.0f);
	detailLabel.setAlignmentX(0.0f);
	lambdaDerivingLabel.setAlignmentX(0.0f);
	
	JPanel panel = new JPanel();
	panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
	panel.add(mainLabel);
	panel.add(detailLabel);
	panel.add(lambdaDerivingLabel);
	initEditingGrammarTable();

	JToolBar editingBar = new JToolBar();
	editingBar.setAlignmentX(0.0f);
	editingBar.setFloatable(false);
	editingBar.add(deleteAction);
	editingBar.add(completeSelectedAction);
	panel.add(editingBar);

	panel.add(new JScrollPane(editingGrammarView));

	JToolBar toolbar = new JToolBar();
	toolbar.setAlignmentX(0.0f);
	toolbar.add(doStepAction);
	toolbar.add(doAllAction);
	toolbar.addSeparator();
	toolbar.add(proceedAction);
	toolbar.add(exportAction);
	right.add(toolbar, BorderLayout.NORTH);
	
	right.add(panel, BorderLayout.CENTER);
	
	return right;
    }

    /**
     * Initializes a table for the grammar.
     * @return a table to display the grammar
     */
    private GrammarTable initGrammarTable() {
	grammarTable = new GrammarTable(new GrammarTableModel(grammar) {
		public boolean isCellEditable(int r, int c) {return false;}});
	grammarTable.addMouseListener(new MouseAdapter() {
		public void mouseClicked(MouseEvent event) {
		    GrammarTable gt = (GrammarTable) event.getSource();
		    Point at = event.getPoint();
		    int row = gt.rowAtPoint(at);
		    if (row == -1) return;
		    if (row == gt.getGrammarModel().getRowCount()-1) return;
		    Production p = gt.getGrammarModel().getProduction(row);
		    controller.productionClicked(p, event);
		} });
	grammarTable.getSelectionModel().addListSelectionListener
	    (new ListSelectionListener() {
		    public void valueChanged(ListSelectionEvent event) {
			updateCompleteSelectedEnabledness();
		    }});
	return grammarTable;
    }

    /**
     * Updates the delete action enabledness.
     */
    void updateDeleteEnabledness() {
	if (controller.step != LambdaController.PRODUCTION_MODIFY) {
	    deleteAction.setEnabled(false);
	    return;
	}
	int min = editingGrammarView.getSelectionModel().getMinSelectionIndex();
	if (min == -1 || min >= editingGrammarModel.
	    getRowCount()-1) {
	    deleteAction.setEnabled(false);
	    return;
	}
	deleteAction.setEnabled(true);
    }

    /**
     * Updates the complete selected action enabledness.
     */
    void updateCompleteSelectedEnabledness() {
	if (controller.step != LambdaController.PRODUCTION_MODIFY) {
	    completeSelectedAction.setEnabled(false);
	    return;
	}
	int min = grammarTable.getSelectionModel().getMinSelectionIndex();
	if (min == -1 || min >= grammarTable.getGrammarModel().
	    getRowCount()-1) {
	    completeSelectedAction.setEnabled(false);
	    return;
	}
	completeSelectedAction.setEnabled(true);
    }

    /**
     * Initializes the editing grammar view.
     */
    private void initEditingGrammarTable() {
	editingGrammarModel.addTableModelListener(new TableModelListener() {
		public void tableChanged(TableModelEvent event) {
		    if (!editingActive) return;
		    //cancelEditing();
		    int r = event.getFirstRow();
		    if (event.getType() != TableModelEvent.UPDATE) {
			// If we're editing anything, we have to get
			// out of the funk.
			return;
		    }
		    editingColumn[event.getColumn()>>1] = true;
		    if (editingColumn[0] == true && editingColumn[1] == true) {
			Production p = editingGrammarModel.getProduction(r);
			if (p == null) return;
			if (!controller.productionAdded(p, r)) {
			    editingGrammarModel.deleteRow(r);
			}
			editingRow = -1;
		    }
		} });
	editingGrammarView.getSelectionModel().addListSelectionListener
	    (new ListSelectionListener() {
		    public void valueChanged(ListSelectionEvent event) {
			updateDeleteEnabledness();
		    }
		});
	Object o = new Object();
	editingGrammarView.getInputMap().put
	    (KeyStroke.getKeyStroke(KeyEvent.VK_BACK_SPACE, 0), o);
	editingGrammarView.getInputMap().put
	    (KeyStroke.getKeyStroke(KeyEvent.VK_DELETE, 0), o);
	editingGrammarView.getActionMap().put(o, deleteAction);
    }

    /**
     * Calling this method discontinues any editing taking place.
     */
    void cancelEditing() {
	if (editingGrammarView.getCellEditor() != null)
	    editingGrammarView.getCellEditor().stopCellEditing();
	if (editingRow != -1) {
	    editingGrammarModel.deleteRow(editingRow);
	    editingRow = -1;
	}
    }

    /**
     * Returns the grammar that results.
     * @return the grammar that results
     */
    private Grammar getGrammar() {
	return editingGrammarView.getGrammar(grammar.getClass());
    }

    /**
     * This method should be called when the deletion method is called.
     */
    private void deleteActivated() {
	if (controller.step != LambdaController.PRODUCTION_MODIFY) return;
	cancelEditing();
	int deleted = 0, kept = 0;
	for (int i=editingGrammarModel.getRowCount()-2; i>=0; i--) {
	    if (!editingGrammarView.isRowSelected(i)) continue;
	    Production p = editingGrammarModel.getProduction(i);
	    if (controller.productionDeleted(p, i)) {
		editingGrammarModel.deleteRow(i);
		deleted++;
	    } else {
		kept++;
	    }
	}
	if (kept != 0) {
	    JOptionPane.showMessageDialog
		(this,kept+" production(s) selected should not be removed.\n"+
		 deleted+" production(s) were removed.","Bad Selection",
		 JOptionPane.ERROR_MESSAGE);
	}
	if (deleted != 0) {
	    controller.updateDisplay();
	}
    }

    // These are some of the data structures relevant.

    /** The grammar environment. */
    GrammarEnvironment environment;
    /** The grammar to remove lambdas on. */
    Grammar grammar;
    /** The controller object. */
    LambdaController controller;

    // These are some of the graphical elements.

    /** The grammar table. */
    GrammarTable grammarTable;
    /** The main instruction label. */
    JLabel mainLabel = new JLabel(" ");
    /** The detail instruction label. */
    JLabel detailLabel = new JLabel(" ");
    /** The lambda deriving variable labels. */
    JLabel lambdaDerivingLabel = new JLabel(" ");

    // These are general controls.
    AbstractAction doStepAction = new AbstractAction("Do Step") {
	    public void actionPerformed(ActionEvent e) {controller.doStep();}};
    AbstractAction doAllAction = new AbstractAction("Do All") {
	    public void actionPerformed(ActionEvent e) {controller.doAll();}};
    AbstractAction proceedAction = new AbstractAction("Proceed") {
	    public void actionPerformed(ActionEvent e) {
		gui.action.GrammarTransformAction.hypothesizeUnit
		    (environment, getGrammar());
	    }};
    AbstractAction exportAction = new AbstractAction("Export") {
	    public void actionPerformed(ActionEvent e) {
		FrameFactory.createFrame(editingGrammarView.getGrammar
					 (grammar.getClass()));
	    }};

    // These are some of the special structures relevant to the
    // grammar editing table.

    /** Simple kludge to allow us to add stuff to the table without fear. */
    boolean editingActive = false;
    /** The editing row in the table. */
    private int editingRow = -1;
    /** Which columsn of the editing row have been edited yet? */
    private boolean editingColumn[] = new boolean[2];
    /** The editing grammar table mode. */
    GrammarTableModel editingGrammarModel = new GrammarTableModel() {
	    public boolean isCellEditable(int r, int c) {
		if (controller.step != LambdaController.PRODUCTION_MODIFY)
		    return false;
		if (c == 1) return false;
		if (editingRow == -1) {
		    if (r == getRowCount()-1) {
			editingRow = r;
			editingColumn[0] = editingColumn[1] = false;
			return true;
		    }
		    return false;
		} else
		    return editingRow == r;
	    }
	};
    /** The editing grammar table view. */
    GrammarTable editingGrammarView
	= new GrammarTable(editingGrammarModel);
    /** The delete action for deleting rows. */
    AbstractAction deleteAction = new AbstractAction("Delete") {
	    public void actionPerformed(ActionEvent e) {
		deleteActivated();
	    } };
    /** The complete selected action. */
    AbstractAction completeSelectedAction = new AbstractAction
	("Complete Selected") {
	    public void actionPerformed(ActionEvent e) {
		cancelEditing();
		for (int i=0; i<grammarTable.getRowCount()-1; i++)
		    if (grammarTable.isRowSelected(i))
			controller.expandRowProduction(i);
	    } };
}
