package org.clrs.algorithms.dp;

import java.util.Arrays;

import org.clrs.algorithms.dp.MatrixChainOrder.Tables;

class MatrixChainMultiplication {

	MatrixChainMultiplication() {
		throw new AssertionError();
	}

	public static void main(String[] args) {
		final int[][] m1 = { { 1, -2 }, { 2, -1 } };
		final int[][] m2 = { { 1, 0, 2 }, { 3, -2, 1 } };
		final int[][] m3 = { { -1, 0 }, { 3, 1 }, { 2, 4 } };
		final int[] p = { 2, 2, 3, 2 };
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
		final int[][] a1 = matrixChainMultiply(s, i, s[i][j], matrices);
		final int[][] a2 = matrixChainMultiply(s, s[i][j] + 1, j, matrices);
		return matrixMultiply(a1, a2);
	}

	static int[][] matrixMultiply(int[][] a1, int[][] a2) {
		final int oneRows = a1.length;
		final int oneCols = a1[0].length;
		final int twoRows = a2.length;
		final int twoCols = a2[0].length;

		if (oneCols != twoRows)
			throw new IllegalStateException("Incompatible Matrices found !");

		// Perform the actual multiplication here.
		final int[][] c = new int[oneRows][twoCols];
		for (int i = 0; i < oneRows; i++) {
			for (int j = 0; j < twoCols; j++) {
				c[i][j] = 0;
				for (int k = 0; k < oneCols; k++)
					c[i][j] = c[i][j] + a1[i][k] * a2[k][j];
			}
		}
		return c;
	}
}
