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
