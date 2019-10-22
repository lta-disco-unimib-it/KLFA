package it.unimib.disco.lta.alfa.dataTransformation.parserRules;


import java.util.List;


public class ParserRuleAction extends ParserRule {


	private List<String> actions;

	public ParserRuleAction(List<String> actions) {
		super(0, 1, ".*", ".*");
		this.actions = actions;
	}
	
	public boolean match ( String lineElements[] ){
	
		String token=lineElements[0];
		for ( String action : actions ){
			if ( token.matches(action) ){
				return true;
			}
		}
		return false;
	}
	


}
