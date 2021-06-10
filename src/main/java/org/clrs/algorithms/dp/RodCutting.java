package org.clrs.algorithms.dp;

public class RodCutting {
	private RodCutting() {
		throw new AssertionError();
	}

	public static void main(String[] args) {
		final double[] p = { 0, 1, 5, 8, 9, 10, 17, 17, 20, 24, 30 };
		final int n = 7;
		final RevAndPieceLenTables tabs = bottomUpCutRod(p, n);
		printCutRodSolution(tabs, n);
	}

	static void printCutRodSolution(RevAndPieceLenTables tabs, int n) {
		System.out.println(String.format("Max revenue obtainable: %f", tabs.r[n]));
		while (n > 0) {
			System.out.println(tabs.s[n]);
			n = n - tabs.s[n];
		}
	}

	private static RevAndPieceLenTables bottomUpCutRod(double[] p, int n) {
		final double[] r = new double[n + 1];
		final int[] s = new int[n + 1];
		r[0] = 0;

		for (int j = 1; j <= n; j++) {
			double q = -1;
			for (int i = 1; i <= j; i++) {
				if (p[i] + r[j - i] > q) {
					q = p[i] + r[j - i];
					s[j] = i;
				}
			}
			r[j] = q;
		}
		return new RevAndPieceLenTables(r, s);
	}

	static class RevAndPieceLenTables {
		final double[] r;
		final int[] s;

		RevAndPieceLenTables(double[] r, int[] s) {
			super();
			this.r = r;
			this.s = s;
		}
	}
}
