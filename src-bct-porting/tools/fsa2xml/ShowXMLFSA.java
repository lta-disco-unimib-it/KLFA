package tools.fsa2xml;

import gui.environment.FrameFactory;

import java.io.File;
import java.io.IOException;

import tools.fsa2xml.codec.api.FSACodec;
import tools.fsa2xml.codec.factory.FSA2FileFactory;

import automata.fsa.FiniteStateAutomaton;

public class ShowXMLFSA{
    
    

    public void show(String filename) throws IOException, ClassNotFoundException{

        File f = new File(filename);
        
        FSACodec codec = FSA2FileFactory.getFSAXml();
        FiniteStateAutomaton fsa = null;
        

        if(f.isDirectory()){

            File files[] = f.listFiles(new XmlFileFilter());
            

            for(int i=0; i < files.length; i++){
                
                if(!files[i].isDirectory()){// Process for regular file: for the moment we discard recursive stuffs
                    System.out.println(files[i].getAbsolutePath());
                
                    fsa = codec.loadFSA(files[i].getAbsolutePath());
                    FrameFactory.createFrame(fsa);
                }
            }
        }
        else if(filename.endsWith(XmlFileFilter.EXTENSION)){
            
            System.out.println(f.getAbsolutePath());
            
            fsa = codec.loadFSA(f.getAbsolutePath());
            FrameFactory.createFrame(fsa);
           
        }
        else{
            System.out.println("FSAShower for XML file failed: not a directory nor a .xml file");
        }
    }


   public static void main(String[] args){
       
       
       if (args.length < 1) {
           System.out.println("Usage: java -classpath <bct jar file> <fsa2xml jar file> main.java.fsa.tools.ShowXMLFSA <serFileName> or <directory>");
           System.out.println("Example: java -classpath bct.jar:fsa2xml main.java.fsa.tools.ShowXMLFSA 1.ser or serFilesDirectory");
           System.exit(0);
       }
       
       
       

       try{ 
           
           ShowXMLFSA shower = new ShowXMLFSA();
           shower.show(args[0]);
           
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
