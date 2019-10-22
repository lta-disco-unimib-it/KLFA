package it.unimib.disco.lta.alfa.tools;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import sun.misc.Regexp;

/**
 * This class match a regular expression against a file and return all the matching sequences.
 * 
 * 
 * 
 * @author Fabrizio Pastore fabrizio.pastore AT gmail.com
 *
 */
public class FileMatcher {
	
	public CharSequence fromFile(File file) throws IOException {
        FileInputStream fis = new FileInputStream(file);
        FileChannel fc = fis.getChannel();
    
        // Create a read-only CharBuffer on the file
        ByteBuffer bbuf = fc.map(FileChannel.MapMode.READ_ONLY, 0, (int)fc.size());
        CharBuffer cbuf = Charset.forName("8859_1").newDecoder().decode(bbuf);
        return cbuf;
    }

	public ArrayList<FileMatcherResult> find(String regexp, File input) throws IOException {
		Pattern pattern = Pattern.compile(regexp);
		CharSequence buf = fromFile(input);
		Matcher matcher = pattern.matcher(buf);
		ArrayList<FileMatcherResult> results = new ArrayList<FileMatcherResult>();
		
		Pattern nlpattern = Pattern.compile("\n");
		int start = 0;
		int startLine = 0;
		while (matcher.find()) {
            // Get the matching string
			int lastStart = start; 
            String match = matcher.group();
            start = matcher.start();
            
            for ( int i = lastStart; i < start; ++i )
            	if ( buf.charAt(i) == '\n' )
            		++startLine;
            
            lastStart = start;
            
            
            results.add( new FileMatcherResult(startLine,match) );
        }
		return results;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		if ( args.length < 2 ){
			printUsage();
			System.exit(-1);
		}
		
		String regexp = args[0];
		File input = new File(args[1]);
		if ( ! input.exists() ){
			System.out.println("File "+input.getAbsolutePath()+" does not exists!");
			System.exit(-1);
		}
		
		FileMatcher cf = new FileMatcher();
		try {
			ArrayList<FileMatcherResult> results = cf.find(regexp, input);
			for ( FileMatcherResult result : results ){
				System.out.println(result.getStartLine());
				System.out.println(result.getContent());
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private static void printUsage() {
		System.out.println("This program match a given regular expression against the ocntentx of a tet file and print all the matching sequences." +
				"\nUsage:" +
				"\n\t"+FileMatcher.class.getName()+" <regexp> <file>" +
						"\n\t<regexp> is teh regular expresion to match against the contents of a file;" +
						"\n\t<file> is the file to be matched.");
		
	}

}
