package check.ioInvariantParser;

public class ImplicationStateRegistry {
	
	private boolean implication = false;
	private boolean leftSideTrue = false;
	private int lastQueueSize = 0;
	
	private static ImplicationStateRegistry instance;

	public static synchronized ImplicationStateRegistry getInstance(){
		if ( instance == null ){
			instance = new ImplicationStateRegistry();
		}
		return instance;
	}

	public boolean isImplication() {
		return implication;
	}

	public void setImplication(boolean implication) {
		//System.out.println("implication state "+implication);
		this.implication = implication;
	}

	public boolean isLeftSideTrue() {
		return leftSideTrue;
	}

	public void setLeftSideTrue(boolean leftSideTrue) {
		//System.out.println("implication left side "+leftSideTrue);
		this.leftSideTrue = leftSideTrue;
	}

	public int getLastQueueSize() {
		return lastQueueSize;
	}

	public void setLastQueueSize(int lastQueueSize) {
		this.lastQueueSize = lastQueueSize;
	}
	
	public void reset(){
		//System.out.println("reset implication");
		implication = false;
		leftSideTrue = false;
		lastQueueSize = 0;
	}
}
