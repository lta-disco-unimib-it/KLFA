package flattener.flatteners;


import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Vector;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;


import conf.EnvironmentalSetter;

import flattener.core.Flattener;
import flattener.core.Handler;
import flattener.core.InspectorRecognizer;
import flattener.core.Plugin;
import flattener.handlers.BreadthHandler;
import flattener.handlers.PrintHandler;
import flattener.handlers.RawHandler;
import flattener.inspectorRecognizers.DefaultInspectorRecognizer;
import flattener.utilities.BFPropertiesLoader;
import flattener.utilities.FieldFilter;
import flattener.utilities.FieldsFilterFactory;
import flattener.utilities.FieldsRetriever;
import flattener.utilities.ObjectInfo;
import flattener.utilities.ObjectInfoField;
import flattener.utilities.ObjectInfoMethod;
import flattener.utilities.PropertiesLoader;

/**
 * 
 * @author Fabrizio Pastore fabrizio.pastore AT gmail.com
 *
 */
public class BreadthObjectFlattener implements Flattener {

	private static class FlattenerException extends Exception {
		
	}
	
	private static interface ElaboratedMapInterface {

		public abstract void put(Object key, ObjectInfo value);

		public abstract void clear();

		public abstract boolean containsKey(Object key);

		public abstract Object get(Object key);

	}
	
	
	/**
	 * This inner class traces objects visited, and maintain informations on the path on wich they are visited
	 * 
	 * @author Fabrizio Pastore fabrizio.pastore AT gmail.com
	 *
	 */
	private static class ElaboratedMap implements ElaboratedMapInterface{
		private ArrayList<Object> keys = new ArrayList<Object>();
		private ArrayList<Object> values = new ArrayList<Object>();

		
		/* (non-Javadoc)
		 * @see flattener.flatteners.ElaboratedMapInterface#put(java.lang.Object, flattener.utilities.ObjectInfo)
		 */
		public void put ( Object key, ObjectInfo value ){
			int pos;
			
			if ( ( pos = getPos(key) ) >= 0 ){
				values.set(pos,value);
				return;
			}
			keys.add(key);
			values.add(value);
		}

		/* (non-Javadoc)
		 * @see flattener.flatteners.ElaboratedMapInterface#clear()
		 */
		public void clear() {
			keys.clear();
			values.clear();
		}

		/* (non-Javadoc)
		 * @see flattener.flatteners.ElaboratedMapInterface#containsKey(java.lang.Object)
		 */
		public boolean containsKey(Object key) {
			return getPos(key) >= 0;
		}

		private int getPos(Object key){
			
			for ( int i = 0; i < keys.size(); i++ ){
				if ( keys.get(i) == key )
					return i;
			}
			return -1;
		}
		
		/* (non-Javadoc)
		 * @see flattener.flatteners.ElaboratedMapInterface#get(java.lang.Object)
		 */
		public Object get(Object key) {
			int pos = getPos(key);
			if ( pos < 0 )
				return null;
			return values.get(pos);
		}
	}
	
	private static class ElaboratedMapIdentityHash implements ElaboratedMapInterface {
		private HashMap<Integer,Object> elements = new HashMap<Integer, Object>();
		
		public void clear() {
			elements.clear();
		}

		public boolean containsKey(Object key) {
			return elements.containsKey(System.identityHashCode(key));
		}

		public Object get(Object key) {
			return elements.get(System.identityHashCode(key));
		}

		public void put(Object key, ObjectInfo value) {
			elements.put(System.identityHashCode(key), value);
		}
		
	}
	
	
	private static interface NodeRecorder{

		void recordRootValue(Object object);

		void recordMethod(String string, Object object);

		void record(ObjectInfo objectInfo);
		
		void recordNotNull(ObjectInfo objectInfo);

		void recordReference(ObjectInfo objInfo, ObjectInfo visited);

		void setDataHandler( Handler dataHandler);

		Handler getDataHandler();
	}
	
	private static class NodeRecorderRaw implements NodeRecorder{
		
		private RawHandler dataHandler;
		
		
		
