package conf;

import java.util.Properties;

/**
 * @author Davide Lorenzoli
 *
 */
public class SimplifyTheoremProverSettings {

	private String temporaryDir;
	private String executableFile;
	
	public SimplifyTheoremProverSettings(Properties p) {
		temporaryDir = p.getProperty("temporaryDir");
		executableFile = p.getProperty("executableFile");
	}

	/**
	 * @return the executableFile
	 */
	public String getExecutableFile() {
		return executableFile;
	}

	/**
	 * @return the temporaryDir
	 */
	public String getTemporaryDir() {
		return temporaryDir;
	}	
}
