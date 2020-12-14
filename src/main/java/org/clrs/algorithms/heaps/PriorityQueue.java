package org.clrs.algorithms.heaps;

import java.util.Comparator;
import java.util.NoSuchElementException;

import org.clrs.algorithms.dc.Student;

/**
 * Defines a priority queue using a heap data structure.
 * 
 * @author ravindra
 *
 * @param <E>
 */
public class PriorityQueue<E> implements Queue<E> {
	private final Comparator<? super E> comparator;
	@SuppressWarnings("unchecked")
	private E[] a = (E[]) new Object[10];
	private int size = 0;

	private PriorityQueue(Comparator<? super E> comparator) {
		this.comparator = comparator;
	}

	/**
	 * Constructs a priority queue using the given comparator.
	 * 
	 * @param <T>
	 * @param comparator comparator used for ordering the elements in the heap.
	 * @return priority queue data structure built using the given comparator
	 */
	public static <T> PriorityQueue<T> of(Comparator<? super T> comparator) {
		return new PriorityQueue<>(comparator);
	}

	/**
	 * Constructs a priority queue using the natural order of it's elements.
	 * 
	 * @param <T>
	 * @return priority queue data structure built using the natural order of it's
	 *         elements.
	 */
	public static <T extends Comparable<? super T>> PriorityQueue<T> of() {
		return new PriorityQueue<>(Comparator.naturalOrder());
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

	private void minHeapify(int i, int heapSize) {
		if (heapSize > size)
			throw new IllegalArgumentException("Heap size cannot be larger than array length.");
		final int l = left(i);
		final int r = right(i);
		int smalest = i;
		if (l <= heapSize && comparator.compare(a[l], a[i]) < 0)
			smalest = l;
		if (r <= heapSize && comparator.compare(a[r], a[i]) < 0)
			smalest = r;
		if (smalest != i) {
			// exchange the elements and build the heap property.
			final E tmp = a[i];
			a[i] = a[smalest];
			a[smalest] = tmp;
			minHeapify(smalest, heapSize);
		}
	}

	private void iterativeMinHeapify(int i, int heapSize) {
		int smallest = i;
		while (smallest <= size / 2) {
			final int l = left(i);
			final int r = right(i);
			if (comparator.compare(a[l], a[i]) < 0)
				smallest = l;
			if (comparator.compare(a[r], a[i]) < 0)
				smallest = r;
			if (smallest == i)
				return;

			// exchange the elements and build the heap property.
			final E tmp = a[i];
			a[i] = a[smallest];
			a[smallest] = tmp;

		}
	}

	private void buildMinHeap() {
		for (int i = size / 2; i >= 0; i--)
			minHeapify(i, size);
	}

	private void heapSort() {
		buildMinHeap();
		for (int i = size, heapSize = size; i >= 2; i--) {
			final E tmp = a[0];
			a[0] = a[i];
			a[i] = tmp;
			heapSize = heapSize - 1;
			minHeapify(0, heapSize);
		}
	}

	private void ensureCapacity(int mincap) {
		int oldcap = size;
		if (mincap > oldcap) {
			int newcap = Math.max(mincap, (oldcap * 3) / 2 + 1);
			E[] oldarr = a;
			a = (E[]) new Object[newcap]; // unchecked cast
			System.arraycopy(oldarr, 0, a, 0, size);
		}
	}

	@Override
	public void insert(E elt) {
		ensureCapacity(size + 1);
		a[size] = elt;
		updateKey(size);
		size++;
	}

	private void updateKey(int i) {
		while (i > 0 && comparator.compare(a[parent(i)], a[i]) > 0) {
			final E tmp = a[parent(i)];
			a[parent(i)] = a[i];
			a[i] = tmp;
			i = parent(i);
		}
	}

	@Override
	public E examineHead() {
		if (this.size < 1)
			throw new NoSuchElementException();
		return a[0];
	}

	@Override
	public E extractHead() {
		if (this.size < 1)
			throw new NoSuchElementException();
		final E max = a[0];
		a[0] = a[size - 1];
		size--;
		iterativeMinHeapify(0, size);
		return max;
	}

	@Override
	public boolean isEmpty() {
		return size == 0;
	}

	public static void main(String[] args) {
		final PriorityQueue<Integer> intQueue = PriorityQueue.of();
		intQueue.insert(4);
		intQueue.insert(2);
		intQueue.insert(7);
		intQueue.insert(5);

		while (!intQueue.isEmpty())
			System.out.println(intQueue.extractHead());

		final Student bloch = new Student("Bloch", 3.81);
		final Student jenny = new Student("Jenny", 2.51);
		final Student meyers = new Student("Meyers", 3.76);
		final Student stroustrup = new Student("Stroustrup", 3.94);
		final Student forrest = new Student("Forrest", 2.74);

		final PriorityQueue<Student> stdQueue = PriorityQueue
				.of(Comparator.comparingDouble(Student::getGpa).reversed());
		stdQueue.insert(meyers);
		stdQueue.insert(stroustrup);
		stdQueue.insert(jenny);
		stdQueue.insert(bloch);
		stdQueue.insert(forrest);

		while (!stdQueue.isEmpty())
			System.out.println(stdQueue.extractHead());
	}

}