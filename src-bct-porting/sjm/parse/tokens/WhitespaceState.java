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
 * A whitespace state ignores whitespace (such as blanks
 * and tabs), and returns the tokenizer's next token. By 
 * default, all characters from 0 to 32 are whitespace.
 * 
 * @author Steven J. Metsker
 *
 * @version 1.0 
 */
public class WhitespaceState extends TokenizerState {
	protected boolean whitespaceChar[] = new boolean[256];
/**
 * Constructs a whitespace state with a default idea of what
 * characters are, in fact, whitespace.
 *
 * @return   a state for ignoring whitespace
 */
public WhitespaceState() {
	setWhitespaceChars(0, ' ', true);
}
/**
 * Ignore whitespace (such as blanks and tabs), and return 
 * the tokenizer's next token.
 *
 * @return the tokenizer's next token
 */
public Token nextToken(
	PushbackReader r, int aWhitespaceChar, Tokenizer t) 
	throws IOException {
		
	int c;
	do {
		c = r.read();
	} while (
		c >= 0 && 
		c < whitespaceChar.length && 
		whitespaceChar[c]);
	
	if (c >= 0) {
		r.unread(c);
	}
	return t.nextToken();
}
/**
 * Establish the given characters as whitespace to ignore.
 *
 * @param   first   char
 *
 * @param   second   char
 *
 * @param   boolean   true, if this state should ignore
 *                    characters in the given range
 */
public void setWhitespaceChars(int from, int to, boolean b) {
	for (int i = from; i <= to; i++) {
		if (i >= 0 && i < whitespaceChar.length) {
			whitespaceChar[i] = b;
		}
	}
}
}
