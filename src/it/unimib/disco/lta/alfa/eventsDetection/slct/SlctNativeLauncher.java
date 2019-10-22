package it.unimib.disco.lta.alfa.eventsDetection.slct;

import it.unimib.disco.lta.alfa.logging.Logger;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class SlctNativeLauncher implements SlctLauncher {

	private String slctExecutablePath;
	private boolean intersectionEnabled;

	public SlctNativeLauncher ( String slctExecutablePath, boolean intersectionEnabled ){
		this.slctExecutablePath = slctExecutablePath;
		this.intersectionEnabled = intersectionEnabled;
	}
	
	/* (non-Javadoc)
	 * @see it.unimib.disco.lta.alfa.eventsDetection.slct.SlctLauncher#run(int, java.io.File, java.io.File)
	 */
	public List<Pattern> run(int support, File inputFile, File outliersFile){
		
		Logger.info("Launching SlctNativeLauncher on "+inputFile.getAbsolutePath());
		Logger.info("Outliers: "+outliersFile.getAbsolutePath());
		Logger.info("Support: "+support);
		
		Logger.fine("Iteration start");
		List<String> commandList = new ArrayList<String>();
		
		commandList.add( slctExecutablePath );
		if ( intersectionEnabled ){
			commandList.add("-j");
		}

		//add support

		commandList.add("-s");
		commandList.add(""+support);

		//save outliers
		commandList.add("-r");
		commandList.add("-o");
		commandList.add(outliersFile.getAbsolutePath());
		commandList.add(inputFile.getAbsolutePath());
		
		String command[] = new String[commandList.size()];
		
		command = commandList.toArray(command);
		
		Logger.fine("Launching ");
		Process p;
		try {
			p = Runtime.getRuntime().exec(command);
		
		
		final InputStream es = p.getErrorStream();
		final InputStream is = p.getInputStream();

		

		Thread est = new Thread(){

			public void run(){
				int bufLen = 256;
				byte b[] = new byte[bufLen];

				int read;
				try {
					while( ( read = es.read(b, 0, bufLen )  ) > 0 ){
						System.err.write(b,0,read);
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}

		};

		final StringBuffer rulesOutput = new StringBuffer();
		
		Thread ist = new Thread(){

			public void run(){
				int bufLen = 256;
				byte b[] = new byte[bufLen];

				int read;
				try {
					while( ( read = is.read(b, 0, bufLen )  ) > 0 ){
						String s = new String(b,0,read);
						rulesOutput.append(s);
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}

		};
		ist.start();
		est.start();

		p.waitFor();
		System.err.flush();

		Logger.info("Extracting Rules from "+rulesOutput.toString());
		
		StringReader r = new StringReader(rulesOutput.toString());
		return SlctUtils.getRules(r,true);
		
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return new ArrayList<Pattern>();
	}
}
