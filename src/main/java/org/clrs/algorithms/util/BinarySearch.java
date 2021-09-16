package org.clrs.algorithms.util;

public class BinarySearch {

	BinarySearch() {
		throw new AssertionError();
	}

	public static void main(String[] args) {
		final int[] nums = { -1, 0, 3, 5, 9, 12 };
		final int target = 9;
		final int idx = search(nums, target);
		System.out.println(idx);
		int i = successor(nums, 1);
		System.out.println(nums[i]);
	}

	/**
	 * Given an array of integers nums which is sorted in ascending order, and an
	 * integer target, this function searches target in nums. If target exists, then
	 * return its index. Otherwise, return -1.
	 * 
	 * @param nums   an array of integers
	 * @param target integer target
	 * @return index of the target if it exists in the array. Otherwise, return -1
	 */
	public static int search(int[] nums, int target) {
		int l = 0;
		int r = nums.length - 1;
		while (l <= r) {
			final int m = (l + r) / 2;
			if (nums[m] < target)
				l = m + 1;
			else if (nums[m] > target)
				r = m - 1;
			else
				return m;
		}
		return -1;
	}

	public static int successor(int[] nums, int target) {
		return successor(nums, target, 0, nums.length - 1);
	}

	public static int successor(int[] nums, int target, int i, int j) {
		int l = i;
		int r = j + 1;
		while (l < r) {
			final int mid = (l + r) / 2;
			if (nums[mid] > target)
				r = mid;
			else
				l = mid + 1;
		}
		return r;
	}

	public static int predecessor(int[] a, int target) {
		int l = 0;
		int r = a.length;
		while (l < r) {
			final int mid = (l + r) / 2;
			if (a[mid] < target)
				l = mid + 1;
			else
				r = mid;
		}
		return l - 1;
	}
}
