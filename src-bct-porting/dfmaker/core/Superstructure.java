package dfmaker.core;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.Map.Entry;

import conf.EnvironmentalSetter;

import flattener.flatteners.BreadthObjectFlattener;
import flattener.flatteners.ObjectFlattener;

/**
 * This class manage information on a daikon trace file structure.
 * 
 * 
 * @author Fabrizio Pastore [ fabrizio.pastore at gmail dot com ]
 *
 */
public class Superstructure {
		private SortedMap<String,SuperstructureField> structure = 
			new TreeMap<String,SuperstructureField>(new SuperstructureComparator() );
		private String programPointName;
		
		private ArrayList<String> alwaysNotNull = null; 
		
		private SortedMap<String,String> references = new TreeMap<String,String>(new SuperstructureComparator() );
		private SortedMap<String,String> alwaysRef = new TreeMap<String,String>(new SuperstructureComparator() );
		private HashMap<String, String> notExapandable = new HashMap<String, String>();
		
		private static FlattenerWrapper flattenerWrapper = new FlattenerWrapper(); 
		
		private static class FlattenerWrapper {
			private static Integer depth = null;
			
			public int getMaxDepth(){
				if ( depth == null ){
					Hashtable p = flattener.utilities.PropertiesLoader.getInstance().getObjectFlattenerProperties();
					depth = Integer.valueOf( (String) p.get("objectFlattener.maxDepth") );
				}
				return depth;
			}
		}
		
		/**
		 * Constructor, create a superstructure for the passed programpoint.
		 * 
		 * @param programPointName name of the program point
		 */
		public Superstructure ( String programPointName ){
			this.programPointName = programPointName;
		}
		
		/**
		 * Return name of a variable without ":::.." inserted by Daikon
		 * 
		 * @return Name of the variable
		 */
		public String getJavaName( ){
			int pos = programPointName.indexOf(":::");
			return programPointName.substring(0,pos);
		}
		
		
		/**
		 * Add a field to superstructure
		 * @param field
		 */
		public void add (SuperstructureField field) {
			add ( field.getVarName(), field.getVarType(), field.isFakeHash(), field.isArray() );
		}
		
		/**
		 * Add var to superstructure.
		 * Variable type is determined by VarTypeResolver by checking passed value.
		 * If value is null SuperstructureField is set to fakeHash.
		 * If variable was already present representation-type is set to new type only if it is set to hashcode.
		 * If old field fakehash was true and now variable has a value fakehash is reset to false.
		 * 
		 * @param varName
		 * @param value
		 */
		public void add ( String varName, String value ){
			
			if ( value.startsWith("@") ){
				String referenced;
				
				if ( value.length() == 1 )
					referenced = varName.substring(0,varName.lastIndexOf('.'));
				else
					referenced = value.substring(1);
				
				references.put(varName, referenced);
				return;
			}
			
			
			VarTypeResolver.Types type = VarTypeResolver.getType( value );
			boolean isArray = VarTypeResolver.isArray(value);
			
			boolean fakeHash = false;
			if ( value.equals( "null" ) || value.equals("!NULL") )
				fakeHash  = true;
			
			add( varName, type, fakeHash, isArray );
			
		}
		
		/**
		 * Add a field to superstructure.
		 * 
		 * If varName is present do:
		 * if representation-type is hashcode change representation type to new passed type
		 * else maintain current type.
		 * 
		 * If fakeHash is false field is set to be fakehash.
		 * If isArray is true and previous variable was a fakehashed one SuperstructureField.isArray is set to true.
		 * @param varName	variable name
		 * @param type		variable type
		 * @param fakeHash	is a fake hash variable
		 * @param isArray	is an array
		 */
		private void add ( String varName, VarTypeResolver.Types type, boolean fakeHash, boolean isArray ){
			
			SuperstructureField var = structure.get(varName);
			
			
			if ( var == null ){
				var = new SuperstructureField( varName, type, fakeHash, isArray );
			} else {
				
				if ( var.isFakeHash() && ! fakeHash ){
					var.setFakeHash(false);
					var.setArray(isArray);
				}
				
				//check if types are the same
				if ( var.getVarType() != type ){
					
					//hashcode has the lowest importance, set new type
					if ( var.getVarType() == VarTypeResolver.Types.hashcodeType ){
						var.setVarType(type);
						var.setArray(isArray);
					}
					
					//all other types have the same importance
					//TODO: signalate different types for the same variable
					
				}
			}
			structure.put( var.getVarName(), var );
		}

