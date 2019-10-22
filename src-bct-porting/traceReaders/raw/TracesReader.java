package traceReaders.raw;

import java.io.File;
import java.util.Iterator;

import traceReaders.TraceReaderException;

import conf.BctSettingsException;
import conf.InvariantGeneratorSettings;
import conf.TraceReaderSettings;
import database.DataLayerException;

public interface TracesReader {

	/**
	 * Used to initialize internal TraceReader parameters
	 * @param trs
	 * @throws BctSettingsException
	 */
	public void init( InvariantGeneratorSettings trs ) throws BctSettingsException;
	
	/**
	 * Returns an Iterator over all IoTraces
	 * @return
	 * @throws TraceException 
	 * @throws TraceException 
	 * @throws FileReaderException 
	 */
	public Iterator<IoTrace> getIoTraces() throws TraceException, TraceReaderException;

	/**
	 * Returns an Iterator over all Interaction Traces
	 * @return
	 */
	public Iterator<InteractionTrace> getInteractionTraces() throws TraceException;

	/**
	 * Returns the IO trace of the passed method.
	 * The passed name must be exactly equeals to the name of the method recorded in the data recording phase.
	 * 
	 * @param methodName
	 * @return
	 * @throws TraceReaderException 
	 */
	public IoTrace getIoTrace(String methodName) throws TraceReaderException;

}
