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
