package modelsFetchers;

import java.util.ArrayList;
import java.util.Iterator;

public class IoModel {
	private ArrayList preconditions = new ArrayList();
	private ArrayList postconditions = new ArrayList();	
	
	public void addPreconditions(ArrayList preconditions) {
		this.preconditions.addAll(preconditions);
	}

	public void addPrecondition(String line) {
		preconditions.add(line);
	}

	public void addPostconditions(ArrayList postconditions) {
		this.postconditions.addAll(postconditions);
	}
	
	public void addPostcondition(String line) {
		postconditions.add(line);
	}
	
	public Iterator preconditionsIt(){
		return preconditions.iterator();
	}

	public Iterator postconditionsIt(){
		return postconditions.iterator();
	}
}
