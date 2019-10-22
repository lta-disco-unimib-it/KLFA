package traceReaders.normalized;

import java.util.ArrayList;
import java.util.Iterator;

import database.DataLayerException;

/**
 * @author 
 * This class contains methods to get from DB data needed to execute 
 * the third step of the GK-Tail algorithm (merge equivalent trace)
 */
public interface GKTailTracesReader {
	
	/**
	 * Returns an Iterator over all Methods 
	 * @return
	 */
	public Iterator getMethods() throws DataLayerException;

	/**
	 * Returns an Iterator over all Interaction Traces
	 * @return
	 */
	public ArrayList getInteractionTraces(Iterator methodList) throws DataLayerException;
	
	/**
	 * Returns an Iterator over all Method Calls of an Interaction Trace
	 * @return
	 */
	public Iterator getMethodCall(ArrayList interactionTraceList) throws DataLayerException;
	
	/**
	 * Returns an Iterator over all ID Method Calls of an Interaction Trace
	 * @return
	 */
	public Iterator getIDMethodCall(int traceMethod, ArrayList interactionTraceList) throws DataLayerException;

	public String getConstraint(int idMethodCall) throws DataLayerException;
	
}
