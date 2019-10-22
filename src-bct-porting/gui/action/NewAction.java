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
 
package gui.action;

import gui.environment.FrameFactory;
import gui.environment.Universe;
import gui.menu.MenuBarCreator;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.Serializable;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;

/**
 * The <CODE>NewAction</CODE> handles when the user decides to create
 * some new environment, that is, some sort of new automaton, or
 * grammar, or regular expression, or some other such editable object.
 * 
 * @author Thomas Finley
 */

public class NewAction extends RestrictedAction {
    /**
     * Instantiates a new <CODE>NewAction</CODE>.
     */
    public NewAction() {
	super("New...", null);
	putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke
		 (KeyEvent.VK_N, MAIN_MENU_MASK));
    }

    /**
     * Shows the new machine dialog box.
     * @param event the action event
     */
    public void actionPerformed(ActionEvent event) {
	showNew();
    }

    /**
     * Shows the new environment dialog.
     */
    public static void showNew() {
	if (DIALOG == null) DIALOG = new NewDialog();
	DIALOG.show();
	DIALOG.toFront();
    }

    /**
     * Hides the new environment dialog.
     */
    public static void hideNew() {
		if (DIALOG == null) DIALOG = new NewDialog();
		DIALOG.hide();
    }

    /**
     * Called once a type of editable object is choosen.  The editable
     * object is passed in, the dialog is hidden, and the window is
     * created.
     * @param object the object that we are to edit
     */
    private static void createWindow(Serializable object) {
	DIALOG.hide();
	FrameFactory.createFrame(object);
    }

    /** The dialog box that allows one to create new environments. */
    private static class NewDialog extends JFrame {
	/**
	 * Instantiates a <CODE>NewDialog</CODE> instance.
	 */
	public NewDialog() {
	    //super((java.awt.Frame)null, "New Document");
	    super("New Document");
	    getContentPane().setLayout(new GridLayout(0,1));
	    initMenu();
	    initComponents();
	    setResizable(false);
	    this.pack();
	    this.setLocation(50,50);

	    this.addWindowListener(new WindowAdapter() {
		    public void windowClosing(WindowEvent event) {
			if (Universe.numberOfFrames() > 0) {
			    NewDialog.this.hide();
			} else {
			    QuitAction.beginQuit();
			}
		    }
		});
	}

	private void initMenu() {
	    // Mini menu!
	    JMenuBar menuBar = new JMenuBar();
	    JMenu menu = new JMenu("File");
	    if (Universe.CHOOSER != null) {
		MenuBarCreator.addItem(menu, new OpenAction());
	    }
	    try {
		SecurityManager sm = System.getSecurityManager();
		if (sm != null) sm.checkExit(0);
		MenuBarCreator.addItem(menu, new QuitAction());
	    } catch (SecurityException e) {
		// Well, can't exit anyway.
	    }
	    menuBar.add(menu);
	    menu = new JMenu("Help");
	    MenuBarCreator.addItem(menu, new NewHelpAction());
	    MenuBarCreator.addItem(menu, new AboutAction());
	    menuBar.add(menu);
	    setJMenuBar(menuBar);
	}

	private void initComponents() {
	    JButton button = null;
	    // Let's hear it for sloth!
	    
	    button = new JButton("Finite Automaton");
	    button.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
			createWindow(new automata.fsa.FiniteStateAutomaton());
		    }
		});
	    getContentPane().add(button);

	    button = new JButton("Pushdown Automaton");
	    button.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
			createWindow(new automata.pda.PushdownAutomaton());
		    }
		});
	    getContentPane().add(button);

	    button = new JButton("Turing Machine");
	    button.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
			createWindow(new automata.turing.TuringMachine(1));
		    }
		});
	    getContentPane().add(button);

	    button = new JButton("Multi-Tape Turing Machine");
	    button.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
			if (INTS==null) {
			    INTS = new Integer[4];
			    for (int i=0; i<INTS.length; i++)
				INTS[i] = new Integer(i+2);
			}
			Number n = (Number) JOptionPane.showInputDialog
			    (NewDialog.this.getContentPane(), "How many tapes?", 
			     "Multi-tape Machine", JOptionPane.QUESTION_MESSAGE,
			     null, INTS, INTS[0]);
			if (n==null) return;
			createWindow
			    (new automata.turing.TuringMachine(n.intValue()));
		    }
		    private Integer[] INTS = null;
		});
	    getContentPane().add(button);

	    button = new JButton("Grammar");
	    button.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
			createWindow(new grammar.cfg.ContextFreeGrammar());
		    }
		});
	    getContentPane().add(button);

	    button = new JButton("L-System");
	    button.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
			createWindow(new grammar.lsystem.LSystem());
		    }
		});
	    getContentPane().add(button);

	    button = new JButton("Regular Expression");
	    button.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
			createWindow(new regular.RegularExpression());
		    }
		});
	    getContentPane().add(button);
	}
    }

    /** The universal dialog. */
    private static NewDialog DIALOG = null;
}
