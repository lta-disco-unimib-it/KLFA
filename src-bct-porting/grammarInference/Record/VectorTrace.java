/*
 * Created on 12-lug-2004
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package grammarInference.Record;

import java.util.Iterator;
import java.util.Vector;

/**
 * @author Leonardo Mariani
 *
 * The class represents a trace 
 */
public class VectorTrace implements Trace {
	private static final String separator = " ";
	private Vector trace = null;
	
	private boolean positiveTrace;

	/**
	 *  Creation of an empty trace
	 */
	public VectorTrace() {
		trace = new Vector();
	}
	
	/* (non-Javadoc)
	 * @see grammarInference.Record.Trace#addSymbol(grammarInference.Record.Symbol)
	 */
	public void addSymbol(Symbol s) {
		trace.add(s);
	}

	/* (non-Javadoc)
	 * @see grammarInference.Record.Trace#getSymbol(int)
	 */
	public Symbol getSymbol(int i) {
		return (Symbol) trace.elementAt(i);
	}

	/* (non-Javadoc)
	 * @see grammarInference.Record.Trace#getLength()
	 */
	public int getLength() {
		return trace.size();
	}

	/* (non-Javadoc)
	 * @see grammarInference.Record.Trace#getSymbolIterator()
	 */
	public Iterator getSymbolIterator(){
		return trace.listIterator();
	}
	
	/* (non-Javadoc)
	 * @see grammarInference.Record.Trace#getSubTrace(int, int)
	 */
	public Trace getSubTrace(int from, int to) {
		Trace ret = new VectorTrace();
		for (int i =from; i<=to;i++) {
			ret.addSymbol(new Symbol(((Symbol)trace.elementAt(i)).getValue()));
		}
		return ret;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		String rtrString = "";
		
		for (int i=0; i<trace.size(); i++) {
			rtrString = rtrString + ((Symbol)trace.elementAt(i)).getValue() + separator;
		}
		
		return rtrString;
	}
	/* (non-Javadoc)
	 * @see grammarInference.Record.Trace#isPositiveTrace()
	 */
	public boolean isPositiveTrace() {
		return positiveTrace;
	}

	/* (non-Javadoc)
	 * @see grammarInference.Record.Trace#setPositiveTrace(boolean)
	 */
	public void setPositiveTrace(boolean positiveTrace) {
		this.positiveTrace = positiveTrace;
	}

}

