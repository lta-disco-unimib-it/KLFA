package flattener.handlers;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import flattener.core.Handler;



/**
 * This class doesn't store any data, it just print on screen the
 * given object. It has two way to operate: normal way and verbose way.
 * The verbose way prints all the calls to methods and filds, for that reason
 * it may be useful for debugging.
 *
 * @author Davide Lorenzoli
 */
 
public class PrintHandler implements Handler {
	private boolean verbose = false;
	
	/**
	 * Constructor
	 */
	public PrintHandler() {		
	}
		
	/**
	 * Constructor
	 * 
	 * @param verbose
	 */
	public PrintHandler(boolean verbose) {
		this.verbose = verbose;
	}

	/**
	 * @see Handler#add(java.lang.Object)
	 */
	public void add(Object object) {
		if (object.getClass().isArray()) {
			printArray(object);
		} else {
			System.out.println(object.getClass().getName() + " " + object);
		}
	}

	/**
	 * This method always return null
	 * 
	 * @see Handler#getData()
	 */
	public Object getData() {
		return null;
	}

	/**
	 * @see Handler#goDown(java.lang.reflect.Method)
	 */
	public void goDown(Method method) {
		if (verbose) System.out.println("goDown: " + method.toString());
	}

	/**
	 * @see Handler#goDown(java.lang.reflect.Field)
	 */
	public void goDown(Field field) {
		if (verbose) System.out.println("doDown: " + field.toString());
	}

	/**
	 * @see Handler#goUp()
	 */
	public void goUp() {
		if (verbose) System.out.println("goUP");
	}

	/**
	 * This methods prints on screen any primitive values array.
	 *
	 * @param array
	 */
	private void printArray(Object array) {
		String typeName = array.getClass().getComponentType().getName();
		System.out.print(typeName + " [");

		if (typeName.equals("byte")) {
			byte[] primitiveArray = (byte[]) array;

			for (int i = 0; i < primitiveArray.length; i++) {
				System.out.print(primitiveArray[i] + ((i == (primitiveArray.length - 1)) ? "" : ", "));
			}
		} else if (typeName.equals("char")) {
			char[] primitiveArray = (char[]) array;

			for (int i = 0; i < primitiveArray.length; i++) {
				System.out.print(primitiveArray[i] + ((i == (primitiveArray.length - 1)) ? "" : ", "));
			}
		} else if (typeName.equals("boolean")) {
			boolean[] primitiveArray = (boolean[]) array;

			for (int i = 0; i < primitiveArray.length; i++) {
				System.out.print(primitiveArray[i] + ((i == (primitiveArray.length - 1)) ? "" : ", "));
			}
		} else if (typeName.equals("short")) {
			short[] primitiveArray = (short[]) array;

			for (int i = 0; i < primitiveArray.length; i++) {
				System.out.print(primitiveArray[i] + ((i == (primitiveArray.length - 1)) ? "" : ", "));
			}
		} else if (typeName.equals("int")) {
			int[] primitiveArray = (int[]) array;

			for (int i = 0; i < primitiveArray.length; i++) {
				System.out.print(primitiveArray[i] + ((i == (primitiveArray.length - 1)) ? "" : ", "));
			}
		} else if (typeName.equals("long")) {
			long[] primitiveArray = (long[]) array;

			for (int i = 0; i < primitiveArray.length; i++) {
				System.out.print(primitiveArray[i] + ((i == (primitiveArray.length - 1)) ? "" : ", "));
			}
		} else if (typeName.equals("float")) {
			float[] primitiveArray = (float[]) array;

			for (int i = 0; i < primitiveArray.length; i++) {
				System.out.print(primitiveArray[i] + ((i == (primitiveArray.length - 1)) ? "" : ", "));
			}
		} else if (typeName.equals("double")) {
			double[] primitiveArray = (double[]) array;

			for (int i = 0; i < primitiveArray.length; i++) {
				System.out.print(primitiveArray[i] + ((i == (primitiveArray.length - 1)) ? "" : ", "));
			}
		}

		System.out.println("]");
	}

	/**
	 * @see flattener.core.Handler#goDownArray()
	 */
	public void goDownArray() {
		if (verbose) System.out.println("goDownArray"); 
		
	}

	/**
	 * @see flattener.core.Handler#goUpArray()
	 */
	public void goUpArray() {
		if (verbose) System.out.println("goUpArray");
		
	}

	/**
	 * @see flattener.core.Handler#goDownArray(int)
	 */
	public void goDownArray(int i) {
		if (verbose) System.out.println("goDownArray: " + i);
		
	}

	/**
	 * @see flattener.core.Handler#goUpArray(int)
	 */
	public void goUpArray(int i) {
		if (verbose) System.out.println("goUpArray: " + i);
		
	}
}
