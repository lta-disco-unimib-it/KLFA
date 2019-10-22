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
 * A Symbol matches a specific sequence, such as 
 * <code><</code>, or <code><=</code> that a tokenizer
 * returns as a symbol. 
 * 
 * @author Steven J. Metsker
 * 
 * @version 1.0 
 */

public class Symbol extends Terminal {
/**
 * the literal to match
 */
	protected Token symbol; 

/**
 * Constructs a symbol that will match the specified char.
 *
 * @param   char   the character to match. The char must be 
 *                 one that the tokenizer will return as a 
 *                 symbol token. This typically includes most 
 *                 characters except letters and digits. 
 *
 * @return   a symbol that will match the specified char
 */
public Symbol(char c) {
	this(String.valueOf(c));
}
/**
 * Constructs a symbol that will match the specified sequence
 * of characters.
 *
 * @param   String   the characters to match. The characters
 *                   must be a sequence that the tokenizer will 
 *                   return as a symbol token, such as
 *                   <code><=</code>.
 *
 * @return   a Symbol that will match the specified sequence
 *           of characters
 */
public Symbol(String s) {
	symbol = new Token(Token.TT_SYMBOL, s, 0);
}
/**
 * Returns true if the symbol this object represents equals an
 * assembly's next element.
 *
 * @param   object   an element from an assembly
 *
 * @return   true, if the specified symbol equals the next 
 *           token from an assembly
 */
protected boolean qualifies(Object o) {
	return symbol.equals((Token) o);
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
	return symbol.toString();
}
}
