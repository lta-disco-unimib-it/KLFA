package recorders;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.PrintWriter;

import flattener.core.Handler;
import flattener.core.StimuliRecorder;
import flattener.factories.DaikonComponentsFactory;
import flattener.flatteners.ObjectFlattener;
import flattener.utilities.LogWriter;


/**
 * @author Davide Lorenzoli
 */
public class FlattenerFacade {

    /**
     * @param logFile
     * @param rootName
     * @param entryPointName
     * @param object
     */
    public static void flatteningEnterPoint(String logFile, String rootName, String entryPointName, Object object, Class objType) {        
    	try {
    	    ObjectFlattener of = null;
    	    
    	    // :::ENTER
    		LogWriter.log(logFile, "\n" + entryPointName + ":::ENTER\n");
    		    	    
        	of = new ObjectFlattener();					
        	
        	// Create the factory
        	DaikonComponentsFactory daikonFactory = new DaikonComponentsFactory();
        	// Get the handler and set the root node name
        	Handler handler = daikonFactory.getHandler(rootName);
        	of.setDataHandler(handler);
        	
        	//sr = new DOMStimuliRecorder();
        	StimuliRecorder stimuliRecorder = daikonFactory.getStimuliRecorder();
        	//sr.setRecorder(new FileRecorder(new File(logFile)));
        	stimuliRecorder.setWriter(new PrintWriter(new BufferedWriter(new FileWriter(logFile, true))));
        	
       		//OFRunningRegistry.getInstance().setInFlattener( true );
       		
        	of.doSmash(object,objType);
        	
       		//OFRunningRegistry.getInstance().setInFlattener( false );
        	
        	//sr.record(of.getDataHandler().getData());        	        	
    	stimuliRecorder.record(of.getDataHandler().getData());
    	} catch (Exception exception) {
    		exception.printStackTrace();
    	}		        
    }
    
    /**
     * @param logFile
     * @param rootNames
     * @param entryPointName
     * @param objects
     * @param argumentTypes 
     */
    public static void flatteningEnterPoint(String logFile, String[] rootNames, String entryPointName, Object[] objects, Class[] argumentTypes) {        
    	try {
    	    ObjectFlattener of = null;

    	    
    	    // :::ENTER
    		LogWriter.log(logFile, "\n" + entryPointName + ":::ENTER\n");
    		
    	    for (int i=0; i<rootNames.length; i++) {
        		of = new ObjectFlattener();					
        		//of.setDataHandler(new DOMHandler(rootNames[i]));
        		// Create the factory
        		DaikonComponentsFactory daikonFactory = new DaikonComponentsFactory();
        		// Get the handler and set the root node name
        		Handler handler = daikonFactory.getHandler(rootNames[i]);
        		of.setDataHandler(handler);
        		
        		//sr = new DOMStimuliRecorder();
        		StimuliRecorder stimuliRecorder = daikonFactory.getStimuliRecorder();
        		//sr.setRecorder(new FileRecorder(new File(logFile)));
        		stimuliRecorder.setWriter(new PrintWriter(new BufferedWriter(new FileWriter(logFile, true))));
        		
        		
        		
        		of.doSmash(objects[i],argumentTypes[i]);									
        		
        		
        		
        		//sr.record(of.getDataHandler().getData()); 
        		stimuliRecorder.record(of.getDataHandler().getData());
    	    }    		
    	} catch (Exception exception) {
    		exception.printStackTrace();
    	}		        
    }
    
    /**
     * @param logFile
     * @param rootName
     * @param entryPointName
     * @param object
     */
    public static void flatteningExitPoint(String logFile, String rootName, String entryPointName, Object object, Class objType ) {
    	try {    									
    	    ObjectFlattener of = null;

    	    
    	    // :::EXIT
    		LogWriter.log(logFile, "\n" + entryPointName + ":::EXIT1\n");
    		    	    
        	of = new ObjectFlattener();					
        	//of.setDataHandler(new DOMHandler(rootName));
        	// Create the factory
	DaikonComponentsFactory daikonFactory = new DaikonComponentsFactory();
	// Get the handler and set the root node name
        	Handler handler = daikonFactory.getHandler(rootName);
        	of.setDataHandler(handler);
        	
        	//sr = new DOMStimuliRecorder();    
        	StimuliRecorder stimuliRecorder = daikonFactory.getStimuliRecorder();
        	//sr.setRecorder(new FileRecorder(new File(logFile)));    		
        	stimuliRecorder.setWriter(new PrintWriter(new BufferedWriter(new FileWriter(logFile, true))));
        	
        
       		//OFRunningRegistry.getInstance().setInFlattener( true );
        	
        	of.doSmash(object);
        	
       		//OFRunningRegistry.getInstance().setInFlattener( false );
        	
        	
        	//sr.record(of.getDataHandler().getData());        
        	stimuliRecorder.record(of.getDataHandler().getData());
        	

        	stimuliRecorder.getWriter().close();
    	} catch (Exception exception) {
    		exception.printStackTrace();
    	}        
    }           
    
    public static void flatteningExitPoint(String logFile, String[] rootNames, String entryPointName, Object[] objects, Class[] argumentTypes) {
    	try {    									
    	    ObjectFlattener of = null;

    	    
    	    // :::EXIT
    		LogWriter.log(logFile, "\n" + entryPointName + ":::EXIT1\n");
    		
    	    for (int i=0; i<rootNames.length; i++) {
    	    	
        		of = new ObjectFlattener();					
        		//of.setDataHandler(new DOMHandler(rootNames[i]));
        		// Create the factory
        		DaikonComponentsFactory daikonFactory = new DaikonComponentsFactory();
        		// Get the handler and set the root node name
        		Handler handler = daikonFactory.getHandler(rootNames[i]);
        		of.setDataHandler(handler);
        		
        		//sr = new DOMStimuliRecorder();    
        		StimuliRecorder stimuliRecorder = daikonFactory.getStimuliRecorder();
        		//sr.setRecorder(new FileRecorder(new File(logFile)));    		
        		stimuliRecorder.setWriter(new PrintWriter(new BufferedWriter(new FileWriter(logFile, true))));
        		
        		//OFRunningRegistry.getInstance().setInFlattener( true );
        		
        		
        		of.doSmash(objects[i],argumentTypes[i]);									
        		   
        		//OFRunningRegistry.getInstance().setInFlattener( false );
        		
        		//sr.record(of.getDataHandler().getData());    
        		stimuliRecorder.record(of.getDataHandler().getData());

        		stimuliRecorder.getWriter().close();
    	    }
    	} catch (Exception exception) {
    		exception.printStackTrace();
    	}        
    }
}