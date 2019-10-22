package it.unimib.disco.lta.alfa.testUtils;

import java.io.File;

import util.FileUtil;


public class ArtifactsProvider {
	
	

	public static File getFile(String string) {
		return new File("tests/"+string);
	}

	public static File getNonExistentFile(String string) {
		File f = getFile(string);
		
		checkNonExistent(f);
		
		
		return f;
	}

	private static void checkNonExistent(File f) {
		if ( f.exists() ){
			if ( f.isDirectory() ){
				FileUtil.deleteRecursively(f);
			} else {
				f.delete();
			}
		}
	}

	public static File getBugFile(String string) {
		return new File("tests/bugs/artifacts/"+string);
	}
	
	public static File getUnitTestFile(String string) {
		return new File("tests/unit/main/artifacts/"+string);
	}
	
	public static File getSystemTestFile(String string) {
		return new File("tests/system/"+string);
	}
	
	public static File getNonexistentUnitTestFile(String string) {
		File file = getUnitTestFile(string);
		checkNonExistent(file);
		return file;
	}
	
}
