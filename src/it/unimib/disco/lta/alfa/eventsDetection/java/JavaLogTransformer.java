package it.unimib.disco.lta.alfa.eventsDetection.java;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;



public abstract class JavaLogTransformer {

	private File src;
	private File dst;
	private BufferedReader fr;
	private FileWriter wr;
	//private String curLine;
	private ArrayList<String> parameters;
	private boolean detectActions;
	protected String curComponent;
	protected String curEvent;
	protected String logDate = "\\w\\w\\w \\d+, \\d\\d\\d\\d \\d+:\\d+:\\d+ \\w\\w .*";
	private String separator = "\t";
	protected static final String actionPrefix = "ACTION";
	protected boolean skip = false;
	
	private static class Actions {
		public final String starting = "INFO: Starting";
		public final String initializing = "INFO: Initializing";
		public final String deploying = "INFO: Deploying";
	}

	protected JavaLogTransformer(File src, File dst){
		this.src = src;
		this.dst = dst;
	}
	
	protected void transform() throws IOException {
		fr = new BufferedReader(new FileReader(src));
		
		wr = new FileWriter(dst);
		
		parameters= new ArrayList<String>();
		
		boolean action = false;
		String line;
		StringBuffer parameter = new StringBuffer();
		while ( (line = fr.readLine()) != null  ){
			if( isLogStart(line) ){
				if ( ! skip ){
					parameters.add(parameter.toString());
					if ( action ){
						saveCurLineAction();
					} else {
						saveCurLine();
					}
				} else {
					skip = false;
				}
				String[] elements = line.split(" ");
				
				curComponent = elements[elements.length-2];
				curEvent = elements[elements.length-1];
				
				
				
				parameter = new StringBuffer();
			} else {
				if ( isAction(line) ){
					action = true;
				}
				
				line = processLine(line);
				parameter.append(line);
				parameter.append("\\n");
			}
			
			
			
		}
		saveCurLine();
		
		wr.close();
		fr.close();
	}

	protected abstract String processLine(String line);
	

	private void saveCurLineAction() {
		// TODO Auto-generated method stub
		
	}

	private boolean isAction(String line) {
		if ( ! detectActions ){
			return false;
		}
		
		return getActionName(line) != null;
		
	}

	protected abstract String getActionName(String line);

	protected void saveCurLine() throws IOException {
		if( curComponent != null ){
			StringBuffer parcontent = new StringBuffer();
			boolean first = true;
			for( String par : parameters ){
				if ( first ){
					first = false;
					parcontent.append(separator);
				}
				parcontent.append(par);
			}
			
			
			wr.write(curComponent);
			wr.write(separator);
			
			wr.write(curEvent);
			wr.write(separator);
			
			wr.write(parcontent.toString());
			wr.write("\n");
			
			
			curComponent = null;
			parameters = new ArrayList<String>();
		}
	}

	protected boolean isLogStart(String line) {
		return line.matches(logDate ); 
	}

	public boolean isDetectActions() {
		return detectActions;
	}

	public void setDetectActions(boolean detectActions) {
		this.detectActions = detectActions;
	}

	public String getSeparator() {
		return separator;
	}

	public void setSeparator(String separator) {
		this.separator = separator;
	}



}
