package org.clrs.algorithms.heaps;

public interface Queue<E> {
	void insert(E elt);
	E examine();
	E extract();
	boolean isEmpty();
}
