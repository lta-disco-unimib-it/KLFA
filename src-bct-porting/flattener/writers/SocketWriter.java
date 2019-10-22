/*
 * Created on 21-mag-2005
 */
package flattener.writers;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * @author Davide Lorenzoli
 */
public class SocketWriter extends OutputStreamWriter {
	
	/**
	 * @param host
	 * @param port
	 * @throws UnknownHostException
	 * @throws IOException
	 */
	public SocketWriter(String host, int port) throws UnknownHostException, IOException {
		super((new Socket(host, port)).getOutputStream());				
	}
}
