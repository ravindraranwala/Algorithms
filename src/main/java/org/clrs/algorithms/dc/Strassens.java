package org.clrs.algorithms.dc;

import java.util.Arrays;
import java.util.function.IntBinaryOperator;

public class Strassens {
	private Strassens() {
		throw new AssertionError();
	}

	public static void main(String[] args) {
		int[][] a = { { 1, 3 }, { 7, 5 } };
		int[][] b = { { 6, 8 }, { 4, 2 } };
		int[][] c = squareMatrixMultiply(a, b);
		System.out.println(Arrays.deepToString(c));

		int[][] d = { { 3, 1, 1, 4 }, { 5, 3, 2, 1 } };
		int[][] e = { { 4, 9 }, { 6, 8 }, { 9, 7 }, { 7, 6 } };
		final int n = 2;
		int[][] m1 = squareMatrixMultiply(d, 0, 0, e, 0, 0, n);
		int[][] m2 = squareMatrixMultiply(d, 0, n, e, n, 0, n);
		int[][] m3 = matrixSum(m1, m2, Integer::sum);
		System.out.println(Arrays.deepToString(m3));

		/*
		 * How would you modify Strassen’s algorithm to multiply n x n matrices in which
		 * n is not an exact power of 2?
		 */
		final int[][] f = { { 2, 7, 3 }, { 1, 5, 8 }, { 0, 4, 1 } };
		final int[][] g = { { 3, 0, 1 }, { 2, 1, 0 }, { 1, 2, 4 } };
		final int[][] m4 = squareMatrixMultiply(f, g);
		System.out.println(Arrays.deepToString(m4));
	}

	public static int[][] squareMatrixMultiply(int[][] a, int[][] b) {
		int n = a.length;
		if (n == 0 || n != a[0].length || n != b.length || a[0].length != b[0].length)
			throw new IllegalArgumentException(
					"Not conformable for multiplication, different dimensions or empty matrices provided.");

		if (isExactPowerOf2(n)) {
			return squareMatrixMultiply(a, 0, 0, b, 0, 0, n);
		} else {
			final int m = (int) Math.pow(2, Math.ceil(base2Exponent(n)));
			final int[][] paddedA = new int[m][m];
			final int[][] paddedB = new int[m][m];
			matrixCopy(a, paddedA, 0, 0);
			matrixCopy(b, paddedB, 0, 0);
			int[][] paddedC = squareMatrixMultiply(paddedA, 0, 0, paddedB, 0, 0, m);
			final int[][] c = new int[n][n];
			matrixCopy(paddedC, c, 0, 0, n);
			return c;
		}
	}

	private static int[][] squareMatrixMultiply(int[][] a, int startRowA, int startColA, int[][] b, int startRowB,
			int startColB, int side) {
		if (!isExactPowerOf2(side))
			throw new IllegalArgumentException(String.format("n = %d should be an exact power of 2", side));
		// Base case of the recursion.
		if (side == 1)
			return new int[][] { { a[startRowA][startColA] * b[startRowB][startColB] } };

		// partition A, B and C.
		final int mid = side / 2;
		final int[][] s1 = matrixSum(b, startRowB, startColB + mid, b, startRowB + mid, startColB + mid, mid,
				(x, y) -> x - y);
		final int[][] s2 = matrixSum(a, startRowA, startColA, a, startRowA, startColA + mid, mid, Integer::sum);
		final int[][] s3 = matrixSum(a, startRowA + mid, startColA, a, startRowA + mid, startColA + mid, mid,
				Integer::sum);
		final int[][] s4 = matrixSum(b, startRowB + mid, startColB, b, startRowB, startColB, mid, (x, y) -> x - y);
		final int[][] s5 = matrixSum(a, startRowA, startColA, a, startRowA + mid, startColA + mid, mid, Integer::sum);
		final int[][] s6 = matrixSum(b, startRowB, startColB, b, startRowB + mid, startColB + mid, mid, Integer::sum);
		final int[][] s7 = matrixSum(a, startRowA, startColA + mid, a, startRowA + mid, startColA + mid, mid,
				(x, y) -> x - y);
		final int[][] s8 = matrixSum(b, startRowB + mid, startColB, b, startRowB + mid, startColB + mid, mid,
				Integer::sum);
		final int[][] s9 = matrixSum(a, startRowA, startColA, a, startRowA + mid, startColA, mid, (x, y) -> x - y);
		final int[][] s10 = matrixSum(b, startRowB, startColB, b, startRowB, startColB + mid, mid, Integer::sum);

		// recursive calls.
		final int[][] p1 = squareMatrixMultiply(a, startRowA, startColA, s1, 0, 0, mid);
		final int[][] p2 = squareMatrixMultiply(s2, 0, 0, b, startRowB + mid, startColB + mid, mid);
		final int[][] p3 = squareMatrixMultiply(s3, 0, 0, b, startRowB, startColB, mid);
		final int[][] p4 = squareMatrixMultiply(a, startRowA + mid, startColA + mid, s4, 0, 0, mid);
		final int[][] p5 = squareMatrixMultiply(s5, 0, 0, s6, 0, 0, mid);
		final int[][] p6 = squareMatrixMultiply(s7, 0, 0, s8, 0, 0, mid);
		final int[][] p7 = squareMatrixMultiply(s9, 0, 0, s10, 0, 0, mid);

		final int[][] c11 = matrixSum(matrixSum(p4, p5, Integer::sum), matrixSum(p6, p2, (x, y) -> x - y),
				Integer::sum);
		final int[][] c12 = matrixSum(p1, p2, Integer::sum);
		final int[][] c21 = matrixSum(p3, p4, Integer::sum);
		final int[][] c22 = matrixSum(matrixSum(p5, p3, (x, y) -> x - y), matrixSum(p1, p7, (x, y) -> x - y),
				Integer::sum);

		final int[][] c = new int[side][side];
		matrixCopy(c11, c, 0, 0);
		matrixCopy(c12, c, 0, mid);
		matrixCopy(c21, c, mid, 0);
		matrixCopy(c22, c, mid, mid);
		return c;
	}

	private static boolean isExactPowerOf2(int n) {
		return base2Exponent(n) % 1 == 0;
	}

	private static double base2Exponent(int n) {
		return Math.log(n) / Math.log(2);
	}

	private static int[][] matrixSum(int[][] a, int startRowA, int startColA, int[][] b, int startRowB, int startColB,
			int side, IntBinaryOperator binOp) {
		final int[][] c = new int[side][side];
		// raw major order.
		for (int i = 0; i < side; i++)
			for (int j = 0; j < side; j++)
				c[i][j] = binOp.applyAsInt(a[startRowA + i][startColA + j], b[startRowB + i][startColB + j]);
		return c;
	}

	private static int[][] matrixSum(int[][] a, int[][] b, IntBinaryOperator binOp) {
		if (a.length != b.length && a[0].length != b[0].length)
			throw new IllegalArgumentException("Not conformable for addition, different orders/dimensions.");

		return matrixSum(a, 0, 0, b, 0, 0, a.length, binOp);
	}

	private static void matrixCopy(int[][] source, int[][] target, int startRow, int startCol) {
		matrixCopy(source, target, startRow, startCol, source.length);
	}

	private static void matrixCopy(int[][] source, int[][] target, int startRow, int startCol, int side) {
		if (side > target.length)
			throw new IllegalArgumentException("Target matrix is too smaller than number of elements to be copied !");

		for (int i = 0; i < side; i++)
			for (int j = 0; j < side; j++)
				target[startRow + i][startCol + j] = source[i][j];
	}
}
