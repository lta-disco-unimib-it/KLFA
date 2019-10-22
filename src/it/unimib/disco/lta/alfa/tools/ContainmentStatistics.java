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
package it.unimib.disco.lta.alfa.tools;

import it.unimib.disco.lta.alfa.tools.FSAInspector.TransitionPath;

public class ContainmentStatistics {

	private TransitionPath tp;
	private int missing;
	private int out;
	private int archs;

	public ContainmentStatistics(TransitionPath tp, int missing, int out,
			int archs) {
		this.tp = tp;
		this.missing = missing;
		this.out = out;
		this.archs = archs;
	}

	public TransitionPath getTp() {
		return tp;
	}

	public void setTp(TransitionPath tp) {
		this.tp = tp;
	}

	public int getMissing() {
		return missing;
	}

	public void setMissing(int missing) {
		this.missing = missing;
	}

	public int getOut() {
		return out;
	}

	public void setOut(int out) {
		this.out = out;
	}

	public int getArchs() {
		return archs;
	}

	public void setArchs(int archs) {
		this.archs = archs;
	}

}
