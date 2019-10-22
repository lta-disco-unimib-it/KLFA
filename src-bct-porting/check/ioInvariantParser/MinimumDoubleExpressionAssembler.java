package check.ioInvariantParser;

import sjm.parse.Assembler;
import sjm.parse.Assembly;

public class MinimumDoubleExpressionAssembler extends Assembler{

	public void workOn(Assembly a) {
		Target target = (Target)a.getTarget();
		try{
			Double z = (Double)target.pop();
			Double y = (Double)target.pop();
			Double x = (Double)target.pop();

			if ( z != null && y != null && x != null )
				target.push( Boolean.valueOf(Math.min(y.longValue() , z.longValue()) == x.longValue()));
			else {
				EvaluationRuntimeErrors.evaluationError();
				return;
			}
		}	catch(ArrayIndexOutOfBoundsException e) {
			EvaluationRuntimeErrors.emptyStack();
			return;
		}	catch (Exception e) {
			EvaluationRuntimeErrors.evaluationError();

			return;
		}
	}
}
