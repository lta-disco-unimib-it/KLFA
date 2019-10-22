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
 * This class provides a "visitor" hierarchy in support of
 * the Visitor pattern -- see the book, "Design Patterns" for
 * an explanation of this pattern.
 * 
 * @author Steven J. Metsker
 * 
 * @version 1.0 
 */
public abstract class ParserVisitor {
/**
 * Visit an alternation.
 *
 * @param   Alternation   the parser to visit
 *
 * @param   Vector   a collection of previously visited parsers
 *
 */
public abstract void visitAlternation(
	Alternation a, Vector visited);
/**
 * Visit an empty parser.
 *
 * @param   Empty   the parser to visit
 *
 * @param   Vector   a collection of previously visited parsers
 *
 */
public abstract void visitEmpty(Empty e, Vector visited);
/**
 * Visit a repetition.
 *
 * @param   Repetition   the parser to visit
 *
 * @param   Vector   a collection of previously visited parsers
 *
 */
public abstract void visitRepetition(
	Repetition r, Vector visited);
/**
 * Visit a sequence.
 *
 * @param   Sequence   the parser to visit
 *
 * @param   Vector   a collection of previously visited parsers
 *
 */
public abstract void visitSequence(Sequence s, Vector visited);
/**
 * Visit a terminal.
 *
 * @param   Terminal   the parser to visit
 *
 * @param   Vector   a collection of previously visited parsers
 *
 */
public abstract void visitTerminal(Terminal t, Vector visited);
}
