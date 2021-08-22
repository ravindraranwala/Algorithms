package org.clrs.algorithms.greedy;

import java.util.LinkedHashSet;
import java.util.Set;

class ActivitySelection {

	ActivitySelection() {
		throw new AssertionError();
	}

	public static void main(String[] args) {
		final int[] s = { 1, 3, 0, 5, 3, 5, 6, 8, 8, 2, 12 };
		final int[] f = { 4, 5, 6, 7, 9, 9, 10, 11, 12, 14, 16 };
		final Set<Integer> a2 = new LinkedHashSet<>();
		recursiveActivitySelector(s, f, 0, a2);
		System.out.println(a2);
		final Set<Integer> a1 = greedyActivitySelector(s, f);
		System.out.println(a1);
	}

	static void recursiveActivitySelector(int[] s, int[] f, int k, Set<Integer> a) {
		if (k == 0)
			a.add(0);
		int m = k + 1;
		final int n = s.length;
		while (m < n && s[m] < f[k])
			m = m + 1;
		if (m < n) {
			a.add(m);
			recursiveActivitySelector(s, f, m, a);
		}
	}

	static Set<Integer> greedyActivitySelector(int[] s, int[] f) {
		final Set<Integer> a = new LinkedHashSet<>();
		a.add(0);
		int k = 0;
		for (int m = 2; m < s.length; m++) {
			if (s[m] >= f[k]) {
				a.add(m);
				k = m;
			}
		}
		return a;
	}
}
