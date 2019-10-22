package traceReaders.raw;

import java.io.File;
import java.io.FilenameFilter;

public class TraceNameFilter implements FilenameFilter {

	public boolean accept(File dir, String name) {
		if ( name.endsWith(".dtrace" ) )
			return true;
		
		return false;
	}
	
}
