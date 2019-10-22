package jvmMon.preprocessorRules;

import it.unimib.disco.lta.alfa.dataTransformation.GenericRule;
import it.unimib.disco.lta.alfa.dataTransformation.LineIterator;
import it.unimib.disco.lta.alfa.dataTransformation.PreproessorRule;


public class VMRule extends GenericRule implements PreproessorRule {

	public VMRule() {
		super("VM");
	}

	@Override
	public String process(String line, LineIterator dispenser) {
		String[] words = line.split("\t");
		StringBuffer buffer = new StringBuffer();
		if ( words[2].equals("START")){
			buffer.append("VS");
		} else if ( words[2].equals("INIT")){
			buffer.append("VI");
		}else{
			buffer.append(words[0]);
			buffer.append(".");
			buffer.append(words[2]);
		}
		return buffer.toString();
	}

}
