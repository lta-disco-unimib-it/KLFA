package tools.fsa2xml;


import tools.fsa2xml.codec.factory.FSA2FileFactory;
import automata.fsa.FiniteStateAutomaton;

public class MainExample{
    
    
    
    
    public static void main(String[] args){
        
        MainExample main = new MainExample();
        
        main.readFSA();
        main.saveFSA2XML();
        
        
    }
    
    
    
    private FiniteStateAutomaton readFSA(){
        
        String serFile = "src/main/resources/fsa/2.ser";
        
        try{
            System.out.println(">> Loading ser file : "+serFile);
            FiniteStateAutomaton fsa = FSA2FileFactory.getFSASer().loadFSA(serFile);
            System.out.println(">> Printing FSA ");
            System.out.println(fsa.toString());
            return fsa;
        }
        catch(Exception e){
            e.printStackTrace();
        }
        
        return null;
        
    }
    
    
    private void saveFSA2XML(){
        
        String xmlFile = "src/main/resources/fsa/2.xml";
        
        try{
            
            
            FiniteStateAutomaton fsa = readFSA();
            System.out.println(">> Saving FSA in xml : "+xmlFile);
            FSA2FileFactory.getFSAXml().saveFSA(fsa, xmlFile);
            System.out.println(">> Saving Done!");

            
        }
        catch(Exception e){
            e.printStackTrace();
        }
        
    }
    

}
