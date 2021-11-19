package org.clrs.algorithms.dp;

class MemoizedLCS {
	MemoizedLCS() {
		throw new AssertionError();
	}

	public static void main(String[] args) {
		final String strandOne = "ACCGGTCGAGTGCGCGGAAGCCGGCCGAA";
		final String strandTwo = "GTCGTTCGGAATGCCGTTGCTCTGTAAA";
		final int[][] c = memoizedLcsLength(strandOne, strandTwo);
		System.out.println(String.format("The length of an LCS of two sequences is: %d",
				c[strandOne.length()][strandTwo.length()]));
		LCS.printLcs(c, strandOne, strandTwo, strandOne.length(), strandTwo.length());
	}

	private static int[][] memoizedLcsLength(String x, String y) {
		final int m = x.length();
		final int n = y.length();
		final int[][] c = new int[m + 1][n + 1];
		for (int i = 0; i <= m; i++) {
			for (int j = 0; j <= n; j++) {
				c[i][j] = -1;
			}
		}
		memoizedLcsLength(x, y, c, m, n);
		return c;
	}

	private static int memoizedLcsLength(String x, String y, int[][] c, int i, int j) {
		if (-1 < c[i][j])
			return c[i][j];
		if (i == 0 || j == 0)
			c[i][j] = 0;
		else if (x.charAt(i - 1) == y.charAt(j - 1))
			c[i][j] = memoizedLcsLength(x, y, c, i - 1, j - 1) + 1;
		else {
			final int l1 = memoizedLcsLength(x, y, c, i - 1, j);
			final int l2 = memoizedLcsLength(x, y, c, i, j - 1);
			c[i][j] = Math.max(l1, l2);
		}
		return c[i][j];
	}
}
