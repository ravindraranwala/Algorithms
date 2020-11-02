package org.clrs.algorithms.dc;

import java.util.Arrays;
import java.util.Comparator;

public class MergeSort {
	private MergeSort() {
		throw new AssertionError();
	}

	public static void main(String[] args) {
		final Integer[] ints = new Integer[] { 2, 4, 5, 7, 1, 2, 3, 6 };
		sort(ints);
		System.out.println(Arrays.toString(ints));

		final Student bloch = new Student("Bloch", 3.81);
		final Student jenny = new Student("Jenny", 2.51);
		final Student meyers = new Student("Meyers", 3.76);
		final Student stroustrup = new Student("Stroustrup", 3.94);
		final Student forrest = new Student("Forrest", 2.74);
		final Student[] stds = new Student[] { bloch, jenny, meyers, stroustrup, forrest };

		sort(stds, Comparator.comparingDouble(Student::getGpa).reversed());
		System.out.println(Arrays.toString(stds));
	}

	private static <T> void merge(T[] a, int p, int q, int r, Comparator<T> cmp) {
		final int n1 = q - p + 1;
		final int n2 = r - q;
		/*
		 * The elements array will contain only T instances. This is sufficient to
		 * ensure type safety, but the runtime type of the array won't be T[]; it will
		 * always be Object[]!
		 */
		@SuppressWarnings("unchecked")
		T[] left = (T[]) new Object[n1];
		@SuppressWarnings("unchecked")
		T[] right = (T[]) new Object[n2];

		for (int i = 0; i < n1; i++)
			left[i] = a[p + i];
		for (int j = 0; j < n2; j++)
			right[j] = a[q + j + 1];

		int i = 0;
		int j = 0;
		int k = p;
		while (i < n1 && j < n2) {
			if (cmp.compare(left[i], right[j]) <= 0)
				a[k] = left[i++];
			else
				a[k] = right[j++];

			k++;
		}

		while (i < n1)
			a[k++] = left[i++];

		while (j < n2)
			a[k++] = right[j++];
	}

	public static <T> void sort(T[] a, Comparator<? super T> cmp) {
		mergeSort(a, 0, a.length - 1, cmp);
	}

	public static <T extends Comparable<? super T>> void sort(T[] a) {
		mergeSort(a, 0, a.length - 1, Comparator.naturalOrder());
	}

	private static <T> void mergeSort(T[] a, int p, int r, Comparator<T> cmp) {
		if (p < r) {
			final int q = (p + r) / 2;
			mergeSort(a, p, q, cmp);
			mergeSort(a, q + 1, r, cmp);
			merge(a, p, q, r, cmp);
		}
	}
}
