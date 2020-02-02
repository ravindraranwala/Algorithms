package org.clrs.algorithms.dp;

public class OptimalBST {

	public static void main(String[] args) {
		final double[] p = new double[] { Double.NaN, 0.15, 0.10, 0.05, 0.1, 0.2 };
		final double[] q = new double[] { 0.05, 0.1, 0.05, 0.05, 0.05, 0.1 };
		final int n = 5;
		final Tables tables = optimalBst(p, q, n);
		System.out.println(String.format("Search cost of Optimal BST is: %s", tables.e[1][n]));
	}

	private static final Tables optimalBst(double[] p, double[] q, int n) {
		final double[][] e = new double[n + 2][n + 1];
		final double[][] w = new double[n + 2][n + 1];

		// eazy case of the recursion where j = i - 1 is handled first.
		for (int i = 1; i <= n + 1; i++) {
			e[i][i - 1] = q[i - 1];
			w[i][i - 1] = q[i - 1];
		}

		// Handle the case where j >= i in recursive formulation.
		// Bottom up recursion for each l = 1, 2, ..., n
		final int[][] root = new int[n + 1][n + 1];
		for (int l = 1; l <= n; l++) {
			for (int i = 1; i <= (n - l + 1); i++) {
				final int j = i + l - 1;
				e[i][j] = Double.MAX_VALUE; // set to infinity first.
				w[i][j] = w[i][j - 1] + p[j] + q[j];
				// split i, .., j at each r position and then find the optimal BST
				for (int r = i; r <= j; r++) {
					final double t = e[i][r - 1] + e[r + 1][j] + w[i][j];
					if (t < e[i][j]) {
						// We have found a better r to split the subtree ki, ..., kj
						e[i][j] = t;
						root[i][j] = r;
					}
				}
			}
		}

		return new Tables(e, root);
	}

	private static final void constructOptimalBst(int[][] root, int i, int j) {

	}

	static class Tables {
		final double[][] e;
		final int[][] root;

		public Tables(double[][] e, int[][] root) {
			super();
			this.e = e;
			this.root = root;
		}
	}
}
