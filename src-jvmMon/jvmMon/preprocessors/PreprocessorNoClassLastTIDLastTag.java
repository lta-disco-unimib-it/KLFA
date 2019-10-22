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
import it.unimib.disco.lta.alfa.dataTransformation.ValueTransformerRelativeToAccess;
import it.unimib.disco.lta.alfa.dataTransformation.ValueTransformerRelativeToInstantiation;
import jvmMon.dataTransformation.ClassLoaderNoName;
import jvmMon.preprocessorRules.CompilerRule;
import jvmMon.preprocessorRules.GCRule;
import jvmMon.preprocessorRules.MemoryRule;
import jvmMon.preprocessorRules.RuntimeRule;
import jvmMon.preprocessorRules.ThreadRule;
import jvmMon.preprocessorRules.VMRule;


public class PreprocessorNoClassLastTIDLastTag {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		Preprocessor normalizer = new Preprocessor();
		
		ValueTransformer excKeyDispenser = new ValueTransformerNone( );
		ValueTransformer threadKeyDispenser = new ValueTransformerRelativeToAccess("T");
		
		
		ValueTransformer monitorTagKeyDispenser = new ValueTransformerRelativeToAccess("M");
		
		
		normalizer.addRule( new ClassLoaderNoName() );
		
		normalizer.addRule( new RuntimeRule( threadKeyDispenser, excKeyDispenser ) );
		normalizer.addRule( new CompilerRule() );
		normalizer.addRule( new GCRule() );
		normalizer.addRule( new MemoryRule() );
		normalizer.addRule( new ThreadRule( threadKeyDispenser, monitorTagKeyDispenser ) );
		normalizer.addRule( new VMRule() );
		

		normalizer.run(args);


	}

}
