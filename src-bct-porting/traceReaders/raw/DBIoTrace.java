package traceReaders.raw;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.NoSuchElementException;

import database.ConnectionDispenser;
import database.DBException;


public class DBIoTrace implements IoTrace {
	
	private String methodDeclaration;
	private BufferedReader reader = null;
	private ArrayList <Integer> beginEndExecMethodID = new ArrayList<Integer>();
	private long curLine = 0;
	
	public class DBLineIterator implements LineIterator {
		
		private BufferedReader reader;
		
		/*
		 * tmp: used to check if the line in the BufferedReader has already read
		 * line: line read from the BufferedReader
		 */		
		private boolean tmp = false; 
		private String line = null;
		
		public DBLineIterator(BufferedReader reader) {
			this.reader = reader; 
		}

		/*
		 * reimplemetation of the method in order to avoid null line read from 
		 * the method java.io.BufferedReader.readLine()
		 */
		public boolean hasNext() {
			try {
				 line = reader.readLine();
				 tmp = true;
			} catch (IOException e) {
			}
			if (line == null)
				return false; 
			else return true;
			
			/*try {
				return reader.ready();
			} catch (IOException e) {
				return false;
			}*/
		}

		/*
		 * reimplemetation of the method in order to avoid null line read from 
		 * the method java.io.BufferedReader.readLine()
		 */
		public String next() {
			++curLine;
			if (tmp) {
				tmp = false; 
				return line;
			}else {
				try {
					line = reader.readLine();
					return line;
				} catch (IOException e) {
					throw new NoSuchElementException();
				}
			}
		}

		public void remove() {
			throw new UnsupportedOperationException();
		}

		public long getCurrentLineNumber() {
			return curLine;
		}
		
	}

	public DBIoTrace(String methodDeclaration) {
		this.methodDeclaration = methodDeclaration;
	}

	public String getMethodName() {
		return methodDeclaration;
	}

	public LineIterator getLineIterator() throws TraceException {
		
		StringBuffer dataDefinition = new StringBuffer();		
		
		beginEndExecMethodID.clear();
		
		try {
			PreparedStatement stmt = ConnectionDispenser.getConnection().prepareStatement(
				"SELECT dataDefinition, idBeginEndExecMethod FROM method, beginendexecmethod, datum " +
				"WHERE idMethod = method_idMethod AND idBeginEndExecMethod = beginendexecmethod_idBeginEndExecMethod " + 
				"AND methodDeclaration = ? " );
			stmt.setString(1, methodDeclaration);
			System.out.println("LineIterator.getLineIterator() QUERY: "+stmt);
			ResultSet rs = stmt.executeQuery();
						
			while (rs.next()) {								
				dataDefinition.append(rs.getString("dataDefinition"));
				beginEndExecMethodID.add(rs.getInt("idBeginEndExecMethod"));
					
			}
			
			stmt.close();
		} catch (SQLException e) {
			throw new TraceException(e.getMessage());
		} catch (DBException e) {
			throw new TraceException(e.getMessage());
		}
		//System.out.println("LineIterator.getLineIterator() dataDefinition"+dataDefinition);
		return new DBLineIterator(new BufferedReader(new StringReader(dataDefinition.toString())));
	}

	public MetaDataIterator getMetaDataIterator() throws TraceException {
		// TODO Auto-generated method stub
		return null;
	}

	public void release() throws TraceException {
		try {
			if ( reader != null )
				reader.close();
			
			//if ( metaReader != null )
				//metaReader.close();
			
		} catch (IOException e) {
			throw new TraceException("Cannot release "+e.getMessage());
		} finally {
			reader = null;
			//metaReader = null;
		}	
	}
	
	public ArrayList<Integer> getBeginEndExecMethod() {
		
		if (beginEndExecMethodID.isEmpty()) {
			try {
				getLineIterator();
			} catch (TraceException e) {
				e.printStackTrace();
			}
		}
		return beginEndExecMethodID;
	}

}

