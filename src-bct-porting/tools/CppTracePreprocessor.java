package tools;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import conf.EnvironmentalSetter;
import recorders.FileDataRecorder;
import recorders.RecorderException;
import recorders.RecorderFactory;

public class CppTracePreprocessor {
	
	public static class CppTracePreprocessorException extends Exception {

		public CppTracePreprocessorException(String string) {
			// TODO Auto-generated constructor stub
		}
		
	}
	
	
	
	FileDataRecorder recorder;
	private final String suffixEnter = ":::ENTER";
	private final String suffixExit = ":::EXIT1";
	private long threadId = 0;
	
	CppTracePreprocessor(){
		recorder = new FileDataRecorder();
		recorder.init(EnvironmentalSetter.getDataRecorderSettings());
	}
	
	public void process( File file ) throws CppTracePreprocessorException{
		++threadId;
		try {
			BufferedReader reader = new BufferedReader(new FileReader(file));
			String inpline;
			String methodName = null;
			String suffix = null;
			CppHandler handler = null;
			while ( ( inpline = reader.readLine() ) != null ){
				String line = inpline.trim();
				
				if ( line.equals("") ){//skip white lines
				} else if ( line.endsWith(suffixEnter)){
					recordMethodInvocationInfo(methodName,handler,suffix,threadId);
					methodName = line.substring(0, line.length()-suffixEnter.length());
					suffix = suffixEnter;
					handler = new CppHandler();
				} else if  ( line.endsWith(suffixExit) ) {
					recordMethodInvocationInfo(methodName,handler,suffix,threadId);
					methodName = line.substring(0, line.length()-suffixExit.length());
					suffix = suffixExit;
					handler = new CppHandler();
				} else {
					String inspectorName = line.replace("*.", "->");
					inspectorName = inspectorName.replace("*", "->");
					inpline = reader.readLine();
					if ( inpline == null ){
						throw new CppTracePreprocessorException("Inspector value missing");
					}
					String value = inpline.trim();
					
					if ( value.startsWith("0x") ){
						if ( value.equals("0x00000000") ){
							value = "NULL";
						} else {
							value = "!NULL";
						}
						handler.add( inspectorName, value);
					} else if (value.startsWith("\"")){
						String middle;
						if ( inspectorName.endsWith(".") || inspectorName.endsWith("->") ){
							middle="";
							
						} else {
							middle=".";
						}
						handler.add( inspectorName+middle+"c_str()", value);
						handler.add( inspectorName+middle+"size()", ""+(value.length()-2) );
					} else {
						handler.add( inspectorName, value);
					}
					
					
					
				}
				
			}
			
			recordMethodInvocationInfo(methodName,handler,suffix,threadId);
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void recordMethodInvocationInfo(String methodName, CppHandler handler, String suffix, long threadId) throws CppTracePreprocessorException {
		if ( suffix == null || methodName == null )
			return;
		methodName = methodName.trim();
		methodName = methodName.replace(" ", "_").replace("::", ".");
		if ( ! methodName.contains(".") ){
			methodName=".."+methodName;
		}
		try{
		CppHandler[] handlers = new CppHandler[]{ handler };
		if ( suffix.equals(suffixEnter)){
			recorder.recordInteractionEnter(methodName, threadId);
			recorder.recordIoEnter(methodName, handlers);
		} else {
			recorder.recordInteractionExit(methodName, threadId);
			recorder.recordIoExit(methodName, handlers);
		}
		} catch ( RecorderException e ){
			throw new CppTracePreprocessorException("Recorder exception : "+e.getMessage());
		}
	}
	
	
	public static void main(String[] args){
		if ( args.length < 1 ){
			printUsage();
			System.exit(-1);
		}
		CppTracePreprocessor preprocessor = new CppTracePreprocessor();
		for ( String arg : args ){
			File file = new File(arg);
			if ( ! file.exists() ){
				System.err.println( "File "+file+" doe not exist!!\nAborting");
				System.exit(-1);
			}
			try {
				preprocessor.process(file);
			} catch (CppTracePreprocessorException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	private static void printUsage() {
		System.out.println("Usage : " +
				CppTracePreprocessor.class.getName()+" <files>\n" +
						"\n" +
						"<files> : list of trace files containing BCTC(++) recorder data \n\n");
		
	}
}
