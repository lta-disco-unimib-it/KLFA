package util.componentsDeclaration;



import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * This class reads a component definition file and returns a list of components defined in the file.
 *  
 * @author Fabrizio Pastore fabrizio.pastore AT gmail.com
 *
 */
public class ComponentsDefinitionFactory {

	public static List<Component> getComponents(File file) throws ComponentsDefinitionException {
		InputStream is;
		try {
			is = new FileInputStream ( file );
		
		InputStreamReader reader = new InputStreamReader ( is );

		BufferedReader br = new BufferedReader(reader);
		
		String line;
		Component currentComponent = null;
		String componentName = null;
		
	
		List<Component> components = new ArrayList<Component>();
		
		while ( ( line = br.readLine() ) != null ){
			if ( line.startsWith("COMPONENT")){
				String[] els = line.split("\t");
				componentName = els[1];
				currentComponent = new Component(componentName);
				components.add(currentComponent);
			} else if ( line.startsWith("INCLUDE") || line.startsWith("EXCLUDE")){
				if ( currentComponent == null )
					throw new ComponentsDefinitionException("Missing COMPONENT");
				MatchingRule rule = getRule( line );
				currentComponent.addRule(rule);
			} else if ( line.startsWith("#")) {
				//is a comment
			} else {
				throw new ComponentsDefinitionException("Wrong definition "+line);
			}
		}
		
		
		br.close();
		
		
		return components;
		} catch (FileNotFoundException e) {
			throw new ComponentsDefinitionException(e.getMessage());
		} catch (IOException e) {
			throw new ComponentsDefinitionException(e.getMessage());
		}
	}

	

	private static MatchingRule getRule(String line) throws ComponentsDefinitionException {
		String[] els = line.split("\t");
		if ( els.length != 4 )
			throw new ComponentsDefinitionException("Wrong definition "+line);
		if ( els[0].equals("INCLUDE") ){
			return new MatchingRuleInclude(els[1],els[2],els[3]);
		} else if ( els[0].equals("EXCLUDE") ) {
			return new MatchingRuleExclude(els[1],els[2],els[3]);
		} else {
			throw new ComponentsDefinitionException("Wrong definition "+line);
		}
		
	}

	
	
}