		public NodeRecorderRaw(RawHandler handler) {
			dataHandler = handler;
		}

		/**
		 * Record info stored for an object. The object info can contain data about Methods or Fields.
		 * 
		 * @param objInfo
		 */
		public void record(ObjectInfo objInfo) {
			if ( objInfo instanceof ObjectInfoMethod )
				recordMethod(objInfo.getInspectorFullName(),objInfo.getObject());
			if ( objInfo instanceof ObjectInfoField )
				recordField(objInfo.getInspectorFullName(),objInfo.getObject());
		}

		/**
		 * Record the value of a field.
		 * 
		 * @param fieldPath
		 * @param object
		 */
		private void recordField( String fieldPath, Object object){
			dataHandler.addNode(fieldPath, object);
		}
		
		/**
		 * Record a value returned from a method call.
		 * 
		 * @param methodPath
		 * @param object
		 */
		public void recordMethod(String methodPath, Object object){
			dataHandler.addNode(methodPath+"()", object);
		}
		
		/**
		 * Record the value of the element at pos position in the specified array
		 * 
		 * @param parentPath
		 * @param object
		 * @param pos
		 */
		public void recordArray(String array, Object object, int pos ){
			dataHandler.addNode(array+"["+pos+"]", object);
		}
		
		/**
		 * Record value at th eroot of the tree. Used when is flattened a primitive or a wrapped one.
		 * 
		 * @param object
		 */
		public void recordRootValue( Object object ){
			dataHandler.addNode("", object);
		}
		
		/**
		 * Set the data handler we are using.
		 * @param dataHandler
		 */
		public void setDataHandler(RawHandler dataHandler) {
			this.dataHandler = dataHandler;
		}

		/**
		 * Record a reference from referencing to referenced. 
		 * Use this method when an object can be accessed through two different paths
		 *  
		 * @param referencing
		 * @param referenced
		 */
		public void recordReference( ObjectInfo referencing, ObjectInfo referenced ) {
			
			dataHandler.addNodeRef(referencing.getInspectorFullName(),referenced.getInspectorFullName());
			
		}


		public void setDataHandler(Handler dataHandler) {
			this.dataHandler = (RawHandler) dataHandler;
		}

		public RawHandler getDataHandler() {
			return dataHandler;
		}

		public void recordNotNull(ObjectInfo objectInfo) {
			dataHandler.addNotNull(objectInfo.getInspectorFullName());
		}

				
	}
	
	/**
	 * This class wraps the data handler.
	 * This is done beacuse we use the data handler designed for ObjectFlattener(with depth first visit) in a hack-way
	 * So it is better to encapsulate operations on the handler.
	 *  
	 * @author Fabrizio Pastore fabrizio.pastore AT gmail.com
	 *
	 */
	private static class NodeRecorderXml implements NodeRecorder {

		/** Interface to format output in specified ways */
		private BreadthHandler dataHandler;
		
		public NodeRecorderXml(BreadthHandler handler) {
			dataHandler = handler;
		}
		
		/**
		 * Record info stored for an object. The object info can contain data about Methods or Fields.
		 * 
		 * @param objInfo
		 */
		public void record(ObjectInfo objInfo) {
			if ( objInfo instanceof ObjectInfoMethod )
				recordMethod(objInfo.getInspectorFullName(),objInfo.getObject());
			if ( objInfo instanceof ObjectInfoField )
				recordField(objInfo.getInspectorFullName(),objInfo.getObject());
		}

		/**
		 * Record the value of a field.
		 * 
		 * @param fieldPath
		 * @param object
		 */
		private void recordField( String fieldPath, Object object){
			
			if (fieldPath.startsWith("."))
				dataHandler.goDownField(fieldPath.substring(1));
			else
				dataHandler.goDownField(fieldPath);
			dataHandler.add(object);
			dataHandler.goUp();
		}
		
