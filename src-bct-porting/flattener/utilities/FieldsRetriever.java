package flattener.utilities;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.List;

/**
 * 
 * 
 * @author Fabrizio Pastore
 *
 */
public abstract class FieldsRetriever {
	private static Hashtable<Class,List<Field>> classFields = new Hashtable<Class, List<Field>>();
	protected FieldFilter filter;
	
	/**
	 * This class implements a FiledsRetriever that returns all declared fields
	 * 
	 * @author Fabrizio Pastore fabrizio.pastore AT gmail.com
	 *
	 */
	public static class FieldsRetrieverAll extends FieldsRetriever {

		public FieldsRetrieverAll(FieldFilter fieldsFilter) {
			super(fieldsFilter);
		}
	
		public ArrayList<Field> getFieldsInternal(Class objClass) {
			
			if ( objClass == Object.class )
				return new ArrayList<Field>(0);
			
			ArrayList<Field> fields = new ArrayList<Field>();
			
			
			
			for ( Field field : objClass.getDeclaredFields() ){
				if ( filter.accept(field) ) 
					fields.add(field);
			}
			
			return fields;
		}	
	}
	
	
	/**
	 * This FieldsRetriever returns all declared fields whit a getter associated.
	 *  
	 * @author Fabrizio Pastore fabrizio.pastore AT gmail.com
	 *
	 */
	public static class FieldsRetireverGetters extends FieldsRetriever {

		public FieldsRetireverGetters(FieldFilter fieldsFilter) {
			super(fieldsFilter);
		}

		@Override
		protected List<Field> getFieldsInternal(Class objClass) {
			
			
			if ( objClass == Object.class )
				return new ArrayList<Field>(0);
			
			ArrayList<Field> fields = new ArrayList<Field>();
			
			try{
				
				for ( Method method : objClass.getMethods() ){
					String methodName = method.getName(); 
					if ( ! methodName.startsWith("get") && ! methodName.startsWith("is") )
						continue;
					String attrName = methodName.substring(3);
					int attrLen = attrName.length();
					
					if ( attrLen < 1 )
						continue;
					
					String fieldName;
					
					if ( attrLen == 1 )
						fieldName = attrName.toLowerCase();
						
					else	{
						char[] chars = attrName.toCharArray();
						
						
						//LowerCase
						//chars[0] = Character.toLowerCase( chars[0] );
						if ( chars[0] < 97 ){
							chars[0] = (char)( ((int)chars[0]) +32);
						}
						
						fieldName = String.valueOf(chars);
					}
						
						
					try {
						
						Field field = objClass.getDeclaredField(fieldName);
						if ( filter.accept(field) ){ 
							fields.add(field);
						}
							
						} catch (SecurityException e) {

						} catch (NoSuchFieldException e) {

					}

				}
				
			} catch ( Throwable t ) {
				//FIXME: we need to signalate this information
				//the problemis that in some cases a NoClassDefFound can be thrown
				//this can be an interesting information (f.e we can put this class in the list of classes to ignore)
				System.err.println("FLATTENER : error accessing fields");
				t.printStackTrace();
			}
			return fields;
		}
		

		
	}
	
	
	public static class FieldsRetrieverInspectable extends FieldsRetriever {

		public FieldsRetrieverInspectable(FieldFilter fieldsFilter) {
			super(fieldsFilter);
		}

