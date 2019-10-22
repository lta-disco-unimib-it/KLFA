/*
 * Created on 19-ago-2004
 */
package flattener.utilities;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Properties;

import conf.ObjectFlattener;

import flattener.core.InspectorRecognizer;
import flattener.core.Plugin;
import flattener.inspectorRecognizers.DefaultInspectorRecognizer;

/**
 * This class is used for loading the ObjectFlattener's properties.
 * It contains the information related to the configuration's files name and to
 * the packages in which to retrieve the plugins and the inspector recognizers.
 * The class PropertiesLoader also provide to automatically look for the
 * configuration files; they are looked for inside the paths inserted in the
 * environment variable java.class.path.
 * 
 * @author Davide Lorenzoli
 */
public class PropertiesLoader {
    // The default configuration files name
/*    public static String objectFlattenerPropertiesFileName = "objectFlattener.conf";
    public static String inspectorRecognizersListFileName = "inspectorRecognizers.list";
    public static String classesToIgnoreListFileName = "classesToIgnore.list";
    public static String pluginsListFileName = "plugins.list";
 */   
    
    private File propertiesFile;
    private File classPath;
	private static PropertiesLoader intance;
	private Hashtable defaultProperties;
	private Hashtable properties;
	private Hashtable pluginsList;
	private Hashtable plugins;
	private Hashtable classesToIgnoreList;
	private Hashtable inspectorRecognizersList;
	private InspectorRecognizer inspectorRecognizer;
    
    /**
     * Constructor
     */
    private PropertiesLoader() { }
    
    
    public static PropertiesLoader getInstance(){
    	if ( intance == null )
    		intance = new PropertiesLoader();
    	return intance;
    }
    
    public Hashtable getObjectFlattenerDefaultProperties() {
    	if ( defaultProperties == null ){
    		defaultProperties = new Hashtable();

    		// Set the default properties value
    		defaultProperties.put("objectFlattener.maxDepth", "10");
    		defaultProperties.put("objectFlattener.smashAggregations", "false");					
    		defaultProperties.put("classesToIgnore.load", "false");							
    		defaultProperties.put("plugins.load", "false");			    		
    		defaultProperties.put("inspectorRecognizer.load", "false");
    		defaultProperties.put("objectFlattener.smashPublicAttributes", "true");
    	}
        return defaultProperties;
    }
    
    /**
     * It returns the ObjectFlattener configuration's properties stored in
     * the file objectFlattener.conf placed at the path [FLATTENER_HOME]/conf.
     * In the case in which the file is not found the following default values
     * are used:<PRE>
     * - objectFlattener.maxDepth = 10  
     * - objectFlattener.smashAggregations = false  
     * - classesToIgnore.load = false  
     * - plugins.load = false  
     * - inspectorRecognizer.load = false
     * </PRE>
     * @return It returns the ObjectFlattener configuration's properties.
     */
    public Hashtable getObjectFlattenerProperties() {
    	if ( properties == null ){
    		properties = new Hashtable();

    		// Set the default properties value
    		Hashtable defaultProperties = getObjectFlattenerDefaultProperties();

    		try {
    			// Load the properties from file
    			properties = load(ObjectFlattener.getObjectFlattenerPropertiesFileName());

    		} catch (FileNotFoundException e) {            
    			e.printStackTrace();
    			System.out.println("Default properties will be used");
    		} catch (IOException e) {
    			e.printStackTrace();
    			System.out.println("Default properties will be used");
    		}
    		// Get the object flattener properties

    		if (properties == null) {
    			properties = defaultProperties;
    		}

    		FieldFilter fieldsFilter;
    		try {
    			String filterType = ObjectFlattener.getFieldsFilterFileName();
    			
    			if ( filterType != null )
    				fieldsFilter = FieldsFilterFactory.createFilter(filterType);
    			else
    				fieldsFilter = FieldsFilterFactory.createFilter();

    		} catch (IOException e) {
    			// problem during fieldFilter creation
    			fieldsFilter = FieldsFilterFactory.createFilter();
    		}

    		String retriever = (String) properties.get("fieldsRetrieverConfig");
    		if ( retriever == null )
    			retriever = "";
    		
    		properties.put("fieldsFilter", fieldsFilter);
    		properties.put("fieldsRetrieverConfig", retriever);
    	}
		return properties;
    }
    
    /**
     * @return
     */
    public Hashtable getPluginsList() {
        if ( pluginsList == null ){
        	pluginsList = new Hashtable();
        	try {		    
        		pluginsList = load(ObjectFlattener.getPluginsListFileName());				
        	} catch (FileNotFoundException e) {
        		e.printStackTrace();
        		System.out.println("Default plugins will be used.");
        	} catch (IOException e) {
        		e.printStackTrace();
        		System.out.println("Default plugins will be used.");
        	}
        }
		return pluginsList;
    }

