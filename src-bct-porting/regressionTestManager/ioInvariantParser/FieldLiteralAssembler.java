package regressionTestManager.ioInvariantParser;

import java.lang.reflect.Field;
import java.util.ArrayList;

import flattener.utilities.FieldFilter;
import flattener.utilities.FieldFilterVoid;
import flattener.utilities.FieldsRetriever;

import sjm.parse.Assembler;
import sjm.parse.Assembly;
import sjm.parse.tokens.Token;

public class FieldLiteralAssembler extends Assembler  {

	public void workOn(Assembly a) {
		Target target = (Target)a.getTarget();

		//Token memberNameToken = (Token)a.pop();
		Token memberNameToken = null;
		try{
			memberNameToken = (Token)a.pop();
		}catch(ArrayIndexOutOfBoundsException e) {
			EvaluationRuntimeErrors.emptyStack();
			return;
		}

		String memberName = memberNameToken.sval();

		EvaluationRuntimeErrors.log("FieldLiteralAssembler "+memberName);

		/*
      Object targetObject = target.pop();
		 */
		Variable targetObject = null;
		try{
			targetObject = (Variable) target.pop();
			target.push(new Variable(targetObject.getProgramPoint(),targetObject.getName()+"."+memberName));
		}catch(ArrayIndexOutOfBoundsException e) {
			EvaluationRuntimeErrors.emptyStack();
			return;
		}
		
		
	}
}