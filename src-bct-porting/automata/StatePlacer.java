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
 
package automata;

import java.awt.Point;

/**
 * The state placer object can be used to determine the location
 * (on the canvas) to place a State.  Currently, the placement 
 * algorithm is simply choosing random x and y coordinates in the 
 * range of 0 to X_MAX and Y_MAX respectively.
 *
 * @author Ryan Cavalcante
 */

public class StatePlacer {
    /**
     * Instantiates a <CODE>StatePlacer</CODE>.
     */
    public StatePlacer() {
	
    }

    /**
     * Returns a Point object that represents where to place
     * the State on the canvas.
     * @param automaton the automaton.
     * @return a Point object that represents where to place
     * the State on the canvas.
     */
    public Point getPointForState(Automaton automaton) {
	double xcoord = Math.random() * X_MAX;
	int x = (int) xcoord;
	double ycoord = Math.random() * Y_MAX;
	int y = (int) ycoord;
	return new Point(x,y);
    }

    /** The maximum value for the X-coordinate. */
    protected final static int X_MAX = 600;
    /** The maximum value for the Y-coordinate. */
    protected final static int Y_MAX = 600;
}
