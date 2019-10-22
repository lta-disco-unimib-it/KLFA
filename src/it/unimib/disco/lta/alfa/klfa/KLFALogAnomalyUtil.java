package it.unimib.disco.lta.alfa.klfa;

import it.unimib.disco.lta.alfa.inferenceEngines.FSAExtensionsRecorder.FSAExtension;
import it.unimib.disco.lta.alfa.inferenceEngines.FSAExtensionsRecorder.FSAExtensionBranch;
import it.unimib.disco.lta.alfa.inferenceEngines.FSAExtensionsRecorder.FSAExtensionFinalState;
import it.unimib.disco.lta.alfa.inferenceEngines.FSAExtensionsRecorder.FSAExtensionTail;
import it.unimib.disco.lta.alfa.klfa.KLFALogAnomaly.AnomalyType;
import it.unimib.disco.lta.alfa.tools.LogMapper;
import it.unimib.disco.lta.alfa.utils.FSAUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


import automata.State;
import automata.Transition;
import automata.fsa.FiniteStateAutomaton;

public class KLFALogAnomalyUtil {

	protected static List<KLFALogAnomaly> createKlfaAnomalies(String componentName, List<FSAExtension> fsaExtensions, LogMapper m, FiniteStateAutomaton trainingFSA, FiniteStateAutomaton extendedFSA ) throws IOException{
		List<KLFALogAnomaly> anomalies = new ArrayList<KLFALogAnomaly>();
		
		//This hashset is used to detect if states are new or not, pay attention it works only if the checking fsa was not minimized
		HashMap<String,State> statesNames = new HashMap<String,State>();
		for ( State state : trainingFSA.getStates() ){
			statesNames.put(state.getName(),state);
		}

		
		for ( FSAExtension data :  fsaExtensions ){

			KLFALogAnomaly anomaly = new KLFALogAnomaly();
			anomalies.add(anomaly);
			
			String startState = data.getStartState().getName();
			anomaly.setFromState(startState);

			if ( statesNames.containsKey(startState) ){
				anomaly.setStateType(KLFALogAnomaly.StateType.Existing);
			} else {
				anomaly.setStateType(KLFALogAnomaly.StateType.New);
			}

			
			
			int lineN=data.getLogLine()+1;

			anomaly.setComponent(componentName);
			
			
			//Anomaly line
			
			
			anomaly.setAnomalyLine(lineN);
			

			anomaly.setAnomalousEvents(data.getAnomalousSequence());

			
			int originalLine;
			
			if ( data instanceof FSAExtensionFinalState ){
				originalLine = m.getLineNumber(lineN-1);
			} else {
				originalLine = m.getLineNumber(lineN);	
			}
			
			anomaly.setOriginalAnomalyLine(originalLine);

			ArrayList<String> originalEvents = new ArrayList<String>();
			originalEvents.add(m.getLineValue(lineN));
			anomaly.setOriginalAnomalousEvents(originalEvents);

			//
			//TO State
			//
			if ( data instanceof FSAExtensionBranch ){
				FSAExtensionBranch changeDataBranch = (FSAExtensionBranch) data;
				anomaly.setToState(changeDataBranch.getToState().getName());
			}
			
			//
			//Branch len
			//
			anomaly.setBranchLength(data.getTraceLen());
			
			
			if ( data instanceof FSAExtensionBranch ){
				anomaly.setAnomalyType(AnomalyType.Branch);
			} else if ( data instanceof FSAExtensionFinalState ){
				anomaly.setAnomalyType(AnomalyType.FinalState);
			} else if ( data instanceof FSAExtensionTail ){
				anomaly.setAnomalyType(AnomalyType.Tail);
			}

			
			//
			// Transitions in/out
			//
			
			State failState = trainingFSA.getStateWithID(data.getStartState().getID());
			if ( failState == null ){  //NEW STATE do nothing
				
			} else {

				//
				//Outgoing transitions
				//
				
				Set<Transition> expectedTransitions = FSAUtil.returnOutGoingTransitions(failState);

				List<String> expectedOutgoing = new ArrayList<String>();
				for ( Transition t : expectedTransitions ){
					expectedOutgoing.add(t.getDescription());
				}
				anomaly.setExpectedOutgoing(expectedOutgoing);

				//
				//Incoming transitions
				//
				
				if ( data instanceof FSAExtensionBranch ){
					FSAExtensionBranch changeDataBranch = (FSAExtensionBranch) data; 
					//Retrieve the TO state in the ORIGINAL automata
					State arrivalState = statesNames.get( changeDataBranch.getToState().getName() );
					
					Set<Transition> incomingTransitions;
					if ( arrivalState != null){
						incomingTransitions = FSAUtil.returnIncomingTransitions(arrivalState);
					} else {
						incomingTransitions = new HashSet<Transition>();
					}
					
					List<String> expectedIncoming = new ArrayList<String>();
					for ( Transition t : incomingTransitions ){
						expectedIncoming.add(t.getFromState().getName()+")"+t.getDescription());
					}
					
					anomaly.setExpectedIncoming(expectedIncoming);

				} else if ( data instanceof FSAExtensionFinalState ){
					State[] finalStates = trainingFSA.getFinalStates();
					List<String> expectedIncoming = new ArrayList<String>();
					for ( State finalState : finalStates ){

						Set<Transition> incomingTransitions = FSAUtil.returnIncomingTransitions(finalState);

						
						for ( Transition t : incomingTransitions ){
							expectedIncoming.add(t.getFromState().getName()+")"+t.getDescription());
						}

					}

					anomaly.setExpectedIncoming(expectedIncoming);
				}
				
				
				

				
			}
		}
		return anomalies;
	}
	
	
}
