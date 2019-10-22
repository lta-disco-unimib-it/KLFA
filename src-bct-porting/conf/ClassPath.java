/*
 *
 * This class stores values for the environmental classpaths
 * 
 */
package conf;

public class ClassPath {
	private static String xmlParserPath;
	private static String daikonPath;
	
	public static String getDaikonPath() {
		return daikonPath;
	}
	public static void setDaikonPath(String daikonPath) {
		ClassPath.daikonPath = daikonPath;
	}
	public static String getXmlParserPath() {
		return xmlParserPath;
	}
	public static void setXmlParserPath(String xmlParserPath) {
		ClassPath.xmlParserPath = xmlParserPath;
	}
}
