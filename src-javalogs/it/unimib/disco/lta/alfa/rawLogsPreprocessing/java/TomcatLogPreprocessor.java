package it.unimib.disco.lta.alfa.rawLogsPreprocessing.java;

import it.unimib.disco.lta.alfa.eventsDetection.java.JavaLogTransformer;
import it.unimib.disco.lta.alfa.rawLogsPreprocessing.java.JbossLogTransformer;

import java.io.File;
import java.io.IOException;




public class TomcatLogPreprocessor extends JavaLogTransformer {

	TomcatLogPreprocessor(File src, File dst) {
		super(src, dst);
	}

	protected String processLine(String line) {
		line = line.replace("\t", "\\t");
		
		line=processLine(line,curComponent,curEvent);
		
		return line;
	}
	
	protected static String processLine(String line, String component, String event){
		if ( component.equals("org.apache.catalina.loader.WebappClassLoader") ) {
			if ( event.equals("loadClass") ) {
				line = getContentBetweenParenthesis(line);
			} else if (event.equals("findClass")){
				if ( line.contains("findClass(") || line.contains("findClassInternal(") ){
					line = getContentBetweenParenthesis(line);
				}
			}
		}
	
		return line;
	}
	
	private static String getContentBetweenParenthesis(String line) {
		return line.substring(line.indexOf("(") + 1,line.length()-1 ).trim();
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		File src= new File(args[0]);
		File dst= new File(args[1]);
		
		TomcatLogPreprocessor trans = new TomcatLogPreprocessor(src,dst);
		try {
			trans.transform();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	@Override
	protected String getActionName(String line) {
		// TODO Auto-generated method stub
		return null;
	}
	
}
