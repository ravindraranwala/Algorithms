package org.clrs.algorithms.dp;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import static org.clrs.algorithms.dp.EditDistance.TRANSFORMATION.*;

public final class EditDistance {
	private static final String SPACE = " ";

	public static void main(String[] args) {
		// findEditDistance("bc", "abcd");
		// findEditDistance("intention", "execution");
		// findEditDistance("intention", "transformation");
		// findEditDistance("intention", "reputation");
		findEditDistance("abcd", "ecd");
	}

	private static void findEditDistance(String x, String y) {
		final int m = x.length();
		final int n = y.length();
		final int l = Math.max(m, n);
		final String[] transformations = new String[l];
		int spaces = n - m;
		int cost = 0;

		for (int i = 0, j = 0, k = 0; k < l; k++) {
			if (i < m && j < n) {
				if (x.charAt(i) == y.charAt(j)) {
					cost += costOf(COPY);
					transformations[k] = COPY + SPACE + x.charAt(i);
					j++;
					i++;
					continue;
				}
				if (spaces == 0) {
					cost += costOf(REPLACE);
					transformations[k] = REPLACE + SPACE + y.charAt(j);
					i++;
					j++;
					continue;
				}
			}

			if (spaces > 0) {
				// Insert case.
				cost += costOf(INSERT);
				transformations[k] = INSERT + SPACE + y.charAt(j);
				j++;
				spaces--;
			} else {
				// Delete case
				cost += costOf(DELETE);
				transformations[k] = DELETE + SPACE + x.charAt(i);
				i++;
				spaces++;
			}
		}
		System.out.println(String.format("Edit distance: %d", cost));
		System.out.println(Arrays.toString(transformations));
	}

	static enum TRANSFORMATION {
		COPY(-1), REPLACE(1), INSERT(2), DELETE(2);

		private static final Map<TRANSFORMATION, Integer> transformationCost = new HashMap<>();

		static {
			for (TRANSFORMATION transformation : values())
				transformationCost.put(transformation, transformation.cost);
		}

		private final int cost;

		private TRANSFORMATION(int cost) {
			this.cost = cost;
		}

		public static int costOf(TRANSFORMATION t) {
			return transformationCost.get(t);
		}
	}
}
