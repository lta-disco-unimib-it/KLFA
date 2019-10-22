package it.unimib.disco.lta.alfa.dataTransformation;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * This value trabformer work like the ValueTransformerGlobalHash, but two distinct values cannot be associated to the same abstract value (the ker automatically generated)
 * In that case the algorithm associate a new key by adding the appearance order.
 * 
 * 
 * 
 * @author Fabrizio Pastore
 *
 */
public class ValueTransformerGlobalHashIdentity extends ValueTransformerGlobalHash {
	private Set<String> generatedKeys = new HashSet<String>();
	
	public ValueTransformerGlobalHashIdentity(String prefix,
			List<Integer> positions) {
		super(prefix, positions);
	}

	@Override
	protected String calculateKey(String[] line) {
		
		String generatedKey = super.calculateKey(line);
		
		int i = 1;
		String key = generatedKey;
		while ( generatedKeys.contains(key) ){
			key=generatedKey+"_"+i;
		}
		generatedKeys.add(key);
		return key;
	}

}
