package org.clrs.algorithms.heaps;

public interface Queue<E> {
	void insert(E elt);
	E examineHead();
	E extractHead();
	boolean isEmpty();
}
