package org.clrs.algorithms.trees;

import java.util.Comparator;
import java.util.Iterator;

import org.clrs.algorithms.trees.AbstractBST.TreeNode;
import org.clrs.algorithms.trees.BST.BSTreeNode;

public class BST<E> extends AbstractBST<E, BSTreeNode<E>> implements Iterable<E> {
	private BST(Comparator<? super E> comparator) {
		super(comparator, null);
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

		for (final Iterator<Integer> it = bst.iterator(); it.hasNext();) {
			Integer val = it.next();
			System.out.println(val);
		}

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

		for (final Iterator<Integer> it = bst.iterator(); it.hasNext();) {
			Integer val = it.next();
			System.out.println(val);
		}
		BST<?> tree = BST.of();
		System.out.println(tree.inorderTreeWalkIterative());
		System.out.println(tree.inorderTreeWalkIterativeAdvanced());
		
		for (final Iterator<?> it = tree.iterator(); it.hasNext();) {
			Object val = it.next();
			System.out.println(val);
		}
	}

	@Override
	public void delete(E key) {
		final BSTreeNode<E> nodeToDelete = iterativeTreeSearch(key);
		if (nodeToDelete == null)
			throw new IllegalArgumentException("Invalid key: " + key);
		treeDelete(nodeToDelete);
	}

	private void treeDelete(BSTreeNode<E> z) {
		if (z.left == null)
			transplant(z, z.right);
		else if (z.right == null)
			transplant(z, z.left);
		else {
			BSTreeNode<E> y = treeMinimum(z.right);
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

	private void transplant(BSTreeNode<E> u, BSTreeNode<E> v) {
		if (u.p == null)
			root = v;
		else if (u.p.left == u)
			u.p.left = v;
		else
			u.p.right = v;

		if (v != null)
			v.p = u.p;
	}

	@Override
	public void insert(E key) {
		treeInsert(new BSTreeNode<>(key));
	}

	private void treeInsert(BSTreeNode<E> z) {
		BSTreeNode<E> y = null;
		BSTreeNode<E> x = this.root;

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

	@Override
	public void print() {
		inorderTreeWalk(root);
		System.out.println(inorderTreeWalkIterative());
		System.out.println(inorderTreeWalkIterativeAdvanced());
	}

	@Override
	public Iterator<E> iterator() {
		return new BSTIterator();
	}

	static class BSTreeNode<T> extends TreeNode<T, BSTreeNode<T>> {
		protected BSTreeNode(T x) {
			super(x);
		}
	}
}
