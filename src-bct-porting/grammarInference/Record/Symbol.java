/*
 * Created on 12-lug-2004
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package grammarInference.Record;

/**
 * 
 * @author Leonardo Mariani
 *
 * This class represents a symbol in a trace
 */
public class Symbol {
	private String value=null;
	
	/**
	 * The constructor creates a new symbol that is represented by a string
	 * 
	 * @param val the string value representing the current symbol
	 */
	public Symbol(String val) {
		value=val;
	}

	/**
	 * returns the symbolic value of the current symbol
	 * 
	 * @return symbolic value of hte current symbol
	 */
	public String getValue(){
		return value;
	}
	
	public boolean equals( Object o ){
		if ( o == this ){
			return true;
		}
		if ( ! (o instanceof Symbol ) ){
			return false;
		}
		return value.equals(((Symbol)o).value);
	}
	
	public String toString(){
		return value;
	}
}
