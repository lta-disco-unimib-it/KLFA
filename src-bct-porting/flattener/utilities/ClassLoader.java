package flattener.utilities;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

/**
 * This class is used to load a specified class given its name.
 *
 * @author Davide Lorenozoli
 */
public class ClassLoader extends java.lang.ClassLoader {
	/**
	 * Constructor
	 */
	public ClassLoader() {
	}

	/**
	 * This method is used to load a specified class given the name of the
	 * class.
	 *
	 * @param classPackage The package of the class
	 * @param className The name of the class
	 *
	 * @return <code>Object</code>
	 *
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	
	public Object getObject(String className) throws FileNotFoundException, IOException {		
		Object object = null;
		
		try {
			InputStream is = ClassLoader.getSystemResourceAsStream(className.replace('.', '/') + ".class");
			byte bytes[] = new byte[is.available()];
			is.read(bytes);		    
			object = (Object) defineClass(className, bytes, 0, bytes.length).newInstance();			
		} catch (ClassFormatError e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (NoClassDefFoundError e) {
		    e.printStackTrace();
		}

		return object;
	}	
}
