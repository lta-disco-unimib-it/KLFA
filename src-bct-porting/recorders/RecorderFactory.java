package recorders;

import conf.DataRecorderSettings;
import conf.EnvironmentalSetter;
import conf.ViolationsRecorderSettings;

public class RecorderFactory {
	private static DataRecorder loggingRecorder = null;
	private static ViolationsRecorder violationsRecorder = null;
	
	/**
	 * This method return a DataRecorder as configured in BCT.properties file
	 * The method is synchronized since we want a common recorder for all threads
	 * 
	 * @return
	 */
	public static synchronized DataRecorder getLoggingRecorder(){
		if ( loggingRecorder == null ){
			try {
				
				DataRecorderSettings drs = EnvironmentalSetter.getDataRecorderSettings();
				Class recorderClass = drs.getType();
			
				loggingRecorder = (DataRecorder) recorderClass.newInstance();
				loggingRecorder.init( drs );
				
			} catch ( Exception e) {
				System.err.println("Problem loading Recorder  Loaded default.");
				loggingRecorder = new FileDataRecorder();
				((FileDataRecorder)loggingRecorder).setLogDir("bctLogging");
			}
			
		}
		return loggingRecorder;
	}

	/**
	 * The method return a ViolationRecorder as configured in prperties file
	 * The method is synchronized since we want a common recorder for all threads
	 * 
	 * @return
	 */
	public static synchronized ViolationsRecorder getViolationRecorder() {
		
		if ( violationsRecorder == null ){
			
			
			try {
				
				ViolationsRecorderSettings vrs = EnvironmentalSetter.getViolationsRecorderSettings();
				
				Class recorderClass = vrs.getType(); 
				
			
				violationsRecorder = (ViolationsRecorder) recorderClass.newInstance();
				violationsRecorder.init( vrs );
				
			} catch ( Exception e) {
				System.err.println("Problem loading Violations Recorder Loaded default.");
				e.printStackTrace();
				violationsRecorder = new FileSystemTestsViolationsRecorder();
				((FileSystemTestsViolationsRecorder)violationsRecorder).setViolationsFile("bctModelsViolations.txt");
			}
			
		}
		
		return violationsRecorder;
	}
	
}
