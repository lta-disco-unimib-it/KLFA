package jvmMon.preprocessorRules;

import it.unimib.disco.lta.alfa.dataTransformation.GenericRule;
import it.unimib.disco.lta.alfa.dataTransformation.LineIterator;
import it.unimib.disco.lta.alfa.dataTransformation.PreproessorRule;


public class MemoryRule extends GenericRule implements PreproessorRule {

	public MemoryRule() {
		super("MEMORY");
		
	}

	@Override
	public String process(String line, LineIterator dispenser) {
		String[] words = line.split("\t");
		StringBuffer buffer = new StringBuffer();
		//buffer.append("MM");
		//buffer.append(".");
		//buffer.append(words[2]);
		if (!words[2].equals("GC START") ){
			buffer.append("MM");
			buffer.append(".");
			buffer.append(words[2]);
		} else {
			buffer.append("GS");
		}
//		buffer.append("_");
//		buffer.append(words[3]);
		return buffer.toString();
	}

}
