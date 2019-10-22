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
package tools.fsa2xml.codec.factory;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Set;



import tools.fsa2xml.codec.api.FSACodec;
import tools.fsa2xml.codec.impl.DLoXml;
import tools.fsa2xml.codec.impl.FSABctXml;
import tools.fsa2xml.codec.impl.FSASer;
import tools.fsa2xml.codec.impl.FSAXml;




/**
 * 
 * This factory allows one to get a codec for reading/writing FSA in a serialiazed ("ser")
 * or xml-based ("xml") file
 * 
 * 
 * @author herve
 *
 */
public class FSA2FileFactory{
    private static HashMap<String,FSACodec> codecs;
    
	static {
		codecs= new HashMap<String, FSACodec>();
		codecs.put("ser",FSASer.INSTANCE);
		codecs.put("fsa",FSABctXml.INSTANCE);
		codecs.put("jff",FSAXml.INSTANCE);
		codecs.put("dlo",DLoXml.INSTANCE);
	}
	
    public static Collection<FSACodec> getAllCodecs(){
    	return codecs.values();
    }
    
    public static Set<String> getAllCodecsExtensions(){
    	return codecs.keySet();
    }
    
    /**
     * Return the codec that corresponds to the provided file extension.
     * Or null if no corresponding codec is found. 
     * @param fileExtension
     * @return
     * @throws FSA2FileFactoryException 
     */
    public static FSACodec getCodecForFileExtension( String fileExtension ) throws FSA2FileFactoryException{
    	
    	FSACodec codec = codecs.get(fileExtension);
    	if ( codec != null ){
    		return codec;
    	}
		throw new FSA2FileFactoryException("Unknown file format :"+fileExtension);
    	
    }
    
    /**
     * Codec for XML-based files
     * @return 
     */
    public static tools.fsa2xml.codec.api.FSACodec getFSAXml(){
        
        return FSAXml.INSTANCE;
    }
    
    /**
     * Codec for DLo XML-based files
     * @return 
     */
    public static tools.fsa2xml.codec.api.FSACodec getDLoXml(){
        return DLoXml.INSTANCE;
    }
    
    /**
     * Codec for BCT XML-based files
     * @return 
     */
    public static tools.fsa2xml.codec.api.FSACodec getFSABctXml(){
        
        return FSABctXml.INSTANCE;
    }
    
    /**
     * Codec for Serialized files
     * @return
     */
    public static FSACodec getFSASer(){
        
        return FSASer.INSTANCE;
        
    }
    

}
