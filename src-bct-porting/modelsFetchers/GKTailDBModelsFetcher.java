package modelsFetchers;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;

import javax.sql.rowset.serial.SerialBlob;
import javax.sql.rowset.serial.SerialException;

import util.FileIndexAppend;
import util.FileIndex.FileIndexException;

import automata.fsa.FiniteStateAutomaton;
import conf.ModelsFetcherSettings;
import database.DataLayerException;
import database.Efsa;
import database.Fsa;
import flattener.writers.DBWriter;

/**
 * This class returns models saved on DB.
 * 
 * Models are saved with the same name of the method they represent.
 * 
 * FIXME: controllare metodi per il check
 */

public class GKTailDBModelsFetcher implements ModelsFetcher {
	
	//path to the models directories 
	private File ioModelsDir;
	//private FilenameFilter ioModelsFilterEnter = new BCTFileFilter( "in.txt" );
	//private FilenameFilter ioModelsFilterExit = new BCTFileFilter( "out.txt" );
	private FileIndexAppend 	ioIndexEnter;
	private FileIndexAppend 	ioIndexExit;
	
	/**
	 * Initialization method, ModelsFetcherSettings must contain a "modelsDir" field. It indicates the directory containing the 
	 * 04-invariants BCT directory with ioInvariants and interctionInvariants.
	 * 
	 */
	public void init ( ModelsFetcherSettings options ){
		ioModelsDir = new File ( (String)options.getProperty("modelsDir")+File.separator+"ioInvariants" ); 
		ioModelsDir.mkdirs();
		ioIndexEnter = new FileIndexAppend( new File(ioModelsDir, "ioModelsEnter.idx" ), ".io.enter" );
		ioIndexExit = new FileIndexAppend( new File(ioModelsDir, "ioModelsExit.idx" ), ".io.exit" );
	}

	/*
	 * Return the FSA associated to the method passed as input.
	 * The model is searched in <modelsDir>/interactionInvariants/.
	 * Usually the model has the name of the method.
	 * 
	 * @see modelsFetchers.ModelsFetcher#getInteractionModel(java.lang.String)
	 */
	public FiniteStateAutomaton getInteractionModel(String methodSignature) throws ModelsFetcherException {
		// TODO Auto-generated method stub
		return null;
	}
	
	private File getIoModelEnterFile(String methodSignature) throws ModelsFetcherException{
		String file;
		try {
			file = ioIndexEnter.getId( methodSignature );

			if ( file == null ){
				file = ioIndexEnter.add(methodSignature);
			}
			return new File( ioModelsDir, file );
		} catch (FileIndexException e) {
			throw new ModelsFetcherException(e.getMessage());
		}
		
	}
	
	private File getIoModelExitFile(String methodSignature) throws ModelsFetcherException{
		try{
			String file = ioIndexExit.getId( methodSignature );
			if ( file == null ){
				file = ioIndexExit.add(methodSignature);
			}
			return new File( ioModelsDir, file );
		} catch (FileIndexException e) {
			throw new ModelsFetcherException(e.getMessage());
		}
	}
	
	///////////////////////////////////////////
	/**
	 * This method returns a Collection of all io expressions found
	 * 
	 * TODO: add a cache of models previously read
	 * 
	 * @param fileName	the name of the file we want to retrieve.
	 * @return the right IoExpressionCollection
	 */
	//////////////////////////////////////////////
	private ArrayList<String> getIoCollection(File fileToOpen) throws ModelsFetcherException{
		try {
			BufferedReader in = new BufferedReader(new FileReader(fileToOpen));
			ArrayList<String> ioEC = new ArrayList<String>(); 
			String expression = null;
			do {
				expression = in.readLine();
				if ( isValid  ( expression ) )
					ioEC.add(expression);
			} while	(expression != null);
			
			in.close();
			return ioEC;
			
		} catch (IOException e) {
			throw new ModelsFetcherException("Problem loading IO models from "+fileToOpen);
		}

	}
	
	/**
	 * Chek if a string is a right expresion. Basically it checks if it is real expression or a white line. 
	 * 
	 * @param expression	the expression string to be checked
	 * @return			
	 */
	private static boolean isValid(String expression) {
		if ( expression == null )
			return false;
		
		StringBuffer cleanString = new StringBuffer();
		for (int i = 0; i < expression.length(); i++)
			if (Character.isWhitespace(expression.charAt(i)))
				continue;
			else
				cleanString = cleanString.append(expression.charAt(i));

		if (cleanString.toString().equals(""))
			return false;
		else
			return true;
	}
	
	public void addIoModel(String idMethodCall, IoModel model) throws ModelsFetcherException {
		try {
			String dbTable = "GKTailInvariants";
			DBWriter writer = new DBWriter(idMethodCall, dbTable);

			Iterator it = model.preconditionsIt();
			while ( it.hasNext() ){
				writer.write((String)it.next());
				writer.write("\n");
			}
			//these lines need to separate preconditions from postcondiction in the DB 
			writer.write("====");
			writer.write("\n");
			
			it = model.postconditionsIt();
			while ( it.hasNext() ){
				writer.write((String)it.next());
				writer.write("\n");
			}
			writer.close();
			
		} 
		catch (IOException e) {
			throw new ModelsFetcherException("Cannot save IoModel for " + idMethodCall);
		}
	}
	
	public void addInteractionModel(int idMethod, ByteArrayOutputStream fsa) throws ModelsFetcherException {

		SerialBlob sb = null;
		try {
			sb = new SerialBlob(fsa.toByteArray());
			Efsa.insert(idMethod, sb);
		} catch (SerialException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch (DataLayerException e) {
			e.printStackTrace();
			throw new ModelsFetcherException("Cannot save fsa for method " + idMethod);
		} 
	}
	
	public IoModelIterator getIoModelIteratorEnter(String methodSignature) throws ModelsFetcherException {
		return new CollectionIoModelIterator ( getIoCollection( getIoModelEnterFile(methodSignature)) );
	}
	
	public IoModelIterator getIoModelIteratorExit(String methodSignature) throws ModelsFetcherException {
		return new CollectionIoModelIterator ( getIoCollection(getIoModelExitFile(methodSignature)) );
	}
	
	public Set<String> getIoModelsNames() {
		return  ioIndexEnter.getNames();
	}

	public boolean interactionModelExist(String methodSignature) {
		// TODO Auto-generated method stub
		return false;
	}
	
	public boolean ioModelEnterExist(String methodSignature) {
		try {
			return getIoModelEnterFile(methodSignature).exists();
		} catch (ModelsFetcherException e) {
			return false;
		}
	}

	public boolean ioModelExitExist(String methodSignature) {
		try{
			return getIoModelExitFile(methodSignature).exists();
		} catch (ModelsFetcherException e) {
			return false;
		}
	}

	public void addInteractionModel(String methodName, File fsaFile) throws ModelsFetcherException {
		// TODO Auto-generated method stub	
	}

}
