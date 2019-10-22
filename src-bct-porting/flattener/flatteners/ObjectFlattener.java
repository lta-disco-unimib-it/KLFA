package flattener.flatteners;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import conf.EnvironmentalSetter;

import flattener.core.Flattener;
import flattener.core.Handler;
import flattener.core.InspectorRecognizer;
import flattener.core.Plugin;
import flattener.handlers.PrintHandler;
import flattener.inspectorRecognizers.DefaultInspectorRecognizer;
import flattener.utilities.FieldFilter;
import flattener.utilities.FieldsFilterFactory;
import flattener.utilities.FieldsRetriever;
import flattener.utilities.PropertiesLoader;


/**
 * ObjectFlattener is used to flat an object into its primitive types.
 *
 * @author Davide Lorenzoli
 */
public class ObjectFlattener implements Flattener {

	/* -------------------------- Default settings -------------------------- */	       
    
	

	/*    private String classPath;
    private String objectFlattenerPath;
    private String confPath;
    private String inspectorRecognizersPath;
    private String pluginsPath;
*/	
	/** Boolean constant of value <code>true</code> */
	private final boolean INCREMENT = true;

	/** Boolean constant of value <code>false</code> */
	private final boolean DECREMENT = false;

	/* ---------------------------- Global fields ---------------------------- */

	/** It contains the maxDepth of the itaration. By dafault is 5. */
	private int maxDepth;

	/**
	 * If <code>true</code> aggragation types will be smash, otherwise it will
	 * not. By default is false.
	 */
	private boolean smashAggregations;
	
	/**
	 * If <code>true</code> inner classes are handled in special manner.
	 */
	private boolean ignoreInnerClasses;
	
	private boolean loadPlugins;
	private boolean loadClassesToIgnore ;
	private boolean loadInspectorRecognizer;
	
	/** It contains the current maxDepth */
	private int currentDepth = 0;
	
	/** It contains the references of the object aready alaborated */
	private Vector elaboratedObjects = new Vector();

	/** Contains the name of the classes to be ignored */
	private Hashtable classesToIgnoreList = new Hashtable();
	
	/** Contains the name of the methods to be ignored */
	private Hashtable methodsToIgnoreList = new Hashtable();

	/** Contains the name of the pluginsList to be used */
	private Hashtable pluginsList = new Hashtable();

	/** Interface to format output in specified ways */
	private Handler dataHandler;
	
	private InspectorRecognizer inspectorRecognizer;

	private FieldFilter fieldsFilter; 

	/* ---------------------------- Constructors ---------------------------- */

	/**
	 * Creates a new ObjectFlattener object. By default will be applied the
	 * setting contained into the file "objectFlattener.conf"
	 *
	 */
	public ObjectFlattener() {
	    // Default properties
	    this.inspectorRecognizer = new DefaultInspectorRecognizer();
	    this.dataHandler = new PrintHandler();
	    
		// Load properties from file
		EnvironmentalSetter.setObjectFlattenerVariables();
	    loadProperties();		
	    
		/*
		System.out.println("------------------- Object flattener properties -------------------");
		System.out.println("Class path: " + this.classPath);
		System.out.println("Object flattener path: " + this.objectFlattenerPath);
		System.out.println("Conf path: " + this.confPath);
		System.out.println("Plugins path: " + this.pluginsPath);
		System.out.println("Inspector recognizers path: " + this.inspectorRecognizersPath);
		System.out.println();
		System.out.println("objectFlattener.smashAggregations: " + this.smashAggregations);
		System.out.println("objectFlattener.maxDepth: " + this.maxDepth);
		System.out.println("plugins.load: " + this.loadPlugins);
		System.out.println("classesToIgnore.load: " + this.loadClassesToIgnore);
		System.out.println("inspectorRecognizer.load: " + this.loadInspectorRecognizer);
		System.out.println();
		System.out.println("Plugins loaded: " + this.pluginsList);
		System.out.println("Classes to ignore loaded: " + this.classesToIgnoreList);
		System.out.println("Inspector recognizer loaded: " + this.inspectorRecognizer);
		System.out.println("-------------------------------------------------------------------");
		*/
	}

