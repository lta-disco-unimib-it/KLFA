package it.unimib.disco.lta.alfa.rawLogsPreprocessing.java;

import it.unimib.disco.lta.alfa.eventsDetection.java.JavaLogTransformer;
import it.unimib.disco.lta.alfa.rawLogsPreprocessing.java.JbossLogTransformer;

import java.io.File;
import java.io.IOException;




/**
 * This log transformer interpret a java log file and put on one line the content present in two lines
 * 
 * @author Fabrizio Pastore fabrizio.pastore AT gmail.com
 *
 */
public class OneLineLogPreprocessor extends JavaLogTransformer {

	OneLineLogPreprocessor(File src, File dst) {
		super(src, dst);
	}

	protected String processLine(String line) {
		return line;
	}
	
	

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		File src= new File(args[0]);
		File dst= new File(args[1]);
		
		OneLineLogPreprocessor trans = new OneLineLogPreprocessor(src,dst);
		trans.setSeparator(" ");
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
