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
