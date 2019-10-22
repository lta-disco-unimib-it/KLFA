package it.unimib.disco.lta.alfa.parametersAnalysis;

import it.unimib.disco.lta.alfa.utils.FileUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 * This class contains data representing the configuration to use with the trace preprocessor
 * 
 * @author Fabrizio Pastore fabrizio.pastore AT gmail.com
 *
 */
public class TracePreprocessorConfiguration {

	private List<List<String>> preprocessingRules;
	private Map<String, String> transformersMap;
	
	public TracePreprocessorConfiguration( ){
		preprocessingRules = new ArrayList<List<String>>();
		transformersMap = new HashMap<String, String>();
	}

	public TracePreprocessorConfiguration( Map<String, String> transformerRulesMap, List<List<String>> preprocessingRules ){
		this.preprocessingRules = preprocessingRules;
		this.transformersMap = transformerRulesMap;
	}

	public List<List<String>> getPreprocessingRules() {
		return preprocessingRules;
	}

	public String getTransformersFileContent() {
		
		StringBuffer sbTransformers = new StringBuffer();
		for( Entry<String,String> e : transformersMap.entrySet() ){
			sbTransformers.append(e.getKey());
			sbTransformers.append(",");
			sbTransformers.append(e.getValue());
			sbTransformers.append(FileUtils.lineSeparator);
		}
		
		return sbTransformers.toString();
	}



	public Map<String, String> getTransformersMap() {
		return transformersMap;
	}
	
	public void mergeConfiguration( TracePreprocessorConfiguration toMerge ){
		this.preprocessingRules.addAll(toMerge.preprocessingRules);
		this.transformersMap.putAll(toMerge.transformersMap);
	}
	
}
