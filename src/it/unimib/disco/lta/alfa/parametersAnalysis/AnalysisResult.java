package it.unimib.disco.lta.alfa.parametersAnalysis;

import it.unimib.disco.lta.alfa.logging.Logger;

import java.util.ArrayList;



public class AnalysisResult {
	private int curTrace = -1;
	private ArrayList<TraceData> tracesDatas=new ArrayList<TraceData>();
	private boolean countLines;
	private boolean dontSeparateTraces = false;
	
	public AnalysisResult(boolean countLines) {
		this.countLines = countLines;
		newTrace();
	}
	
	public void newTrace() {
		if ( dontSeparateTraces ){
			return;
		}
		//System.out.println("new trace");
		Logger.entering(this.getClass().getName(), "newTrace");
		++curTrace;
		tracesDatas.add(new TraceData(countLines));
	}

	public void addStat(String signature, ArrayList<String> parameters) {
		TraceData traceData = getCurTraceData();
		traceData.addStat(signature,parameters);
	}

	private TraceData getCurTraceData() {
		return tracesDatas.get(curTrace);
	}

	public ArrayList<TraceData> getTracesDatas() {
		return tracesDatas;
	}

	public void setTracesDatas(ArrayList<TraceData> tracesDatas) {
		this.tracesDatas = tracesDatas;
	}

	public void newLine(){
		getCurTraceData().newLine();
	}

	public void setDontSeparateTraces(boolean dontSeparateTraces) {
		this.dontSeparateTraces  = dontSeparateTraces;
	}
}
