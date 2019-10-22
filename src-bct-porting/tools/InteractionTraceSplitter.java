package tools;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Stack;

import traceReaders.raw.FileInteractionTrace;
import traceReaders.raw.Token;
import traceReaders.raw.TraceException;

public class InteractionTraceSplitter {
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		FileInteractionTrace ft = new FileInteractionTrace("1",new File(args[0]),null);
		String prefix = "Logs For Thread ";
		String suffix = ".int";
		int traceCounter = 1;
		try {
			FileWriter fw = new FileWriter(prefix+traceCounter+suffix);
			Token token = ft.getNextToken();
			String method = token.getMethodName();
			Stack<String> s = new Stack<String>();
			int counter=0;
			while (method != null) {
				fw.write(method);
				fw.write("#");
				
				if (method.endsWith("B")) {
					s.push(method.substring(0,method.length()-1));
					++counter;
				} else {
					
					String oldM = s.pop();
					String newM = method.substring(0,method.length()-1);
					
					if (!oldM.equals(newM)){
						System.err.println("Malformed trace: found "+newM+" expecting "+oldM);
					}
					
					--counter;
					if ( counter == 0 ){
						fw.close();
						++traceCounter;
						fw = new FileWriter(prefix+traceCounter+suffix);
					}
				}
				token = ft.getNextToken();
				if ( token != null )
					method = token.getMethodName();
				else
					method = null;
			}
			
			if ( counter != 0 ){
				System.err.println("The structure of the input file is not valid.");

			} else {
				System.out.println("Result is ok");
			}
			fw.close();
		} catch (TraceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