	/**
	 * Creates a new ObjectFlattener object.
	 *
	 * @param maxDepth It contains the maxDepth of the itaration
	 * @param smashAggregations If <code>true</code> aggregation types will be
	 * 		  smash; if <code>false</code> will not.
	 */
	public ObjectFlattener(int maxDepth, boolean smashAggregations) {
	    // Default properties	    
	    this.inspectorRecognizer = new DefaultInspectorRecognizer();
	    this.dataHandler = new PrintHandler();
	    
	    // Load properties from file
		EnvironmentalSetter.setObjectFlattenerVariables();
	   loadProperties();
		this.maxDepth = maxDepth;
		this.smashAggregations = smashAggregations;				
		
		System.out.println("------------------- Object flattener properties -------------------");
		/*System.out.println("Class path: " + this.classPath);
		System.out.println("Object flattener path: " + this.objectFlattenerPath);
		System.out.println("Conf path: " + this.confPath);
		System.out.println("Plugins path: " + this.pluginsPath);
		System.out.println("Inspector recognizers path: " + this.inspectorRecognizersPath);*/
		System.out.println();
		System.out.println("objectFlattener.smashAggregations: " + this.smashAggregations);
		System.out.println("objectFlattener.maxDepth: " + this.maxDepth);
		System.out.println("plugins.load: " + this.loadPlugins);
		System.out.println("classesToIgnore.load: " + this.loadClassesToIgnore);
		System.out.println("inspectorRecognizer.load: " + this.loadInspectorRecognizer);
		System.out.println();
		System.out.println("Plugins loaded: " + this.pluginsList);
		System.out.println("Classes to ignore loaded: " + this.classesToIgnoreList);
		System.out.println("Inspector recognizer loaded: " + this.inspectorRecognizer);
		System.out.println("-------------------------------------------------------------------");				
	}

