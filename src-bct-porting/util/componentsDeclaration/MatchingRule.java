package util.componentsDeclaration;


public abstract class MatchingRule {
	private String packageExpr;
	private String classExpr;
	private String methodExpr;
	
	protected MatchingRule( String packageExpr, String classExpr, String methodExpr ){
		this.packageExpr = packageExpr;
		this.classExpr = classExpr;
		this.methodExpr = methodExpr;
	}

	/**
	 * Returns true if according to this rule the method must be accepted
	 * 
	 * @param packageName
	 * @param className
	 * @param methodName
	 */
	public abstract boolean acceptMethod( String packageName, String className, String methodName );
	
	
	/**
	 * Returns true if according to this rule the method must be rejected
	 * 
	 * @param packageName
	 * @param className
	 * @param methodName
	 */
	public abstract boolean rejectMethod( String packageName, String className, String methodName );
	
	/**
	 * Returns true if according to this rule the class must be accepted
	 * 
	 * @param packageName
	 * @param className
	 * @param methodName
	 */
	public abstract boolean acceptClass( String packageName, String className );
	
	/**
	 * Returns true if according to this rule the class must be rejected
	 * @param packageName
	 * @param className
	 * @return
	 */
	public abstract boolean rejectClass(String packageName, String className);
	
	public String getClassExpr() {
		return classExpr;
	}

	public void setClassExpr(String classExpr) {
		this.classExpr = classExpr;
	}

	public String getMethodExpr() {
		return methodExpr;
	}

	public void setMethodExpr(String methodExpr) {
		this.methodExpr = methodExpr;
	}

	public String getPackageExpr() {
		return packageExpr;
	}

	public void setPackageExpr(String packageExpr) {
		this.packageExpr = packageExpr;
	}
	
	
	/**
	 * Returns wether a method is matched by the rule defined in this object
	 * 
	 * @param packageName
	 * @param className
	 * @param methodName
	 * @return
	 */
	protected boolean matchMethod( String packageName, String className, String methodName ){
		if ( ! matchClass(packageName,className))
			return false;
		return methodName.matches(methodExpr);
	}

	
	/**
	 * Returns wether the rule defined in this object matches the passed class
	 * 
	 * @param packageName
	 * @param className
	 * @return
	 */
	protected boolean matchClass(String packageName, String className) {
		if ( ! packageName.matches(packageExpr) )
			return false;
		
		return className.matches(classExpr);
	}

	/**
	 * Returns true if the rule match a method
	 * @return
	 */
	public boolean isMethodRule(){
		return ( classExpr.length() > 0 );
	}
	
	/**
	 * Returns true if the rule match a function
	 * @return
	 */
	public boolean isFunctionRule(){
		return ( classExpr.length() == 0 );
	}
}
