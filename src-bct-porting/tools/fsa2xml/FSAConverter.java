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
import java.io.FileFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import automata.fsa.FiniteStateAutomaton;

import tools.fsa2xml.LazyFSALoader.LazyFSALoaderException;
import tools.fsa2xml.codec.api.FSACodec;
import tools.fsa2xml.codec.factory.FSA2FileFactory;
import tools.fsa2xml.codec.factory.FSA2FileFactoryException;
import tools.fsa2xml.codec.utility.EncoderSer2XML;
import util.FileUtil;



public class FSAConverter{
    

    public void encode(String format, File f) throws IOException, ClassNotFoundException, FSA2FileFactoryException{

        
        
        
    	List<File> filesList = new ArrayList<File>();
    	
        if(f.isDirectory()){

            File files[] = f.listFiles(new FileFilter(){
            	private Set<String> extensions = FSA2FileFactory.getAllCodecsExtensions();
            	
				public boolean accept(File pathname) {
					String ext = FileUtil.getFileExtension(pathname);
					return extensions.contains(ext);	
				}
            	
            });

            for(int i=0; i < files.length; i++){
                
                if(!files[i].isDirectory()){// Process for regular file: for the moment we discard recursive stuffs
                    filesList.add(files[i]);
                }
            }
        } else {
        	filesList.add(f);
        }
        
        FSACodec codec = FSA2FileFactory.getCodecForFileExtension(format);
        for ( File file : filesList ){
        	FiniteStateAutomaton fsa;
			try {
				fsa = LazyFSALoader.loadFSA(file.getAbsolutePath());
				String ext = FileUtil.getFileExtension(file);
	        	String name = file.getName();
	        	String newName = name.substring(0,name.length()-ext.length())+format;
	        	codec.saveFSA(fsa, new File(file.getParent(),newName) );
	        	
			} catch (LazyFSALoaderException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        	
        }
        
    }


   public static void main(String[] args) throws FSA2FileFactoryException{
       
       
       if (args.length < 2) {
           System.out.println("Usage: java -classpath <bct jar file> "+FSAConverter.class.getCanonicalName()+"<format> <serFileName> or <directory>");
           System.out.println("Example: java -classpath bct.jar "+FSAConverter.class.getCanonicalName()+"fsa 1.ser or serFilesDirectory");
           System.exit(0);
       }
       
       

       try{ 
    	   String format = args[0];
    	   File orig = new File(args[1]);
           FSAConverter encoder = new FSAConverter();
           encoder.encode(format,orig);
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
