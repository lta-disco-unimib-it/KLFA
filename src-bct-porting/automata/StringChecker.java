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

/**
 * The StringChecker class is useful for determining whether a string
 * has certain characteristics.
 * 
 * @author Thomas Finley
 */

public class StringChecker {
    /**
     * We can't have people creating instances of us, now can we?
     */
    private StringChecker() { }

    /**
     * Determines if all characters in a string are alphanumeric,
     * i.e., are either digits or numbers.
     * @param string the string to check
     * @return <CODE>true</CODE> if all characters in the string are
     * alphanumeric, <CODE>false</CODE> if at least one character in
     * the string is non-alphanumeric
     */
    public static boolean isAlphanumeric(String string) {
	for (int i=0; i<string.length(); i++)
	    if (!Character.isLetterOrDigit(string.charAt(i)))
		return false;
	return true;
    }
}
