package flattener.writers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.io.Writer;
import java.util.StringTokenizer;

import database.BeginEndDeclaration;
import database.DataLayerException;
import database.DataModel;
import database.Datum;
import database.GKTailDataModel;

import recorders.RecorderException;

public class DBWriter extends Writer {
	
	private String message = "";
	protected String methodSignature = "";
	protected String dbTable = ""; //identifies the db table where we write the data
	private String beginDeclaration = ""; //used to store declaration trace for the table BeginEndDeclaration
	private String endDeclaration = ""; //used to store declaration trace for the table BeginEndDeclaration
	//used to store io invariants on the table DataModel
	private String dataModelIN = "";  
	private String dataModelOUT = "";
	
	public DBWriter (String methodSignature, String dbTable) {
		this.methodSignature = methodSignature;
		this.dbTable = dbTable;
	}
	
	@Override
	public void write(char[] cbuf, int off, int len) throws IOException {
		
		message = message.concat(String.copyValueOf(cbuf, off, len));
		//System.out.println("DBWRITER---------------" + message + "END DBWRITER");
	}

	@Override
	public void flush() throws DBWriterException {
		
		//throw new DBWriterException ("not implemented method in DBWriter");
	}

	@Override
	public void close() throws IOException {
		//FIXME: refactor this method 
		if(dbTable == "Registration") {
			try {
				Datum.insert(methodSignature, message);
			} catch (DataLayerException e) {
				e.printStackTrace();
			}
		}
		if(dbTable == "Normalization") {
			defineDeclarationString(message);
			try {
				BeginEndDeclaration.insert(methodSignature, beginDeclaration, endDeclaration);
			} catch (DataLayerException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if(dbTable == "Invariants") {
			//System.out.println("INVARIANTS TO WRITE ON DB " + message);
			defineDataModel(message);
			try {
				DataModel.insert(methodSignature, dataModelIN, dataModelOUT);
			} catch (DataLayerException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if(dbTable == "GKTailInvariants") {
			//System.out.println("INVARIANTS TO WRITE ON DB " + message);
			defineDataModel(message);
			try {
				GKTailDataModel.insert(methodSignature, dataModelIN, dataModelOUT);
			} catch (DataLayerException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	private void defineDataModel(String message) throws IOException {
		BufferedReader br = new BufferedReader(new StringReader(message));
		String line = "";

		while(!line.contains("====")) {
			dataModelIN = dataModelIN.concat(line + "\n");
			line = br.readLine();
		}
		line = br.readLine();
		while(line != null) {
			dataModelOUT = dataModelOUT.concat(line + "\n");
			line = br.readLine();
		}
	}

	private void defineDeclarationString(String message) throws IOException {
		BufferedReader br = new BufferedReader(new StringReader(message));
		String line = null;
		line = br.readLine();
		while(line != null) {
			if((beginDeclaration.contains("DECLARE") & line.contains("DECLARE")) | endDeclaration.contains("DECLARE")) {
				endDeclaration = endDeclaration.concat(line + "\n");
			}else {
				beginDeclaration = beginDeclaration.concat(line + "\n");
			}
			line = br.readLine();
		}
	}
}
