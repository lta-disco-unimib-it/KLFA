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
