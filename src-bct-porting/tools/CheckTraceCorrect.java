package tools;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Stack;


import traceReaders.raw.FileInteractionTrace;
import traceReaders.raw.Token;
import traceReaders.raw.TraceException;

public class CheckTraceCorrect {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		FileInteractionTrace ft = new FileInteractionTrace("1",new File(args[0]),null);
		String prefix = "Logs For Thread ";
		String suffix = ".int";
		try {
			
			Token token = ft.getNextToken();
			String method = token.getMethodName();
			Stack<String> s = new Stack<String>();
			while (method != null) {
				
				String methodName = method.substring(0, method.length() - 1);
				//System.out.println("Method name : "+methodName+" "+method);
				
				if (method.endsWith("B")) {
					
					s.push(methodName);
				} else {
					String popped = s.pop();
					if ( ! popped.equals(methodName) ){
						System.err.println("Error, unexpected "+method+" expecting "+popped);
					}
				}
				token = ft.getNextToken();
				if ( token != null )
					method = token.getMethodName();
				else
					method = null;
			}
			if ( s.size() != 0 ){
				System.err.println("The structure of the input file is not valid.");
				System.err.println("Expected :");

				for ( int c = s.size()-1; c >= 0; --c )
					System.err.println(" "+s.get(c)+"E");

			} else {
				System.out.println("Result is ok");
			}
		} catch (TraceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
