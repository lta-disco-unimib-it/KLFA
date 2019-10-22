package util.componentsDeclaration;

import java.util.ArrayList;
import java.util.List;


public class Component {
	
	protected List<MatchingRule> rules = new ArrayList<MatchingRule>();
	private String name;
	
	public Component( String name, List<MatchingRule> rules ){
		this( name );
		this.rules.addAll(rules);
	}

	public Component( String name ) {
		this.name = name;
	}
	
	public void addRule(MatchingRule rule) {
		rules.add(rule);
	}
	
	public boolean acceptClass( String packageName, String className ){
		System.out.println("Component.acceptClass : "+packageName+" "+className);
		for ( MatchingRule rule : rules ){
			if ( rule.acceptClass(packageName, className) ){
				return true;
			} else if ( rule.rejectClass(packageName, className) ) {
				return false;
			}
		}
		return false;
	}

	public String getName() {
		return name;
	}

	public List<MatchingRule> getRules() {
		return rules;
	}

	public boolean acceptFunction(String packageName, String functionName) {
		return acceptMethod( packageName, "", functionName );
	}

	public boolean acceptMethod(String packageName, String className, String methodName) {
		for ( MatchingRule rule : rules ){
			if ( rule.acceptMethod(packageName, className, methodName) ){
				return true;
			} else if ( rule.rejectMethod(packageName, className, methodName ) ) {
				return false;
			}
		}
		return false;
	}
}
