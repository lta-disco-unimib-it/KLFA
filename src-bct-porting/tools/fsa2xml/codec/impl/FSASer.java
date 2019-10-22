/*******************************************************************************
 *    Copyright 2019 Fabrizio Pastore, Leonardo Mariani
 *   
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 *******************************************************************************/
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
