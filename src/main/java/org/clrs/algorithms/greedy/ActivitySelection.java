package org.clrs.algorithms.greedy;

import java.util.Arrays;

class ActivitySelection {

	ActivitySelection() {
		throw new AssertionError();
	}

	public static void main(String[] args) {
		final int[] s = { 1, 3, 0, 5, 3, 5, 6, 8, 8, 2, 12 };
		final int[] f = { 4, 5, 6, 7, 9, 9, 10, 11, 12, 14, 16 };
		final int[] a = recursiveActivitySelector(s, f, -1, 0);
		System.out.println(Arrays.toString(a));
	}

	static int[] recursiveActivitySelector(int[] s, int[] f, int k, int i) {
		int m = k + 1;
		final int n = s.length;
		while (m < n && m > 0 && s[m] < f[k])
			m = m + 1;
		if (m < n) {
			final int[] a = recursiveActivitySelector(s, f, m, i + 1);
			a[i] = m;
			return a;
		} else
			return new int[i];
	}
}
