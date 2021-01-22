package org.clrs.algorithms.trees;

import java.util.ArrayDeque;
import java.util.Comparator;
import java.util.Deque;
import java.util.Iterator;
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
		final N succ = treeSuccessor(currNode);
		return succ == sentinel ? null : succ.key;
	}

	private N treeSuccessor(final N x) {
		if (x.right != sentinel)
			return treeMinimum(x.right);

		N y = x;
		while (y.p != sentinel && y.p.right == y)
			y = y.p;
		return y.p;
	}

	public final E predecessor(E key) {
		final N currNode = iterativeTreeSearch(key);
		if (currNode == sentinel)
			throw new IllegalArgumentException("Invalid key: " + key);
		final N pred = treePredecessor(currNode);
		return pred == sentinel ? null : pred.key;
	}

	private N treePredecessor(final N x) {
		if (x.left != sentinel)
			return treeMaximum(x.left);
		N y = x;
		while (y.p != sentinel && y.p.left == y)
			y = y.p;
		return y.p;
	}

	public final E min() {
		return treeMinimum(root).key;
	}

	protected final N treeMinimum(final N x) {
		N min = x;
		while (min.left != sentinel)
			min = min.left;
		return min;
	}

	public final E max() {
		return treeMaximum(root).key;
	}

	// Need to find the predecessor of a given node.
	private N treeMaximum(final N x) {
		N max = x;
		while (max.right != sentinel)
			max = max.right;
		return max;
	}

	protected final N iterativeTreeSearch(final E k) {
		N x = root;
		while (x != sentinel && !x.key.equals(k)) {
			if (comparator.compare(k, x.key) < 0)
				x = x.left;
			else
				x = x.right;
		}
		return x;
	}

	public final boolean search(final E k) {
		return treeSearch(root, k) != null;
	}

	private N treeSearch(final N x, final E k) {
		if (x == sentinel || k.equals(x.key))
			return x;
		if (comparator.compare(k, x.key) < 0)
			return treeSearch(x.left, k);
		else
			return treeSearch(x.right, k);
	}

	protected void inorderTreeWalk(final N x) {
		if (x != sentinel) {
			inorderTreeWalk(x.left);
			System.out.print(x.key + " ");
			inorderTreeWalk(x.right);
		}
	}

	private String inorderTreeWalkIterativeV2() {
		final Deque<N> s = new ArrayDeque<>();
		N current = root;
		final StringJoiner sj = new StringJoiner(", ", "[", "]");
		while (!s.isEmpty() || current != sentinel) {
			if (current != sentinel) {
				s.push(current);
				current = current.left;
			} else {
				final N r = s.pop();
				sj.add(r.key.toString());
				current = r.right;
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
}
