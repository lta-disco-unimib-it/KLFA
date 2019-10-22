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

import file.Codec;
import file.ParseException;
import gui.environment.EnvironmentFrame;
import gui.environment.FrameFactory;
import gui.environment.Universe;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.Serializable;
import java.util.Iterator;
import java.util.List;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;
import javax.swing.filechooser.FileFilter;

/**
 * The <CODE>OpenAction</CODE> is an action to load a structure from a
 * file, and create a new environment with that object.
 * 
 * @author Thomas Finley
 */

public class OpenAction extends RestrictedAction {
    /**
     * Instantiates a new <CODE>OpenAction</CODE>.
     */
    public OpenAction() {
	super("Open...", null);
	putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke
		 (KeyEvent.VK_O, MAIN_MENU_MASK));
	this.fileChooser = Universe.CHOOSER;
    }

    /**
     * If an open is attempted, call the methods that handle the
     * retrieving of an object, then create a new frame for the
     * environment.
     * @param event the action event
     */
    public void actionPerformed(ActionEvent event) {
	Component source = null;
	try {
	    source = (Component) event.getSource();
	} catch (Throwable e) {
	    // Might not be a component, or the event may be null.
	    // Who cares.
	}

	// Apple is so fucking stupid.
	File tempFile = fileChooser.getCurrentDirectory();
	fileChooser.setCurrentDirectory(tempFile.getParentFile());
	fileChooser.setCurrentDirectory(tempFile);
	fileChooser.rescanCurrentDirectory();

	// Set up the file filters.
	Universe.CHOOSER.resetChoosableFileFilters();
	List decoders = Universe.CODEC_REGISTRY.getDecoders();
	Iterator it = decoders.iterator();
	while (it.hasNext())
	    Universe.CHOOSER.addChoosableFileFilter((FileFilter)it.next());
	Universe.CHOOSER.setFileFilter
	    (Universe.CHOOSER.getAcceptAllFileFilter());

	// Open the dialog.
	int result = fileChooser.showOpenDialog(source);
	if (result != JFileChooser.APPROVE_OPTION) return;
	File file = fileChooser.getSelectedFile();
	// Get the decoders.
	Codec[] codecs = null;
	FileFilter filter = Universe.CHOOSER.getFileFilter();
	if (filter == Universe.CHOOSER.getAcceptAllFileFilter()) {
	    codecs = (Codec[]) decoders.toArray(new Codec[0]);
	} else {
	    codecs = new Codec[1];
	    codecs[0] = (Codec) filter;
	}
	Universe.CHOOSER.resetChoosableFileFilters();

	// Is this file already open?
	if (Universe.frameForFile(file) != null) {
	    Universe.frameForFile(file).toFront();
	    return;
	}

	try {
	    openFile(file, codecs);
	} catch (ParseException e) {
	    JOptionPane.showMessageDialog
		(source, e.getMessage(),
		 "Read Error", JOptionPane.ERROR_MESSAGE);
	}
    }

    /**
     * Attempts to open a specified file with a set of codecs.
     * @param file the file to attempt to open
     * @param codecs the codecs to use
     * @throws ParseException if there was an error with all or one of the
     * codecs
     */
    public static void openFile(File file, Codec[] codecs) {
	ParseException p = null;
	for (int i=0; i<codecs.length; i++) {
	    try {
		Serializable object = codecs[i].decode(file, null);
		// Set the file on the thing.
		EnvironmentFrame ef = FrameFactory.createFrame(object);
		if (ef == null) return;
		ef.getEnvironment().setFile(file);
		ef.getEnvironment().setEncoder
		    (codecs[i].correspondingEncoder());
		return;
	    } catch (ParseException e) {
		p = e;
	    }
	}
	if (codecs.length != 1)
	    p = new ParseException("No format could read the file!");
	throw p;
    }

    /**
     * The open action is completely environment independent.
     * @param object some object, which is ignored
     * @return always returns <CODE>true</CODE>
     */
    public static boolean isApplicable(Object object) {
	return true;
    }

    /** The file chooser. */
    private JFileChooser fileChooser; 

    /** The exception class for when a file could not be read properly. */
    protected static class FileReadException extends RuntimeException {
	/**
	 * Instantiates a file read exception with a given message.
	 * @param message the specific message for why the read failed
	 */
	public FileReadException(String message) {
	    super(message);
	}
    }
}
