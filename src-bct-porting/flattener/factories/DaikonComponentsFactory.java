/*
 * Created on 20-mag-2005
 */
package flattener.factories;

import flattener.core.Factory;
import flattener.core.Handler;
import flattener.core.StimuliRecorder;
import flattener.handlers.DaikonHandler;
import flattener.stimuliRecorders.DaikonStimuliRecorder;

/**
 * @author Davide Lorenzoli
 */
public class DaikonComponentsFactory implements Factory {

	/**
	 * @param rootNodeName
	 * @return
	 */
	public Handler getHandler(String rootNodeName) {
		return new DaikonHandler(rootNodeName);
	}
	
	/**
	 * @see flattener.core.Factory#getHandler()
	 */
	public Handler getHandler() {		
		return new DaikonHandler();
	}
	
	/**
	 * @see flattener.core.Factory#getStimuliRecorder()
	 */
	public StimuliRecorder getStimuliRecorder() {
		return new DaikonStimuliRecorder();
	}
}
