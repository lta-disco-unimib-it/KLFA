package flattener.core;

import java.lang.reflect.Field;
import java.lang.reflect.Method;


/**
 * A class implementing this interface is like a "continer"; it knows in
 * witch way store the given data. In example it can implements a tree data
 * structure or an array data structure.
 *
 * @author Davide Lorenzoli
 */
public interface Handler {
		
	/**
	 * Add an object to it's internal data structure.
	 *
	 * @param object
	 */
	public void add(Object object);
	
	/**
	 * Go down in a array data sturcture; it should be used when you want
	 * create a new node in the structure, i.e. in a tree data structure.
	 */
	public void goDownArray();
	
	/**
	 * Go down in a array data sturcture; it should be used when you want
	 * inser arrau structure. If you are in a given node, i.e. "arrayNode",
	 * using this methods the next element will be added as branchs
	 * [E1, ..., En] of the node, as shown is the following schema.
	 * <pre>
	 *           (arrayNode)
	 *            |    |   |
	 *          (E1) (..) (En)
	 * </pre>  
	 *
	 * @param i the index of the current element
	 */
	public void goDownArray(int i);
	
	/**
	 * Go up in a array data sturcture
	 */	
	public void goUpArray();	

	/**
	 * It is used to go down in a sturcture, i.e. a tree structure
	 *
	 * @param method
	 */
	public void goDown(Method method);

	/**
	 * It is used to go down in a sturcture, i.e. a tree structure
	 *
	 * @param field
	 */
	public void goDown(Field field);

	/**
	 * It is used to go up in a sturcture, i.e. a tree structure
	 */
	public void goUp();

	/**
	 * It returns the stored data
	 *
	 * @return <code>Object</code> containing the stored data
	 */
	public Object getData();
}
