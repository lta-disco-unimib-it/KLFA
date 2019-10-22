package tools.fsa2xml;

import java.io.FileNotFoundException;
import java.io.IOException;

import tools.fsa2xml.codec.api.FSACodec;
import tools.fsa2xml.codec.factory.FSA2FileFactory;
import tools.fsa2xml.codec.factory.FSA2FileFactoryException;
import util.FileUtil;
import automata.fsa.FiniteStateAutomaton;
import file.ParseException;

public class LazyFSALoader {
	
	public static class LazyFSALoaderException extends Exception {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public LazyFSALoaderException(String message) {
			super(message);
			// TODO Auto-generated constructor stub
		}
		
	}

	public static FiniteStateAutomaton loadFSA( String path ) throws LazyFSALoaderException, FileNotFoundException {
		
		String extension = FileUtil.getFileExtension(path);
		
		FSACodec extCodec = null;
		try {
			extCodec = FSA2FileFactory.getCodecForFileExtension(extension);
			return loadFSA(extCodec,path);
		} catch (Exception e1) {
			e1.printStackTrace();
		} 
		
		
		//try to load with another codec
		for ( FSACodec codec : FSA2FileFactory.getAllCodecs() ){
			
			if ( codec.equals(extCodec) ){
				continue;
			}
			
			try {

				return loadFSA(codec,path);
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch ( FileNotFoundException e) {
				// TODO Auto-generated catch block
				throw e;

			} catch ( ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		throw new LazyFSALoaderException("No codec available to load fsa "+path);
		
	}
	
	private static FiniteStateAutomaton loadFSA( FSACodec codec, String path ) throws IOException, ClassNotFoundException{
		FiniteStateAutomaton fsa = codec.loadFSA(path);
		
		
		
		return fsa;
	}

	
}