		/**
		 * Record a value returned from a method call.
		 * 
		 * @param methodPath
		 * @param object
		 */
		public void recordMethod(String methodPath, Object object){
			
			if (methodPath.startsWith("."))
				dataHandler.goDownMethod(methodPath.substring(1));
			else
				dataHandler.goDownMethod(methodPath);

			dataHandler.add(object);
			dataHandler.goUp();
		}
		
		/**
		 * Record the value of the element at pos position in the specified array
		 * 
		 * @param parentPath
		 * @param object
		 * @param pos
		 */
		public void recordArray(String array, Object object, int pos ){
			if ( array.startsWith(".") )
				array = array.substring(1);
			
			dataHandler.goDownField(array+"["+pos+"]");
			dataHandler.add(object);
			dataHandler.goUp();
		}
		
		/**
		 * Record value at th eroot of the tree. Used when is flattened a primitive or a wrapped one.
		 * 
		 * @param object
		 */
		public void recordRootValue( Object object ){
			dataHandler.add(object);
		}
		
		/**
		 * Set the data handler we are using.
		 * @param dataHandler
		 */
		public void setDataHandler(BreadthHandler dataHandler) {
			this.dataHandler = dataHandler;
		}

		/**
		 * Record a reference from referencing to referenced. 
		 * Use this method when an object can be accessed through two different paths
		 *  
		 * @param referencing
		 * @param referenced
		 */
		public void recordReference( ObjectInfo referencing, ObjectInfo referenced ) {
			String referencingName;
			String referencedName = null;
			
			if ( referencing.getInspectorFullName().startsWith("."))
				referencingName = referencing.getInspectorFullName().substring(1);
			else
				referencingName = referencing.getInspectorFullName();
			
			if ( referenced.getInspectorFullName().startsWith("."))
				referencedName = referenced.getInspectorFullName().substring(1);
			else
				referencedName = referenced.getInspectorFullName();
			
			
			dataHandler.goDownField(referencingName);
			dataHandler.addRef(referencedName);
			dataHandler.goUp();
		}

		public void setDataHandler(Handler dataHandler) {
			this.dataHandler = (BreadthHandler) dataHandler;	
		}

		public Handler getDataHandler() {
			return dataHandler;	
		}

		public void recordNotNull(ObjectInfo objectInfo) {
			throw new NotImplementedException();
		}
		
		
	}
	
	/**
	 * This inner class handles children insert /remove operations from the queues used for breadth first visit
	 * 
	 * @author Fabrizio Pastore fabrizio.pastore AT gmail.com
	 *
	 */
	private class NodeHandler {
		private Queue<ObjectInfo> currentLevel = new LinkedList<ObjectInfo>();
		private Queue<ObjectInfo> childrenLevel = new LinkedList<ObjectInfo>();
		private ObjectInfo root;
		
		public void addArrayChildren( Object obj, ObjectInfo parent, int pos ){
			childrenLevel.add( new ObjectInfoField( obj, parent.getInspectorFullName()+"["+pos+"]"  ));
		}
		
		public void addFieldChildren( Object obj, ObjectInfo parent, String name ){
			//System.out.println("added "+name+" to "+parent.getInspectorFullName());
			childrenLevel.add( new ObjectInfoField( obj, parent.getInspectorFullName()+"."+name ) );
		}
		
		public void addMethodChildren( Object obj, ObjectInfo parent, String name ){
			childrenLevel.add( new ObjectInfoMethod( obj, parent.getInspectorFullName()+"."+name ) );
		}
		
		public boolean curLevelEmpty(){
			return currentLevel.isEmpty();
		}
		
		/**
		 * Signalate to the handler that now we work in the next level.
		 * It simply swaps levels.
		 *
		 */
		public void nextLevel(){
			currentLevel.clear();
			Queue aux = currentLevel;
			currentLevel = childrenLevel;
			childrenLevel = aux;
		}

		/**
		 * Clear NodeHandler by cleaning the queues it handles
		 *
		 */
		public void clear() {
			currentLevel.clear();
			childrenLevel.clear();
		}

		public void addRoot(Object object) {
			ObjectInfo element = new ObjectInfoField(object,"");
			root = element;
			currentLevel.add(element);
		}

