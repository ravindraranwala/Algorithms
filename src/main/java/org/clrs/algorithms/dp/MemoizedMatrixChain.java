package org.clrs.algorithms.dp;

// Natural top-down recursion with Memoization.
public class MemoizedMatrixChain {

	public static void main(String[] args) {
		final int[] p = new int[] { 30, 35, 15, 5, 10, 20, 25 };
		int minCost = memoizedMatrixChain(p);
		System.out.println(String.format("Min number of scalar multiplications: %d", minCost));
	}

	private static int memoizedMatrixChain(int[] p) {
		final int n = p.length - 1;
		final int[][] m = new int[n + 1][n + 1];
		for (int i = 1; i <= n; i++)
			for (int j = i; j <= n; j++)
				m[i][j] = Integer.MAX_VALUE;
		return lookupChain(m, p, 1, n);
	}

	private static int lookupChain(int[][] m, int[] p, int i, int j) {
		if (m[i][j] < Integer.MAX_VALUE)
			return m[i][j];
		if (i == j)
			m[i][j] = 0; // trivial case of the recursion.
		else {
			// non-trivial case
			for (int k = i; k < j; k++) {
				final int q = lookupChain(m, p, i, k) + lookupChain(m, p, k + 1, j) + p[i - 1] * p[k] * p[j];
				if (q < m[i][j])
					m[i][j] = q;
			}
		}
		return m[i][j];
	}
}
