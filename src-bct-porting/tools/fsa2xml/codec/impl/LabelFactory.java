package tools.fsa2xml.codec.impl;

import java.util.HashMap;

public class LabelFactory {
	private HashMap<String,String> label = new HashMap<String, String>();
	
	/**
	 * Returns a label equals to the provided one.
	 * This method guarantees that all the labels returned which are equals are also the same object.
	 * @param description
	 * @return
	 */
	public String getLabel(String description) {
		String desc = label.get(description);
		if ( desc == null ){
			label.put(description, description);
			desc = description;
		}
		return desc;
	}

}
