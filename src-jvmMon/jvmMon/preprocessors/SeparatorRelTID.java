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
package jvmMon.preprocessors;
import it.unimib.disco.lta.alfa.dataTransformation.Preprocessor;
import it.unimib.disco.lta.alfa.dataTransformation.ValueTransformer;
import it.unimib.disco.lta.alfa.dataTransformation.ValueTransformerNone;
import it.unimib.disco.lta.alfa.dataTransformation.ValueTransformerRelativeToInstantiation;
import it.unimib.disco.lta.alfa.dataTransformation.ValueTransformerSame;

import java.io.File;
import java.io.IOException;

import jvmMon.preprocessorRules.GCRule;
import jvmMon.preprocessorRules.MemoryRule;
import jvmMon.preprocessorRules.RuntimeRule;
import jvmMon.preprocessorRules.ThreadRule;



public class SeparatorRelTID extends Preprocessor {


	public static Preprocessor getVMNormalizer(){
		Preprocessor normalizer = new Preprocessor();
		normalizer.addRule( new GCRule() );
		normalizer.addRule( new MemoryRule() );
		return normalizer;
	}




	public static Preprocessor getTHNormalizer(){
		Preprocessor normalizer = new Preprocessor();

		ValueTransformer threadKeyDispenser = new ValueTransformerRelativeToInstantiation("T");
		ValueTransformer excKeyDispenser = new ValueTransformerNone( );
		
		ValueTransformer monitorTagKeyDispenser = new ValueTransformerNone();


		normalizer.addRule( new RuntimeRule( threadKeyDispenser, excKeyDispenser ) );
		normalizer.addRule( new ThreadRule( threadKeyDispenser, monitorTagKeyDispenser ) );
		return normalizer;

	}


	
	public static void main(String[] args) {
		if ( args.length != 2 ){
			printUsage();
		}
		File input = new File ( args[0] );
		File VMoutput = new File ( "VM"+args[1] );
		File THoutput = new File ( "TH"+args[1] );
		File VMoutputLog = new File ( "VM"+args[1]+".extlog" );
		File THoutputLog = new File ( "TH"+args[1]+".extlog" );
		Preprocessor vmn = getVMNormalizer();
		try {
			vmn.process(input, VMoutput, VMoutputLog);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		Preprocessor thn = getTHNormalizer();
		try {
			thn.process(input, THoutput, THoutputLog);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
