package org.clrs.algorithms.dc;

import java.util.concurrent.ThreadLocalRandom;

class QuickSelect {
	QuickSelect() {
		throw new AssertionError();
	}

	static int randomizedSelect(int[] a, int p, int r, int i) {
		if (p == r)
			return a[p];
		final int q = randomizedPartition(a, p, r);
		final int k = q - p + 1;
		if (i == k)
			return a[q];
		else if (i < k)
			return randomizedSelect(a, p, q - 1, i);
		else
			return randomizedSelect(a, q + 1, r, i - k);
	}

	private static int randomizedPartition(int[] a, int p, int r) {
		final int i = ThreadLocalRandom.current().nextInt(p, r);
		exchange(a, i, r);
		return partition(a, p, r);
	}

	private static int partition(int[] a, int p, int r) {
		final int x = a[r];
		int i = p - 1;
		for (int j = p; j < r; j++) {
			if (a[j] <= x) {
				i = i + 1;
				exchange(a, i, j);
			}
		}
		exchange(a, i + 1, r);
		return i + 1;
	}

	private static void exchange(int[] a, int i, int j) {
		final int tmp = a[i];
		a[i] = a[j];
		a[j] = tmp;
	}
}
