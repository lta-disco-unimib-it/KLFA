/*
 * Created on 20-mag-2005
 */
package flattener.core;

/**
 * @author Davide Lorenzoli
 */
public interface Factory {
	public Handler getHandler();
	public StimuliRecorder getStimuliRecorder();
}
