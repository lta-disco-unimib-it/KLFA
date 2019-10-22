package grammarInference.Record;

import java.util.Iterator;

public interface Trace {

	/**
	 * addition of a symbol to the end position of the trace
	 * 
	 * @param s the symbol that must be added
	 */
	public abstract void addSymbol(Symbol s);

	/**
	 * Return the symbol stored at a given position
	 * 
	 * @param i the position storing the symbol
	 * @return the value of the symbol
	 */
	public abstract Symbol getSymbol(int i);

	/**
	 * Return the length of the trace
	 * @return length of the trace
	 */
	public abstract int getLength();

	/**
	 * Return an iterator over all symbols stored in the trace
	 * @return iterator of symbols in the trace
	 */
	public abstract Iterator getSymbolIterator();

	/**
	 * Return a subtrace of the current trace
	 * 
	 * @param from first symbol of the substrace (included)
	 * @param to last symbol of the subtrace (included)
	 * @return the subtrace
	 */
	public abstract Trace getSubTrace(int from, int to);

	/**
	 * @return
	 */
	public abstract boolean isPositiveTrace();

	/**
	 * @param positiveTrace
	 */
	public abstract void setPositiveTrace(boolean positiveTrace);

}