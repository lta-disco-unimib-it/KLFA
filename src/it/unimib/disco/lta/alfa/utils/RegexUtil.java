package it.unimib.disco.lta.alfa.utils;

public class RegexUtil {

	public static String getEscapedString ( String string ){
		return string.replaceAll("\\*", "\\\\*")
		.replaceAll("\\+", "\\\\+")
		.replaceAll("\\.", "\\\\.")
		.replaceAll("\\(", "\\\\(")
		.replaceAll("\\)", "\\\\)")
		.replaceAll("\\[", "\\\\[")
		.replaceAll("\\]", "\\\\]")
		.replaceAll("\\{", "\\\\{")
		.replaceAll("\\}", "\\\\}")
		;
	}
	

}
