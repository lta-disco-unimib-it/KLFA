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
import gui.viewer.AutomatonPane;

import java.util.List;

/**
 * A <CODE>ToolBox</CODE> is an object used for defining what tools
 * are in a <CODE>ToolBar</CODE> object.
 * 
 * @see gui.editor.ToolBar
 * @see gui.editor.Tool
 * 
 * @author Thomas Finley
 */

public interface ToolBox {
    /**
     * Returns a list of tools in the order they should be in the tool
     * bar.
     * @param view the view that the automaton will be drawn in
     * @param drawer the automaton drawer for the view
     */
    public List tools(AutomatonPane view, AutomatonDrawer drawer);
}