	/**
	 * @return
	 */
	public Hashtable getPlugins() {
        
		if ( plugins != null)
        	return plugins;
        	
		Hashtable pluginsList = getPluginsList();				
	    
	    plugins = new Hashtable();
		try {					
			//Load the plugins list
			ClassLoader cl = new ClassLoader();
			Enumeration keys = pluginsList.keys();
			while (keys.hasMoreElements()) {
				String pluginName = (String) keys.nextElement();
				Boolean load = new Boolean((String)pluginsList.get(pluginName));
				if (load.booleanValue()) {
				    Plugin pluginObject = (Plugin) cl.getObject(pluginName);
				    if (pluginObject != null) {
				        plugins.put(pluginObject.getTargetClass().getName(), pluginObject);
				    }
				}
			}			
		} catch (FileNotFoundException fnfe) {
			System.out.println(fnfe.getMessage());
		} catch (IOException ioe) {
			System.out.println(ioe.getMessage());
		}
				
		return plugins;
	}
	
    /**
     * @return
     */
    public Hashtable getClassesToIgnoreList() {
    	if ( classesToIgnoreList != null )
    		return classesToIgnoreList;
    	
        classesToIgnoreList = new Hashtable();
		try {
		    classesToIgnoreList = load(ObjectFlattener.getClassesToIgnoreListFileName());						
		} catch (FileNotFoundException e) {
		    e.printStackTrace();
		    System.out.println("Defalt classes will be ignored.");
		} catch (IOException e) {
		    e.printStackTrace();
		    System.out.println("Defalt classes will be ignored.");
		}
		
		return classesToIgnoreList;
    }
    
    /**
     * @return
     */
    public Hashtable getInspectorRecognizersList() {
    	if ( inspectorRecognizersList != null )
    		return inspectorRecognizersList;
    	
        inspectorRecognizersList = new Hashtable();
		try {				
		    inspectorRecognizersList =  load(ObjectFlattener.getInspectorRecognizersListFileName());			
		} catch (FileNotFoundException e) {
		    System.err.println(e.getMessage());
		    System.out.println("Defalt Inspactor recognizer will be ignored.");
		} catch (IOException e) {
		    e.printStackTrace();
		    System.out.println("Defalt Inspactor recognizer will be ignored.");
		}
		
		return inspectorRecognizersList;
    }
    
	/**
	 * @return
	 */
	public InspectorRecognizer getInspectorRecognizer() {
		if ( inspectorRecognizer != null )
			return inspectorRecognizer;
		
	    Hashtable inspectorRecognizersList = getInspectorRecognizersList();
	    inspectorRecognizer = null;
	    
		try {
			Enumeration keys = inspectorRecognizersList.keys();
			while (keys.hasMoreElements()) {
				String key = (String) keys.nextElement();
				Boolean  load = new Boolean((String) inspectorRecognizersList.get(key));
				if (load.booleanValue()) {
					ClassLoader cl = new ClassLoader();
					inspectorRecognizer = (InspectorRecognizer) cl.getObject(key);					
				} 
			}
		} catch (FileNotFoundException fnfe) {
			System.out.println(fnfe.getMessage());
		} catch (IOException ioe) {
			System.out.println(ioe.getMessage());
		}
		
		if ( inspectorRecognizer == null )
			inspectorRecognizer = new DefaultInspectorRecognizer();
		
		return inspectorRecognizer;
		//return inspectorRecognizer == null ? new DefaultInspectorRecognizer() : inspectorRecognizer;				
	}
    
    // ------------------------------ Private Methods -------------------------
    
    /**
     * @param fileName
     * @return
     * @throws FileNotFoundException
     * @throws IOException
     */
    private Properties load(String fileName) throws FileNotFoundException, IOException {
        Properties properties = new Properties();
        this.propertiesFile = findPropertiesFile(fileName);       
        if (this.propertiesFile == null) {
            throw new FileNotFoundException("[java.class.path]" + File.separator + "conf" + File.separator + fileName);
        }
        FileInputStream inFile = new FileInputStream(this.propertiesFile);
        properties.load( inFile );
        inFile.close();
        return properties;
    }    
    
    /**
     * @param fileName
     * @return
     * @throws FileNotFoundException
     */
    private File findPropertiesFile(String fileName) throws FileNotFoundException {        

        File propertiesFile = new File(fileName);

// In the old versione the file to be loaded was searched in the classpath        
/*    	File propertiesFile = null;
        
        String classPath = (String) System.getProperty("java.class.path");
        java.util.StringTokenizer stringTokenizer = new java.util.StringTokenizer(classPath, java.io.File.pathSeparator);        
        
        String separator = File.separator;                
        
        for (int i=0; stringTokenizer.hasMoreElements(); i++) {
            String currentClassPath = stringTokenizer.nextToken();            
            
            propertiesFile = new File(currentClassPath + File.separator + "flattener" + File.separator + "conf" +File.separator + fileName);
            if (propertiesFile.exists()) {
                this.classPath = new File(currentClassPath);
                break;
            } else {
                propertiesFile = null;
            }
        }                */
        return propertiesFile;        
    }        
}
