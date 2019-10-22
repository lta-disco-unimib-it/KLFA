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

import it.unimib.disco.lta.alfa.dataTransformation.ValueTransformer;


public class ValueTransformerExcConcurrent implements ValueTransformer {

	public String getTransformedValue(String data) {
		if ( data != null ){
			if ( data.contains("Interrupted") || 
					data.contains("Thread") )
				return data;
		}
		return null;
	}

	public void reset() {
		
	}

	public String getTransformedValue(String value, String[] line) {
		// TODO Auto-generated method stub
		return getTransformedValue(value);
	}

	public void back() throws UnsupportedOperationException {
		throw new UnsupportedOperationException();
	}

}
