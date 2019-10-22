package sjm.parse.tokens;

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
 * A CaselessLiteral matches a specified String from an
 * assembly, disregarding case.
 * 
 * @author Steven J. Metsker
 * 
 * @version 1.0 
 */
public class CaselessLiteral extends Literal {
/**
 * Constructs a literal that will match the specified string,
 * given mellowness about case.
 *
 * @param   string   the string to match as a token
 *
 * @return   a literal that will match the specified string,
 *           disregarding case
 */
public CaselessLiteral(String literal) {
	super(literal);
}
/**
 * Returns true if the literal this object equals an
 * assembly's next element, disregarding case.
 *
 * @param   object   an element from an assembly
 *
 * @return   true, if the specified literal equals the next 
 *           token from an assembly, disregarding case
 */
protected boolean qualifies(Object o) {
	return literal.equalsIgnoreCase((Token) o);
}
}
