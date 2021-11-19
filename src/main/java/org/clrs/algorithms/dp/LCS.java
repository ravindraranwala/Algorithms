package org.clrs.algorithms.dp;

class LCS {
	LCS() {
		throw new AssertionError();
	}

	public static void main(String[] args) {
//		final String seqOne = "ABCBDAB";
//		final String seqTwo = "BDCABA";

		final String strandOne = "ACCGGTCGAGTGCGCGGAAGCCGGCCGAA";
		final String strandTwo = "GTCGTTCGGAATGCCGTTGCTCTGTAAA";
		final int[][] c = lcsLength(strandOne, strandTwo);
		System.out.println(String.format("The length of an LCS of two sequences is: %d",
				c[strandOne.length()][strandTwo.length()]));
		printLcs(c, strandOne, strandTwo, strandOne.length(), strandTwo.length());
	}

	static void printLcs(int[][] c, String x, String y, int i, int j) {
		if (c[i][j] == 0)
			System.out.print("");
		else if (x.charAt(i - 1) == y.charAt(j - 1)) {
			printLcs(c, x, y, i - 1, j - 1);
			System.out.print(x.charAt(i - 1));
		} else if (c[i - 1][j] >= c[i][j - 1])
			printLcs(c, x, y, i - 1, j);
		else
			printLcs(c, x, y, i, j - 1);

	}

	private static int[][] lcsLength(String x, String y) {
		final int m = x.length();
		final int n = y.length();
		final int[][] c = new int[m + 1][n + 1];

		for (int i = 1; i <= m; i++)
			c[i][0] = 0;
		for (int j = 0; j <= n; j++)
			c[0][j] = 0;

		for (int i = 1; i <= m; i++) {
			for (int j = 1; j <= n; j++) {
				if (x.charAt(i - 1) == y.charAt(j - 1))
					c[i][j] = c[i - 1][j - 1] + 1;
				else if (c[i - 1][j] >= c[i][j - 1])
					c[i][j] = c[i - 1][j];
				else
					c[i][j] = c[i][j - 1];
			}
		}
		return c;
	}
}
