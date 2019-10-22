package recorders;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import conf.EnvironmentalSetter;
import conf.ViolationsRecorderSettings;

/**
 * Record violations adding the name of the current system test case.
 * The name of the currenly running test case must be written in the first line 
 * of the file specified in the FileTCSystemViolationsRecorder config file.
 * 
 * @author Fabrizio Pastore fabrizio.pastore AT gmail.com
 *
 */
public class FileTCSystemViolationsRecorder extends FileTCViolationsRecorder {

	@Override
	protected String getTestName() throws ViolationRecorderException {
		ViolationsRecorderSettings settings = EnvironmentalSetter.getViolationsRecorderSettings();
		String file = settings.getProperty("currentTestCaseFile");
		
		try {
			BufferedReader br = new BufferedReader( new FileReader(file) );
			String testName = br.readLine();
			br.close();
			return testName;
		} catch (IOException e) {
			throw new ViolationRecorderException(e.getMessage());
		}
	}

}

