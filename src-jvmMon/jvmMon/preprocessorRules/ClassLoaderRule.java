package jvmMon.preprocessorRules;

import it.unimib.disco.lta.alfa.dataTransformation.GenericRule;
import it.unimib.disco.lta.alfa.dataTransformation.LineIterator;

public class ClassLoaderRule extends GenericRule {

	public ClassLoaderRule( ){
		super( "CLASSLOADER" );
	}
	
	public String process(String line, LineIterator dispenser) {
		String[] words = line.split("\t");
		StringBuffer buffer = new StringBuffer();
		buffer.append("CL");
		
		//Action (LOAD_HOOK)
		if ( words[2] != "LOAD_HOOK" ){
			buffer.append(".");
			buffer.append(words[2]);
		}
		
		//Print class name
		buffer.append("_");
		buffer.append(words[3]);
//		
//		CLASSES PATHS
//		
//		String[] objects = words[3].split("/");
//		
//		buffer.append(objects[0]);
//		if ( objects.length > 1 ){
//			buffer.append(".");
//			buffer.append(objects[1]);
//		}
		

		return buffer.toString();
	}

}
