package flattener.core;

import java.io.Writer;


/**
 * @author Davide Lorenzoli
 */
public interface StimuliRecorder {
	
	/**
	 * @param writer
	 */
	public void setWriter(Writer writer);

	/**
	 * @return
	 */
	public Writer getWriter();

	/**
	 * @param object
	 * @throws Exception
	 */
	public void record(Object object) throws Exception;
}
