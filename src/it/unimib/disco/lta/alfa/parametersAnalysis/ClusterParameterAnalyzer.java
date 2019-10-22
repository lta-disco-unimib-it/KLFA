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
package it.unimib.disco.lta.alfa.parametersAnalysis;

import it.unimib.disco.lta.alfa.logging.Logger;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;



public class ClusterParameterAnalyzer {
	
	private RTableExporter exporter;
	private Cluster<RuleParameter> cluster;
	private ArrayList<Integer> interestingTraces = null;
	private String traceSeparator = "|";
	private int maxLinesToAnalyze = -1;

	public int getMaxLinesToAnalyze() {
		return maxLinesToAnalyze;
	}

	public void setMaxLinesToAnalyze(int maxLinesToAnalyze) {
		this.maxLinesToAnalyze = maxLinesToAnalyze;
	}

	public ClusterParameterAnalyzer(RTableExporter exporter,Cluster<RuleParameter> cluster){
		this.exporter = exporter;
		this.cluster = cluster;
	}

	public void setInterestingTraces(List<Integer> traces){
		this.interestingTraces = new ArrayList<Integer>(traces.size());
		interestingTraces.addAll(traces);
	}

	public ParameterAnalysisResult analyze(File file) throws IOException {
		Logger.info("ClusterParameterAnalyzer: starting analysis "+file.getAbsolutePath());
		BufferedReader reader = new BufferedReader(new FileReader(file));
		
		int analyzedLines = 0;
		
		try{

			ParameterAnalysisResult result = new ParameterAnalysisResult();

			String line;
			boolean newTrace = false;
			int curTrace=0;
			while(( line = reader.readLine())!=null){
				
				if ( newTrace == true ){ //Add trace only if it has content 
					result.newTrace();
					curTrace++;
					newTrace = false;
				}
				
				if ( line.equals(traceSeparator )){
					Logger.fine(ClusterParameterAnalyzer.class.getName()+" trace separator found, new trace");
					newTrace = true;
					continue;
				} 
				
				
				
				result.newLine();
				
				Logger.finest(ClusterParameterAnalyzer.class.getName()+" new line");
				
				if ( ! analyzeTrace(curTrace) ){
					continue;
				}
				
				Logger.finest("Analyzing line");
				
				String signature = exporter.getRuleSignature(line);
				
				analyzedLines++;
				
				if ( maxLinesToAnalyze > 0 && analyzedLines > maxLinesToAnalyze ){
					break;
				}

				for ( RuleParameter ruleParameter : cluster.getElements() ){
					if ( signature.equals(ruleParameter.getRuleName()) ){
						Logger.finest(ClusterParameterAnalyzer.class.getName()+" adding parameter ");
						ArrayList<String> parameters = exporter.getParameters(line);
						ArrayList<String> par = new ArrayList<String>(1);
						if ( parameters.size() > ruleParameter.getParameterPos() ){
							par.add(parameters.get(ruleParameter.getParameterPos()));
						}
						
						result.addStat(par);
						break;
					}
				}
				
				
				
				
			}

			reader.close();
			
			return result;
		} finally {
			if ( reader != null ){
				reader.close();
			}
		}
	}

	/**
	 * Returns true if we have to analyze the elements contained in the current trace
	 * @param curTrace
	 * @return
	 */
	private boolean analyzeTrace(int curTrace) {

		return ( interestingTraces == null || interestingTraces.contains(curTrace) );
		
	}
	

}
