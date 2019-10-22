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

import gui.viewer.AutomatonDrawer;
import gui.viewer.AutomatonPane;

import java.awt.Component;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;

import javax.swing.Icon;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.KeyStroke;

import automata.State;
import automata.StateRenamer;
import automata.Transition;
import automata.graph.AutomatonGraph;
import automata.graph.GEMLayoutAlgorithm;
import automata.graph.LayoutAlgorithm;

/**
 * The arrow tool is used mostly for editing existing objects.
 * 
 * @author Thomas Finley
 */

public class ArrowTool extends Tool {
    /**
     * Instantiates a new arrow tool.
     * @param view the view where the automaton is drawn
     * @param drawer the object that draws the automaton
     * @param creator the transition creator used for editing transitions
     */
    public ArrowTool(AutomatonPane view, AutomatonDrawer drawer,
		     TransitionCreator creator) {
	super(view, drawer);
	this.creator = creator;
    }

    /**
     * Instantiates a new arrow tool.
     * @param view the view where the automaton is drawn
     * @param drawer the object that draws the automaton
     */
    public ArrowTool(AutomatonPane view, AutomatonDrawer drawer) {
	super(view, drawer);
	this.creator =
	    TransitionCreator.creatorForAutomaton(getAutomaton(),getView());
    }

    /**
     * Gets the tool tip for this tool.
     * @return the tool tip for this tool
     */
    public String getToolTip() {
	return "Attribute Editor";
    }

    /**
     * Returns the tool icon.
     * @return the arrow tool icon
     */
    protected Icon getIcon() {
	java.net.URL url = getClass().getResource("/ICON/arrow.gif");
	return new javax.swing.ImageIcon(url);
    }

    /**
     * On a mouse click, if this is a double click over a transition
     * edit the transition.
     * @param event the mouse event
     */
    public void mouseClicked(MouseEvent event) {
	//if (event.getClickCount() != 2) return;
	Transition trans = getDrawer().transitionAtPoint(event.getPoint());
	if (trans == null) return;
	creator.editTransition(trans, event.getPoint());
    }

    /**
     * Possibly show a popup menu.
     * @param event the mouse event
     */
    protected void showPopup(MouseEvent event) {
	// Should we show a popup menu?
	if (event.isPopupTrigger()) {
	    Point p = getView().transformFromAutomatonToView(event.getPoint());
	    if (lastClickedState != null && shouldShowStatePopup()) {
		stateMenu.show(lastClickedState, getView(), p);
	    } else {
		emptyMenu.show(getView(), p);
	    }
	}
	lastClickedState = null;
	lastClickedTransition = null;
    }

    /**
     * On a mouse press, allows the state to be dragged about unless
     * this is a popup trigger.
     */
    public void mousePressed(MouseEvent event) {
	lastClickedState =
	    getDrawer().stateAtPoint(event.getPoint());
	if (lastClickedState == null)
	    lastClickedTransition =
		getDrawer().transitionAtPoint(event.getPoint());

	// Should we show a popup menu?
	if (event.isPopupTrigger())
	    showPopup(event);

	if (lastClickedState != null) {
	    initialPointState.setLocation(lastClickedState.getPoint());
	    initialPointClick.setLocation(event.getPoint());
	}

	if (lastClickedTransition != null) {
	    initialPointClick.setLocation(event.getPoint());
	}
    }

    /**
     * Returns if the state popup menu should be shown whenever
     * applicable.
     * @return <CODE>true</CODE> if the state menu should be popped
     * up, <CODE>false</CODE> if it should not be... returns
     * <CODE>true</CODE> by default
     */
    protected boolean shouldShowStatePopup() {
	return true;
    }

    /**
     * On a mouse drag, possibly move a state if the first press was
     * on a state.
     */
    public void mouseDragged(MouseEvent event) {
	if (lastClickedState != null) {
	    if (event.isPopupTrigger()) return;
	    Point p = event.getPoint();
	    int x = initialPointState.x + p.x - initialPointClick.x;
	    int y = initialPointState.y + p.y - initialPointClick.y;
	    lastClickedState.getPoint().setLocation(x,y);
	    lastClickedState.setPoint(lastClickedState.getPoint());
	    getView().repaint();
	} else if (lastClickedTransition != null) {
	    if (event.isPopupTrigger()) return;
	    Point p = event.getPoint();
	    int x = p.x - initialPointClick.x;
	    int y = p.y - initialPointClick.y;
	    State f = lastClickedTransition.getFromState(),
		t = lastClickedTransition.getToState();
	    f.getPoint().translate(x,y);
	    f.setPoint(f.getPoint());
	    if (f != t) {
		// Don't want self loops moving twice the speed...
		t.getPoint().translate(x,y);
		t.setPoint(t.getPoint());
	    }
	    initialPointClick.setLocation(p);
	    getView().repaint();
	}
    }

    /**
     * On a mouse release, sets the tool to the "virgin" state.
     */
    public void mouseReleased(MouseEvent event) {
	if (event.isPopupTrigger())
	    showPopup(event);
	lastClickedState = null;
	lastClickedTransition = null;
    }

    /**
     * Returns the key stroke that will activate this tool.
     * @return the key stroke that will activate this tool
     */
    public KeyStroke getKey() {
	return KeyStroke.getKeyStroke('a');
    }

