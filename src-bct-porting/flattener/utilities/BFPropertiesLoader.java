package flattener.utilities;

import java.util.Hashtable;

public class BFPropertiesLoader {
	private static BFPropertiesLoader instance;
	private int maxDepth;
	private boolean smashAggregations;
	private boolean loadClassesToIgnore;
	private Hashtable classesToIgnoreList;
	private FieldsRetriever fieldsRetriever;
	
	private BFPropertiesLoader(){
		PropertiesLoader pl = PropertiesLoader.getInstance();
		Hashtable properties = pl.getObjectFlattenerProperties();
		    
	    // Get the object flattener properties
	    try {
	        this.maxDepth = Integer.parseInt((String) properties.get("objectFlattener.maxDepth"));		        
	    } catch (Exception e) {
	        e.printStackTrace();
	        this.maxDepth = Integer.parseInt((String) pl.getObjectFlattenerDefaultProperties().get("objectFlattener.maxDepth"));				
		    System.out.println("Default max depth value will be used");		        
	    }
	    
	    
	    //this.ignoreInnerClasses = Boolean.valueOf((String) properties.get("objectFlattener.ignoreInnerClasses")).booleanValue(); 
        				        
        this.smashAggregations = Boolean.valueOf((String) properties.get("objectFlattener.smashAggregations")).booleanValue();
		
		this.loadClassesToIgnore = Boolean.valueOf((String) properties.get("classesToIgnore.load")).booleanValue();
			
		if (this.loadClassesToIgnore) {
		    this.classesToIgnoreList = pl.getClassesToIgnoreList();
		} else {
			classesToIgnoreList = new Hashtable(0);
		}
			
		
		FieldFilter fieldsFilter = (FieldFilter) properties.get("fieldsFilter");
		
		String retriever = (String) properties.get("fieldsRetrieverConfig");
		
		if ( retriever == null )
			fieldsRetriever = new FieldsRetriever.FieldsRetrieverInspectable( fieldsFilter );
		
		else if ( retriever.toLowerCase().equals("all") )
			fieldsRetriever = new FieldsRetriever.FieldsRetrieverAll( fieldsFilter );
		else if ( retriever.toLowerCase().equals("getters") )	
			fieldsRetriever = new FieldsRetriever.FieldsRetireverGetters( fieldsFilter );
		else
			fieldsRetriever = new FieldsRetriever.FieldsRetrieverInspectable( fieldsFilter );
		
		
	}
	
	public static synchronized BFPropertiesLoader getInstance(){
		if ( instance == null ){
			instance = new BFPropertiesLoader();
		}
		return instance;
	}

	public Hashtable getClassesToIgnoreList() {
		return classesToIgnoreList;
	}


	public boolean isLoadClassesToIgnore() {
		return loadClassesToIgnore;
	}

	public int getMaxDepth() {
		return maxDepth;
	}

	public boolean isSmashAggregations() {
		return smashAggregations;
	}

	/**
	 * Returns a FieldRetriever.
	 * Pay attention the FieldsRetriever is synchronized. A common retriever for all thread is returned, this can slow down systems.
	 * TODO: return a different retriever for every thread
	 * 
	 * @return
	 */
	public FieldsRetriever getFieldsRetriever() {
		return fieldsRetriever;
	}
}
