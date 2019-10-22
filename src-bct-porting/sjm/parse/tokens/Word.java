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
 * A Word matches a word from a token assembly.
 * 
 * @author Steven J. Metsker
 * 
 * @version 1.0 
 */
public class Word extends Terminal {

/**
 * Returns true if an assembly's next element is a word.
 *
 * @param   object   an element from an assembly
 *
 * @return   true, if an assembly's next element is a word
 */
protected boolean qualifies(Object o) {
	Token t = (Token) o;
	return t.isWord();
}
/**
 * Create a set with one random word (with 3 to 7 
 * characters).
 */
public Vector randomExpansion(int maxDepth, int depth) {
	int n = (int) (5.0 * Math.random()) + 3;
	
	char[] letters = new char[n];
	for (int i = 0; i < n; i++) {
		int c = (int) (26.0 * Math.random()) + 'a';
		letters[i] = (char) c;
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
	return "Word";
}
}
