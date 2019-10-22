/*
 * Created on 29-dic-2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package tools;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;



import traceReaders.normalized.NormalizedInteractionTraceFile;
import traceReaders.normalized.NormalizedInteractionTraceHandler;
import util.FileIndexAppend;
import util.FileIndex.FileIndexException;

/**
 * @author Leonardo Mariani
 *
 */
public class TraceRepository {
	
	private File outputFolder;

	private FileIndexAppend repository;

	private static boolean APPEND = true;
	private static boolean NOT_APPEND = false;
	
	/**
	 * 
	 */
	public TraceRepository(File outputFolder) {
		this.outputFolder = outputFolder;
		repository = new FileIndexAppend(new File(outputFolder,"interaction.idx"),".txt");
	}

	public void addTrace(String methodName, String trace) throws FileIndexException {
		String methodId;
		// open outputFolder\methodName in append ed aggiungere trace
		//System.out.println("Method: " + methodName + " trace: " + trace + "\n");
		if ( repository.containsName(methodName) )
			methodId = repository.getId(methodName); 
		else
			methodId = repository.add(methodName);
		
		File file = new File( outputFolder + "/" + methodId );				
		FileOutputStream fOut;
		try {
			fOut = new FileOutputStream(file, APPEND);			
			fOut.write(trace.getBytes());			
			fOut.close();			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}				
	}

	public List<NormalizedInteractionTraceFile> getTraces() throws FileIndexException {
		ArrayList<String> ids = repository.getIds();
		ArrayList<NormalizedInteractionTraceFile> al = new ArrayList<NormalizedInteractionTraceFile>();
		for ( String id : ids ){
			al.add( new NormalizedInteractionTraceFile(repository.getNameFromId(id),new File ( outputFolder, id) ) );
			
		}
		return al;
	}


	
}
