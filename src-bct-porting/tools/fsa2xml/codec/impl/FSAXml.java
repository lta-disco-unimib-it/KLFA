package tools.fsa2xml.codec.impl;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;

import tools.fsa2xml.codec.api.FSACodec;
import tools.fsa2xml.codec.utility.FSAOptimization;
import automata.fsa.FiniteStateAutomaton;
import file.XMLCodec;

public class FSAXml implements FSACodec{

    
    public  final static FSAXml INSTANCE = new FSAXml();
    
    
    private FSAXml(){
        
    }
    
    
    
        
    /**
     * Saves a FSA object in a XML format
     * @param o
     * @param filename
     * @return
     */
    public void saveFSA(FiniteStateAutomaton o, String filename){
        
        File f = new File(filename);
        XMLCodec codec = new XMLCodec();
        codec.encode(o, f, null);
        
        
    }
    
    /**
     * 
     */
    // This method does not throws any e
    public FiniteStateAutomaton loadFSA(String filename){
        
        File f = new File(filename);
        XMLCodec codec = new XMLCodec();
        
        Serializable s = codec.decode(f, null);
        
        FiniteStateAutomaton fsa = (FiniteStateAutomaton)s;
        
        FSAOptimization.optimizeFSA(fsa);
        
        return fsa;
    }




	public FiniteStateAutomaton loadFSA(File file) throws IOException,
			ClassNotFoundException {
		return loadFSA(file.getAbsoluteFile());
	}




	public void saveFSA(FiniteStateAutomaton o, File file)
			throws FileNotFoundException, IOException {
		saveFSA(o, file.getAbsoluteFile());
	}
        
        
    
    
    
    
}
