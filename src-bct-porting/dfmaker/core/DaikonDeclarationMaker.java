package dfmaker.core;

import java.io.IOException;
import java.io.Writer;

import java.util.Iterator;

import dfmaker.core.VarTypeResolver.Types;
/**
 * 
 * @author Fabrizio Pastore [ fabrizio.pastore at gmail dot com ]
 *
 */
public class DaikonDeclarationMaker {    
    
    

    
    public static void writeAll(SuperstructureCollection structuresCollection, Writer writer ) throws IOException {
        Iterator entryIt = structuresCollection.values().iterator();
        
        while ( entryIt.hasNext() ){
        	Superstructure superStructure = (Superstructure) entryIt.next();
        	writeDown ( superStructure, writer );
        }
		
	}

    public static void write( Writer writer, Superstructure entrySuperstructure, Superstructure exitSuperstructure ) throws IOException{
    	writeDown(entrySuperstructure, writer);
    	writeDown(exitSuperstructure, writer);
    }
    
	
    private static void writeDown(Superstructure entryStructure, Writer writer ) throws IOException {
        Iterator entryIterator = entryStructure.varFields().iterator();
        
        writer.write("DECLARE\n");

        writer.write(entryStructure.getProgramPointName()+"\n");
        
        
        while ( entryIterator.hasNext() ){
        	SuperstructureField field = (SuperstructureField) entryIterator.next();
        	String arrayExtension = "";
        	
        	String varName = field.getVarName();
        	Types fieldVarType = field.getVarType();
        	String varType = fieldVarType.toString();
        	if ( field.isArray() ) {
        		varType += "[]";
        		if ( ! varName.endsWith("[]") )
        			varName += "[]";
        	}
        	String objType = null;
        	
        	if ( fieldVarType == Types.hashcodeType )
        		objType = "Object";
        	else
        		objType = varType;
        	
        	writer.write(varName+"\n"+objType+"\n"+varType+"\n1\n");
        }
		
        writer.write("\n");
        		
	}
	
    
}
