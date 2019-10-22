package modelsFetchers;

import java.util.Iterator;

public interface IoModelIterator extends Iterator<String> {

	/**
	 * Reset the iterator to teh first position
	 *
	 */
	public void reset();
}
