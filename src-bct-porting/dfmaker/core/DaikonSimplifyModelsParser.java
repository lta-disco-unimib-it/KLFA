package dfmaker.core;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;

/**
 * @author Davide Lorenzoli
 *
 * Handles the Daikon output containing the inferred models in Simplify format.
 * The Simplify format can be obtained running Daikon with "--format simplify"
 * option.
 */
public class DaikonSimplifyModelsParser implements DaikonModelsParserInterface {			

	/**
	 * @see dfmaker.core.DaikonModelsParserInterface#getPostconditions(java.io.Reader)
	 */
	public ArrayList<String> getPreconditions(Reader reader) throws IOException {
		BufferedReader in = new BufferedReader(reader);		
		ArrayList<String> ioModel = new ArrayList<String>();		
		
		// read the first line
		String line = in.readLine();
		// Loop to the line before the ENTER statement		
		while (line != null && !line.contains("====")) {
			line = in.readLine();
		}
		// Start parsing the ENTER block
		boolean endEnterBlock = false;
		while (line != null && !endEnterBlock) {
			if (line.contains(":::ENTER")) {
				line = in.readLine();
				while (line != null && !line.contains("====")) {
					ioModel.add(line);
					line = in.readLine();					
				}
				endEnterBlock = true;
			}
			line = in.readLine();
		}
		in.close();
		return ioModel;
	}

	/**
	 * @throws IOException 
	 * @see dfmaker.core.DaikonModelsParserInterface#getPreconditions(java.io.Reader)
	 */
	public ArrayList<String> getPostconditions(Reader reader) throws IOException {
		BufferedReader in = new BufferedReader(reader);
		ArrayList<String> ioModel = new ArrayList<String>();
		
		String line = in.readLine();
		// Loop to the line before the ENTER statement
		while (line != null && !line.contains("====")) {
			line = in.readLine();
		}
		// Start parsing the EXIT block
		boolean endExitBlock = false;
		while (line != null && !endExitBlock) {
			if (line.contains(":::EXIT")) {
				line = in.readLine();
				while (line != null && !line.contains("Exiting")) {
					ioModel.add(line);
					line = in.readLine();					
				}
				endExitBlock = true;
			}
			line = in.readLine();
		}
		in.close();
		return ioModel;
	}
}
