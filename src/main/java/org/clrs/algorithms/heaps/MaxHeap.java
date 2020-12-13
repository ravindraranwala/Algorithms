package org.clrs.algorithms.heaps;

import java.util.Comparator;

/**
 * Defines a heap data structure which can be used for heap-sort and as a
 * priority queue.
 * 
 * @author ravindra
 *
 * @param <E>
 */
public class MaxHeap<E> {
	private final Comparator<? super E> comparator;
	@SuppressWarnings("unchecked")
	private E[] a = (E[]) new Object[10];
	private int size = 0;

	private MaxHeap(Comparator<? super E> comparator) {
		this.comparator = comparator;
	}

	/**
	 * Constructs a max-heap using the given comparator. This method can be used to
	 * easily convert the max-heap into a min-heap by merely reversing the order
	 * imposed by the comparator.
	 * 
	 * @param <T>
	 * @param comparator comparator used for ordering the elements in the heap.
	 * @return max-heap data structure built using the given comparator
	 */
	public static <T> MaxHeap<T> of(Comparator<? super T> comparator) {
		return new MaxHeap<>(comparator);
	}

	/**
	 * Constructs a max-heap using the natural order of it's elements.
	 * 
	 * @param <T>
	 * @return max-heap data structure built using the natural order of it's
	 *         elements.
	 */
	public static <T extends Comparable<? super T>> MaxHeap<T> of() {
		return new MaxHeap<>(Comparator.naturalOrder());
	}

	/**
	 * Constructs a max-heap using either the natural or reverse order of it's
	 * elements. This method can be used to easily convert the max-heap into a
	 * min-heap by merely reversing the order of elements.
	 * 
	 * @param <T>
	 * @param reversed <code>true</code> if reverse order is needed, false for
	 *                 natural ordering.
	 * @return max-heap data structure built using the specified ordering criteria.
	 */
	public static <T extends Comparable<? super T>> MaxHeap<T> of(boolean reversed) {
		if (reversed)
			return new MaxHeap<>(Comparator.<T>naturalOrder().reversed());
		return of();
	}

	private int parent(int i) {
		return i / 2;
	}

	private int left(int i) {
		return i * 2;
	}

	private int right(int i) {
		return i * 2 + 1;
	}

	private void maxHeapify(int i, int heapSize) {
		if (heapSize > size)
			throw new IllegalArgumentException("Heap size cannot be larger than array length.");
		final int l = left(i);
		final int r = right(i);
		int largest = i;
		if (l <= heapSize && comparator.compare(a[l], a[i]) > 0)
			largest = l;
		if (r <= heapSize && comparator.compare(a[r], a[i]) > 0)
			largest = r;
		if (largest != i) {
			// exchange the elements and build the heap property.
			final E tmp = a[i];
			a[i] = a[largest];
			a[largest] = tmp;
			maxHeapify(largest, heapSize);
		}
	}

	private void iterativeMaxHeapify(E[] a, int i) {
		int largest = i;
		while (largest <= size / 2) {
			final int l = left(i);
			final int r = right(i);
			if (comparator.compare(a[l], a[i]) > 0)
				largest = l;
			if (comparator.compare(a[r], a[i]) > 0)
				largest = r;
			if (largest == i)
				return;

			// exchange the elements and build the heap property.
			final E tmp = a[i];
			a[i] = a[largest];
			a[largest] = tmp;

		}
	}

	private void buildMaxHeap(E[] a) {
		for (int l = size, i = l / 2; i >= 0; i--)
			maxHeapify(i, l);
	}

	private void heapSort() {
		buildMaxHeap(a);
		for (int i = size, heapSize = size; i >= 2; i--) {
			final E tmp = a[0];
			a[0] = a[i];
			a[i] = tmp;
			heapSize = heapSize - 1;
			maxHeapify(0, heapSize);
		}
	}

	public void ensureCapacity(int mincap) {
		int oldcap = size;
		if (mincap > oldcap) {
			int newcap = Math.max(mincap, (oldcap * 3) / 2 + 1);
			E[] oldarr = a;
			a = (E[]) new Object[newcap]; // unchecked cast
			System.arraycopy(oldarr, 0, a, 0, size);
		}
	}

	public void add(int i, E elt) {
		ensureCapacity(size + 1);
	}

	public static void main(String[] args) {

	}

}
