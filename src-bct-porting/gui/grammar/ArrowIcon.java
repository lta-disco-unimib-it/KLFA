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
 
package gui.grammar;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;

import javax.swing.Icon;

/**
 * This is an icon that draws a simple arrow pointing to the right.
 * 
 * @author Thomas Finley
 */

public class ArrowIcon implements Icon {
    /**
     * Instantiates a new <CODE>ArrowIcon</CODE>.
     * @param width the width of this icon
     * @param height the height of this icon
     */
    public ArrowIcon(int width, int height) {
	this.width = width;
	this.height = height;
    }

    /**
     * Returns the icon's height.
     * @return the icon's height
     */
    public int getIconHeight() {
	return height;
    }

    /**
     * Returns the icon's width.
     * @return the icon's width
     */
    public int getIconWidth() {
	return width;
    }

    /**
     * Paints the arrow.
     */
    public void paintIcon(Component c, Graphics g, int x, int y) {
	g.setColor(Color.black);
	g.drawLine(x, y+height/2, x+width, y+height/2);
	g.drawLine(x+width-height/2, y, x+width, y+height/2);
	g.drawLine(x+width-height/2, y+height, x+width, y+height/2);
    }

    /** The width of the icon. */
    private int width;
    /** The height of the icon. */
    private int height;
}
