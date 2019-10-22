package sjm.parse.tokens;

import sjm.parse.Assembly;
import sjm.parse.Parser;
import sjm.parse.ParserTester;
public class TokenTester extends ParserTester {
/**
 * 
 */
public TokenTester(Parser p) {
	super(p);
}
/**
 * assembly method comment.
 */
protected Assembly assembly(String s) {
	return new TokenAssembly(s);
}
}
