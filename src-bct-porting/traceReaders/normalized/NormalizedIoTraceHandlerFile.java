package traceReaders.normalized;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Vector;

import dfmaker.core.DaikonDeclarationMaker;
import dfmaker.core.Superstructure;
import dfmaker.core.Variable;


import traceReaders.raw.IoTrace;
import traceReaders.raw.TraceException;
import util.FileIndex;
import util.FileIndexAppend;
import util.FileIndex.FileIndexException;

public class NormalizedIoTraceHandlerFile implements NormalizedIoTraceHandler {
	
	/**
	 * Inner class to implement the NormalizedIoTraceIterator.
	 * It is declared private because we want that this is used only locally.
	 * Classes that uses an instance of this class must refer to its interface and cannot instantiate it 
	 * 
	 * 
	 * @author Fabrizio Pastore fabrizio.pastore AT gmail.com
	 *
	 */
	private class FileIoTracesIterator implements NormalizedIoTraceIterator {
		private Iterator<String> declsIterator;
		private Iterator<String> dtraceIterator;
		
		public FileIoTracesIterator(FileIndex decls, FileIndex dtraces) {
			
			declsIterator = decls.getIds().iterator();
			
			
		}

		public boolean hasNext() {
			return ( declsIterator.hasNext() );
		}

		public NormalizedIoTrace next() {
			
			String id = declsIterator.next();
			String name = null;
			try {
				name = decls.getNameFromId(id);
			} catch (FileIndexException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			File declsFile = getDeclFile(name);
			File tracesFile = getTraceFile(name);
			
			return new NormalizedIoTraceFile (name,declsFile,tracesFile);
		}

		public void remove() {
			throw new UnsupportedOperationException();
		}

	}

	private FileIndexAppend decls;
	private FileIndexAppend dtraces;
	private File declsIndex;
	private File traceIndex;
	private File declsDir;
	private File traceDir;
	
	public NormalizedIoTraceHandlerFile(File decls, File dtrace) {
		this.declsDir = decls;
		this.traceDir = dtrace;
		this.declsIndex = new File ( decls, "declarations.idx" );
		this.traceIndex = new File ( dtrace, "traces.idx" );
		this.decls = new FileIndexAppend(declsIndex,".decls");
		this.dtraces = new FileIndexAppend(traceIndex,".dtrace");
	}



	public File getTraceFile( String methodName ){
		
		String file = null;
		if ( ! dtraces.containsName(methodName) ){
			file = dtraces.add(methodName);
			try {
				dtraces.save(traceIndex);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			try {
				file = dtraces.getId(methodName);
			} catch (FileIndexException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return new File ( traceDir, file );
	}

	public File getDeclFile( String methodName ){
		String file = null;
		if ( ! decls.containsName(methodName) ){
			file = decls.add(methodName);
			try {
				decls.save(declsIndex);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			try {
				file = decls.getId(methodName);
			} catch (FileIndexException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return new File ( declsDir, file );
	}
	
	public NormalizedIoTrace newIoTrace(IoTrace trace) {
		String methodName = trace.getMethodName();
		return new NormalizedIoTraceFile ( methodName, getTraceFile(methodName), getDeclFile(methodName)) ;
	}
	
	public NormalizedIoTraceIterator getIoTracesIterator() {	
		return new FileIoTracesIterator(decls,dtraces);
	}

	public void saveTrace(NormalizedIoTrace normalizedTrace) throws TraceException {
		normalizedTrace.commit();
	}
	
}
