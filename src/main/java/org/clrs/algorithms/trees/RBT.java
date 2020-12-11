package org.clrs.algorithms.trees;

import static org.clrs.algorithms.trees.RBT.RBTreeNode.Color.BLACK;
import static org.clrs.algorithms.trees.RBT.RBTreeNode.Color.RED;

import java.util.Comparator;

import org.clrs.algorithms.trees.AbstractBST.TreeNode;
import org.clrs.algorithms.trees.RBT.RBTreeNode;
import org.clrs.algorithms.trees.RBT.RBTreeNode.Color;

public class RBT<E> extends AbstractBST<E, RBTreeNode<E>> {
	private final RBTreeNode<E> nil;

	private RBT(Comparator<? super E> comparator) {
		super(comparator, new RBTreeNode<>(null, BLACK));
		nil = sentinel;
		root = nil;
	}

	public static <T> RBT<T> of(Comparator<? super T> comparator) {
		return new RBT<>(comparator);
	}

	public static <T extends Comparable<? super T>> RBT<T> of() {
		return new RBT<>(Comparator.naturalOrder());
	}

	public static void main(String[] args) {
		// Sample driver code obtained from the CLRS text book chapter exercises.
		final RBT<Integer> rbt = RBT.of();
		rbt.insert(41);
		rbt.insert(38);
		rbt.insert(31);
		rbt.insert(12);
		rbt.insert(19);
		rbt.insert(8);
		rbt.print();

		rbt.delete(8);
		rbt.delete(12);
		rbt.print();
		rbt.delete(19);
		rbt.delete(31);
		rbt.delete(38);
		rbt.delete(41);
	}

	@Override
	public void insert(E key) {
		rbInsert(new RBTreeNode<>(key));
	}

	private void rbInsert(RBTreeNode<E> z) {
		RBTreeNode<E> y = nil;
		RBTreeNode<E> x = root;
		while (x != nil) {
			y = x;
			if (comparator.compare(z.key, x.key) < 0)
				x = x.left;
			else
				x = x.right;
		}
		z.p = y;
		if (y == nil)
			root = z;
		else if (comparator.compare(z.key, y.key) < 0)
			y.left = z;
		else
			y.right = z;

		z.left = nil;
		z.right = nil;
		z.color = RED;
		rbInsertFixup(z);
	}

	private void rbInsertFixup(RBTreeNode<E> z) {
		while (z.p.color == RED) {
			if (z.p == z.p.p.left) {
				final RBTreeNode<E> y = z.p.p.right;
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
				final RBTreeNode<E> y = z.p.p.left;
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

	private void leftRotate(RBTreeNode<E> x) {
		final RBTreeNode<E> y = x.right; // set y
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

	private void rightRotate(RBTreeNode<E> y) {
		final RBTreeNode<E> x = y.left;
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

	private void rbTransplant(final RBTreeNode<E> u, final RBTreeNode<E> v) {
		if (u.p == nil)
			root = v;
		else if (u == u.p.left)
			u.p.left = v;
		else
			u.p.right = v;
		v.p = u.p;
	}

	@Override
	public void delete(E key) {
		final RBTreeNode<E> nodeToDelete = iterativeTreeSearch(key);
		if (nodeToDelete == nil)
			throw new IllegalArgumentException("Invalid key: " + key);
		rbDelete(nodeToDelete);
	}

	private void rbDelete(final RBTreeNode<E> z) {
		RBTreeNode<E> y = z;
		Color yOriginalColor = y.color;
		RBTreeNode<E> x;
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
	private void rbDeleteFixup(RBTreeNode<E> x) {
		while (x != root && x.color == BLACK) {
			if (x == x.p.left) {
				RBTreeNode<E> w = x.p.right;
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
				RBTreeNode<E> w = x.p.left;
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

	@Override
	public void print() {
		if (root == nil)
			System.out.println(String.format("%s is the %s color root", "NIL", root.color));
		else {
			System.out.println(String.format("%d is the %s color root", root.key, root.color));
			printSubtree(root);
		}
	}

	private void printSubtree(RBTreeNode<E> node) {
		final RBTreeNode<E> left = node.left;
		if (left != nil) {
			System.out
					.println(String.format("%d is the left child of %d with color %s", left.key, node.key, left.color));
			printSubtree(left);
		}
		final RBTreeNode<E> right = node.right;
		if (right != nil) {
			System.out.println(
					String.format("%d is the right child of %d with color %s", right.key, node.key, right.color));
			printSubtree(right);
		}

	}

	static class RBTreeNode<T> extends TreeNode<T, RBTreeNode<T>> {
		Color color;

		RBTreeNode(T key) {
			super(key);
		}

		RBTreeNode(T key, Color color) {
			this(key);
			this.color = color;
		}

		static enum Color {
			RED, BLACK;
		}
	}
}
