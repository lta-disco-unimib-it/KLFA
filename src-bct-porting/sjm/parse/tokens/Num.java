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
 * A Num matches a number from a token assembly.
 * 
 * @author Steven J. Metsker
 * 
 * @version 1.0 
 */

public class Num extends Terminal {

/**
 * Returns true if an assembly's next element is a number.
 *
 * @param   object   an element from an assembly
 *
 * @return   true, if an assembly's next element is a number as
 *           recognized the tokenizer
 */
protected boolean qualifies(Object o) {
	Token t = (Token) o;
	return t.isNumber();
}
/**
 * Create a set with one random number (between 0 and 
 * 100).
 */
public Vector randomExpansion(int maxDepth, int depth) {
	double d = Math.floor(1000.0 * Math.random()) / 10;
	Vector v = new Vector();
	v.addElement(Double.toString(d));
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
	return "Num";
}
}
