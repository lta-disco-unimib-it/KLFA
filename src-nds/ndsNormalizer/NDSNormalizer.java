package ndsNormalizer;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;



public class NDSNormalizer {
	private static final int recLevel = 30;
	//private static HandleRepository handle = new HandleRepository(recLevel);
	private static HandleRepositoryCycle handle = new HandleRepositoryCycle(recLevel);
	private static StreamRepository stream = new StreamRepository(recLevel);
	private static ConnRepository conn = new ConnRepository(recLevel);
	private static XConRepository xcon = new XConRepository(recLevel);
	
	private static String[] parameters = {
		"HANDLE",
		"REASON",
		"STREAM",
		"CONNID",
		"XCON",
		"PIPE",
		"CONTEXT_HANDLE"
	};
	
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		if ( args.length != 2 ){
			printUsage();
			System.exit(-1);
		}
		
		// TODO Auto-generated method stub
		File input = new File ( args[0] );
		File output = new File ( args[1] );
		ArrayList<LineElement> lines = new ArrayList<LineElement>();
		try {
			BufferedReader ir = new BufferedReader( new FileReader(input) );
			String line;
			while ( ( line = ir.readLine() ) != null ){
				//System.out.println(line);
				LineElement lineElement = processLine(line);
				
				lines.add(lineElement);
				nextLine();
			}
			ir.close();
			
			
			BufferedWriter ow = new BufferedWriter( new FileWriter(output) );
			
			for ( LineElement lineEl : lines ){
				String msg = lineEl.toString();
				ow.write(msg+"\n");
			}
			ow.close();
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private static void printUsage() {
		System.err.println("Usage : "+NDSNormalizer.class.getSimpleName()+" <inputTrace> <normalizedTrace>");
	}

	private static void nextLine() {
		stream.nextCycle();
		handle.nextCycle();
		conn.nextCycle();
		xcon.nextCycle();
	}

	private static LineElement processLine(String line) {
		int separatorIdx = line.indexOf("__");
		 
		
		if ( separatorIdx == -1 ){
			LineElement element = new GenericLineElement(line);
			return element;
		} 
		
		
		String name = line.substring(0, separatorIdx );
		
		LineElement element = new GenericLineElement( name );
		
		String parameters = line.substring( separatorIdx + 2 );
		
		int idx = process( parameters, element );	
		
		while(idx >0 && idx<parameters.length()){
			parameters = parameters.substring(idx);
			idx = process( parameters, element );
		}
		
		return element;
	}

	
	private static LineData getReasonElement(String value) {
		return new ReasonData(value);
	}

	private static StreamData getStreamElement(String value) {
		return stream.get(value);
	}

	private static HandleData getHandleElement(String value) {
		 return handle.get(value);
	}

	private static int process( String line, LineElement element ){
		for ( String parameter : parameters ){
			if ( ! line.startsWith(parameter))
				continue;
			
			if ( parameter.equals("PIPE") ){
				int end = line.indexOf( "_", parameter.length()+1 );
				end = line.indexOf( "_", end+1 );
				end = line.indexOf( "_", end+1 );
				LineData data;
				if ( end < 0 )
					data = new PipeElement( line );
				else
					data = new PipeElement( line.substring(0, end) );
				element.addData(data);
				return end+1;
			} else if (	parameter.equals("CONTEXT_HANDLE") ){
				element.addData(new ContextData() );
				return line.length();
			}
		
			int end = line.indexOf( "_", parameter.length()+1 );
			String value;
			if ( end >= 0 )
				value = line.substring(parameter.length()+1,end);
			else
				value = line.substring(parameter.length()+1);
			
		
			LineData data = null;
			
			if ( parameter.equals("HANDLE") )
				data = getHandleElement(value);
			else if ( parameter.equals("STREAM"))
				data = getStreamElement(value);
			else if ( parameter.equals("REASON"))
				data = getReasonElement(value);
			else if ( parameter.equals("CONNID"))
				data = getConnidElement(value);
			else if ( parameter.equals("XCON"))
				data = getXConElement(value);
			
			element.addData(data);
			
			return end+1;
		}
		System.out.println(line);
		element.addData(new GenericData(line));
		return line.length();
		
	}

	private static LineData getXConElement(String value) {
		return xcon.get(value);
	}

	private static LineData getConnidElement(String value) {
		return conn.get(value);
	}

}
