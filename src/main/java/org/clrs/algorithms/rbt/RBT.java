package org.clrs.algorithms.rbt;

import static org.clrs.algorithms.rbt.RBT.TreeNode.Color.BLACK;
import static org.clrs.algorithms.rbt.RBT.TreeNode.Color.RED;

import org.clrs.algorithms.rbt.RBT.TreeNode.Color;

public final class RBT<T extends Comparable<? super T>> {
	private final TreeNode<T> nil;
	private TreeNode<T> root;

	public RBT() {
		nil = new TreeNode<>(null, BLACK);
		root = nil;
	}

	public static void main(String[] args) {
		// sample driver code obtained from this site:
		// https://www.geeksforgeeks.org/red-black-tree-set-3-delete-2/
		final RBT<Integer> rbt = new RBT<>();
		rbt.insert(7);
		rbt.insert(3);
		rbt.insert(18);
		rbt.insert(10);
		rbt.insert(22);
		rbt.insert(8);
		rbt.insert(11);
		rbt.insert(26);
		rbt.insert(2);
		rbt.insert(6);
		rbt.insert(13);

		// rbt.print();

		rbt.delete(18);
		rbt.delete(11);
		rbt.delete(3);
		rbt.delete(10);
		rbt.delete(22);

		rbt.print();
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
					if (z == z.p.left) {
						// case 2
						z = z.p;
						rightRotate(z);
					}
					// case 3
					z.p.color = BLACK;
					z.p.p.color = RED;
					leftRotate(z.p.p);
				}
			}
		}
		root.color = BLACK;
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

	private void rbTransplant(final TreeNode<T> u, final TreeNode<T> v) {
		if (u.p == nil)
			root = v;
		else if (u == u.p.left)
			u.p.left = v;
		else
			u.p.right = v;
		v.p = u.p;
	}

	public void delete(T key) {
		final TreeNode<T> nodeToDelete = iterativeTreeSearch(key);
		if (nodeToDelete == nil)
			throw new IllegalArgumentException("Invalid key: " + key);
		rbDelete(nodeToDelete);
	}

	private TreeNode<T> iterativeTreeSearch(T k) {
		TreeNode<T> x = root;
		while (x != nil && !x.key.equals(k)) {
			if (k.compareTo(x.key) < 0)
				x = x.left;
			else
				x = x.right;
		}
		return x;
	}

	private void rbDelete(final TreeNode<T> z) {
		TreeNode<T> y = z;
		Color yOriginalColor = y.color;
		TreeNode<T> x;
		if (z.left == nil) {
			x = z.right;
			rbTransplant(z, z.right);
		} else if (z.right == nil) {
			x = z.left;
			rbTransplant(z, z.left);
		} else {
			y = treeMinimum(z.right);
			yOriginalColor = y.color;
			x = y.right;
			if (y.p == z)
				x.p = y;
			else {
				rbTransplant(y, y.right);
				y.right = z.right;
				y.right.p = y;
			}
			rbTransplant(z, y);
			y.left = z.left;
			y.left.p = y;
			y.color = z.color;
		}
		if (yOriginalColor == BLACK)
			rbDeleteFixup(x); // Fix any violations of Red-Black properties.
	}

	// Restores red-black properties.
	private void rbDeleteFixup(TreeNode<T> x) {
		while (x != root && x.color == BLACK) {
			if (x == x.p.left) {
				TreeNode<T> w = x.p.right;
				if (w.color == RED) {
					// case 1
					w.color = BLACK;
					x.p.color = RED;
					leftRotate(x.p);
					w = x.p.right;
				}
				if (w.left.color == BLACK && w.right.color == BLACK) {
					// case 2
					w.color = RED;
					x = x.p;
				} else {
					if (w.right.color == BLACK) {
						// case 3: w's left child is red
						w.left.color = BLACK;
						w.color = RED;
						rightRotate(w);
						w = x.p.right;
					}
					// case 4: w's right child is red
					w.color = x.p.color;
					x.p.color = BLACK;
					w.right.color = BLACK;
					leftRotate(x.p);
					x = root;
				}
			} else {
				// symmetric to the then clause.
				TreeNode<T> w = x.p.left;
				if (w.color == RED) {
					// case 1
					w.color = BLACK;
					x.p.color = RED;
					rightRotate(x.p);
					w = x.p.left;
				}
				if (w.left.color == BLACK && w.right.color == BLACK) {
					w.color = RED;
					x = x.p;
				} else {
					if (w.left.color == BLACK) {
						w.right.color = BLACK;
						w.color = RED;
						leftRotate(w);
						w = x.p.left;
					}
					w.color = x.p.color;
					x.p.color = BLACK;
					w.left.color = BLACK;
					rightRotate(x.p);
					x = root;
				}
			}
		}
		x.color = BLACK;
	}

	private TreeNode<T> treeMinimum(TreeNode<T> x) {
		while (x.left != nil)
			x = x.left;
		return x;
	}

	public void print() {
		System.out.println(String.format("%d is the %s color root", root.key, root.color));
		printSubtree(root);
	}

	private void printSubtree(TreeNode<T> node) {
		final TreeNode<T> left = node.left;
		if (left != nil) {
			System.out
					.println(String.format("%d is the left child of %d with color %s", left.key, node.key, left.color));
			printSubtree(left);
		}
		final TreeNode<T> right = node.right;
		if (right != nil) {
			System.out.println(
					String.format("%d is the right child of %d with color %s", right.key, node.key, right.color));
			printSubtree(right);
		}

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
