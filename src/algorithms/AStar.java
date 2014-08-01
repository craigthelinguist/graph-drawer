package algorithms;

import graph.Edge;
import graph.Graph;
import graph.Node;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;

import algorithms.Algorithm.Iteration;

public class AStar extends Algorithm{

	private List<State> states = new ArrayList<>();
	private Set<StarEdge> edges; //contains edges converted to where weight = euclidean distance
	private Set<StarNode> nodes;
	private StarNode goal;
	
	private Color COLOR_PATH = new Color(152,251,152);
	private Color COLOR_VISITED = new Color(46,139,87);
	private Color COLOR_FRINGE = new Color(143,188,143);
	private Color COLOR_GOAL = new Color(95,158,160);
	private Color COLOR_UNVISITED = new Color(244,238,224);
	
	public AStar(Graph graph, Node start, Node goal){

		// convert the graph into one appropriate for A* - this is necessary because edge weights
		// are probably arbitrary and they must be consistent for this algorithm to work.
		Map<Node,StarNode> mapping = new HashMap<>();
		nodes = new HashSet<>();
		for (Node node : graph.getNodes()){
			StarNode starNode = new StarNode(node);
			nodes.add(starNode);
			mapping.put(node, starNode);
		}
		edges = new HashSet<>();
		for (Edge edge : graph.getEdges()){
			StarNode node1 = mapping.get(edge.node1);
			StarNode node2 = mapping.get(edge.node2);
			StarEdge starEdge = new StarEdge(node1,node2,edge);
			node1.addNeighbour(starEdge, node1);
			node2.addNeighbour(starEdge, node2);
			edges.add(starEdge);
		}
		
		this.goal = mapping.get(goal);
		
		// solve algorithm
		solve(mapping.get(start),mapping.get(goal));
		
	}

	/**
	 * Solve A*, recording each iteration.
	 * @param graph
	 * @param goal
	 */
	private void solve(final StarNode start, final StarNode goal){

		// set up; push start node onto fringe; record initial state
		Set<StarNode> visited = new HashSet<>();
		PriorityQueue<FringeNode> fringe = new PriorityQueue<>();
		fringe.offer(new FringeNode(start,null,0,distanceBetween(start,goal)));
		states.add(new State(visited,fringe,fringe.peek()));
		
		while (true){
			
			// get next thing off the fringe
			FringeNode currentFringeNode = fringe.poll();
			StarNode currentNode = currentFringeNode.node;
			
			// already visited the node
			if (visited.contains(currentNode)){
				states.add(new State(visited,fringe,currentFringeNode));
				continue;
			}
			
			// reached goal node
			if (currentNode == goal){
				visited.add(currentNode);
				states.add(new State(visited,fringe,currentFringeNode));
				states.add(new State(null,null,currentFringeNode));
				return;
			}
			
			// otherwise add neighbours onto fringe
			visited.add(currentNode);
			for (Map.Entry<StarEdge,StarNode> entry : currentNode.neighbours.entrySet()){
				StarEdge edge = entry.getKey();
				StarNode neighbour = entry.getValue();
				if (visited.contains(neighbour)) continue;
				int cost = currentFringeNode.costToHere + edge.weight;
				int dist = distanceBetween(currentNode,neighbour);
				FringeNode newFringeNode = new FringeNode(neighbour,currentFringeNode,cost,dist);
				fringe.offer(newFringeNode);
			}
			
			// record state, keep going
			states.add(new State(visited,fringe,currentFringeNode));
			
		}
		
	}
	
	private class StarNode{
		private Map<StarEdge,StarNode> neighbours;
		private Node node;
		
		private StarNode(Node n){
			neighbours = new HashMap<>();
		}
		
		private void addNeighbour(StarEdge e, StarNode sn){
			neighbours.put(e,sn);
		}
		
		private void draw(Graphics g, Color col){
			node.draw(g, col);
		}
		
	}
	
