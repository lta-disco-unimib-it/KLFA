package check.ioInvariantParser;

import sjm.parse.Assembler;
import sjm.parse.Assembly;

public class LeftAndAssembler extends Assembler {
	
	
	public void workOn(Assembly a) {
		
		Target target = null;
		
		//System.out.println("LeftAndAssembler");
		target = (Target)a.getTarget();
		
		try {
			Boolean lr = (Boolean)target.top();
			AndStateRegistry.getInstance().setAndState(lr, target.queueSize() );
		} catch ( ClassCastException e ){
			EvaluationRuntimeErrors.leftAndNotBoolean();
		}
		
	}


}
