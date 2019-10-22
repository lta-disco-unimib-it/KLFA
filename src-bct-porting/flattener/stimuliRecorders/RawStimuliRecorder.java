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

import java.io.Writer;
import java.util.Iterator;

import flattener.core.StimuliRecorder;
import flattener.handlers.RawHandler;

public class RawStimuliRecorder implements StimuliRecorder {
	private Writer writer;
	
	public Writer getWriter() {
		return writer;
	}

	public void record(Object object) throws Exception {
		RawHandler handler = (RawHandler) object;
		
		Iterator it = handler.getNodeNamesIt();
		
		while ( it.hasNext() ){
			String nodeName = (String) it.next();
			String value = handler.getNodeValue(nodeName);
			String message = nodeName + "\n" +
							value + "\n" +
							"1\n";
			writer.write(message);
			writer.flush();
		}

	}

	public void setWriter(Writer writer) {
		this.writer = writer;
	}

}
