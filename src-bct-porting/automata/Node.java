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


/**
 * A node object is a data structure used in graph algorithms.
 * It stores a State object and a color.  This object is used 
 * to find the unreachable states in an automaton.
 * 
 * @see automata.State
 *
 * @author Ryan Cavalcante
 */

public class Node {
    /**
     * Creates a Node object with no state and no color.
     */
    public Node() {
	myState = null;
	myColor = "";
    }

    /**
     * Creates a Node object with <CODE>state</CODE> and no
     * color.
     * @param state the state contained by the Node object
     */
    public Node(State state) {
	myState = state;
	myColor = "";
    }
    
    /**
     * Creates a Node object with <CODE>state</CODE> and 
     * <CODE>color</CODE>.
     * @param state the state contained by the Node object
     * @param color the color of the node.
     */
    public Node(State state, String color) {
	myState = state;
	myColor = color;
    }

    /**
     * Returns the state contained by the Node object.
     * @return the state contained by the Node object.
     */
    public State getState() {
	return myState;
    }

    /**
     * Returns the color of the Node object.
     * @return the color of the Node object.
     */
    public String getColor() {
	return myColor;
    }

    /**
     * Colors the node white.
     */
    public void colorWhite() {
	myColor = WHITE;
    }

    /**
     * Colors the Node grey.
     */
    public void colorGrey() {
	myColor = GREY;
    }

    /**
     * Colors the Node black.
     */
    public void colorBlack() {
	myColor = BLACK;
    }

    /**
     * Returns true if the Node is white.
     * @return true if the Node is white.
     */
    public boolean isWhite() {
	if(myColor.equals(WHITE)) return true;
	return false;
    }

    /**
     * Returns true if the Node is grey.
     * @return true if the Node is grey.
     */
    public boolean isGrey() {
	if(myColor.equals(GREY)) return true;
	return false;
    }

    /**
     * Returns true if the Node is black.
     * @return true if the Node is black.
     */
    public boolean isBlack() {
	if(myColor.equals(BLACK)) return true;
	return false;
    }

    /**
     * Returns a string representation of the Node object, returning
     * a string representation of its state and color.
     * @return a string representation of the Node object.
     */
    public String toString() {
	return "STATE: " + myState.toString() + " COLOR: " + myColor;
    }

    /** Color of node. */
    protected String myColor;
    /** State of node. */
    protected State myState;
    /** String for white. */
    protected static final String WHITE = "white";
    /** String for grey. */
    protected static final String GREY = "grey";
    /** String for black. */
    protected static final String BLACK = "black";
}
