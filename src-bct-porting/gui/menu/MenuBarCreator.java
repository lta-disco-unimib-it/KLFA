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
 
package gui.menu;

import gui.action.AboutAction;
import gui.action.BruteParseAction;
import gui.action.CloseAction;
import gui.action.CloseWindowAction;
import gui.action.CombineAutomaton;
import gui.action.ConvertCFGLL;
import gui.action.ConvertCFGLR;
import gui.action.ConvertFSAToGrammarAction;
import gui.action.ConvertFSAToREAction;
import gui.action.ConvertPDAToGrammarAction;
import gui.action.ConvertRegularGrammarToFSA;
import gui.action.DFAEqualityAction;
import gui.action.EnvironmentHelpAction;
import gui.action.GrammarTransformAction;
import gui.action.LLParseTableAction;
import gui.action.LRParseTableAction;
import gui.action.LSystemDisplay;
import gui.action.LambdaHighlightAction;
import gui.action.MinimizeTreeAction;
import gui.action.MultipleSimulateAction;
import gui.action.NFAToDFAAction;
import gui.action.NewAction;
import gui.action.NoInteractionSimulateAction;
import gui.action.NondeterminismAction;
import gui.action.OpenAction;
import gui.action.PrintAction;
import gui.action.QuitAction;
import gui.action.REToFSAAction;
import gui.action.SaveAction;
import gui.action.SaveAsAction;
import gui.action.SimulateAction;
import gui.action.SimulateNoClosureAction;
import gui.environment.Environment;
import gui.environment.EnvironmentFrame;
import gui.environment.Universe;

import java.io.Serializable;

import javax.swing.Action;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;

import automata.Automaton;

/**
 * The <CODE>MenuBarCreator</CODE> is a creator of the menu bars for
 * the FLAP application.
 * 
 * @author Thomas Finley
 */

public class MenuBarCreator {
    /**
     * Instantiates the menu bar.
     * @param frame the environment frame that holds the environment
     * and object
     * @return the menu bar appropriate for the environment
     */
    public static JMenuBar getMenuBar(EnvironmentFrame frame) {
	JMenuBar bar = new JMenuBar();
	JMenu menu;

	menu = getFileMenu(frame);
	if (menu.getItemCount() > 0) bar.add(menu);

	menu = getInputMenu(frame);
	if (menu.getItemCount() > 0) bar.add(menu);

	menu = getTestMenu(frame);
	if (menu.getItemCount() > 0) bar.add(menu);

	menu = getConvertMenu(frame);
	if (menu.getItemCount() > 0) bar.add(menu);

	menu = getHelpMenu(frame);
	if (menu.getItemCount() > 0) bar.add(menu);

	return bar;
    }

    /**
     * Adds an action to a menu with the accelerator key set.
     * @param menu the menu to add the action to
     * @param a the action to create the menu item for
     */
    public static void addItem(JMenu menu, Action a) {
	JMenuItem item = new JMenuItem(a);
	item.setAccelerator((KeyStroke) a.getValue(Action.ACCELERATOR_KEY));
	menu.add(item);
    }

    /**
     * Instantiates the file menu.
     * @param frame the environment frame that holds the environment
     * and object
     * @return a file menu
     */
    private static JMenu getFileMenu(EnvironmentFrame frame) {
	Environment environment = frame.getEnvironment();
	JMenu menu = new JMenu("File");
	addItem(menu, new NewAction());
	SecurityManager sm = System.getSecurityManager();
	if (Universe.CHOOSER != null) {
	    // Can't open and save files.
	    addItem(menu, new OpenAction());
	    addItem(menu, new SaveAction(environment));
	    addItem(menu, new SaveAsAction(environment));
	}
	addItem(menu, new CloseAction(environment));
	addItem(menu, new CloseWindowAction(frame));
	try {
	    if (sm != null) sm.checkPrintJobAccess();
	    addItem(menu, new PrintAction(environment));
	} catch (SecurityException e) {
	    // Damn.  Can't print!
	}
	try {
	    if (sm != null) sm.checkExit(0);
	    addItem(menu, new QuitAction());
	} catch (SecurityException e) {
	    // Well, can't exit anyway.
	}
	return menu;
    }

