package org.clrs.algorithms.dc;

import java.util.Arrays;

public class MatrixMultiply {

	private MatrixMultiply() {
		throw new AssertionError();
	}

	public static void main(String[] args) {
		final int[][] a = new int[][] { { 1, 2, 3 }, { 4, 5, 6 } };
		final int[][] b = new int[][] { { 7, 8 }, { 9, 10 }, { 11, 12 } };
		int[][] c = matrixMultiply(a, b);
		System.out.println(Arrays.deepToString(c));

		final int[][] m1 = new int[][] { { 3, 4, 2 } };
		final int[][] m2 = new int[][] { { 13, 9, 7, 15 }, { 8, 7, 4, 6 }, { 6, 4, 0, 3 } };
		int[][] m3 = matrixMultiply(m1, m2);
		System.out.println(Arrays.deepToString(m3));

		final int[][] r = new int[][] { { 4 }, { 5 }, { 6 } };
		final int[][] s = new int[][] { { 1, 2, 3 } };
		int[][] t = matrixMultiply(r, s);
		System.out.println(Arrays.deepToString(t));
	}

	public static int[][] matrixMultiply(int[][] a, int[][] b) {
		if (a.length == 0 || b.length == 0)
			throw new IllegalArgumentException("Matrices should not be empty.");
		/*
		 * In order for matrix multiplication to be defined, the number of columns in
		 * the first matrix must be equal to the number of rows in the second matrix.
		 */
		if (a[0].length != b.length)
			throw new IllegalArgumentException(
					"the number of columns in the first matrix must be equal to the number of rows in the second matrix");

		final int m = a.length;
		final int n = b[0].length;
		final int[][] c = new int[m][n];

		// initialize the array.
		for (int p = 0; p < m; p++)
			for (int q = 0; q < n; q++)
				c[p][q] = 0;

		for (int i = 0; i < m; i++)
			for (int j = 0; j < n; j++)
				for (int k = 0; k < a[0].length; k++)
					c[i][j] += a[i][k] * b[k][j];

		return c;
	}
}