	private class StarEdge{
		private Edge edge;
		private StarNode node1;
		private StarNode node2;
		private boolean directed;
		private int weight;
		
		private StarEdge(StarNode n1, StarNode n2, Edge e){
			edge = e;
			node1 = n1;
			node2 = n2;
			directed = e.directed;
			weight = distanceBetween(n1,n2);
		}
		
		private void draw(Graphics g, Color col){
			g.setColor(col);
			g.drawLine(node1.node.X, node1.node.Y, node2.node.X, node2.node.Y);
			g.drawString(""+weight, (node1.node.XMID + node2.node.XMID)/2, (node1.node.YMID + node2.node.YMID)/2);
		}
		
	}
	
	/**
	 * Helper method. Returns the straight-line distance between two nodes.
	 * @param n1: first node.
	 * @param n2: second node.
	 * @return: distance between n1 and n2.
	 */
	private int distanceBetween(StarNode n1, StarNode n2){
		int dx = Math.abs(n1.node.X - n2.node.X);
		int dy = Math.abs(n1.node.Y - n2.node.Y);
		return dx*dx + dy*dy;
	}

	private class FringeNode implements Comparable<FringeNode>{
		private StarNode node;
		private FringeNode from;
		private int costToHere;
		private int estimate;
		
		private FringeNode(StarNode node, FringeNode from, int cost, int heuristic){
			this.node = node;
			this.from = from;
			costToHere = cost;
			estimate = heuristic;
		}
		
		@Override
		public int compareTo(FringeNode other) {
			return (costToHere + estimate) - (other.costToHere + estimate);
		}
		
		@Override
		public int hashCode(){
			return node.hashCode();
		}
		
	}
	
	/**
	 * The state of A* is a set of visited nodes, a priorityQueue of nodes ready to be
	 * visited, and the last visited node.
	 * @author craigaaro
	 */
	private class State implements Iteration{
		private Set<StarNode> visited;
		private PriorityQueue<FringeNode> fringe;
		private FringeNode lastVisited;
		private State(Set<StarNode> visited, PriorityQueue<FringeNode> fringe, FringeNode lastVisited){
			this.visited = visited;
			this.fringe = fringe;
			this.lastVisited = lastVisited;
		}
	}

	@Override
	public void draw(Graphics g) {
		
		State state = states.get(stateIndex);
		
		// draw all edges
		for (StarEdge se : edges) se.draw(g, Color.DARK_GRAY);
		
		// draw all nodes
		for (StarNode sn : nodes) sn.draw(g,COLOR_UNVISITED);
		
		// draw visited nodes
		if (state.visited != null){
			for (StarNode sn : state.visited) sn.draw(g,COLOR_VISITED);
		}
		
		// draw fringe nodes
		if (state.fringe != null){
			for (FringeNode fn : state.fringe){
				StarNode sn = fn.node;
				sn.draw(g, COLOR_FRINGE);
			}
		}
		
		// draw path from last processed to start
		if (state.lastVisited != null){
			
			FringeNode fn = state.lastVisited;
			while (fn != null){
				if (fn.from != null){
					StarEdge edge = edgeBetween(fn.node,fn.from.node);
					edge.draw(g, COLOR_PATH);
				}
				fn.node.draw(g, COLOR_PATH);
				fn = fn.from;
			}
			
		}
		
		// draw goal node
		goal.draw(g, COLOR_GOAL);
		
		
	}
	
	/**
	 * Get the edge between two nodes, or null if there isn't one.
	 * @param node1: first node.
	 * @param node2: second node.
	 * @return: edge between node1 and node2.
	 */
	private StarEdge edgeBetween(StarNode node1, StarNode node2){
		if (node1 == null || node2 == null) throw new NullPointerException();
		for (Map.Entry<StarEdge,StarNode> entry : node1.neighbours.entrySet()){
			if (entry.getValue() == node2) return entry.getKey();
		}
		return null;
	}
	
	@Override
	protected int numberOfStates() {
		return states.size();
	}
	
}
