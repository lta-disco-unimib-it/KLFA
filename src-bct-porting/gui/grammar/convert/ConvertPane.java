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
 
package gui.grammar.convert;

import grammar.Grammar;
import gui.SplitPaneFactory;
import gui.editor.ArrowNontransitionTool;
import gui.editor.EditorPane;
import gui.editor.ToolBox;
import gui.editor.TransitionTool;
import gui.environment.Environment;
import gui.viewer.AutomatonDrawer;
import gui.viewer.AutomatonPane;
import gui.viewer.SelectionDrawer;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.util.LinkedList;
import java.util.Map;

import javax.swing.AbstractAction;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JToolBar;

import automata.Automaton;

/**
 * This is a graphical component to aid in the conversion of a context
 * free grammar to some form of pushdown automaton.
 * 
 * @author Thomas Finley
 */

public class ConvertPane extends JPanel {
    /**
     * Instantiates a <CODE>ConvertPane</CODE>.
     * @param grammar the grammar to convert
     * @param automaton a "starting automaton" that may already have
     * some start points predefined
     * @param productionsToTransitions the mapping of productions to
     * transitions, which should be one to one
     * @param env the environment to which this pane will be added
     */
    public ConvertPane(Grammar grammar, Automaton automaton,
		       Map productionsToTransitions, Environment env) {
	this.grammar = grammar;
	this.automaton = automaton;
	
	this.setLayout(new BorderLayout());
	JSplitPane split =
	    SplitPaneFactory.createSplit(env, true, .4, null, null);
	this.add(split, BorderLayout.CENTER);

	grammarViewer = new GrammarViewer(grammar);
	JScrollPane scroller = new JScrollPane(grammarViewer);
	split.setLeftComponent(scroller);
	// Create the right view.
	
	automatonDrawer = new SelectionDrawer(automaton);
	EditorPane ep = new EditorPane(automatonDrawer, new ToolBox() {
		public java.util.List tools
		    (AutomatonPane view, AutomatonDrawer drawer) {
		    LinkedList tools = new LinkedList();
		    tools.add(new ArrowNontransitionTool(view, drawer));
		    tools.add(new TransitionTool(view, drawer));
		    return tools;
		}
	    });
	// Create the controller device.
	ConvertController controller =
	    new ConvertController(grammarViewer, automatonDrawer,
				  productionsToTransitions, this);
	controlPanel(ep.getToolBar(), controller);
	split.setRightComponent(ep);
	editorPane = ep;
    }

    /**
     * Initializes the control objects in the editor pane's tool bar.
     * @param controller the controller object
     */
    private void controlPanel(JToolBar bar,
			      final ConvertController controller) {
	bar.addSeparator();
	bar.add(new AbstractAction("Show All") {
		public void actionPerformed(ActionEvent e) {
		    controller.complete();
		} });
	bar.add(new AbstractAction("Create Selected") {
		public void actionPerformed(ActionEvent e) {
		    controller.createForSelected();
		} });
	bar.add(new AbstractAction("Done?") {
		public void actionPerformed(ActionEvent e) {
		    controller.isDone();
		} });
	bar.add(new AbstractAction("Export") {
		public void actionPerformed(ActionEvent e) {
		    controller.export();
		} });
    }

    /**

    /**
     * Returns the editor pane.
     * @return the editor pane
     */
    public EditorPane getEditorPane() {
	return editorPane;
    }

    /** The grammar that this convertpane holds. */
    private Grammar grammar;
    /** The grammar viewer. */
    private GrammarViewer grammarViewer;
    /** The automaton selection drawer. */
    private SelectionDrawer automatonDrawer;
    /** The automaton. */
    private Automaton automaton;
    /** The editor pane. */
    private EditorPane editorPane;
}
