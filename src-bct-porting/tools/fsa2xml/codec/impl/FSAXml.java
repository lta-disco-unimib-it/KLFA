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
