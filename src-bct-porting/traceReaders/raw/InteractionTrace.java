package traceReaders.raw;

import java.io.IOException;

/**
 * This interface represent an interaction trace
 * @author Fabrizio Pastore fabrizio.pastore AT gmail.com
 *
 */
public interface InteractionTrace {
	
	/**
	 * Return the thread ID of this trace
	 * 
	 * @return
	 */
	public String getThreadId();
	
	/**
	 * Return the next token of a trace, i.e. the signature of a method in a sequence call.
	 * For example package.myClass.myMethod()B
	 * 
	 * @return
	 * @throws TraceException
	 */
	public Token getNextToken() throws TraceException;
	
	/**
	 * Return the next meta data information. If called in synchrony with getNextToken returns the meta data 
	 * associated to the method token returned.
	 * 
	 * If not implemented throw an unsupported operation exception.
	 * 
	 * @return
	 * @throws TraceException
	 */
	public String getNextMetaData() throws TraceException;
	
	/**
	 * Close thetrace, this is done for optimization purposes
	 * 
	 * @throws IOException
	 */
	public void close() throws IOException;
}
