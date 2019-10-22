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
 
package gui.deterministic;

import gui.SplitPaneFactory;
import gui.TooltipAction;
import gui.editor.ArrowNontransitionTool;
import gui.editor.EditorPane;
import gui.editor.ToolBox;
import gui.environment.Environment;
import gui.viewer.AutomatonDraggerPane;
import gui.viewer.AutomatonDrawer;
import gui.viewer.AutomatonPane;
import gui.viewer.SelectionDrawer;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JToolBar;

import automata.fsa.FiniteStateAutomaton;

/**
 * This is the pane where the user defines all that is needed for the
 * conversion of an NFA to a DFA.
 * 
 * @author Thomas Finley
 */

public class ConversionPane extends JPanel {
    /**
     * Instantiates a new <CODE>ConversionPane</CODE>.
     * @param nfa the NFA we are converting to a DFA
     * @param environment the environment this pane will be added to
     */
    public ConversionPane(FiniteStateAutomaton nfa,
			  Environment environment) {
	super(new BorderLayout());
	FiniteStateAutomaton dfa = new FiniteStateAutomaton();
	controller = new ConversionController(nfa, dfa, this);
	// Create the left view of the original NFA.
	AutomatonPane nfaPane = new AutomatonDraggerPane(nfa);
	// Put it all together.
	JSplitPane split = SplitPaneFactory.createSplit
	    (environment, true,.25,nfaPane,createEditor(dfa));
	add(split, BorderLayout.CENTER);

	// When the component is first shown, perform layout.
	addComponentListener(new ComponentAdapter() {
		public void componentShown(ComponentEvent event) {
		    // We may now lay out the states...
		    controller.performFirstLayout();
		    editor.getAutomatonPane().repaint();
		}
		boolean doneBefore = false;
	    });
    }

    /**
     * Creates the editor pane for the DFA.
     * @param dfa the dfa to create the editor pane for
     */
    private EditorPane createEditor(FiniteStateAutomaton dfa) {
	SelectionDrawer drawer = new SelectionDrawer(dfa);
	editor = new EditorPane(drawer, new ToolBox() {
		public java.util.List tools
		    (AutomatonPane view, AutomatonDrawer drawer) {
		    java.util.List tools = new java.util.LinkedList();
		    tools.add(new ArrowNontransitionTool(view, drawer));
		    tools.add(new TransitionExpanderTool(view, drawer,
							 controller));
		    tools.add(new StateExpanderTool(view, drawer, controller));
		    return tools;
		}
	    });
	addExtras(editor.getToolBar());
	return editor;
    }

    /**
     * Adds the extra controls to the toolbar for the editorpane.
     * @param toolbar the tool bar to add crap to
     */
    private void addExtras(JToolBar toolbar) {
	toolbar.addSeparator();
	toolbar.add(new TooltipAction
		    ("Complete", "This will finish all expansion.") {
		public void actionPerformed(ActionEvent e) {
		    controller.complete();
		}
	    });
	toolbar.add(new TooltipAction
		    ("Done?", "Are we finished?") {
		public void actionPerformed(ActionEvent e) {
		    controller.done();
		}
	    });
    }

    /** The controller object. */
    private ConversionController controller;
    /** The editor pane. */
    EditorPane editor;
}
