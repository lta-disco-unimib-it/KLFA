package conf;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Properties;
import java.util.Set;



public class InvariantGeneratorSettings extends ConfigurationSettings {
	private File temporaryDir;
	
	private Class normalizedTraceHandlerType;
	private Class traceReaderType;
	private File distilledDir;

	private String fsaEngine;

	private String daikonConfig;



	private Set<String> methodsToIgnore;

	private boolean addAdditionalInvariants = false;

	private String daikonAdditionalOptions;

	private int parallelInferenceThreads; 
	private boolean expandReferences = true;

	private boolean optimizationEnabled; 
	
	public interface Options{
		public final String temporaryDir = "temporaryDir";
		public final String normalizedTraceHandlerType = "normalizedTraceHandler.type";
		public final String traceReaderType = "traceReader.type";
		public final String fsaEngine = "FSAEngine";
		public final String daikonConfig = "daikonConfig";
		public final String daikonAdditionalOptions = "daikonAdditionalOptions";
		public final String methodsToIgnoreFile = "methodsToIgnoreFile";
		public final String addAdditionalInvariants = "addAdditionalInvariants";
		public final String expandReferences = "expandReferences";
		public final String parallelInferenceThreads = "parallelInferenceThreads";
		public final String metaDataHandlerSettingsType = "metaDataHandlerSettingsType";
		public final String enableOptimization = "enableOptimization";
	}
	
	public InvariantGeneratorSettings(Class type, Properties p) throws SettingsException {
		super(type, p);
		
		temporaryDir = new File(p.getProperty(Options.temporaryDir));
		temporaryDir.mkdirs();
		if ( ! temporaryDir.exists() )
			throw new SettingsException("Cannot set temporary dir "+temporaryDir);
		
		String typeN = (String) p.getProperty(Options.normalizedTraceHandlerType);
		try {
			normalizedTraceHandlerType = Class.forName( typeN );
		} catch (ClassNotFoundException e) {
			throw new SettingsException("Wrong normalizedTraceHandlerType : "+typeN);
		}
	
		String typeR = (String) p.getProperty(Options.traceReaderType);
		try {
			traceReaderType = Class.forName( typeR );
		} catch (ClassNotFoundException e) {
			throw new SettingsException("Wrong traceReaderType");
		}

		
		String addAdditionalInvariantsString = (String) p.getProperty(Options.addAdditionalInvariants);
		if ( addAdditionalInvariantsString != null ){
			try {
				addAdditionalInvariants  = Boolean.valueOf(addAdditionalInvariantsString);
			} catch ( Exception e ){
				throw new SettingsException("addAdditionalInvariants must be true or false");
			}
		}
		
	
		fsaEngine = (String) p.getProperty(Options.fsaEngine);
		
		daikonConfig = (String) p.getProperty(Options.daikonConfig);
		
		daikonAdditionalOptions = (String) p.getProperty(Options.daikonAdditionalOptions);
		if ( daikonAdditionalOptions == null ){
			daikonAdditionalOptions = "";
		}
		
		distilledDir = new File ( temporaryDir, "distilled");
		distilledDir.mkdirs();
		
		
		methodsToIgnore = getMethodsToIgnore( p.getProperty( Options.methodsToIgnoreFile ) );
	
		
		String expandReferencesString = p.getProperty(Options.expandReferences);
		if ( expandReferencesString != null ){
			try{
				expandReferences = Boolean.valueOf(expandReferencesString);
			} catch ( Exception e ){
				throw new SettingsException("Wrong value for "+Options.expandReferences+", can be one of true or false. Found "+expandReferencesString);
			}
		}
		
		
		String enableOptimizationString = p.getProperty(Options.enableOptimization);
		if ( enableOptimizationString != null ){
			try{
				optimizationEnabled = Boolean.valueOf(enableOptimizationString);
			} catch ( Exception e ){
				throw new SettingsException("Wrong value for "+Options.enableOptimization+", can be one of true or false. Found "+enableOptimizationString);
			}
		}
		
		
		
		
		String metaSettings = (String) p.getProperty(Options.metaDataHandlerSettingsType);
		
		
		
		String parallelInferenceThreadsString = (String) p.getProperty(Options.parallelInferenceThreads);
		if ( parallelInferenceThreadsString != null ){
			parallelInferenceThreads = Integer.valueOf(parallelInferenceThreadsString);
		}
		
		if ( parallelInferenceThreads < 1 ){
			parallelInferenceThreads = 1;
		}
		
		
		
	}
	
	private Properties extractMataDataHandlerProperties ( String metaDataHandlerClassName, Properties p ){
		Iterator it = p.keySet().iterator();
		String prefix = metaDataHandlerClassName+".";
		
		Properties properties = new Properties();
		
		while ( it.hasNext() ){
			String property = (String) it.next();
			if ( property.startsWith(prefix) ){
				properties.put(property.substring(prefix.length()), p.getProperty(property));
			}
		}
		return properties;
	}

	private Set<String> getMethodsToIgnore(String fileName) throws SettingsException {
		Set<String> toIgnore = new HashSet<String>();
		if ( fileName == null )
			return toIgnore;
		File file = new File(fileName);
		if ( ! file.exists() )
			throw new SettingsException("InvariantGeneratorSettings, "+Options.methodsToIgnoreFile+" : file "+fileName+" does not exist");
		Properties p = new Properties();
		InputStream is;
		try {
			is = new FileInputStream(file);
			p.load(is);
			
			for ( Object o : p.keySet() ){
				toIgnore.add(o.toString());
			}
			is.close();
			return toIgnore;
		} catch (FileNotFoundException e) {
			throw new SettingsException("InvariantGeneratorSettings, "+Options.methodsToIgnoreFile+" : file "+fileName+" cannot be read");
		} catch (IOException e) {
			throw new SettingsException("InvariantGeneratorSettings, "+Options.methodsToIgnoreFile+" : file "+fileName+" cannot be read");
		}
		
	}

	public File getTemporaryDir() {
		return temporaryDir;
	}

	public Class getNormalizedTraceHandlerType() {
		return normalizedTraceHandlerType;
	}

	public File getDistilledDir() {
		return distilledDir;
	}

	public Class getTraceReaderType() {
		return traceReaderType;
	}

	public String getFSAEngine() {
		return fsaEngine;
	}

	public String getDaikonConfig() {
		return daikonConfig;
	}

	public Set<String> getMethodsToIgnore() {
		return methodsToIgnore;
	}

	/**
	 * Returns true if InvariantGenerator can add additional derivide invariants like NOT NULL statements
	 * 
	 * @return
	 */
	public boolean addAdditionInvariants() {
		return addAdditionalInvariants;
	}

	public String getDaikonAdditionalOptions() {
		return daikonAdditionalOptions;
	}

	/**
	 * Returns the number of parallel threads to execute in the invariant generation process.
	 * The value i sset by the user in the iNvariantGeneratorSettings.conf file
	 *  
	 * @return
	 */
	public int getParallelInferenceThreads() {
		return parallelInferenceThreads;
	}
	
	public boolean isExapandReferences() {
		return expandReferences;
	}



	public boolean isOptimizationEnabled() {
		return optimizationEnabled;
	}




	

}
