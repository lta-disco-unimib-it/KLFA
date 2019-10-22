package database;

/**
 * Exception Thrown when the data required is not in DataBase
 * 
 * @author Trucco Enrico
 *
 */
public class DataNotFoundException extends Exception {

	private static final long serialVersionUID = -7582294519088909479L;

	public DataNotFoundException(String arg0) {
		super(arg0);
	}
}
