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
 * Parsers that have an Assembler ask it to work on an
 * assembly after a successful match.
 * <p>
 * By default, terminals push their matches on a assembly's 
 * stack after a successful match. 
 * <p>
 * Parsers recognize text, and assemblers provide any 
 * sort of work that should occur after this recognition. 
 * This work usually has to do with the state of the assembly,
 * which is why assemblies have a stack and a target. 
 * Essentially, parsers trade advancement on a assembly 
 * for work on the assembly's stack or target.
 * 
 * @author Steven J. Metsker
 * 
 * @version 1.0 
 */
public abstract class Assembler {
/**
 * Returns a vector of the elements on an assembly's stack 
 * that appear before a specified fence.
 * <p>
 * Sometimes a parser will recognize a list from within 
 * a pair of parentheses or brackets. The parser can mark 
 * the beginning of the list with a fence, and then retrieve 
 * all the items that come after the fence with this method.
 *
 * @param   assembly   a assembly whose stack should contain 
 * some number of items above a fence marker
 * 
 * @param   object   the fence, a marker of where to stop 
 *                   popping the stack
 * 
 * @return   Vector   the elements above the specified fence
 * 
 */
public static Vector elementsAbove(Assembly a, Object fence) {
	Vector items = new Vector();
	 
	while (!a.stackIsEmpty()) {
		Object top = a.pop();
		if (top.equals(fence)) {
			break;
		}
		items.addElement(top);
	}
	return items;
}
/**
 * This is the one method all subclasses must implement. It 
 * specifies what to do when a parser successfully 
 * matches against a assembly.
 *
 * @param   Assembly   the assembly to work on
 */
public abstract void workOn(Assembly a);
}
