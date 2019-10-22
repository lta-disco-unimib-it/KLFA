package check.ioInvariantParser;

public class EvaluationRuntimeErrors {

	public static void emptyStack() {
//		System.out.println("Empty Stack during the evaluation of invariant: " + IoInvariantParser.getExpressionUnderEvaluation());
//		System.out.flush();
		
	}
	
	/**
	 * Usually it indicates that the parser is in the wrong way.
	 *
	 */
	public static void evaluationError() {
//		System.out.println("Error during the evaluation of invariant: " + IoInvariantParser.getExpressionUnderEvaluation());
//		Thread.dumpStack();
//		System.out.flush();
	}
	
	public static void noSuchMethod(String methodName  ) {
		
//		//if we are inside right side of an implication and left side is false we have to do nothing
//		ImplicationStateRegistry isr = ImplicationStateRegistry.getInstance();
//		if ( isr.isImplication() && ( ! isr.isLeftSideTrue() ) )
//			return;
//
//		//if we are in right side of a false "and" access errors can occurr, dont signalate them
//		AndStateRegistry asr = AndStateRegistry.getInstance();
//		if( asr.isAndState() && ( ! asr.isLeftResultTrue()) )
//			return;
//		
//		System.out.println("Error during the evaluation of invariant: " + IoInvariantParser.getExpressionUnderEvaluation() +" : ");
//		System.out.println("\tNo such method : "+methodName);
//		System.out.flush();
	}
	
	public static void nullObjectWithMethodToBeInvoked(String methodName) {
//		//if we are in right side of an implication whith left side false, access errors can occurr, dont signalate them
//		ImplicationStateRegistry isr = ImplicationStateRegistry.getInstance();
//		if ( isr.isImplication() && ( ! isr.isLeftSideTrue() ) )
//			return;
//
//		//if we are in right side of a false "and" access errors can occurr, dont signalate them
//		AndStateRegistry asr = AndStateRegistry.getInstance();
//		if( asr.isAndState() && ( ! asr.isLeftResultTrue()) )
//			return;
//		
//		System.out.println("Error during the evaluation of invariant: " + IoInvariantParser.getExpressionUnderEvaluation() +" : ");
//		System.out.println("\tmethod "+methodName+" cannot be invoked on null object");
//		//an object o that should be target of a method invocation m (o.m())
//		// is NULL
//		System.out.flush();
	}

	public static void nullObjectWithAttribute(String memberName) {
//		//if we are in right side of an implication whith left side false access errors can occurr, dont signalate them
//		ImplicationStateRegistry isr = ImplicationStateRegistry.getInstance();
//		if ( isr.isImplication() && ( ! isr.isLeftSideTrue() ) )
//			return;
//		
//		//if we are in right side of a false "and" access errors can occurr, dont signalate them
//		AndStateRegistry asr = AndStateRegistry.getInstance();
//		if( asr.isAndState() && ( ! asr.isLeftResultTrue()) )
//			return;
//		
//		System.out.println("Error during the evaluation of invariant: " + IoInvariantParser.getExpressionUnderEvaluation() +" : ");
//		System.out.println("\tcannot visit "+memberName+" in null object");
//		System.out.flush();
	}

	public static void leftImplictionNotBoolean() {
//		System.out.println("Error during the evaluation of invariant: " + IoInvariantParser.getExpressionUnderEvaluation() +" : left side of implication is not a boolean");
//		System.out.flush();
	}

	public static void leftAndNotBoolean() {
//		System.out.println("Error during the evaluation of invariant: " + IoInvariantParser.getExpressionUnderEvaluation() +" : left side of and is not a boolean");
//		System.out.flush();
	}

	public static void noSuchField(String name) {
		
//		System.out.println("No field "+name);
//		System.out.flush();
		
	}
	
	public static void log(String msg){
		System.err.println(msg);
		System.out.flush();
	}
}
