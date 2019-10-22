/*
 * Created on 31-lug-2004
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package grammarInference.Record;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;

import conf.InteractionInferenceEngineSettings;

/**
 * @author Leonardo Mariani
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class AbbadingoParser implements TraceParser {
	BufferedReader br = null;

	public AbbadingoParser(String fileName) throws FileNotFoundException {
			br= new BufferedReader(new FileReader(fileName));
	}

	

	/* (non-Javadoc)
	 * @see grammarInference.Record.TraceParser#getTraceIterator()
	 */
	public Iterator getTraceIterator() {
			return new TraceIterator(br);
	}


	class TraceIterator implements Iterator {
		private int nTrace;
		private int nSymbols;
		private int currentTrace=0;

		public TraceIterator(BufferedReader br) {
				String line1=null;
				try {
					line1 = br.readLine();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				int spacePos = line1.indexOf(" ");
			InteractionInferenceEngineSettings.logger.logEvent("" + spacePos);
				nTrace = Integer.parseInt(line1.substring(0,spacePos));
				nSymbols = Integer.parseInt(line1.substring(spacePos+1,line1.length()));
		}
		
		/* (non-Javadoc)
		 * @see java.util.Iterator#hasNext()
		 */
		public boolean hasNext() {
			// TODO Auto-generated method stub
			if (currentTrace >= nTrace) return false; else return true;
		}

		/* (non-Javadoc)
		 * @see java.util.Iterator#next()
		 */
		public Object next() {
			String currentLine=null;
			Trace result = new VectorTrace();

			do {
			currentTrace++;
		
			
			try {
				currentLine = br.readLine();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				currentTrace = nTrace;
				e.printStackTrace();
				return null;
			}

			if (currentLine.substring(0,1).equals("1")) {
				result.setPositiveTrace(true);
			} else {
				result.setPositiveTrace(false);
			}

			}while ((currentTrace<nTrace) && (result.isPositiveTrace()==false));
							
			if (result.isPositiveTrace()==false) return null; //it is required that the trace file ends with a positive trace				

				int initTrace = currentLine.indexOf(" ", 2)+1;		
				while (currentLine.charAt(initTrace)==' ') initTrace++;
			
				int currentPos=initTrace;
				int pos;
			 while ((pos = currentLine.indexOf(" ", currentPos))!= -1) {
			 	result.addSymbol( new Symbol(currentLine.substring(currentPos, pos)));
				currentPos = pos+1;
			 }
			 result.addSymbol( new Symbol(currentLine.substring(currentPos, currentLine.length())));
				
				
			
			return result;
		}

		/* (non-Javadoc)
		 * @see java.util.Iterator#remove()
		 */
		public void remove() {
			// TODO Auto-generated method stub
			
		}
		
	}

}
