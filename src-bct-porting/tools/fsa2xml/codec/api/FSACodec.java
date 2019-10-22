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
package tools.fsa2xml.codec.api;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import automata.fsa.FiniteStateAutomaton;


/**
 * Interface for reading/writing an fsa object from/to a file
 * 
 * @author herve
 *
 */
public interface FSACodec{
    
	/**
	 * Load an FSA from the file stored in the given path
	 * @param filename path of the file
	 * @return
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
    public FiniteStateAutomaton loadFSA(String filename) throws IOException, ClassNotFoundException;
    
    /**
     * Save the automaton to the file with the given path name
     * 
     * @param fsa	the automaton
     * @param filename path of the file
     * 
     * @throws FileNotFoundException
     * @throws IOException
     */
    public void saveFSA(FiniteStateAutomaton fsa, String filename) throws FileNotFoundException, IOException;
    
    /**
     * Load an FSA from the given file
     * @param file
     * @return
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public FiniteStateAutomaton loadFSA(File file) throws IOException, ClassNotFoundException;
    
    /**
     * Save an FSA to th egiven file
     * @param o
     * @param file
     * @return
     * @throws FileNotFoundException
     * @throws IOException
     */
    public void saveFSA(FiniteStateAutomaton o, File file) throws FileNotFoundException, IOException;
  

}
