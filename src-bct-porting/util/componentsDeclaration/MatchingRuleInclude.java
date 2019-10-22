package util.componentsDeclaration;


public class MatchingRuleInclude extends MatchingRule {

	public MatchingRuleInclude(String packageExpr, String classExpr,
			String methodExpr) {
		super(packageExpr, classExpr, methodExpr);
	}

	@Override
	public boolean acceptClass(String packageName, String className) {
		return matchClass(packageName, className);
	}

	@Override
	public boolean acceptMethod(String packageName, String className, String methodName) {
		return matchMethod(packageName, className, methodName);
	}

	@Override
	public boolean rejectClass(String packageName, String className) {
		return false;
	}

	@Override
	public boolean rejectMethod(String packageName, String className, String methodName) {
		return false;
	}

}
