package regressionTestManager.tcSpecifications;

public class TcSpecificationNot implements TcSpecification {
	private TcSpecification element;
	
	public TcSpecificationNot( TcSpecification specification) {
		this.element = specification;
	}

	public String toString(){
		return "! "+element.toString();
	}
	
	public boolean equals( Object o ){
		if ( o == this )
			return true;
		if ( o instanceof TcSpecificationNot ){
			TcSpecificationNot spec = (TcSpecificationNot) o;
			return spec.element.equals(element);
		}
		return false;
	}

	public TcSpecification getElement() {
		return element;
	}
}
