package dfmaker.core;

import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;

/**
 * @author Davide Lorenzoli
 * 
 * Handles the Daikon output containing the inferred models.
 */
public interface DaikonModelsParserInterface {
	
	/**
	 * Parse the ENTER model set and the EXIT model set and returns the
	 * <code>IoModel</code> representing the set of ENTER and EXIT models. 
	 * @param reader
	 * @return <code>IoModel</code> representing the set of ENTER end EXIT models
	 * @throws IOException
	 */
	//public IoModel getModels(Reader reader) throws IOException;
	
	/**
	 * Parse the ENTER model set and the EXIT model set and returns the
	 * <code>IoModel</code> representing the set of ENTER and EXIT models. 
	 * @param reader
	 * @return <code>IoModel</code> representing the set of ENTER end EXIT models
	 * @throws IOException
	 */
	public ArrayList<String> getPreconditions(Reader reader) throws IOException;
	
	/**
	 * Parse the EXIT model set and returns the <code>IoModel</code>
	 * representing the set of EXIT models.
	 * @param reader
	 * @return <code>IoModel</code> representing the set of EXIT models
	 * @throws IOException
	 */
	public ArrayList<String> getPostconditions(Reader reader) throws IOException;
}