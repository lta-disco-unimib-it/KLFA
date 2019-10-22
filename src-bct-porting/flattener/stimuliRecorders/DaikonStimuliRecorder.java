package flattener.stimuliRecorders;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import flattener.core.StimuliRecorder;


/**
 * @author Davide Lorenzoli
 *
 */
public class DaikonStimuliRecorder implements StimuliRecorder {	
	private Writer writer;
	private String currentPath;
	
	/**
	 * Constructor
	 */
	public DaikonStimuliRecorder() {
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
	 * @throws ClassCastException
	 * @throws IOException
	 * @see flattener.core.StimuliRecorder#record(java.lang.Object)
	 */
	public void record(Object object) throws ClassCastException, IOException {
		currentPath = new String();		

		Document root = (Document) object;	
					
		explore(root.getDocumentElement());
	}

	/**
	 * It explore the DOM tree and store the trace into the trece file.
	 * 
	 * @param element
	 * @throws IOException
	 * @throws IOException
	 */
	private void explore(Element element) throws IOException {
		//store current path length, used to go back in current ppath name
		int oldPathLen = currentPath.length();
		
		// Make current path		
		if (element.getNodeName().equals("class")) {
			currentPath += element.getAttribute("name");
		}
		if (element.getNodeName().equals("method")) {
			currentPath += "." + element.getAttribute("name") + "()";
		}
		if (element.getNodeName().equals("field")) {
			currentPath += "." + element.getAttribute("name");
		}
		if (element.getNodeName().equals("index")) {			
			currentPath += "[" + element.getAttribute("position") + "]";
		}

		// Recursion
		for (int i=0; i<element.getChildNodes().getLength(); i++) {			
			explore((Element) element.getChildNodes().item(i));
		}					
		
		if (element.getAttributeNode("value") != null && element.getAttributeNode("type") != null) {
			String value = element.getAttribute("value"); 
			String type = element.getAttribute("type");
							
			// Make the message body
			String message = null;
			
			if (value.equals("null") && type.equals("")) {
				message = currentPath + "\n";
				message += "null\n";
				message += "1\n";							
			} else {
				if (isArrayType(type)) {
					message = currentPath + "[]\n";
					message += "[" + formatValue(value, type) + "]\n";
					message += "1\n";
				} else {
					message = currentPath + "\n";
					message += formatValue(value, type) + "\n";
					message += "1\n";
				}
			}
						
			// Write the message
			this.writer.write(message);
			this.writer.flush();			
		} else if ( element.getAttributeNode("reference") != null) { // Handle references to visited objects
			
			String refTo = element.getAttribute("reference");
			String message = currentPath + "\n";
			message += "@"+refTo+"\n";
			message += "1\n";
			
			//Write the message
			this.writer.write(message);
			this.writer.flush();
		}
		
		currentPath = currentPath.substring(0, oldPathLen);
		return;
		
		//OLD CODE
		// Make currentPath string
//		if (element.getNodeName().equals("class")) {
//			return;
//		}
//		else if (element.getNodeName().equals("index")) {
//			currentPath = currentPath.substring(0, currentPath.lastIndexOf("["));
//		}
//		else if (!element.getNodeName().equals("array")) {			
//			currentPath = currentPath.substring(0, currentPath.lastIndexOf("."));
//		}
		
	}
		
	/**
	 * It converts a String in an array of Character. In example if the input
	 * string is "Hello" the output string will be "H" "e" "l" "l" "o".
	 * 
	 * @param value
	 * @param type
	 * @return
	 */
	private String formatValue(String value, String type) {
		String newValue = new String();
		
		if (isArrayType(type)) {
			char charArray[] = value.toCharArray();
			for (int i=0; i<charArray.length; i++) {
				newValue += "\"" + Character.toString(charArray[i]) + "\"";
				if (i<charArray.length-1) {
					newValue += " ";
				}
			}		
		}
		else if (type.equals(String.class.getName())) {
		    newValue = "\"" + value + "\"";
		}		
		else if (type.equals(Character.class.getName())) {
		    newValue = "\"" + value + "\"";
		}
		else if (type.equals(Boolean.class.getName())) {
		    if (value.equals("true")) newValue = "1";
		    if (value.equals("false")) newValue = "0";
		}
		else {
			newValue = value;
		}
		
		return newValue;
	}
	
	private boolean isArrayType(String type) {
		boolean result = false;
		
		if (type.equals("java.lang.String")) {
			result = false;
		}		
		if (type.equals("java.lang.Boolean")) {
			result = false;
		}
		return result;			
	}
}
