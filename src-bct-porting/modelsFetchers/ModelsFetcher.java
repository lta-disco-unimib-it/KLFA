package modelsFetchers;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;


import conf.ModelsFetcherSettings;

import automata.fsa.FiniteStateAutomaton;

/**
 * 
 * Interface for all the fetchers of models for methods
 * 
 * @author Fabrizio Pastore fabrizio.pastore AT gmail.com
 *
 */
public interface ModelsFetcher {
	
	/**
	 * Return an FSA given the method name.
	 * If an FSA for the method does not exists or if there is a problem during read throws a ModelsFetcherException
	 * 
	 * @param methodSignature	the method for which we want the model
	 * @return	the FSA
	 * 
	 * @throws ModelsFetcherException
	 */
	public FiniteStateAutomaton getInteractionModel(String methodSignature) throws ModelsFetcherException;
	
	/**
	 * Returns if there is an Interaction model for the give method.
	 * 
	 * @param methodSignature
	 * @return
	 */
	public boolean interactionModelExist( String methodSignature );
	
	/**
	 * Returns if there is an IO model for the give method.
	 * 
	 * @param methodSignature
	 * @return
	 */
	public boolean ioModelEnterExist( String methodSignature );
	
	/**
	 * Returns if there is an IO model for the give method.
	 * 
	 * @param methodSignature
	 * @return
	 */
	public boolean ioModelExitExist( String methodSignature );
	
	/**
	 * Return an iterator over the expressions of a method model 
	 * If a model for the method does not exists or if there is a problem during read throws a ModelsFetcherException
	 * 
	 * @param methodSignature	the method for which we want the model
	 * @return	the ioExpressionCollection
	 * 
	 * @throws ModelsFetcherException
	 */
	public IoModelIterator getIoModelIteratorEnter(String methodSignature) throws ModelsFetcherException;
	
	/**
	 * Return an iterator over the expressions of a method model
	 * If a model for the method does not exists or if there is a problem during read throws a FileModelFetcherException
	 * 
	 * @param methodSignature	the method for which we want the model
	 * @return	the ioExpressionCollection
	 * 
	 * @throws ModelsFetcherException
	 */
	public IoModelIterator getIoModelIteratorExit(String methodSignature) throws ModelsFetcherException;


	/**
	 * Returns an ArrayList with the names of the methods for wich we have an IO model
	 *  
	 * @return
	 */
	public Set<String> getIoModelsNames();
	
	/**
	 * This method is common to all ModelsFetcher it is called by the ModelsFetcherFactory after istantiating the object.
	 * ModelsFetcherSettings is not passed to the constructor because Class.newInstance does not permit it 
	 * (and reflection costs too much).
	 * 
	 * @param mfs	ModelsFetcherSetting, settings of the models fetcher.
	 */
	public void init(ModelsFetcherSettings mfs);
	
	public void addIoModel( String methodName, IoModel model )  throws ModelsFetcherException;
	
	public void addInteractionModel( String methodName, File fsaFile ) throws ModelsFetcherException;
	
	/**
	 * used to generate GKTail interaction models 
	 */
	public void addInteractionModel( int idMethod, ByteArrayOutputStream fsa ) throws ModelsFetcherException;
	
}
