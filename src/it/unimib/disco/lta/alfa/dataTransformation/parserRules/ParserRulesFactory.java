package it.unimib.disco.lta.alfa.dataTransformation.parserRules;

import it.unimib.disco.lta.alfa.csv.CsvParsersFactory;
import it.unimib.disco.lta.alfa.csv.CsvReader;
import it.unimib.disco.lta.alfa.dataTransformation.ValueTransformer;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;


public class ParserRulesFactory {

	/**
	 * This methods creates the parser rules associated to the given value transformers
	 * 
	 * @param parserRuleFile
	 * @param trasformers
	 * @return
	 * @throws IOException
	 * @throws ParserFactoryException 
	 */
	public static List<ParserRule> createRules(int componentColumn,int eventColumn,File parserRuleFile, HashMap<String, ValueTransformer> trasformers) throws IOException, ParserFactoryException {
		return createRules(componentColumn, eventColumn, parserRuleFile, trasformers, false);
	}
	
		public static List<ParserRule> createRules(int componentColumn,int eventColumn,File parserRuleFile, HashMap<String, ValueTransformer> trasformers, boolean ignoreMissingTransformers) throws IOException, ParserFactoryException {
		List<ParserRule> parserRules  = new LinkedList<ParserRule>();
		BufferedReader r = new BufferedReader(new FileReader(parserRuleFile));
		String line=null;
		HashMap<String,ParserRule> ruleMap = new HashMap<String, ParserRule>();
		CsvReader csvReader = CsvParsersFactory.createNewCsvReader();
		
		while ( ( line = r.readLine()) != null){
			String[] cols = csvReader.readLine(line);
			
			
			
			String componentRegExp = cols[0];
			String eventRegExp = cols[1];
			
			String ruleKey = componentRegExp+","+eventRegExp;
			ParserRule rule = ruleMap.get(ruleKey);
			if ( rule == null ){
				rule = new ParserRule(componentColumn,eventColumn,componentRegExp,eventRegExp);
				ruleMap.put(ruleKey,rule);
			}
			
			for ( int i = 2; i < cols.length; ++i){
				String[] els = cols[i].split(":");
				ValueTransformer transformer = trasformers.get(els[1]);
				if ( transformer == null ) {
					//System.out.println("Missing "+els[1]);
					if ( ! ignoreMissingTransformers ){
						throw new ParserFactoryException("No such transformer "+els[1]);
					}
				} else{
					//System.out.println("Found "+els[1]);
					rule.setValueTrasformer(Integer.valueOf(els[0]), transformer);
				}
			}
			parserRules.add(rule);
		}
		r.close();
		return parserRules;
	}
}
