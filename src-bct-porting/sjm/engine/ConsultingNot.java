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
 * A ConsultingNot is a Not that has an axiom source to
 * consult.
 * 
 * @author Steven J. Metsker
 *
 * @version 1.0 
 */

public class ConsultingNot extends Gateway {
	ConsultingStructure consultingStructure;
/*
 * Contructs a ConsultingNot from the specified consulting
 * structure. This constructor is for use by Not.
 */
protected ConsultingNot(
	ConsultingStructure consultingStructure) {
		
	super(consultingStructure.functor, consultingStructure.terms);
	this.consultingStructure = consultingStructure;
}
/**
 * Returns <code>false</code> if there is any way to prove this
 * structure.
 *
 * @return <code>false</code> if there is any way to prove 
 *         this structure
 */
public boolean canProveOnce() {
	return !(consultingStructure.canUnify() && 
		     consultingStructure.resolvent.canEstablish());
}
/**
 * After succeeding once, unbind any variables bound during
 * the successful proof, and set the axioms to begin
 * again at the beginning.
 */
protected void cleanup() {
	consultingStructure.unbind();
	consultingStructure.axioms = null;
}
/**
 * Returns a string description of this Not.
 *
 * @return a string description of this Not
 */
public String toString() {
	return "not " + consultingStructure;
}
}
