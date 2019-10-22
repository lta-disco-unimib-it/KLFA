package sjm.parse;

import java.util.Vector;
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
 * An <code>Empty</code> parser matches any assembly once, 
 * and applies its assembler that one time.
 * <p>
 * Language elements often contain empty parts. For example, 
 * a language may at some point allow a list of parameters
 * in parentheses, and may allow an empty list. An empty
 * parser makes it easy to match, within the 
 * parenthesis, either a list of parameters or "empty".
 * 
 * @author Steven J. Metsker
 * 
 * @version 1.0 
 * 
 */
public class Empty extends Parser {
/**
 * Accept a "visitor" and a collection of previously visited
 * parsers.
 *
 * @param   ParserVisitor   the visitor to accept
 *
 * @param   Vector   a collection of previously visited parsers
 */
public void accept(ParserVisitor pv, Vector visited) {
	pv.visitEmpty(this, visited);
}
/**
 * Given a set of assemblies, this method returns the set as
 * a successful match.
 * 
 * @return   the input set of states
 *
 * @param   Vector   a vector of assemblies to match against
 *
 */
public Vector match(Vector in) {
	return elementClone(in);
}
/*
 * There really is no way to expand an empty parser, so
 * return an empty vector.
 */
protected Vector randomExpansion(int maxDepth, int depth) {
	return new Vector();
}
/*
 * Returns a textual description of this parser.
 */
 protected String unvisitedString(Vector visited) {
	return " empty ";
}
}
