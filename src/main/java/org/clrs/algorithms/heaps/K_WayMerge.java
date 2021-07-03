package org.clrs.algorithms.heaps;

import java.lang.reflect.Array;
import java.util.AbstractMap;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Map;
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
		final Queue<Map.Entry<T, Integer>> queue = PriorityQueue.of(Map.Entry.comparingByKey(cmp), k);
		int n = 0;
		int pos = 0;
		final int[] nextIdx = new int[k];
		for (T[] a : sortedLists) {
			if (a.length == 0)
				throw new NoSuchElementException("Empty array is provided !");
			// Adding the first element of each list to the heap.
			nextIdx[pos] = 1;
			queue.insert(new AbstractMap.SimpleEntry<>(a[0], pos++));
			n += a.length;
		}

		@SuppressWarnings("unchecked")
		final T[] sortedData = (T[]) Array.newInstance(sortedLists[0].getClass().getComponentType(), n);
		int count = 0;
		while (!queue.isEmpty()) {
			final Map.Entry<T, Integer> entry = queue.extract();
			sortedData[count++] = entry.getKey();
			final int listPos = entry.getValue();
			final int nextArrIdx = nextIdx[listPos];
			if (nextArrIdx < sortedLists[listPos].length) {
				queue.insert(new AbstractMap.SimpleEntry<>(sortedLists[listPos][nextArrIdx], listPos));
				nextIdx[listPos] += 1;
			}
		}
		return sortedData;
	}

	public static <T extends Comparable<? super T>> T[] kWayMerge(T[]... sortedLists) {
		return kWayMerge(Comparator.naturalOrder(), sortedLists);
	}
}
