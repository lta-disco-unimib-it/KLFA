package util;

public class ClassNameAndDefinitionFormatter {

  private static String processArrayName(Class arrayArgument) {
    String argumentName = arrayArgument.getName();
    /*
    String argumentType = arrayArgument.getComponentType().getName();
    while ( argumentName.charAt(0) == '[' ) {
      argumentName = argumentName.substring(1, argumentName.length());
      argumentType = argumentType + "[]";
    }
    return argumentType;
    */
    
    //System.out.println("argName : "+argumentName);
    int max = argumentName.length();
    String post="";
    int i;
    for ( i = 0; i < max && ( argumentName.charAt(i) == '[' ); i++ )
    	post+="[]";
    //This is an hack
    //TODO: correct it
    String type;
    if ( i < max-1 )
    	//questo permette di gestire le stringhe
    	type = argumentName.substring(++i,max-1);
	else
		try {
			type = translate(argumentName.charAt(i));
		} catch (ClassAndDefinitionFormatterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			type = ""+argumentName.charAt(i);
		}
    	
    type += post;
    
    return type;
    
  }
  
  public static String translate(char c) throws ClassAndDefinitionFormatterException {
	switch ( c ){
		case 'I':
			return "int";
		case 'B':
			return "byte";
		case 'D':
			return "double";
		case 'S':
			return "short";
		case 'J':
			return "long";
		case 'F':
			return "float";
		case 'C':
			return "char";
		case 'Z':
			return "boolean";
			
	}
	throw new ClassAndDefinitionFormatterException( "Unknown ByteCode Type" );
  }
/**
   * return a class name that follows the java type definition
   * if the given class is an array, even multidimensional
   */
  public static String getNormalizedClassName(Class c) {
    if ( c.isArray() )
      return processArrayName(c);
    else
      return c.getName();
  }

  private static String estractArguments(Class[] arguments) {
    String args = "";
    for ( int i = 0; i < arguments.length; i++)
      args = args + getNormalizedClassName(arguments[i]) + ",";
    if ( !args.equals("") )
      args = args.substring(0, args.length() - 1);
    return args;
  }

  public static String estractMethodSignature(String targetClass,String methodName, Class returnType , Class[] arguments) {
    String args = estractArguments(arguments);
    return //getNormalizedClassName(returnType) + " " + 
      targetClass+"."+methodName + "(" + args + ")";
  }

  public static String estractConstructorSignature(String constuctorName, Class[] arguments) {
    String args = estractArguments(arguments);
    return constuctorName + ".new(" + args + ")";
  }
}
