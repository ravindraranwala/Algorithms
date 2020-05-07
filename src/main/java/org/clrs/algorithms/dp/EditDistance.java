package org.clrs.algorithms.dp;

import static org.clrs.algorithms.dp.EditDistance.TRANSFORMATION.*;

public final class EditDistance {
	public static void main(String[] args) {
		// int minDist = editDistance("mart", "karma");
		// sample data used to test (ABCDE, ABDE), (, a), (ISLANDER, SLANDER), (KITTEN,
		// SITTING), (INTENTION, EXECUTION), (horse, ros) and
		// (AGGCTATCACCTGACCTCCAGGCCGATGCCC, TAGCTATCACGACCGCGGTCGATTTGCCCGAC)
		String x = "AGGCTATCACCTGACCTCCAGGCCGATGCCC";
		String y = "TAGCTATCACGACCGCGGTCGATTTGCCCGAC";
		int[][] c = editDistance(x, y);
		int minDist = c[x.length()][y.length()];
		System.out.println(String.format("Min Edit Distance: %d", minDist));
		StringBuilder alignOne = new StringBuilder();
		StringBuilder alignTwo = new StringBuilder();
		printAlignment(c, x, y, x.length(), y.length(), alignOne, alignTwo);
		System.out.println();
		System.out.println(alignOne);
		System.out.println(alignTwo);
	}

	private static int[][] editDistance(String x, String y) {
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
		return c;
	}

	private static void printAlignment(int[][] c, String x, String y, int i, int j, StringBuilder alignOne,
			StringBuilder alignTwo) {
		// Base case of our recursion
		if (i == 0 && j == 0)
			return;

		int minCost = Integer.MAX_VALUE, iPrev = -1, jPrev = -1;
		TRANSFORMATION t = null;
		/*
		 * Note that COPY should be given highest priority and treated first when
		 * confronted with ties, since it is the most optimal operation.
		 */
		// COPY or REPLACE operation
		if (i > 0 && j > 0) {
			iPrev = i - 1;
			jPrev = j - 1;
			minCost = c[i - 1][j - 1];
			if (x.charAt(i - 1) == y.charAt(j - 1))
				t = COPY;

			else
				t = REPLACE;
		}
		// Assume DELETE is the optimal operation
		if (i > 0 && c[i - 1][j] < minCost) {
			iPrev = i - 1;
			jPrev = j;
			minCost = c[i - 1][j];
			t = DELETE;
		}
		// INSERT is the optimal operation so far
		if (j > 0 && c[i][j - 1] < minCost) {
			iPrev = i;
			jPrev = j - 1;
			minCost = c[i][j - 1];
			t = INSERT;
		}

		printAlignment(c, x, y, iPrev, jPrev, alignOne, alignTwo);
		final char nullCh = '\0';
		t.apply(alignOne, alignTwo, i > 0 ? x.charAt(i - 1) : nullCh, j > 0 ? y.charAt(j - 1) : nullCh);
		System.out.print(t + (i == x.length() && j == y.length() ? "" : ", "));
	}

	static enum TRANSFORMATION {
		COPY {
			@Override
			void apply(StringBuilder alignOne, StringBuilder alignTwo, char source, char target) {
				alignOne.append(source);
				alignTwo.append(target);
			}
		},
		REPLACE {
			@Override
			void apply(StringBuilder alignOne, StringBuilder alignTwo, char source, char target) {
				alignOne.append(source);
				alignTwo.append(target);
			}
		},
		INSERT {
			@Override
			void apply(StringBuilder alignOne, StringBuilder alignTwo, char source, char target) {
				alignOne.append(PLACE_HOLDER);
				alignTwo.append(target);
			}
		},
		DELETE {
			@Override
			void apply(StringBuilder alignOne, StringBuilder alignTwo, char source, char target) {
				alignOne.append(source);
				alignTwo.append(PLACE_HOLDER);
			}
		};

		private static final char PLACE_HOLDER = '_';

		abstract void apply(StringBuilder alignOne, StringBuilder alignTwo, char source, char target);
	}
}