    /**
     * Returns true if only changing the final stateness of a state
     * should be allowed in the state menu.
     */
    public boolean shouldAllowOnlyFinalStateChange() {
	return false;
    }

    /**
     * The contextual menu class for editing states.
     */
    private class StateMenu extends JPopupMenu implements ActionListener {
	public StateMenu() {
	    makeFinal = new JCheckBoxMenuItem("Final");
	    makeFinal.addActionListener(this);
	    this.add(makeFinal);
	    makeInitial = new JCheckBoxMenuItem("Initial");
	    changeLabel = new JMenuItem("Change Label");
	    deleteLabel = new JMenuItem("Clear Label");
	    deleteAllLabels = new JMenuItem("Clear All Labels");
	    if (shouldAllowOnlyFinalStateChange()) return;
	    makeInitial.addActionListener(this);
	    changeLabel.addActionListener(this);
	    deleteLabel.addActionListener(this);
	    deleteAllLabels.addActionListener(this);
	    this.add(makeInitial);
	    this.add(changeLabel);
	    this.add(deleteLabel);
	    this.add(deleteAllLabels);
	}

	public void show(State state, Component comp, Point at) {
	    this.state = state;
	    makeFinal.setSelected(getAutomaton().isFinalState(state));
	    makeInitial.setSelected(getAutomaton().getInitialState()==state);
	    deleteLabel.setEnabled(state.getLabel() != null);
	    show(comp, at.x, at.y);
	}

	public void actionPerformed(ActionEvent e) {
	    JMenuItem item = (JMenuItem) e.getSource();
	    if (item == makeFinal) {
		if (item.isSelected())
		    getAutomaton().addFinalState(state);
		else
		    getAutomaton().removeFinalState(state);
	    } else if (item == makeInitial) {
		if (!item.isSelected()) state = null;
		getAutomaton().setInitialState(state);
	    } else if (item == changeLabel) {
		String oldlabel = state.getLabel();
		oldlabel = oldlabel == null ? "" : oldlabel;
		String label = (String) JOptionPane.showInputDialog
		    (this, "Input a new label, or \n"+
		     "set blank to remove the label",
		     "New Label", JOptionPane.QUESTION_MESSAGE,
		     null, null, oldlabel);
		if (label == null) return;
		if (label.equals("")) label = null;
		state.setLabel(label);
	    } else if (item == deleteLabel) {
		state.setLabel(null);
	    } else if (item == deleteAllLabels) {
		State[] states = getAutomaton().getStates();
		for (int i=0; i<states.length; i++) states[i].setLabel(null);
	    }
	    getView().repaint();
	}

	private State state = null;
	private JCheckBoxMenuItem makeFinal, makeInitial;
	private JMenuItem changeLabel, deleteLabel, deleteAllLabels;
    }

    /**
     * The contextual menu class for editing transitions.
     */
    private class TransitionMenu extends JPopupMenu {
	
    }

    /**
     * The contextual menu class for context clicks in blank space.
     */
    private class EmptyMenu extends JPopupMenu implements ActionListener {
	public EmptyMenu() {
	    stateLabels = new JCheckBoxMenuItem("Display State Labels");
	    stateLabels.addActionListener(this);
	    this.add(stateLabels);
	    layoutGraph = new JMenuItem("Layout Graph");
	    if (!(ArrowTool.this instanceof ArrowDisplayOnlyTool)) {
		layoutGraph.addActionListener(this);
		this.add(layoutGraph);
	    }
	    renameStates = new JMenuItem("Rename States");
	    if (!(ArrowTool.this instanceof ArrowDisplayOnlyTool)) {
		renameStates.addActionListener(this);
		this.add(renameStates);
	    }
	}

	public void show(Component comp, Point at) {
	    stateLabels.setSelected(getDrawer().doesDrawStateLabels());
	    show(comp, at.x, at.y);
	}

	public void actionPerformed(ActionEvent e) {
	    JMenuItem item = (JMenuItem) e.getSource();
	    if (item == stateLabels) {
		getView().getDrawer().shouldDrawStateLabels(item.isSelected());
	    } else if (item == layoutGraph) {
		AutomatonGraph g = new AutomatonGraph(getAutomaton());
		LayoutAlgorithm alg = new GEMLayoutAlgorithm();
		alg.layout(g, null);
		g.moveAutomatonStates();
		getView().fitToBounds(30);
	    } else if (item == renameStates) {
		StateRenamer.rename(getAutomaton());
	    }
	    getView().repaint();
	}

	private JCheckBoxMenuItem stateLabels;
	private JMenuItem layoutGraph;
	private JMenuItem renameStates;
    }

    /** The transition creator for editing transitions. */
    private TransitionCreator creator;

    /** The state that was last clicked. */
    private State lastClickedState = null;
    /** The transition that was last clicked. */
    private Transition lastClickedTransition = null;
    /** The initial point of the state. */
    private Point initialPointState = new Point();
    /** The initial point of the click. */
    private Point initialPointClick = new Point();
    
    /** The state menu. */
    private StateMenu stateMenu = new StateMenu();
    /** The transition menu. */
    private TransitionMenu transitionMenu = new TransitionMenu();
    /** The empty menu. */
    private EmptyMenu emptyMenu = new EmptyMenu();
}
