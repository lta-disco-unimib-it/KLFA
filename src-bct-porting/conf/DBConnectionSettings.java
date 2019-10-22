package conf;

import java.util.Properties;

public class DBConnectionSettings  {
	private Properties properties;
	private String databaseURI;
	private String databaseUSER;
	private String databasePASSWORD; 
	
	public DBConnectionSettings( Properties p) throws SettingsException {
		properties = p;
		
		Object val = p.get("databaseURI");
		if ( val == null ){
			 throw new SettingsException("no databaseURI defined");
		}
		databaseURI = val.toString();
		
		val = p.get("databaseUSER");
		if ( val == null ){
			 throw new SettingsException("no databaseUSER defined");
		}
		databaseUSER=val.toString();
		
		val = p.get("databasePASSWORD");
		if ( val == null ){
			 throw new SettingsException("no databasePASSWORD defined");
		}
		databasePASSWORD=val.toString();
		
	}

	public String getProperty(String key) {
		return properties.getProperty(key);
	}

	public String getDatabasePASSWORD() {
		return databasePASSWORD;
	}

	public String getDatabaseURI() {
		return databaseURI;
	}

	public String getDatabaseUSER() {
		return databaseUSER;
	}

	public Properties getProperties() {
		return properties;
	}

	
	
	

}
