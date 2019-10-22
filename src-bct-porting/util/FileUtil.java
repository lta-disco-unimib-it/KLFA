package util;

import java.io.File;

public class FileUtil {
	
	public static String getFileExtension ( File file ){
		return getFileExtension(file.getAbsolutePath());	
	}
	
	public static String getFileExtension ( String path ){
		int dotPosition = path.lastIndexOf('.');
		return path.substring(dotPosition+1, path.length());	
	}
	
	static public boolean deleteDirectory(File path) {
		if( path.exists() ) {
			File[] files = path.listFiles();
			for(int i=0; i<files.length; i++) {
				if(files[i].isDirectory()) {
					if ( ! deleteDirectory(files[i]) )
						System.out.println("cannotDelete "+files[i]);
					else
						System.out.println("deleted "+files[i]);
				}
				else {
					if ( ! files[i].delete() )
						System.out.println("cannotDelete "+files[i]);
					else
						System.out.println("deleted "+files[i]);
				}
			}
		}
		return( path.delete() );
	}
}
