/*******************************************************************************
 *    Copyright 2019 Fabrizio Pastore, Leonardo Mariani
 *   
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 *******************************************************************************/
package regressionTestManager.ioInvariantParser;

public class EvaluationRuntimeErrors {

	public static void emptyStack() {
		//System.out.println("Empty Stack during the evaluation of invariant: " + IoInvariantParser.getExpressionUnderEvaluation());
		//System.out.flush();
		
	}
	
	/**
	 * Usually it indicates that the parser is in the wrong way.
	 *
	 */
	public static void evaluationError() {
		System.out.println("Error during the evaluation of invariant: " + IoInvariantParser.getExpressionUnderEvaluation());
		Thread.dumpStack();
		System.out.flush();
	}
	
	public static void noSuchMethod(String methodName  ) {
		
		//if we are inside right side of an implication and left side is false we have to do nothing
		ImplicationStateRegistry isr = ImplicationStateRegistry.getInstance();
		if ( isr.isImplication() && ( ! isr.isLeftSideTrue() ) )
			return;

		//if we are in right side of a false "and" access errors can occurr, dont signalate them
		AndStateRegistry asr = AndStateRegistry.getInstance();
		if( asr.isAndState() && ( ! asr.isLeftResultTrue()) )
			return;
		
		//System.out.println("Error during the evaluation of invariant: " + IoInvariantParser.getExpressionUnderEvaluation() +" : ");
		//System.out.println("\tNo such method : "+methodName);
		//System.out.flush();
	}
	
	public static void nullObjectWithMethodToBeInvoked(String methodName) {
		//if we are in right side of an implication whith left side false, access errors can occurr, dont signalate them
		ImplicationStateRegistry isr = ImplicationStateRegistry.getInstance();
		if ( isr.isImplication() && ( ! isr.isLeftSideTrue() ) )
			return;

		//if we are in right side of a false "and" access errors can occurr, dont signalate them
		AndStateRegistry asr = AndStateRegistry.getInstance();
		if( asr.isAndState() && ( ! asr.isLeftResultTrue()) )
			return;
		
		//System.out.println("Error during the evaluation of invariant: " + IoInvariantParser.getExpressionUnderEvaluation() +" : ");
		//System.out.println("\tmethod "+methodName+" cannot be invoked on null object");
		//an object o that should be target of a method invocation m (o.m())
		// is NULL
		//System.out.flush();
	}

	public static void nullObjectWithAttribute(String memberName) {
		//if we are in right side of an implication whith left side false access errors can occurr, dont signalate them
		ImplicationStateRegistry isr = ImplicationStateRegistry.getInstance();
		if ( isr.isImplication() && ( ! isr.isLeftSideTrue() ) )
			return;
		
		//if we are in right side of a false "and" access errors can occurr, dont signalate them
		AndStateRegistry asr = AndStateRegistry.getInstance();
		if( asr.isAndState() && ( ! asr.isLeftResultTrue()) )
			return;
		
		//System.out.println("Error during the evaluation of invariant: " + IoInvariantParser.getExpressionUnderEvaluation() +" : ");
		//System.out.println("\tcannot visit "+memberName+" in null object");
		//System.out.flush();
	}

	public static void leftImplictionNotBoolean() {
		//System.out.println("Error during the evaluation of invariant: " + IoInvariantParser.getExpressionUnderEvaluation() +" : left side of implication is not a boolean");
		//System.out.flush();
	}

	public static void leftAndNotBoolean() {
		//System.out.println("Error during the evaluation of invariant: " + IoInvariantParser.getExpressionUnderEvaluation() +" : left side of and is not a boolean");
		//System.out.flush();
	}

	public static void noSuchField(String name) {
		
		//System.out.println("No field "+name);
		//System.out.flush();
		
	}
	
	public static void log(String msg){
		//System.err.println(msg);
		//System.err.flush();
	}
}
