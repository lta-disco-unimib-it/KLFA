/*
 * This class set environmental values for all classes in this package
 *  
 */
package conf;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Observer;
import java.util.Properties;


import traceReaders.raw.DtraceFileFilter;

public class EnvironmentalSetter  {

	private static Properties properties;

	private static DataRecorderSettings dataRecorderSettings;
	private static ViolationsRecorderSettings violationsRecorderSettings;
	private static ModelsFetcherSettings modelsFetcherSettings;
	private static InvariantGeneratorSettings invariantGeneratorSettings;

	private static InteractionInferenceEngineSettings inferenceEngineSettings;
	private static SimplifyTheoremProverSettings simplifyTheoremProverSettings;
	private static TraceReaderSettings traceReaderSettings;

	private static BCTFileFilter interactionTraceFilter;

	private static String interactionTraceExtension = "int" ;

	private static DBConnectionSettings dbConnectionSettings;
	
	private static Class flattenerType = null;

	private static BCTFileFilter ioTraceFilter;

	private static String ioTraceExtension = "dtrace";

	private static ArrayList<EnvironmentalSetterObserver> observers = new ArrayList<EnvironmentalSetterObserver>();

	private static String bctHome = null;

	
	public static void setBctHome( String bctHome ){
		EnvironmentalSetter.bctHome= bctHome;
	}
	
	public static String getBctHome() {
		
		//TODO: is this still necessary?
		
		if (bctHome == null) {
			bctHome  = System.getenv("BCT_HOME");
			if ( bctHome == null ){
				System.out.println("ERROR: The environmental variable BCT_HOME is not specified. The application will abort.");
				System.exit(1);
			}
		}
		return bctHome;
	}


	public static String getBctHomeNoJAR() {
		String bctHome = getBctHome();
		if (bctHome.endsWith(".jar")) {
			bctHome = bctHome.substring(0,bctHome.lastIndexOf("\\"));
		}
		return bctHome;
	}

