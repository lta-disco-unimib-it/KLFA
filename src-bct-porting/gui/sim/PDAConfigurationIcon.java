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

import java.awt.Component;
import java.awt.Graphics2D;

import automata.Configuration;
import automata.pda.PDAConfiguration;

/**
 * This is a configuration icon for configurations related to finite
 * state automata.  These sorts of configurations are defined only by
 * the state that the automata is current in, plus the input left.
 * 
 * @author Thomas Finley
 */

public class PDAConfigurationIcon extends ConfigurationIcon {
    /**
     * Instantiates a new <CODE>PDAConfigurationIcon</CODE>.
     * @param configuration the PDA configuration that is represented
     * @param automaton the PD-automaton that this configuration
     * "arose" from
     */
    public PDAConfigurationIcon(Configuration configuration) {
	super(configuration);
    }

    /**
     * Returns the height of this icon.
     * @return the height of this icon
     */
    public int getIconHeight() {
	// Why not...
	return super.getIconHeight() + 25;
    }

    /**
     * This will paint a sort of "torn tape" object that shows the
     * rest of the input, as well as the stack.
     * @param c the component this icon is drawn on
     * @param g the <CODE>Graphics2D</CODE> object to draw on
     * @param width the width to draw the configuration in
     * @param height the height to draw the configuration in
     */
    public void paintConfiguration(Component c, Graphics2D g,
				   int width, int height) {
	super.paintConfiguration(c, g, width, height);
	PDAConfiguration config = (PDAConfiguration) getConfiguration();
	// Draw the torn tape with the rest of the input.
	Torn.paintString((Graphics2D)g, config.getInput(),
			 RIGHT_STATE.x+5.0f,
			 ((float)super.getIconHeight())*0.5f, 
			 Torn.MIDDLE, width-RIGHT_STATE.x-5.0f,
			 false, true, config.getInput().length()-
			 config.getUnprocessedInput().length());
	// Draw the stack.
	Torn.paintString((Graphics2D)g, config.getStack().toString(),
			 BELOW_STATE.x, BELOW_STATE.y + 5.0f,
			 Torn.TOP, getIconWidth(), false, true, -1);
    }
}
