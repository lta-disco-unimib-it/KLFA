package sjm.parse.chars;

import java.util.Vector;

import sjm.parse.Parser;
import sjm.parse.Terminal;
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
 * A SpecificChar matches a specified character from a character 
 * assembly.
 * 
 * @author Steven J. Metsker
 * 
 * @version 1.0 
 */
public class SpecificChar extends Terminal {
	
/**
 * the character to match
 */
	protected Character character;

/**
 * Constructs a SpecificChar to match the specified char.
 *
 * @param   char  the character to match
 *
 * @return   a SpecificChar to match a Character constructed
 *           from the specified char.
 */
public SpecificChar(char c) {
	this(new Character(c));
}
/**
 * Constructs a SpecificChar to match the specified character.
 *
 * @param   character   the character to match
 *
 * @return   a SpecificChar to match the specified character
 */
public SpecificChar(Character character) {
	this.character = character;
}
/**
 * Returns true if an assembly's next element is equal to the 
 * character this object was constructed with.
 *
 * @param   object   an element from an assembly
 *
 * @return   true, if an assembly's next element is equal to 
 *           the character this object was constructed with
 */
public boolean qualifies(Object o) {
	Character c = (Character) o;
	return c.charValue() == character.charValue();
}
/**
 * Returns a textual description of this parser.
 *
 * @param   vector   a list of parsers already printed in
 *                   this description
 * 
 * @return   string   a textual description of this parser
 *
 * @see Parser#toString()
 */
public String unvisitedString(Vector visited) {
	return character.toString();
}
}
