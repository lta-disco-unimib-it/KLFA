package database;

import java.sql.SQLException;

/**
 * Exception Thrown when an error occurs during 
 * data manipulation or extraction
 * 
 * @author Trucco Enrico
 *
 */
public class DataLayerException extends Exception {

	private static final long serialVersionUID = -1930025238946607542L;

	public DataLayerException(String arg0) {
		super(arg0);
	}

	public DataLayerException(String message, Throwable e) {
		super(message,e);
	}

}
