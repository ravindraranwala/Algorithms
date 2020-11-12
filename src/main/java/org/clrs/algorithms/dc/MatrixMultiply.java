package org.clrs.algorithms.dc;

import java.util.Arrays;

public class MatrixMultiply {

	private MatrixMultiply() {
		throw new AssertionError();
	}

	public static void main(String[] args) {
		final int[][] a = { { 1, 2, 3 }, { 4, 5, 6 } };
		final int[][] b = { { 7, 8 }, { 9, 10 }, { 11, 12 } };
		int[][] c = matrixMultiply(a, b);
		System.out.println(Arrays.deepToString(c));

		final int[][] m1 = { { 3, 4, 2 } };
		final int[][] m2 = { { 13, 9, 7, 15 }, { 8, 7, 4, 6 }, { 6, 4, 0, 3 } };
		int[][] m3 = matrixMultiply(m1, m2);
		System.out.println(Arrays.deepToString(m3));

		final int[][] r = { { 4 }, { 5 }, { 6 } };
		final int[][] s = { { 1, 2, 3 } };
		int[][] t = matrixMultiply(r, s);
		System.out.println(Arrays.deepToString(t));

		final int[][] m4 = new int[][] { { 5, 8 }, { 3, 8 } };
		final int[][] m5 = new int[][] { { 3, 8 }, { 8, 9 } };
		int[][] m6 = squareMatrixMulRec(m4, m5);
		System.out.println(Arrays.deepToString(m6));

		final int[][] m7 = new int[][] { { 5, 7, 9, 10 }, { 2, 3, 3, 8 }, { 8, 10, 2, 3 }, { 3, 3, 4, 8 } };
		final int[][] m8 = new int[][] { { 3, 10, 12, 18 }, { 12, 1, 4, 9 }, { 9, 10, 12, 2 }, { 3, 12, 4, 10 } };
		int[][] m9 = squareMatrixMulRec(m7, m8);
		System.out.println(Arrays.deepToString(m9));
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

	private static void squareMatrixMultiplyRecursive(int[][] a, int[][] b, int startRowA, int startColA, int startRowB,
			int startColB, int n, int[][] c) {
		if (n == 1)
			// Note the combine step here.
			c[startRowA][startColB] += a[startRowA][startColA] * b[startRowB][startColB];
		else {
			// partition A, B and C.
			final int mid = n / 2;
			squareMatrixMultiplyRecursive(a, b, startRowA, startColA, startRowB, startColB, mid, c);
			squareMatrixMultiplyRecursive(a, b, startRowA, startColA + mid, startRowB + mid, startColB, mid, c);

			squareMatrixMultiplyRecursive(a, b, startRowA, startColA, startRowB, startColB + mid, mid, c);
			squareMatrixMultiplyRecursive(a, b, startRowA, startColA + mid, startRowB + mid, startColB + mid, mid, c);

			squareMatrixMultiplyRecursive(a, b, startRowA + mid, startColA, startRowB, startColB, mid, c);
			squareMatrixMultiplyRecursive(a, b, startRowA + mid, startColA + mid, startRowB + mid, startColB, mid, c);

			squareMatrixMultiplyRecursive(a, b, startRowA + mid, startColA, startRowB, startColB + mid, mid, c);
			squareMatrixMultiplyRecursive(a, b, startRowA + mid, startColA + mid, startRowB + mid, startColB + mid, mid,
					c);
		}
	}

	public static int[][] squareMatrixMulRec(int[][] a, int[][] b) {
		final int n = a.length;
		final int[][] c = new int[n][n];
		// initialize the matrix to zero.
		for (int i = 0; i < n; i++)
			for (int j = 0; j < n; j++)
				c[i][j] = 0;
		squareMatrixMultiplyRecursive(a, b, 0, 0, 0, 0, n, c);
		return c;
	}
}
