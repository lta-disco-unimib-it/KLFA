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
 
package gui.sim;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.LinkedList;

import javax.swing.Icon;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JScrollPane;

import automata.Configuration;

/**
 * A class that epitomizes the ultimate in bad design: a fusion of
 * model, view, and controller.  Look upon my works, ye mighty, and
 * despair.
 *
 * @author Thomas Finley
 */

public class TraceWindow extends JFrame {
    /**
     * Instantiates a new step window with the given configuration.
     * @param last the last configuration that we are tracing; we
     * display it along with its parents
     */
    public TraceWindow(Configuration last) {
	super("Traceback");
	getContentPane().setLayout(new BorderLayout());
	getContentPane().add(getPastPane(last));
	pack();
	if (getSize().height > MAXHEIGHT)
	    setSize(getSize().width, MAXHEIGHT);
	setVisible(true);
    }

    /**
     * Returns a component that displays the ancestry of a
     * configuration.
     * @param configuration the configuration whose ancestry we want
     * to display
     * @return a component with the ancestry of the configuration
     */
    public static Component getPastPane(Configuration configuration) {
	JScrollPane sp =
	    new JScrollPane(new PastPane(configuration),
			    JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
			    JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
	sp.validate();
	if (sp.getSize().height > MAXHEIGHT) {
	    sp.setSize(sp.getSize().width, MAXHEIGHT);
	}
	return sp;
    }
    
    /**
     * Returns a component that displays the ancestry of a
     * configuration.
     * @param configuration the configuration whose ancestry we want
     * to display
     * @return a component with the ancestry of the configuration
     */
    public static PastPane getPastPane2(Configuration configuration) {
	return new PastPane(configuration);
    }

    /** The pane that displays the past configurations. */
    public static class PastPane extends JComponent {
	public PastPane(Configuration last) {
	    setConfiguration(last);
	}

	public void setConfiguration(Configuration last) {
	    java.util.List list = new LinkedList();
	    int height = 0;
	    int width = 0;
	    while (last != null) {
		Icon icon =
		    ConfigurationIconFactory.iconForConfiguration(last);
		width = Math.max(width, icon.getIconWidth());
		height += icon.getIconHeight() + ARROW_LENGTH;
		list.add(icon);
		last = last.getParent();
	    }
	    icons = (Icon[]) list.toArray(new Icon[0]);
	    this.setPreferredSize(new Dimension(width, height));
	}

	public void paintComponent(Graphics g) {
	    Rectangle visible = getVisibleRect();
	    int height = ARROW_LENGTH + icons[0].getIconHeight();
	    int max = icons.length-1 - visible.y / height;
	    int min = icons.length-1 - (visible.y + visible.height) / height;
	    try {
		min = Math.max(min, 0);
		g = g.create();
		g.translate(0, height*(icons.length-1-max));
		for (int i=max; i>=min; i--) {
		    drawArrow(g);
		    drawIcon(g, icons[i]);
		}
		g.dispose();
	    } catch (Throwable e) {
		System.err.println(e);
	    }
	}

	public final void drawArrow(Graphics g) {
	    int center = getWidth()>>1;
	    g.setColor(Color.black);
	    g.drawLine(center, 0, center, ARROW_LENGTH);
	    g.drawLine(center, ARROW_LENGTH, center-10, ARROW_LENGTH-10);
	    g.drawLine(center, ARROW_LENGTH, center+10, ARROW_LENGTH-10);
	    g.translate(0, ARROW_LENGTH);
	}
	
	public final void drawIcon(Graphics g, Icon icon) {
	    icon.paintIcon(this, g, (getWidth()-icon.getIconWidth())>>1, 0);
	    g.translate(0, icon.getIconHeight());
	}
	
	private Icon[] icons;
	private static final int ARROW_LENGTH = 20;
    }

    /** The maximum height these trace displays should get to. */
    private static final int MAXHEIGHT = 400;
}
