package database;

/**
 * Exception Thrown when the connection to the database 
 * is not available or in an inconsistent state
 * 
 * @author Trucco Enrico
 *
 */
public class DBException extends Exception {

	private static final long serialVersionUID = 1L;


	public DBException(String arg0) {
		super(arg0);
	}


	public DBException(String message, Exception e) {
		super(message,e);
	}

}
