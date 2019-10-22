/*******************************************************************************
 *    Copyright 2019 Fabrizio Pastore, Leonardo Mariani
 *   
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 *******************************************************************************/
package it.unimib.disco.lta.alfa.rawLogsPreprocessing.java;

import it.unimib.disco.lta.alfa.eventsDetection.java.JavaLogTransformer;

import java.io.File;
import java.io.IOException;



public class GlassfishLogPreprocessor extends JavaLogTransformer {

	private static class Actions{
		public static final String loadApplication = "INFO: Loading application ";
		public static final String initService = "INFO: Init service :";
		public static final String startupService = "INFO: Startup service :";
		public static final String started = "INFO: Glassfish v3 started";
		public static final String shutdown = "INFO: Server shutdown initiated";
	}
	
	GlassfishLogPreprocessor(File src, File dst) {
		super(src, dst);
	}

	protected String processLine(String line) {
		line = line.replace("\t", "\\t");
		
		line = TomcatLogPreprocessor.processLine(line, curComponent, curEvent);
		
		if ( curEvent.equals("load") ){
			line = line.substring(line.indexOf("load") + 5 ).trim();
		} else if (line.startsWith("FINEST:")) {
			skip = true;
		}
		
		
		return line;
	}
	
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		File src= new File(args[0]);
		File dst= new File(args[1]);
		
		GlassfishLogPreprocessor trans = new GlassfishLogPreprocessor(src,dst);
		trans.setDetectActions(true);
		try {
			trans.transform();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	@Override
	protected String getActionName(String line) {
		String content = null;
		if ( curComponent.equals("com.sun.enterprise.web.WebDeployer") ){
			if ( line.startsWith(Actions.loadApplication) ){
				content="LOADAPP_"+line.substring(Actions.loadApplication.length());
			} else if ( line.startsWith(Actions.initService ) ){
				content="INIT_S_"+line.substring(Actions.initService.length());
			} else if ( line.startsWith(Actions.startupService ) ){
				content="STARTUP_S_"+line.substring(Actions.startupService.length());
			} else if ( line.startsWith(Actions.shutdown ) ){
				content="SHUTDOWN";
			} else if ( line.startsWith(Actions.started )){
				content="STARTED";
			}
		}
		
		if ( content!=null){
			return actionPrefix+content;
		}
		return null;
	}
	
}
