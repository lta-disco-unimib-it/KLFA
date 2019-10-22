/*******************************************************************************
 *    Copyright 2019 Fabrizio Pastore, Leonardo Mariani
 *   
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 *******************************************************************************/
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
