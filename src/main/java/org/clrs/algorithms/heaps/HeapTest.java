package org.clrs.algorithms.heaps;

import org.clrs.algorithms.dc.Student;

class HeapTest {
	HeapTest() {
		throw new AssertionError();
	}

	public static void main(String[] args) {
		final Queue<Integer> intQueue = PriorityQueue.of();
		intQueue.insert(4);
		intQueue.insert(2);
		intQueue.insert(7);
		intQueue.insert(5);

		while (!intQueue.isEmpty())
			System.out.println(intQueue.extract());

		final Student bloch = new Student("Bloch", 3.81);
		final Student jenny = new Student("Jenny", 2.51);
		final Student meyers = new Student("Meyers", 3.76);
		final Student stroustrup = new Student("Stroustrup", 3.94);
		final Student forrest = new Student("Forrest", 2.74);

		final Queue<Student> stdQueue = PriorityQueue.of((a, b) -> Double.compare(b.getGpa(), a.getGpa()));
		stdQueue.insert(meyers);
		stdQueue.insert(stroustrup);
		stdQueue.insert(jenny);
		stdQueue.insert(bloch);
		stdQueue.insert(forrest);

		while (!stdQueue.isEmpty())
			System.out.println(stdQueue.extract());
	}

}
