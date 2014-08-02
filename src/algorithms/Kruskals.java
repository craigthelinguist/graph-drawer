package algorithms;

import graph.Edge;
import graph.Graph;
import graph.Node;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;

public class Kruskals extends Algorithm{

	private List<State> states = new ArrayList<State>();
	private Graph graph;

	public Kruskals(Graph g){
		graph = g;
		solve();
		this.stateIndex = 0;
	}

	private void solve(){

		/* Algorithm set up */
		PriorityQueue<Edge> edges = new PriorityQueue<>(graph.getEdges());
		Set<Edge> tree = new HashSet<>();
		Set<UFNode> nodes = new HashSet<>();
		Map<Node,UFNode> mapping = new HashMap<>();
		for (Node node : graph.getNodes()){
			UFNode kruskalNode = new UFNode(node);
			nodes.add(kruskalNode);
			mapping.put(node,kruskalNode);
		}

		/* Add the initial, 'empty' state. */
		states.add(new State(null,tree,edges));

		/* Algorithm execution */
		while (!edges.isEmpty()){
			Edge edge = edges.poll();
			UFNode first = mapping.get(edge.node1);
			UFNode second = mapping.get(edge.node2);
			if (union(first,second)) tree.add(edge);
			states.add(new State(edge,tree,edges));
		}

		/* Add the final, 'complete' state. */
		states.add(new State(null,tree,new PriorityQueue<Edge>()));

	}

	@Override
	public void draw(Graphics g) {

		// get current state and draw unprocessed nodes
		State state = states.get(stateIndex);

		// draw edges
		for (Edge e : state.spanningTree) e.draw(g,Color.GREEN);
		for (Edge e : state.toBeChecked) e.draw(g, Color.BLACK);
		Edge lastTouched = state.lastProcessed;
		if (lastTouched != null){
			lastTouched.draw(g, Color.CYAN);
			lastTouched.node1.draw(g, Color.CYAN);
			lastTouched.node2.draw(g, Color.CYAN);
		}
		
		// draw nodes
		graph.drawNodes(g,Color.WHITE);
		for (Edge e : state.spanningTree){
			e.node1.draw(g, Color.GREEN);
			e.node2.draw(g, Color.GREEN);
		}
		
	}

	/**
	 * Wrapper class that represents the 'state' of the algorithm at each step. We're interested in
	 * what edges belong to the spanningTree after each iteration, and what the last edge was that
	 * we considered.
	 * @author craigthelinguist
	 */
	private class State implements Iteration{
		private Edge lastProcessed;
		private Set<Edge> spanningTree;
		private Set<Edge> toBeChecked;
		public State(Edge e, Set<Edge> s, PriorityQueue<Edge> edges){
			if (e != null) lastProcessed = e.clone();
			spanningTree = new HashSet<Edge>();
			for (Edge edge : s){
				spanningTree.add(edge.clone());
			}
			toBeChecked = new HashSet<Edge>();
			for (Edge edge : edges){
				toBeChecked.add(edge);
			}
		}
	}

	@Override
	protected int numberOfStates() {
		return states.size();
	}

	/**
	 * UFNode - or 'UnionFindNode', is what we use to perform Kruskal's. The set of nodes in the
	 * tree is represented as the union of disjoint sets. Each node has a head. If you follow the
	 * trail of heads until you get to null, then the node at the top represents the set.
	 * @author craigthelinguist
	 */
	private class UFNode extends Node{
		private UFNode head = null;
		private UFNode(Node node){
			super(node.X,node.Y);
		}
	}

	/**
	 * Find and return the node that represents the set containing the node that you provide.
	 * @param node: node whose head you'll find.
	 * @return: the head representing the set containing this node.
	 */
	private UFNode find(UFNode node){
		if (node.head == null) return node;
		UFNode parent = find(node.head);
		if (parent != node) node.head = parent;
		return parent;
	}

	/**
	 * Take the union of these two sets by setting their
	 * heads to be the same. Returns true if the sets were
	 * unioned, false if they weren't (i.e.: they already
	 * belong to the same set).
	 * @param node1: node in the first set.
	 * @param node2: node in the second set.
	 * @return: true/false if the two sets were unioned and it resulted in change.
	 */
	private boolean union(UFNode node1, UFNode node2){
		UFNode head1 = find(node1);
		UFNode head2 = find(node2);
		if (head1 == head2) return false;
		head1.head = head2;
		return true;
	}



}
