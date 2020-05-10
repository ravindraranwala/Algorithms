package org.clrs.algorithms.dc;

public final class MaxSubArr {

	public static void main(String[] args) {
		final int[] delta = new int[] { 13, -3, -25, 20, -3, -16, -23, 18, 20, -7, 12, -5, -22, 15, -4, 7 };
		int[] result = findMaxSubArr(delta, 0, delta.length - 1);
		System.out.println(
				String.format("Low index: %d, High index: %d, Max profit per share $: %d", result[0], result[1], result[2]));
	}

	private static int[] findMaxSubArr(int[] a, int low, int high) {
		if (low == high)
			return new int[] { low, high, a[low] }; // base case, only one element
		else {
			final int mid = (low + high) / 2;
			final int[] left = findMaxSubArr(a, low, mid);
			final int[] right = findMaxSubArr(a, mid + 1, high);
			final int[] crossing = findMaxCrossingSubArr(a, low, mid, high);
			if (left[2] >= right[2] && left[2] >= crossing[2])
				return left;
			else if (right[2] >= left[2] && right[2] >= crossing[2])
				return right;
			else
				return crossing;
		}
	}

	private static int[] findMaxCrossingSubArr(int[] a, int low, int mid, int high) {
		int leftSum = Integer.MIN_VALUE;
		int maxLeft = -1;
		int sum = 0;
		for (int i = mid; i >= low; i--) {
			sum = sum + a[i];
			if (sum > leftSum) {
				leftSum = sum;
				maxLeft = i;
			}
		}

		sum = 0;
		int maxRight = -1;
		int rightSum = Integer.MIN_VALUE;
		for (int j = mid + 1; j <= high; j++) {
			sum += a[j];
			if (sum > rightSum) {
				rightSum = sum;
				maxRight = j;
			}
		}

		return new int[] { maxLeft, maxRight, leftSum + rightSum };
	}
}
