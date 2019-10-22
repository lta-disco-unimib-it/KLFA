package it.unimib.disco.lta.alfa.parametersAnalysis;

import it.unimib.disco.lta.alfa.dataTransformation.ValueTransformer;
import it.unimib.disco.lta.alfa.dataTransformation.ValueTransformerGlobal;
import it.unimib.disco.lta.alfa.dataTransformation.ValueTransformerRelativeToAccess;
import it.unimib.disco.lta.alfa.dataTransformation.ValueTransformerRelativeToInstantiation;
import it.unimib.disco.lta.alfa.dataTransformation.ValueTransformerSame;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map.Entry;

public class TransformationRulesAnalysis {
	private ValueTransformerSame same = new ValueTransformerSame();
	private ValueTransformerGlobal go = new ValueTransformerGlobal("");
	private ValueTransformerRelativeToAccess ra = new ValueTransformerRelativeToAccess("");
	private ValueTransformerRelativeToInstantiation ri = new ValueTransformerRelativeToInstantiation("");
	private HashMap<ValueTransformer,List<List<String>>> a = new HashMap<ValueTransformer, List<List<String>>>();
	
	public TransformationRulesAnalysis(){
		LinkedList<List<String>> list = new LinkedList<List<String>>();
		list.add(new LinkedList<String>());
		a.put(same,list);
		
		list = new LinkedList<List<String>>();
		list.add(new LinkedList<String>());
		a.put(ra,list);
		
		list = new LinkedList<List<String>>();
		list.add(new LinkedList<String>());
		a.put(go,list);
		
		list = new LinkedList<List<String>>();
		list.add(new LinkedList<String>());
		a.put(ri,list);
		
	}
	
	public void addParameters(ArrayList<String> par) {
		for ( String data : par ){
			addValues(same,same.getTransformedValue(data));
			addValues(go,go.getTransformedValue(data));
			addValues(ra,ra.getTransformedValue(data));
			addValues(ri,ri.getTransformedValue(data));
		}
	}



	private void addValues(ValueTransformer vt, String transformedValue) {
		List<List<String>> list = a.get(vt);
		list.get(list.size()-1).add(transformedValue);
	}



	public void newTrace() {
		for ( Entry<ValueTransformer,List<List<String>>> entry : a.entrySet() ){
			entry.getValue().add(new LinkedList<String>());
		}
	}

}
