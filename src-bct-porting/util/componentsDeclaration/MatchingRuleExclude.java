package util.componentsDeclaration;


public class MatchingRuleExclude extends MatchingRule {

	public MatchingRuleExclude(String packageExpr, String classExpr, String methodExpr) {
		super(packageExpr, classExpr, methodExpr);
	}

	@Override
	public boolean acceptMethod( String packageName, String className, String methodName ){
		return false;
	}

	@Override
	public boolean acceptClass(String packageName, String className) {
		return false;
	}

	@Override
	public boolean rejectClass(String packageName, String className) {
		return ! matchClass(packageName, className );
	}

	@Override
	public boolean rejectMethod(String packageName, String className, String methodName) {
		return ! matchMethod( packageName, className, methodName );
	}
}