		@Override
		protected List<Field> getFieldsInternal(Class objClass) {
			List<Field> fields = new ArrayList<Field>();
			
			HashSet<Method> meths = new HashSet<Method>();
			for ( Method m : objClass.getMethods() ){
				meths.add( m );
			}
			
			for ( Field field : objClass.getDeclaredFields() ){
				boolean accept = false;
				
				if ( Modifier.isPublic( field.getModifiers() ) ) {
					//Public fields are accepted
					accept = true;
				} else {
					//Private fields are accepted only if they have a getter associated with them
					char[] chars = field.getName().toCharArray();
					
					if ( chars[0] < 97 ){
						chars[0] = (char)( ((int)chars[0]) +32);
					}
					
					String getMeth = "get"+String.valueOf(chars);
					if ( meths.contains(getMeth) )
						accept = true;
					else {
						String isMeth = "is"+String.valueOf(chars);
						if ( meths.contains(isMeth) )
							accept = true;
						else
							accept = false;
					}
				}
					
				if ( accept ){
					if ( filter.accept(field) ){ 
						fields.add(field);
					}
				}
				
			}
			
			return fields;
		}
		
	}
	
	
	
	
	public FieldsRetriever(FieldFilter fieldsFilter) {
		this.filter = fieldsFilter; 
	}

	/**
	 * Returns the fields declared in a class.
	 * Fields declared in superclasses MUST NOT be returned
	 * @param classToInspect
	 * @return
	 */
	protected abstract List<Field> getFieldsInternal( Class classToInspect );

	/**
	 * Return a list of field that can be accessed for flattening purposes
	 * 
	 * Synchronization is done over "classFields" Hashtable.
	 * What could happend in the worst case is that many threads recover information on a class. And then write this information sequencially.
	 * But this can be accepted since this cause only few performance problems.
	 * 
	 * Synchronization over FieldsRetriever itself could causeperformance problem on multi threaded programs.
	 * 
	 * Synchronization over the Class of the object being inspected could cause concurrency issues with other methods synchronized on the class. 
	 *
	 * @param classToInspect
	 * @return
	 */
	public List<Field> getFields( Class classToInspect ){
		if ( classToInspect == null )
			return new ArrayList<Field>(0);
		
		List<Field> fields = classFields.get(classToInspect);
		if ( fields != null )
			return  fields;
		
		//Retrieve all fields for this class
		
		//get fields declared at class level
		fields = getFieldsInternal(classToInspect);
		
		//get fields declared in superclass
		Class superClass = classToInspect.getSuperclass(); 
		if ( superClass != null && superClass != Object.class ){
			for ( Field field : getFields(superClass) ){
				if ( ! fields.contains(field) )
					fields.add(field);
			}
		}
		
		
		classFields.put(classToInspect,fields);
		
		return fields;
	}

	/**
	 * Return the Field object with name fieldName if it is acceptable according
	 * to FieldsRetriever and FieldFilter.
	 * Otherwise returns null.
	 * 
	 * @param classToInspect
	 * @param fieldName
	 * @return
	 */
	public Field getField( Class classToInspect, String fieldName ){
		List<Field> fields = getFields(classToInspect);
		
		Field field;
		try {
			field = classToInspect.getDeclaredField(fieldName);
			if ( fields.contains( field ) )
				return field;
		} catch (SecurityException e) {
			
		} catch (NoSuchFieldException e) {
		
		}
		
		
		return null;
	}
	
	/**
	 * Returns the field with the passed name declared in the class if has a getter associated with it
	 * 
	 * The list includes also inherited fields
	 * @param object
	 * @param filter
	 * @return
	 */
//	public Field getDeclaredFieldMeth(Object object, String fieldName) {
//		Class objClass = object.getClass();
//		try {
//			Field field = objClass.getDeclaredField(fieldName);
//
//			//IF A FIELD HAS NOT A GETTER WE CAN HAVE A FALSE POSITIVE BECAUSE IT IS NOT RECORDED
//			if ( field != null){
//				if ( filter.accept(field) )
//					return field;
//				else
//					return null;
//			}
//
//			Class superClass = objClass;
//			while ( ( superClass = superClass.getSuperclass() ) != Object.class ){
//
//				field = superClass.getDeclaredField(fieldName);
//
//				if ( field != null){
//					if ( filter.accept(field) )
//						return field;
//					else
//						return null;
//				}
//			}
//		} catch (SecurityException e) {
//	
//		} catch (NoSuchFieldException e) {
//	
//		}
//		return null;
//	}
	
}