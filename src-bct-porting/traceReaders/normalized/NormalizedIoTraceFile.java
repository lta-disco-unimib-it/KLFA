package traceReaders.normalized;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;
import java.util.Vector;

import traceReaders.raw.TraceException;
import dfmaker.core.DaikonDeclarationMaker;
import dfmaker.core.Superstructure;
import dfmaker.core.Variable;

public class NormalizedIoTraceFile implements NormalizedIoTrace {
	private PrintWriter trace;
	private PrintWriter decl;
	private File fileTrace;
	private File fileDecl;
	private Superstructure entrySuperstructure;
	private Superstructure exitSuperstructure;
	private String methodName;
	
	/**
	 * Constructor 
	 * @param method	the name of the method the trace we handles refers to
	 * @param fileTrace	the daikon dtrace file associated with this trace
	 * @param fileDecl	the daikon declarations file associated with this trace
	 */
	NormalizedIoTraceFile( String method, File fileTrace, File fileDecl ) {
		this.fileDecl = fileDecl;
		this.fileTrace = fileTrace;
		this.methodName = method;
	}
	
	public void addEntryPoint(String entryPoint, Vector<Variable> normalizedPoint) {
		addPoint(entryPoint,normalizedPoint);
	}


	public void addExitPoint(String exitPoint, Vector<Variable> normalizedPoint) {
		addPoint(exitPoint,normalizedPoint);
	}
	
	private void addPoint( String point, Vector<Variable> normalizedPoint ){
		  if ( trace == null ){
			  try {
				trace = new PrintWriter ( new FileWriter( fileTrace ) );
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}  
		  }
		
		  trace.println(point);
		  Enumeration enumeration = normalizedPoint.elements();
          while (enumeration.hasMoreElements()) {
          	Variable variable = (Variable) enumeration.nextElement();
          	trace.println(variable.getName());
          	trace.println(variable.getValue());
          	trace.println(variable.getModified());
          }
          trace.println();
          
	}


	public void addObjectPoint(String objectPoint, Vector<Variable> normalizedPoint) {
		throw new UnsupportedOperationException("Object Point Not Supported ");
	}

	

	public void commit() throws TraceException {
		writeDeclarations();
		if ( decl != null ){
			decl.close();
			decl = null;
		}
		
		if ( trace != null ){
			trace.close();
			trace = null;
		}
	}
	
	private void writeDeclarations() throws TraceException {
		if ( decl == null ){
			try {
				decl = new PrintWriter ( new FileWriter( fileDecl ) );
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		try {
			DaikonDeclarationMaker.write(decl, entrySuperstructure, exitSuperstructure );
		} catch (IOException e) {
			// TODO Auto-generated catch block
			throw new TraceException("Problem saving "+entrySuperstructure.getJavaName());
		}
	}
	
	public void setEntrySuperStructure( Superstructure entrySuperStructure) {
		this.entrySuperstructure = entrySuperStructure;
	}
	
	public void setExitSuperStructure( Superstructure exitSuperStructure) {
		this.exitSuperstructure = exitSuperStructure;
	}

	public String getMethodName() {
		return methodName;
	}

	
	public File getDaikonDeclFile() {
		return fileDecl;
	}

	
	public File getDaikonTraceFile() {
		return fileTrace;
	}
}
