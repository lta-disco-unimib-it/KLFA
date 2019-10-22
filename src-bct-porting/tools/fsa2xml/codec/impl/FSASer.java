package tools.fsa2xml.codec.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import tools.fsa2xml.codec.api.FSACodec;
import tools.fsa2xml.codec.utility.FSAOptimization;



import automata.Transition;
import automata.fsa.FSATransition;
import automata.fsa.FiniteStateAutomaton;

public class FSASer implements FSACodec{

    
    public  final static FSASer INSTANCE = new FSASer();
    
    
    private FSASer(){
        
    }
    
    /**
     * Loads a FSA object from a Serialized object
     * @param filename
     * @return the FSA
     */
    public FiniteStateAutomaton loadFSA(String filename) throws IOException, ClassNotFoundException{
        FiniteStateAutomaton res = null;  
        ObjectInputStream in = null;
        try {
        	
        
        in = new ObjectInputStream(new FileInputStream(filename));
        res = (FiniteStateAutomaton)in.readObject();
        //FSAOptimization.optimizeFSA(res);
        }finally{
        	if ( in != null ){
        		in.close();
        	}
        }
        return res;
    }
    



	public FiniteStateAutomaton loadFSA(File file) throws IOException, ClassNotFoundException{    
    	return loadFSA(file.getAbsolutePath());
    }
        
        
    /**
     * Saves a FSA object in a Serialized object format
     * @param fsa
     * @param filename
     * @return
     */
    public void saveFSA(FiniteStateAutomaton fsa, File file) throws FileNotFoundException, IOException{
        saveFSA(fsa, file.getAbsolutePath());
    }

    public void saveFSA(FiniteStateAutomaton fsa, String filename) throws FileNotFoundException, IOException{
    	ObjectOutputStream out = null;
    	try {
        out = new ObjectOutputStream(new FileOutputStream(filename));
        out.writeObject(fsa);
    	} finally {
    		if ( out != null ){
    			out.close();
    		}
    	}
        
    }

    
    
    
    
}
