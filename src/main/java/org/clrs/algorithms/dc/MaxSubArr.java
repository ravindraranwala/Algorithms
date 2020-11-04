package org.clrs.algorithms.dc;

public final class MaxSubArr {

	public static void main(String[] args) {
		final int[] change = new int[] { 0, 13, -3, -25, 20, -3, -16, -23, 18, 20, -7, 12, -5, -22, 15, -4, 7 };
		int[] result = findMaxSubArr(change, 0, change.length - 1);
		System.out.println(String.format("Low index: %d, High index: %d, Max profit per share $: %d", result[0],
				result[1], result[2]));
		final int[] price = new int[] { 100, 113, 110, 85, 105, 102, 86, 63, 81, 101, 94, 106, 101, 79, 94, 90, 97 };
		result = findMaxSubArrBF(price);
		System.out.println(String.format("Buy at the EOD: %d, Sell at the EOD %d, Max profit $: %d", result[0], result[1],
				result[2]));
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

	private static int[] findMaxSubArrBF(int[] a) {
		int maxProfit = Integer.MIN_VALUE;
		int maxI = 0;
		int maxJ = 0;
		final int n = a.length;

		for (int l = 2; l <= n; l++) {
			for (int i = 0; i <= (n - l); i++) {
				final int j = i + l - 1;
				final int currProfit = a[j] - a[i];
				if (currProfit > maxProfit) {
					maxProfit = currProfit;
					maxI = i;
					maxJ = j;
				}
			}
		}
		return new int[] { maxI, maxJ, maxProfit };
	}
}
