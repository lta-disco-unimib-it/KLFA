package flattener.handlers;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

import flattener.core.Handler;

/**
 * This id the data handler made for BreadthObjectFlattener.
 * 
 * Since BreadthObjectFlattener does not meke a depth-first visit it is not possible to use the goDown/Up methods defined in Handler.
 * 
 * The role of this class i to provide compatibility between data produced by the old flattener and data produced by the new (the Breadth one).
 * 
 * TODO:
 * Splitting Handler interface into two parts: ReadHandler and WriteHandler could be a more clean solution.
 *  
 * @author Fabrizio Pastore fabrizio.pastore AT gmail.com
 *
 */
public class BreadthHandler implements Handler {
	private Document document = null;
	private Element currentElement = null;
	private Element root = null;
	
	public BreadthHandler( String rootNodeName ){



		try {
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			//DocumentBuilderFactoryImpl dbf = new DocumentBuilderFactoryImpl();
			DocumentBuilder db = dbf.newDocumentBuilder();
			document = db.newDocument();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		}

		Element element = document.createElement("class");
		element.setAttribute("name", rootNodeName);

		document.appendChild(element);
		root = element;		
		currentElement = root;
	}
	
	/**
	 * Add and object with the nodeName given to the root of the document
	 *  
	 * @param nodeName
	 * @param object
	 */
	public void goDownMethod( String nodeName ) {
		Element newElement = document.createElement("method");
		newElement.setAttribute("name", nodeName);
		
		currentElement.appendChild(newElement);
		currentElement = newElement;

	}

	/**
	 * Add and object with the nodeName given to the root of the document
	 *  
	 * @param nodeName
	 * @param object
	 */
	public void goDownField( String nodeName ) {
		Element newElement = document.createElement("field");
		newElement.setAttribute("name", nodeName);
				
		currentElement.appendChild(newElement);
		currentElement = newElement;
	}
	
	public void addRef(String refTo) {
		currentElement.setAttribute("reference", refTo);
	}
	
	public void add(Object object) {
		if (object != null) {
			currentElement.setAttribute("value", object.toString());
			currentElement.setAttribute("type", object.getClass().getName());
		} else {
			currentElement.setAttribute("value", "null");
			currentElement.setAttribute("type", new String());
		}
	}

	public Object getData() {
		return document;
	}

	public void goDown(Method method) {
		throw new UnsupportedOperationException("Not implemented in DepthHandler, are you using DepthHandler with ObjectFlattener? ");	
	}
	

	public void goDown(Field field) {
		throw new UnsupportedOperationException("Not implemented in DepthHandler, are you using DepthHandler with ObjectFlattener? ");
	}

	public void goDownArray() {
		Element newElement = document.createElement("array");
		
		currentElement.appendChild(newElement);
		currentElement = newElement;		
	}

	public void goDownArray(int i) {
		Element newElement = document.createElement("index");
		newElement.setAttribute("position", Integer.toString(i));
		
		currentElement.appendChild(newElement);
		currentElement = newElement;
	}

	public void goUp() {
		currentElement = (Element) currentElement.getParentNode();
	}

	public void goUpArray() {
		currentElement = (Element) currentElement.getParentNode();
	}

}
