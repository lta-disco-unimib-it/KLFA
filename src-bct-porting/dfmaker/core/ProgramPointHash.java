package dfmaker.core;

/**
 * This class is used for optimization purposes to reopresent the content of an IoTrace program point.
 * 
 * @author Fabrizio Pastore fabrizio.pastore AT gmail.com
 *
 */
public class ProgramPointHash {
	
	public static enum Type {ENTRY,EXIT}

	private Type type;
	private long startline;
	private long length;
	private int hash;
	
	/**
	 * Represnt a program point of the specified type taht start at the startline line of the tracefile, is long length lines and the hashcode of its contents is the passed one.
	 * 
	 * @param type
	 * @param hash
	 * @param startline
	 * @param length
	 */
	public ProgramPointHash(Type type, int hash, long startline, long length){
		//System.out.println("PPHASH "+type+" "+hash+" "+startline+" "+length);
		this.type = type;
		this.hash = hash;
		this.startline = startline;
		this.length = length;
	}

	public long getLength() {
		return length;
	}

	public long getStartline() {
		return startline;
	}

	public Type getType() {
		return type;
	}

	public int getHash() {
		return hash;
	}
}
