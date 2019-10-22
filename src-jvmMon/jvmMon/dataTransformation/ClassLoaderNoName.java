package jvmMon.dataTransformation;

import it.unimib.disco.lta.alfa.dataTransformation.LineIterator;
import jvmMon.preprocessorRules.ClassLoaderRule;

public class ClassLoaderNoName extends ClassLoaderRule {

	public String process(String line, LineIterator dispenser) {
		String[] words = line.split("\t");
		StringBuffer buffer = new StringBuffer();
		buffer.append("C");
		if ( ! words[2].equals( "LOAD_HOOK" ) ){
			buffer.append(".");
			buffer.append(words[2]);
		}

		

		return buffer.toString();
	}
	
}
