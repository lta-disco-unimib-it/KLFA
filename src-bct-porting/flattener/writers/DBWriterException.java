package flattener.writers;

import java.io.IOException;

public class DBWriterException extends IOException {
	
	public DBWriterException( String msg ){
		super(msg);
	}
}