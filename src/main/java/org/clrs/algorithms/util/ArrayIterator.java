package org.clrs.algorithms.util;

import java.util.ConcurrentModificationException;
import java.util.Iterator;

/**
 * An iterator over the elements of the given array. This iterator is fail-fast.
 * 
 * @author ravindraranwala
 *
 * @param <E>
 */
class ArrayIterator<E> implements Iterator<E> {
	private final E[] a;
	private final int size;
	private int pos = 0;

	ArrayIterator(E[] a) {
		this.a = a;
		this.size = a.length;
	}

	@Override
	public boolean hasNext() {
		detectModifications();
		return pos < size;
	}

	@Override
	public E next() {
		detectModifications();
		return a[pos++];
	}

	private void detectModifications() {
		if (size != a.length)
			throw new ConcurrentModificationException();
	}
}
