package it.unimib.disco.lta.alfa.parametersAnalysis;

public class ParametersIntersection {

	private RuleParameter ruleParameter2;
	private RuleParameter ruleParameter1;
	private int intersectionSize;

	public ParametersIntersection(RuleParameter ruleParameter1,RuleParameter ruleParameter2,int size){
		this.ruleParameter1 = ruleParameter1;
		this.ruleParameter2 = ruleParameter2;
		this.intersectionSize = size;
	}

	public RuleParameter getRuleParameter2() {
		return ruleParameter2;
	}

	public RuleParameter getRuleParameter1() {
		return ruleParameter1;
	}

	public int getIntersectionSize() {
		return intersectionSize;
	}
}