		/**
		 * Returns a set of all fields
		 * 
		 * @return
		 */
		public Set varFieldSet() {
			return structure.entrySet();
		}

		/**
		 * Returns a set of all fields names.
		 * @return
		 */
		public Set varNamesSet() {
			return structure.keySet();
		}
		
		/**
		 * Returns a collection of all fields
		 * @return
		 */
		public Collection<SuperstructureField> varFields() {
			return structure.values();
		}
		
		/**
		 * Check if superstructure contains a field.
		 * 
		 * @param field
		 * @return
		 */
		public boolean contains(SuperstructureField field) {
			return structure.containsKey(field.getVarName());
		}

		/**
		 * Return program point name, with ":::ENTER" or ":::EXIT1"
		 * 
		 * @return
		 */
		public String getProgramPointName() {
			return programPointName;
		}

		public void expandReferences(){
			//if this variable contains always a ref to the same variable do not expand
			//we need only to maintain the information of the reference
			//FIXME: add this information in a registry and use do create hand written invariants
			//like parameter[0].field1.parent == parameter[0]
			filterReferences();
			
			while ( ! references.isEmpty() ){
				//System.out.println(references);
				String referencer = references.firstKey();
				String referenced = references.remove(referencer);
				
				
				
				
				SuperstructureField referrerField = new SuperstructureField(referencer,VarTypeResolver.Types.hashcodeType,false,false);
				structure.put(referencer, referrerField);
				
				List<String> refdChildren = getChildren(referenced);
				for ( String refdChild : refdChildren ){
					String childName = referencer + refdChild.substring( referenced.length() );
					
					//if the referred variable would not have been observed by the flattener don't add
					if ( ! accept(childName) )
						continue;
					
					SuperstructureField refdField = structure.get(refdChild);
					SuperstructureField field = new SuperstructureField ( refdField );
					field.setVarName(childName);
					
					refdField.setReferenced(true);
					
					structure.put(childName, field);
					
				}
				
				
				//add child references
				Iterator referIt = new ArrayList(references.keySet()).iterator();
				while(referIt.hasNext()){
					
					String curRef = (String) referIt.next();
					if ( curRef.startsWith(referenced+".") ){
						String referenceName = curRef.substring(referenced.length());
						String varName = referencer + referenceName;
						
						//System.out.println(varName);
						if ( accept( varName ) ){
							String refTo = references.get(curRef);
							//System.out.println("REF "+varName+" TO "+refTo);
							references.put(varName, refTo);
						}
					}
				}
				
				//handle self references
				if ( referencer.startsWith(referenced+".") ){
					String referenceName = referencer.substring(referenced.length());
					String varName = referencer + referenceName;
					
					if ( accept( varName ) ){
						
						//System.out.println("REF "+varName+" TO "+referenced);
						references.put(varName, referenced);
					}
				}
			}
			
		}
		
		/**
		 * Filter references removing references always present and putting them in alwaysRef, and leaving the others in references
		 * 
		 * @param referencer
		 * @return
		 */
		private void filterReferences() {
			Iterator it = structure.keySet().iterator();
			Iterator refIt = new ArrayList(references.keySet()).iterator();
			
			
			String referencer = null; 
			
			if ( refIt.hasNext() )
				referencer = (String) refIt.next();
			
			while ( it.hasNext() && refIt.hasNext() ){
				String varName = (String) it.next();
				
				
				while ( varName.compareTo(referencer) > 0 && refIt.hasNext() ){
					//System.out.println("Remove "+referencer+" "+references);
					String referenced = references.get(referencer);
					
					//We can leave not expanded the references that always point to the same variable if
					//they are contained in notExpandble map
					//or
					//the notExpandable map is void
					if ( ( notExapandable.size() != 0 )  ){
						if ( notExapandable.containsKey(referencer) && notExapandable.get(referencer).equals(referenced) )
							alwaysRef.put(referencer, references.remove(referencer) );
					}
					
					referencer = (String) refIt.next();
				}
					
				if ( ( varName.equals( referencer ) || varName.startsWith( referencer+"." ) ) && refIt.hasNext() )
						referencer = (String) refIt.next();
				
			}
		}
		

