package conf;

public class SettingsException extends Exception {

	public SettingsException(String msg) {
		super(msg);
	}

	public SettingsException(String string, Exception e ) {
		super(string,e);
	}

}
