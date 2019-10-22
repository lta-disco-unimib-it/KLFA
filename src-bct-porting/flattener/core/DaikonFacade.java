/*
 * Created on 21-mag-2005
 */
package flattener.core;

import java.io.Writer;
import java.lang.reflect.InvocationTargetException;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;

import flattener.factories.DaikonComponentsFactory;
import flattener.flatteners.ObjectFlattener;

/**
 * @author Davide Lorenzoli
 */
public class DaikonFacade {

	/**
	 * Converts a DOM document into a text XML document. It works ONLY if the
	 * values of the attributes of all nodes are different from the
	 * <code>null</code> object.
	 * 
	 * @param object
	 * @param outputStream
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @throws TransformerException
	 */
	public static void getXML(Object object, Writer writer) throws IllegalArgumentException, IllegalAccessException, InvocationTargetException, TransformerException {
		// Create the factory
		DaikonComponentsFactory daikonFactory = new DaikonComponentsFactory();
		Handler handler = daikonFactory.getHandler(object.getClass().getName());
		
		// Initializing the Object Flattener
	    ObjectFlattener objectFlattener = new ObjectFlattener();
    	objectFlattener.setDataHandler(handler);
    	
    	// Flattening the object
    	objectFlattener.doSmash(object);
    	
		TransformerFactory tf = TransformerFactory.newInstance();
		DOMSource domS = new DOMSource((Document) objectFlattener.getDataHandler().getData());
		
		StreamResult stremR = new StreamResult(writer);				
		
		Transformer t = tf.newTransformer();
		t.transform(domS, stremR);
	}
	
	/**
	 * This method mark the entry point of a Daikon trace.
	 * @param object The object to be flatten
	 * @param writer
	 * @throws Exception
	 */
	public static void entryPoint(Object object, Writer writer) throws Exception {
		// Create the factory
		DaikonComponentsFactory daikonFactory = new DaikonComponentsFactory();
		// Get the handler and set the root node name as "p0"
		Handler handler = daikonFactory.getHandler("p0");		
		StimuliRecorder stimuliRecorder = daikonFactory.getStimuliRecorder();

		// Initializing the Object Flattener
	    ObjectFlattener objectFlattener = new ObjectFlattener();
    	objectFlattener.setDataHandler(handler);
    	stimuliRecorder.setWriter(writer);

    	// Flattening the object
    	objectFlattener.doSmash(object);
    	
	    // :::ENTER
    	String callerMethod = (new Throwable()).getStackTrace()[1].toString();	    	    
	    writer.write("\n" + callerMethod + ":::ENTER\n");
	    
    	stimuliRecorder.record(objectFlattener.getDataHandler().getData());
	}

	/**
	 * This method mark the entry point of a Daikon trace.
	 * @param object The objects array to be flatten
	 * @param recorder
	 * @throws Exception
	 */
	public static void entryPoint(Object object[], Writer writer) throws Exception {
		// Create the factory
		DaikonComponentsFactory daikonFactory = new DaikonComponentsFactory();
		
		// :::ENTER		
		String callerMethod = (new Throwable()).getStackTrace()[1].toString();
		writer.write("\n" + callerMethod + ":::ENTER\n");
		
		for (int i=0; i<object.length; i++) {
			// Get the handler and set the root node name as "p" + i
			Handler handler = daikonFactory.getHandler("p" + i);		
			StimuliRecorder stimuliRecorder = daikonFactory.getStimuliRecorder();

			// Initializing the Object Flattener
			ObjectFlattener objectFlattener = new ObjectFlattener();
			objectFlattener.setDataHandler(handler);
			stimuliRecorder.setWriter(writer);

			// Flattening the object
			objectFlattener.doSmash(object[i]);
			
			// Record data
			stimuliRecorder.record(objectFlattener.getDataHandler().getData());
		}
		
		writer.flush();
	}
	
	/**
	 * This method mark the exit point of a Daikon trace.
	 * @param object The object to be flatten
	 * @param recorder
	 * @throws Exception
	 */
	public static void exitPoint(Object object, Writer writer) throws Exception {
		// Create the factory
		DaikonComponentsFactory daikonFactory = new DaikonComponentsFactory();
		// Get the handler and set the root node name as "p0"
		Handler handler = daikonFactory.getHandler("p0");		
		StimuliRecorder stimuliRecorder = daikonFactory.getStimuliRecorder();

		// Initializing the Object Flattener
	    ObjectFlattener objectFlattener = new ObjectFlattener();
    	objectFlattener.setDataHandler(handler);
    	stimuliRecorder.setWriter(writer);

    	// Flattening the object
    	objectFlattener.doSmash(object);
    	
	    // :::EXIT1
    	String callerMethod = (new Throwable()).getStackTrace()[1].toString();	    
	    
	    writer.write("\n" + callerMethod + ":::EXIT1\n");
    	stimuliRecorder.record(objectFlattener.getDataHandler().getData());
	}
	
	/**
	 * This method mark the exit point of a Daikon trace.
	 * @param object The objects array to be flatten
	 * @param recorder
	 * @throws Exception
	 */
	public static void exitPoint(Object object[], Writer writer) throws Exception {
		// Create the factory
		DaikonComponentsFactory daikonFactory = new DaikonComponentsFactory();
		
		// :::EXIT1		
		String callerMethod = (new Throwable()).getStackTrace()[1].toString();
	    writer.write("\n" + callerMethod + ":::EXIT1\n");
		
		for (int i=0; i<object.length; i++) {
			// Get the handler and set the root node name as "p" + i
			Handler handler = daikonFactory.getHandler("p" + i);		
			StimuliRecorder stimuliRecorder = daikonFactory.getStimuliRecorder();

			// Initializing the Object Flattener
			ObjectFlattener objectFlattener = new ObjectFlattener();
			objectFlattener.setDataHandler(handler);
			stimuliRecorder.setWriter(writer);

			// Flattening the object
			objectFlattener.doSmash(object[i]);
			
			// Record data
			stimuliRecorder.record(objectFlattener.getDataHandler().getData());
		}
	}	
	
	// ONLY FOR TESTING PURPOSES 
	public static void main(String[] args) {
        try {            
            int m[][] = new int[][] {{12, 3, 5,5,6,7,4,5,6},
                                     {3, 54, 7,5,6,7,8,9,1},
                                     {4, 6, 83,2,3,4,6,7,8}};            
            recorders.FlattenerFacade.flatteningEnterPoint("c:/pippo.txt", new String[] {"pippo"}, "pippo", new Object [] {m}, new Class [] {m.getClass()});
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
