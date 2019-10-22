package sjm.parse.tokens;

import java.util.Vector;

import sjm.parse.Parser;
import sjm.parse.Terminal;
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

/**
 * A QuotedString matches a quoted string, like "this one" 
 * from a token assembly.
 * 
 * @author Steven J. Metsker
 * 
 * @version 1.0 
 */
public class QuotedString extends Terminal {
/**
 * Returns true if an assembly's next element is a quoted 
 * string.
 *
 * @param   object   an element from a assembly
 *
 * @return   true, if a assembly's next element is a quoted 
 *           string, like "chubby cherubim".
 */
protected boolean qualifies(Object o) {
	Token t = (Token) o;
	return t.isQuotedString();
}
/**
 * Create a set with one random quoted string (with 2 to
 * 6 characters).
 */
public Vector randomExpansion(int maxDepth, int depth) {
	int n = (int) (5.0 * Math.random());
	
	char[] letters = new char[n + 2];
	letters[0] = '"';
	letters[n + 1] = '"';
	
	for (int i = 0; i < n; i++) {
		int c = (int) (26.0 * Math.random()) + 'a';
		letters[i + 1] = (char) c;
	}
	
	Vector v = new Vector();
	v.addElement(new String(letters));
	return v;
}
/**
 * Returns a textual description of this parser.
 *
 * @param   vector   a list of parsers already printed in
 *                   this description
 * 
 * @return   string   a textual description of this parser
 *
 * @see Parser#toString()
 */
public String unvisitedString(Vector visited) {
	return "QuotedString";
}
}
