package org.clrs.algorithms.dp;

import static org.clrs.algorithms.dp.EditDistance.TRANSFORMATION.COPY;
import static org.clrs.algorithms.dp.EditDistance.TRANSFORMATION.DELETE;
import static org.clrs.algorithms.dp.EditDistance.TRANSFORMATION.INSERT;
import static org.clrs.algorithms.dp.EditDistance.TRANSFORMATION.REPLACE;

public final class EditDistance {
	private static final String COMMA = ", ";
	public static void main(String[] args) {
		// int minDist = editDistance("mart", "karma");
		// sample data used to test (ABCDE, ABDE), (, a), (ISLANDER, SLANDER), (KITTEN,
		// SITTING), (INTENTION, EXECUTION), (horse, ros) and
		// (AGGCTATCACCTGACCTCCAGGCCGATGCCC, TAGCTATCACGACCGCGGTCGATTTGCCCGAC)
		String x = "mart";
		String y = "karma";
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

		TRANSFORMATION t = null;
		int iPrev = -1, jPrev = -1;
		// COPY operation
		if (i > 0 && j > 0 && x.charAt(i - 1) == y.charAt(j - 1)) {
			iPrev = i - 1;
			jPrev = j - 1;
			t = COPY;
		} else {
			int minCost = Integer.MAX_VALUE;
			// DELETE operation
			if (i > 0 && c[i - 1][j] < minCost) {
				iPrev = i - 1;
				jPrev = j;
				minCost = c[i - 1][j];
				t = DELETE;
			}
			// INSERT operation
			if (j > 0 && c[i][j - 1] < minCost) {
				iPrev = i;
				jPrev = j - 1;
				minCost = c[i][j - 1];
				t = INSERT;
			}
			// REPLACE operation
			if (i > 0 && j > 0 && c[i - 1][j - 1] < minCost) {
				iPrev = i - 1;
				jPrev = j - 1;
				t = REPLACE;
			}
		}

		printAlignment(c, x, y, iPrev, jPrev, alignOne, alignTwo);
		final char nullCh = '\0';
		t.apply(alignOne, alignTwo, i > 0 ? x.charAt(i - 1) : nullCh, j > 0 ? y.charAt(j - 1) : nullCh);
		System.out.print(t);
		if(i != x.length() || j != y.length())
			System.out.print(COMMA);
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
