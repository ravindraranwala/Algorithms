package org.clrs.algorithms.greedy;

import java.util.Arrays;

class MaxPayoff {

	MaxPayoff() {
		throw new AssertionError();
	}

	public static void main(String[] args) {

	}

	static double payoff(int[] a, int[] b) {
		Arrays.sort(a);
		Arrays.sort(b);
		double p = 0;
		for (int i = 0; i < a.length; i++)
			p = p * Math.pow(a[i], b[i]);
		return p;
	}
}
