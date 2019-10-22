/*
 * Created on 31-ago-2004
 */
package flattener.plugins;

import java.util.Vector;

import flattener.core.Plugin;

/**
 * @author Davide Lorenzoli
 */
public class VectorPlugin implements Plugin {

    /**
     * @see flattener.plugins.Plugin#smash(java.lang.Object)
     */
    public Object[] smash(Object object) {        
        Vector vector = (Vector) object;
        return vector.toArray();
    }

    /**
     * @see flattener.plugins.Plugin#getTargetClass()
     */
    public Class getTargetClass() {
        return Vector.class;
    }

}
