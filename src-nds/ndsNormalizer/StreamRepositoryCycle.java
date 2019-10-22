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
package ndsNormalizer;

import tracePreprocessor.ValueTransformerRelativeToInstantiationAndLimited;



public class StreamRepositoryCycle implements ElementRepository {
	private ValueTransformerRelativeToInstantiationAndLimited keyDispenser;
	
	public StreamRepositoryCycle( int level ){
		 keyDispenser = new ValueTransformerRelativeToInstantiationAndLimited( "STREAM", level );
	}
	public LineData get(String value) {
		String key = keyDispenser.getTransformedValue(value);
		return new HandleData( key );
	}

	public void nextCycle() {
		keyDispenser.newLevel();
	}

}
