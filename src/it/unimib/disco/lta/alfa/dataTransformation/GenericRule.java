package it.unimib.disco.lta.alfa.dataTransformation;



public abstract class GenericRule implements PreproessorRule {

	private String startWord;

	public GenericRule( String startWord ){
		this.startWord = startWord;
	}
	
	public boolean accept(String line) {
		return line.startsWith(startWord);
	}

	public abstract String process(String line, LineIterator dispenser);

}