		public ObjectInfo popFirst() {
			return (ObjectInfo) currentLevel.poll();
		}
		
		public ObjectInfo getRoot() {
			return root;
		}
		
	}
	/* -------------------------- Default settings -------------------------- */	       
    
	
	
	
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
	
	
	private boolean loadClassesToIgnore ;
	
	/** It contains the current maxDepth */
	private int currentDepth = 0;
	
	/** It contains the references of the object aready alaborated */
	private ElaboratedMapInterface elaboratedObjects = new ElaboratedMapIdentityHash();

	/** Contains the name of the classes to be ignored */
	private Hashtable classesToIgnoreList = new Hashtable();

	/** Contains the name of the pluginsList to be used */
	private Hashtable pluginsList = new Hashtable();

	
	private NodeHandler nodeHandler = new NodeHandler();
	
	private NodeRecorderRaw nodeRecorder = null;

	private FieldsRetriever fieldsRetriever;

	
	/* ---------------------------- Constructors ---------------------------- */


	/**
	 * Creates a Flattener that uses the passed data handler
	 * @param handler
	 */
	public BreadthObjectFlattener( RawHandler handler) {
		//inspectorRecognizer = new DefaultInspectorRecognizer();
		nodeRecorder = new NodeRecorderRaw(handler);
		
		EnvironmentalSetter.setObjectFlattenerVariables();
		
		loadProperties();
		
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
		smashFirst(object);
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
		nodeRecorder.recordRootValue(new Boolean(primitiveType));
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
		nodeRecorder.recordRootValue(new Byte(primitiveType));
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
		nodeRecorder.recordRootValue(new Character(primitiveType));
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
		nodeRecorder.recordRootValue((new Short(primitiveType)));
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
		nodeRecorder.recordRootValue((new Integer(primitiveType)));
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
		nodeRecorder.recordRootValue((new Long(primitiveType)));
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
		nodeRecorder.recordRootValue((new Float(primitiveType)));
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
		nodeRecorder.recordRootValue((new Double(primitiveType)));
	}

	/* ---------------------------- Private Methods ---------------------------- */	

	
	/**
	 * Smash a primitive wrapper
	 * 
	 * @param object
	 * @param objInfo
	 */
	private void smashPrimitiveWrapper( Object object, ObjectInfo objInfo ){
		String methName = null;
		

		if ( object instanceof Integer )
			methName = "intValue";
		else if ( object instanceof Double )
			methName = "doubleValue";
		else if ( object instanceof Boolean )
			methName = "booleanValue";
		else if ( object instanceof Byte )
			methName = "byteValue";
		else if ( object instanceof Character ){
			methName = "charValue";
			if ( object.equals('"') ) 
				object = "\\\"";
			else if ( object.equals('\\') )
				object = "\\\\";
			else if ( object.equals('\n') )
				object = "\\n";
			else if ( object.equals('\n') )
				object = "\\r";
		}
		else if ( object instanceof Float )
			methName = "floatValue";
		else if ( object instanceof Long )
			methName = "longValue";
		else if ( object instanceof Short )
			methName = "shortValue";
		else if ( object instanceof String ){
			String currentObj = (String)object;
			currentObj = currentObj.replace("\"","\\\"");
			currentObj = currentObj.replace("\\","\\\\");
			currentObj = currentObj.replace("\n","\\n");
			currentObj = currentObj.replace("\r","\\r");
			//System.out.println("String :"+currentObj);
			object = currentObj;
			methName = "toString";
		}


		nodeRecorder.recordMethod(objInfo.getInspectorFullName()+"."+methName, object);

	}

	/**
	 * Clear flattener memory.
	 *
	 */
	public void clear(){
		nodeHandler.clear();
		elaboratedObjects.clear();
	}
	
