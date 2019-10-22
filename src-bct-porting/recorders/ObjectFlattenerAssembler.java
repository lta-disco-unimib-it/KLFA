package recorders;

import flattener.core.Flattener;
import flattener.core.Handler;
import flattener.core.StimuliRecorder;
import flattener.factories.DaikonComponentsFactory;
import flattener.flatteners.ObjectFlattener;
import flattener.handlers.DaikonHandler;

public class ObjectFlattenerAssembler implements FlattenerAssembler {
	private DaikonComponentsFactory daikonFactory = new DaikonComponentsFactory();
	
	public Flattener getFlattener(String rootName) {
		Handler handler = daikonFactory.getHandler(rootName);
		ObjectFlattener flattener = new ObjectFlattener();
		flattener.setDataHandler(handler);
		return flattener;
	}

	public StimuliRecorder getStimuliRecorder() {
		return daikonFactory.getStimuliRecorder();
	}

}
