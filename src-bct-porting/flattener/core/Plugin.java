package flattener.core;

/*
 * Created on 18-ott-2003
 */

/**
 * A plugin is a class used to manage a particular class, i.e. user defined class.
 * The Java Standard classes are well known an conseguently are well known their
 * inspectors. In user defined classes indentify the inspectors can be really
 * difficoult; for that reason, implementing a plugin, is the best way to manage
 * a user defined class. Who implements a plugin should know the inspector of the
 * target class, infact a plugin must be specific for a given class.  
 * 
 * @author Davide Lorenzoli
 */
public interface Plugin {
    
	/**
	 *
	 * @param object
	 *
	 * @return
	 */
	public Object[] smash(Object object);

	/**
	 * Returns the target class; i.e. the correct implementation for a plugin
	 * used to manage the <code>java.util.Hashtable</code> class must be as
	 * following:
	 * <pre>
	 * 		public Class getTargetClass() {
	 * 			return java.util.Hashtable.class;
	 * 		}
	 * </pre>
	 * 
	 * @return Returns the target class
	 */
	public Class getTargetClass();
}
