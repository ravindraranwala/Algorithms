package org.clrs.algorithms.dp;

import java.util.Arrays;

class LIS {
	LIS() {
		throw new AssertionError();
	}

	public static void main(String[] args) {
		final int[] nums = { 3, 5, 6, 2, 5, 4, 19, 5, 6, 7, 12 };
		final int[] a = findLIS(nums);
		System.out.println(Arrays.toString(a));
	}

	static int[] findLIS(int[] nums) {
		final int n = nums.length;
		final int[] m = new int[n];
		int l = 0;
		for (int i = 0; i < n; i++) {
			final int s = successor(m, nums[i] - 1, 0, l - 1);
			if (s < l)
				m[s] = nums[i];
			else {
				m[l] = nums[i];
				l = l + 1;
			}
		}
		return Arrays.copyOfRange(m, 0, l);
	}

	static int successor(int[] nums, int target, int i, int j) {
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
}
