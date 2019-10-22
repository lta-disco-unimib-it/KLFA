package it.unimib.disco.lta.alfa.klfa;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class KLFALogAnomaly implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public enum AnomalyType { Branch, Tail, FinalState, NewComponent }
	public enum StateType { New, Existing }
	
	protected String component;
	protected AnomalyType anomalyType;
	
	protected int anomalyLine;
	protected int originalAnomalyLine;
	protected String fromState = "";
	protected StateType stateType;
	
	protected List<String> anomalousEvents = new ArrayList<String>();
	protected List<String> originalAnomalousEvents = new ArrayList<String>();
	
	protected String toState;
	protected int branchLength;
	protected List<String> expectedOutgoing;
	protected List<String> expectedIncoming;
	

	public List<String> getExpectedOutgoing() {
		return expectedOutgoing;
	}
	public void setExpectedOutgoing(List<String> expectedOutgoing) {
		this.expectedOutgoing = expectedOutgoing;
	}
	public List<String> getExpectedIncoming() {
		return expectedIncoming;
	}
	public void setExpectedIncoming(List<String> expectedIncoming) {
		this.expectedIncoming = expectedIncoming;
	}
	public String getComponent() {
		return component;
	}
	public void setComponent(String component) {
		this.component = component;
	}
	public AnomalyType getAnomalyType() {
		return anomalyType;
	}
	public void setAnomalyType(AnomalyType extensionType) {
		this.anomalyType = extensionType;
	}
	public int getAnomalyLine() {
		return anomalyLine;
	}
	public void setAnomalyLine(int anomalousEventPosition) {
		this.anomalyLine = anomalousEventPosition;
	}
	public String getFromState() {
		return fromState;
	}
	public void setFromState(String state) {
		this.fromState = state;
	}
	public StateType getStateType() {
		return stateType;
	}
	public void setStateType(StateType stateType) {
		this.stateType = stateType;
	}

	public String getToState() {
		return toState;
	}
	public void setToState(String toState) {
		this.toState = toState;
	}
	public int getBranchLength() {
		return branchLength;
	}
	public void setBranchLength(int branchLength) {
		this.branchLength = branchLength;
	}
	public int getOriginalAnomalyLine() {
		return originalAnomalyLine;
	}
	public void setOriginalAnomalyLine(int originalAnomalyLine) {
		this.originalAnomalyLine = originalAnomalyLine;
	}
	public List<String> getAnomalousEvents() {
		return anomalousEvents;
	}
	public void setAnomalousEvents(List<String> anomalousEvents) {
		this.anomalousEvents = anomalousEvents;
	}
	public List<String> getOriginalAnomalousEvents() {
		return originalAnomalousEvents;
	}
	public void setOriginalAnomalousEvents(List<String> originalAnomalousEvents) {
		this.originalAnomalousEvents = originalAnomalousEvents;
	}
	
}
