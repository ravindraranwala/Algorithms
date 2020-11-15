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
		int[][] c = matrixMultiply(a, b);
		System.out.println(Arrays.deepToString(c));
	}
	
	public static int[][] matrixMultiply(int[][] a, int[][] b) {
		return squareMatrixMultiply(a, 0, 0, b, 0, 0, a.length);
	}

	private static int[][] squareMatrixMultiply(int[][] a, int startRowA, int startColA, int[][] b, int startRowB,
			int startColB, int n) {
		final double exp = base2Exponent(n);
		if (exp % 1 != 0)
			throw new IllegalArgumentException(String.format("n = %d should be an exact power of 2", n));
		// Base case of the recursion.
		if (n == 1)
			return new int[][] { { a[startRowA][startColA] * b[startRowB][startColB] } };

		// partition A, B and C.
		final int mid = n / 2;
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
		final int[][] c22 = matrixSum(matrixSum(p5, p3, (x, y) -> x - y), matrixSum(p5, p7, (x, y) -> x - y),
				Integer::sum);

		final int[][] c = new int[n][n];
		matrixCopy(c11, c, 0, 0);
		matrixCopy(c12, c, 0, mid);
		matrixCopy(c21, c, mid, 0);
		matrixCopy(c22, c, mid, mid);
		return c;
	}

	private static double base2Exponent(int n) {
		return Math.log(n) / Math.log(2);
	}

	private static int[][] matrixSum(int[][] a, int startRowA, int startColA, int[][] b, int startRowB, int startColB,
			int n, IntBinaryOperator binOp) {
		final int[][] c = new int[n][n];
		// raw major order.
		for (int i = 0; i < a.length; i++)
			for (int j = 0; j < a[0].length; j++)
				c[i][j] = binOp.applyAsInt(a[startRowA + i][startColA + j], b[startRowB + i][startColB + j]);
		return c;
	}

	private static int[][] matrixSum(int[][] a, int[][] b, IntBinaryOperator binOp) {
		if (a.length != b.length && a[0].length != b[0].length)
			throw new IllegalArgumentException("Not conformable for addition, different orders/dimensions.");

		return matrixSum(a, 0, 0, b, 0, 0, a.length, binOp);
	}

	private static void matrixCopy(int[][] source, int[][] target, int startRow, int startCol) {
		for (int i = 0; i < source.length; i++)
			for (int j = 0; j < source[0].length; j++)
				target[startRow + i][startCol + j] = source[i][j];
	}
}