	/**
	 * This method is used to tread a generic <code>java.lang.Object</code>.
	 * If no information can be retrieved from the object (because it has fields that cannot be inspected due to flattening rules),
	 * it is added just the value of the System.identityHashCode() to signalate that the object is not null. 
	 * 
	 * @param object The <code>java.lang.Object</code> to be smash
	 *
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	private void smashFirst(Object object) {
		
		nodeHandler.addRoot( object );
		
		do{
			while ( ! nodeHandler.curLevelEmpty() ){
				try {
				ObjectInfo element = nodeHandler.popFirst();
				
				Object currentObj = element.getObject();
				
				//Check if the current object has been previusly elaborated		
				if ( currentObj != null && elaboratedObjects.containsKey(currentObj)) {
					addRef(element);
				} else {
					
					//add to visited objects if not primitive
					if ( currentObj != null && ! isPrimitiveType(object) )
						elaboratedObjects.put(currentObj,element);
					
					smash(currentObj,element);
				}
				
				} catch ( Throwable e ) {
					e.printStackTrace();
					System.err.println("FLATTENER : Error accessing object "+object.getClass()+" EXception "+e.getMessage());
				}
			}
			
			nodeHandler.nextLevel();
			
		} while ( ! updateCounter(INCREMENT) && ! nodeHandler.curLevelEmpty() );
		
		if ( nodeRecorder.getDataHandler().getNodesNumber() == 0 ){
			nodeRecorder.recordNotNull( nodeHandler.getRoot() );		
		}
	}

	private void smash(boolean[] array, ObjectInfo objectInfo){
		if (array == null) {	
			nodeRecorder.record(objectInfo);  
			return;			
		}
		
		for (int i = 0; i < array.length; i++) {
			nodeHandler.addArrayChildren(array[i], objectInfo, i);
		}
	}
	
	private void smash(float[] array, ObjectInfo objectInfo){
		if (array == null) {	
			nodeRecorder.record(objectInfo);  
			return;			
		}
		
		for (int i = 0; i < array.length; i++) {
			nodeHandler.addArrayChildren(array[i], objectInfo, i);
		}
	}
	
	private void smash(char[] array, ObjectInfo objectInfo){
		if (array == null) {	
			nodeRecorder.record(objectInfo);  
			return;			
		}
		
		for (int i = 0; i < array.length; i++) {
			nodeHandler.addArrayChildren(array[i], objectInfo, i);
		}
	}
	
	private void smash(long[] array, ObjectInfo objectInfo){
		if (array == null) {	
			nodeRecorder.record(objectInfo);  
			return;			
		}
		
		for (int i = 0; i < array.length; i++) {
			nodeHandler.addArrayChildren(array[i], objectInfo, i);
		}
	}
	
	private void smash(byte[] array, ObjectInfo objectInfo){
		if (array == null) {	
			nodeRecorder.record(objectInfo);  
			return;			
		}
		
		for (int i = 0; i < array.length; i++) {
			nodeHandler.addArrayChildren(array[i], objectInfo, i);
		}
	}
	
	private void smash(int[] array, ObjectInfo objectInfo){
		if (array == null) {	
			nodeRecorder.record(objectInfo);  
			return;			
		}
		
		for (int i = 0; i < array.length; i++) {
			nodeHandler.addArrayChildren(array[i], objectInfo, i);
		}
	}
	
	private void smash(short[] array, ObjectInfo objectInfo){
		if (array == null) {	
			nodeRecorder.record(objectInfo);  
			return;			
		}
		
		for (int i = 0; i < array.length; i++) {
			nodeHandler.addArrayChildren(array[i], objectInfo, i);
		}
	}
	
	private void smash(double[] array, ObjectInfo objectInfo){
		if (array == null) {	
			nodeRecorder.record(objectInfo);  
			return;			
		}
		
		for (int i = 0; i < array.length; i++) {
			nodeHandler.addArrayChildren(array[i], objectInfo, i);
		}
	}
	
	private void smash(Object object, ObjectInfo objInfo) {
		
		if ( object == null ){
			nodeRecorder.record(objInfo);
			return;
		}
		
		//Contains the java.lang.Class of the object 
		Class objectClass = object.getClass();

		// First "skimming" of the object, in those following IF will be
		// considered particulars object
		if (isIgnoredType(object))
			return;
			

		//Test if is a primitive array		
		if (isPrimitiveArray(object)) {
			if (smashAggregations){   
				if(objectClass.getComponentType() == boolean.class ) {				
					smash((boolean[]) object,objInfo);				
				}
				else if(objectClass.getComponentType() == byte.class ) {
					smash((byte[]) object,objInfo);
				}
				else if(objectClass.getComponentType() == char.class ) {
					smash((char[]) object,objInfo);
				}				
				else if(objectClass.getComponentType() == short.class ) {
					smash((short[]) object,objInfo);
				}
				else if(objectClass.getComponentType() == int.class ) {
					smash((int[]) object,objInfo);
				}
				else if(objectClass.getComponentType() == long.class ) {
					smash((long[]) object,objInfo);
				}
				else if(objectClass.getComponentType() == float.class ) {
					smash((float[]) object,objInfo);
				}
				else if(objectClass.getComponentType() == double.class ) {
					smash((double[]) object,objInfo);
				}
									
			}	
			return;
		}

		// array of java.lang.Object		
		if (objectClass.isArray()) {
			if ( smashAggregations) {
				smash((Object[]) object, objInfo );
			}
			return;
		}

		//System.out.println("smash -> managing: " + objectClass + ", current maxDepth: " + currentDepth);
		// If the object is a primitive or a primitive array the method will return
		if ( isPrimitiveType(object) ) {
			// Add the object to the Handler
			// dataHandler.add(object);
			smashPrimitiveWrapper( object, objInfo );

			return;
		}

		//java.util.Map object
		if (object.getClass() == java.util.HashMap.class ) {

			if( smashAggregations ) {		
				smash((HashMap) object,objInfo);
			}

			return;
		}		

		flattenFields( object, objInfo );
	}




	/**
	 * Add a reference in the data handler instead of shashing the passed object
	 * 
	 * @param currentObj
	 * @throws FlattenerException 
	 */
	private void addRef(ObjectInfo objInfo) throws FlattenerException {
		
		ObjectInfo visited = (ObjectInfo) elaboratedObjects.get(objInfo.getObject());
		
		if ( visited == null ){
			//FIXME trow an exception?
			return;
		}
		nodeRecorder.recordReference( objInfo, visited );
	}


