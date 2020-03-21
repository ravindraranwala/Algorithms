package org.clrs.algorithms.rbt;

public final class RBT<T extends Comparable<T>> {
	private final TreeNode<T> nil;
	private TreeNode<T> root;

	public RBT() {
		nil = new TreeNode<>(null);
	}

	public static void main(String[] args) {
		RBT<Integer> rbt = new RBT<>();
	}

	private void leftRotate(TreeNode<T> x) {
		final TreeNode<T> y = x.right; // set y
		// turn y's left subtree into x's right subtree
		x.right = y.left;
		if (y.left != nil)
			y.left.p = x;
		y.p = x.p; // link x's parent to y
		if (x.p == nil)
			root = y;
		else if (x.p.left == x)
			x.p.left = y;
		else
			x.p.right = y;
		y.left = x; // put x on y's left
		x.p = y;
	}

	private void rightRotate(TreeNode<T> y) {
		final TreeNode<T> x = y.left;
		y.left = x.right;
		if (x.right != nil)
			x.right.p = y;
		x.p = y.p;
		if (y.p == nil)
			root = x;
		else if (y.p.left == y)
			y.p.left = x;
		else
			y.p.right = x;
		x.right = y;
		y.p = x;
	}

	static class TreeNode<T extends Comparable<T>> {
		private final T key;
		private TreeNode<T> left;
		private TreeNode<T> right;
		private TreeNode<T> p;
		private Color color;

		TreeNode(T key) {
			super();
			this.key = key;
		}

		public TreeNode(T key, Color color) {
			super();
			this.key = key;
			this.color = color;
		}

		static enum Color {
			RED, BLACK;
		}
	}
}
