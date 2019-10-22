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
package traceReaders.normalized;

import java.io.File;
import java.util.Vector;

import traceReaders.raw.TraceException;
import dfmaker.core.Superstructure;
import dfmaker.core.Variable;

public class GKTailNormalizedIoTraceDB implements NormalizedIoTrace {
	
	private File daikonTraceFile;
	private File daikonDeclFile;
	private String methodName;
	private int idMethodCall;


	//costruttore invocato da durante l'esecuzione di Daikon
	public GKTailNormalizedIoTraceDB(String methodName, File declsFile, File tracesFile, int idMethodCall) {
		this.daikonDeclFile = declsFile;
		this.daikonTraceFile = tracesFile;
		this.methodName = methodName;
		this.idMethodCall = idMethodCall;
	}

	public void addEntryPoint(String entryPoint, Vector<Variable> normalizedPoint) {
	}

	public void addExitPoint(String exitPoint, Vector<Variable> normalizedPoint) {
	}
	
	public void addObjectPoint(String objectPoint, Vector<Variable> normalizedPoint) {
	}
	
	public void commit() throws TraceException {
	}
	
	public void setEntrySuperStructure( Superstructure entrySuperStructure) {
	}
	
	public void setExitSuperStructure( Superstructure exitSuperStructure) {
	}

	public String getMethodName() {
		return methodName;
	}

	public File getDaikonDeclFile() {
		return daikonDeclFile;
		
	}

	public File getDaikonTraceFile() {
		return daikonTraceFile;
	}
	
	public int getIdMethodCall() {
		return idMethodCall;
	}
}

