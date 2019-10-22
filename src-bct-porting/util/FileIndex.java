package util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Properties;
import java.util.Set;
import java.util.TreeSet;



/**
 * An index for method name, that associate a method to each id
 * 
 * @author Fabrizio Pastore fabrizio.pastore AT gmail.com
 *
 */
public class FileIndex {
	
	
	public class FileIndexException extends Exception {

		public FileIndexException(String string) {
			// TODO Auto-generated constructor stub
		}
		
	}
	
	/* Data are recorded as Name = ID*/
	private Properties index = new Properties();
	/*Reverse index, useful to get name from an id*/
	private HashMap<String,String> reverseIndex = new HashMap<String,String>();
	
	private String suffix;
	
	/**
	 * Create a file index
	 * 
	 * @param file
	 */
	public FileIndex ( File file ){
		this( file, "" );
	}
	
	/**
	 * Create a fil index that creates ids that end with suffix
	 * 
	 * @param file
	 * @param suffix
	 */
	public FileIndex ( File file, String suffix ){
		this.suffix = suffix;
		
		try {
			if ( ! file.exists() ){
				file.createNewFile();
				return;
			}
			InputStream fileStream = new FileInputStream(file);
		
			index.load(fileStream);
			fileStream.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Iterator it = index.keySet().iterator();
		while ( it.hasNext() ){
			String key = (String) it.next();
			reverseIndex.put( (String) index.get(key), key );
		}
		
	}

	/**
	 * Given a method name return an id
	 * 
	 * @param methodName
	 * @return
	 * @throws FileIndexException 
	 */
	public String getId(String name) throws FileIndexException {
		if ( ! containsName(name) )
			throw new FileIndexException("Method not present");
		return index.getProperty(name);
	}
	
	/**
	 * Given an id return the correspnding metgod name
	 * @param id
	 * @return
	 * @throws FileIndexException
	 */
	public String getNameFromId(String id) throws FileIndexException {
		//System.out.println("FILE INDEX request for id "+id+" "+reverseIndex.get(id));
		if ( ! containsId( id ) )
			throw new FileIndexException("Method not present");
		return (String) reverseIndex.get(id);
	}

	/**
	 * Returns true or false weathe ror not this index contains a name associated to the given id
	 *  
	 * @param id
	 * @return
	 */
	public boolean containsId( String id ){
		return reverseIndex.containsKey(id);
	}
	
	/**
	 * Returns true or false weathe ror not this index contains a name associated to the given id
	 *  
	 * @param id
	 * @return
	 */
	public boolean containsName( String name ){
		return index.containsKey(name);
	}
	
	/**
	 * Add a method to this list and returns an id
	 * 
	 * @param methodName
	 * @return
	 */
	public String add(String methodName) {
		if ( index.containsKey(methodName) )
			return index.getProperty(methodName);
		String id = ""+index.size()+suffix;
		index.put(methodName, id);
		reverseIndex.put(id, methodName);
		return id;
	}
	
	
	public void save( File file ) throws IOException {
			FileOutputStream fos = new FileOutputStream( file );
			index.store(fos, null);
			fos.close();
	}

	/**
	 * Return an ArrayList of all the IDs
	 * 
	 * @return
	 */
	public ArrayList getIds() {
		return new ArrayList(reverseIndex.keySet());
	}
	
	public String toString() {
		String string = "FIleIndex : { ";
		Iterator it = index.keySet().iterator();
		while ( it.hasNext() ){
			String id = (String) it.next();
			string +=  id+ " = "+index.getProperty(id); 
		}
		return string + "}";
	}

	public int size() {
		return index.size();
	}
	
	public Set<String> getNames() {
		Set<String> names = new TreeSet<String>();
		
		//Not working in Eclipse Ganymede
		//names.addAll((Collection<? extends String>)index.keySet());
		
		Set<Object> keySet = index.keySet();
		for ( Object key : keySet ){
			names.add( (String)key );
		}
		return names;
	}
	
}
