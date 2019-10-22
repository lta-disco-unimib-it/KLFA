package it.unimib.disco.lta.alfa.dataTransformation;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * HashMap that stores last state and allows the loading of the previous state.
 *
 * @param <K> Key type.
 * @param <V> Value type.
 */
public class HistoryHashMap<K, V> implements Map<K, V> {
	
	private HashMap<K, V> theMap = new HashMap<K, V>();
	private HashMap<K, V> lastState = new HashMap<K, V>();
	private HashSet<K> addedKeys = new HashSet<K>();
	
	public void clear() {
		theMap.clear();
	}
	
	public Object clone() {
		return theMap.clone();
	}
	
	public boolean containsKey(Object key) {
		return theMap.containsKey(key);
	}
	
	public boolean containsValue(Object value) {
		return theMap.containsValue(value);
	}
	
	public Set<Entry<K, V>> entrySet() {
		return theMap.entrySet();
	}
	
	public boolean equals(Object o) {
		return theMap.equals(o);
	}
	
	public V get(Object key) {
		return theMap.get(key);
	}
	
	public int hashCode() {
		return theMap.hashCode();
	}
	
	public boolean isEmpty() {
		return theMap.isEmpty();
	}
	
	public Set<K> keySet() {
		return theMap.keySet();
	}
	
	public  synchronized V put(K key, V value) {
		saveStatePut(key, value);
		
		return theMap.put(key, value);
	}
	
	protected void saveStatePut( K key, V value ){
		resetOldState();
		stateModification(key, value);
	}
	
	protected void resetOldState(){
		lastState.clear();
		addedKeys.clear();
	}
	
	protected void stateModification( K key, V value ){
		if ( theMap.containsKey(key) ){
			V oldValue = theMap.get(key);
			lastState.put(key, oldValue);
		} else {
			addedKeys.add(key);
		}
	}
	
	public  synchronized void putAll( Map<? extends K, ? extends V> m ) {
		saveStatePutAll(m);
		
		theMap.putAll(m);
	}
	
	protected  void saveStatePutAll( Map<? extends K, ? extends V> m ){
		resetOldState();
		
		for ( Entry<? extends K, ? extends V> e : m.entrySet() ){
			stateModification(e.getKey(), e.getValue());	
		}
	}
	
	public  synchronized V remove(Object key) {
		saveStateRemove(key);
		return theMap.remove(key);
	}
	
	protected void saveStateRemove( Object key ) {
		resetOldState();
		if ( theMap.containsKey(key) ){
			lastState.put((K) key, theMap.get(key));
		}
	}
	
	public synchronized void  revertToOldState(){
	
		for ( K key : addedKeys ){
			theMap.remove(key);
		}
		
		for ( Entry<K,V> entry : lastState.entrySet() ){
			theMap.put(entry.getKey(), entry.getValue());
		}
		
		
	}
	
	public int size() {
		return theMap.size();
	}
	
	public String toString() {
		return theMap.toString();
	}
	
	public Collection<V> values() {
		return theMap.values();
	}
	
}
