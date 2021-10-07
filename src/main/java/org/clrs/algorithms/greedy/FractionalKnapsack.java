package org.clrs.algorithms.greedy;

import java.util.concurrent.ThreadLocalRandom;

class FractionalKnapsack {

	FractionalKnapsack() {
		throw new AssertionError();
	}

	public static void main(String[] args) {
		// Usecase 1.
		final int[] w = { 20, 10, 30 };
		final int[] v = { 100, 60, 120 };
		double p = knapsack(w, v, 50);
		System.out.println(p);

		// Usecase 2.
		final int[] w1 = { 40, 10, 20, 24 };
		final int[] v1 = { 280, 100, 120, 120 };
		p = knapsack(w1, v1, 60);
		System.out.println(p);

		// Usecase 3.
		p = knapsack(w, v, 60);
		System.out.println(p);

		p = knapsack(w, v, 100);
		System.out.println(p);

		// Usecase 4.
		p = knapsack(w, v, 0);
		System.out.println(p);

		// Usecase 5.
		// https://www.javatpoint.com/fractional-knapsack-problem
		final int w2[] = { 5, 10, 20, 30, 40 };
		final int v2[] = { 30, 20, 100, 90, 160 };
		p = knapsack(w2, v2, 60);
		System.out.println(p);

		// Usecase 6.
		// https://www.gatevidyalay.com/fractional-knapsack-problem-using-greedy-approach/
		final int[] w3 = { 5, 10, 15, 22, 25 };
		final int[] v3 = { 30, 40, 45, 77, 90 };
		p = knapsack(w3, v3, 60);
		System.out.println(p);

		// Usecase 7.
		final int[] w4 = { 50 };
		final int[] v4 = { 100 };
		p = knapsack(w4, v4, 60);
		System.out.println(p);

		p = knapsack(w4, v4, 20);
		System.out.println(p);

		p = knapsack(new int[0], new int[0], 20);
		System.out.println(p);

		p = knapsack(w, v, 5);
		System.out.println(p);

		p = knapsack(w, v, 0);
		System.out.println(p);
	}

	static double knapsack(int[] w, int[] v, int c) {
		final int n = w.length;
		final double[] d = new double[n];
		for (int i = 0; i < n; i++)
			d[i] = (double) v[i] / w[i];
		return knapsack(w, v, c, 0, n - 1, d);
	}

	static double knapsack(int[] w, int[] v, int c, int p, int r, double[] d) {
		if (r < p)
			return 0;
		final int[] k = randomizedPartition(w, v, d, p, r);
		final int q = k[0];
		if (c < k[1])
			return knapsack(w, v, c, q + 1, r, d);
		else if (k[1] + w[q] < c)
			return k[2] + v[q] + knapsack(w, v, c - k[1] - w[q], p, q - 1, d);
		else
			return k[2] + (double) (c - k[1]) / w[q] * v[q];

	}

	static int[] partition(int[] w, int[] v, double[] d, int p, int r) {
		final double x = d[r];
		int i = p - 1;
		int lw = 0;
		int lv = 0;
		int wSum = 0;
		int vSum = 0;
		for (int j = p; j < r; j++) {
			wSum = wSum + w[j];
			vSum = vSum + v[j];
			if (d[j] <= x) {
				i = i + 1;
				lw = lw + w[j];
				lv = lv + v[j];
				// exchange d[i] with d[j]
				final double tmpD = d[i];
				d[i] = d[j];
				d[j] = tmpD;
				// exchange w[i] with w[j]
				final int tmpW = w[i];
				w[i] = w[j];
				w[j] = tmpW;
				// exchange v[i] with v[j]
				final int tmpV = v[i];
				v[i] = v[j];
				v[j] = tmpV;
			}
		}
		// exchange d[i + 1] with d[r]
		final double rPivot = d[r];
		d[r] = d[i + 1];
		d[i + 1] = rPivot;

		// exchange w[i + 1] with w[r]
		final int wPivot = w[r];
		w[r] = w[i + 1];
		w[i + 1] = wPivot;

		// exchange v[i + 1] with v[r]
		final int vPivot = v[r];
		v[r] = v[i + 1];
		v[i + 1] = vPivot;

		final int[] res = { i + 1, wSum - lw, vSum - lv };
		return res;
	}

	static int[] randomizedPartition(int[] w, int[] v, double[] d, int p, int r) {
		final int i = ThreadLocalRandom.current().nextInt(p, r + 1);
		final double pivotD = d[r];
		d[r] = d[i];
		d[i] = pivotD;

		final int pivotW = w[r];
		w[r] = w[i];
		w[i] = pivotW;

		final int pivotV = v[r];
		v[r] = v[i];
		v[i] = pivotV;

		return partition(w, v, d, p, r);
	}
}
