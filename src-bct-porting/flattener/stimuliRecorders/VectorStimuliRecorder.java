package flattener.stimuliRecorders;

import java.io.BufferedWriter;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Enumeration;
import java.util.Vector;

import flattener.core.StimuliRecorder;


/**
 * @author Davide Lorenzoli
 */
public class VectorStimuliRecorder implements StimuliRecorder {
	private Writer writer;

	/**
	 * Constructor
	 */
	public VectorStimuliRecorder() {	
		this.writer = new BufferedWriter(new OutputStreamWriter(System.out));	
	}

	/**
	 * @see flattener.core.StimuliRecorder#setWriter(java.io.Writer)
	 */
	public void setWriter(Writer writer) {
		this.writer = new BufferedWriter(writer);
	}
	
	/**
	 * @see flattener.core.StimuliRecorder#getWriter()
	 */
	public Writer getWriter() {
		return this.writer;
	}
	
	/**
	 * @see flattener.core.StimuliRecorder#record(java.lang.Object)
	 */
	public void record(Object object) throws Exception {		
		Enumeration elements = ((Vector) object).elements();
		while (elements.hasMoreElements()) {			
			this.writer.write(elements.nextElement().toString() + "\n");
		}
	}
}
