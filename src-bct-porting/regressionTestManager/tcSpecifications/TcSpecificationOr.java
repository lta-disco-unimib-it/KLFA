package regressionTestManager.tcSpecifications;

import java.util.ArrayList;
import java.util.Iterator;

public class TcSpecificationOr implements TcSpecification {
	
	private ArrayList specifications = new ArrayList();
	
	public TcSpecificationOr ( TcSpecification spec ){
		specifications.add(spec);
	}

	public void addElement(TcSpecification tcs) {
		specifications.add( tcs );
	}
	
	public boolean equals ( Object o ){
		
		if ( o == this )
			return true;
		if ( o instanceof TcSpecificationOr ){
			TcSpecificationOr rhs = ( TcSpecificationOr) o;
			if ( rhs.specifications.size() != specifications.size() )
				return false;
			Iterator itr = rhs.specifications.iterator();
			while ( itr.hasNext() ){
				TcSpecification elem = (TcSpecification)itr.next();
				if ( ! specifications.contains( elem )){
					System.out.println("Not contains "+elem);
					return false;
				}
			}
			return true;
		}
		
		return false;
	}
	
	public String toString(){
		String msg = null;
		Iterator it = specifications.iterator();
		while ( it.hasNext() ){
			if ( msg == null )
				msg = it.next().toString();
			else
				msg += " OR "+it.next();
		}
		
		return msg;
	}

	public Iterator getElementsIterator() {
		return specifications.iterator();
	}
	
	
	
}
