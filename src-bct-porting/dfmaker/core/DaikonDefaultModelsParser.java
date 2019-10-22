package dfmaker.core;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * @author Davide Lorenzoli
 *
 * Handles the Daikon output containing the inferred models in default format.
 */
public class DaikonDefaultModelsParser implements DaikonModelsParserInterface {

	/**
	 * @see dfmaker.core.DaikonModelsParserInterface#getPostconditions(java.io.Reader)
	 */
	public ArrayList<String> getPreconditions(Reader reader) throws IOException {
		BufferedReader in = new BufferedReader(reader);
		ArrayList<String> ioModel = new ArrayList<String>();
		Set<String> memory = new HashSet<String>();
		
		outerWhile: while (true) {
			String line = in.readLine();
			if (line == null) {
				break;
			} else if (line.contains(":ENTER")) {
				while (true) {
					line = in.readLine();
					//FIXME: throw an exception
					if ( line == null ) {
						break;
					}
					if (line.startsWith("======")) {
						break outerWhile;
					} else {
						ioModel.add(line);
					}
				}
			}
		}
		memory.clear();
		outerWhile: while (true) {
			String line = in.readLine();
			if (line == null) {
				break;
			} else if (line.contains(":EXIT")) {
				while (true) {
					line = in.readLine();
					if (line == null || line.startsWith("Exiting")) {
						break outerWhile;
					} else if (line.contains("orig(")) {
						line = line.substring(line.lastIndexOf("orig("), line.length());
						line.trim();
						line = line.substring(4, line.length());
						memory.add(line);
					}
				}
			}
		}
		Iterator i = memory.iterator();
		while (i.hasNext()) {
			String store = "store" + (String) i.next();
			ioModel.add(store);
		}
		in.close();
		return ioModel;
	}

	/**
	 * @see dfmaker.core.DaikonModelsParserInterface#getPreconditions(java.io.Reader)
	 */
	public ArrayList<String> getPostconditions(Reader reader) throws IOException {
		BufferedReader in = new BufferedReader(reader);
		ArrayList<String> ioModel = new ArrayList<String>();
		
		String line = in.readLine();
		while (line != null) {
			if (line.contains(":EXIT")) {
				line = in.readLine();
				while (line != null && !line.startsWith("Exiting")) {
					ioModel.add(line);
					line = in.readLine();					
				}				
			}
			line = in.readLine();
		}
		in.close();
		return ioModel;
	}
}
