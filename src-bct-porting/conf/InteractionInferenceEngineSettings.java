/*
 * this class stores configuration variables for the engines that infer
 * interaction invariants
 * 
 */
package conf;

import java.util.Properties;

import grammarInference.Log.ConsoleLogger;
import grammarInference.Log.FileLogger;
import grammarInference.Log.Logger;

public class InteractionInferenceEngineSettings {

	public static final String NO_MINIMIZATION = "none";
	public static final String STEP = "step";
	public static final String END = "end";

	public static final String KTAIL = "kTail";
	public static final String KBEHAVIOR = "kBehavior";
	public static final String COOK = "cook";
	public static final String KINCLUSION = "kInclusion";
	public static final String REISS = "reiss";

	
	
	public static Logger logger;
	private int verbose;
	private String logFile; //not used now
	
	
	private String engineName;

	private int minTrustLen;
	private int maxTrustLen;
	private boolean cutOffSearch;
	private String enableMinimization;
	
	public InteractionInferenceEngineSettings( Properties properties ) throws SettingsException{
		try{
			setVerbose( Integer.parseInt( properties.getProperty("level") ) );// 0 no output, 3 max
		} catch ( Exception e ) {
			throw new SettingsException("Property level not properly set");
		}
		/*
		 * val if (val.equals("Console")) {
		 */
		try {
			setLogger(new ConsoleLogger( getVerbose() ) );
		} catch ( Exception e ) {
			throw new SettingsException("Property level not properly set");
		}
		
		/*
		 * else System.out... Invalid Logger <COME SOPRA>
		 */
		try {
			setLogFile(properties.getProperty("logfile"));
		} catch ( Exception e ) {
			throw new SettingsException("Property logfile not properly set");
		}
		
		try {
			setMinTrustLen( Integer.parseInt(properties.getProperty("minTrustLen"))); // k
		} catch ( Exception e ) {
			throw new SettingsException("Property minTrustLen not properly set");
		}
		
		try {
			setMaxTrustLen(Integer.parseInt(properties.getProperty("maxTrustLen")));
		} catch ( Exception e ) {
			throw new SettingsException("Property maxTrustLen not properly set");
		}
		
		try {
		// STEP corresponds to END for inference engine different from kBehavior
			setEnableMinimization(properties.getProperty("enableMinimization"));
		} catch ( Exception e ) {
			throw new SettingsException("Property enableMinimization not properly set");
		}
		try {
			setCutOffSearch(Boolean.parseBoolean((properties.getProperty("cutOffSearch"))));
		} catch ( Exception e ) {
			throw new SettingsException("Property cutOffSearch not properly set");
		}
	}
	
	
	public String getLogFile() {
		return logFile;
	}
	
	public void setLogFile(String logFile) {
		this.logFile = logFile;
	}
	
	public Logger getLogger() {
		return logger;
	}
	
	public void setLogger(Logger logger) {
		this.logger = logger;
	}
	
	public int getMaxTrustLen() {
		return maxTrustLen;
	}
	public void setMaxTrustLen(int maxTrustLen) {
		this.maxTrustLen = maxTrustLen;
	}
	public int getMinTrustLen() {
		return minTrustLen;
	}
	public void setMinTrustLen(int minTrustLen) {
		this.minTrustLen = minTrustLen;
	}
	public int getVerbose() {
		return verbose;
	}
	public void setVerbose(int verbose) {
		this.verbose = verbose;
	}

	public String getEngineName() {
		return engineName;
	}
	public void setEngineName(String engineName) {
		this.engineName = engineName;
	}

	public boolean isCutOffSearch() {
		return cutOffSearch;
	}
	public void setCutOffSearch(boolean cutOffSearch) {
		this.cutOffSearch = cutOffSearch;
	}
	public String getEnableMinimization() {
		return enableMinimization;
	}
	public void setEnableMinimization(String enableMinimization) {
		this.enableMinimization = enableMinimization;
	}
	
	public boolean getBooleanMinimization() {
		if (enableMinimization.equals(NO_MINIMIZATION)) {
			return false;
		} else {
			return true;
		}
			
	}
}
