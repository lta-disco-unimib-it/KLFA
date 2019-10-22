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
package tools.fsa2xml;

import java.io.File;
import java.io.IOException;

import tools.fsa2xml.codec.utility.EncoderSer2XML;



public class FSA2XML{
    

    public void encode(String filename) throws IOException, ClassNotFoundException{

        File f = new File(filename);
        
        

        if(f.isDirectory()){

            File files[] = f.listFiles(new SerFileFilter());

            for(int i=0; i < files.length; i++){
                
                if(!files[i].isDirectory()){// Process for regular file: for the moment we discard recursive stuffs
                    System.out.println(files[i].getAbsolutePath());
                    EncoderSer2XML.INSTANCE.encodeSer2Xml(files[i].getAbsolutePath());
                }
            }
        }
        else if(filename.endsWith(SerFileFilter.EXTENSION)){
            
            System.out.println(f.getAbsolutePath());
            EncoderSer2XML.INSTANCE.encodeSer2Xml(f.getAbsolutePath());
           
        }
        else{
            System.out.println("Decoder from SER file to XML file failed: not a directory nor a .ser file");
        }
    }


   public static void main(String[] args){
       
       
       if (args.length < 1) {
           System.out.println("Usage: java -classpath <bct jar file> tools.fsa2xml.tool.FSA2XML <serFileName> or <directory>");
           System.out.println("Example: java -classpath bct.jar tools.fsa2xml.tool.FSA2XML 1.ser or serFilesDirectory");
           System.exit(0);
       }
       
       

       try{ 
           
           FSA2XML encoder = new FSA2XML();
           encoder.encode(args[0]);
           System.out.println(">> end!");
       }
       catch(IOException ioe){
           ioe.printStackTrace();
       }
       catch(ClassNotFoundException cnfe){
           cnfe.printStackTrace();
       }
       
       
       
   }
    
    
    
}
