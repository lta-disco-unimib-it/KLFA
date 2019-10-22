/*
 * This class implements functionalities for visualizing a serialized FSA.
 * 
 */
package tools;

import gui.environment.EnvironmentFrame;
import gui.environment.FrameFactory;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Blob;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.rowset.serial.SerialBlob;

import automata.fsa.FiniteStateAutomaton;
import database.ConnectionDispenser;
import database.DBException;
import database.DataLayerException;

public class GenerateFSA {
	
	public static void main(String[] args) {
		Blob fsa = null;
		
		if (args.length < 1) {
			printUsage();
			System.exit(-1);
		}
		
		String type = "efsa";
		String outFile = null;
		
		for ( int i = 0; i < args.length -2; ++i ){
			if ( args[i].equals("-type") ){
				type = args[++i];
			} else if ( args[i].equals("-out") ){
				outFile = args[++i];
			} else {
				printUsage();
				System.exit(-1);
			}
		}
		
		if ( !type.equals("fsa") && !type.equals("efsa") ){
			System.err.println("Wrong fsa type must be one of fsa or efsa");
		}
		
		String methodName = args[args.length-1];
		
		//retrieve stream of bytes from DB that represents FSA model
		try {
			fsa = new SerialBlob (getFSAfromDB (methodName,type));
		} catch (DataLayerException e1) {
			e1.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		//create the file.ser of the specified method
		if ( outFile == null ){
			outFile = args[0] + ".ser";
		}
		
		File fsaFile = new File ( outFile );
		try {
			FileOutputStream file = new FileOutputStream(fsaFile);
			file.write(fsa.getBytes(1, (int) fsa.length()));
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		//display FSA
	    try {
			EnvironmentFrame f = FrameFactory.createFrame(FiniteStateAutomaton.readSerializedFSA(fsaFile.getAbsolutePath()));
		} catch (FileNotFoundException e) {
			System.out.println("Error: File " + fsaFile.getAbsolutePath() + " does not exist!");
			//e.printStackTrace();
			System.exit(1);
		} catch (ClassNotFoundException e) {
			System.out.println("The class corresponding to the deserialized object has not been found");
			//e.printStackTrace();
			System.exit(1);
		} catch (IOException e) {
			System.out.println("Input/Output error while opening " + fsaFile.getAbsolutePath() + " resource");
			//e.printStackTrace();
			System.exit(1);
		}
		
	}

	private static void printUsage() {
		System.out.println("Usage : "+
				GenerateFSA.class.getName()+" [options] <methodName> \n" +
						"where:\n\t <methodName> is the name of the method whose FSA you want to open\n" +
						"[options] are : \n" +
						"-type <name> : type of the fsa to open, one of fsa or efsa (default is fsa)\n" +
						"-out <dest> : name of the file where you want to save the fsa (default is the <methodName>.ser)\n"
				);
	}

	private static Blob getFSAfromDB(String methodName,String type) throws DataLayerException {
		Blob fsa;
		
		String fsaTable;
		String fsaColumn;
		//set the table from which we want to generate the fsa/efsa
		if ( type.equals("efsa") ){
			fsaTable = "efsa";
			fsaColumn = "efsa";
		} else {
			fsaTable = "fsa";
			fsaColumn = "fsa";
		}
		
		try {
			PreparedStatement stmt = ConnectionDispenser.getConnection().prepareStatement(
				//"SELECT efsa FROM bct.efsa WHERE method_idMethod = (SELECT idMethod FROM bct.method WHERE methodDeclaration = ?)");
				//"SELECT fsa FROM bct.fsa WHERE method_idMethod = (SELECT idMethod FROM bct.method WHERE methodDeclaration = ?)");
				"SELECT "+fsaColumn+" FROM bct."+fsaTable+" WHERE method_idMethod = (SELECT idMethod FROM bct.method WHERE methodDeclaration = ?)");
			
			stmt.setString(1, methodName);
			
			System.out.println("tools.GenerateFSA > Retriving " + type.toUpperCase() + "...");
			
			ResultSet rs = stmt.executeQuery();
			if (rs.first()) {
				//fsa = rs.getBlob("efsa");
				//fsa = rs.getBlob("fsa");
				fsa = rs.getBlob(type);
				stmt.close();				
				return fsa;
			}
			else {
				stmt.close();
				throw new DataLayerException(type.toUpperCase() + " not found for the method: " + methodName );
			}
		} catch (SQLException e) {
			throw new DataLayerException(e.getMessage());
		} catch (DBException e) {
			throw new DataLayerException(e.getMessage());
		}
	}
}