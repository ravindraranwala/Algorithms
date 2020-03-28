package org.clrs.algorithms.rbt;

import static org.clrs.algorithms.rbt.RBT.TreeNode.Color.*;

public final class RBT<T extends Comparable<? super T>> {
	private final TreeNode<T> nil;
	private TreeNode<T> root;

	public RBT() {
		nil = new TreeNode<>(null);
	}

	public static void main(String[] args) {
		RBT<Integer> rbt = new RBT<>();
	}

	public void insert(T key) {
		rbInsert(new TreeNode<>(key));
	}

	private void rbInsert(TreeNode<T> z) {
		TreeNode<T> y = nil;
		TreeNode<T> x = root;
		while (x != nil) {
			y = x;
			if (z.key.compareTo(x.key) < 0)
				x = x.left;
			else
				x = x.right;
		}
		z.p = y;
		if (y == nil)
			root = z;
		else if (z.key.compareTo(y.key) < 0)
			y.left = z;
		else
			y.right = z;

		z.left = nil;
		z.right = nil;
		z.color = RED;
		rbInsertFixup(z);
	}

	private void rbInsertFixup(TreeNode<T> z) {
		while (z.p.color == RED) {
			if (z.p == z.p.p.left) {
				final TreeNode<T> y = z.p.p.right;
				if (y.color == RED) {
					// case 1
					z.p.color = BLACK;
					y.color = BLACK;
					z.p.p.color = RED;
					z = z.p.p;
				} else {
					if (z == z.p.right) {
						// case 2
						z = z.p;
						leftRotate(z);
					}
					// case 3
					z.p.color = BLACK;
					z.p.p.color = RED;
					rightRotate(z.p.p);
				}
			} else {
				// Note this is symmetric to the previous one.
				final TreeNode<T> y = z.p.p.left;
				if (y.color == RED) {
					// case 1
					z.p.color = BLACK;
					y.color = BLACK;
					z.p.p.color = RED;
					z = z.p.p;
				} else {
					// case 2
					if (z == z.p.right) {
						z = z.p;
						leftRotate(z);
					}
					// case 3
					z.p.color = BLACK;
					z.p.p.color = RED;
					rightRotate(z.p.p);
				}
			}
		}
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

	static class TreeNode<S extends Comparable<? super S>> {
		private final S key;
		private TreeNode<S> left;
		private TreeNode<S> right;
		private TreeNode<S> p;
		private Color color;

		TreeNode(S key) {
			super();
			this.key = key;
		}

		public TreeNode(S key, Color color) {
			super();
			this.key = key;
			this.color = color;
		}

		static enum Color {
			RED, BLACK;
		}
	}
}
