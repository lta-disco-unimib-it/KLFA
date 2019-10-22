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
 * @author Davide Lorenzoli
 *
 */
public class DaikonHandler implements Handler {
	private Document document = null;
	private Element currentElement = null;
	private Element root = null;
	
	/**
	 * 
	 */
	public DaikonHandler() {
		this("Root");
	}
	
	/**
	 * @param rootNodeName
	 */
	public DaikonHandler(String rootNodeName) {
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
	 * @see flattener.core.Handler#goDown(java.lang.reflect.Method)
	 */
	public void goDown(Method method) {
		Element newElement = document.createElement("method");
		newElement.setAttribute("name", method.getName());
		
		currentElement.appendChild(newElement);
		currentElement = newElement;				
	}

	private void logCurrent(){
		Node parent = currentElement;
		String res ="";
		NamedNodeMap m = parent.getAttributes();
		if ( m != null ){
			Node a = m.getNamedItem("name");
			if ( a != null ){
				res = a.toString();
				while ( (parent != null ) && ( parent = parent.getParentNode() ) != null ){
					NamedNodeMap att = parent.getAttributes();
					if ( att != null)
						res = att.getNamedItem("name")+"."+res;

				}
			}
		}
		System.out.println("        >>>>>>>>>>>>>>>>  "+res);

	}
	
	/**
	 * @see flattener.core.Handler#goDown(java.lang.reflect.Field)
	 */
	public void goDown(Field field) {		
		Element newElement = document.createElement("field");
		newElement.setAttribute("name", field.getName());		
		
		//logCurrent();
				
		currentElement.appendChild(newElement);
		currentElement = newElement;
	}

	/**
	 * @see flattener.core.Handler#goDownArray()
	 */
	public void goDownArray() {
		Element newElement = document.createElement("array");
				
		currentElement.appendChild(newElement);
		currentElement = newElement;		
	}

	/**
	 * @see flattener.core.Handler#goDownArray(int)
	 */
	public void goDownArray(int i) {
		Element newElement = document.createElement("index");
		newElement.setAttribute("position", Integer.toString(i));
		
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
	 * @see flattener.core.Handler#add(java.lang.Object)
	 */
	public void add(Object object) {
		
		if (object != null) {
			currentElement.setAttribute("value", object.toString());
			currentElement.setAttribute("type", object.getClass().getName());
		} else {
			currentElement.setAttribute("value", "null");
			currentElement.setAttribute("type", new String());
		}
	}
	
	/**
	 * @see flattener.core.Handler#getData()
	 */
	public Object getData() {
		return document;
	}
}
