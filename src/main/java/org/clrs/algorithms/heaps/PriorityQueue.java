package org.clrs.algorithms.heaps;

import java.lang.reflect.Array;
import java.util.Comparator;
import java.util.NoSuchElementException;
import java.util.StringJoiner;

import org.clrs.algorithms.dc.Student;

/**
 * An unbounded priority {@link Queue} based on a priority heap. The elements of
 * the priority queue are ordered according to their natural ordering, or by a
 * Comparator provided at queue construction time, depending on which static
 * factory method is used for construction.
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
		a[0] = null; // dummy head
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
		if (heapSize > size)
			throw new IllegalArgumentException("Heap size cannot be larger than array length.");
		int smallest = i;
		while (smallest <= size / 2) {
			final int l = left(i);
			final int r = right(i);
			if (l <= heapSize && comparator.compare(a[l], a[i]) < 0)
				smallest = l;
			else if (r <= heapSize && comparator.compare(a[r], a[i]) < 0)
				smallest = r;
			else
				return;

			// exchange the elements and build the heap property.
			final E tmp = a[i];
			a[i] = a[smallest];
			a[smallest] = tmp;
		}
	}

	private void buildMinHeap() {
		for (int i = size / 2; i > 0; i--)
			minHeapify(i, size);
	}

	private void heapSort() {
		buildMinHeap();
		for (int i = size, heapSize = size; i >= 2; i--) {
			final E tmp = a[1];
			a[1] = a[i];
			a[i] = tmp;
			heapSize = heapSize - 1;
			minHeapify(1, heapSize);
		}
	}

	private void ensureCapacity(int mincap) {
		final int oldcap = a.length;
		if (mincap > oldcap) {
			int newcap = Math.max(mincap, (oldcap * 3) / 2 + 1);
			E[] oldarr = a;
			a = (E[]) new Object[newcap]; // unchecked cast
			a[0] = null;
			System.arraycopy(oldarr, 1, a, 1, size);
		}
	}

	@Override
	public void insert(E elt) {
		ensureCapacity(size + 2);
		a[size + 1] = elt;
		decreaseKey(size + 1);
		size++;
	}

	private void decreaseKey(int i) {
		while (i > 1 && comparator.compare(a[parent(i)], a[i]) > 0) {
			final E tmp = a[parent(i)];
			a[parent(i)] = a[i];
			a[i] = tmp;
			i = parent(i);
		}
	}

	@Override
	public E examine() {
		if (this.size == 0)
			throw new NoSuchElementException();
		return a[1];
	}

	@Override
	public E extract() {
		if (this.size == 0)
			throw new NoSuchElementException();
		final E head = a[1];
		a[1] = a[size];
		a[size] = null;
		size--;
		minHeapify(1, size);
		return head;
	}

	@Override
	public boolean isEmpty() {
		return size == 0;
	}

	@Override
	public String toString() {
		final StringJoiner sj = new StringJoiner(", ", "[", "]");
		for (int i = 1; i <= size; i++)
			sj.add(a[i].toString());

		return sj.toString();
	}

	/**
	 * This implementation returns an array containing all the elements of this
	 * {@link PriorityQueue}. The runtime type of the returned array is that of the
	 * specified type.
	 * 
	 * @param <T>
	 * @param clazz The runtime type of the returned array
	 * @return an array containing all the elements of this {@link PriorityQueue}
	 */
	public <T> T[] toArray(Class<T> clazz) {
		@SuppressWarnings("unchecked")
		final T[] arr = (T[]) Array.newInstance(clazz, size);
		System.arraycopy(a, 1, arr, 0, size);
		return arr;
	}

	public static void main(String[] args) {
		final Queue<Integer> intQueue = PriorityQueue.of();
		intQueue.insert(4);
		intQueue.insert(2);
		intQueue.insert(7);
		intQueue.insert(5);

		while (!intQueue.isEmpty())
			System.out.println(intQueue.extract());

		final Student bloch = new Student("Bloch", 3.81);
		final Student jenny = new Student("Jenny", 2.51);
		final Student meyers = new Student("Meyers", 3.76);
		final Student stroustrup = new Student("Stroustrup", 3.94);
		final Student forrest = new Student("Forrest", 2.74);

		final Queue<Student> stdQueue = PriorityQueue.of(Comparator.comparingDouble(Student::getGpa).reversed());
		stdQueue.insert(meyers);
		stdQueue.insert(stroustrup);
		stdQueue.insert(jenny);
		stdQueue.insert(bloch);
		stdQueue.insert(forrest);

		while (!stdQueue.isEmpty())
			System.out.println(stdQueue.extract());
	}

}
