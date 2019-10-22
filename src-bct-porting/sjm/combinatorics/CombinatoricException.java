package sjm.combinatorics;

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
 * Signals that a requested combinatoric quantity or 
 * enumeration is undefined.
 * 
 * @author Steven J. Metsker
 * 
 * @version 1.0
 */
public class CombinatoricException extends Exception {


/**
 * Constructs a <code>CombinatoricException</code> with no 
 * detail message.
 *
 */
public CombinatoricException() {
	super();
}
/**
 * Constructs a <code>CombinatoricException</code> with the 
 * specified detail message. 
 *
 * @param   s   the detail message.
 */
public CombinatoricException(String s) {
	super(s);
}
}
