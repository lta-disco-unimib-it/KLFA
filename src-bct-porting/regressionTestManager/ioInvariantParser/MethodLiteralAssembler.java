package regressionTestManager.ioInvariantParser;

import sjm.parse.Assembler;
import sjm.parse.Assembly;
import sjm.parse.tokens.Token;

public class MethodLiteralAssembler extends Assembler  {
	public void workOn(Assembly a) {
		Target target = (Target)a.getTarget();
		//Token methodNameToken = (Token)a.pop();
		Token methodNameToken = null;
		try{
			methodNameToken = (Token)a.pop();
		}catch(ArrayIndexOutOfBoundsException e) {
			EvaluationRuntimeErrors.emptyStack();
			return;
		}



		String methodName = methodNameToken.sval();

		//System.out.println("Method literal:"+methodName);
		//Object targetObject = target.pop();
		Variable targetObject = null;
		try{
			targetObject = (Variable) target.pop();
		}catch(ArrayIndexOutOfBoundsException e) {
			EvaluationRuntimeErrors.emptyStack();
			return;
		}

		target.push(new Variable( target.getProgramPointName(), targetObject.getName()+"."+methodName+"()"));
	}

}