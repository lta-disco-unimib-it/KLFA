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
package it.unimib.disco.lta.alfa.frequentItemsetsAnalysis;

import java.util.HashMap;
import java.util.HashSet;

public class SequencePatternMatchingResult {

	private int windowSize;
	private int startingLine;
	private int overlappedSequences;
	private int overlappingSequences;
	private HashMap<String,Integer> notMatchedSequences = new HashMap<String,Integer>();
	
	public SequencePatternMatchingResult( int startingLine, int windowSize ){
		this.startingLine = startingLine;
		this.windowSize = windowSize;
	}

	public int getWindowSize() {
		return windowSize;
	}

	public void setWindowSize(int windowSize) {
		this.windowSize = windowSize;
	}

	public int getStartingLine() {
		return startingLine;
	}

	public void setStartingLine(int startingLine) {
		this.startingLine = startingLine;
	}
	
	public int getEndingLine(){
		return startingLine+windowSize-1;
	}

	/**
	 * Number of sequences that this sequence is overlapping.
	 * I.e. sequences that starts after this sequence started, but before this sequence end
	 * @return
	 */
	public int getOverlappedSequences() {
		return overlappedSequences;
	}
	
	public void setOverlappedSequences(int value) {
		overlappedSequences = value;
	}

	/**
	 * Set the number of sequences thi selement is overlapped by.
	 *I.e. tghe number of sequences starrting before this sequence start but ending before this sequence end 
	 * @param value
	 */
	public void setOverlappingSequences(int value) {
		overlappingSequences = value;
	}

	public void addNotMatchedSequence(String seqString) {
		Integer matched = notMatchedSequences.get(seqString);
		if ( matched == null ){
			notMatchedSequences.put(seqString,1);	
		} else {
			notMatchedSequences.put(seqString,matched+1);
		}
	}

	public HashMap<String, Integer> getNotMatchedSequences() {
		return notMatchedSequences;
	}

	public int getOverlappingSequences() {
		return overlappingSequences;
	}
}
