package org.clrs.algorithms.dp;

import java.util.Arrays;
import java.util.StringJoiner;

class Knapsack {

	Knapsack() {
		throw new AssertionError();
	}

	public static void main(String[] args) {
		// Usecase 1.
		final int[] value = { 20, 5, 10, 40, 15, 25 };
		final int[] weight = { 1, 2, 3, 8, 7, 4 };
		final int c = 10;
		final int[][] k = knapsack(value, weight, c);
		System.out.println(String.format("Knapsack value is: %d", k[value.length][c]));
		findItems(k, weight);

		// Usecase 2.
		final int[] v1 = { 10, 40, 30, 50 };
		final int[] w1 = { 5, 4, 6, 3 };
		final int c1 = 10;
		final int[][] k1 = knapsack(v1, w1, c1);
		System.out.println(String.format("Knapsack value is: %d", k1[v1.length][c1]));
		System.out.println(Arrays.deepToString(k1));
		findItems(k1, w1);

		// Usecase 3.
		final int[] w2 = { 25, 35, 45, 5, 25, 3, 2, 2 };
		final int[] v2 = { 350, 400, 450, 20, 70, 8, 5, 5 };
		final int c2 = 104;
		final int[][] k2 = knapsack(v2, w2, c2);
		System.out.println(String.format("Knapsack value is: %d", k2[v2.length][c2]));
		findItems(k2, w2);

		// Usecase 4.
		final int[] w3 = { 41, 50, 49, 59, 55, 57, 60 };
		final int[] v3 = { 442, 525, 511, 593, 546, 564, 617 };
		final int c3 = 170;
		final int[][] k3 = knapsack(v3, w3, c3);
		System.out.println(String.format("Knapsack value is: %d", k3[v3.length][c3]));
		findItems(k3, w3);

		// Usecase 5.
		final int[] w4 = { 70, 73, 77, 80, 82, 87, 90, 94, 98, 106, 110, 113, 115, 118, 120 };
		final int[] v4 = { 135, 139, 149, 150, 156, 163, 173, 184, 192, 201, 210, 214, 221, 229, 240 };
		final int c4 = 750;
		final int[][] k4 = knapsack(v4, w4, c4);
		System.out.println(String.format("Knapsack value is: %d", k4[v4.length][c4]));
		findItems(k4, w4);
	}

	static int[][] knapsack(int[] v, int[] w, int c) {
		final int n = v.length;
		final int[][] k = new int[n + 1][c + 1];

		for (int i = 0; i <= n; i++)
			k[i][0] = 0;
		for (int j = 1; j <= c; j++)
			k[0][j] = 0;

		for (int i = 1; i <= n; i++) {
			for (int j = 1; j <= c; j++) {
				k[i][j] = k[i - 1][j];
				if (w[i - 1] <= j)
					k[i][j] = Math.max(v[i - 1] + k[i - 1][j - w[i - 1]], k[i - 1][j]);
			}
		}
		return k;
	}

	static void findItems(int[][] k, int[] w) {
		int j = k[1].length - 1;
		int i = w.length;
		final StringJoiner r = new StringJoiner(", ", "[", "]");
		while (k[i][j] != 0) {
			if (k[i][j] > k[i - 1][j]) {
				r.add(i + "");
				j = j - w[i - 1];
			}
			i = i - 1;
		}
		System.out.println(r.toString());
	}
}
