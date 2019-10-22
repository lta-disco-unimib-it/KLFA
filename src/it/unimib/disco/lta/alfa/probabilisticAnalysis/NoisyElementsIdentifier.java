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
package it.unimib.disco.lta.alfa.probabilisticAnalysis;

import it.unimib.disco.lta.alfa.probabilisticAnalysis.SequencesStatsCalculator.SequencesStatsCalculatorResults;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;


public class NoisyElementsIdentifier {
	int sequenceSize = 10;
	private SequencesStatsCalculator ssc;
	
	
	public NoisyElementsIdentifier( int sequenceSize, List<Integer> elementsToConsider){
		ssc = new SequencesStatsCalculator( sequenceSize, elementsToConsider );
	}
	
	
	public static void main (String args[]){
		
	}
	
	public void process(File file) throws IOException{
//		SequencesStatsCalculator singleSymbolsResult = new SequencesStatsCalculator( 1, ssc.getElementsToConsider() );
//		singleSymbolsResult.getStringSequencesMap(statPoint, sequenceMinSize)
//		SequencesStatsCalculatorResults results = ssc.process(file);
//		
//		results.getSymbols();
	}
}
