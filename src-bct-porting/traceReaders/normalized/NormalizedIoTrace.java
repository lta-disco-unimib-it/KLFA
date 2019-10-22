package traceReaders.normalized;

import java.io.File;
import java.util.Vector;

import traceReaders.raw.TraceException;

import dfmaker.core.Superstructure;
import dfmaker.core.Variable;

/**
 * This class wraps a normalized IO trace. 
 * The behavior of this class is managed by a NormalizedTraceMapper, this class delegates all its functions to the mapper.
 * 
 * 
 * @author Fabrizio Pastore fabrizio.pastore AT gmail.com
 *
 */
public interface NormalizedIoTrace {

	/**
	 * Set the superStructure for entry points
	 * 
	 * @param entrySuperStructure
	 */
	public void setEntrySuperStructure( Superstructure entrySuperStructure );
	
	/**
	 * Set the superStructure for exit points
	 * 
	 * @param exitSuperStructure
	 */
	public void setExitSuperStructure( Superstructure exitSuperStructure );
	
	/**
	 * Add an entry point
	 * @param entryPoint
	 * @param normalizedPoint
	 */
	public void addEntryPoint( String entryPoint, Vector<Variable> normalizedPoint);
	
	/**
	 * 
	 * @param exitPoint
	 * @param normalizedPoint
	 */
	public void addExitPoint( String exitPoint, Vector<Variable> normalizedPoint);
	
	/**
	 * Add an object point to trace
	 * 
	 * @param objectPoint
	 * @param normalizedPoint
	 */
	public void addObjectPoint( String objectPoint, Vector<Variable> normalizedPoint);

	/**
	 * Returns the name of the method this trace belongs to
	 * 
	 * @return
	 */
	public String getMethodName();
	
	/**
	 * Returns the File cointaining trace declarations in daikon format.
	 * The File returned must exist, the caller will open and read from it.
	 * 
	 * Implementations of this method must generate a real .decl file.
	 *  
	 * @return
	 */
	
	public File getDaikonDeclFile();

	/**
	 * Returns the file cointaining trace in daikon format.
	 * The file returned must exists and must respect daikon format. 
	 * 
	 * @return
	 */
	public File getDaikonTraceFile();

	/**
	 * This method is called for optimization purposes. It is called to tell the system that the 
	 * object can be written.
	 * 
	 * @throws TraceException
	 */
	public void commit() throws TraceException;

}
