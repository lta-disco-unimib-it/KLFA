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
