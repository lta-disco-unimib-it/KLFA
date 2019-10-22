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
