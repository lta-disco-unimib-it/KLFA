package recorders;

import flattener.core.Flattener;
import flattener.core.StimuliRecorder;

public interface FlattenerAssembler {
	
	public Flattener getFlattener(String rootName);
	
	public StimuliRecorder getStimuliRecorder();
	
}
