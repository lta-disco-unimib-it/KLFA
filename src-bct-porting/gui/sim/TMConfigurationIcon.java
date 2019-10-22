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

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics2D;

import automata.Configuration;
import automata.turing.TMConfiguration;
import automata.turing.Tape;

/**
 * This is a configuration icon for configurations related to finite
 * state automata.  These sorts of configurations are defined only by
 * the state that the automata is current in, plus the input left.
 * 
 * @author Thomas Finley
 */

public class TMConfigurationIcon extends ConfigurationIcon
    implements TuringConstants {
    /**
     * Instantiates a new <CODE>TMConfigurationIcon</CODE>.
     * @param configuration the TM configuration that is represented
     */
    public TMConfigurationIcon(Configuration configuration) {
	super(configuration);
	config = (TMConfiguration) configuration;
    }

    /**
     * Returns the height of this icon.
     * @return the height of this icon
     */
    public int getIconHeight() {
	// Why not...
	return super.getIconHeight() + 25*config.getTapes().length;
    }

    /**
     * This will paint a sort of "torn tape" object that shows the
     * current contents and position of the tape.
     * @param c the component this icon is drawn on
     * @param g the <CODE>Graphics2D</CODE> object to draw on
     */
    public void paintConfiguration(Component c, Graphics2D g,
				   int width, int height) {
	if (c != null) super.paintConfiguration(c, g, width, height);
	float position = BELOW_STATE.y + 5.0f;
	int headx = BELOW_STATE.x + width/2;
	int heady = BELOW_STATE.y + 5;
	
	Tape[] tapes = config.getTapes();

	for (int i=0; i<tapes.length; i++) {
	    float tornHeight=Torn.paintString
		(g, FIX+tapes[i].getContents()+FIX,
		 BELOW_STATE.x, position, Torn.TOP, width, true, true,
		 tapes[i].getTapeHead()+FIX.length());
	    g.setColor(Color.black);
	    g.drawLine(headx, heady, headx-SIZE_HEAD, heady-SIZE_HEAD);
	    g.drawLine(headx, heady, headx+SIZE_HEAD, heady-SIZE_HEAD);
	    position += tornHeight + 8f;
	}
	position -= 8f;
    }

    /** The turing machine configuration. */
    private TMConfiguration config;
}
