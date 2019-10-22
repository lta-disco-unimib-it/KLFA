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
 * This class abstracts the behavior common to parsers
 * that consist of a series of other parsers.
 * 
 * @author Steven J. Metsker
 * 
 * @version 1.0 
 */
public abstract class CollectionParser extends Parser {
	/**
	 * the parsers this parser is a collection of
	 */
	protected Vector subparsers = new Vector();
/**
 * Supports subclass constructors with no arguments.
 */
public CollectionParser() {
}
/**
 * Supports subclass constructors with a name argument
 *
 * @param   string   the name of this parser
 */
public CollectionParser(String name) {
	super(name);
}
/**
 * Adds a parser to the collection.
 *
 * @param   Parser   the parser to add
 *
 * @return   this
 */
public CollectionParser add (Parser e) {
	subparsers.addElement(e);
	return this;
}
/**
 * Return this parser's subparsers.
 *
 * @return   Vector   this parser's subparsers
 */
public Vector getSubparsers() {
	return subparsers;
}
/**
 * Helps to textually describe this CollectionParser.
 *
 * @returns   the string to place between parsers in 
 *            the collection
 */
protected abstract String toStringSeparator();
/*
 * Returns a textual description of this parser.
 */
protected String unvisitedString(Vector visited) {
	StringBuffer buf = new StringBuffer("<");
	boolean needSeparator = false;
	Enumeration e = subparsers.elements();
	while (e.hasMoreElements()) {
		if (needSeparator) {
			buf.append(toStringSeparator());
		}
		Parser next = (Parser) e.nextElement();
		buf.append(next.toString(visited));
		needSeparator = true;
	}
	buf.append(">");
	return buf.toString();
}
}
