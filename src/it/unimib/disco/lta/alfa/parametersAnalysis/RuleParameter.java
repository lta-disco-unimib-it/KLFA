package it.unimib.disco.lta.alfa.parametersAnalysis;

import java.util.Set;
import java.util.regex.Pattern;


public class RuleParameter {
	
	private String ruleName;
	private int parameterPos;


	public RuleParameter(String ruleName, int parameterPos ){
		this.ruleName = ruleName;
		this.parameterPos = parameterPos;
	}

	

	public String getRuleName() {
		return ruleName;
	}

	public int getParameterPos() {
		return parameterPos;
	}
	
	public String toString(){
		return ruleName+"_"+parameterPos;
	}
	
	public int hashCode(){
		return toString().hashCode();
	}
	
	public boolean equals(Object o ){
		
		if ( ! ( o instanceof RuleParameter ) ){
			return false;
		}
		
		RuleParameter rhs = (RuleParameter) o;
		return ( rhs.ruleName.equals(ruleName) && rhs.parameterPos == parameterPos );
	}



}
