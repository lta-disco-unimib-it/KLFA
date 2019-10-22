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

import java.util.Iterator;
import java.util.List;

import grammarInference.Record.Symbol;
import grammarInference.Record.Trace;
import grammarInference.Record.VectorTrace;

public class ListTrace extends VectorTrace {


		private List<String> ssequence;

		public ListTrace(List<String> ssequence) {
			this.ssequence = ssequence;
		}

		@Override
		public int getLength() {
			// TODO Auto-generated method stub
			return ssequence.size();
		}

		@Override
		public Trace getSubTrace(int from, int to) {
			// TODO Auto-generated method stub
			return new ListTrace(ssequence.subList(from, to));
		}

		@Override
		public String getSymbol(int i) {
			// TODO Auto-generated method stub
			return ssequence.get(i);
		}

		@Override
		public Iterator<String> getSymbolIterator() {
			return ssequence.iterator();
		}
		

	
}
