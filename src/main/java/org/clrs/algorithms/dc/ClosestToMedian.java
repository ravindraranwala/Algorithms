package org.clrs.algorithms.dc;

import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;

class ClosestToMedian {
	ClosestToMedian() {
		throw new AssertionError();
	}

	public static void main(String[] args) {
		final int[] numsOne = { 9, 1, 8, 4, 10, 5, 11, 2, 3 };
		final int[] closestNums = closestPointsToMedian(numsOne, 4);
		System.out.println(Arrays.toString(closestNums));
	}

	static int[] closestPointsToMedian(int[] s, int k) {
		final int n = s.length;
		final int mid = (n + 1) / 2;
		final int m = QuickSelect.randomizedSelect(s, 0, n - 1, mid);
		randomizedSelect(s, 0, n - 1, m, k);
		return Arrays.copyOf(s, k);
	}

	// This solution is incorrect, since the answers are not guaranteed to be unique
	// (except for the order that it is in.)
	static int[] closestNumbersToMedian(int[] s, int k) {
		final int n = s.length;
		final int mid = (n + 1) / 2;
		final int m = QuickSelect.randomizedSelect(s, 0, n - 1, mid);
		final int[] d = new int[n];
		for (int i = 0; i < n; i++)
			d[i] = Math.abs(s[i] - m);

		final int kthClosestDistance = QuickSelect.randomizedSelect(d, 0, n - 1, k);
		final int[] nums = new int[k];
		for (int i = 0, j = 0; i < n; i++) {
			if (Math.abs(s[i] - m) <= kthClosestDistance) {
				System.out.println(s[i]);
				nums[j] = s[i];
				j = j + 1;
			}
		}
		return nums;
	}

	static int randomizedSelect(int[] a, int p, int r, int i, int m) {
		if (p == r)
			return a[p];
		final int q = randomizedPartition(a, p, r, m);
		final int k = q - p + 1;
		if (i == k)
			return a[q];
		else if (i < k)
			return randomizedSelect(a, p, q - 1, i, m);
		else
			return randomizedSelect(a, q + 1, r, i - k, m);
	}

	private static int randomizedPartition(int[] a, int p, int r, int m) {
		final int i = ThreadLocalRandom.current().nextInt(p, r);
		exchange(a, i, r);
		return partition(a, p, r, m);
	}

	private static int partition(int[] a, int p, int r, int m) {
		final int x = Math.abs(a[r] - m);
		int i = p - 1;
		for (int j = p; j < r; j++) {
			if (Math.abs(a[j] - m) <= x) {
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