		/**
		 * This method check if a variable name can be accepted. The rule is based on the length used by the flattener.
		 * 
		 * Example:
		 * if Flattener maxDepth is set to 2
		 * parameter[0].myValue					will be accepted
		 * parameter[0].next.myInt.intValue()	will be accepted
		 * parameter[0].friend.element.myValue	will NOT be accepted
		 * 
		 * @param varName
		 * @return
		 */
		private boolean accept(String varName) {
			//FIXME: this must not be done in this way. We cannot depend on flattener configuration files
			//Possible solution: parse all file and count number of DOTS, this is max depth
			int threshold = flattenerWrapper.getMaxDepth();
			//System.out.println("ACCEPT "+varName);
			String wrappersGetters[] = {
					"intValue()",
					"doubleValue()",
					"booleanValue()",
					"floatValue()",
					"byteValue()",
					"charValue()",
					"longValue()",
					"shortValue()"
			};
			
			//do not count getters for wrapped values being in next level
			for ( String wrapperGetter : wrappersGetters ){
				if ( varName.endsWith(wrapperGetter)){
					++threshold;
					break;
				}
			}
			
			
			byte[] chars = varName.getBytes();
			int count = 0;
			for ( int i = chars.length-1; i>=0; --i ){
				if ( chars[i] == '.' )
					++count;
			}
			return ! ( count > threshold );
		}

		private List<String> getChildren(String referenced) {
			ArrayList<String> children = new ArrayList<String>();
			SortedMap<String, SuperstructureField> results = structure.tailMap(referenced);
			Iterator<String> keyIterator = results.keySet().iterator();
			while ( keyIterator.hasNext() ){
				String childName = keyIterator.next();
				if ( ! childName.startsWith(referenced) )
					break;
				children.add(childName);
			}
			return children;
		}

		/**
		 * Return if contains a variable with the given name
		 * 
		 * @param varName
		 * @return
		 */
		public boolean containsVar(String varName) {
			return structure.containsKey(varName);
		}
		
		/**
		 * Return a set with references taht are always true.
		 * 
		 * For instance, if for all executipons of a method parameter[0].data contains the same object as parameter[1].data 
		 * is returned a Set with an entry <parameter[0].data,parameter[1].data>
		 * 
		 * @return
		 */
		public Set<Entry<String, String>> getConstantReferences(){
			return alwaysRef.entrySet();
		}

		public ArrayList<String> getAlwaysNotNull() {
			return alwaysNotNull;
		}

		public void setAlwaysNotNull(ArrayList<String> alwaysNotNull) {
			this.alwaysNotNull = alwaysNotNull;
		}

		public void addProgramPointNotNull(Set<String> methodsNotNull) {
			
			ArrayList<String> notNull = new ArrayList<String>();
	        
	        if ( alwaysNotNull == null )
	        	alwaysNotNull = new ArrayList<String>( methodsNotNull );
	        
	        
			for ( String method : alwaysNotNull ){
	        	if ( methodsNotNull.contains(method) )
	        		notNull.add(method);
	        }
			
			alwaysNotNull = notNull;
		}

		public Set<Entry<String, String>> getReferences() {
			return references.entrySet();
		}

		/**
		 * Set a list of references that if present and always point to the same variable can be not expanded
		 *  
		 * @param references
		 */
		public void setNotExpandableReferences(Set<Entry<String, String>> references) {
			for ( Entry<String, String> reference : references )
				notExapandable.put( reference.getKey(), reference.getValue());
		}

		
		
}
