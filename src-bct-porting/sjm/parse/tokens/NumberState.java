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
 * A NumberState object returns a number from a reader. This 
 * state's idea of a number allows an optional, initial 
 * minus sign, followed by one or more digits. A decimal 
 * point and another string of digits may follow these 
 * digits. 
 * 
 * @author Steven J. Metsker
 *
 * @version 1.0 
 */
public class NumberState extends TokenizerState {
	protected int c;
	protected double value;
	protected boolean absorbedLeadingMinus;
	protected boolean absorbedDot;
	protected boolean gotAdigit;
/*
 * Convert a stream of digits into a number, making this
 * number a fraction if the boolean parameter is true.
 */
protected double absorbDigits(
	PushbackReader r, boolean fraction) throws IOException {
		
	int divideBy = 1;
	double v = 0;
	while ('0' <= c && c <= '9') {
		gotAdigit = true;
		v = v * 10 + (c - '0');
		c = r.read();
		if (fraction) {
			divideBy *= 10;
		}
	}
	if (fraction) {
		v = v / divideBy;
	}
	return v;
}
/**
 * Return a number token from a reader.
 *
 * @return a number token from a reader
 */
public Token nextToken(
	PushbackReader r, int cin, Tokenizer t)
	throws IOException {
	 
	reset(cin);	
	parseLeft(r);
	parseRight(r);
	r.unread(c);
	return value(r, t);
}
/*
 * Parse up to a decimal point.
 */
protected void parseLeft(PushbackReader r)
	throws IOException {
	 
	if (c == '-') {
		c = r.read();
		absorbedLeadingMinus = true;
	}
	value = absorbDigits(r, false);	 
}
/*
 * Parse from a decimal point to the end of the number.
 */
protected void parseRight(PushbackReader r)
	throws IOException {
	 
	if (c == '.') {
		c = r.read();
		absorbedDot = true;
		value += absorbDigits(r, true);
	}
}
/*
 * Prepare to assemble a new number.
 */
protected void reset(int cin) {
	c = cin;
	value = 0;
	absorbedLeadingMinus = false;
	absorbedDot = false;
	gotAdigit = false;
}
/*
 * Put together the pieces of a number.
 */
protected Token value(PushbackReader r, Tokenizer t) 
	throws IOException {
		
	if (!gotAdigit) {
		if (absorbedLeadingMinus && absorbedDot) {
			r.unread('.');
			return t.symbolState().nextToken(r, '-', t);
			}
		if (absorbedLeadingMinus) {
			return t.symbolState().nextToken(r, '-', t);
		}
		if (absorbedDot) {
			return t.symbolState().nextToken(r, '.', t);
		}
	}
	if (absorbedLeadingMinus) {
		value = -value;
	}
	return new Token(Token.TT_NUMBER, "", value);
}
}
