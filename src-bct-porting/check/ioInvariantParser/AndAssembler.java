package check.ioInvariantParser;

import sjm.parse.Assembler;
import sjm.parse.Assembly;

public class AndAssembler extends Assembler {

	
	public void workOn(Assembly a) {
		Target target = null;
		Boolean leftSide = null;
		Boolean rightSide = null;
		//System.out.println("AndAssembler");
		try{
			target = (Target)a.getTarget();
			
			//if left side of and was not true expression on right side can be not evaluated
			if ( AndStateRegistry.getInstance().getLastQueueSize() == target.queueSize() 
					&& ( ! AndStateRegistry.getInstance().isLeftResultTrue() ) ){
				leftSide = (Boolean) target.pop();
				rightSide = Boolean.FALSE;
			} else {
				rightSide = (Boolean)target.pop();  
				leftSide = (Boolean)target.pop();  
			}
			
		}catch(ArrayIndexOutOfBoundsException e) {
			//System.out.println("AndAssembler "+target.toString());
			EvaluationRuntimeErrors.emptyStack();
			return;
		}finally{
			AndStateRegistry.getInstance().reset();
		}
		
		if( leftSide.equals(Boolean.TRUE) && rightSide.equals(Boolean.TRUE) )
			target.push(new Boolean(true));
		else
			target.push(new Boolean(false));
	}
	


}
