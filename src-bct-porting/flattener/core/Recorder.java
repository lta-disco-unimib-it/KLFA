package flattener.core;

import java.io.OutputStream;


/**
 * Interface of all the flattener.stimuliRecorders
 *
 * @author Davide Lorenzoli
 */
public interface Recorder {
	/**
	 * Sets the output stream to use
	 *
	 * @param out Output stream
	 */
	public void setOutputStream(OutputStream out);

	/**
	 * Returns the set output stream
	 *
	 * @return <code>OutputStream</code>
	 */
	public OutputStream getOutputStream();

	/**
	 * Close the output stream
	 */
	public void closeOutputStream() throws Exception;
}
