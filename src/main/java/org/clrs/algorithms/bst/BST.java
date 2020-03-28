package org.clrs.algorithms.bst;

import java.util.Iterator;
import java.util.NoSuchElementException;

public final class BST<T extends Comparable<? super T>> implements Iterable<T> {
	private TreeNode<T> root = null;

	public static void main(String[] args) {
		final BST<Integer> bst = new BST<>();
		bst.insert(4);
		bst.insert(2);
		bst.insert(7);
		bst.insert(1);
		bst.insert(5);
		System.out.println("Min value of the tree: " + bst.min());
		System.out.println("Max value of the tree: " + bst.max());
		System.out.println("Successor of 5 is: " + bst.successor(5));
		System.out.println(String.format("Node with key %d found: %b", 2, bst.search(2)));
		System.out.println(String.format("Node with key %d found: %b", 3, bst.search(3)));
		bst.print();
		bst.delete(7);
		bst.insert(8);
		System.out.println();
		bst.print();
		System.out.println();
		for (final Iterator<Integer> it = bst.iterator(); it.hasNext();) {
			Integer val = it.next();
			if (val == 5)
				it.remove();
			System.out.println(val);
		}
		bst.print();
	}

	public void delete(T key) {
		final TreeNode<T> nodeToDelete = iterativeTreeSearch(key);
		if (nodeToDelete == null)
			throw new IllegalArgumentException("Invalid key: " + key);
		treeDelete(nodeToDelete);
	}

	private void treeDelete(TreeNode<T> z) {
		if (z.left == null)
			transplant(z, z.right);
		else if (z.right == null)
			transplant(z, z.left);
		else {
			TreeNode<T> y = treeMinimum(z.right);
			if (y.p != z) {
				transplant(y, y.right);
				y.right = z.right;
				y.right.p = y;
			}
			transplant(z, y);
			y.left = z.left;
			y.left.p = y;
		}
	}

	private void transplant(TreeNode<T> u, TreeNode<T> v) {
		if (u.p == null)
			root = v;
		else if (u.p.left == u)
			u.p.left = v;
		else
			u.p.right = v;

		if (v != null)
			v.p = u.p;
	}

	public void insert(T key) {
		treeInsert(new TreeNode<>(key));
	}

	private void treeInsert(TreeNode<T> z) {
		TreeNode<T> y = null;
		TreeNode<T> x = this.root;

		while (x != null) {
			y = x;
			if (z.key.compareTo(x.key) < 0)
				x = x.left;
			else
				x = x.right;
		}

		z.p = y;
		if (y == null)
			this.root = z; // tree T was empty
		else if (z.key.compareTo(y.key) < 0)
			y.left = z;
		else
			y.right = z;
	}

	public T successor(T key) {
		final TreeNode<T> currNode = iterativeTreeSearch(key);
		if (currNode == null)
			throw new IllegalArgumentException("Invalid key: " + key);
		return treeSuccessor(currNode).key;
	}

	private TreeNode<T> treeSuccessor(TreeNode<T> x) {
		if (x.right != null)
			return treeMinimum(x.right);

		TreeNode<T> y = x.p;
		while (y != null && y.right == x) {
			x = y;
			y = y.p;
		}
		return y;
	}

	public T min() {
		return treeMinimum(root).key;
	}

	private TreeNode<T> treeMinimum(TreeNode<T> x) {
		while (x.left != null)
			x = x.left;
		return x;
	}

	public T max() {
		return treeMaximum(root).key;
	}

	// Need to find the predecessor of a given node.
	private TreeNode<T> treeMaximum(TreeNode<T> x) {
		while (x.right != null)
			x = x.right;
		return x;
	}

	private TreeNode<T> iterativeTreeSearch(T k) {
		TreeNode<T> x = root;
		while (x != null && !x.key.equals(k)) {
			if (k.compareTo(x.key) < 0)
				x = x.left;
			else
				x = x.right;
		}
		return x;
	}

	public TreeNode<T> search(T k) {
		return treeSearch(root, k);
	}

	private TreeNode<T> treeSearch(TreeNode<T> x, T k) {
		if (x == null || k.equals(x.key))
			return x;
		if (k.compareTo(x.key) < 0)
			return treeSearch(x.left, k);
		else
			return treeSearch(x.right, k);
	}

	public void print() {
		inorderTreeWalk(root);
	}

	private void inorderTreeWalk(TreeNode<T> x) {
		if (x != null) {
			inorderTreeWalk(x.left);
			System.out.print(x.key);
			inorderTreeWalk(x.right);
		}
	}

	static class TreeNode<S extends Comparable<? super S>> {
		S key;
		TreeNode<S> left;
		TreeNode<S> right;
		TreeNode<S> p;

		TreeNode(S x) {
			key = x;
		}
	}

	class BSTIterator implements Iterator<T> {
		private TreeNode<T> current;
		private TreeNode<T> next;

		BSTIterator() {
			current = null;
			next = treeMinimum(root);
		}

		@Override
		public boolean hasNext() {
			return next != null;
		}

		@Override
		public T next() {
			if (next == null)
				throw new NoSuchElementException();
			current = next;
			next = treeSuccessor(next);
			return current.key;
		}

		@Override
		public void remove() {
			if (current == null)
				throw new IllegalStateException();
			treeDelete(current);
			// current is already deleted, we should not allow to delete it again.
			current = null;
		}
	}

	@Override
	public Iterator<T> iterator() {
		return new BSTIterator();
	}
}
