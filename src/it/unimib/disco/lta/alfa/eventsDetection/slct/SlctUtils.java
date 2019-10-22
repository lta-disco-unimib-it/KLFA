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
package it.unimib.disco.lta.alfa.eventsDetection.slct;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Pattern;

public class SlctUtils {

	public static Pattern getRulePattern(String line){
		
			String regex = getRuleRegex(line);
			return Pattern.compile(regex);
			
	}
	
	/**
	 * Returns a list of patterns retrieved from the slct derived rules.
	 * If requested rules are sorted in reverse order
	 * 
	 * @param r
	 * @param reverseSort
	 * @return
	 * @throws IOException
	 */
	public static List<Pattern> getRules ( BufferedReader r, boolean reverseSort ) throws IOException{
		String line;
		LinkedList<Pattern> rules= new LinkedList<Pattern>();
		ArrayList<String> ruleList = new ArrayList<String>();
		
		while( ( line = r.readLine()) != null ){
			line = line.trim();
			if ( line.length() > 0 ){
				if ( line.startsWith("Support:") || line.equals("|") ){
					continue;
				}
				ruleList.add(getRuleRegex(line));
				
				
			}
		}
		
		//sort in reverse order if necessary
		if ( reverseSort ){
			Collections.sort(ruleList,Collections.reverseOrder());
		}
		
		//create patterns
		for ( String regex : ruleList ){
			rules.add(Pattern.compile(regex));
		}
		
		return rules;
	}
	
	private static String getRuleRegex(String line) {
		String regex = "^"+line
		.replaceAll("\\S+\\*", "*") //with -j option slct splits words, we do not like it
		.replaceAll("\\*\\S+", "*") //with -j option slct splits words, we do not like it
		.replace("+", "\\+")
		.replace("-", "\\-")
		.replace("{", "\\{")
		.replace("}", "\\}")
		.replace("[", "\\[")
		.replace("[", "\\]")
		.replace("(", "\\(")
		.replace(")", "\\)")
		.replace("*", "(.*)")
		.replaceAll("(\\(\\.\\*\\))+", "(.*)"); //avoid results like "actionX (.*)(.*)(.*)"

//		if ( ! regex.endsWith("(.*)") ) {
//			regex = regex+"(.*)";
//		}
		
		return regex;
	}


	public static List<Pattern> getRules ( Reader r, boolean sort ) throws IOException{
		return getRules( new BufferedReader(r), sort );
	}
}
