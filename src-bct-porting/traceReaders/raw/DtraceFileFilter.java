package traceReaders.raw;

import java.io.File;
import java.io.FilenameFilter;

/**
 * 
 * @author Fabrizio Pastore fabrizio.pastore AT gmail.com
 *
 */
public class DtraceFileFilter implements FilenameFilter {

	public boolean accept(File dir, String name) {
		return name.endsWith(".dtrace");
	}

}
