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
 
// I lifted this code directly from http://www.codeguru.com/java/articles/122.shtml.

// JMultiLineToolTip.java
package gui;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;

import javax.swing.CellRendererPane;
import javax.swing.JComponent;
import javax.swing.JTextArea;
import javax.swing.JToolTip;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.basic.BasicToolTipUI;

/**
 * @author Zafir Anjum
 */

 public class JMultiLineToolTip extends JToolTip
{
	private static final String uiClassID = "ToolTipUI";
	
	String tipText;
	JComponent component;
	
	public JMultiLineToolTip() {
	    updateUI();
	}
	
	public void updateUI() {
	    setUI(MultiLineToolTipUI.createUI(this));
	}
	
	public void setColumns(int columns)
	{
		this.columns = columns;
		this.fixedwidth = 0;
	}
	
	public int getColumns()
	{
		return columns;
	}
	
	public void setFixedWidth(int width)
	{
		this.fixedwidth = width;
		this.columns = 0;
	}
	
	public int getFixedWidth()
	{
		return fixedwidth;
	}
	
	protected int columns = 0;
	protected int fixedwidth = 0;
}



class MultiLineToolTipUI extends BasicToolTipUI {
	static MultiLineToolTipUI sharedInstance = new MultiLineToolTipUI();
	Font smallFont; 			     
	static JToolTip tip;
	protected CellRendererPane rendererPane;
	
	private static JTextArea textArea ;
	
	public static ComponentUI createUI(JComponent c) {
	    return sharedInstance;
	}
	
	public MultiLineToolTipUI() {
	    super();
	}
	
	public void installUI(JComponent c) {
	    super.installUI(c);
		tip = (JToolTip)c;
	    rendererPane = new CellRendererPane();
	    c.add(rendererPane);
	}
	
	public void uninstallUI(JComponent c) {
		super.uninstallUI(c);
		
	    c.remove(rendererPane);
	    rendererPane = null;
	}
	
	public void paint(Graphics g, JComponent c) {
	    Dimension size = c.getSize();
	    textArea.setBackground(c.getBackground());
		rendererPane.paintComponent(g, textArea, c, 1, 1,
					    size.width - 1, size.height - 1, true);
	}
	
	public Dimension getPreferredSize(JComponent c) {
		String tipText = ((JToolTip)c).getTipText();
		if (tipText == null)
			return new Dimension(0,0);
		textArea = new JTextArea(tipText );
	    rendererPane.removeAll();
		rendererPane.add(textArea );
		textArea.setWrapStyleWord(true);
		int width = ((JMultiLineToolTip)c).getFixedWidth();
		int columns = ((JMultiLineToolTip)c).getColumns();
		
		if( columns > 0 )
		{
			textArea.setColumns(columns);
			textArea.setSize(0,0);
		textArea.setLineWrap(true);
			textArea.setSize( textArea.getPreferredSize() );
		}
		else if( width > 0 )
		{
		textArea.setLineWrap(true);
			Dimension d = textArea.getPreferredSize();
			d.width = width;
			d.height++;
			textArea.setSize(d);
		}
		else
			textArea.setLineWrap(false);


		Dimension dim = textArea.getPreferredSize();
		
		dim.height += 1;
		dim.width += 1;
		return dim;
	}
	
	public Dimension getMinimumSize(JComponent c) {
	    return getPreferredSize(c);
	}
	
	public Dimension getMaximumSize(JComponent c) {
	    return getPreferredSize(c);
	}
}
