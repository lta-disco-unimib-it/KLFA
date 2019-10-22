package sjm.parse;

import java.util.Enumeration;
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
 * A <code>Repetition</code> matches its underlying parser 
 * repeatedly against a assembly.
 * 
 * @author Steven J. Metsker
 * 
 * @version 1.0 
 */

public class Repetition extends Parser {
	/*
	 * the parser this parser is a repetition of
	 */
	protected Parser subparser;

	/*
	 * the width of a random expansion
	 */
	protected static final int EXPWIDTH = 4;
	
	/*
	 * an assembler to apply at the beginning of a match
	 */
	protected Assembler preAssembler;
/**
 * Constructs a repetition of the given parser. 
 * 
 * @param   parser   the parser to repeat
 *
 * @return   a repetiton that will match the given 
 *           parser repeatedly in successive matches
 */
public Repetition (Parser p) {
	this(p, null);
}
/**
 * Constructs a repetition of the given parser with the
 * given name.
 * 
 * @param   Parser   the parser to repeat
 *
 * @param   String   a name to be known by
 *
 * @return   a repetiton that will match the given 
 *           parser repeatedly in successive matches
 */
public Repetition(Parser subparser, String name) {
	super(name);
	this.subparser = subparser;
}
/**
 * Accept a "visitor" and a collection of previously visited
 * parsers.
 *
 * @param   ParserVisitor   the visitor to accept
 *
 * @param   Vector   a collection of previously visited parsers
 */
public void accept(ParserVisitor pv, Vector visited) {
	pv.visitRepetition(this, visited);
}
/**
 * Return this parser's subparser.
 *
 * @return   Parser   this parser's subparser
 */
public Parser getSubparser() {
	return subparser;
}
/**
 * Given a set of assemblies, this method applies a preassembler
 * to all of them, matches its subparser repeatedly against each
 * of them, applies its post-assembler against each, and returns
 * a new set of the assemblies that result from the matches.
 * <p>
 * For example, matching the regular expression <code>a*
 * </code> against <code>{^aaab}</code> results in <code>
 * {^aaab, a^aab, aa^ab, aaa^b}</code>.
 *
 * @return   a Vector of assemblies that result from 
 *           matching against a beginning set of assemblies
 *
 * @param   Vector   a vector of assemblies to match against
 *
 */
public Vector match(Vector in) {
	if (preAssembler != null) {
		Enumeration e = in.elements();
		while (e.hasMoreElements()) {
			preAssembler.workOn((Assembly) e.nextElement());
		}
	}
	Vector out = elementClone(in);
	Vector s = in; // a working state
	while (!s.isEmpty()) {
		s = subparser.matchAndAssemble(s);
		add(out, s);
	}
	return out;
}
/**
 * Create a collection of random elements that correspond to
 * this repetition.
 */
protected Vector randomExpansion(int maxDepth, int depth) {
	Vector v = new Vector();
	if (depth >= maxDepth) {
		return v;
	}

	int n = (int) (EXPWIDTH * Math.random());
	for (int j = 0; j < n; j++) {
		Vector w = subparser.randomExpansion(maxDepth, depth++);
		Enumeration e = w.elements();
		while (e.hasMoreElements()) {
			v.addElement(e.nextElement());
		}
	}
	return v;
}
/**
 * Sets the object that will work on every assembly before 
 * matching against it.
 *
 * @param   Assembler   the assembler to apply
 *
 * @return   Parser   this
 */
public Parser setPreAssembler(Assembler preAssembler) {
	this.preAssembler = preAssembler;
	return this;
}
/*
 * Returns a textual description of this parser.
 */
 protected String unvisitedString(Vector visited) {
	return subparser.toString(visited) + "*";
}
}
