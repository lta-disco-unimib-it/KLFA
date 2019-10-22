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
 
package gui.environment;

import file.Codec;
import file.ParseException;

import java.awt.BorderLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.Serializable;
import java.util.Iterator;
import java.util.List;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileFilter;

/**
 * The <CODE>EnvironmentFrame</CODE> is the general sort of frame for
 * holding an environment.
 * 
 * @author Thomas Finley
 */

public class EnvironmentFrame extends JFrame {
    /**
     * Instantiates a new <CODE>EnvironmentFrame</CODE>.  This does
     * not fill the environment with anything.
     * @param environment the environment that the frame is created for
     */
    public EnvironmentFrame(Environment environment) {
	this.environment = environment;
	environment.addFileChangeListener(new FileChangeListener() {
		public void fileChanged(FileChangeEvent e) {refreshTitle();}
	    });
	initMenuBar();
	this.getContentPane().setLayout(new BorderLayout());
	this.getContentPane().add(environment, BorderLayout.CENTER);

	// Register this frame with the universe.
	myNumber = Universe.registerFrame(this);
	refreshTitle();

	this.addWindowListener(new Listener());
	this.setLocation(50,50);
	this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
    }

    /**
     * Returns a simple identifying string for this frame.
     * @return a simple string that identifies this frame
     */
    public String getDescription() {
	if (environment.getFile() == null) return "<untitled"+myNumber+">";
	else return "("+environment.getFile().getName()+")";
    }
    
    /**
     * Sets the title on this frame to be the name of the file for the
     * environment, or untitled if there is no file for this
     * environment yet.
     */
    protected void refreshTitle() {
	String title = DEFAULT_TITLE + " : " + getDescription();
	setTitle(title);
    }

    /**
     * Initializes the menu bar for this frame.
     */
    protected void initMenuBar() {
	this.setJMenuBar(gui.menu.MenuBarCreator.getMenuBar(this));
    }

    /**
     * Returns the environment for this frame.
     * @return the environment for this frame
     */
    public Environment getEnvironment() {
	return environment;
    }

    /**
     * Saves the environment's object to a file.  This serializes the
     * object found in the environment, and then writes it to the
     * file of the environment.
     * @param saveAs if <CODE>true</CODE> this will prompt the user
     * with a save file dialog box no matter what, otherwise the user
     * will only be prompted if the environment has no file
     * @return <CODE>true</CODE> if the save was a success,
     * <CODE>false</CODE> if it was not
     */
    public boolean save(boolean saveAs) {
	File file = saveAs ? null : environment.getFile();
	Codec codec = (Codec) environment.getEncoder();
	Serializable object = environment.getObject();
	boolean badname = false;

	// Is this encoder valid?
	if (file != null && (codec == null || !codec.canEncode(object))) {
	    JOptionPane.showMessageDialog
		(this, "We cannot write this structure in the same format\n"+
		 "it was read as!  Use Save As to select a new format.",
		 "IO Error", JOptionPane.ERROR_MESSAGE);
	    return false;
	}

	// Set the file filters.
	FileFilter[] filters = Universe.CHOOSER.getChoosableFileFilters();
	for (int i=0; i<filters.length; i++)
	    Universe.CHOOSER.removeChoosableFileFilter(filters[i]);
	List encoders = Universe.CODEC_REGISTRY.getEncoders(object);
	Iterator it = encoders.iterator();
	while (it.hasNext())
	    Universe.CHOOSER.addChoosableFileFilter((FileFilter)it.next());
	if (codec != null && codec.canEncode(object)) {
	    Universe.CHOOSER.setFileFilter(codec);
	} else {
	    Universe.CHOOSER.setFileFilter((FileFilter)encoders.get(0));
	}
	// Check the name.
	if (file != null && codec != null) {
	    // Get the suggested file name.
	    String filename = file.getName();
	    String newname = codec.proposeFilename(filename, object);
	    if (!filename.equals(newname)) {
		int result = JOptionPane.showConfirmDialog
		    (this, "To save as a "+codec.getDescription()+",\n"+
		     "JFLAP wants to save "+filename+" to a new file\n"+
		     "named "+newname+".  Is that OK?");
		switch (result) {
		case JOptionPane.CANCEL_OPTION:
		    // They cancelled.  Get out of here.
		    return false;
		case JOptionPane.NO_OPTION:
		    // No, it's not OK!  Use the original name.
		    break;
		case JOptionPane.YES_OPTION:
		    // Yes, we want the new name!  Change the file.
		    file = new File(file.getParent(), newname);
		    badname = true;
		}
	    }
	}
	// The save as loop.
	while (badname || file == null) {
	    if (!badname) {
		int result = Universe.CHOOSER.showSaveDialog(this);
		if (result != JFileChooser.APPROVE_OPTION)
		    return false;
		file = Universe.CHOOSER.getSelectedFile();
		// Get the suggested file name.
		String filename = file.getName();
		codec = (Codec) Universe.CHOOSER.getFileFilter();
		file = new File(file.getParent(), codec.proposeFilename
				(filename, object));
		// Check for the existing file.
	    }
	    badname = false;
	    if (file.exists()) {
		int result = JOptionPane.showConfirmDialog
		    (this, "Overwrite "+file.getName()+"?");
		switch (result) {
		case JOptionPane.CANCEL_OPTION:
		    return false;
		case JOptionPane.NO_OPTION:
		    file = null;
		    continue;
		default:
		}
	    }
	}
	System.out.println("CODEC: "+codec.getDescription());
	Universe.CHOOSER.resetChoosableFileFilters();

	// Use the codec to save the file.
	try {
	    codec.encode(object, file, null);
	    environment.setFile(file);
	    environment.setEncoder(codec);
	    environment.clearDirty();
	    return true;
	} catch (ParseException e) {
	    JOptionPane
		.showMessageDialog(this, e.getMessage(),
				   "Write Error", JOptionPane.ERROR_MESSAGE);
	    return false;
	}
    }

    /**
     * Attempts to close an environment frame.
     * @return <CODE>true</CODE> if the window was successfully
     * closed, <CODE>false</CODE> if the window could not be closed at
     * this time (probably user intervention)
     */
    public boolean close() {
	if (environment.isDirty()) {
	    File file = environment.getFile();
	    String title;
	    if (file == null) title = "untitled";
	    else title = file.getName();

	    int result =
		JOptionPane.showConfirmDialog(this, "Save "+title+
					      " before closing?");
	    if (result == JOptionPane.CANCEL_OPTION) return false;
	    if (result == JOptionPane.YES_OPTION) save(false);
	}
	dispose();
	Universe.unregisterFrame(this);
	return true;
    }

    /**
     * Returns the string that describes this frame.
     * @return the string that describes this frame
     */
    public String toString() {
	return getDescription();
    }

    /** The environment that this frame displays. */
    private Environment environment;
    /** The number of this environment frames. */
    private int myNumber = 0xdeadbeef;
    /** The default title for these frames. */
    private static final String DEFAULT_TITLE = "JFLAP";

    /**
     * The window listener for this frame.
     */
    private class Listener extends WindowAdapter {
	public void windowClosing(WindowEvent event) {
	    close();
	}
    }
}
