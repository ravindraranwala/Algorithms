package org.clrs.algorithms.bst;

import java.util.Comparator;
import java.util.Iterator;
import java.util.NoSuchElementException;

public final class BST<E> implements Iterable<E> {
	private TreeNode<E> root = null;
	private final Comparator<? super E> comparator;

	private BST(Comparator<? super E> comparator) {
		this.comparator = comparator;
	}

	public static <T> BST<T> of(Comparator<? super T> comparator) {
		return new BST<>(comparator);
	}

	public static <T extends Comparable<? super T>> BST<T> of() {
		return new BST<>(Comparator.naturalOrder());
	}

	public static void main(String[] args) {
		final BST<Integer> bst = BST.of();
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

	public void delete(E key) {
		final TreeNode<E> nodeToDelete = iterativeTreeSearch(key);
		if (nodeToDelete == null)
			throw new IllegalArgumentException("Invalid key: " + key);
		treeDelete(nodeToDelete);
	}

	private void treeDelete(TreeNode<E> z) {
		if (z.left == null)
			transplant(z, z.right);
		else if (z.right == null)
			transplant(z, z.left);
		else {
			TreeNode<E> y = treeMinimum(z.right);
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

	private void transplant(TreeNode<E> u, TreeNode<E> v) {
		if (u.p == null)
			root = v;
		else if (u.p.left == u)
			u.p.left = v;
		else
			u.p.right = v;

		if (v != null)
			v.p = u.p;
	}

	public void insert(E key) {
		treeInsert(new TreeNode<>(key));
	}

	private void treeInsert(TreeNode<E> z) {
		TreeNode<E> y = null;
		TreeNode<E> x = this.root;

		while (x != null) {
			y = x;
			if (comparator.compare(z.key, x.key) < 0)
				x = x.left;
			else
				x = x.right;
		}

		z.p = y;
		if (y == null)
			this.root = z; // tree T was empty
		else if (comparator.compare(z.key, y.key) < 0)
			y.left = z;
		else
			y.right = z;
	}

	public E successor(E key) {
		final TreeNode<E> currNode = iterativeTreeSearch(key);
		if (currNode == null)
			throw new IllegalArgumentException("Invalid key: " + key);
		return treeSuccessor(currNode).key;
	}

	private TreeNode<E> treeSuccessor(TreeNode<E> x) {
		if (x.right != null)
			return treeMinimum(x.right);

		TreeNode<E> y = x.p;
		while (y != null && y.right == x) {
			x = y;
			y = y.p;
		}
		return y;
	}

	public E min() {
		return treeMinimum(root).key;
	}

	private TreeNode<E> treeMinimum(TreeNode<E> x) {
		while (x.left != null)
			x = x.left;
		return x;
	}

	public E max() {
		return treeMaximum(root).key;
	}

	// Need to find the predecessor of a given node.
	private TreeNode<E> treeMaximum(TreeNode<E> x) {
		while (x.right != null)
			x = x.right;
		return x;
	}

	private TreeNode<E> iterativeTreeSearch(E k) {
		TreeNode<E> x = root;
		while (x != null && !x.key.equals(k)) {
			if (comparator.compare(k, x.key) < 0)
				x = x.left;
			else
				x = x.right;
		}
		return x;
	}

	public TreeNode<E> search(E k) {
		return treeSearch(root, k);
	}

	private TreeNode<E> treeSearch(TreeNode<E> x, E k) {
		if (x == null || k.equals(x.key))
			return x;
		if (comparator.compare(k, x.key) < 0)
			return treeSearch(x.left, k);
		else
			return treeSearch(x.right, k);
	}

	public void print() {
		inorderTreeWalk(root);
	}

	private void inorderTreeWalk(TreeNode<E> x) {
		if (x != null) {
			inorderTreeWalk(x.left);
			System.out.print(x.key);
			inorderTreeWalk(x.right);
		}
	}

	static class TreeNode<T> {
		T key;
		TreeNode<T> left;
		TreeNode<T> right;
		TreeNode<T> p;

		TreeNode(T x) {
			key = x;
		}
	}

	class BSTIterator implements Iterator<E> {
		private TreeNode<E> current;
		private TreeNode<E> next;

		BSTIterator() {
			current = null;
			next = treeMinimum(root);
		}

		@Override
		public boolean hasNext() {
			return next != null;
		}

		@Override
		public E next() {
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
	public Iterator<E> iterator() {
		return new BSTIterator();
	}
}
