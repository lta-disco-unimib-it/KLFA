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

import java.awt.Component;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.AbstractButton;
import javax.swing.ActionMap;
import javax.swing.ButtonGroup;
import javax.swing.InputMap;
import javax.swing.JToggleButton;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;

/**
 * A tool bar for editing and manipulating an automaton.
 * 
 * @author Thomas Finley
 */

public class ToolBar extends JToolBar implements ActionListener {
    /**
     * Instantiates a new tool bar.
     * @param view the view the automaton is displayed in
     * @param drawer the automaton drawer
     * @param box the toolbox to get the initial tools to put in the
     * bar
     */
    public ToolBar(EditCanvas view, AutomatonDrawer drawer, ToolBox box) {
	super();
	adapter = new ToolAdapter(view);
	this.view = view;
	this.drawer = drawer;
	tools = box.tools(view, drawer);
	initBar();
	view.addMouseListener(adapter);
	view.addMouseMotionListener(adapter);
    }

    /**
     * Returns the view that the automaton is drawn in.
     * @return the view that the automaton is drawn in
     */
    protected Component getView() {
	return view;
    }
    
    /**
     * Returns the automaton drawer for the automaton.
     * @return the automaton drawer for the automaton
     */
    protected AutomatonDrawer getDrawer() {
	return drawer;
    }
    
    /**
     * Initializes the tool bar.
     */
    private void initBar() {
	ButtonGroup group = new ButtonGroup();
	JToggleButton button = null;
	Iterator it = tools.iterator();
	KeyStroke key;
	while (it.hasNext()) {
	    Tool tool = (Tool) it.next();
	    button = new JToggleButton(tool.getIcon());
	    buttonsToTools.put(button,tool);
	    button.setToolTipText(tool.getShortcutToolTip());
	    group.add(button);
	    this.add(button);
	    button.addActionListener(this);
	    key = tool.getKey();
	    if (key == null) continue;
	    InputMap imap = button.getInputMap(JToggleButton.WHEN_IN_FOCUSED_WINDOW);
	    ActionMap amap = button.getActionMap();
	    Object o = new Object();
	    imap.put(key, o);
	    amap.put(o, new ButtonClicker(button));
	}
    }

    /**
     * If a tool is clicked, sets the new current tool.
     */
    public void actionPerformed(ActionEvent e) {
	Tool tool = (Tool) buttonsToTools.get(e.getSource());
	if (tool != null) {
	    adapter.setAdapter(tool);
	    currentTool = tool;
	}
    }

    /**
     * Draws the tool view.
     * @param g the graphics object to draw upon
     */
    public void drawTool(Graphics g) {
	if (currentTool == null) return;
	currentTool.draw(g);
    }

    /**
     * The action that clicks a button.
     */
    private class ButtonClicker extends AbstractAction {
	public ButtonClicker(AbstractButton button) {
	    this.button = button;
	}
	public void actionPerformed(ActionEvent e) {
	    button.doClick();
	}
	AbstractButton button;
    }

    private Component view;
    private AutomatonDrawer drawer;
    private List tools;
    private HashMap buttonsToTools = new HashMap();
    private ToolAdapter adapter;
    private Tool currentTool = null;
}
