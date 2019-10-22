package modelsFetchers;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;

import util.FileIndexAppend;


import conf.BCTFileFilter;
import conf.EnvironmentalSetter;
import conf.ModelsFetcherSettings;

import automata.fsa.FiniteStateAutomaton;

/**
 * This class returns models saved as files on the fileSystem. It uses method signature as names for model files.
 * It is still in the repository (even if there is a version that uses a method index) to maintain a retro compatibility
 * in order to use models generated for the old version.
 * 
 * 
 * TODO: change the association between fileName-methodName, we need shorter names.
 * 
 * @author Fabrizio Pastore fabrizio.pastore AT gmail.com
 *
 */
public class FileModelsFetcherOld implements ModelsFetcher {
	//path to the models directories 
	private File ioModelsDir;
	private File interactionModelsDir;
	//private FilenameFilter ioModelsFilterEnter = new BCTFileFilter( "in.txt" );
	//private FilenameFilter ioModelsFilterExit = new BCTFileFilter( "out.txt" );
	private FileIndexAppend 	interactionIndex;
	private FileIndexAppend 	ioIndexEnter;
	private FileIndexAppend 	ioIndexExit;
	/**
	 * Initialization method, ModelsFetcherSettings must contain a "modelsDir" field. It indicates the directory containing the 
	 * 04-invariants BCT directory with ioInvariants and interctionInvariants.
	 * 
	 */
	public void init ( ModelsFetcherSettings options ){
		ioModelsDir = new File ( (String)options.getProperty("modelsDir")+File.separator+"ioInvariants" ); 
		interactionModelsDir = new File ( (String)options.getProperty("modelsDir")+File.separator+"interactionInvariants" ) ;
		ioModelsDir.mkdirs();
		interactionModelsDir.mkdirs();
		ioIndexEnter = new FileIndexAppend( new File(ioModelsDir, "ioModelsEnter.idx" ), ".io.enter" );
		ioIndexExit = new FileIndexAppend( new File(ioModelsDir, "ioModelsExit.idx" ), ".io.exit" );
		interactionIndex = new FileIndexAppend( new File(interactionModelsDir, "interactionModels.idx" ), ".ser" );
	}
	
	/*
	 * Return the FSA associated to the method passed as input.
	 * The model is searched in <modelsDir>/interactionInvariants/.
	 * Usually the model has the name of the method.
	 * 
	 * @see modelsFetchers.ModelsFetcher#getInteractionModel(java.lang.String)
	 */
	public FiniteStateAutomaton getInteractionModel(String methodSignature) throws ModelsFetcherException {
		
		File fsaFile = getInteractionModelFile(methodSignature);
		ObjectInputStream in = null;
		
		try {
			FiniteStateAutomaton fsa ;
			in = new ObjectInputStream(new BufferedInputStream(	new FileInputStream( fsaFile )));
			fsa = (FiniteStateAutomaton) in.readObject();
			in.close();
			return fsa;
		} catch ( Exception e) {
			throw new ModelsFetcherException("No model");
		} 
		
		 
	}

	private File getIoModelEnterFile(String methodSignature){
		String file = methodSignature+".in.txt";
		
		return new File( ioModelsDir, file );
	}
	
	private File getIoModelExitFile(String methodSignature){
		String file = methodSignature+".out.txt";
		return new File( ioModelsDir, file );
	}
	
	private File getInteractionModelFile(String methodSignature){
		String file = methodSignature+".ser";
		
		
		return new File( interactionModelsDir, file );
	}
	
	/**
	 * This method returns a Collection of all io expressions found
	 * 
	 * TODO: add a cache of models previously read
	 * 
	 * @param fileName	the name of the file we want to retrieve.
	 * @return the right IoExpressionCollection
	 */
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

	public void addIoModel(String methodName, IoModel model) throws ModelsFetcherException {
		try {
			BufferedWriter br = new BufferedWriter(
					new FileWriter( getIoModelEnterFile(methodName) )
					);
			Iterator it = model.preconditionsIt();
			while ( it.hasNext() ){
				br.write((String)it.next());
				br.newLine();
			}
			br.close();
			
			br = new BufferedWriter(
					new FileWriter( getIoModelExitFile(methodName) )
					);
			it = model.postconditionsIt();
			while ( it.hasNext() ){
				br.write((String)it.next());
				br.newLine();
			}
			
			br.close();
			
		} catch (IOException e) {
			throw new ModelsFetcherException("Cannot save IoModel for "+methodName);
		}
	}

	public void addInteractionModel(String methodName, File fsaFile) throws ModelsFetcherException {

		InputStream in;
		try {
			in = new BufferedInputStream(new FileInputStream( fsaFile ));

			OutputStream out = new BufferedOutputStream( new FileOutputStream( getInteractionModelFile(methodName) ));
			while (true) {
				int val = in.read();
				if (val == -1)
					break;
				else
					out.write(val);
			}
			in.close();
			out.close();

		} catch (FileNotFoundException e) {
			throw new ModelsFetcherException("Cannot save fsa for "+methodName);
		} catch (IOException e) {
			throw new ModelsFetcherException("Cannot save fsa for "+methodName);
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
		return getInteractionModelFile(methodSignature).exists();
		
	}

	public boolean ioModelEnterExist(String methodSignature) {
		return getIoModelEnterFile(methodSignature).exists();
	}
	
	public boolean ioModelExitExist(String methodSignature) {
		return getIoModelExitFile(methodSignature).exists();
	}

	//FIXME: used to generate GKTail interaction models 
	public void addInteractionModel(int idMethod, ByteArrayOutputStream fsa) throws ModelsFetcherException {
		// TODO Auto-generated method stub
		
	}
}
