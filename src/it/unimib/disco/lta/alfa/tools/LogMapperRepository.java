package it.unimib.disco.lta.alfa.tools;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;

public class LogMapperRepository implements Serializable {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String prefix;
	private File folder;

	/**
	 * 
	 * @param folder
	 * @param prefix  e.g. checking_
	 */
	public LogMapperRepository (File folder, String prefix) {
		this.folder = folder;
		this.prefix = prefix;
	}

	public LogMapper getLogMapperForComponent(String componentId) throws IOException{
		LogMapper mapper = new LogMapper(new File(folder,prefix+componentId+".trace"), new File(folder,prefix+componentId+".trans"));
		return mapper;
	}
}
