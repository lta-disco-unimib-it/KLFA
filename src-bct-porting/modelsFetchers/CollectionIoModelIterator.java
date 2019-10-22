package modelsFetchers;

import java.util.Collection;
import java.util.Iterator;

public class CollectionIoModelIterator implements IoModelIterator {

	private Collection<String> models;
	private Iterator<String>	iterator;
	
	public CollectionIoModelIterator( Collection<String> models ){
		this.models = models;
		iterator = models.iterator();
	}
	
	public void reset() {
		iterator = models.iterator();
	}

	public boolean hasNext() {
		return iterator.hasNext();
	}

	public String next() {
		return iterator.next();
	}

	public void remove() {
		throw new UnsupportedOperationException();
	}

}
