package flattener.core;

import java.lang.reflect.InvocationTargetException;


/**
 * A flattener is an instrument that permits to inspect the state of an object.
 * The state of an object is composed by it's own fields value and all the
 * return value of it's inspector methods. The inspector methods, by definition,
 * are all the methods that do not modify the state of the object when they
 * are invoked.
 * 
 * @author Davide Lorenzoli
 */
public interface Flattener {
	/**
	 * Invoking this method the given object should be inspected and it's state
	 * should be stored into the defined <code>DataHandler</code>.
	 *
	 * @param object The object to be inspected
	 *
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	public abstract void doSmash(Object object)
		throws
			IllegalArgumentException,
			IllegalAccessException,
			InvocationTargetException;
	/**
	 * Invoking this method the given primitive type should be inspected and 
	 * it's state should be stored into the defined <code>Handler</code>.
	 *
	 * @param primitiveType The primitive type to be inspected
	 *
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	public abstract void doSmash(boolean primitiveType)
		throws
			IllegalArgumentException,
			IllegalAccessException,
			InvocationTargetException;
	/**
	 * Invoking this method the given primitive type should be inspected and 
	 * it's state should be stored into the defined <code>Handler</code>.
	 *
	 * @param primitiveType The primitive type to be inspected
	 *
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	public abstract void doSmash(byte primitiveType)
		throws
			IllegalArgumentException,
			IllegalAccessException,
			InvocationTargetException;
	/**
	 * Invoking this method the given primitive type should be inspected and 
	 * it's state should be stored into the defined <code>Handler</code>.
	 *
	 * @param primitiveType The primitive type to be inspected
	 *
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	public abstract void doSmash(char primitiveType)
		throws
			IllegalArgumentException,
			IllegalAccessException,
			InvocationTargetException;
	/**
	 * Invoking this method the given primitive type should be inspected and 
	 * it's state should be stored into the defined <code>Handler</code>.
	 *
	 * @param primitiveType The primitive type to be inspected
	 *
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	public abstract void doSmash(short primitiveType)
		throws
			IllegalArgumentException,
			IllegalAccessException,
			InvocationTargetException;
	/**
	 * Invoking this method the given primitive type should be inspected and 
	 * it's state should be stored into the defined <code>Handler</code>.
	 *
	 * @param primitiveType The primitive type to be inspected
	 *
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	public abstract void doSmash(int primitiveType)
		throws
			IllegalArgumentException,
			IllegalAccessException,
			InvocationTargetException;
	/**
	 * Invoking this method the given primitive type should be inspected and 
	 * it's state should be stored into the defined <code>Handler</code>.
	 *
	 * @param primitiveType The primitive type to be inspected
	 *
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	public abstract void doSmash(long primitiveType)
		throws
			IllegalArgumentException,
			IllegalAccessException,
			InvocationTargetException;
	/**
	 * Invoking this method the given primitive type should be inspected and 
	 * it's state should be stored into the defined <code>Handler</code>.
	 *
	 * @param primitiveType The primitive type to be inspected
	 *
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	public abstract void doSmash(float primitiveType)
		throws
			IllegalArgumentException,
			IllegalAccessException,
			InvocationTargetException;
	/**
	 * Invoking this method the given primitive type should be inspected and 
	 * it's state should be stored into the defined <code>Handler</code>.
	 *
	 * @param primitiveType The primitive type to be inspected
	 *
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	public abstract void doSmash(double primitiveType)
		throws
			IllegalArgumentException,
			IllegalAccessException,
			InvocationTargetException;
	/**
	 * Set the <code>Handler</code> that will be used to store the
	 * state of a given object. Before invoke the <code>doSmash</code> method
	 * the <code>Hadler</code> must be defined.
	 *
	 * @param dataHandler
	 */
	public abstract void setDataHandler(Handler dataHandler);
	
	/**
	 * Returns the current <code>Handler</code> containing the structure of
	 * the computated object.
	 *
	 * @return <code>Handler</code>
	 */
	public abstract Handler getDataHandler();
}