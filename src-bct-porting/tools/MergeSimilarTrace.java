package tools;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeMap;
import java.util.Vector;

import conf.BctSettingsException;
import conf.EnvironmentalSetter;
import conf.InvariantGeneratorSettings;

import database.DataLayerException;
import database.GKTailInteractionTrace;

import traceReaders.normalized.DBNormalizedTracesReader;
import traceReaders.normalized.NormalizedTraceHandlerException;
import traceReaders.normalized.NormalizedTracesReader;
import traceReaders.raw.TraceException;

/**
 * @author 
 * 
 * This class manage the Interactions Traces in order to identify similar traces
 * To do this method calls are checked and if two traces have the same method calls
 * these are merged. During this step method call data of an interaction trace 
 * are also checked and in the case of similar traces they are also merged.
 * 
 */

public class MergeSimilarTrace {
	
	private static NormalizedTracesReader ntr = new DBNormalizedTracesReader();
	
	/**
	 * used to identify similar traces
	 */
	private static Iterator interactionTraces1;
	private static Iterator interactionTraces2;
	private static ArrayList al = new ArrayList();
	private static ArrayList al1 = new ArrayList();
	
	/**
	 * used to manage method call data of similar traces
	 */
	private static TreeMap dataENTER = new TreeMap();
	private static TreeMap dataEXIT = new TreeMap();
	private static TreeMap dataENTEREXIT = new TreeMap();
	
	/**
	 * through the iterator of over all method. InteractionTrace iterators are
	 * inizialized with all the interactions traces of the current method 
	 */
	private static void analizeNormalizedTrace() throws DataLayerException {
		
		Iterator methodList = ntr.getMethods();

		int method;
		while ( methodList.hasNext() ) {
			method = (Integer) methodList.next();
			
			interactionTraces1 = ntr.getInteractionTraces(method);
		
			//initialize ArrayList
			interactionTraces2 = ntr.getInteractionTraces(method);
			while(interactionTraces2.hasNext()) {
				al.add((Integer) interactionTraces2.next());
			}
			
			int trace;
			int thread;

			while (interactionTraces1.hasNext()) {
				trace = (Integer) interactionTraces1.next();
				thread = ntr.getInteractionThread(trace);
				System.out.println("analysing trace " + trace);
				findSimilarTrace(trace, method, thread);
			}
			al1.clear();
		}
	}

	private static void findSimilarTrace(int trace, int method, int thread) throws DataLayerException {
//////////////////////////////////////////////
		//to avoid to repeat the of a trace
		if(al1.contains((Integer) trace)){
			return;
		}
///////////////////////////////////7
		if(al.isEmpty()) {
			System.out.println("Empty ArrayList. Checking similar traces terminated");
			return;
		}
		
		loadNormalizedTraceData(trace);
		
		 //to avoid riflessive check
		al.remove((Integer)trace);
		interactionTraces2 = al.iterator();
		//System.out.println("ArrayList " + al);
		int trace2;
		while(interactionTraces2.hasNext()) {
			trace2 = (Integer) interactionTraces2.next();
			System.out.println("analysing trace " + trace + " vs " + trace2);
			checkTrace(trace, trace2);
		}
		System.out.println("similar traces for the method " + method);

		//write on DB
		GKTailInteractionTrace.insert(thread, method, ntr.getMethodCall(trace), dataENTER, dataEXIT, dataENTEREXIT);
		
		dataENTER.clear();//pulisco per inserire nuovi dati di un'altra interaction trace
		dataEXIT.clear();
		//FIXME: Should clear also this? 
		//dataENTEREXIT.clear();
		System.out.println("Normalized data container cleaned");
	}

