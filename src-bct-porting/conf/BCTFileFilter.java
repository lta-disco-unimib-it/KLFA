package conf;

import java.io.File;
import java.io.FileFilter;
import java.io.FilenameFilter;

public class BCTFileFilter implements FilenameFilter {
	private String extension;
	
	public BCTFileFilter( String extension ){
		this.extension = "."+extension;
	}
	
	public boolean accept(File dir, String name) {
		if ( name.endsWith(extension) )
			return true;
		return false;
	}

}
