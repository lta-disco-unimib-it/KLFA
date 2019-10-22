package traceReaders.normalized;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Vector;

import traceReaders.raw.DBIoTrace;
import traceReaders.raw.IoTrace;
import traceReaders.raw.TraceException;
import database.BeginEndDeclaration;
import database.DataLayerException;
import database.Datum;
import database.NormalizedData;
import dfmaker.core.DaikonDeclarationMaker;
import dfmaker.core.Superstructure;
import dfmaker.core.Variable;
import flattener.writers.DBWriter;

public class NormalizedIoTraceDB implements NormalizedIoTrace {
	//to write on DB
	private PrintWriter traceDB;
	private StringWriter stringTraceDB = null;
	private DBWriter writerDeclDB;
	private String dbTable = "Normalization";
	//to write on daikon file
	private PrintWriter traceFile;
	private PrintWriter declFile;
	private File daikonTraceFile;
	private File daikonDeclFile;
	
	private Superstructure entrySuperstructure;
	private Superstructure exitSuperstructure;
	private DBIoTrace trace;
	private String methodName;

	
	/**
	 * Constructor 
	 * @param trace     	raw trace used to extract the name of the method and the id of trace for the normalized trace  
	 * @param tracesFile	the daikon dtrace file associated with this trace
	 * @param declsFile		the daikon declarations file associated with this trace 
	 * */
	NormalizedIoTraceDB(IoTrace trace, File tracesFile, File declsFile) {
		this.trace = (DBIoTrace) trace;
		this.daikonDeclFile = declsFile;
		this.daikonTraceFile = tracesFile;
	}
	//costruttore invocato da durante l'esecuzione di Daikon
	public NormalizedIoTraceDB(String methodName, File declsFile, File tracesFile) {
		this.daikonDeclFile = declsFile;
		this.daikonTraceFile = tracesFile;
		this.methodName = methodName;
	}

	public void addEntryPoint(String entryPoint, Vector<Variable> normalizedPoint) {
		addPoint(entryPoint,normalizedPoint);
	}

	public void addExitPoint(String exitPoint, Vector<Variable> normalizedPoint) {
		addPoint(exitPoint,normalizedPoint);
	}
	
	private void addPoint( String point, Vector<Variable> normalizedPoint ){	
		if ( traceDB == null ) {
			  stringTraceDB = new StringWriter();
			  traceDB = new PrintWriter (stringTraceDB); 	  
		}
		
		if ( traceFile == null ) {
			try {
				traceFile = new PrintWriter ( new FileWriter( daikonTraceFile ) );
			} catch (IOException e) {
				e.printStackTrace();
			}  
		}
		  
		traceDB.println(point);
		traceFile.println(point);
		Enumeration enumeration = normalizedPoint.elements();
        while (enumeration.hasMoreElements()) {
         	 Variable variable = (Variable) enumeration.nextElement();
         	 traceDB.println(variable.getName());
          	 traceDB.println(variable.getValue());
          	 traceDB.println(variable.getModified());
          	 
           	traceFile.println(variable.getName());
          	traceFile.println(variable.getValue());
          	traceFile.println(variable.getModified());
        }
        traceDB.println();
        traceFile.println();     
          
        //write normalizedTrace on DB
        try {
        	NormalizedData.insert(trace.getMethodName(), stringTraceDB.toString(), trace.getBeginEndExecMethod().remove(0));
		} catch (DataLayerException e) {
		    e.printStackTrace();
		}
	
		traceDB.close();
		traceDB = null;
		try {
			stringTraceDB.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		stringTraceDB = null;
		
	}

	public void addObjectPoint(String objectPoint, Vector<Variable> normalizedPoint) {
		throw new UnsupportedOperationException("Object Point Not Supported ");
	}
	
	public void commit() throws TraceException {
		writeDeclarations();
		
		if ( writerDeclDB != null ){
			try {
				writerDeclDB.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			writerDeclDB = null;
		}
		
		if ( declFile != null ){
			declFile.close();
			declFile = null;
		}
		
		if ( traceFile != null ){
			traceFile.close();
			traceFile = null;
		}
		
		if ( traceDB != null ){
			traceDB.close();
			traceDB = null;
			try {
				stringTraceDB.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
			stringTraceDB = null;
		}
		
		
	}
	
	private void writeDeclarations() throws TraceException {
		if ( writerDeclDB == null ){
			writerDeclDB = new DBWriter(trace.getMethodName(), dbTable);
		}
		
		if ( declFile == null ){
			try {
				declFile = new PrintWriter ( new FileWriter( daikonDeclFile ) );
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		try {
			DaikonDeclarationMaker.write(declFile, entrySuperstructure, exitSuperstructure );
			DaikonDeclarationMaker.write(writerDeclDB, entrySuperstructure, exitSuperstructure );
		} catch (IOException e) {
			// TODO Auto-generated catch block
			throw new TraceException("Problem saving " + entrySuperstructure.getJavaName());
		}
	}
	
	public void setEntrySuperStructure( Superstructure entrySuperStructure) {
		this.entrySuperstructure = entrySuperStructure;
	}
	
	public void setExitSuperStructure( Superstructure exitSuperStructure) {
		this.exitSuperstructure = exitSuperStructure;
	}

	public String getMethodName() {
		//return trace.getMethodName();
		return methodName;
	}

	
	public File getDaikonDeclFile() {
		return daikonDeclFile;
		
	}

	public File getDaikonTraceFile() {
		return daikonTraceFile;
	}
}
