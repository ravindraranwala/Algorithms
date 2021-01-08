package org.clrs.algorithms.trees;

import java.util.ArrayDeque;
import java.util.Comparator;
import java.util.Deque;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.StringJoiner;

import org.clrs.algorithms.trees.AbstractBST.TreeNode;

public abstract class AbstractBST<E, N extends TreeNode<E, N>> {
	protected N root;
	protected final N sentinel;
	protected final Comparator<? super E> comparator;

	public AbstractBST(Comparator<? super E> comparator, N sentinel) {
		this.comparator = comparator;
		this.sentinel = sentinel;
	}

	/**
	 * Deletes the given element from the BST
	 * 
	 * @param key to be deleted
	 */
	public abstract void delete(E key);

	/**
	 * Inserts the given element into the BST
	 * 
	 * @param key to be inserted
	 */
	public abstract void insert(E key);

	/**
	 * Prints the tree in a human readable format.
	 */
	public abstract void print();

	public final E successor(E key) {
		final N currNode = iterativeTreeSearch(key);
		if (currNode == sentinel)
			throw new IllegalArgumentException("Invalid key: " + key);
		return treeSuccessor(currNode).key;
	}

	private N treeSuccessor(N x) {
		if (x.right != sentinel)
			return treeMinimum(x.right);

		N y = x.p;
		while (y != sentinel && y.right == x) {
			x = y;
			y = y.p;
		}
		return y;
	}

	public final E min() {
		return treeMinimum(root).key;
	}

	protected final N treeMinimum(N x) {
		while (x.left != sentinel)
			x = x.left;
		return x;
	}

	public final E max() {
		return treeMaximum(root).key;
	}

	// Need to find the predecessor of a given node.
	private N treeMaximum(N x) {
		while (x.right != sentinel)
			x = x.right;
		return x;
	}

	protected final N iterativeTreeSearch(E k) {
		N x = root;
		while (x != sentinel && !x.key.equals(k)) {
			if (comparator.compare(k, x.key) < 0)
				x = x.left;
			else
				x = x.right;
		}
		return x;
	}

	public final boolean search(E k) {
		return treeSearch(root, k) != null;
	}

	private N treeSearch(N x, E k) {
		if (x == sentinel || k.equals(x.key))
			return x;
		if (comparator.compare(k, x.key) < 0)
			return treeSearch(x.left, k);
		else
			return treeSearch(x.right, k);
	}

	protected void inorderTreeWalk(N x) {
		if (x != sentinel) {
			inorderTreeWalk(x.left);
			System.out.print(x.key + " ");
			inorderTreeWalk(x.right);
		}
	}

	private String inorderTreeWalkIterativeV2() {
		final Deque<N> s = new ArrayDeque<>();
		boolean done = root == sentinel;
		N current = root;
		final StringJoiner sj = new StringJoiner(", ", "[", "]");
		while (!done) {
			if (current != sentinel) {
				s.push(current);
				current = current.left;
			} else {
				final N r = s.pop();
				sj.add(r.key.toString());
				if (r.right != sentinel)
					current = r.right;
				done = s.isEmpty() && current == sentinel;
			}
		}
		return sj.toString();
	}

	/**
	 * A nonrecursive algorithm that performs an inorder tree walk.
	 * 
	 * @return String representation of the Binary search tree. Elements are in
	 *         sorted order.
	 */
	protected String inorderTreeWalkIterative() {
		final StringJoiner sj = new StringJoiner(", ", "[", "]");
		for (Iterator<E> it = new BSTIterator(); it.hasNext();)
			sj.add(it.next().toString());
		return sj.toString();
	}

	protected String inorderTreeWalkIterativeAdvanced() {
		N current = root;
		N previous = root;
		final StringJoiner sj = new StringJoiner(", ", "[", "]");
		while (current != sentinel) {
			if (current.left != sentinel && current.left != previous && current.right != previous) {
				previous = current;
				current = current.left;
			} else {
				if (current.right != previous)
					sj.add(current.key.toString());
				final N tmp = current;
				if (current.right == sentinel || current.right == previous)
					current = current.p;
				else
					current = current.right;
				previous = tmp;
			}
		}
		return sj.toString();
	}

	static class TreeNode<T, S extends TreeNode<T, S>> {
		T key;
		S left;
		S right;
		S p;

		protected TreeNode(T x) {
			key = x;
		}
	}

	class BSTIterator implements Iterator<E> {
		private final Deque<N> s = new ArrayDeque<>();
		private N current = root;
		private N r = sentinel;

		BSTIterator() {
		}

		@Override
		public boolean hasNext() {
			return !s.isEmpty() || current != sentinel;
		}

		@Override
		public E next() {
			while (true) {
				if (current != sentinel) {
					s.push(current);
					current = current.left;
				} else {
					r = s.pop();
					if (r.right != sentinel)
						current = r.right;
					return r.key;
				}
			}
		}

		@Override
		public void remove() {
			if (r == sentinel)
				throw new IllegalStateException();
			delete(r.key);
			// current is already deleted, we should not allow to delete it again.
			r = sentinel;
		}
	}

	class BSTIteratorAdvanced implements Iterator<E> {
		private N current = root;
		private N previous = root;

		BSTIteratorAdvanced() {
		}

		@Override
		public boolean hasNext() {
			return current != sentinel;
		}

		@Override
		public E next() {
			E val = null;
			while (current != sentinel) {
				if (current.left != sentinel && current.left != previous && current.right != previous) {
					previous = current;
					current = current.left;
				} else {
					if (val == null && current.right != previous)
						val = current.key;

					final N tmp = current;
					if (current.right == sentinel || current.right == previous)
						current = current.p;
					else
						current = current.right;

					previous = tmp;
					if (current == sentinel || current.right != previous)
						return val;
				}
			}
			throw new NoSuchElementException();
		}
	}
}