	/**
	 * This is the method to invoke to smash an object
	 *
	 * @param object The object to be smashed
	 *
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	public void doSmash(Object object) {
		//System.out.println("Depth: " + maxDepth + ", smash aggregations: " + smashAggregations);
		currentDepth = 0;
		smash(object);
	}

	/**
	 * This is the method to invoke to smash a primitive type
	 *
	 * @param primitiveType The primitive type to be smash
	 *
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	public void doSmash(boolean primitiveType) throws IllegalArgumentException, IllegalAccessException, InvocationTargetException {
		smashPrimitiveType(new Boolean(primitiveType));
	}

	/**
	 * This is the method to invoke to smash a primitive type
	 *
	 * @param primitiveType The primitive type to be smash
	 *
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	public void doSmash(byte primitiveType) throws IllegalArgumentException, IllegalAccessException, InvocationTargetException {
		smashPrimitiveType(new Byte(primitiveType));
	}

	/**
	 * This is the method to invoke to smash a primitive type
	 *
	 * @param primitiveType The primitive type to be smash
	 *
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	public void doSmash(char primitiveType) throws IllegalArgumentException, IllegalAccessException, InvocationTargetException {
		smashPrimitiveType(new Character(primitiveType));
	}

	/**
	 * This is the method to invoke to smash a primitive type
	 *
	 * @param primitiveType The primitive type to be smash
	 *
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	public void doSmash(short primitiveType) throws IllegalArgumentException, IllegalAccessException, InvocationTargetException {
		smashPrimitiveType(new Short(primitiveType));
	}

	/**
	 * This is the method to invoke to smash a primitive type
	 *
	 * @param primitiveType The primitive type to be smash
	 *
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	public void doSmash(int primitiveType) throws IllegalArgumentException, IllegalAccessException, InvocationTargetException {
		smashPrimitiveType(new Integer(primitiveType));
	}

	/**
	 * This is the method to invoke to smash a primitive type
	 *
	 * @param primitiveType The primitive type to be smash
	 *
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	public void doSmash(long primitiveType) throws IllegalArgumentException, IllegalAccessException, InvocationTargetException {
		smashPrimitiveType(new Long(primitiveType));
	}

	/**
	 * This is the method to invoke to smash a primitive type
	 *
	 * @param primitiveType The primitive type to be smash
	 *
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	public void doSmash(float primitiveType) throws IllegalArgumentException, IllegalAccessException, InvocationTargetException {
		smashPrimitiveType(new Float(primitiveType));
	}

	/**
	 * This is the method to invoke to smash a primitive type
	 *
	 * @param primitiveType The primitive type to be smash
	 *
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	public void doSmash(double primitiveType) throws IllegalArgumentException, IllegalAccessException, InvocationTargetException {
		smashPrimitiveType(new Double(primitiveType));
	}

	/* ---------------------------- Private Methods ---------------------------- */	

	
	/**
	 * This method is used to handle primitive values, it is necessary because wrapped primitive types are different from primitive variables.
	 * Objects can be null, so we cannot save their representation but we have to save 
	 * their inspector method (and this is done by smashPrimitiveWrapper).
	 * To simplify smash operations primitive types are wrapped.
	 */
	private void smashPrimitiveType( Object primitiveWrapped ){
		if (updateCounter(INCREMENT)) {
			//System.out.println("smash -> reached max maxDepth ");
			//if ( primitiveWrapped != null )
			//	System.out.println(primitiveWrapped.getClass()+" "+primitiveWrapped);
			
			return;
		}

		dataHandler.add(primitiveWrapped);
		updateCounter(DECREMENT);
		
		
	}
	
	
	private void smashPrimitiveWrapper( Object object ){
		Method meth = null;
		
		/*
		if ( updateCounter( INCREMENT ) )
			return;
		*/
		try {
			if ( object instanceof Integer )
				meth = object.getClass().getMethod("intValue");
			else if ( object instanceof Double )
				meth = object.getClass().getMethod("doubleValue");
			else if ( object instanceof Boolean )
				meth = object.getClass().getMethod("booleanValue");
			else if ( object instanceof Byte )
				meth = object.getClass().getMethod("byteValue");
			else if ( object instanceof Character )
				meth = object.getClass().getMethod("charValue");
			else if ( object instanceof Float )
				meth = object.getClass().getMethod("floatValue");
			else if ( object instanceof Long )
				meth = object.getClass().getMethod("longValue");
			else if ( object instanceof Short )
				meth = object.getClass().getMethod("shortValue");
			else if ( object instanceof String ){
				String currentObj = (String)object;
				currentObj = currentObj.replace("\"","\\\"");
				currentObj = currentObj.replace("\\","\\\\");
				currentObj = currentObj.replace("\n","\\n");
				currentObj = currentObj.replace("\r","\\r");
				//System.out.println("String :"+currentObj);
				object = currentObj;
				meth = object.getClass().getMethod("toString");
			}
				
			
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if ( meth == null )
			System.out.println("NullMethod for "+object.getClass());
		
		dataHandler.goDown( meth );
		dataHandler.add(object);
		dataHandler.goUp();
		
		//updateCounter(DECREMENT);
	}
	
	/**
	 * This method is used to tread a generic <code>java.lang.Object</code>.
	 *
	 * @param object The <code>java.lang.Object</code> to be smash
	 *
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	private void smash(Object object) {
		/*
		if (object != null) {
			System.out.println(getClass().getName() + ": Flattening " + object.getClass().getName() + " -> " + object);
		} else {
			System.out.println(getClass().getName() + ": Flattening -> " + object);
		}
		*/
		
		// If the max maxDepth has been reached than return
		if (updateCounter(INCREMENT)) {
			//System.out.println("smash -> reached max maxDepth ");
			//if ( object != null )
			//	System.out.println(object.getClass()+" "+object);
			return;
		}

		if (object == null) {	
			dataHandler.add(object); 
			
			updateCounter(DECREMENT);
			return;			
		}
		
		// Check if the current object has been previusly elaborated
		if (elaboratedObjects.contains(object)) {
			//System.out.println("PRESENTE");
			updateCounter(DECREMENT);
			return;
		} else {
			if (!isPrimitiveType(object))
			elaboratedObjects.add(object);
		}
				
		// Contains the java.lang.Class of the object 
		Class objectClass = object.getClass();
		
		

		// First "skimming" of the object, in those following IF will be
		// considered particulars object
		// java.lang.Class object
		if (isIgnoredType(object)) {
			// The counter will be drecremented
			updateCounter(DECREMENT);

			return;
		}
		
		
		//System.out.println("smash -> managing: " + objectClass + ", current maxDepth: " + currentDepth);
		// If the object is a primitive or a primitive array the method will return
		if (isPrimitiveType(object)) {
			// Add the object to the Handler
			// dataHandler.add(object);
			smashPrimitiveWrapper( object );
			
			// The counter will be drecremented
			updateCounter(DECREMENT);
			
			return;
		}
		
		
		
		//FIXME: remove this is not good
		/*if ( object.getClass().toString().startsWith("java") ){
			updateCounter(DECREMENT);
			return;
		}*/
		
		// Test if is a primitive array		
		if (isPrimitiveArray(object)) {
			if (smashAggregations){
				dataHandler.goDownArray();
				// smash primitive array
				if(objectClass.getComponentType().getName().equals("boolean")) {				
					smash((boolean[]) object);				
				}
				else if(objectClass.getComponentType().getName().equals("byte")) {
					smash((byte[]) object);
				}
				else if(objectClass.getComponentType().getName().equals("char")) {
					smash((char[]) object);
				}				
				else if(objectClass.getComponentType().getName().equals("short")) {
					smash((short[]) object);
				}
				else if(objectClass.getComponentType().getName().equals("int")) {
					smash((int[]) object);
				}
				else if(objectClass.getComponentType().getName().equals("long")) {
					smash((long[]) object);
				}
				else if(objectClass.getComponentType().getName().equals("float")) {
					smash((float[]) object);
				}
				else if(objectClass.getComponentType().getName().equals("double")) {
					smash((double[]) object);
				}
				dataHandler.goUpArray();
			}
			updateCounter(DECREMENT);
			return;
		}

		// array of java.lang.Object		
		if (objectClass.isArray()) {
			if (!smashAggregations) {
				//System.out.println("smash -> ignoring array: " + objectClass);
				// The counter will be drecremented
				//TODO: Fabrizio: i decremented before return so don't decrement here
				//updateCounter(DECREMENT);
			} else {				
				//Flatten the array
				
				smash((Object[]) object);
			}
			
			updateCounter(DECREMENT);
			return;
		}
		
		// java.util.Map object
		if (object.getClass().getName().equals("java.util.HashMap")) {
			
			if(smashAggregations) {		
				smash((HashMap) object);
			}
			
			updateCounter(DECREMENT);
			return;
		}		

		// test if there is a plugin to manage this object
		if (isPluginType(object)) {			
			// get the corrispondent plugin name for the current object 
			Plugin plugin = (Plugin) pluginsList.get(objectClass.getName());			
			// smash
			smash(plugin.smash(object));			
			
			updateCounter(DECREMENT);
			return;
		}				
		
		// Getting the methods and the filds of the object
		Method[] methods = new Method[0];
		

		// It contains the effective number of methods to be analyzed
		int effectiveMethods = 0;

		// Test if the object contains some inner class'
		if ( ! ignoreInnerClasses && objectClass.getName().indexOf('$') > 0) {
			// Getting all the interfaces that implemented by the object
			Class[] interfaces = objectClass.getInterfaces();

			// It contains the amount of methods contained into all the interfaces
			int countMethods = 0;

			for (int i = 0; i < interfaces.length; i++) {
				countMethods += interfaces[i].getMethods().length;
			}

			// Thi variable is used to check is the methods array has been alredy created
			boolean isFirstTime = true;

			// Loop on all the interfaces							
			for (int i = 0; i < interfaces.length; i++) {
				//System.out.println(getClass().getName() + ": Examing interfaces[" + i + "]: " + interfaces[i].getName());
				// If an interface is java.util.Enumeration I'll use particular method to manage it
				if (interfaces[i].getName().equals("java.util.Enumeration") && smashAggregations) {
					smash((Enumeration) object);
				}
				// If an interface is java.util.Collection I'll use particular method to manage it
				else if (interfaces[i].getName().equals("java.util.Collection") && smashAggregations) {
					smash((Collection) object);
				}
				// If an interface is java.util.Map I'll use particular method to manage it
				else if (interfaces[i].getName().equals("java.util.Map") && smashAggregations) {					
					smash((Map) object);
				}
				// If an interface is java.util.HashMap I'll use particular method to manage it
				else if (interfaces[i].getName().equals("java.util.HashMap") && smashAggregations) {
					smash((HashMap) object);
				}
				// Making an array that contains all the methods of all the interfaces
				
				 
				else {
					// Set the methods size only one time
					if (isFirstTime) {
						methods = new Method[countMethods];
						isFirstTime = false;
					}

					for (int j = 0; j < interfaces[i].getMethods().length; j++) {
						methods[effectiveMethods++] = interfaces[i].getMethods()[j];
					}
				}
			}

			// The object doesn't contains any inner class than it will be treated with common methods											
		} else {
			methods = objectClass.getMethods();
			
			effectiveMethods = methods.length;
		}
		
		
		
		
		// Loop on all the methods found
		for (int i = 0; i < effectiveMethods; i++) {
			//System.out.println("smash -> Examing methods[" + i + "]: " + ((Method) methods[i]).getName());
			// Second "skimming" of the object, will be treated methods without parameters 						
			if (methods[i].getParameterTypes().length == 0 && !isIgnoredMethod(methods[i]) ) {
				//System.out.println(getClass().getName() + ": current method: " + methods[i].getReturnType() + " " + methods[i].getName() + "()");
				if (inspectorRecognizer.recognize(object, methods[i])) {
					flattenMethod(object, methods[i]);
				}
		
			}
		}
		
		
		
		//flattenFields( object );
		
		
		// The counter will be drecremented
		updateCounter(DECREMENT);				
	}

