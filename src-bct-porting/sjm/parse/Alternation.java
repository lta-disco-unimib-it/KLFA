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
 * An <code>Alternation</code> object is a collection of 
 * parsers, any one of which can successfully match against
 * an assembly.
 * 
 * @author Steven J. Metsker
 * 
 * @version 1.0 
 */

public class Alternation extends CollectionParser {

/**
 * Constructs a nameless alternation.
 */
public Alternation() {
}
/**
 * Constructs an alternation with the given name.
 *
 * @param    name    a name to be known by
 */
public Alternation(String name) {
	super(name);
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
	pv.visitAlternation(this, visited);
}
/**
 * Given a set of assemblies, this method matches this 
 * alternation against all of them, and returns a new set
 * of the assemblies that result from the matches.
 *
 * @return   a Vector of assemblies that result from 
 *           matching against a beginning set of assemblies
 *
 * @param   Vector   a vector of assemblies to match against
 *
 */
public Vector match(Vector in) {
	Vector out = new Vector();
	Enumeration e = subparsers.elements();
	while (e.hasMoreElements()) {
		Parser p = (Parser) e.nextElement();
		add(out, p.matchAndAssemble(in));
	}
	return out;
}
/*
 * Create a random collection of elements that correspond to
 * this alternation.
 */
protected Vector randomExpansion(int maxDepth, int depth) {
	if (depth >= maxDepth) {
		return randomSettle(maxDepth, depth);
	}
	double n = (double) subparsers.size();
	int i = (int) (n * Math.random());
	Parser j = (Parser) subparsers.elementAt(i);
	return j.randomExpansion(maxDepth, depth++);
}
/*
 * This method is similar to randomExpansion, but it will
 * pick a terminal if one is available.
 */
protected Vector randomSettle(int maxDepth, int depth) {
	
	// which alternatives are terminals?

	Vector terms = new Vector();
	Enumeration e = subparsers.elements();
	while (e.hasMoreElements()) {
		Parser j = (Parser) e.nextElement();
		if (j instanceof Terminal) {
			terms.addElement(j);
		}
	}

	// pick one of the terminals or, if there are no
	// terminals, pick any subparser

	Vector which = terms;
	if (terms.isEmpty()) {
		which = subparsers;
	}
	
	double n = (double) which.size();
	int i = (int) (n * Math.random());
	Parser p = (Parser) which.elementAt(i);
	return p.randomExpansion(maxDepth, depth++);
}
/*
 * Returns the string to show between the parsers this
 * parser is an alternation of.
 */
protected String toStringSeparator() {
	return "|";
}
}
