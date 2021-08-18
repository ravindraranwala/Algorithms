package org.clrs.algorithms.dp;

import static org.clrs.algorithms.dp.EditDistance.Operation.COPY;
import static org.clrs.algorithms.dp.EditDistance.Operation.DELETE;
import static org.clrs.algorithms.dp.EditDistance.Operation.INSERT;
import static org.clrs.algorithms.dp.EditDistance.Operation.REPLACE;

import java.util.StringJoiner;

class EditDistance {
	private static final char PLACE_HOLDER = '_';

	EditDistance() {
		throw new AssertionError();
	}

	public static void main(String[] args) {
		final String x = "AGGCTATCACCTGACCTCCAGGCCGATGCCC";
		final String y = "TAGCTATCACGACCGCGGTCGATTTGCCCGAC";
		final int[][] d = minDistance(x, y);
		final int minDist = d[x.length()][y.length()];
		System.out.println(String.format("Min Edit Distance: %d", minDist));
		final StringBuilder a1 = new StringBuilder();
		final StringBuilder a2 = new StringBuilder();
		final StringJoiner s = new StringJoiner(", ");
		constructAlignment(d, x, y, a1, a2, s, x.length(), y.length());
		System.out.println(a1);
		System.out.println(a2);
		System.out.println(s);
	}

	static int[][] minDistance(String x, String y) {
		final int m = x.length();
		final int n = y.length();
		final int[][] d = new int[m + 1][n + 1];

		for (int i = 0; i <= m; i++)
			d[i][0] = i;
		for (int j = 1; j <= n; j++)
			d[0][j] = j;

		for (int i = 1; i <= m; i++) {
			for (int j = 1; j <= n; j++) {
				final int rep = d[i - 1][j - 1] + REPLACE.cost;
				final int del = d[i - 1][j] + DELETE.cost;
				final int ins = d[i][j - 1] + INSERT.cost;
				if (x.charAt(i - 1) == y.charAt(j - 1))
					d[i][j] = d[i - 1][j - 1] + COPY.cost;
				else if (rep <= del && rep <= ins)
					d[i][j] = rep;
				else if (ins <= del)
					d[i][j] = ins;
				else
					d[i][j] = del;
			}
		}
		return d;
	}

	static void constructAlignment(int[][] d, String x, String y, StringBuilder a1, StringBuilder a2, StringJoiner s,
			int i, int j) {
		if (i == 0 && j == 0)
			return;
		if (i > 0 && j > 0 && d[i - 1][j - 1] <= d[i - 1][j] && d[i - 1][j - 1] <= d[i][j - 1]) {
			constructAlignment(d, x, y, a1, a2, s, i - 1, j - 1);
			a1.append(x.charAt(i - 1));
			a2.append(y.charAt(j - 1));
			if (x.charAt(i - 1) == y.charAt(j - 1))
				s.add(COPY.toString());
			else
				s.add(REPLACE.toString());
		} else if (j == 0 || (i > 0 && d[i - 1][j] < d[i][j - 1])) {
			constructAlignment(d, x, y, a1, a2, s, i - 1, j);
			a1.append(x.charAt(i - 1));
			a2.append(PLACE_HOLDER);
			s.add(DELETE.toString());
		} else {
			constructAlignment(d, x, y, a1, a2, s, i, j - 1);
			a1.append(PLACE_HOLDER);
			a2.append(y.charAt(j - 1));
			s.add(INSERT.toString());
		}
	}

	static enum Operation {
		COPY(0), REPLACE(1), INSERT(1), DELETE(1);
		final int cost;

		Operation(int cost) {
			this.cost = cost;
		}
	}
}
