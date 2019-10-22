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
 * The <CODE>DefaultToolBox</CODE> has all the tools for general
 * editing of an automaton.
 */

public class DefaultToolBox implements ToolBox {
    /**
     * Returns a list of tools including a <CODE>ArrowTool</CODE>,
     * <CODE>StateTool</CODE>, <CODE>TransitionTool</CODE> and
     * <CODE>DeleteTool</CODE>, in that order.
     * @param view the component that the automaton will be drawn in
     * @param drawer the drawer that will draw the automaton in the
     * view
     * @return a list of <CODE>Tool</CODE> objects.
     */
    public List tools(AutomatonPane view, AutomatonDrawer drawer) {
	List list = new java.util.ArrayList();
	list.add(new ArrowTool(view, drawer));
	list.add(new StateTool(view, drawer));
	list.add(new TransitionTool(view, drawer));
	list.add(new DeleteTool(view, drawer));
	return list;
    }
}
