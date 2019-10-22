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
package tools.fsa2xml.codec.utility;

import java.io.IOException;

import tools.fsa2xml.SerFileFilter;
import tools.fsa2xml.XmlFileFilter;
import tools.fsa2xml.codec.factory.FSA2FileFactory;


import automata.fsa.FiniteStateAutomaton;



/**
 * Utility class which directly provides functions to encode from ser to xml representation
 * by using the codec classes 
 * @author herve
 *
 */
public class EncoderSer2XML{
    
    
    
    public final static EncoderSer2XML INSTANCE = new EncoderSer2XML();
    
    
    
    
    private EncoderSer2XML(){
        
    }
    
    
    /**
     * Encodes a source ser file from a destination xml file 
     * 
     * @param source
     * @param destination
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public void encodeSer2Xml(String source, String destination) throws IOException, ClassNotFoundException{
        
        FiniteStateAutomaton fsa = FSA2FileFactory.getFSASer().loadFSA(source);
        
        FSA2FileFactory.getFSAXml().saveFSA(fsa, destination);

    }
    
    /**
     * Encodes a ser file to the same destination
     * @param source
     * @throws ClassNotFoundException
     * @throws IOException
     */
    public void encodeSer2Xml(String source) throws ClassNotFoundException, IOException{
        
        String destination = source.replaceFirst(SerFileFilter.EXTENSION, XmlFileFilter.EXTENSION); 
        
        encodeSer2Xml(source, destination);
        
    }
    

}
