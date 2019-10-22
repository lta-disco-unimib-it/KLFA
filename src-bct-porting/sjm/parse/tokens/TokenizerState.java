package sjm.parse.tokens;

import java.io.IOException;
import java.io.PushbackReader;
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
 * A tokenizerState returns a token, given a reader, an 
 * initial character read from the reader, and a tokenizer 
 * that is conducting an overall tokenization of the reader. 
 * The tokenizer will typically have a character state table 
 * that decides which state to use, depending on an initial 
 * character. If a single character is insufficient, a state 
 * such as <code>SlashState</code> will read a second 
 * character, and may delegate to another state, such as 
 * <code>SlashStarState</code>. This prospect of delegation is 
 * the reason that the <code>nextToken()</code> method has a 
 * tokenizer argument. 
 * 
 * @author Steven J. Metsker
 *
 * @version 1.0 
 */
public abstract class TokenizerState {
/**
 * Return a token that represents a logical piece of a reader.
 * 
 * @return  a token that represents a logical piece of the 
 *          reader
 *
 * @param   PushbackReader   a reader to read from
 *
 * @param   c   the character that a tokenizer used to 
 *              determine to use this state
 *
 * @param   Tokenizer   the tokenizer conducting the overall
 *                      tokenization of the reader
 *
 * @exception   IOException   if there is any problem reading
 */
public abstract Token nextToken(
	PushbackReader r, int c, Tokenizer t)
	throws IOException;
}
