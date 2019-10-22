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
package it.unimib.disco.lta.alfa.utils;

import static org.junit.Assert.*;

import org.junit.Test;


public class RegexUtilTest {

	@Test
	public void testRegexEscape(){
		String original;
		String escaped;
		String expected;
		
		original = "- ***++()[]{}";
		expected = "- \\*\\*\\*\\+\\+\\(\\)\\[\\]\\{\\}";
		escaped = RegexUtil.getEscapedString(original);
		
		assertEquals( expected, escaped );
	}
	
	@Test
	public void testRegexEscape_SingleChar_NotEscape(){
		String original;
		String escaped;
		String expected;
		
		original = "C";
		expected = "C";
		escaped = RegexUtil.getEscapedString(original);
		
		assertEquals( expected, escaped );
	}
	
	@Test
	public void testRegexEscape_MultipleChars_NotEscape(){
		String original;
		String escaped;
		String expected;
		
		original = "ABCD";
		expected = "ABCD";
		escaped = RegexUtil.getEscapedString(original);
		
		assertEquals( expected, escaped );
	}
}
