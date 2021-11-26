package org.clrs.algorithms.heaps;

/**
 * A data structure designed for holding elements prior to processing.
 * 
 * @author ravindra
 *
 * @param <E> the type of elements in this {@link Queue}
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

	/**
	 * This implementation returns an array containing all the elements of this
	 * {@link Queue}. The runtime type of the returned array is that of the
	 * specified type.
	 * 
	 * @param <T>
	 * @param clazz The runtime type of the returned array
	 * @return an array containing all the elements of this {@link Queue}
	 */
	public <T> T[] toArray(Class<T> clazz);
}
