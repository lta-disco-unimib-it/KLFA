package dfmaker.core;

import java.util.Collection;
import java.util.HashMap;

public class SuperstructureCollection {
	private HashMap superstructures = new HashMap(2);
	
	public void put ( Superstructure superstructure ){
		superstructures.put( superstructure.getProgramPointName(), superstructure );
	}
	
	public Superstructure get ( String programPointName ){
		return (Superstructure) superstructures.get(programPointName);
	}

	public Collection values() {
		return superstructures.values();
	}
}
