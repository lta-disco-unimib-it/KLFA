/*******************************************************************************
 *    Copyright 2019 Fabrizio Pastore, Leonardo Mariani
 *   
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 *******************************************************************************/
package probes;

import util.ClassAndDefinitionFormatterException;
import util.ClassNameAndDefinitionFormatter;

public class ClassFormatter {
	
	public static String getSignature(String className, String methodName, String methodSig ){
		return (className+"."+methodName+"("+methodSig+")").replace('/','.');
	}
	
	public static String getSignature( String methodS, Object[] argsPassed, String cName, String theMethodName ){
	
		String signature = cName.replace('/','.')+"."+theMethodName+"(";
	
	
		String internal = methodS.replace('/','.').substring(methodS.indexOf('(')+1,methodS.indexOf(')'));
		String[] argumentsColl = internal.split(";");
		boolean first = true;
		
		for ( String argColl : argumentsColl ){
			int size = argColl.length();
			int c = 0;
			String post="";
			
			
			while ( c < size ){
				char type = argColl.charAt(c);
		
				
				
				//handle arrays
				if ( type == '[' ){
					post += "[]";
				
				//handle objects
				} else {  
					
					if ( ! first )
						signature+=",";
					first = false;

					if ( type == 'L' ){
						signature += argColl.substring(c+1);
						signature += post;
						post = "";
						break;
					} else {

						//handle primitives
						try {
							signature += ClassNameAndDefinitionFormatter.translate(type);
							signature += post;
							post = "";
						} catch (ClassAndDefinitionFormatterException e) {
							e.printStackTrace();
						}
					}

					//next char
					++c;
				}
			}
		}
		
			
		
		//System.out.println(signature);
		return signature+")";
	
	}

	public static Class getType(char c) throws ClassAndDefinitionFormatterException{
		switch ( c ){
		case 'I':
			return java.lang.Integer.TYPE;
		case 'B':
			return java.lang.Byte.TYPE;
		case 'D':
			return java.lang.Double.TYPE;
		case 'S':
			return java.lang.Short.TYPE;
		case 'J':
			return java.lang.Long.TYPE;
		case 'F':
			return java.lang.Float.TYPE;
		case 'C':
			return java.lang.Character.TYPE;
		case 'Z':
			return java.lang.Boolean.TYPE;

		}
		throw new ClassAndDefinitionFormatterException( "Unknown ByteCode Type" );
	}

	
	
	/**
	public static Class[] getTypes(String methodS, Object[] argsPassed) {
		Class[] types = new Class[argsPassed.length];
		
		if ( argsPassed.length > 0 ){
			String intSign = methodS.replace('/', '.').substring(methodS.indexOf('(')+1,methodS.length()-1);
			String[] nametypes = intSign.split(";");
		
			boolean first = true;
			int i=0;
			for ( String type : nametypes ){
				if ( type.startsWith("L") )
					types[i] = argsPassed[i].getClass();
				else {
					try {
						Class curClass = ClassNameAndDefinitionFormatter.getType(type.charAt(0));
						types[i] = curClass;
					} catch (ClassAndDefinitionFormatterException e) {
						// TODO Auto-generated catch block
						types[i] = argsPassed[i].getClass();
					}
				
				}
				++i;
			}
			
		}
		

	}*/
}
