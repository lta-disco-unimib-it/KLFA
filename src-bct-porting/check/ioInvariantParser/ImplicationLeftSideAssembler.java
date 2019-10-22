package check.ioInvariantParser;

import sjm.parse.Assembler;
import sjm.parse.Assembly;

public class ImplicationLeftSideAssembler extends Assembler {

	
	public void workOn(Assembly a) {
		Target target = null;
		target = (Target) a.getTarget();
		
		
		try  {
			Boolean leftSide = ( Boolean ) target.top();
			ImplicationStateRegistry.getInstance().setImplication(true);
			ImplicationStateRegistry.getInstance().setLeftSideTrue(leftSide.booleanValue());
			ImplicationStateRegistry.getInstance().setLastQueueSize( target.queueSize() );
		} catch ( ClassCastException e ){
			EvaluationRuntimeErrors.leftImplictionNotBoolean();
		} catch ( ArrayIndexOutOfBoundsException e ){
			
		}
	}

}
