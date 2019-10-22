package it.unimib.disco.lta.alfa.dataTransformation;
import java.io.BufferedReader;




public interface PreproessorRule {

	public boolean accept( String line );
	
	public String process( String line, LineIterator dispenser );
	
}
