package traceReaders.raw;

import java.io.File;
import java.io.IOException;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


import util.FileIndex;
import util.FileIndexAppend;
import util.FileIndex.FileIndexException;

public class FileIoTraceRepository {
	private FileIndexAppend fileIndex;
	private FileIndexAppend metaIndex;
	private File traceIndexFile; 
	private File metaIndexFile;
	private File storageDir;
	
	private static final String traceIndexFileName = "traces.idx";
	private static final String metaIndexFileName = "meta.idx";
	
	public FileIoTraceRepository( File storageDir ){
		this.storageDir = storageDir;
		traceIndexFile = new File( storageDir, traceIndexFileName );
		metaIndexFile = new File( storageDir, metaIndexFileName );	
		fileIndex = new FileIndexAppend( traceIndexFile, ".dtrace" );
		metaIndex = new FileIndexAppend( metaIndexFile, ".meta" );
	}
	
	public FileIoTrace getRawTrace( String methodName ) throws FileReaderException{
		
		
		String file;
		String meta;
		
		if ( ! fileIndex.containsName(methodName) )
			file = fileIndex.add(methodName);
		
		if ( ! metaIndex.containsName(methodName) )
			meta = metaIndex.add(methodName);
		
		try {
			file = fileIndex.getId(methodName);
			meta = metaIndex.getId(methodName);
		} catch (FileIndexException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new FileReaderException("Cannot get file for "+methodName);
		}
		
		return getFileIoTrace( methodName,  file, meta);
	}

	public List<IoTrace> getRawTraces() throws FileReaderException {
		ArrayList<String> ids = fileIndex.getIds();
		ArrayList<IoTrace> traces = new ArrayList<IoTrace>();
		
		for ( String id :  ids ){
			
			String methodName;
			try {
				methodName = fileIndex.getNameFromId(id);
			} catch (FileIndexException e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
				throw new FileReaderException("Unconsistent index file");
			}
			if ( metaIndex.containsName(methodName)){
				try {
					traces.add(  getFileIoTrace(methodName, id, metaIndex.getId(methodName)) );
				} catch (FileIndexException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else {
				System.out.println("NOT CONTAIN "+id+"|");
				traces.add( getFileIoTrace(methodName, id) );
			}
					
		}
		
		return traces;
	}
	
	private IoTrace getFileIoTrace(String methodName, String id) {
		return new FileIoTrace( methodName,	new File( storageDir, id ) );
	}

	private FileIoTrace getFileIoTrace( String methodName, String traceId, String metaId ){
		return new FileIoTrace( methodName,	new File( storageDir, traceId ),	new File ( storageDir, metaId ));
	}
	
	
}
