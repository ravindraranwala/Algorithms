package org.clrs.algorithms.dp;

import org.clrs.algorithms.dp.RodCutting.RevAndPieceLenTables;

public class TopDownMemoizedCutRod {

	private TopDownMemoizedCutRod() {
		throw new AssertionError();
	}

	public static void main(String[] args) {
		final double[] p = { 0, 1, 5, 8, 9, 10, 17, 17, 20, 24, 30 };
		final int n = 7;
		final RevAndPieceLenTables tables = memoizedCutRod(p, n);
		RodCutting.printCutRodSolution(tables.r, tables.s, n);
		
	}

	public static RevAndPieceLenTables memoizedCutRod(double[] p, int n) {
		final double[] r = new double[n + 1];
		final int[] s = new int[n + 1];
		for (int i = 0; i <= n; i++)
			r[i] = Integer.MIN_VALUE;
		memoizedCutRodAux(p, n, r, s);
		return new RevAndPieceLenTables(r, s);
	}

	private static double memoizedCutRodAux(double[] p, int n, double[] r, int[] s) {
		if (r[n] >= 0)
			return r[n];
		double q = Integer.MIN_VALUE;
		if (n == 0)
			q = 0;
		else {
			for (int i = 1; i <= n; i++) {
				final double rev = memoizedCutRodAux(p, n - i, r, s);
				if (q < p[i] + rev) {
					q = p[i] + rev;
					s[n] = i;
				}
			}
		}
		r[n] = q;
		return q;
	}
}
