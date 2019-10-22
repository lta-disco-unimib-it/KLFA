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
 * A Literal matches a specific String from an assembly.
 * 
 * @author Steven J. Metsker
 * 
 * @version 1.0 
 */

public class Literal extends Terminal {
/**
 * the literal to match
 */
	protected Token literal; 

/**
 * Constructs a literal that will match the specified string.
 *
 * @param   string   the string to match as a token
 *
 * @return   a literal that will match the specified string
 */
public Literal(String s) {
	literal = new Token(s);
}
/**
 * Returns true if the literal this object equals an
 * assembly's next element.
 *
 * @param   object   an element from an assembly
 *
 * @return   true, if the specified literal equals the next 
 *           token from an assembly
 */
protected boolean qualifies(Object o) {
	return literal.equals((Token) o);
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
	return literal.toString();
}
}
