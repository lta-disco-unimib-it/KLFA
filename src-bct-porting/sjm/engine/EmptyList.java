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
 * The EmptyList is a list with no terms.
 * <p>
 * All lists except this one contain a head, which may be any
 * term, and a tail, which is another list. This recursive 
 * defintion terminates with this singleton, the empty list.
 * 
 * @author Steven J. Metsker
 *
 * @version 1.0 
 */
public class EmptyList extends Fact {
/**
 * Constructs the empty list singleton.
 */
protected EmptyList() {
	super(".");
}
/**
 * Return true, since an empty list is a list.
 *
 * @return true 
 */
public boolean isList() {
	return true;
}
/**
 * Returns a string representation of this list as a part of 
 * another list. When the empty list represents itself as part
 * of another list, it just returns "".
 *
 * @return an empty string
 */
public String listTailString() {
	return "";
}
/**
 * Returns a string representation of the empty list.
 *
 * @return   a string representation of the empty list
 */
public String toString() {
	return "[]";
}
}
