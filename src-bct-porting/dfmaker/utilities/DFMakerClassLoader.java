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
package dfmaker.utilities;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * @author Davide Lorenzoli
 */

/**
 * This class is responsible for loading class
 */
public class DFMakerClassLoader extends ClassLoader {
	
	/**
	 * Constructor
	 */
	public DFMakerClassLoader() {
	    super();
	}		
	
    /**
     * @see java.lang.ClassLoader#findClass(java.lang.String)
     */
    protected Class findClass(String className) throws ClassNotFoundException {
        Class currentClass = null;
        
        // search the class wanted class in the loaded classes
        currentClass = super.findLoadedClass(className);               
        
        // search the class wanted class in the system classes
        try {
            currentClass = currentClass == null ? super.findSystemClass(className) : null;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }       
        
        // search the class wanted class in the file system
        if (currentClass == null) {                        
            try {
                FileInputStream fIn = new FileInputStream(className.replace('.', '/') + ".class");
                byte buff[] = new byte[fIn.available()];
                fIn.read(buff);
                currentClass = defineClass(className, buff, 0, buff.length);                
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } 
        }		        
        
        return currentClass; 
    }
    
	/**
	 * @see java.lang.ClassLoader#loadClass(java.lang.String)
	 */
	public Class getClass(File file) throws ClassNotFoundException, IOException, FileNotFoundException  {			
		String classPackage = ClassParser.getPackageName(new FileInputStream(file));
		String className = file.getName().substring(0, file.getName().lastIndexOf(".class"));					
		FileInputStream fis = new FileInputStream(file);		
		byte buff[] = new byte[fis.available()];			
		fis.read(buff);				
		
		return defineClass(classPackage + "." + className, buff, 0, buff.length);	
	}	
}