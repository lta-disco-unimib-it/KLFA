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
package dfmaker.core;

/**
 * @author Davide Lorenzoli
 */
public class Variable {
    private String name;
    private String value;
    private int modified;
        
    /**
     * Variable constructor 
     * 
     * @param name	name of the variable
     * @param value value of variable
     * @param modified	vrable modifier
     */
    public Variable(String name, String value, int modified) {
        this.name = name;
        this.value = value;
        this.modified = modified;
    }
    
    /**
     * @return Returns the name.
     */
    public String getName() {
        return name;
    }
    
    /**
     * @return Returns the value.
     */
    public String getValue() {
        return value;
    }
    
    /**
     * @return Returns the modified.
     */
    public int getModified() {
        return modified;
    }            
    
    /**
     * @see java.lang.Object#toString()
     */
    public String toString() {
        return 	"[name: " + name +
        		", value: " + value +
        		", modified: " + modified +
        		"]";
    }

	public void setValue(String value) {
		this.value = value;		
	}
	
	public boolean equals( Object o ){
		if ( this == o ){
			return true;
		}
		
		if ( ! ( o instanceof Variable ) ){
			return false;
		}
		
		Variable rhs = (Variable)o;
		
		return ( this.modified == rhs.modified && this.name.equals( rhs.name ) && this.value.equals( rhs.value ));
	}
	
	public int hasCode(){
		return (name+value+modified).hashCode();
	}
}
