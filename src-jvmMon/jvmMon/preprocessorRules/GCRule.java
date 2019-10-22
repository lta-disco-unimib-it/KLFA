package jvmMon.preprocessorRules;

import it.unimib.disco.lta.alfa.dataTransformation.GenericRule;
import it.unimib.disco.lta.alfa.dataTransformation.LineIterator;
import it.unimib.disco.lta.alfa.dataTransformation.PreproessorRule;


public class GCRule extends GenericRule implements PreproessorRule {

	public GCRule() {
		super("GC");
		// TODO Auto-generated constructor stub
	}

	@Override
	public String process(String line, LineIterator dispenser) {
		String[] words = line.split("\t");
		StringBuffer buffer = new StringBuffer();
		
		//buffer.append(words[0]);
		if (!words[2].equals("GC FINISH") ){
			buffer.append(".");
			buffer.append(words[2]);
		} else {
			buffer.append("GF");
		}
//		buffer.append("_");
//		buffer.append(words[3]);
		return buffer.toString();
	}

}
