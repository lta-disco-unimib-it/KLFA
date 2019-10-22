package grammarInference.Record;

import java.util.HashMap;

/**
 * This class is a factory used to create flyweigth symbols.
 *  
 * @author Fabrizio Pastore
 *
 */
public class SymbolsFactory {
	
	HashMap<String,Symbol> symbols = new HashMap();
	
	/**
	 * Returns a SYmbol instance for teh given value
	 * 
	 * @param symbolValue
	 * @return
	 */
	public Symbol getSymbol( String symbolValue ){
		Symbol symbol = symbols.get(symbolValue);
		if ( symbol == null ){
			symbol = new Symbol(symbolValue);
			symbols.put(symbolValue,symbol);
		}
		
		return symbol;
	}
}
