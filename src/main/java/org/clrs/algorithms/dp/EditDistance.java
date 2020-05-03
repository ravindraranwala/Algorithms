package org.clrs.algorithms.dp;

import java.util.Arrays;

public final class EditDistance {
	public static void main(String[] args) {
		int minDist = editDistance("horse", "ros");
		System.out.println(String.format("Min Edit Distance: %d", minDist));
	}

	private static int editDistance(String x, String y) {
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
		System.out.println(Arrays.deepToString(c));
		System.out.println("Find LCS indices for both X and Y");
		final int lcsLen = c[x.length()][y.length()];
		int[] xLcsIdx = new int[lcsLen];
		int[] yLcsIdx = new int[lcsLen];
		findLcsIdx(c, x, y, x.length(), y.length(), xLcsIdx, yLcsIdx);
		System.out.println(Arrays.toString(xLcsIdx));
		System.out.println(Arrays.toString(yLcsIdx));
		return minDistance(c, x, y, x.length(), y.length(), xLcsIdx, yLcsIdx);
	}

	private static void findLcsIdx(int[][] c, String x, String y, int i, int j, int[] xLcsIdx, int[] yLcsIdx) {
		if (i == 0 || j == 0)
			return;
		else if (x.charAt(i - 1) == y.charAt(j - 1)) {
			final int currPos = c[i][j] - 1;
			xLcsIdx[currPos] = i;
			yLcsIdx[currPos] = j;
			findLcsIdx(c, x, y, i - 1, j - 1, xLcsIdx, yLcsIdx);
		} else if (c[i - 1][j] >= c[i][j - 1])
			findLcsIdx(c, x, y, i - 1, j, xLcsIdx, yLcsIdx);
		else
			findLcsIdx(c, x, y, i, j - 1, xLcsIdx, yLcsIdx);
	}

	private static int minDistance(int[][] c, String x, String y, int i, int j, int[] xLcsIdx, int[] yLcsIdx) {
		final int currPos = Math.max(c[i][j] - 1, 0);
		// Base case of our recursion.
		if (i == 0 || j == 0)
			return Math.max(j, i);
		// Copy task
		else if (i == xLcsIdx[currPos] && j == yLcsIdx[currPos])
			return minDistance(c, x, y, i - 1, j - 1, xLcsIdx, yLcsIdx);
		// Replace task
		else if (i != xLcsIdx[currPos] && j != yLcsIdx[currPos])
			return minDistance(c, x, y, i - 1, j - 1, xLcsIdx, yLcsIdx) + 1;
		// Delete task
		else if (i != xLcsIdx[currPos] && j == yLcsIdx[currPos])
			return minDistance(c, x, y, i - 1, j, xLcsIdx, yLcsIdx) + 1;
		// Insert task
		else
			return minDistance(c, x, y, i, j - 1, xLcsIdx, yLcsIdx) + 1;
	}

	static enum TRANSFORMATION {
		COPY, REPLACE, INSERT, DELETE;
	}
}
