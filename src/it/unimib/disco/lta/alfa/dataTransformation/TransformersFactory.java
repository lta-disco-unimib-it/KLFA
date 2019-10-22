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
package it.unimib.disco.lta.alfa.dataTransformation;

import it.unimib.disco.lta.alfa.csv.CsvParsersFactory;
import it.unimib.disco.lta.alfa.csv.CsvReader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;


public class TransformersFactory {

	public static HashMap<String, ValueTransformer> createTrasformers(File traformerRuleFile) throws IOException {
		HashMap<String, ValueTransformer> res = new HashMap<String, ValueTransformer>();
		CsvReader csvReader = CsvParsersFactory.createNewCsvReader();
		BufferedReader r = new BufferedReader(new FileReader(traformerRuleFile));
		String line=null;
		while ( ( line = r.readLine()) != null){
			String[] cols = csvReader.readLine(line);
			ValueTransformer t=null;
			String type = cols[1];
			if ( type.equals("GO") ){
				t = new ValueTransformerGlobal("_");
			} else if ( type.equals("RA") ){
				t = new ValueTransformerRelativeToAccess("_");	
			} else if ( type.equals("RI") ){
				t = new ValueTransformerRelativeToInstantiation("_");
			} else if ( type.equals("SAME") ){
				t = new ValueTransformerSame();
			} else if ( type.equals("NONE") ){
				t = new ValueTransformerNone();
			} else if ( type.equals("HASH") ){
				t = new ValueTransformerHash();
			} else if ( type.equals("GOHASH") ){
				ArrayList<Integer> consider= new ArrayList<Integer>();
				for ( int i = 2; i < cols.length; ++i ){
					consider.add(Integer.valueOf(cols[i]));
				}
				t = new ValueTransformerGlobalHash("_",consider);
			} else if ( type.equals("GOHASHID") ){
				ArrayList<Integer> consider= new ArrayList<Integer>();
				for ( int i = 2; i < cols.length; ++i ){
					consider.add(Integer.valueOf(cols[i]));
				}
				t = new ValueTransformerGlobalHashIdentity("_",consider);
			} else if ( type.equals("REPLACE") ){
				t = new ValueTransformerReplace(cols[2]);
			} else if ( type.equals("RIGH") ){
				t = new ValueTransformerRelativeToInstantiationGroupHigher("_",Integer.valueOf(cols[2]));
			} else if ( type.equals("RAGH") ){
				t = new ValueTransformerRelativeToAccessGroupHigher("_",Integer.valueOf(cols[2]));
			} else if ( type.equals("RARESET") ){
				t = new ValueTransformerRelativeToAccessReset("_",Integer.valueOf(cols[2]));
			} else {
				System.err.println("ERRR "+type);
			}
			
			res.put(cols[0],t);
		}
		r.close();
		return res;
	}
	
}
