package dfmaker.core;

/**
 * This class is used to resolve  a variable type given the daikon variable value.
 * 
 * @author Fabrizio Pastore [ fabrizio.pastore at gmail dot com ]
 *
 */
public class VarTypeResolver {
	
	/**
	 * Class used only to contain type, with java 1.5 we can use enum, but we want to maintain compatibility with 
	 * java 1.4
	 * 
	 * @author Fabrizio Pastore [ fabrizio.pastore at gmail dot com ]
	 *
	 */
	public static abstract class Types{
		public static final class StringType extends Types{
			public String toString(){
				return "java.lang.String";
			}
		}
		
		public static final class IntegerType extends Types{
			public String toString(){
				return "int";
			}
		}

		public static final class DoubleType extends Types{
			public String toString(){
				return "double";
			}
		}

		public static final class BooleanType extends Types{
			public String toString(){
				return "boolean";
			}
		}

		public static final class HashcodeType extends Types{
			public String toString(){
				return "hashcode";
			}
		}
		
		public static final StringType stringType = new StringType();
		public static final DoubleType doubleType = new DoubleType();
		public static final IntegerType integerType = new IntegerType();
		public static final BooleanType booleanType = new BooleanType();
		public static final HashcodeType hashcodeType = new HashcodeType();
	}
	
	/**
	 * Returns the type of the passed value.
	 * 
	 * @param variableValue value of the variable we want to know the representation type
	 * 
	 * @return variable type
	 */
	public static Types getType(String variableValue) {
        Types declaredType = null;
        
        // If is an array the starting '[' is removed        
        if (isArray(variableValue)) {
            variableValue = variableValue.substring(1);
        }
        
        if (variableValue.startsWith("\"")) {
            declaredType = Types.stringType;
        }
        else if (variableValue.indexOf(".") != -1) {
            declaredType = Types.doubleType;
        }
        else if (variableValue.equals("true") || variableValue.equals("false")) {
            declaredType = Types.booleanType;
        }
        else if (variableValue.equals("null") ) {
            declaredType = Types.hashcodeType;
        }
        else if (variableValue.equals("!NULL") ) {
            declaredType = Types.hashcodeType;
        }
        else if (variableValue.startsWith("0x") ) {
            declaredType = Types.hashcodeType;
        }
        else {
            declaredType = Types.integerType;
        }
        
        return declaredType;
		
	}

	
	/**
	 * Check if passed value is one of an array
     * @param variableValue
     * @return
     */
    public static boolean isArray(String variableValue) {        
        if (variableValue.startsWith("[") && variableValue.endsWith("]")) {
            return true;
        } else {
            return false;
        }
    }

}
