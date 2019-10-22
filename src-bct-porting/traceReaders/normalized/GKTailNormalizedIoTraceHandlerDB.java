package traceReaders.normalized;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;

import traceReaders.raw.IoTrace;
import traceReaders.raw.TraceException;
import database.BeginEndDeclaration;
import database.DataLayerException;
import database.GKTailMethodCall;
import database.GKTailNormalizedData;

public class GKTailNormalizedIoTraceHandlerDB implements NormalizedIoTraceHandler {
	
	/**
	 * Inner class to implement the GKTailNormalizedIoTraceIterator.
	 * It is declared private because we want that this is used only locally.
	 * Classes that uses an instance of this class must refer to its interface and cannot instantiate it 
	 * 
	 */
	private class IoTracesIterator implements NormalizedIoTraceIterator {
		
		private Iterator<Integer> ioTraces;
		
		public IoTracesIterator() {

			try {
				ioTraces = GKTailMethodCall.getTracesID();
			} catch (DataLayerException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		public boolean hasNext() {
			return ioTraces.hasNext();
		}

		public NormalizedIoTrace next() {

			int idMethodCall = ioTraces.next();
			String methodName = "";
			try {
				methodName = GKTailMethodCall.getMethodName(idMethodCall);
			} catch (DataLayerException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			File declsFile = getDeclFile(methodName);
			File tracesFile = getTraceFile(idMethodCall, methodName);
			
			return new GKTailNormalizedIoTraceDB(methodName, declsFile, tracesFile, idMethodCall);
		}

		public void remove() {
			throw new UnsupportedOperationException();
		}

	}

	private File declsDir;
	private File traceDir;
	
	public GKTailNormalizedIoTraceHandlerDB(File decls, File dtrace) {
		this.declsDir = decls;
		this.traceDir = dtrace;
	}
	
	public File getTraceFile( int idTrace, String methodName) {

		String data = "";
		File trace = new File(traceDir, methodName + ".dtrace");
		FileWriter fw;
		//System.out.println("Get trace file "+methodName);
	
		try {
			data = GKTailNormalizedData.getData(idTrace);
			//System.out.println("data "+data);
			fw = new FileWriter(trace);
			fw.write(data);
			fw.close();
		} catch (DataLayerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return trace;
	}

	public File getDeclFile(String methodName){

		String declaration = "";
		File decls = new File(declsDir, methodName + ".decls");
		FileWriter fw;
		
		try {
			declaration = BeginEndDeclaration.getDeclaration(methodName);
			fw = new FileWriter(decls);
			fw.write(declaration);
			fw.close();
		} catch (DataLayerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return decls;
	}

	//FIXME generate an axception instead return null
	public NormalizedIoTrace newIoTrace(IoTrace trace) throws TraceNotSupportedException {
		return null;
	}
	
	public NormalizedIoTraceIterator getIoTracesIterator() {	
		return new IoTracesIterator();
	}

	public void saveTrace(NormalizedIoTrace normalizedTrace) throws TraceException {
	}
}
