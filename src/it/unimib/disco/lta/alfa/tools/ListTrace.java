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
