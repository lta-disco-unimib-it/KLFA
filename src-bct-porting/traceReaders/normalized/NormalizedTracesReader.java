package traceReaders.normalized;

import java.util.ArrayList;
import java.util.Iterator;

import database.DataLayerException;

import traceReaders.raw.TraceException;

/**
 * @author 
 * This class contains methods to get from DB data needed to execute 
 * the first step of the GK-Tail algorithm (merge similar trace)
 */

public interface NormalizedTracesReader {
	
	/**
	 * Returns an Iterator over all Methods 
	 * @return
	 */
	public Iterator getMethods() throws DataLayerException;

	/**
	 * Returns an Iterator over all Interaction Traces
	 * @return
	 */
	public Iterator getInteractionTraces(int method) throws DataLayerException;
	
	/**
	 * Returns thread of the specified trace
	 * @return
	 */
	public int getInteractionThread(int interactionTrace) throws DataLayerException;
	
	/**
	 * Returns an Iterator over all Method Calls of an Interaction Trace
	 * @return
	 */
	public Iterator getMethodCall(int interactionTrace) throws DataLayerException;
	
	/**
	 * Returns an Iterator over all ID Method Calls of an Interaction Trace
	 * @return
	 */
	public Iterator getIDMethodCall(int interactionTrace) throws DataLayerException;
	
	/**
	 * Returns an Iterator over all Enter Normalized Data of a Method Call
	 * @return
	 */
	public String getNormalizedDataENTER(int idMethodCall) throws DataLayerException;
	
	/**
	 * Returns an Iterator over all Exit Normalized Data of a Method Call
	 * @return
	 */
	public String getNormalizedDataEXIT(int idMethodCall) throws DataLayerException;

	/**
	 * Returns an Iterator over all Normalized Data of a Method Call
	 * @return
	 */
	public String getNormalizedDataENTEREXIT(int idMethodCall) throws DataLayerException; 
}