	/**
	 * This method flatten all fields of an Object and add their contents to nodeHandler
	 * 
	 * @param object
	 */
	private void flattenFields(Object object,ObjectInfo objectInfo) {
		
		if ( object == null )
			return;
		
		Class classToInspect = object.getClass();
		List<Field> fields = fieldsRetriever.getFields(classToInspect);
		//List<Field> fields = FieldsRetriever.getFieldsMeth(object,fieldsFilter);
		//List<Field> fields = FieldsRetriever.getFields(object,fieldsFilter);
		
		//If we could not access this object's fields, but this object is not null, wewant to signalate this to the user
		if ( fields.size() == 0 ){
			nodeRecorder.recordNotNull(objectInfo);
			return;
		}
		
		for ( Field field : fields ){
			addField(object,field,objectInfo);
		}
		
	}

	/**
	 * This method is used to treath the object
	 * <code>java.util.Enumeration</code>.
	 *
	 * @param enumeration The object <code>java.util.Enumeration</code> to be
	 * 		  smash
	 *
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	private void smash(Enumeration enumeration, ObjectInfo objInfo) {
		if (enumeration == null) {	
			nodeRecorder.record(objInfo); 
			return;			
		}
		
		//System.out.println("smash(Enumeration)");
		int i = 0;		
		while (enumeration.hasMoreElements()) {
			nodeHandler.addArrayChildren(enumeration.nextElement(), objInfo, i++);
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
	private void smash(Collection collection, ObjectInfo objInfo) {
		if (collection == null) {		
			nodeRecorder.record(objInfo);
			return;			
		}
		
		smash(collection.toArray(),objInfo);		
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
	private void smash(Map map,ObjectInfo objInfo) {
		if (map == null) {	
			nodeRecorder.record(objInfo); 
			return;			
		}
		
		//System.out.println("smash(Map)");				
		smash(map.values(),objInfo);		
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
	private void smash(Object[] array,ObjectInfo objectInfo) {
		if (array == null) {	
			nodeRecorder.record(objectInfo);  
			return;			
		}
		
		for (int i = 0; i < array.length; i++) {
			nodeHandler.addArrayChildren(array[i], objectInfo, i);
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
	private void addMethod(Object object, Method method, ObjectInfo objectInfo) throws IllegalArgumentException {
		
		try {
			nodeHandler.addMethodChildren(method.invoke(object, (Object[])null), objectInfo, method.getName());
		} catch (IllegalAccessException e) {			
			// Do not touch!			
		} catch (InvocationTargetException e) {
			// Do not touch!
		}
		
	}

	/**
	 * This method is used to flat a <code>java.reflect.Field</code>.
	 *
	 * @param object The <code>field</code> owner
	 * @param field The <code>java.reflect.Field</code> to be flat
	 *
	 */
	private void addField(Object object, Field field, ObjectInfo objectInfo) {
		try {
			field.setAccessible(true);
			nodeHandler.addFieldChildren(field.get(object), objectInfo, field.getName() );
		} catch (IllegalAccessException e) {			
			//e.printStackTrace();
		}		
			
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
	public boolean isPrimitiveType(Object object) {
		// Contains the java.lang.Class of the object 
		Class objectClass = object.getClass();

		if ( object.getClass().isPrimitive() )
			return true;
		
		// java.lang.String object		
		if (objectClass == java.lang.String.class ) {
			return true;
		}
		// Test if object is a rapresentation of a primitive type
		else if ( objectClass == java.lang.Boolean.class ) {
			return true;
		} else if (objectClass == java.lang.Character.class ) {
			return true;
		} else if ( objectClass == java.lang.Byte.class ) {
			return true;
		} else if (objectClass == java.lang.Short.class ) {
			return true;
		} else if (objectClass == java.lang.Integer.class ) {
			return true;
		} else if (objectClass == java.lang.Long.class ) {
			return true;
		} else if (objectClass == java.lang.Float.class ) {
			return true;
		} else if (objectClass == java.lang.Double.class ) {
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
	 * 
	 * Returns true if the Object must be ignored beacuse is an istance of a class that must be ignored or that 
	 * implements/extends an interface/class taht must be ignored (as specified in conf file).
	 *  
	 * @param object The object to test
	 *
	 * @return <code>boolean</code>
	 */
	public boolean isIgnoredType(Object object) {
		if ( classesToIgnoreList.size() == 0 )
			return false;
		
		return isIgnoredClassType( object.getClass() );
		
	}
	
	private boolean isIgnoredClassType(Class objectClass){
		boolean ignoreClass = false;
		
		if ( objectClass == null ){
			return false;
		}
		
		String value = (String)classesToIgnoreList.get( objectClass.getName() );
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

		BFPropertiesLoader pl = BFPropertiesLoader.getInstance();
		this.maxDepth = pl.getMaxDepth();
		this.smashAggregations = pl.isSmashAggregations();
		this.loadClassesToIgnore = pl.isLoadClassesToIgnore();
		this.classesToIgnoreList = pl.getClassesToIgnoreList();
		this.fieldsRetriever = pl.getFieldsRetriever();		
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
	 * Set the maximum depth of the iteration.
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
	public void setDataHandler(RawHandler dataHandler) {
		nodeRecorder.setDataHandler( dataHandler );
	}

	/**
	 * Returns the current <code>Handler</code> containing the structure of
	 * the data smashed.
	 *
	 * @return <code>Handler</code>
	 */
	public Handler getDataHandler() {
		return nodeRecorder.getDataHandler();
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
			nodeRecorder.recordRootValue(object);
		else
			smashFirst(object);
		
	}


	public void setDataHandler(Handler dataHandler) {
		//we have to respect the interface but we must use DepthHandler
		throw new UnsupportedOperationException("Cannot set an Handler");
		
	}


	public FieldsRetriever getFieldsRetriever() {
		return fieldsRetriever;
	}
	
		
}
