package org.clrs.algorithms.dc;

class FindQuantiles {
	FindQuantiles() {
		throw new AssertionError();
	}

	public static void main(String[] args) {
		final int[] a = { 5, 13, 6, 14, 4, 9, 19, 11, 12, 15, 20, 1, 8, 18, 10, 16, 22, 7, 3, 2, 17, 23, 21 };
		final int k = 7;
		findQuantiles(a, k);
	}

	static void findQuantiles(int[] s, int k) {
		final int n = s.length;
		findQuantilesAux(s, 0, n - 1, k);
	}

	static void findQuantilesAux(int[] s, int i, int j, int k) {
		// trivial case of the recursion.
		if (k == 1)
			return;

		final int n = j - i + 1;
		final int k1 = k / 2;
		final int l = n * k1 / k; // we get the floor by default.
		// find lth smallest element in the input set s.
		final int p = QuickSelect.randomizedSelect(s, i, j, l);
		// solve left subproblem recursively.
		findQuantilesAux(s, i, i + l - 1, k1);
		// print current solution.
		System.out.println(p);
		// solve right subproblem recursively.
		findQuantilesAux(s, i + l, j, k - k1);
	}
}
