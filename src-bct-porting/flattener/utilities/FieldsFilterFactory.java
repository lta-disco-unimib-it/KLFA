package flattener.utilities;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

public class FieldsFilterFactory {
	
	/**
	 * Creates a FieldFilter from file.
	 * 
	 * If file does not exists throws an Exception
	 * @param filePath
	 * @return
	 * @throws IOException 
	 */
	
	public static FieldFilter createFilter(String filePath) throws IOException {
		File file = new File(filePath);
		FieldFilter fieldFilter = null;


		BufferedReader inp = new BufferedReader ( new FileReader( file ) );
		fieldFilter = createFilter( inp );

		//this is an optimization
		if ( fieldFilter.getRulesNumber() == 0)
			return new FieldFilterVoid();

		return fieldFilter;
	
	}
	
	/**
	 * Return a field filter create from Buffer content.
	 * 
	 * @param inp
	 * @return
	 * @throws IOException 
	 */
	private static FieldFilter createFilter(BufferedReader inp) throws IOException {
		FieldFilter fieldsFilter = new FieldFilter();
		String line;
		
		while(  ( line = inp.readLine() ) != null ){
				
				FieldCondition condition = processLine(line);
				
				if ( condition != null)
					fieldsFilter.addCondition(condition);
		}
		
		
		return fieldsFilter;
	}

	private static FieldCondition processLine(String inputLine) {
		String line = inputLine.trim();
		if ( line.startsWith("#") )
			return null;
		
		
		String strings[] = line.split(" |\t");
		
		boolean	acceptRule = true;
		
		if ( strings[0].trim().equals("EXCLUDE") ){
			acceptRule = false;
		} else if ( ! strings[0].equals("INCLUDE") ) {
			
			return null;
		} 
		
		
		
		
		String target = strings[1].trim();
		
		if ( target.equals("MODIFIER")){
			return handleModifierCondition( acceptRule, strings[2] );
		} else if ( target.equals("FIELD") ){
			if ( strings.length > 4 )
				return handleFieldNameCondition( acceptRule, strings[2].trim(), strings[3].trim(), strings[4].trim() );
		}
		
		return null;
	}

	private static FieldCondition handleFieldNameCondition(boolean acceptRule, String packageCond, String classCond, String fieldCond) {
		return new FieldNameCondition(acceptRule,packageCond,classCond,fieldCond);
	}

	private static FieldCondition handleModifierCondition(boolean acceptRule, String string) {
		try {
			Field modifier = Modifier.class.getField(string);
			int modifierCode = modifier.getInt(null);
			FieldCondition condition = new FieldModifiersCondition(acceptRule,modifierCode);
			return condition;
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchFieldException e) {
			
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public static FieldFilter createFilter() {
		return new FieldFilterVoid();
	}

}
