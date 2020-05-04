package org.clrs.algorithms.dp;

public final class EditDistance {
	public static void main(String[] args) {
		int minDist = editDistance("intention", "execution");
		System.out.println(String.format("Min Edit Distance: %d", minDist));
	}

	private static int editDistance(String x, String y) {
		final int m = x.length();
		final int n = y.length();
		final int[][] c = new int[m + 1][n + 1];

		for (int i = 1; i <= m; i++)
			c[i][0] = i;
		for (int j = 0; j <= n; j++)
			c[0][j] = j;

		for (int i = 1; i <= m; i++) {
			for (int j = 1; j <= n; j++) {
				// Copy task
				if (x.charAt(i - 1) == y.charAt(j - 1))
					c[i][j] = c[i - 1][j - 1];
				else
					// Replace, Insert and Delete tasks are considered here.
					c[i][j] = Math.min(Math.min(c[i - 1][j], c[i][j - 1]), c[i - 1][j - 1]) + 1;
			}
		}
		return c[m][n];
	}

	static enum TRANSFORMATION {
		COPY, REPLACE, INSERT, DELETE;
	}
}
