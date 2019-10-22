package util;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class BctFileLogger {

	private static BctFileLogger instance;
	private BufferedWriter wr;

	public BctFileLogger(String string) {
		try {
			wr = new BufferedWriter( new FileWriter(string) );
		} catch (IOException e) {
			
		}
	}

	public static synchronized BctFileLogger getInstance() {
		if ( instance == null ){
			instance = new BctFileLogger("/tmp/bct.log");
		}
		return instance;
	}
	
	public void log(String text){
		try {
			wr.write(text+"\n");
			wr.flush();
		} catch (IOException e) {
			
		}
	}
}
