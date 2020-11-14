package org.clrs.algorithms.dc;

import java.util.Arrays;
import java.util.function.IntBinaryOperator;

public class Strassens {

	public static void main(String[] args) {
		int[][] a = { { 2, 5 }, { -1, 3 } };
		int[][] b = { { 1, 4 }, { 3, 7 } };
		int[][] c = new int[2][2];
		matrixSum(a, b, c, 0, 0, Integer::sum);
		System.out.println(Arrays.deepToString(c));
	}

	private static void matrixSum(int[][] a, int startRowA, int startColA, int[][] b, int startRowB, int startColB, int[][] c, int startRowC, int startColC, int m, int n,
			IntBinaryOperator binOp) {
		// raw major order.
		for (int i = 0; i < a.length; i++)
			for (int j = 0; j < a[0].length; j++)
				c[startRowC + i][startColC + j] = binOp.applyAsInt(a[startRowA + i][startColA +j], b[startRowB + i][startColB + j]);
	}

	private static int[][] matrixSum(int[][] a, int startRowA, int startColA, int[][] b, int startRowB, int startColB, int n, IntBinaryOperator binOp) {
		final int[][] c = new int[n][n];
		matrixSum(a, startRowA, startColA, b, startRowB, startColB, c, 0, 0, n, n, binOp);
		return c;
	}
	
	private static void matrixSum(int[][] a, int[][] b, int[][] c, int startRowC, int startColC, IntBinaryOperator binOp) {
		if (a.length != b.length && a[0].length != b[0].length)
			throw new IllegalArgumentException("Not conformable for addition, different orders/dimensions.");
		
		matrixSum(a, 0, 0, b, 0, 0, c, startRowC, startColC, a.length, a[0].length, binOp);
	}
}
