package util;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

public class FileIndexAppend extends FileIndex {
	private File indexFile;
	
	public FileIndexAppend(File file) {
		this(file,"");
	}
	/**
	 * @see FileIndex
	 */
	public FileIndexAppend(File file,String suffix) {
		super(file,suffix);
		this.indexFile = file;
	}

	public String add(String name){
		String res = super.add(name);
		try {
			Writer w = new FileWriter(indexFile,true);
			w.append(name+"="+res+"\n");
			w.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return res;
	}

	
}
