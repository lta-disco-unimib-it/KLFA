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
package traceReaders.raw;

import conf.BctSettingsException;
import conf.EnvironmentalSetter;
import conf.InvariantGeneratorSettings;

public class TraceReaderFactory {
	private static TracesReader tr = null;
	
	public static TracesReader getReader() throws BctSettingsException{
		if ( tr != null )
			return tr;
		
		InvariantGeneratorSettings trs = EnvironmentalSetter.getInvariantGeneratorSettings();
		TracesReader tr;
		
		try {
			tr = (TracesReader) trs.getTraceReaderType().newInstance();
			tr.init(trs);
			return tr;
		} catch (InstantiationException e) {
			throw new BctSettingsException(e.getMessage());
		} catch (IllegalAccessException e) {
			throw new BctSettingsException(e.getMessage());
		} catch (BctSettingsException e) {
			tr = null;
			throw e;
		}
		
	}

}
