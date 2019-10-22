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
 * A slashSlash state ignores everything up to an end-of-line
 * and returns the tokenizer's next token.
 * 
 * @author Steven J. Metsker
 *
 * @version 1.0 
 */
public class SlashSlashState extends TokenizerState {
/**
 * Ignore everything up to an end-of-line and return the 
 * tokenizer's next token.
 *
 * @return the tokenizer's next token
 */
public Token nextToken(
	PushbackReader r, int theSlash, Tokenizer t)
	throws IOException {
		
	int c;
	while ((c = r.read()) != '\n' && c != '\r' && c >= 0) {
	}
	return t.nextToken();
}
}
