package org.clrs.algorithms.dp;

public final class MatrixChainOrder {

	public static void main(String[] args) {
		final int[] p = new int[] { 30, 35, 15, 5, 10, 20, 25 };
		final Tables mAndS = matrixChainOrder(p);
		final int n = p.length - 1;
		System.out.println(String.format("Min number of scalar multiplications: %d", mAndS.m[1][n]));
		printOptimalParens(mAndS.s, 1, n);
	}

	private static void printOptimalParens(int[][] s, int i, int j) {
		if (i == j)
			System.out.print("A" + i);

		else {
			System.out.print("(");
			printOptimalParens(s, i, s[i][j]);
			printOptimalParens(s, s[i][j] + 1, j);
			System.out.print(")");
		}
	}

	private static Tables matrixChainOrder(int[] p) {
		final int n = p.length - 1;
		final int[][] m = new int[n + 1][n + 1];
		final int[][] s = new int[n][n + 1];

		for (int i = 1; i <= n; i++)
			m[i][i] = 0;

		// l is the chain length
		for (int l = 2; l <= n; l++) {
			for (int i = 1; i <= n - l + 1; i++) {
				final int j = i + l - 1;
				m[i][j] = Integer.MAX_VALUE;
				for (int k = i; k < j; k++) {
					final int q = m[i][k] + m[k + 1][j] + p[i - 1] * p[k] * p[j];
					if (q < m[i][j]) {
						m[i][j] = q;
						s[i][j] = k;
					}
				}
			}
		}
		return new Tables(m, s);
	}

	static final class Tables {
		final int[][] m;
		final int[][] s;

		public Tables(int[][] m, int[][] s) {
			super();
			this.m = m;
			this.s = s;
		}
	}
}
