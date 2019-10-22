package sjm.parse.tokens;

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
 * Objects of this class represent a type of token, such
 * as "number" or "word".
 * 
 * @author Steven J. Metsker
 *
 * @version 1.0 
 */
 public class TokenType {
	 protected String name;
/**
 * Creates a token type of the given name.
 */
public TokenType(String name) {
	this.name = name;
}
}
