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
