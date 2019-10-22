package regressionTestManager.tcSpecifications;

public class TcSpecificationAnd implements TcSpecification {

	private TcSpecification rigthSide;
	private TcSpecification leftSide;

	public TcSpecificationAnd(TcSpecification leftSide, TcSpecification rightSide) {
		this.leftSide = leftSide;
		this.rigthSide = rightSide;
	}

	public String toString(){
		return " ( "+leftSide+" AND "+rigthSide+" ) ";
	}
	
	public boolean equals( Object o ){
		if ( o == this )
			return true;
		if ( o instanceof TcSpecificationAnd ){
			TcSpecificationAnd spec = (TcSpecificationAnd) o;
			return ( spec.contains(leftSide) && spec.contains( rigthSide) );
		}
		return false;
			
	}

	private boolean contains(TcSpecification spec) {
		return ( spec.equals( rigthSide) || spec.equals( leftSide) );
	}

	public TcSpecification getLeftSide() {
		return leftSide;
	}

	public TcSpecification getRigthSide() {
		return rigthSide;
	}
}
