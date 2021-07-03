package org.clrs.algorithms.heaps;

import java.lang.reflect.Array;
import java.util.AbstractMap;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Map;

public class K_WayMerge {
	private K_WayMerge() {
		throw new AssertionError("Non instantiable !");
	}

	public static void main(String[] args) {
		final Integer[] sortedOne = { 2, 7, 16 };
		final Integer[] sortedTwo = { 5, 10, 20 };
		final Integer[] sortedThree = { 3, 6, 21 };
		final Integer[] sortedFour = { 4, 8, 9 };

		final Integer[] mergedData = kWayMerge(Integer.class, sortedOne, sortedTwo, sortedThree, sortedFour);
		System.out.println(Arrays.toString(mergedData));
	}

	public static <T> T[] kWayMerge(Comparator<? super T> cmp, Class<T> clazz, T[]... sortedLists) {
		final Queue<Map.Entry<T, Iterator<T>>> queue = PriorityQueue.of(Map.Entry.comparingByKey(cmp),
				sortedLists.length);
		int n = 0;
		for (T[] a : sortedLists) {
			final Iterator<T> it = Arrays.asList(a).iterator();
			if (it.hasNext())
				queue.insert(new AbstractMap.SimpleEntry<>(it.next(), it));
			n += a.length;
		}

		@SuppressWarnings("unchecked")
		final T[] sortedData = (T[]) Array.newInstance(clazz, n);
		int count = 0;
		while (!queue.isEmpty()) {
			final Map.Entry<T, Iterator<T>> entry = queue.extract();
			sortedData[count++] = entry.getKey();
			final Iterator<T> it = entry.getValue();
			if (it.hasNext())
				queue.insert(new AbstractMap.SimpleEntry<>(it.next(), it));
		}
		return sortedData;
	}

	public static <T extends Comparable<? super T>> T[] kWayMerge(Class<T> clazz, T[]... sortedLists) {
		return kWayMerge(Comparator.naturalOrder(), clazz, sortedLists);
	}
}