	private static void checkTrace(int trace1, int trace2) throws DataLayerException {
		
		
		Iterator methodCalls1 = ntr.getMethodCall(trace1);
		Iterator methodCalls2 = ntr.getMethodCall(trace2);
		if(!(methodCalls1.hasNext()) || !(methodCalls1.hasNext())) {
			 //to avoid redundant check
			interactionTraces2.remove();
			al.remove((Integer)trace2);
			al1.add((Integer)trace2);
			return;
		}
		int call1, call2;
		while(methodCalls1.hasNext() && methodCalls2.hasNext()) {
			call1 = (Integer) methodCalls1.next();
			call2 = (Integer) methodCalls2.next();
			if(call1 != call2) {
				System.out.println("Different Trace " + trace1 + " " + trace2);
				//System.out.println(al);
				return;
			}	
		}
		if(methodCalls1.hasNext() || methodCalls2.hasNext()) {
			System.out.println("Different trace length " + trace1 + " " + trace2);
			return;
		}
		System.out.println("Similar Trace " + trace1 + " " + trace2);
		
		mergeNormalizedTraceData(trace1, trace2);
		
		
		 //to avoid redundant check
		interactionTraces2.remove();
		al.remove((Integer)trace2);
		al1.add((Integer)trace2);
		
		//System.out.println(al);
	}	
	
	private static void loadNormalizedTraceData(int trace) throws DataLayerException {
		
		Iterator it = ntr.getIDMethodCall(trace);
		String dataDefinitionENTER = "";
		String dataDefinitionEXIT = "";
		String dataDefinitionENTEREXIT = "";
		int id;
		
		while(it.hasNext()){
				id = (Integer) it.next();
				//System.out.println("idMethodCall " + id);
				dataDefinitionENTER = ntr.getNormalizedDataENTER(id);
				dataDefinitionEXIT = ntr.getNormalizedDataEXIT(id);
				dataDefinitionENTEREXIT = ntr.getNormalizedDataENTEREXIT(id);
				dataEXIT.put(id, dataDefinitionEXIT);
				dataENTER.put(id, dataDefinitionENTER);
				dataENTEREXIT.put(id, dataDefinitionENTEREXIT);
		}
	}
	
	private static void mergeNormalizedTraceData(int trace1, int trace2) throws DataLayerException {
		
		Iterator it1 = ntr.getIDMethodCall(trace2);
		Iterator it2 = ntr.getIDMethodCall(trace1);
		
		String dataDefinitionENTER;
		String dataDefinitionEXIT;
		String dataDefinitionENTEREXIT;
		Integer indexIT1;
		Integer indexIT2;
		while(it1.hasNext() && it2.hasNext()){
			//while(it2.hasNext()) {
				indexIT1 = (Integer) it1.next();
				indexIT2 = (Integer) it2.next();
				dataDefinitionENTER = (String) dataENTER.get(indexIT2);
				dataDefinitionEXIT = (String) dataEXIT.get(indexIT2);
				//System.out.println("dati gia presenti " + dataDefinitionENTER);
				//System.out.println("dati gia presenti " + dataDefinitionEXIT);
				dataDefinitionENTER = dataDefinitionENTER.concat(ntr.getNormalizedDataENTER(indexIT1));
				dataDefinitionEXIT = dataDefinitionEXIT.concat(ntr.getNormalizedDataEXIT(indexIT1));
				//System.out.println("nuovi dati " + dataDefinitionENTER);
				//System.out.println("nuovi dati " + dataDefinitionEXIT);
				dataENTER.remove(indexIT2);
				dataEXIT.remove(indexIT2);
		
				dataENTER.put(indexIT2, dataDefinitionENTER);
				dataEXIT.put(indexIT2, dataDefinitionEXIT);
				/////////////////////////////
				dataDefinitionENTEREXIT = (String) dataENTEREXIT.get(indexIT2);
				dataDefinitionENTEREXIT = dataDefinitionENTEREXIT.concat(ntr.getNormalizedDataENTEREXIT(indexIT1));
				dataENTEREXIT.remove(indexIT2);
				dataENTEREXIT.put(indexIT2, dataDefinitionENTEREXIT);
			//}
		}		
	}
	
	public static void main(String argv[]) {
		try {
			analizeNormalizedTrace();
		} catch (DataLayerException e) {
			e.printStackTrace();
		}
	}
}



