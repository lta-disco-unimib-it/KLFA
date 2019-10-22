package traceReaders.raw;

import conf.BctSettingsException;
import conf.EnvironmentalSetter;
import conf.InvariantGeneratorSettings;

public class TraceReaderFactory {
	private static TracesReader tr = null;
	
	public static TracesReader getReader() throws BctSettingsException{
		if ( tr != null )
			return tr;
		
		InvariantGeneratorSettings trs = EnvironmentalSetter.getInvariantGeneratorSettings();
		TracesReader tr;
		
		try {
			tr = (TracesReader) trs.getTraceReaderType().newInstance();
			tr.init(trs);
			return tr;
		} catch (InstantiationException e) {
			throw new BctSettingsException(e.getMessage());
		} catch (IllegalAccessException e) {
			throw new BctSettingsException(e.getMessage());
		} catch (BctSettingsException e) {
			tr = null;
			throw e;
		}
		
	}

}
