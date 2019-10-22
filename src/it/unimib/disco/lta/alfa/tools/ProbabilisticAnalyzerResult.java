/*******************************************************************************
 *    Copyright 2019 Fabrizio Pastore, Leonardo Mariani
 *   
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 *******************************************************************************/
package it.unimib.disco.lta.alfa.tools;

import grammarInference.Record.Symbol;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.Map.Entry;

public class ProbabilisticAnalyzerResult {
	private HashMap<KPath,Integer> pathsCount = new HashMap<KPath, Integer>();
	private int totEls=0;
	
	public void addPath(KPath kpath) {
		Integer pathCount = pathsCount.get(kpath);
		totEls++;
		if ( pathCount == null ){
			pathCount = 1;
		} else {
			pathCount++;
		}
		
		
		pathsCount.put(new KPath(kpath), pathCount);
		
	}

	public void addResults(ProbabilisticAnalyzerResult result) {
		for ( Entry<KPath,Integer> resultEntry : result.pathsCount.entrySet() ){
			
			Integer myEntryCount = pathsCount.get(resultEntry.getKey());
			
			Integer newValue;
			totEls+=resultEntry.getValue();
			if ( myEntryCount == null ){
				newValue = resultEntry.getValue();
			} else {
				newValue = myEntryCount + resultEntry.getValue();
			}
			
			pathsCount.put(new KPath(resultEntry.getKey()), newValue);
		}
	}

	public Set<Entry<KPath, Integer>> entrySet() {
		return pathsCount.entrySet();
	}

	public int symbolsNumber() {
		return totEls;
	}

	public Set<KPath> getPaths() {
		return pathsCount.keySet();
	}

	public Integer getOccurrencies(KPath path) {
		if ( ! pathsCount.containsKey(path))
			return 0;
		return pathsCount.get(path);
	}

	public double getPercentage(KPath path) {
		return (double)getOccurrencies(path)/(double)symbolsNumber();
	}

}