	/**
	 * 
	 *@deprecated
	 */
	public static void setStreams() {
		//if (outputFile == null) setOutputFile();
		//if (outputFile.equals("CONSOLE")) return;
		try {
			System.setErr(new PrintStream(new FileOutputStream(new File(
			"OutputInvariantsCheck.txt"), true)));
			System.setOut(new PrintStream(new FileOutputStream(new File(
			"OutputInvariantsCheck.txt"), true)));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	/*
	 * 
	 * public static String getDaikonHome() { String DaikonHome =
	 * System.getenv("DAIKONPARENT"); if (DaikonHome==null) {
	 * System.out.println("ERROR: The environmental variable DAIKON is not
	 * specified. The application will abort."); System.exit(1); } return
	 * DaikonHome; }
	 */

	public static void setConfigurationValues(FileInputStream fileStream) {
		properties = new Properties();
		try {
			properties.load(fileStream);
		} catch (FileNotFoundException e) {
			System.out.println("Properties file has been not found.");
			e.printStackTrace();
			System.exit(1);
		} catch (IOException e) {
			System.out.println("Properties file has not been opened.");
			e.printStackTrace();
			System.exit(1);
		}

		// setting classpath variables
		setClasspathVariables();
		
		// setting variables of object flattening
		setObjectFlattenerVariables();

		// setting variables of Daikon
		setDaikonVariables();

	}






	public static void setConfigurationValues() {
		
		try {
			setConfigurationValues(new FileInputStream(getBctHomeNoJAR()
					+ File.separator+"conf"+File.separator+"files"+File.separator+"BCT.properties"));
		} catch (FileNotFoundException e) {
			System.out.println("Properties file has been not found.");
			e.printStackTrace();
			System.exit(1);
		} 
	}


	private static void setClasspathVariables() {

		ClassPath.setXmlParserPath(properties.getProperty("setXmlParserPath"));
		ClassPath.setDaikonPath(properties.getProperty("setDaikonPath"));
	}	

	public static void setDaikonVariables() {
		try{
			Daikon.setConfidenceLevel(convertStringToDouble(properties
					.getProperty("confidenceLevel")));
			Daikon.setOperatorFile(getBctHomeNoJAR()
					+ File.separator + "conf" + File.separator + "files" 
					+ File.separator + "DaikonSettings.txt");
		} catch ( Exception e ){
			//TODO: add code for default case
		}
	}

	public static void setObjectFlattenerVariables() {
		

		String flattenerTypeName = (String) properties.get("flattenerType");
		
		try {
			flattenerType = Class.forName(flattenerTypeName);
		} catch (Exception e) {
			e.printStackTrace();
			flattenerType = flattener.flatteners.ObjectFlattener.class;
		}


		String confDir = File.separator+"conf"+File.separator+"files"+File.separator;
		ObjectFlattener.setObjectFlattenerPropertiesFileName(getBctHomeNoJAR()
				+ confDir +"objectFlattener.conf");
		ObjectFlattener.setClassesToIgnoreListFileName(getBctHomeNoJAR()
				+ confDir + "classesToIgnore.list");
		ObjectFlattener.setPluginsListFileName(getBctHomeNoJAR()
				+ confDir + "plugins.list");
		ObjectFlattener.setInspectorRecognizersListFileName(getBctHomeNoJAR()
				+ confDir + "inspectorRecognizers.list");
		ObjectFlattener.setFieldsFilterFileName(getBctHomeNoJAR()
				+ confDir + "fieldsFilter.conf");
	}

	public static int convertStringToInt(String value) {
		Integer number = null;
		int level = number.parseInt(value);
		return level;
	}

	public static boolean convertStringToBoolean(String value) {
		boolean state = true;
		if (value.equalsIgnoreCase("true")) {
			state = true;
		} else if (value.equalsIgnoreCase("false")) {
			state = false;
		}
		return state;
	}

	public static double convertStringToDouble(String value) {
		Double number = null;
		double level = number.parseDouble(value);
		return level;
	}
	
	public static void setSimplifyTheoremProverSettings() {
		try {
			Properties p = getPropertiesFromConfFile("simplifyTheoremProverSettings");
			simplifyTheoremProverSettings = new SimplifyTheoremProverSettings(p);
			notifyObservers();
		} catch (Exception e) {
			e.printStackTrace();
		}		
	}
	

	private static void setDBConnectionSettings(){
		try {
			Properties p = getPropertiesFromConfFile( "dbConnectionSettings" );
			dbConnectionSettings = new DBConnectionSettings(p);
			notifyObservers();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public static void setDBConnectionSettings(DBConnectionSettings settings){
		dbConnectionSettings = settings;
		notifyObservers();
	}
	
	private static void setDataRecorderSettings(){
		try {
			Properties p = getPropertiesFromConfFile( "dataRecorderSettings" );
			Class type = getSettingsType(p);
			dataRecorderSettings = new DataRecorderSettings(type,p);
			notifyObservers();
		} catch (Exception e) {
			e.printStackTrace();
		}
	
	}


	private static void setViolationsRecorderSettings(){
		try {
			Properties p = getPropertiesFromConfFile( "violationsRecorderSettings" );
			Class type = getSettingsType(p);
			violationsRecorderSettings = new ViolationsRecorderSettings(type,p);
			notifyObservers();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	private static void setModelsFetcherSettings(){
		try {
			Properties p = getPropertiesFromConfFile( "modelsFetcherSettings" );
			setModelsFetcherSettings(p);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public static void setModelsFetcherSettings( Properties p ){
		try {
			Class type = getSettingsType(p);
			modelsFetcherSettings = new ModelsFetcherSettings(type,p);
			notifyObservers();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}



	private static Class getSettingsType( Properties p ) throws SettingsException{
		String type = (String) p.get("type");
		if ( type == null ){
			throw new SettingsException("No type key");
		}
		
		try {
			return Class.forName(type);
		} catch (ClassNotFoundException e) {
			throw new SettingsException("Type key does not match a real class : "+type);
		}

	}

	/**
	 * This method returns the Properties object built reading the file indicated in propertyName field of  BCT.properties
	 * 
	 * @param propertyName
	 * @return
	 * @throws IOException
	 * @throws SettingsException 
	 */
	private static Properties getPropertiesFromConfFile( String propertyName ) throws SettingsException{
		
		if ( properties == null ){
			setConfigurationValues();
		}
		String file = (String) properties.get(propertyName);

		if ( file == null )
			throw new SettingsException("Property "+propertyName+" not set in BCT.properties ");

		

		//FIXME: change in order to load from jar file

		File propertyFile = new File ( EnvironmentalSetter.getBctHome()+
				File.separator+"conf"+
				File.separator+"files"+
				File.separator+file);
		
		return getPropertiesFromFile(propertyFile,propertyName);
	}
	
	private static Properties getPropertiesFromFile(File propertyFile,String propertyName) throws SettingsException{
		Properties p = new Properties();
		
		if ( ! propertyFile.exists() ){
			throw new SettingsException("Property "+propertyName+" point to "+propertyFile+" but this does not exists");
		}

		try {
			FileInputStream fis;
			fis = new FileInputStream( propertyFile );
			p.load(fis);
			fis.close();

		} catch (FileNotFoundException e) {
			throw new SettingsException("Problem reading "+propertyFile+" "+e.getMessage()+" "+e.getCause());
		} catch (IOException e) {
			throw new SettingsException("Problem reading "+propertyFile+" "+e.getMessage()+" "+e.getCause());
		}

		return p;

	}

	public static void main(String[] args) {
		getBctHomeNoJAR();
	}


	public static DataRecorderSettings getDataRecorderSettings() {
		if ( dataRecorderSettings == null )
			setDataRecorderSettings();
		return dataRecorderSettings;
	}


	public static ModelsFetcherSettings getModelsFetcherSettings() {
		if ( modelsFetcherSettings == null )
			setModelsFetcherSettings();
		return modelsFetcherSettings;
	}


	public static ViolationsRecorderSettings getViolationsRecorderSettings() {
		if ( violationsRecorderSettings == null )
			setViolationsRecorderSettings();
		return violationsRecorderSettings;
	}


//	public static TraceReaderSettings getTraceReaderSettings(){
//		if ( traceReaderSettings == null )
//			setTraceReaderSettings();
//		return traceReaderSettings;
//	}

	public static DBConnectionSettings getDBConnectionSettings(){
		if ( dbConnectionSettings == null )
			setDBConnectionSettings();
		return dbConnectionSettings;
	}

	public static SimplifyTheoremProverSettings getSimplifyTheoremProverSettings() {
		if (simplifyTheoremProverSettings == null) {
			setSimplifyTheoremProverSettings();
		}
		return simplifyTheoremProverSettings;
	}
	
//	private static void setTraceReaderSettings(){
//		try {
//			Properties p = getPropertiesFromConfFile( "traceReaderSettings" );
//			Class type = getSettingsType(p);
//			traceReaderSettings = new TraceReaderSettings(type,p);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}

	private static void setInvariantGeneratorSettings() {
		Properties p;
		try {
			p = getPropertiesFromConfFile( "invariantGeneratorSettings" );
			setInvariantGeneratorSettings(p);
			notifyObservers();
		} catch (SettingsException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
//	private static void setGKTailInvariantGeneratorSettings() {
//		
//		Properties p;
//		try {
//			p = getPropertiesFromConfFile( "invariantGeneratorSettings" );
//			setGKTailInvariantGeneratorSettings(p);
//		} catch (SettingsException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}	
//	}
	
	public static void setInvariantGeneratorSettings( Properties p ){
		try {
			Class type = getSettingsType(p);
			invariantGeneratorSettings = new InvariantGeneratorSettings(type,p);
			notifyObservers();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void setInvariantGeneratorSettings( InvariantGeneratorSettings settings ){
		invariantGeneratorSettings = settings;
		notifyObservers();
	}
	
//	public static void setGKTailInvariantGeneratorSettings( Properties p ){
//		try {
//			Class type = getSettingsType(p);
//			gktailInvariantGeneratorSettings = new GKTailInvariantGeneratorSettings(type,p);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}

	public static FilenameFilter getInteractionTraceFilter() {
		if ( interactionTraceFilter == null )
			interactionTraceFilter = new BCTFileFilter( interactionTraceExtension  );
		return interactionTraceFilter;
	}


	public static InvariantGeneratorSettings getInvariantGeneratorSettings() {
		if ( invariantGeneratorSettings == null ){
			setInvariantGeneratorSettings();
		}
		return invariantGeneratorSettings;
	}
	
//	public static GKTailInvariantGeneratorSettings getGKTailInvariantGeneratorSettings() {
//		if ( gktailInvariantGeneratorSettings == null )
//			setGKTailInvariantGeneratorSettings();
//		return gktailInvariantGeneratorSettings;
//	}

	
	private static void setInferenceEngineSettings( Properties p ) throws SettingsException{
		inferenceEngineSettings = new InteractionInferenceEngineSettings(p);
		notifyObservers();
	}

	public static InteractionInferenceEngineSettings getInferenceEngineSettings() {
		if ( inferenceEngineSettings == null ){
				Properties p;
				try {
					p = getPropertiesFromConfFile( "interactionInferenceEngineSettings" );
					setInferenceEngineSettings(p);
				} catch (SettingsException e) {
					e.printStackTrace();
				}
				
		}
		return inferenceEngineSettings;
	}
	
	public static void setInvariantGeneratorSettingsFile( String filePath ) throws SettingsException{
		setProperty( "invariantGeneratorSettings", filePath );
		File file = new File(filePath);
		
		Properties p = getPropertiesFromFile(file, "invariantGeneratorSettings");
		setInvariantGeneratorSettings(p);
	}


	private static void setProperty(String key, String value) {
		if ( properties == null )
			setConfigurationValues();
		
		properties.setProperty(key, value);
	}


	public static void setModelsFetcherSettingsFile(String modelsFetcherFilePath) throws SettingsException {
		File propertyFile = new File (modelsFetcherFilePath);
		Properties p = getPropertiesFromFile(propertyFile, "modelsFetcherSettings");
		setModelsFetcherSettings(p);
	}

	public static void setInferenceEngineSettingsFile(String inferenceEngineFilePath) throws SettingsException {
		File propertyFile = new File (inferenceEngineFilePath);
		Properties p = getPropertiesFromFile(propertyFile, "interactionInferenceEngineSettings");
		setInferenceEngineSettings(p);
	}

	public static Class getFlattenerType() {
		if ( flattenerType == null ){
			setConfigurationValues();
		}
		return flattenerType;
	}


	public static void setFlattenerType(Class flattenerType) {
		EnvironmentalSetter.flattenerType = flattenerType;
	}


	public static BCTFileFilter getIoTraceFilter() {
		if ( ioTraceFilter == null )
			ioTraceFilter = new BCTFileFilter( ioTraceExtension   );
		return ioTraceFilter;
	}

	/**
	 * Add an object to the list of the observers that want to be informed when a change in the environmentalSetter has occurred.
	 * These objects are notifyed after a setter is called.
	 *  
	 * @param observer
	 */
	public static void addChangeObserver( EnvironmentalSetterObserver observer ){
		observers.add(observer);
	}
	
	private static void notifyObservers(){
		if ( observers.size() == 0 ){
			return;
		}
		EnvironmentalSetterEvent e = new EnvironmentalSetterEventUpdate();
		for ( EnvironmentalSetterObserver o : observers ){
			o.update(e);
		}
	}

	public static void reset() {
		dataRecorderSettings=null;
		violationsRecorderSettings=null;
		modelsFetcherSettings=null;
		invariantGeneratorSettings=null;

		inferenceEngineSettings=null;
		simplifyTheoremProverSettings=null;
		traceReaderSettings=null;
	}

}