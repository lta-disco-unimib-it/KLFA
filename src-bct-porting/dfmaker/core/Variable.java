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
