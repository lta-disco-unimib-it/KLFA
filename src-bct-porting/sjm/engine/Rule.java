package sjm.engine;

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
 * A Rule represents a logic statement that a structure is true 
 * if a following series of other structures are true. 
 * <p> 
 * For example, 
 * <blockquote><pre>
 *     bachelor(X) :- male(X), unmarried(X);
 * </pre></blockquote>
 * <p> 
 * is a logical rule. 
 * <p> 
 * A rule can make a provable version of itself, a DynamicRule, 
 * that is a essentially a copy of the structures in the rule, 
 * with a new scope and with a program to consult for other 
 * rules. 
 *
 * @author Steven J. Metsker
 *
 * @version 1.0  
 */

public class Rule implements Axiom {
	protected Structure[] structures;
/**
 * Construct rule from the given structures.
 * 
 * @param Structure[] the structures that make up this rule.
 * 
 */
public Rule(Structure[] structures) {
	this.structures = structures;
}
/**
 * Construct a one-structure rule from the given structure.
 * 
 * @param Structure the structure that makes up this rule.
 * 
 */
public Rule(Structure s) {
	this(new Structure[] {s});
}
/**
 * Return a provable version of this rule.
 *
 * @return a provable version of this rule
 */
public DynamicAxiom dynamicAxiom(AxiomSource as) {
	return new DynamicRule(as, new Scope(), this);
}
/**
 * Returns true if the supplied object is an equivalent 
 * rule.
 *
 * @param   object   the object to compare
 *
 * @return   true, if the supplied object's structures equal
 *           this rule's structures
 */
public boolean equals(Object o) {
	if (!(o instanceof Rule))
		return false;
	Rule r = (Rule) o;
	if (!(structures.length == r.structures.length)) {
		return false;
	}
	for (int i = 0; i < structures.length; i++) {
		if (!(structures[i].equals(r.structures[i]))) {
			return false;
		}
	}
	return true;
}
/**
 * Return the first structure in this rule.
 *
 * @return the first structure in this rule
 */
public Structure head() {
	return structures[0];
}
/**
 * Returns a string representation of this rule. 
 *
 * @return a string representation of this rule.
 */
public String toString() {
	StringBuffer buf = new StringBuffer();
	for (int i = 0; i < structures.length; i++) {
		if (i == 1) {
			buf.append(" :- ");
		}
		if (i > 1) {
			buf.append(", ");
		}
		buf.append(structures[i].toString());
	}
	return buf.toString();
}
}
