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
 * This state will either delegate to a comment-handling 
 * state, or return a token with just a slash in it.
 * 
 * @author Steven J. Metsker
 *
 * @version 1.0 
 */
public class SlashState extends TokenizerState {
	
	protected SlashStarState slashStarState = 
		new SlashStarState();
		
	protected SlashSlashState slashSlashState = 
		new SlashSlashState();
/**
 * Either delegate to a comment-handling state, or return a 
 * token with just a slash in it.
 *
 * @return   either just a slash token, or the results of 
 *           delegating to a comment-handling state
 */
public Token nextToken(
	PushbackReader r, int theSlash, Tokenizer t)
	throws IOException {
		
	int c = r.read();
	if (c == '*') {
		return slashStarState.nextToken(r, '*', t);
	}
	if (c == '/') {
		return slashSlashState.nextToken(r, '/', t);
	}
	if (c >= 0) {
		r.unread(c);
	}
	return new Token(Token.TT_SYMBOL, "/", 0);
}
}
