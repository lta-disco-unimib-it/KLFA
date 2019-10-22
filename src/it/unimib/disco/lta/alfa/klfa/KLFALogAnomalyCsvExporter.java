package it.unimib.disco.lta.alfa.klfa;

import it.unimib.disco.lta.alfa.klfa.KLFALogAnomaly.AnomalyType;
import it.unimib.disco.lta.alfa.klfa.KLFALogAnomaly.StateType;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class KLFALogAnomalyCsvExporter {

	private static final int COMPONENT_POS = 0;
	private static final int ANOMALY_TYPE_POS = 1;
	private static final int ANOMALY_LINE_POS = 2;
	private static final int FROM_STATE_POS = 3;
	private static final int STATE_TYPE_POS = 4;
	private static final int ANOMALOUS_EVENTS_POS = 5;
	private static final int ORIGINAL_ANOMALY_LINE_POS = 6;
	private static final int ORIGINAL_ANOMALOUS_EVENTS_POS = 7;
	private static final int TO_STATE_POS = 8;
	private static final int BRANCH_LENGTH_POS = 9;
	private static final int EXPECTED_OUTGOING_POS = 10;
	private static final int EXPECTED_INCOMING_POS = 11;
	
	
	
	private String intraLineSeparator = ",";
	private char columnSeparator = ';';



	


	public void exportToCsv(File dest, List<KLFALogAnomaly> anomalies) throws IOException{
		
		
		
		BufferedWriter w = null;
		try {
			w = new BufferedWriter(new FileWriter(dest,true));
			w.write("Component"+columnSeparator+"Anomaly"+columnSeparator+"Line" +
					columnSeparator+"State"+columnSeparator+"StateType" +
					columnSeparator+"Event" +
					columnSeparator+"Original log line"+columnSeparator+"Original log event" +
					columnSeparator+"To state"+columnSeparator+"Branch length"+columnSeparator+"Expected"+columnSeparator+"Expected incoming");
			w.newLine();
			
			for ( KLFALogAnomaly data : anomalies ){
				exportToCsv(w,data);
			}
			
		} finally {
			try {
				w.close();
			} catch (IOException e) {
				
			}
		}
		
		
	}
	
	public List<KLFALogAnomaly> importFromCsv(File fileToRead) throws IOException{
		BufferedReader r = new BufferedReader(new FileReader(fileToRead));
		
		try{
			String line = r.readLine();
			ArrayList<KLFALogAnomaly> res = new ArrayList<KLFALogAnomaly>();
			
			while ( ( line = r.readLine()) != null ){
				res.add(importFromCsvLine(line));
			}
			return res;
		} finally {
			r.close();
		}
	}
	
	
	private KLFALogAnomaly importFromCsvLine(String line) {
		String[] cols = splitLine(line,columnSeparator);
		System.out.println(line+" "+cols.length);
		KLFALogAnomaly anomaly = new KLFALogAnomaly();
		
		anomaly.setComponent(cols[COMPONENT_POS]);
		anomaly.setAnomalyType(AnomalyType.valueOf(cols[ANOMALY_TYPE_POS]));
		anomaly.setAnomalyLine(Integer.valueOf(cols[ANOMALY_LINE_POS]));
		anomaly.setFromState(cols[FROM_STATE_POS]);
		anomaly.setStateType(StateType.valueOf(cols[STATE_TYPE_POS]));
		
		anomaly.setAnomalousEvents(lineToArray(cols[ANOMALOUS_EVENTS_POS]));
		
		System.out.println("original    "+cols[ORIGINAL_ANOMALY_LINE_POS]);
		
		anomaly.setOriginalAnomalyLine(Integer.valueOf(cols[ORIGINAL_ANOMALY_LINE_POS]));
		anomaly.setOriginalAnomalousEvents(lineToArray(cols[ORIGINAL_ANOMALOUS_EVENTS_POS]));
		
		String toState = cols[TO_STATE_POS];
		if ( ! toState.isEmpty() ){
			anomaly.setToState(toState);
		}
		
		anomaly.setBranchLength(Integer.valueOf(cols[BRANCH_LENGTH_POS]));
		
		anomaly.setExpectedOutgoing(lineToArray(cols[EXPECTED_OUTGOING_POS]));
		
		anomaly.setExpectedIncoming(lineToArray(cols[EXPECTED_INCOMING_POS]));
		
		
		
		return anomaly;
	}

	private String[] splitLine(String line,char columnSeparator2) {
		List<String> result = new ArrayList<String>();
		char[] chars = line.toCharArray();
		int last = 0;
		for ( int i = 0; i < chars.length; ++i ){
			if ( chars[i] == columnSeparator2 ){
				result.add(line.substring(last, i));
				last = i+1;
			}
		}
		String[] a = new String[result.size()];
		return result.toArray(a);
	}

	private List<String> lineToArray(String string) {
		String[] elements = string.split(intraLineSeparator);
		
		ArrayList<String> res = new ArrayList<String>();
		boolean first = true;
		for ( String el : elements ){
			
			if ( !el.isEmpty() ){	
				res.add(el);
			}
		}
		return res;
	}

	private void exportToCsv(BufferedWriter w, KLFALogAnomaly anomaly) throws IOException{
			
			w.write(anomaly.getComponent());
			w.write(columnSeparator );
			
			w.write(anomaly.getAnomalyType().toString());
			w.write(columnSeparator );
			
			w.write(String.valueOf(anomaly.getAnomalyLine()));
			w.write(columnSeparator );
			
			
			
			w.write(anomaly.getFromState());
			w.write(columnSeparator );
			
			if ( anomaly.getStateType() != null ){
			w.write(anomaly.getStateType().toString());
			}
			w.write(columnSeparator );
			
			
			
			
			w.write(listToLine(anomaly.getAnomalousEvents()));
			w.write(columnSeparator );
			
			
			
			
			w.write(String.valueOf(anomaly.getOriginalAnomalyLine()));
			w.write(columnSeparator );
			
			w.write(listToLine(anomaly.getOriginalAnomalousEvents()));
			w.write(columnSeparator );
			
			
			
			if ( anomaly.getToState() != null ){
				w.write(anomaly.getToState());
			}
			w.write(columnSeparator );
			
			w.write(String.valueOf(anomaly.getBranchLength()));
			w.write(columnSeparator );
			
			w.write(listToLine(anomaly.getExpectedOutgoing()));
			w.write(columnSeparator );
			
			w.write(listToLine(anomaly.getExpectedIncoming()));
			w.write(columnSeparator );
			
			w.newLine();
			
	}


	private String listToLine(List<String> anomalousEvents) {
		if ( anomalousEvents == null ){
			return "";
		}
		
		StringBuffer sb = new StringBuffer();
		for ( int i = 0; i < anomalousEvents.size(); ++i ){
			if ( i > 0 ){
				sb.append(intraLineSeparator );
			}
			sb.append(anomalousEvents.get(i));
			
		}
		return sb.toString();
	}
}
