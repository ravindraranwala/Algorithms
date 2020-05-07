package org.clrs.algorithms.dp;

public final class LongestPalindromeSubseq {

	public static void main(String[] args) {
		final String longestPalindSubseq = longestPalindromeSubseq("character");
		System.out.println(longestPalindSubseq);
	}

	private static String longestPalindromeSubseq(String s) {
		final int n = s.length();
		final int[][] start = new int[n][n];
		final int[][] end = new int[n][n];
		final int[][] length = new int[n][n];

		for (int i = 0; i < n; i++) {
			start[i][i] = i;
			end[i][i] = i;
			length[i][i] = 1;
		}

		for (int l = 2; l <= n; l++) {
			for (int i = 0; i < n - l + 1; i++) {
				final int j = i + l - 1;
				if (s.charAt(i) == s.charAt(j)) {
					start[i][j] = i;
					end[i][j] = j;
					length[i][j] = length[i + 1][j - 1] + 2;
				} else if (length[i + 1][j] >= length[i][j - 1]) {
					start[i][j] = start[i + 1][j];
					end[i][j] = end[i + 1][j];
					length[i][j] = length[i + 1][j];
				} else {
					start[i][j] = start[i][j - 1];
					end[i][j] = end[i][j - 1];
					length[i][j] = length[i][j - 1];
				}
			}
		}
		return constructOptimalSolution(start, end, length, s);
	}

	private static String constructOptimalSolution(int[][] start, int[][] end, int[][] length, String s) {
		return constructLongestPalindSubseq(start, end, length, 0, start.length - 1, s);
	}

	private static String constructLongestPalindSubseq(int[][] start, int[][] end, int[][] length, int i, int j,
			String s) {
		if (i > j)
			return "";
		if (i == j)
			return String.valueOf(s.charAt(start[i][j]));

		final StringBuilder longestPalindSubseq = new StringBuilder();
		final int startPos = start[i][j];
		final int endPos = end[i][j];
		if (s.charAt(startPos) == s.charAt(endPos)) {
			longestPalindSubseq.append(s.charAt(startPos));
			longestPalindSubseq.append(constructLongestPalindSubseq(start, end, length, startPos + 1, endPos - 1, s));
			longestPalindSubseq.append(s.charAt(endPos));
		} else if (length[i + 1][j] >= length[i][j - 1])
			longestPalindSubseq.append(constructLongestPalindSubseq(start, end, length, startPos + 1, endPos, s));
		else
			longestPalindSubseq.append(constructLongestPalindSubseq(start, end, length, startPos, endPos - 1, s));

		return longestPalindSubseq.toString();
	}
}
