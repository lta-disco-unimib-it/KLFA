package flattener.plugins;

import java.util.Enumeration;
import java.util.Hashtable;

import flattener.core.Plugin;


/**
 * @author Davide Lorenzoli
 */
public class HashtablePlugin implements Plugin {

	/**
	 * @see flattener.core.Plugin#smash(java.lang.Object)
	 */
	public Object[] smash(Object object) {
		Hashtable ht = (Hashtable) object;
		Object array[] = new Object[ht.size()];
			
		Enumeration keys = ht.keys();
		int i = 0;		
		while (keys.hasMoreElements()) {
			array[i++] = ht.get(keys.nextElement());
		}	
		return array;
	}

    /**
     * @see flattener.plugins.Plugin#getTargetClass()
     */
    public Class getTargetClass() {        
        return Hashtable.class;
    }
}
