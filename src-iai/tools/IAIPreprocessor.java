package tools;

import java.io.File;

/**
 * This program preprocess the IAI log file.
 * It 
 * 	inserts void lines for missing messages
 *  
 * @author Fabrizio Pastore fabrizio.pastore AT gmail.com
 *
 */
public class IAIPreprocessor {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		if ( args.length < 2 ){
			printUsage();
			System.exit(-1);
		}
		
		File input = new File(args[0]);
		File output = new File(args[args.length-1]);
	}

	private static void printUsage() {
		System.out.println("java "+IAIPreprocessor.class.getName()+" <input>+ <output>");
	}

}
