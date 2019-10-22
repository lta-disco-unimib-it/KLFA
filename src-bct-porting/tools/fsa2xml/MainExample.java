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
