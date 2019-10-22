package jvmMon.preprocessorRules;

import it.unimib.disco.lta.alfa.dataTransformation.GenericRule;
import it.unimib.disco.lta.alfa.dataTransformation.LineIterator;
import it.unimib.disco.lta.alfa.dataTransformation.PreproessorRule;
import it.unimib.disco.lta.alfa.dataTransformation.ValueTransformer;


public class ThreadRule extends GenericRule implements PreproessorRule {
	private ValueTransformer keyDispenserMonitorTag;
	private ValueTransformer keyDispenserThread;
	
	public ThreadRule( ValueTransformer keyDispenserThread, ValueTransformer keyDispenserMonitorTag ) {
		super("THREAD MANAGEMENT");
		this.keyDispenserMonitorTag = keyDispenserMonitorTag;
		this.keyDispenserThread = keyDispenserThread;
	}

	@Override
	public String process(String line, LineIterator dispenser) {
		//System.out.println(line);
		String[] words = line.split("\t");
		StringBuffer buffer = new StringBuffer();
		//buffer.append("TM");
		
		//ACTION
		String action = words[2].trim();
		
		
		
		
		//EXTRACT MONITOR DATA
		//System.out.println(action);
		String data = "";
		String actionToAppend="";
		if ( action.equals("MONITOR WAIT") ){
			data = words[5];
			actionToAppend = "w";
		} else if ( action.equals("MONITOR WAITED") ) {
			data = words[6];
			actionToAppend = "W";
		} else if ( action.equals("MONITOR CONTENDED ENTER") ) {
			data = words[5];
			actionToAppend = "m";
		} else if ( action.equals("MONITOR CONTENDED ENTERED") ) {
			data = words[5];
			actionToAppend = "M";
		}
		
		//Append action shortened
		//buffer.append(".");
		buffer.append(actionToAppend);
		String keyTID = keyDispenserThread.getTransformedValue(words[3]);
		if (!keyTID.equals("")){
			//APPend Thread ID and Worker ID
			buffer.append(keyTID);
		}
		//Append monitor tag
		String keyTag = keyDispenserMonitorTag.getTransformedValue(data);
		if (!keyTag.equals("")){
			if (!keyTID.equals(""))
					buffer.append("_");
			buffer.append(keyTag);
		}

		
		
		return buffer.toString();
	}

}
