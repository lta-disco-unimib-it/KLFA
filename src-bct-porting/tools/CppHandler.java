package tools;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import flattener.core.Handler;
import flattener.handlers.RawHandler;
import flattener.stimuliRecorders.RawStimuliRecorder;

public class CppHandler extends RawHandler {

	public CppHandler() {
		super("");
	}

	public void add(String inspectorName, String value){
		nodes.put(inspectorName, value);
	}


}
