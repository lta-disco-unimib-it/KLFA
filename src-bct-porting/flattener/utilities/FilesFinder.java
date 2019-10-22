/*
 * Created on 30-lug-2004
 */
package flattener.utilities;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Vector;

/**
 * @author Davide Lorenzoli
 */
public class FilesFinder {
    
    private Vector filesFound = new Vector(0);

    /**
     * Find a file into the file system given it's name. The file name specification
     * allows regular expressions as specified in java.util.regex.Pattern
     * and the patterns:
     * <BR>
     * <LI> <B>*</B>: seek for any files;</LI> 
     * <LI> <B>*.*</B>: seek for any files with any extensions;</LI> 
     * <LI> <B>[name].*</B>: seek for a given name file with any extensions;</LI>
     * <LI> <B>*.[extension]</B>: seek for any files name with a specfied extension.</LI>
     * <BR>
     * The matching is case sensitive: "Foo.java" is different from "foo.java".  
     * 
     * @param startPath
     * @param fileName
     * @param recursive
     * @throws FileNotFoundException 
     * @throws IOException
     * 
     * @return The found files array, it will be empty if no one file
     * will be found.
     */
    public File[] find(String startPath, String fileName, boolean recursive) throws FileNotFoundException, IOException {
        // Reset the previous found files
        this.filesFound = new Vector(0);
        
        // Do the find procedure
        doFind(startPath, fileName, recursive);
        return getFilesFound();
    }
    
    private void doFind(String startPath, String fileName, boolean recursive) throws FileNotFoundException, IOException {
        
        File currentFile = new File(startPath);
        
        if (!currentFile.exists()) {
            throw new FileNotFoundException(currentFile.getCanonicalPath());            
        }
        
        if (currentFile.isDirectory()) {
            File files[] = currentFile.listFiles();
            
            for (int i=0; i<files.length; i++) {
                if (files[i].isDirectory() && recursive) {
                    doFind(files[i].getCanonicalPath(), fileName, recursive);
                }
                if (files[i].isFile()) {
                    doFind(files[i].getCanonicalPath(), fileName, recursive);
                }
            }
        } else {            
            if (matches(currentFile.getName(), fileName)) {
                filesFound.add(currentFile);                
            }
        }
        return;        
    }
    
    private boolean matches(String currentFileName, String fileName) {
        boolean isFound = false;        
        
        // *.*
        if (fileName.equals("*.*")) {            
            isFound = currentFileName.lastIndexOf(".") != -1 ? true : false;
        }
        // *
        else if (fileName.equals("*")) {
            isFound = true;
        }        
        // *.[extension]
        else if (fileName.indexOf("*.") != -1) {
            String extension = currentFileName.substring(currentFileName.lastIndexOf(".")+1);
            isFound = ("*." + extension).equals(fileName);            
        }
        // [name].*        
        else if (fileName.indexOf(".*") != -1) {
            String name = currentFileName.substring(0, currentFileName.lastIndexOf("."));
            isFound = (name + ".*").equals(fileName);                        
        }
        else {
            isFound = currentFileName.matches(fileName);
        }
        return isFound;
    }
    
    private File[] getFilesFound() {
        File filesFound[] = new File[this.filesFound.size()];
        
        for (int i=0; i<filesFound.length; i++) {
            filesFound[i] = (File) this.filesFound.get(i);
        }                       
        
        return filesFound;
    }
}