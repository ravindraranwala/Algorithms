package org.clrs.algorithms.dp;

import java.util.Arrays;
import java.util.StringJoiner;

import org.clrs.algorithms.util.BinarySearch;

class WeightedIntervalScheduling {

	WeightedIntervalScheduling() {
		throw new AssertionError();
	}

	public static void main(String[] args) {
		// usecase 1.
		final int[] s = { 1, 2, 3, 3 };
		final int[] f = { 3, 4, 5, 6 };
		final int[] v = { 50, 10, 40, 70 };
		final int[] a = new int[s.length];
		int n = s.length;
		int[] p = schedule(s, f, v, a);
		System.out.println(p[n - 1]);
		String as = findActivities(v, p, a);
		System.out.println(as);

		// Usecase 2.
		final int[] s1 = { 1, 2, 3, 4, 6 };
		final int[] f1 = { 3, 5, 10, 6, 9 };
		final int[] v1 = { 20, 20, 100, 70, 60 };
		final int[] a1 = new int[s1.length];
		p = schedule(s1, f1, v1, a1);
		n = s1.length;
		System.out.println(p[n - 1]);
		as = findActivities(v1, p, a1);
		System.out.println(as);

		// Usecase 3.
		final int[] s2 = { 1, 1, 1 };
		final int[] f2 = { 2, 3, 4 };
		final int[] v2 = { 5, 6, 4 };
		final int[] a2 = new int[s2.length];
		p = schedule(s2, f2, v2, a2);
		n = s2.length;
		System.out.println(p[n - 1]);
		as = findActivities(v2, p, a2);
		System.out.println(as);
	}

	static int[] schedule(int[] s, int[] f, int[] v, int[] an) {
		final int n = s.length;
		final Activity[] a = new Activity[n];
		for (int i = 0; i < n; i++)
			a[i] = new Activity(s[i], f[i], v[i], i + 1);
		Arrays.sort(a, (a1, a2) -> Integer.compare(a1.f, a2.f));
		for (int i = 0; i < n; i++) {
			s[i] = a[i].s;
			f[i] = a[i].f;
			v[i] = a[i].v;
			an[i] = a[i].n;
		}

		final int[] p = new int[n];
		int l = 0;
		p[0] = v[0];
		for (int k = 1; k < n; k++) {
			if (f[l] <= s[k]) {
				p[k] = p[k - 1] + v[k];
				l = k;
			} else {
				// Note that we need a closed interval here.
				final int c = BinarySearch.predecessor(f, s[k] + 1);
				int cv = 0;
				if (-1 < c)
					cv = p[c];
				if (p[k - 1] < cv + v[k]) {
					p[k] = cv + v[k];
					l = k;
				} else
					p[k] = p[k - 1];
			}
		}
		return p;
	}

	static String findActivities(int[] v, int[] p, int[] a) {
		final int n = v.length;
		int r = p[n - 1];
		final StringJoiner s = new StringJoiner(", ", "[", "]");
		for (int i = n - 2; -1 < i; i--) {
			if (p[i] < r) {
				s.add(String.valueOf(a[i + 1]));
				r = r - v[i + 1];
			}
		}
		if (0 < r)
			s.add(String.valueOf(1));
		return s.toString();
	}

	static class Activity {
		final int s;
		final int f;
		final int v;
		final int n;

		Activity(int s, int f, int v, int n) {
			this.s = s;
			this.f = f;
			this.v = v;
			this.n = n;
		}
	}
}
