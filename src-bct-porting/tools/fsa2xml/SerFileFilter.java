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
package tools.fsa2xml;

import java.io.File;
import java.io.FileFilter;

public class SerFileFilter implements FileFilter{
    
 public static final String EXTENSION = ".ser";
   
    
    
    public SerFileFilter(){
        
    }
    
    public boolean accept(File pathname){
        
        
        String name = pathname.getName();
        int nameLength = name.length();
        int extLength = EXTENSION.length();
        
    
        if(pathname.isDirectory()){// return true if it's a directory
            return true;
        }
        else if( (nameLength > extLength) &&
                 (name.substring(nameLength - extLength).equals(EXTENSION))){
            return true;   
        }
        else{
            return false;
        }
    
    }

}
