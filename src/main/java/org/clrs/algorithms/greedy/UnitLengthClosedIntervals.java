package org.clrs.algorithms.greedy;

import java.util.LinkedHashSet;
import java.util.Set;

class UnitLengthClosedIntervals {

	UnitLengthClosedIntervals() {
		throw new AssertionError();
	}

	public static void main(String[] args) {
		// Usecase 1.
		final double[] nl = { 0.5, 1.4, 1.55, 1.6, 2.5 };
		final Set<Interval> s1 = minUnitLengthClosedIntervals(nl, 1);
		System.out.println(s1);

		// Usecase 2.
		final double[] nl2 = { 0.7, 1.0, 1.5, 2.0, 2.3, 2.6, 3.1, 3.6, 3.9, 4.2, 4.7, 5.2, 5.5 };
		final Set<Interval> s2 = minUnitLengthClosedIntervals(nl2, 1);
		System.out.println(s2);
	}

	static Set<Interval> minUnitLengthClosedIntervals(double[] p, int u) {
		final Set<Interval> s = new LinkedHashSet<>();
		double l = p[0];
		for (int i = 0; i < p.length; i++) {
			if (l + u < p[i]) {
				s.add(new Interval(l, l + u));
				l = p[i];
			}
		}
		s.add(new Interval(l, l + u));
		return s;
	}

	static class Interval {
		final double a;
		final double b;

		Interval(double a, double b) {
			this.a = a;
			this.b = b;
		}

		@Override
		public String toString() {
			return "[" + a + ", " + b + "]";
		}

	}
}
