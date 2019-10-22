package tools;

import grammarInference.Engine.kTailEngine;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Iterator;

import modelsFetchers.ModelsFetcher;
import modelsFetchers.ModelsFetcherException;
import modelsFetchers.ModelsFetcherFactoy;

import database.DataLayerException;
import database.GKTailMethodCall;
import traceReaders.normalized.DBGKTailTracesReader;
import traceReaders.normalized.DBNormalizedTracesReader;
import traceReaders.normalized.GKTailTracesReader;
import traceReaders.normalized.NormalizedTracesReader;

public class MergeEquivalentStates {

	private static GKTailTracesReader gkttr = new DBGKTailTracesReader();
	static GKTailConstraintsAnalyser analyser;
	
	static ArrayList al = new ArrayList();//used to compare constraints of different interaction trace method
	static int marker = 0;
	
	/**
	 * through the iterator of over all method. InteractionTrace iterators are
	 * inizialized with all the interactions traces of the current method 
	 */
	private static void loadTrace() throws DataLayerException {

		Iterator methodList = gkttr.getMethods();
		ArrayList interactionTraceList;

		//int method;
		//while ( methodList.hasNext() ) {
			//method = (Integer) methodList.next();
			//System.out.println("analysing traces for method " + method);
			//marker = 0;
			//interactionTraceList = gkttr.getInteractionTraces(method);
			interactionTraceList = gkttr.getInteractionTraces(methodList);
			//System.out.println("traces " + interactionTraceList);
			analyseTraceMethod(interactionTraceList);
		//}
	}
	
	private static void analyseTraceMethod(ArrayList interactionTraceList) throws DataLayerException {
		
		Iterator traceMethodList = gkttr.getMethodCall(interactionTraceList);
		
		int traceMethod;
		while ( traceMethodList.hasNext()) {
			traceMethod = (Integer) traceMethodList.next();
			System.out.println("analysing trace method " + traceMethod);
			mergeEquivalentStatesTrace(traceMethod, interactionTraceList);
		}
		
	}

	private static void mergeEquivalentStatesTrace(int traceMethod, ArrayList interactionTraceList) throws DataLayerException {

		Iterator traceIdMethodCallList;
		
		traceIdMethodCallList = gkttr.getIDMethodCall(traceMethod, interactionTraceList);
		while (traceIdMethodCallList.hasNext()) {
			al.add((Integer) traceIdMethodCallList.next());
		}
		System.out.println("id trace methods " + al);
		
		traceIdMethodCallList = gkttr.getIDMethodCall(traceMethod, interactionTraceList);
		int idMethodCall;
		while (traceIdMethodCallList.hasNext()) {
			if(al.isEmpty()) {
				System.out.println("Empty ArrayList. Checking equivalent traces terminated for traceMethod " + traceMethod);
				break;
			}
			idMethodCall = ((Integer) traceIdMethodCallList.next());
			//analyseConstraints(idMethodCall, ++marker);
			al = analyser.analyseConstraints(al, idMethodCall, ++marker);
		}
	}

	/*
	private static void analyseConstraints(int idMethodCall, int marker) throws DataLayerException {
		
		Iterator traceIdMethodCallList = al.iterator();
		String constraint1;
		String constraint2;
		
		int arrayListIdMethodCall;
		while (traceIdMethodCallList.hasNext()) {
			arrayListIdMethodCall = (Integer)traceIdMethodCallList.next();
			System.out.println("----- " + arrayListIdMethodCall);
			System.out.println("##### " + idMethodCall);
			constraint1 = gkttr.getConstraint(arrayListIdMethodCall);
			constraint2 = gkttr.getConstraint(idMethodCall);

			if(constraint1.equals(constraint2)) {
				System.out.println("similar ");
				GKTailMethodCall.updateLine(arrayListIdMethodCall, marker);
				System.out.println("update ok for method " + arrayListIdMethodCall);
				
				traceIdMethodCallList.remove();
				al.remove((Integer)arrayListIdMethodCall);
				System.out.println("................." + al);
			}		
		}	
	}*/

	public static void main(String[] args) {
		try {
			analyser = new EquivalenceConstraintsAnalyser();
			loadTrace();
		} catch (DataLayerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//generate models
		ModelsFetcher mf = ModelsFetcherFactoy.getModelsFetcher();
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		int method;
		try {
			Iterator methodList = gkttr.getMethods();
		
			while ( methodList.hasNext() ) {
				method = (Integer) methodList.next();
				System.out.println("Start Ktail Engine");
				output = kTailEngine.process(method/*f, output*/);
				
				mf.addInteractionModel(method, output);
			}
		} catch (DataLayerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch (ModelsFetcherException e) {
			System.err.println("Cannot save fsa ");
			e.printStackTrace();
		}	
	}
}
	
