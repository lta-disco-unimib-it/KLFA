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
package tools.fsa2xml.codec.impl;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import automata.fsa.FiniteStateAutomaton;
import tools.fsa2xml.codec.api.FSACodec;

public class DLoXml implements FSACodec {

	public static final FSACodec INSTANCE = new DLoXml();

	public FiniteStateAutomaton loadFSA(String filename) throws IOException,
			ClassNotFoundException {
		// TODO Auto-generated method stub
		return null;
	}

	public FiniteStateAutomaton loadFSA(File file) throws IOException,
			ClassNotFoundException {
		// TODO Auto-generated method stub
		return null;
	}

	public void saveFSA(FiniteStateAutomaton fsa, String filename)
			throws FileNotFoundException, IOException {
		// TODO Auto-generated method stub

	}

	public void saveFSA(FiniteStateAutomaton o, File file)
			throws FileNotFoundException, IOException {
		// TODO Auto-generated method stub

	}

}
