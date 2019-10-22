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
