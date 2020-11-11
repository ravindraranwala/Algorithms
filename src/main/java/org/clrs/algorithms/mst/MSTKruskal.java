package org.clrs.algorithms.mst;

import java.util.Arrays;
import java.util.Comparator;

import org.clrs.algorithms.util.ArrayUtil;

public class MSTKruskal {
	private MSTKruskal() {
		throw new AssertionError("Non instantiable !");
	}

	public static void main(String[] args) {
		// Min Cost to Connect All Nodes (Minimum Spanning Tree I)
		int n = 6;
		Edge[] edges = { new Edge(1, 4, 0), new Edge(4, 5, 0), new Edge(2, 3, 0) };
		Edge[] newEdges = { new Edge(1, 2, 5), new Edge(1, 3, 10), new Edge(1, 6, 2), new Edge(5, 6, 5) };
		int cost = mstKruskal(n, ArrayUtil.concat(edges, newEdges));

		System.out.println(String.format("Min Cost to Connect All Nodes: %d", cost));

		// Min Cost to Repair Edges (Minimum Spanning Tree II)
		// Usecase One
		/*
		 * Note that broken edges has infinite weight in it's initial representation,
		 * since it does not connect the two nodes. The edges with 0 weight implies that
		 * they are already in the MST. Also the initial edges gives us all the edges
		 * (possibly with cycles) and the MST is just a subset of it.
		 */
		n = 5;
		edges = new Edge[] { new Edge(1, 2, Integer.MAX_VALUE), new Edge(2, 3, 0), new Edge(3, 4, Integer.MAX_VALUE),
				new Edge(4, 5, 0), new Edge(1, 5, Integer.MAX_VALUE) };
		Edge[] edgesToRepair = { new Edge(1, 2, 12), new Edge(3, 4, 30), new Edge(1, 5, 8) };
		cost = mstKruskal(n, ArrayUtil.concat(edges, edgesToRepair));
		System.out.println(String.format("Min Cost to Repair Edges: %d", cost));

		n = 6;
		edges = new Edge[] { new Edge(1, 2, 0), new Edge(2, 3, 0), new Edge(4, 5, 0), new Edge(3, 5, 0),
				new Edge(1, 6, Integer.MAX_VALUE), new Edge(2, 4, Integer.MAX_VALUE) };
		edgesToRepair = new Edge[] { new Edge(1, 6, 410), new Edge(2, 4, 800) };
		cost = mstKruskal(n, ArrayUtil.concat(edges, edgesToRepair));
		System.out.println(String.format("Min Cost to Repair Edges: %d", cost));

		n = 6;
		edges = new Edge[] { new Edge(1, 2, 0), new Edge(2, 3, 0), new Edge(4, 5, 0), new Edge(5, 6, 0),
				new Edge(1, 5, Integer.MAX_VALUE), new Edge(2, 4, Integer.MAX_VALUE),
				new Edge(3, 4, Integer.MAX_VALUE) };
		edgesToRepair = new Edge[] { new Edge(1, 5, 110), new Edge(2, 4, 84), new Edge(3, 4, 79) };
		cost = mstKruskal(n, ArrayUtil.concat(edges, edgesToRepair));
		System.out.println(String.format("Min Cost to Repair Edges: %d", cost));
	}

	private static int mstKruskal(int n, Edge[] edges) {
		Node[] vertices = new Node[n + 1];

		for (int i = 1; i <= n; i++) {
			vertices[i] = new Node();
			makeSet(vertices[i]);
		}

		// Sort the edges of G.E into nondecreasing order by weight w.
		Arrays.sort(edges, Comparator.comparingInt(e -> e.weight));

		int cost = 0;
		for (Edge edge : edges) {
			Node u = vertices[edge.source];
			Node v = vertices[edge.target];
			if (findSet(u) != findSet(v)) {
				cost += edge.weight; // A safe, light edge is found. This becomes a part of our MST.
				union(u, v);
			}
		}
		return cost;
	}

	// Disjoint set operations are implemented here.
	private static void makeSet(Node x) {
		x.p = x;
		x.rank = 0;
	}

	private static void union(Node x, Node y) {
		link(findSet(x), findSet(y));
	}

	private static void link(Node x, Node y) {
		// union by rank heuristic.
		if (x.rank > y.rank)
			y.p = x;
		else {
			x.p = y;
			if (x.rank == y.rank)
				y.rank += 1;
		}
	}

	private static Node findSet(Node x) {
		if (x != x.p)
			x.p = findSet(x.p);// Path compression.

		return x.p;
	}

	static class Node {
		private int rank;
		private Node p;
	}

	static class Edge {
		private final int source;
		private final int target;
		private final int weight;

		public Edge(int source, int target, int weight) {
			this.source = source;
			this.target = target;
			this.weight = weight;
		}
	}
}
