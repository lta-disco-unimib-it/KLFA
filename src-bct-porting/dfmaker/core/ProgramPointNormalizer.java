/**
 * 
 */
package dfmaker.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.Vector;

public class ProgramPointNormalizer {
		
		//These fields are used by the normalization algorithm
		//TODO: refactor in another object
		private String parentNull = null;
		private String parentReferencing = null;
		private String parentReference = null;
		protected HashMap<String, Variable> currentPoint;
		//private Superstructure exitSuperstructure;
		//private Superstructure entrySuperstructure;
		private boolean expandReferences;
		private Superstructure ppSuperSructure;
		
		
		ProgramPointNormalizer( boolean expandReferences, HashMap<String, Variable> currentPoint, Superstructure ppSuperstructure ){
			this.expandReferences = expandReferences;
			this.ppSuperSructure = ppSuperstructure;
			this.currentPoint = currentPoint;
		}
		
		
		
	    /**
	     * Normalizes a single program point (Entry, Exit, Object) in respect to
	     * the given super structure.
	     * 
	     */
	    protected Vector<Variable> getNormalizedPoint() {
	        //Vector  = new Vector(0);

	    	Superstructure superStructure = this.ppSuperSructure;
	    	
	    	HashMap<String,Variable> fieldValues = new HashMap<String, Variable>();
	    	
	    	
	    	
	        TreeSet<Variable> normalizedStructure = new TreeSet<Variable>( new VariableComparator() );
	        Set<String> parentNotNull = new TreeSet<String>();
	        
	        fieldValues.clear();
	        
	        //iterate for all program point variables and do normalization
	        Iterator sit = superStructure.varFields().iterator();
	        parentNull = null;
	        parentReferencing = null;
	        
	        SortedMap<String,String> references = new TreeMap<String,String>( new SuperstructureComparator() );
	        
	        while ( sit.hasNext() ){
	        	SuperstructureField field = (SuperstructureField) sit.next();
	        	String varName = field.getVarName();
	        	Variable trackVar = (Variable) currentPoint.get(varName);
	        	//System.out.println(varName);
	        	
	        	
	        	if ( trackVar == null ){
	        		//no value recorded for this variable in this track
	            	//3 possible cases:
	            	//is nonsenscal for this track
	            	//indicate an object and we have to add a fakehash
	            	//is a variable accessible from a parent reference
	            	//
	        		String ref;
	        		if ( ( ref = getReference(field,superStructure) ) != null ){
	        			//it is a ref, remember the referenced field and put the real value later
	        			references.put(field.getVarName(),ref);
	        			continue;
	        		} else {
	        			//object or nonsensical: let getFittitiousVar decide the value
	        			trackVar = getFittitiousVar(field);
	        			
	        			currentPoint.put(field.getVarName(),trackVar);
	        		}
	        	} else {
	        		
	        		String value = trackVar.getValue();
	        		//System.out.println(value);
	        		if ( value.equals("null") ){
	        			parentNull = varName;
	        		} else {
	        			//our parent is not null
	        			if ( varName.contains(".")){
	        				String 	varParent = varName.substring(0, varName.lastIndexOf('.'));
	        				parentNotNull.add(varParent);
	        			}
	        			
	        			
	        			if ( value.startsWith("@") ) {

	        				parentReferencing = varName;
	        				if ( value.length() == 1 )
	        					parentReference = trackVar.getName().substring(0,trackVar.getName().lastIndexOf('.'));
	        				else
	        					parentReference = value.substring(1);
	        				trackVar.setValue(getFakeHash());
	        				//System.out.println(varName+" "+trackVar.getName()+" @"+parentReference);
	        			} else if ( value.equals("!NULL")){
	        				parentNotNull.add(varName);
	        				trackVar = getFittitiousVar(field);
	        			}
	        		}
	        	}
	        	normalizedStructure.add(trackVar);
	        	
	        }
	        
	        if ( expandReferences ){
	        	expandReferences( references, normalizedStructure, currentPoint );
	        }
	        
	        
	        superStructure.addProgramPointNotNull( parentNotNull );
	        
	        
	        
	        return new Vector<Variable> ( normalizedStructure );
	    }


		/**
	     * This method expand iteratively all references in a propgram point.
	     * The iteration is done until all references are expanded.
	     * 
	     * @param references
	     * @param normalizedStructure
	     * @param currentStructure
	     * @param superStructure 
	     */
	    private void expandReferences(SortedMap<String, String> references, TreeSet normalizedStructure, HashMap currentStructure ) {
	    	//System.out.println("EXPAND REF :\n"+references);
	    	
			while ( ! references.isEmpty() ){
				//System.out.println("EXPAND REF "+references);
				Iterator<String> keys = new ArrayList(references.keySet()).iterator();
				
				while ( keys.hasNext() ){
					String referrer = keys.next();
					String referenced = references.get(referrer);
					
					Variable refdVar = (Variable) currentStructure.get(referenced);
					
					if ( refdVar == null ){
						continue;
					}
					
					Variable resVar = new Variable ( referrer, refdVar.getValue(), refdVar.getModified() );
					references.remove(referrer);
					currentStructure.put(referrer, refdVar);
					
					normalizedStructure.add(resVar);
				}
				
			}
			

			
		}

		/**
	     * Return the reference of a field if it is a reference to another field
	     * 
	     * @param field
		 * @param superStructure 
	     * @return
	     */
	    private String getReference(SuperstructureField field, Superstructure superStructure) {
	    	String varName = field.getVarName();
	    	//System.out.println("get ref for "+varName+" "+parentReferencing);
//	    	check if there is a parent that is a reference to a previuosly visited object
	    	if ( parentReferencing != null ){
				//check if it is out parent for real
				int lastIdx = varName.lastIndexOf('.');
				
				if ( lastIdx > 0 && varName.substring(0, lastIdx ).startsWith(parentReferencing) ){
					String tail = varName.substring(parentReferencing.length());
					String refValue = parentReference + tail;
					//return the reference if the new parameter string is in the superstructure
					if ( superStructure.containsVar(refValue) )
						return refValue;
					//System.out.println("NOT PRESENT "+varName+" "+refValue);
					return null;
				}
				else
					parentReferencing = null;
	    	}
	    	
			return null;
		}

	    
	    /**
	     * Return a properly filled Variable in the case the variable is not present in the trace.
	     * 
	     * The variable returned can be
	     * 	a fake hash variable
	     * 	a nonsensical variable
	     * 
	     * @param field
	     * @return
	     */
	    private Variable getFittitiousVar(SuperstructureField field) {
			String varName = field.getVarName();
			
			
			
			//Check if varName is a viable to indicate objects that sometimes become null
	    	if ( field.isFakeHash() ){
	    		//check if there was a null variable before
				if ( parentNull != null ){
					
					int lastIdx = varName.lastIndexOf('.');
					//check if that variable is a parent of varName
					if ( lastIdx > 0 && varName.substring(0, lastIdx ).startsWith(parentNull) ){
						//last null variable is a parent of varName
						return new Variable(varName,"nonsensical",2);
					} else {
						//no, varName can exists
						parentNull = null;
					}
				}
				//no null parents, return a fake hashcode
				return new Variable(varName,getFakeHash(),1);
			}
	    	//it is a real variable that is not present in this contex, set it to nonsensical
			return new Variable( varName, "nonsensical",2);
		}
	    

		private String getFakeHash() {
			Double res = Math.rint(Math.random()*10000000.0);
			return String.valueOf(res.intValue());
		}

	    
	}