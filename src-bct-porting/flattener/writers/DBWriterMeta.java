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
package flattener.writers;

import java.io.IOException;

import database.DataLayerException;
import database.MethodCallMetadata;

public class DBWriterMeta extends DBWriter {
	private String metaInfo;
	
	public DBWriterMeta(String methodSignature, String dbTable,String metaInfo) {
		super(methodSignature, dbTable);
		this.metaInfo = metaInfo;
	}
	
	public void close() throws IOException{
		super.close();
		if(dbTable == "Registration") {
			try {
				MethodCallMetadata.insert(methodSignature, metaInfo);
			} catch (DataLayerException e) {
				e.printStackTrace();
			}
		}
	}
	
}
