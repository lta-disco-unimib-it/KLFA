package logtransformers;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.NoSuchElementException;


import sun.reflect.generics.reflectiveObjects.NotImplementedException;

/**
 * This class takes a raw nettechnologies log file and transform it into a csv log file readable by klfa.
 * 
 * @author Fabrizio Pastore fabrizio.pastore AT gmail.com
 *
 */
public class NettechLogTrasformer {
	private static final String separator = "\t";
	
	public static class MessageIterator implements Iterator<String>{
		private BufferedReader bufferReader;
		private String previousLine;
		private String nextLine;
		private final static String logLineStart = "\\d\\d:\\d\\d:\\d\\d,\\d\\d\\d.*";
		
		
		public MessageIterator(BufferedReader br){
			this.bufferReader = br;
		}
		
		/**
		 * load the next line to the nextLine field.
		 * It leaves nextLine empty if the end of file was already reached
		 * 
		 * @throws IOException
		 */
		private void loadnext() throws IOException{
			String line;
			
			while(  nextLine == null && ( line = bufferReader.readLine() ) != null ){
				//System.out.println("read "+line);
				if ( previousLine == null ){	//first line of the buffer
					previousLine = line;
				} else {
					if ( line.matches(logLineStart) ){
						nextLine = previousLine;
						previousLine = line;
					} else {
						previousLine += "\n"+line;
					}
				}
			}
			
			if ( nextLine == null ){
				nextLine = previousLine;
				previousLine = null;
			}
			
		}
		
		/**
		 * Load the next element if the previous one has been read (i.e. if nextLine is null).
		 * @throws IOException 
		 * 
		 */
		private void safeLoadNext() throws IOException{
			if(nextLine==null){
				loadnext();
			}
		}

		public boolean hasNext() {
			
			try {
				safeLoadNext();
			} catch (IOException e) {
				return false;
			}
			
			if ( nextLine == null ){
				return false;
			} 
			return true;
		}

		public String next() {
			try {
				safeLoadNext();
				if ( nextLine == null ){
					throw new NoSuchElementException();
				}
				
				String result = nextLine;
				nextLine = null;
				return result;
				
			} catch (IOException e) {
				throw new NoSuchElementException();
			}
		}

		public void remove() {
			throw new UnsupportedOperationException();
		}

	}
	
	
	public static void main(String[] args){
		NettechLogTrasformer trasformer = new NettechLogTrasformer();
		
		File src = new File(args[0]);
		File dst = new File(args[1]);
		
		try {
			trasformer.transform(src,dst);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	

	private void transform(File src, File dst) throws IOException {
		BufferedReader fr = new BufferedReader(new FileReader(src));
		
		FileWriter wr = new FileWriter(dst);
		
		MessageIterator lineParser = new MessageIterator(fr);
		
		while ( lineParser.hasNext() ){
			String logLine = processLogLine( lineParser.next() );
//			if( logLine.indexOf("\t") == logLine.lastIndexOf("\t") ){
//				logLine += "\tNOACTION";
//			}
			wr.write(logLine);
			wr.write("\n");
		}
		
		wr.close();
		fr.close();
	}

	private String processLogLine(String line) {
		//System.out.println("Processing line "+line);
		String[] fragments = line.replace("\n","\\n").split("\\s");
	
		String component = fragments[2].split(":")[0];
		
		
		
		if ( component.equals("JspServlet") ){
			return processJspServlet(component, fragments);
		} else if ( component.equals("INFO") ){
			return processINFO(component, fragments);
		} else if ( component.equals("[/secureadmin]") ){
			return processSecureAdmin(component, fragments);
		} else if ( component.equals("JspRuntimeContext") ){
			return processJspRuntimeContext(component, fragments);
		} else {
			return processGeneric(component, fragments);
		}
		
		
	}



	private String processSecureAdmin(String component, String[] fragments) {
		return processGeneric("SecureAdmin", fragments);
	}



	private String processJspRuntimeContext(String component, String[] fragments) {
		StringBuffer buf = new StringBuffer();
		buf.append(component);
		boolean elementStart = true;
		
		ArrayList<String> allFragments = new ArrayList<String>();
		
		for ( int i = 4; i < fragments.length; ++i){
			if ( i == 4 ){
				buf.append(separator);
			} else {
				buf.append("_");
			}
			
			buf.append(fragments[i]);
			
			
		}
//		
//		for ( String fragment : allFragments){
//			if ( fragment.length() == 0 ){
//				
//			} else if (fragment.equals(":"){
//				buf.append(separator);
//				elementStart=true;
//			} else {
//				if ( ! elementStart )
//					buf.append("_");
//				buf.append(fragment);
//			}
//			
//		}
		
		return buf.toString();
	}



	private String processGeneric(String component, String[] fragments) {

		StringBuffer buf = new StringBuffer();
		buf.append(component);
		
		for ( int i = 4; i < fragments.length; ++i){
			
			if ( i == 4 ){
				buf.append(separator);
			}
			String fragment = fragments[i];
			
			if (fragment.equals("for") && i>6 && fragments[i-2].equals("Fire")){
				buf.append(separator);
			}
			
			
			
			//the char ' indicate the start of a text parameter
			
			else if (fragment.equals("'")){
				buf.append(separator);
			} else { 
				boolean textPar = false;
				if ( fragment.startsWith("'") ) {
			
					buf.append(separator);
					buf.append(fragment.substring(1));
					textPar = true;
				}
				
				if ( fragment.endsWith("'") && ! fragment.endsWith("\'")) {
					buf.append(fragment.substring(0,fragment.length()-1));
					buf.append(separator);
					textPar = true;
				}
			
				if ( ! textPar ){
					if( fragments[i-1].length()>0)
						buf.append("_");

					buf.append(fragment);
				}
			}
		}
		
		return buf.toString();
	}

	private String processINFO(String component, String[] fragments) {
		StringBuffer buf = new StringBuffer();
		buf.append(component);
		boolean elementStart = true;
		
		for ( int i = 4; i < fragments.length; ++i){
			String fragment = fragments[i];
			
			if ( i == 4 ){
				buf.append(separator);
			}
			
			
			if ( fragment.length() == 0 ){
				
			} else if (fragment.equals(":")
					|| fragment.equals("-->")) {
				buf.append(separator);
				elementStart=true;
			} else {
				if ( ! elementStart )
					buf.append("_");
				
				buf.append(fragment.replaceFirst(":", separator));
			}
			
		}
		
		return buf.toString();
	}


	private String processJspServlet(String component, String[] fragments) {
		StringBuffer buf = new StringBuffer();
		buf.append(component);
		boolean elementStart = true;
		
		for ( int i = 4; i < fragments.length; ++i){
			if ( i == 4 ){
				buf.append(separator);
			}
			String fragment = fragments[i];
			
			if ( fragment.length() == 0 ){
				
			} else if (fragment.equals(":")
					|| fragment.equals("-->")) {
				buf.append(separator);
				elementStart=true;
			} else {
				if ( ! elementStart )
					buf.append("_");


				buf.append(fragment);
				
				if( fragment.endsWith(":") ){
					buf.append(separator);
				} 
			}
			
		}
		
		return buf.toString();
	}
}
