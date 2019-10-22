package check.ioInvariantParser;

import sjm.parse.Assembler;
import sjm.parse.Assembly;

/**
 * Assembler for the implication production.
 * This is executed after all production is read and so there are 
 * left side result and right side result on the stack.
 * 
 * @author Fabrizio Pastore [ fabrizio.pastore at gmail dot com ]
 *
 */
public class ImplicationAssembler extends Assembler {

	
	public void workOn(Assembly a) {
		Target target = null;
		Boolean leftSide = null;
		Boolean rightSide = null;
		//System.out.println("ImplicationAssembler ");
		try{
			target = (Target)a.getTarget();
			
			//FIXME: add check for non existent variable
			
			//if left side was FALSE and
			//there is no right side due to access problems, evaluation is still correct
			if (  ! ImplicationStateRegistry.getInstance().isLeftSideTrue() ){
				if(	target.queueSize() == ImplicationStateRegistry.getInstance().getLastQueueSize() ) {
					target.pop();
				} else {
					target.pop();
					target.pop();
				}
				leftSide = Boolean.FALSE;
				rightSide = Boolean.TRUE;
			} else {
				rightSide = (Boolean)target.pop();  
				leftSide = (Boolean)target.pop();  
			}
		}catch(ArrayIndexOutOfBoundsException e) {
			//System.out.println("ImplicationAssembler "+target.toString());
			EvaluationRuntimeErrors.emptyStack();
			return;
		}
		//System.out.println("Impication "+leftSide+" "+rightSide);
		if( leftSide.equals(Boolean.FALSE) || rightSide.equals(Boolean.TRUE) )
			target.push( Boolean.TRUE );
		else
			target.push( Boolean.FALSE );
		
		//we are out of an implication, reset
		ImplicationStateRegistry.getInstance().reset();
	}



}
