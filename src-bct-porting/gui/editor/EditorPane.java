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
import gui.viewer.SelectionDrawer;

import java.awt.BorderLayout;
import java.awt.Graphics;

import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.BevelBorder;

import automata.Automaton;

/**
 * This is a view that holds a tool bar and the canvas where the
 * automaton is displayed.
 * 
 * @author Thomas Finley
 */

public class EditorPane extends JComponent {
    /**
     * Instantiates a new editor pane for the given automaton.
     * @param automaton the automaton to create the editor pane for
     */
    public EditorPane(Automaton automaton) {
	this(new SelectionDrawer(automaton));
    }

    /**
     * Instantiates a new editor pane with a tool box.
     */
    public EditorPane(Automaton automaton, ToolBox box) {
	this(new SelectionDrawer(automaton), box);
    }

    /**
     * Instantiates a new editor pane with a given automaton drawer.
     * @param drawer the special automaton drawer for this editor
     */
    public EditorPane(AutomatonDrawer drawer) {
	this(drawer, new DefaultToolBox());
    }

    /**
     * Instantiates a new editor pane with a given automaton drawer.
     * @param drawer the special automaton drawer for this editor
     * @param box the tool box to get the tools from
     */
    public EditorPane(AutomatonDrawer drawer, ToolBox box) {
	this(drawer, box, false);
    }

    /**
     * Instantiates a new editor pane with a given automaton drawer.
     * @param drawer the special automaton drawer for this editor
     * @param box the tool box to get teh tools from
     * @param fit <CODE>true</CODE> if the editor should resize its
     * view to fit the automaton; note that this can be <I>very</I>
     * annoying if the automaton changes
     */
    public EditorPane(AutomatonDrawer drawer, ToolBox box, boolean fit) {
	pane = new EditCanvas(drawer, fit);
	this.drawer = drawer;
	this.automaton = drawer.getAutomaton();
	this.setLayout(new BorderLayout());
	
	JPanel superpane = new JPanel();
	superpane.setLayout(new BorderLayout());
	superpane.add(new JScrollPane
		      (pane, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
		       JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS),
		      BorderLayout.CENTER);
	superpane.setBorder(new BevelBorder(BevelBorder.LOWERED));
	
	toolbar = new gui.editor.ToolBar(pane, drawer, box);
	pane.setToolBar(toolbar);
	
	this.add(superpane, BorderLayout.CENTER);
	this.add(toolbar, BorderLayout.NORTH);
    }

    /**
     * Returns the toolbar for this editor pane.
     * @return the toolbar of this editor pane
     */
    public gui.editor.ToolBar getToolBar() {
	return toolbar;
    }

    /**
     * Returns the automaton drawer for the editor pane canvas.
     * @return the drawer that draws the automaton being edited
     */
    public AutomatonDrawer getDrawer() {
	return pane.getDrawer();
    }
    
    /**
     * Returns the automaton pane.
     * @return the automaton pane
     */
    public EditCanvas getAutomatonPane() {
	return pane;
    }

    /**
     * Prints this component.  This will print only the automaton
     * section of the component.
     * @param g the graphics object to paint to
     */
    public void printComponent(Graphics g) {
	pane.print(g);
    }
    
    /**
     * Children are not painted here.
     * @param g the graphics object to paint to
     */
    public void printChildren(Graphics g) {
	
    }

    /** The automaton. */
    protected Automaton automaton;
    /** The automaton drawer. */
    protected AutomatonDrawer drawer;
    /** The automaton pane. */
    protected EditCanvas pane;
    /** The tool bar. */
    protected gui.editor.ToolBar toolbar;
}
