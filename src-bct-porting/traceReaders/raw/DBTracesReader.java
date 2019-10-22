package traceReaders.raw;

import java.io.File;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;

import conf.BctSettingsException;
import conf.EnvironmentalSetter;
import conf.InvariantGeneratorSettings;
import conf.BCTFileFilter;
import conf.TraceReaderSettings;
import database.ConnectionDispenser;
import database.DBException;
import database.DataLayerException;

public class DBTracesReader implements TracesReader {
	private File ioTracesDir;
	private File interactionTracesDir;
	private static final BCTFileFilter dtraceFilter = EnvironmentalSetter.getIoTraceFilter();
	
	public interface Options {
		public final String tracesPath = "traceReader.tracesPath";
		public final String ioTracesDirName = "traceReader.ioTracesDirName";
		public final String interactionTracesDirName = "traceReader.interactionTracesDirName";
	}
	
	public Iterator<IoTrace> getIoTraces() throws TraceException {
		
		ArrayList<IoTrace> al = new ArrayList<IoTrace>();
		try {
			
			PreparedStatement stmt = ConnectionDispenser.getConnection().prepareStatement(
				"SELECT DISTINCT methodDeclaration FROM method, beginendexecmethod, datum " +
				"WHERE idMethod = method_idMethod AND idBeginEndExecMethod = beginendexecmethod_idBeginEndExecMethod ");
			ResultSet rs = stmt.executeQuery();
			
			while (rs.next()) {
				al.add(getIoTrace( rs.getString("methodDeclaration") ));
			}
			
			stmt.close();
		} catch (SQLException e) {
			throw new TraceException(e.getMessage());
		} catch (DBException e) {
			throw new TraceException(e.getMessage());
		}
		return al.iterator();
	}

	
	public DBIoTrace getIoTrace(String methodDeclaration) {
		
		//String methodName = methodDeclaration.substring(0,methodDeclaration.length()-7);
		return new DBIoTrace(methodDeclaration);
	}

	public void init(InvariantGeneratorSettings trs) throws BctSettingsException {
	}

	public Iterator getInteractionTraces() throws TraceException {
		
		ArrayList al = new ArrayList();
		try {
			
			PreparedStatement stmt = ConnectionDispenser.getConnection().prepareStatement(
				" SELECT idThread FROM thread ");
			ResultSet rs = stmt.executeQuery();
			
			while (rs.next()) {
				al.add(new DBInteractionTrace(rs.getString("idThread")));
				//al.add(new DBInteractionTraceExtended(rs.getString("idThread")));
			}
			
			stmt.close();
		} catch (SQLException e) {
			throw new TraceException(e.getMessage());
		} catch (DBException e) {
			throw new TraceException(e.getMessage());
		}
		return al.iterator();
		/*
		File[] srcFiles = interactionTracesDir.listFiles(EnvironmentalSetter.getInteractionTraceFilter());
		ArrayList al = new ArrayList(srcFiles.length);
		for (int i = 0; i < srcFiles.length; i++) {
				
			File meta = new File ( interactionTracesDir, srcFiles[i].getName().substring(0, srcFiles[i].getName().length()-4)+".meta" );
			System.out.println(meta.getName()+" "+meta.exists());
			if ( ! meta.exists() )
				meta = null;
			
			al.add(new FileInteractionTrace(srcFiles[i].getName().substring(16, srcFiles[i].getName().length()-4),srcFiles[i], meta));
		}
		
		return al.iterator();*/
	}

}