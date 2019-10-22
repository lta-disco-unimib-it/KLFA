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