	/**
	 * This method flatten all fields of an Object.
	 * 
	 * @param object
	 */
	/*private void flattenFields(Object object) {
		
		List<Field> fields = FieldsRetriever.getFieldsMeth(object,fieldsFilter);
		
		for ( Field field : fields ){
			flattenField(object,field);
		}
		
	}*/

	/**
	 * This method is used to tread the object
	 * <code>java.util.Enumeration</code>.
	 *
	 * @param enumeration The object <code>java.util.Enumeration</code> to be
	 * 		  smash
	 *
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	private void smash(Enumeration enumeration) {
		if (enumeration == null) {	
			dataHandler.add(enumeration); 
			return;			
		}
		
		//System.out.println("smash(Enumeration)");
		int i = 0;		
		while (enumeration.hasMoreElements()) {
			dataHandler.goDownArray(i++);
			smash(enumeration.nextElement());
			dataHandler.goUpArray();			
		}
	}

	/**
	 * This method is used to tread the object
	 * <code>java.util.Collection</code>.
	 *
	 * @param collection The object <code>java.util.Collection</code> to be
	 * 		  smash
	 *
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	private void smash(Collection collection) {
		if (collection == null) {		
			dataHandler.add(collection); 
	
			return;			
		}
		
		//System.out.println("smash(Collection)");				
		smash(collection.toArray());		
	}
	
	/**
	 * This method is used to tread the object
	 * <code>java.util.Map</code>.
	 *
	 * @param map The object <code>java.util.Map</code> to be
	 * 		  smash
	 *
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	private void smash(Map map) {
		if (map == null) {	
			dataHandler.add(map); 
		
			return;			
		}
		
		//System.out.println("smash(Map)");				
		smash(map.values());		
	}	
	
	/**
	 * This method is used to tread an array of <code>java.lang.Object</code>.
	 *
	 * @param array array of <code>java.lang.Object</code> to be smash
	 *
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	private void smash(Object[] array) {
		if (array == null) {
			dataHandler.add(array); 
			
			return;			
		}
		
		dataHandler.goDownArray();				
		//System.out.println("smash(Object[])");		
		for (int i = 0; i < array.length; i++) {
			dataHandler.goDownArray(i);
			smash(array[i]);
			dataHandler.goUpArray();	
		}		
		dataHandler.goUpArray();
	}

	/**
	 * This method is used to tread an array of <code>boolean</code>.
	 *
	 * @param array array of <code>boolean</code> to be smash
	 *
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	private void smash(boolean[] array) {
		if (array == null) {	
			dataHandler.add(array); 
			return;			
		}
		
		//System.out.println("smash(Object[])");
		for (int i = 0; i < array.length; i++) {
			dataHandler.goDownArray(i);
			smash(new Boolean(array[i]));			
			dataHandler.goUpArray();
		}		
	}
	
	/**
	 * This method is used to tread an array of <code>byte</code>.
	 *
	 * @param array array of <code>byte</code> to be smash
	 *
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	private void smash(byte[] array) {
		if (array == null) {	
			dataHandler.add(array); 
			return;			
		}
		
		//System.out.println("smash(Object[])");
		for (int i = 0; i < array.length; i++) {
			dataHandler.goDownArray(i);
			smash(new Byte(array[i]));
			dataHandler.goUpArray();
		}
	}
	
	/**
	 * This method is used to tread an array of <code>char</code>.
	 *
	 * @param array array of <code>char</code> to be smash
	 *
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	private void smash(char[] array) {
		if (array == null) {	
			dataHandler.add(array); 
			return;			
		}
		
		//System.out.println("smash(Object[])");
		for (int i = 0; i < array.length; i++) {
			dataHandler.goDownArray(i);
			smash(new Character(array[i]));
			dataHandler.goUpArray();
		}
	}

	/**
	 * This method is used to tread an array of <code>short</code>.
	 *
	 * @param array array of <code>short</code> to be smash
	 *
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	private void smash(short[] array) {
		if (array == null) {	
			dataHandler.add(array); 
		
			return;			
		}
		
		//System.out.println("smash(Object[])");
		for (int i = 0; i < array.length; i++) {
			dataHandler.goDownArray(i);
			smash(new Short(array[i]));
			dataHandler.goUpArray();
		}		
	}
		
	/**
	 * This method is used to tread an array of <code>int</code>.
	 *
	 * @param array array of <code>int</code> to be smash
	 *
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	private void smash(int[] array) {
		if (array == null) {	
			dataHandler.add(array); 
		
			return;			
		}
		
		//System.out.println("smash(Object[])");
		for (int i = 0; i < array.length; i++) {
			dataHandler.goDownArray(i);
			smash(new Integer(array[i]));
			dataHandler.goUpArray();
		}
	}	
	
	/**
	 * This method is used to tread an array of <code>long</code>.
	 *
	 * @param array array of <code>long</code> to be smash
	 *
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	private void smash(long[] array) {
		if (array == null) {	
			dataHandler.add(array); 
		
			return;			
		}
		
		//System.out.println("smash(Object[])");
		for (int i = 0; i < array.length; i++) {
			dataHandler.goDownArray(i);
			smash(new Long(array[i]));
			dataHandler.goUpArray();
		}
	}	
	
	/**
	 * This method is used to tread an array of <code>float</code>.
	 *
	 * @param array array of <code>float</code> to be smash
	 *
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	private void smash(float[] array) {
		if (array == null) {
			dataHandler.add(array); 
			
			return;			
		}
		
		//System.out.println("smash(Object[])");
		for (int i = 0; i < array.length; i++) {
			dataHandler.goDownArray(i);
			smash(new Float(array[i]));
			dataHandler.goUpArray();
		}
	}	
	
	/**
	 * This method is used to tread an array of <code>double</code>.
	 *
	 * @param array array of <code>double</code> to be smash
	 *
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	private void smash(double[] array) {
		if (array == null) {			
			dataHandler.add(array); 
			return;			
		}
		
		//System.out.println("smash(Object[])");
		for (int i = 0; i < array.length; i++) {
			dataHandler.goDownArray(i);
			smash(new Double(array[i]));
			dataHandler.goUpArray();
		}
	}	
	
	/**
	 * This method is used to flat a <code>java.lang.Object</code> with it's
	 * given <code>java.lang.reflect.Method</code>.
	 *
	 * @param object The <code>java.lang.Object</code> to be flat
	 * @param method The <code>java.reflect.Method</code> of the
	 * 		  <code>java.lang.Object</code>
	 *
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	private void flattenMethod(Object object, Method method) throws IllegalArgumentException {
		dataHandler.goDown(method);
		//System.out.println(object.toString()+" "+object.getClass());
		try {
			//This is not useful because in checking phase we treat primitive and object the same way
			//Object returnValue = method.invoke(object, (Object[])null); 
			//doSmash(returnValue,method.getReturnType());
			smash(method.invoke(object, (Object[])null));
		} catch (IllegalAccessException e) {			
			// Do not touch!			
		} catch (InvocationTargetException e) {
			// Do not touch!
		/*
		 * Uncommment following lines if is Used FieldModifiedAspect with thrwo RuntimeException activated
		} catch ( RuntimeException e ) {
		 	//This must be used if FieldModifiedAspect with throw RuntimeException is used
			if ( e.getMessage().equals("OFMOD"))
				updateCounter(DECREMENT);
			else
				throw e;
		*/
		}
		
		dataHandler.goUp();
	}

	/**
	 * This method is used to flat a <code>java.reflect.Field</code>.
	 *
	 * @param object The <code>field</code> owner
	 * @param field The <code>java.reflect.Field</code> to be flat
	 *
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	private void flattenField(Object object, Field field) {
		dataHandler.goDown(field);
		//System.out.println("field "+field.getName());
		try {
			field.setAccessible(true);
			doSmash(field.get(object),field.getType());
		} catch (IllegalAccessException e) {			
			//e.printStackTrace();
		}		
			
		dataHandler.goUp();
	}

	/**
	 * It increments the counter and return <code>true</code> if the set maxDepth
	 * has been reached, <code>false</code> otherwise.
	 *
	 * @param incrementOrDecrement If <code>true</code> the counter will be
	 * 		  increment by one, if <code>false</code> will be decremented by
	 * 		  one.
	 *
	 * @return <code>boolean</code>
	 */
	private boolean updateCounter(boolean incrementOrDecrement) {
		if (!incrementOrDecrement) {
			currentDepth--;

			//System.out.println("updateCounter -> decrement counter: " + currentDepth);
		}

		// First test if the chosen maxDepth has been reached
		if (currentDepth >= maxDepth) {
			//System.out.println("Chosen maxDepth has been reached");
			return true;
		} else if (incrementOrDecrement) {
			currentDepth++;

			//System.out.println("updateCounter -> increment counter: " + currentDepth);			
		}

		return false;
	}

	/**
	 * <pre>
	 * Returns <code>true</code> if the object is one of the following types:
	 * - String
	 * - array of primitive types
	 * - object that rapresent primitive types: Integer, Boolean, Char, etc
	 * it returns <code>false</code> otherwise.
	 * </pre>
	 *
	 * @param object The object to test
	 *
	 * @return <code>boolean</code>
	 */
	private boolean isPrimitiveType(Object object) {
		// Contains the java.lang.Class of the object 
		Class objectClass = object.getClass();

		// java.lang.String object		
		if (objectClass.getName().equals("java.lang.String")) {
			return true;
		}
		// Test if object is a rapresentation of a primitive type
		else if (objectClass.getName().equals("java.lang.Boolean")) {
			return true;
		} else if (objectClass.getName().equals("java.lang.Character")) {
			return true;
		} else if (objectClass.getName().equals("java.lang.Byte")) {
			return true;
		} else if (objectClass.getName().equals("java.lang.Short")) {
			return true;
		} else if (objectClass.getName().equals("java.lang.Integer")) {
			return true;
		} else if (objectClass.getName().equals("java.lang.Long")) {
			return true;
		} else if (objectClass.getName().equals("java.lang.Float")) {
			return true;
		} else if (objectClass.getName().equals("java.lang.Double")) {
			return true;
		}

		return false;
	}
	
	/**
	 * <pre>
	 * Returns <code>true</code> if the object a primitive array:
	 * it returns <code>false</code> otherwise.
	 * </pre>
	 *
	 * @param object The object to test
	 *
	 * @return <code>boolean</code>
	 */	
	public boolean isPrimitiveArray(Object object) {
		// Contains the java.lang.Class of the object 
		Class objectClass = object.getClass();
		// Test if is a primitive array
		if (objectClass.isArray() && objectClass.getComponentType().isPrimitive()) {
			return true;
		}
		return false;				
	}

	/**
	 * <pre>
	 * Returns <code>true</code> if the object is one of the following types:
	 *  - java.labg.Class
	 * returns <code>false</code> otherwise.
	 *  </pre>
	 *
	 * @param object The object to test
	 *
	 * @return <code>boolean</code>
	 */
	public boolean isIgnoredType(Object object) {
		// Contains the java.lang.Class of the object 
		
		Class objectClass = object.getClass();
		
		if ( isIgnoredClassType( objectClass ) ){
			//System.out.println("Ignored: "+object);
			//Thread.dumpStack();
			return true;
		} else if ( objectClass.getName().equals("java.lang.Class")) {
			//By default the class java.lang.Class is ignored
			return true;
		}
		return false;
		
	}
	
	private boolean isIgnoredClassType(Class objectClass){
		boolean ignoreClass = false;
		
		if ( objectClass == null ){
			return false;
		}
	
		String value = (String)classesToIgnoreList.get(new String(objectClass.getName()));
		ignoreClass = new Boolean(value).booleanValue();
		if ( ignoreClass )
			return true;
		
		Class superClass = objectClass.getSuperclass();
		if ( superClass != Object.class )
			ignoreClass = isIgnoredClassType( superClass );
		if ( ignoreClass )
			return true;
		
		Class[] implemented = objectClass.getInterfaces();
		int len = implemented.length;
		
		//check if superClasses have to be ignored
		for ( int i = 0; i < len ; ++i ){
			ignoreClass = isIgnoredClassType( implemented[i]);
			if ( ignoreClass )
				return true;
		}
		
		return false;
	}

	/**
	 * <pre>
	 * Returns <code>true</code> if the method is one of the following types:
	 *  - getClass
	 * returns <code>false</code> otherwise.
	 *  </pre>
	 *
	 * @param method The method to test
	 *
	 * @return
	 */
	private boolean isIgnoredMethod(Method method) {		 		
		// By default the method those methods are ignored
		if (method.getName().equals("getClass")) {
			return true;
		}
		
		return false;
	}
	
	/**
	 * Test if the <code>object</code> has to be managed with its specific
	 * plugin
	 *
	 * @param object
	 *
	 * @return <code>boolean</code>
	 */
	private boolean isPluginType(Object object) {
		if (pluginsList.containsKey(object.getClass().getName())) {
			return true;
		}

		return false;
	}	
	
	private void loadProperties() {
		// Load properties form file
		PropertiesLoader pl = PropertiesLoader.getInstance();

		Hashtable properties = pl.getObjectFlattenerProperties();
		
	    // Get the configuratios paths
/*	    this.classPath = (String) properties.get("classPath");
		this.objectFlattenerPath = (String) properties.get("objectFlattenerPath"); 			 
		this.confPath = (String) properties.get("confPath");
		this.pluginsPath = (String) properties.get("pluginsPath");
	    this.inspectorRecognizersPath = (String) properties.get("inspectorRecognizersPath");		   		    		    		   
*/		    
	    // Get the object flattener properties
	    try {
	        this.maxDepth = Integer.parseInt((String) properties.get("objectFlattener.maxDepth"));		        
	    } catch (Exception e) {
	        e.printStackTrace();
	        this.maxDepth = Integer.parseInt((String) pl.getObjectFlattenerDefaultProperties().get("objectFlattener.maxDepth"));				
		    System.out.println("Default max depth value will be used");		        
	    }
	    
	    
	    this.ignoreInnerClasses = Boolean.valueOf((String) properties.get("objectFlattener.ignoreInnerClasses")).booleanValue(); 
        				        
        this.smashAggregations = Boolean.valueOf((String) properties.get("objectFlattener.smashAggregations")).booleanValue();
		
		this.loadClassesToIgnore = Boolean.valueOf((String) properties.get("classesToIgnore.load")).booleanValue();
		
		this.loadPlugins = Boolean.valueOf((String) properties.get("plugins.load")).booleanValue();
					
	    this.loadInspectorRecognizer = Boolean.valueOf((String) properties.get("inspectorRecognizer.load")).booleanValue();	    	    	    
	    
		if (this.loadPlugins) {		    
			this.pluginsList = pl.getPlugins();				
		}
			
		if (this.loadClassesToIgnore) {
		    this.classesToIgnoreList = pl.getClassesToIgnoreList();
		}
			
		if (this.loadInspectorRecognizer) {				 
			this.inspectorRecognizer = pl.getInspectorRecognizer();
		}


		
		this.fieldsFilter = (FieldFilter) properties.get("fieldsFilter");
	}
	
	/* ------------------------- Public Methods ------------------------- */

	/**
	 * Returns the maximum maxDepth of the iteration.
	 *
	 * @return <code>int</code>
	 */
	public int getMaxDepth() {
		return maxDepth;
	}

	/**
	 * Set the maximum maxDepth of the iteration.
	 *
	 * @param maxDepth
	 */
	public void setMaxDepth(int depth) {
		this.maxDepth = depth;
	}

	/**
	 * If <code>true</code> an array will be smashed, otherwise it will not.
	 *
	 * @param smashAggregation
	 */
	public void setSmashAggregations(boolean smashAggregation) {
		this.smashAggregations = smashAggregation;
	}

	/**
	 * Return <code>true</code> if the array smashing is acitved,
	 * <code>false</code> otherwise.
	 *
	 * @return <code>boolean</code>
	 */
	public boolean isSmashAggregations() {
		return smashAggregations;
	}

	/**
	 * Set the <code>Handler</code> that must be used.
	 *
	 * @param dataHandler
	 */
	public void setDataHandler(Handler dataHandler) {
		this.dataHandler = dataHandler;
	}

	/**
	 * Returns the current <code>Handler</code> containing the structure of
	 * the data smashed.
	 *
	 * @return <code>Handler</code>
	 */
	public Handler getDataHandler() {
		return dataHandler;
	}

	/**
	 * This method smash an objectfor which we know the type, this is the only way to handle
	 * primitive types in a correct manner.
	 *  
	 * @param object
	 * @param objClass
	 */
	public void doSmash(Object object, Class objClass) {
		if ( objClass.isPrimitive() )
			smashPrimitiveType(object);
		else
			smash(object);
		
	}

	
		
}
