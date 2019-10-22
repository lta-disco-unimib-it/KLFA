package util.componentsDeclaration;



import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;


public class ComponentDefinitionExporter {
	
	public static void export(Component component, File file) throws IOException{
		BufferedWriter w = new BufferedWriter( new FileWriter(file));
		w.write("COMPONENT\t"+component.getName()+"\n");
		for ( MatchingRule rule : component.getRules() ){
			String inclusion;
			if ( rule instanceof MatchingRuleInclude ){
				inclusion = "INCLUDE";
			} else {
				inclusion = "EXCLUDE";
			}
			w.write(inclusion);
			w.write("\t");
			w.write(rule.getPackageExpr());
			w.write("\t");
			w.write(rule.getClassExpr());
			w.write("\t");
			w.write(rule.getMethodExpr());
			w.write("\n");
		}
		w.close();
	}
}
