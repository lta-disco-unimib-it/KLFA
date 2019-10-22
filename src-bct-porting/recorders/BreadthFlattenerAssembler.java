package recorders;

import flattener.core.Flattener;
import flattener.core.Handler;
import flattener.core.StimuliRecorder;
import flattener.flatteners.BreadthObjectFlattener;
import flattener.handlers.RawHandler;
import flattener.stimuliRecorders.RawStimuliRecorder;

public class BreadthFlattenerAssembler implements FlattenerAssembler {
	private BreadthObjectFlattener flattener;
	private RawHandler handler;
	private RawStimuliRecorder recorder = new RawStimuliRecorder();
	
	public Flattener getFlattener(String rootName) {
		handler = new RawHandler(rootName);
		return  new BreadthObjectFlattener( handler );
		/*
		if ( flattener == null )
			flattener = new BreadthObjectFlattener( handler );
		else{
			flattener.clear();
			flattener.setDataHandler(handler);
		}
		return flattener;
		*/
	}

	public StimuliRecorder getStimuliRecorder() {
		return recorder;
	}
	
	
	
}
