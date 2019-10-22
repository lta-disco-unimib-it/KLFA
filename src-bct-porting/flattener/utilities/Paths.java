package flattener.utilities;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

/**
 * @author Davide Lorenzoli
 */
public class Paths {
	private Properties properties;
	
	/**
	 * @param propertiesFileName
	 */
	public Paths(String propertiesFileName) {
		FilesFinder fileFinder = new FilesFinder();		
		properties = new Properties();		
		try {			
			File propertiesFile = fileFinder.find(System.getProperty("user.dir"), propertiesFileName, true)[0];
			properties.load(new FileInputStream(propertiesFile));
		} catch (FileNotFoundException e) {			
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * @param propertiesFile
	 */
	public Paths(File propertiesFile) {			
		properties = new Properties();		
		try {						
			properties.load(new FileInputStream(propertiesFile));
		} catch (FileNotFoundException e) {			
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}	
	
	public String getPath(String propertyName) { 
		return properties.getProperty(propertyName);
	}
}
