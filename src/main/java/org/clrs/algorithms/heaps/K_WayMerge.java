package org.clrs.algorithms.heaps;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Comparator;
import java.util.NoSuchElementException;

public class K_WayMerge {
	private K_WayMerge() {
		throw new AssertionError("Non instantiable !");
	}

	public static void main(String[] args) {
		final Integer[] sortedOne = { 2, 7, 16 };
		final Integer[] sortedTwo = { 5, 10, 20 };
		final Integer[] sortedThree = { 3, 6, 21 };
		final Integer[] sortedFour = { 4, 8, 9 };

		final Integer[] mergedData = kWayMerge(sortedOne, sortedTwo, sortedThree, sortedFour);
		System.out.println(Arrays.toString(mergedData));
	}

	public static <T> T[] kWayMerge(Comparator<? super T> cmp, T[]... sortedLists) {
		final int k = sortedLists.length;
		final Queue<ElementWrapper<T>> queue = PriorityQueue.of(new ElementWrapperComparator<T>(cmp));
		int n = 0;
		int pos = 0;
		final int[] nextIdx = new int[k];
		for (T[] a : sortedLists) {
			if (a.length == 0)
				throw new NoSuchElementException("Empty array is provided !");
			// Adding the first element of each list to the heap.
			nextIdx[pos] = 1;
			queue.insert(new ElementWrapper<>(a[0], pos++));
			n += a.length;
		}

		@SuppressWarnings("unchecked")
		final T[] sortedData = (T[]) Array.newInstance(sortedLists[0].getClass().getComponentType(), n);
		int count = 0;
		while (!queue.isEmpty()) {
			final ElementWrapper<T> wrapper = queue.extract();
			sortedData[count++] = wrapper.elt;
			final int nextArrIdx = nextIdx[wrapper.pos];
			if (nextArrIdx < sortedLists[wrapper.pos].length) {
				queue.insert(new ElementWrapper<T>(sortedLists[wrapper.pos][nextArrIdx], wrapper.pos));
				nextIdx[wrapper.pos] += 1;
			}
		}
		return sortedData;
	}

	public static <T extends Comparable<? super T>> T[] kWayMerge(T[]... sortedLists) {
		return kWayMerge(Comparator.naturalOrder(), sortedLists);
	}

	static class ElementWrapper<E> {
		final E elt;
		final int pos;

		ElementWrapper(E elt, int pos) {
			this.elt = elt;
			this.pos = pos;
		}
	}

	static class ElementWrapperComparator<E> implements Comparator<ElementWrapper<E>> {
		private final Comparator<? super E> cmp;

		ElementWrapperComparator(Comparator<? super E> cmp) {
			this.cmp = cmp;
		}

		@Override
		public int compare(ElementWrapper<E> o1, ElementWrapper<E> o2) {
			return this.cmp.compare(o1.elt, o2.elt);
		}
	}
}
