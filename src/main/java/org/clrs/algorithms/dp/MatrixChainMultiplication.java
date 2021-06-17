package org.clrs.algorithms.dp;

import java.util.Arrays;

import org.clrs.algorithms.dp.MatrixChainOrder.Tables;

public class MatrixChainMultiplication {

	private MatrixChainMultiplication() {
		throw new AssertionError();
	}

	public static void main(String[] args) {
		final int[][] m1 = { { 6, 3 } };
		final int[][] m2 = { { 2, -3, 2 }, { 6, -5, 1 } };
		final int[][] m3 = { { -6, 2 }, { -3, 5 }, { 4, 5 } };
		final int[] p = { 1, 2, 3, 2 };
		final Tables mAndS = MatrixChainOrder.matrixChainOrder(p);
		final int n = p.length - 1;
		System.out.println(String.format("Min number of scalar multiplications: %d", mAndS.m[1][n]));
		MatrixChainOrder.printOptimalParens(mAndS.s, 1, n);
		System.out.println();

		final int[][] r = matrixChainMultiply(mAndS.s, 1, n, m1, m2, m3);
		System.out.println(Arrays.deepToString(r));
	}

	static int[][] matrixChainMultiply(int[][] s, int i, int j, int[][]... matrices) {
		// trivial case of the recursion.
		if (i == j)
			return matrices[i - 1];
		final int[][] mOne = matrixChainMultiply(s, i, s[i][j], matrices);
		final int[][] mTwo = matrixChainMultiply(s, s[i][j] + 1, j, matrices);

		final int oneRows = mOne.length;
		final int oneCols = mOne[0].length;
		final int twoRows = mTwo.length;
		final int twoCols = mTwo[0].length;

		if (oneCols != twoRows)
			throw new IllegalStateException("Incompatible Matrices found !");

		// Perform the actual multiplication here.
		final int[][] c = new int[oneRows][twoCols];
		for (int p = 0; p < oneRows; p++) {
			for (int q = 0; q < twoCols; q++) {
				int sum = 0;
				for (int k = 0; k < oneCols; k++)
					sum += mOne[p][k] * mTwo[k][q];
				c[p][q] = sum;
			}
		}
		return c;
	}
}
