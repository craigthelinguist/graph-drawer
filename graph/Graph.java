package graph;

import java.awt.Color;
import java.awt.Graphics;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Represents a graph data structure. A graph has a set of nodes and a set of vertices.
 * There are also some static utility methods.
 * @author craigthelinguist
 */
public class Graph{

	private Set<Node> nodes = new HashSet<>();
	private Set<Edge> edges = new HashSet<>();

	/**
	 * Creates a node centred at (x,y) from the given arguments and adds it to the set of nodes
	 * in this graph. The node will not be added if it overlaps with any of the other nodes.
	 * @param x: centre of the node.
	 * @param y: centre of the node.
	 * @return: true if the node was added, false otherwise.
	 */
	public boolean createNode(int x, int y){
		Node node = new Node(x,y);
		for (Node other : nodes){
			if (overlapping(node,other)) return false;
		}
		nodes.add(new Node(x,y));
		return true;
	}

	/**
	 * Creates an edge between node1 and node2 and adds it to the set of edges
	 * in this graph. Also updates node1 and node2's neighbourhood sets. The edge
	 * will not be added if node1 == node2 or if there already exists an edge
	 * between node1 and node2.
	 * @param node1: first node the edge touches.
	 * @param node2: second node the edge touches.
	 * @param boolean: if the edge is directed
	 * @return: true if the node was added; false otherwise.
	 */
	public boolean createEdge(Node node1, Node node2, boolean directed, int weight){
		if (node1 == null || node2 == null) throw new NullPointerException("Adding an edge that touches a null node.");
		if (node1 == node2) return false;
		if (edgeBetween(node1,node2) != null) return false;
		Edge edge = new Edge(node1,node2,directed,weight);
		edges.add(edge); // dirty but needed to prevent illegal modification
		node1.addNeighbour(edge, node2);
		node2.addNeighbour(edge, node1);
		return true;
	}

	/**
	 * Find and return the edge between the given nodes.
	 * @param node1: first node.
	 * @param node2: second node.
	 * @return: the edge (node1,node2), or null if there isn't one.
	 */
	public static Edge edgeBetween(Node node1, Node node2){
		for (Map.Entry<Edge,Node> entry : node1.getNeighbours().entrySet()){
			if (entry.getValue() == node2) return entry.getKey();
		}
		return null;
	}

	/**
	 * Check if the given nodes overlap with each other.
	 * @param node1: first node.
	 * @param node2: second node.
	 * @return: true if the nodes overlap. False otherwise.
	 */
	public static boolean overlapping(Node node1, Node node2){
		int dx = Math.abs(node1.XMID - node2.XMID);
		int dy = Math.abs(node1.YMID - node2.YMID);
		int dist = dx*dx + dy*dy;
		int diam_squared = Node.DIAM*Node.DIAM;
		return dist <= diam_squared;
	}

	/**
	 * Find and return the node containing the point (x,y).
	 * @param x: x part of the point.
	 * @param y: y part of the point.
	 * @return: the node containing the point (x,y), or null if there is none.
	 */
	public Node getNode(int x, int y){
		for (Node node : nodes) if (node.contains(x,y)) return node;
		return null;
	}

	/**
	 * Return a read-only view of nodes in this set.
	 * @return: set of nodes in this graph.
	 */
	public Set<? extends Node> getNodes(){
		return nodes;
	}

	/**
	 * Return a read-only view of edges in this set.
	 * @return: set of eges in th
	 */
	public Set<? extends Edge> getEdges(){
		return edges;
	}

	/**
	 * Draw this graph. First draws all edges, then all nodes.
	 * @param g: object on which to draw the graph.
	 */
	public void draw(Graphics g){
		drawEdges(g);
		drawNodes(g);
	}

	/**
	 * Draw only the nodes of this graph.
	 * @param g: object on which to draw.
	 */
	public void drawNodes(Graphics g){
		for (Node node : nodes) node.draw(g);
	}

	/**
	 * Draw only the nodes of this graph in the specified colour.
	 * @param g: object on which to draw.
	 * @param col: colour to draw in.
	 */
	public void drawNodes(Graphics g, Color col){
		for (Node node : nodes) node.draw(g,col);
	}

	/**
	 * Draw only the edges of this graph.
	 * @param g: object on which to draw.
	 */
	public void drawEdges(Graphics g){
		for (Edge edge : edges) edge.draw(g);
	}

	/**
	 * Draw only the edges of this graph in the specified colour.
	 * @param g: object on which to draw.
	 * @param col: colour to draw in.s
	 */
	public void drawEdges(Graphics g, Color col){
		for (Edge edge : edges) edge.draw(g,col);
	}

	public class MyNode extends Node{

		public MyNode(int xtopleft, int ytopleft) {
			super(xtopleft, ytopleft);
			// TODO Auto-generated constructor stub
		}

	}

}
