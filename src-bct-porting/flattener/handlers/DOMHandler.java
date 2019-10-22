package flattener.handlers;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import flattener.core.Handler;


/**
 * @author Davide Lorenzoli
 *
 */
public class DOMHandler implements Handler {
	private Document document = null;
	private Element currentElement = null;
	private Element root = null;
	
	/**
	 * @param className
	 * @throws ParserConfigurationException
	 */
	public DOMHandler(String className) throws ParserConfigurationException {
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = dbf.newDocumentBuilder();
		document = db.newDocument();
		Element newElement = document.createElement(className);
		document.appendChild(newElement);
		root = newElement;		
		currentElement = root;
	}

	/**
	 * @see flattener.core.Handler#add(java.lang.Object)
	 */
	public void add(Object object) {
		currentElement.setAttribute("value", object.toString());
		currentElement.setAttribute("type", object.getClass().getName());
	}

	/**
	 * @see flattener.core.Handler#goDown(java.lang.reflect.Method)
	 */
	public void goDown(Method method) {
		Element newElement = document.createElement(method.getName());
		currentElement.appendChild(newElement);
		currentElement = newElement;				
	}

	/**
	 * @see flattener.core.Handler#goDown(java.lang.reflect.Field)
	 */
	public void goDown(Field field) {		
		Element newElement = document.createElement(field.getName());
		currentElement.appendChild(newElement);
		currentElement = newElement;
	}

	/**
	 * @see flattener.core.Handler#goDownArray()
	 */
	public void goDownArray() {
		Element newElement = document.createElement("goDownArray");		
		currentElement.appendChild(newElement);
		currentElement = newElement;		
	}

	/**
	 * @see flattener.core.Handler#goDownArray(int)
	 */
	public void goDownArray(int i) {
		Element newElement = document.createElement("E" + Integer.toString(i));
		currentElement.appendChild(newElement);
		currentElement = newElement;
	}
	
	/**
	 * @see flattener.core.Handler#goUpArray()
	 */
	public void goUpArray() {
		currentElement = (Element) currentElement.getParentNode();		
	}
	
	/**
	 * @see flattener.core.Handler#goUp()
	 */
	public void goUp() {
		currentElement = (Element) currentElement.getParentNode();
	}
	
	/**
	 * @see flattener.core.Handler#getData()
	 */
	public Object getData() {
		return document;
	}	
}
