package check.ioInvariantParser;

/**
 * This class maintain infomrmation on current expression. It tells the user if we are inside right side of an and.
 * FIXME: this class does not handle correctly annidated ands.
 * 
 * @author Fabrizio Pastore [ fabrizio.pastore at gmail dot com ]
 *
 */
public class AndStateRegistry {

	private static AndStateRegistry instance = null;
	private boolean andState = false;
	private boolean leftResult;
	private int 	lastQueueSize = 0;
	private boolean clear = true;
	
	
	
	
	public static synchronized AndStateRegistry getInstance(){
		if ( instance == null ){
			instance = new AndStateRegistry();
		}
		return instance;
	}


	public boolean isAndState() {
		return andState;
	}


	public void setAndState( Boolean lr, int i) {
		this.andState = true;
		
		if ( clear ){
			this.leftResult = lr.booleanValue();
			this.lastQueueSize = i;
			clear = false;
		} else if ( leftResult == true ){
			leftResult = lr.booleanValue();
		}
	}


	public boolean isLeftResultTrue() {
		return leftResult;
	}


	
	public void reset(){
		//System.out.println("ReSetting state ");
		andState = false;
		clear = true;
	}


	public int getLastQueueSize() {
		return lastQueueSize;
	}


}
