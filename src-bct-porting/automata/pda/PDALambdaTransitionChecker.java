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
 
package automata.pda;

import automata.LambdaTransitionChecker;
import automata.Transition;

/**
 * The pda lambda transition checker can be used to determine if a 
 * pushdown automaton's transition is a lambda transition
 *
 * @author Ryan Cavalcante
 */

public class PDALambdaTransitionChecker extends LambdaTransitionChecker {
    /**
     * Creates a <CODE>PDALambdaTransitionChecker</CODE>
     */
    public PDALambdaTransitionChecker() {
	super();
    }

    /**
     * Returns true if <CODE>transition</CODE> is a lambda transition
     * (i.e. all three of its fields are the lambda string).
     * @param transition the transition
     * @return true if <CODE>transition</CODE> is a lambda transition
     * (i.e. all three of its fields are the lambda string).
     */
    public boolean isLambdaTransition(Transition transition) {
	PDATransition trans = (PDATransition) transition;
	String input = trans.getInputToRead();
	String toPop = trans.getStringToPop();
	String toPush = trans.getStringToPush();
	if(input.equals(LAMBDA) && toPop.equals(LAMBDA) 
	   && toPush.equals(LAMBDA)) return true;
	return false;
    }

}
