package jvmMon.preprocessorRules;

import it.unimib.disco.lta.alfa.dataTransformation.GenericRule;
import it.unimib.disco.lta.alfa.dataTransformation.LineIterator;
import it.unimib.disco.lta.alfa.dataTransformation.PreproessorRule;


public class CompilerRule extends GenericRule implements PreproessorRule {

	public CompilerRule() {
		super("COMPILER");
		// TODO Auto-generated constructor stub
	}

	@Override
	public String process(String line, LineIterator dispenser) {
		//System.out.println(line);
		String[] words = line.split("\t");
		StringBuffer buffer = new StringBuffer();
		buffer.append("c");
		//buffer.append(".");
		//buffer.append(words[2]);
		
		//
		//Classi caricate
		//
//		buffer.append("_");
//		buffer.append(words[5].split("/")[0]);
//		buffer.append(".");
//		buffer.append(words[5].split("/")[1]);
		
		
//		buffer.append("_");
//		buffer.append(words[4]);
//		buffer.append("_");
//		buffer.append(words[5]);
//		buffer.append("_");
//		buffer.append(words[6]);
		return buffer.toString();
	}

}
