package flattener.handlers;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Vector;

import flattener.core.Handler;



/**
 * This class collects the objects into an array 
 *
 * @author Davide Lorenzoli
 */
public class VectorHandler implements Handler {
	/** Contains the data */
	private Vector vector = new Vector();
	
	/**
	 * Constructor
	 */
	public VectorHandler() {
	}

	/**
	 * @see Handler#add(java.lang.Object)
	 */
	public void add(Object object) {		
		vector.add(object);				
	}

	/**
	 * @see Handler#goDown(java.lang.reflect.Method)
	 */
	public void goDown(Method method) {				
	}

	/**
	 * @see Handler#goDown(java.lang.reflect.Field)
	 */
	public void goDown(Field field) {
	}

	/**
	 * @see Handler#goUp()
	 */
	public void goUp() {
	}

	/**
	 * @see Handler#getData()
	 */
	public Object getData() {
		return vector;
	}

	/* (non-Javadoc)
	 * @see flattener.core.Handler#goDownArray()
	 */
	public void goDownArray() {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see flattener.core.Handler#goUpArray()
	 */
	public void goUpArray() {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see flattener.core.Handler#goDownArray(int)
	 */
	public void goDownArray(int i) {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see flattener.core.Handler#goUpArray(int)
	 */
	public void goUpArray(int i) {
		// TODO Auto-generated method stub
		
	}
}
