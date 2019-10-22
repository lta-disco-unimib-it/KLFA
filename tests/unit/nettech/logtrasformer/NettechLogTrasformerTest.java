package logtrasformer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.nio.CharBuffer;
import java.util.ArrayList;
import java.util.Iterator;

import junit.framework.TestCase;
import logtransformers.NettechLogTrasformer.MessageIterator;

public class NettechLogTrasformerTest extends TestCase {
	private static String line0;
	private static String line1;
	private static String line2;
	private static String line3;
	private static String line4;
	private static String line5;
	
	public static class BufferedReaderStub extends BufferedReader {

		static String lines;
		
		
		
		static {
			line0 = "16:07:49,341 DEBUG test:123 -	match=test";
			line1 = "16:07:59,341 DEBUG test:123 -	match=try";
			line2 = "16:08:49,341 DEBUG test:123 -	read";
			line3 = "16:09:49,341 DEBUG test:123 -	match";
			line4 = "16:09:49,341 DEBUG test:123 -	match\n    oisyendj ( \n\tuehfj )";
			line5 = "16:09:49,341 DEBUG test:123 -	y";
			
			lines = line0+"\n" +
					line1+"\n" +
					line2+"\n" +
					line3+"\n" +
					line4+"\n" +
					line5+"\n";
		}
	

		public BufferedReaderStub() {
			super(new StringReader(lines));
		}

		
	}
	
	
	protected void setUp() throws Exception {
	}

	protected void tearDown() throws Exception {
	}

	public void testMessageIterator(){
		BufferedReaderStub reader = new BufferedReaderStub();
		MessageIterator it = new MessageIterator(reader);
		
		
		
		assertTrue(it.hasNext());
		assertEquals(line0,it.next());
		
		assertTrue(it.hasNext());
		assertEquals(line1,it.next());
		
		assertTrue(it.hasNext());
		assertTrue(it.hasNext());
		assertTrue(it.hasNext());
		assertEquals(line2,it.next());
		
		assertTrue(it.hasNext());
		assertEquals(line3,it.next());
		
		assertTrue(it.hasNext());
		assertEquals(line4,it.next());
		
		assertTrue(it.hasNext());
		assertEquals(line5,it.next());
		
		assertFalse(it.hasNext());
		
	}
}
