package sjm.parse.chars;

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
 * A Letter matches any letter from a character assembly.
 * 
 * @author Steven J. Metsker
 * 
 * @version 1.0 
 */
public class Letter extends Terminal {

/**
 * Returns true if an assembly's next element is a letter.
 *
 * @param   object   an element from an assembly
 *
 * @return   true, if an assembly's next element is a letter
 */
public boolean qualifies(Object o) {
	Character c = (Character) o;
	return Character.isLetter(c.charValue());
}
/**
 * Create a set with one random letter.
 */
public Vector randomExpansion(int maxDepth, int depth) {
	char c = (char) (26.0 * Math.random() + 'a');
	Vector v = new Vector();
	v.addElement(new String(new char[]{c}));
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
	return "L";
}
}
