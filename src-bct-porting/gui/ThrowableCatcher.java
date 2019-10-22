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
 
package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringWriter;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

/**
 * Handles uncaught exceptions and errors in the AWT thread.  The goal
 * of JFLAP's code is, of course, to not have any uncaught exceptions
 * at all.  However, inevitably mistakes happen, and when they do it
 * is best if the users can be made to give information of somewhat
 * better quality than "it doesn't work!"  This will bring up a dialog
 * box with the stack trace and any other relevant information.
 *
 * @author Thomas Finley
 */

public class ThrowableCatcher {
    /**
     * Handles an exception uncaught by our code.
     * @param throwable the throwable we are trying to catch
     */
    public void handle(Throwable throwable) {
	String message = null;
	String report = null;
	try {
	    // Read the error message.
	    InputStream is = getClass().getResource(ERROR_LOCATION)
		.openStream();
	    BufferedReader reader =
		new BufferedReader(new InputStreamReader(is));
	    StringBuffer sb = new StringBuffer();
	    String nextLine = null;
	    while ((nextLine = reader.readLine()) != null)
		sb.append(nextLine);
	    message = sb.toString();
	    
	    // Compose the report.
	    StringWriter w = new StringWriter();
	    PrintWriter writer = new PrintWriter(w);
	    writer.println("PROPERTIES");
	    System.getProperties().list(writer);
	    writer.println("TRACE");
	    throwable.printStackTrace(writer);
	    writer.flush(); w.flush();
	    report = w.toString();
	} catch (Throwable e) {
	    System.err.println("Could not display AWT error message.");
	    throwable.printStackTrace(); // Not a total loss.
	    return;
	}
	JPanel panel = new JPanel(new BorderLayout());
	panel.add(new JLabel(message), BorderLayout.NORTH);
	JTextArea area = new JTextArea(report);
	area.setEditable(false);
	panel.add(new JScrollPane(area), BorderLayout.CENTER);
	panel.setPreferredSize(new Dimension(400,400));
	JOptionPane.showMessageDialog(null, panel);
    }

    /** The location of the uncaught error message. */
    private static final String ERROR_LOCATION = "/DOCS/error.html";
}
