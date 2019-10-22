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

/**
 * This interface holds constants necessary for the drawing of
 * configuration icons for Turing machines.
 * 
 * @author Thomas Finley
 */

interface TuringConstants {
    /** A simple prefix/postfix string for tape. */
    public static final String FIX = FixCreator.getFix();
    /** The size of the tape head. */
    public static final int SIZE_HEAD = 4;

    static class FixCreator {
	public static String getFix() {
	    char c = automata.turing.Tape.BLANK;
	    StringBuffer b = new StringBuffer();
	    for (int i=0; i<20; i++)
		b.append(c);
	    return b.toString();
	}
    }
}
