package org.clrs.algorithms.bst;

public final class BST {
	TreeNode root = null;

	public BST(TreeNode root) {
		this.root = root;
	}

	public static void main(String[] args) {

	}

	public static void treeDelete(BST t, TreeNode z) {
		if (z.left == null)
			transplant(t, z, z.right);
		else if (z.right == null)
			transplant(t, z, z.left);
		else {
			TreeNode y = treeMinimum(z.right);
			if (y.p != z) {
				transplant(t, y, y.right);
				y.right = z.right;
				y.right.p = y;
			}
			transplant(t, z, y);
			y.left = z.left;
			y.left.p = y;
		}
	}

	public static void transplant(BST t, TreeNode u, TreeNode v) {
		if (u.p == null)
			t.root = v;
		else if (u.p.left == u)
			u.p.left = v;
		else
			u.p.right = v;

		if (v != null)
			v.p = u.p;
	}

	public static void treeInsert(BST t, TreeNode z) {
		TreeNode y = null;
		TreeNode x = t.root;

		while (x != null) {
			y = x;
			if (z.key < x.key)
				x = x.left;
			else
				x = x.right;
		}

		z.p = y;
		if (y == null)
			t.root = z; // tree T was empty
		else if (z.key < y.key)
			y.left = z;
		else
			y.right = z;
	}

	public static TreeNode treeSuccessor(TreeNode x) {
		if (x.right != null)
			return treeMinimum(x.right);

		TreeNode y = x.p;
		while (y != null && y.right == x) {
			x = y;
			y = y.p;
		}
		return y;
	}

	public static TreeNode treeMinimum(TreeNode x) {
		while (x.left != null)
			x = x.left;
		return x;
	}

	public static TreeNode treeMaximum(TreeNode x) {
		while (x.right != null)
			x = x.right;
		return x;
	}

	public static TreeNode iterativeTreeSearch(TreeNode x, int k) {
		while (x != null && x.key != k) {
			if (k < x.key)
				x = x.left;
			else
				x = x.right;
		}
		return x;
	}

	public static TreeNode treeSearch(TreeNode x, int k) {
		if (x == null || k == x.key)
			return x;
		if (k < x.key)
			return treeSearch(x.left, k);
		else
			return treeSearch(x.right, k);
	}

	public static void inorderTreeWalk(TreeNode x) {
		if (x != null) {
			inorderTreeWalk(x.left);
			System.out.print(x.key);
			inorderTreeWalk(x.right);
		}
	}

	static class TreeNode {
		int key;
		TreeNode left;
		TreeNode right;
		TreeNode p;

		TreeNode(int x) {
			key = x;
		}
	}
}
