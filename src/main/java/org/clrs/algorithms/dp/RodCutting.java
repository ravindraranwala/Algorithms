package org.clrs.algorithms.dp;

public final class RodCutting {

	public static void main(String[] args) {
		double[] p = new double[] { 0, 1, 5, 8, 9, 10, 17, 17, 20, 24, 30 };
		printCutRodSolution(p, 7);
	}

	private static void printCutRodSolution(double[] p, int n) {
		final RevAndPieceLenTables revAndLenTables = bottomUpCutRod(p, n);
		System.out.println(String.format("Max revenue obtainable: %f", revAndLenTables.r[n]));
		while (n > 0) {
			System.out.println(revAndLenTables.s[n]);
			n = n - revAndLenTables.s[n];
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

	static final class RevAndPieceLenTables {
		final double[] r;
		final int[] s;

		public RevAndPieceLenTables(double[] r, int[] s) {
			super();
			this.r = r;
			this.s = s;
		}
	}
}
