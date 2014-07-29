package algorithms;

import graph.Edge;
import graph.Graph;
import graph.Node;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;

public class AStar extends Algorithm{

	private List<State> states = new ArrayList<>();
	private Graph ogGraph;

	public AStar(Graph g, Node strt, Node gol){

		// store the original graph
		ogGraph = g;

		// convert ogGraph into a graph suitable for this algorithm.
		Map<Node,StarNode> mapping = new HashMap<>(); //correspondence from ogGraph -> A*'s graph
		Set<StarNode> nodes = new HashSet<>();
		for (Node node : g.getNodes()){
			StarNode starNode = new StarNode(node);
			nodes.add(starNode);
			mapping.put(node,starNode);
		}
		Set<StarEdge> edges = new HashSet<>();
		for (Edge edge : g.getEdges()){
			StarNode node1 = mapping.get(edge.node1);
			StarNode node2 = mapping.get(edge.node2);
			StarEdge starEdge = new StarEdge(edge,node1,node2);
		}

		// solve the algorithm
		StarNode start = mapping.get(strt);
		StarNode goal = mapping.get(gol);
		solve(start,goal,nodes,edges);

	}

	/**
	 * Solve A*, recording each iteration.
	 * @param graph
	 * @param goal
	 */
	private void solve(StarNode start, StarNode goal, Set<StarNode> nodes, Set<StarEdge> edges){

		// record initial, 'empty' state
		states.add(new State(null,nodes,null));

		// algorithm preparation
		Comparator<StarNode> comp = new Comparator<StarNode>(){

			@Override
			public int compare(StarNode o1, StarNode o2) {
				return 0; // TODO: this
			}



		};
		PriorityQueue<StarNode> fringe = new PriorityQueue<>();

	}

	/**
	 * 'StarEdge' is a wrapper class for the edges in A*. Since it is possible for the user to
	 * enter arbitrary edge weights, A* will convert the visual structure of the graph into something
	 * that will have weights that make Euclidean both feasible (never overstimates) and consistent
	 * (the estimate never gets worse).
	 * @author craigaaro
	 */
	private class StarEdge extends Edge{
		public StarEdge(Edge e, StarNode n1, StarNode n2){
			super(n1,n2,e.directed,e.weight);
		}
	}

	/**
	 * 'StarNode' is a wrapper class for Node. It stores some extra information that A* requires
	 * in order to complete, such as the cost to this node.
	 * @author craigaaro
	 */
	private class StarNode extends Node{
		private StarNode from = null;
		private boolean visited = false;
		private int costToHere;

		public StarNode(Node n){
			super(n.X,n.Y);
		}

	}

	/**
	 * The state of A* is a set of visited nodes, a priorityQueue of nodes ready to be
	 * visited, and the last visited node.
	 * @author craigaaro
	 */
	private class State implements Iteration{
		private Set<Node> visited;
		private Set<Node> toBeVisited;
		private Node lastVisited;
		private State(Set<StarNode> _visited, Set<StarNode> nodes, Node _lastVisited){

		}

	}

	@Override
	protected int numberOfStates() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void draw(Graphics g) {
		// TODO Auto-generated method stub

	}


}