    /**
     * Instantiates the menu that holds input related menu events.
     * @param frame the environment frame that holds the environment
     * and object
     * @return an input menu
     */
    private static JMenu getInputMenu(EnvironmentFrame frame) {
	Environment environment = frame.getEnvironment();
	JMenu menu = new JMenu("Input");
	Serializable object = environment.getObject();
	if (SimulateAction.isApplicable(object))
	    addItem(menu, new SimulateAction((Automaton) object, environment));
	if (SimulateNoClosureAction.isApplicable(object))
	    addItem(menu, new SimulateNoClosureAction
		    ((Automaton)object, environment));
	if (NoInteractionSimulateAction.isApplicable(object))
	    addItem(menu, new NoInteractionSimulateAction
		    ((Automaton) object, environment));
	if (MultipleSimulateAction.isApplicable(object))
	    addItem(menu, new MultipleSimulateAction
		    ((Automaton) object, environment));
	/*if (GrammarOutputAction.isApplicable(object))
	    addItem(menu, new GrammarOutputAction
	    ((gui.environment.GrammarEnvironment) environment));*/

	// Grammar-y actions.

	if (LLParseTableAction.isApplicable(object))
	    addItem(menu, new LLParseTableAction
		    ((gui.environment.GrammarEnvironment) environment));
	if (LRParseTableAction.isApplicable(object))
	    addItem(menu, new LRParseTableAction
		    ((gui.environment.GrammarEnvironment) environment));
	if (BruteParseAction.isApplicable(object))
	    addItem(menu, new BruteParseAction
		    ((gui.environment.GrammarEnvironment) environment));

	// LSystem-y actions.
	
	if (LSystemDisplay.isApplicable(object))
	    addItem(menu, new LSystemDisplay
		    ((gui.environment.LSystemEnvironment) environment));

	return menu;
    }

    /**
     * This is the fun test menu for those that wish to run tests.
     * @param frame the environment frame that holds the tests
     * @return a test menu
     */
    private static JMenu getTestMenu(EnvironmentFrame frame) {
	Environment environment = frame.getEnvironment();
	JMenu menu = new JMenu("Test");
	Serializable object = environment.getObject();

	if (DFAEqualityAction.isApplicable(object))
	    addItem(menu, new DFAEqualityAction
		    ((automata.fsa.FiniteStateAutomaton) object, environment));

	/*if (MinimizeAction.isApplicable(object))
	    addItem(menu, new MinimizeAction
	    ((automata.fsa.FiniteStateAutomaton) object, environment));*/
	if (NondeterminismAction.isApplicable(object))
	    addItem(menu, new NondeterminismAction
		    ((automata.Automaton) object, environment));
	/*if (UnnecessaryAction.isApplicable(object))
	    addItem(menu, new UnnecessaryAction
		    ((automata.Automaton) object, environment));*/
	if (LambdaHighlightAction.isApplicable(object))
	    addItem(menu, new LambdaHighlightAction
		    ((automata.Automaton) object, environment));

	/*if (GrammarTestAction.isApplicable(object))
	    addItem(menu, new GrammarTestAction
	    ((gui.environment.GrammarEnvironment) environment));*/
	
	return menu;
    }

    /**
     * This is the menu for doing conversions.
     * @param frame the environment frame that holds the conversion items
     * @return the conversion menu
     */
    private static JMenu getConvertMenu(EnvironmentFrame frame) {
	Environment environment = frame.getEnvironment();
	JMenu menu = new JMenu("Convert");
	Serializable object = environment.getObject();
	
	if (NFAToDFAAction.isApplicable(object))
	    addItem(menu, new NFAToDFAAction
		    ((automata.fsa.FiniteStateAutomaton) object, environment));
	if (MinimizeTreeAction.isApplicable(object))
	    addItem(menu, new MinimizeTreeAction
		    ((automata.fsa.FiniteStateAutomaton) object, environment));

	if (ConvertFSAToGrammarAction.isApplicable(object))
	    addItem(menu, new ConvertFSAToGrammarAction
		    ((gui.environment.AutomatonEnvironment) environment));
	if (ConvertPDAToGrammarAction.isApplicable(object))
	    addItem(menu, new ConvertPDAToGrammarAction
		    ((gui.environment.AutomatonEnvironment) environment));

	if (ConvertFSAToREAction.isApplicable(object))
	    addItem(menu, new ConvertFSAToREAction
		    ((gui.environment.AutomatonEnvironment) environment));

	if (ConvertCFGLL.isApplicable(object))
	    addItem(menu, new ConvertCFGLL
		    ((gui.environment.GrammarEnvironment) environment));
	if (ConvertCFGLR.isApplicable(object))
	    addItem(menu, new ConvertCFGLR
		    ((gui.environment.GrammarEnvironment) environment));
	if (ConvertRegularGrammarToFSA.isApplicable(object))
	    addItem(menu, new ConvertRegularGrammarToFSA
		    ((gui.environment.GrammarEnvironment) environment));
	if (GrammarTransformAction.isApplicable(object))
	    addItem(menu, new GrammarTransformAction
		    ((gui.environment.GrammarEnvironment) environment));

	if (REToFSAAction.isApplicable(object))
	    addItem(menu, new REToFSAAction
		    ((gui.environment.RegularEnvironment) environment));

	if (CombineAutomaton.isApplicable(object))
	    addItem(menu, new CombineAutomaton
		    ((gui.environment.AutomatonEnvironment) environment));

	return menu;
    }

    /**
     * This is the menu for help.
     * @param frame the environment frame
     * @return the help menu
     */
    private static JMenu getHelpMenu(EnvironmentFrame frame) {
	Environment environment = frame.getEnvironment();
	JMenu menu = new JMenu("Help");
	Serializable object = environment.getObject();

	addItem(menu, new EnvironmentHelpAction(environment));
	addItem(menu, new AboutAction());

	return menu;
    }
}
