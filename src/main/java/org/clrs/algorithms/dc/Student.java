package org.clrs.algorithms.dc;

public class Student {
	private final String name;
	private final double gpa;

	Student(String name, double gpa) {
		super();
		this.name = name;
		this.gpa = gpa;
	}

	public String getName() {
		return name;
	}

	public double getGpa() {
		return gpa;
	}

	@Override
	public String toString() {
		return String.format("{ name= %s , gpa=%.2f }", name, gpa);
	}
}
