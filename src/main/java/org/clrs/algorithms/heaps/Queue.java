package org.clrs.algorithms.heaps;

/**
 * A data structure designed for holding elements prior to processing.
 * 
 * @author ravindra
 *
 * @param <E>
 */
public interface Queue<E> {
	/**
	 * Adds an element into the queue.
	 * 
	 * @param elt element to be inserted
	 */
	void insert(E elt);

	/**
	 * Inspects the element at the head of the queue without removing it.
	 * 
	 * @return element at the head of the queue.
	 */
	E examine();

	/**
	 * Inspects and removes the element at the head of the queue.
	 * 
	 * @return element at the head of the queue.
	 */
	E extract();

	/**
	 * Checks whether the queue is empty. The queue is empty if there are no
	 * elements in it.
	 * 
	 * @return <code>true</code> if the queue is empty, <code>false</code>
	 *         otherwise.
	 */
	boolean isEmpty();
}
