package tools;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import theoremProver.Simplify;
import theoremProver.SimplifyResult;
import traceReaders.normalized.DBGKTailTracesReader;
import traceReaders.normalized.GKTailTracesReader;

import database.DataLayerException;
import database.GKTailMethodCall;

public class EquivalenceConstraintsAnalyser implements GKTailConstraintsAnalyser {
	
	public  ArrayList analyseConstraints (ArrayList al, int idMethodCall, int marker) throws DataLayerException {
		
		GKTailTracesReader gkttr = new DBGKTailTracesReader();
		
		Iterator traceIdMethodCallList = al.iterator();
		String constraint1;
		String constraint2;
		
		int arrayListIdMethodCall;
		while (traceIdMethodCallList.hasNext()) {
			arrayListIdMethodCall = (Integer)traceIdMethodCallList.next();
			constraint1 = gkttr.getConstraint(arrayListIdMethodCall);
			constraint2 = gkttr.getConstraint(idMethodCall);
						
			System.out.println("EVALUATING CONSTRAINS:");
			System.out.println("1: " + constraint1);
			System.out.println("2: " + constraint2);
									
			String predicate1 = "(AND " + constraint1 + " )";
			String predicate2 = "(AND " + constraint2 + " )";
			
			ArrayList<SimplifyResult> results = new ArrayList<SimplifyResult>();
			// Execute Simplify 
			
			try {
				Simplify simplify = new Simplify();
				results = simplify.doIMPLIES(predicate1, predicate2);
				System.out.println("RESULT: " + results);
			} catch (IOException e) {
				System.out.println("Check Simplify executable file is in your path");
				e.printStackTrace();
			}
			//results.add(new SimplifyResult("", SimplifyResult.BAD_INPUT));
			
			// If any result exist
			if (!results.isEmpty()) {
				System.out.println("SONO DENTRO ;)");
				// The predicate1 implies predicate2 => state merging
				//if (constraint1.equals(constraint2)) {
				if (results.get(0).getResult() == SimplifyResult.VALID) {
					System.out.println("VALID");
					GKTailMethodCall.updateLine(arrayListIdMethodCall, marker);
					System.out.println("update ok for method " + arrayListIdMethodCall);
					
					traceIdMethodCallList.remove();
					al.remove((Integer)arrayListIdMethodCall);
					System.out.println("................." + al);					
				}
				// Something went wrong => no state merging
				else {
					switch (results.get(0).getResult()) {
						case SimplifyResult.BAD_INPUT:
							System.out.println("BAD INPUT");
							break;
						case SimplifyResult.SYNTAX_ERROR:
							System.out.println("SYNTAX ERROR");
							break;
						default:
							System.out.println("INVALID");
							break;
					}
				}
			}
			
			/*
			 * OLD CODE
			 */
			/*
			if(constraint1.equals(constraint2)) {
				System.out.println("similar ");
				GKTailMethodCall.updateLine(arrayListIdMethodCall, marker);
				System.out.println("update ok for method " + arrayListIdMethodCall);
				
				traceIdMethodCallList.remove();
				al.remove((Integer)arrayListIdMethodCall);
				System.out.println("................." + al);	
			}
			*/			
		}
		return al;
	}
}
