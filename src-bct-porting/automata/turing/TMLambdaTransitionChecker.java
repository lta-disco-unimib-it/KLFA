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
 
package automata.turing;

import automata.LambdaTransitionChecker;
import automata.Transition;

/**
 * The tm lambda transition checker can be used to check if a one-tape
 * Turing machine's transition is a lambda transition
 *
 * @author Ryan Cavalcante
 */

public class TMLambdaTransitionChecker extends LambdaTransitionChecker {
    /**
     * Returns true if <CODE>transition</CODE> is a lambda transition
     * @param transition the transition
     * @return true if <CODE>transition</CODE> is a lambda transition
     */
    public boolean isLambdaTransition(Transition transition) {
	return false;
    }

}
