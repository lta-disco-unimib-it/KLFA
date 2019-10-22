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
