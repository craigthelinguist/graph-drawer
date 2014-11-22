package controller;

import java.awt.Color;

import javax.swing.JOptionPane;

import algorithms.AStar;
import algorithms.Algorithm;
import algorithms.Kruskals;
import graph.Graph;
import graph.Node;
import gui.GraphGui;

public class GraphController {

	/**
	 * Keeps track of what mode the sidebar is in. Graphing: the user is able to
	 * draw a graph on the screen. Algorithm: the user can select inputs and
	 * perform their selected algorithm.
	 *
	 * @author craigthelinguist
	 */
	public enum Mode {
		GRAPHING, ALGORITHMS;
	}
	private GraphGui gui;

	// gui constants
	public static final String[] algorithms = { "", "A* Pathfinding",
			"Kruskal's Algorithm", "Articulation Points" };

	// graph data structure
	private Graph graph = new Graph();
	private Algorithm algorithm;

	// mode keeps track of whether you're in algorithms mode or graphing mode
	private Mode mode = Mode.GRAPHING;
	private String selectedAlgorithm = "";

	private int weight = 1;
	private boolean areEdgesDirected = false;
	
	public GraphController(){
	}
	
	public void setGUI(GraphGui gui){
		this.gui = gui;
	}
	
	
	public void buttonPress(String buttonName){

		if (gui == null) return;
		
		if (mode == Mode.ALGORITHMS){
		
			if (algorithm == null){
				createAlgorithm();
			}
			else if (buttonName.equals("step")){
				algorithm.nextIteration();
			}
			else if (buttonName.equals("back")){
				algorithm.previousIteration();
			}
			else if (buttonName.equals("run")){
				algorithm.lastIteration();
			}
		}
		else if (mode == Mode.GRAPHING){

			if (buttonName.equals("clear")){
				graph = new Graph();
			}
				
		}
		
	}
	

	public void changeAlgorithm(String algorithmName){

		if (gui == null) return;
		
		algorithm = null;
		selectedAlgorithm = algorithmName;
		createAlgorithm();
		gui.getCanvas().repaint();
	}
	
	public void changeMode(String name) {

		if (gui == null) return;
		
		System.out.println("Mode change: " + name);
		if (name.equals("algorithms")) {
			mode = Mode.ALGORITHMS;
			gui.getCanvas().deselect();
		} else if (name.equals("graphing")) {
			mode = Mode.GRAPHING;
		}
		algorithm = null;
		gui.getCanvas().repaint();
	}

	/**
	 * Creates an algorithm based on the value in the text field.
	 */
	private void createAlgorithm() {
		

		if (gui == null) return;
		
		String name = getSelectedAlgorithm();
		System.out.println("selected algorithm is " + name);
		selectedAlgorithm = name;
		
		if (name.equals(algorithms[1])){
			Node start = gui.getCanvas().getSelected(0);
			Node goal = gui.getCanvas().getSelected(1);
			if (start == null || goal == null){
				gui.createErrorDialog("You must select a start node and an end node for A*. Select nodes by left-clicking them.");
				return;
			}
			algorithm = new AStar(graph,start,goal);
		}
		else if (name.equals(algorithms[2])) {
			algorithm = new Kruskals(graph);
		}
		else if (name.equals(algorithms[3])){
			gui.createErrorDialog("Articulation points algorithm not yet fully implemented.");
			return;
		}
		else{
			algorithm = null;
			selectedAlgorithm = "";
		}
	}


	public boolean runningAlgorithm(){
		return algorithm != null;
	}
	
	public String getSelectedAlgorithm(){
		return selectedAlgorithm;
	}



	public void updateWeight(int weight) {
		this.weight = weight;
	}

	public void updateDirectedEdges(boolean directed){
		this.areEdgesDirected = directed;
	}


	public Algorithm getAlgorithm() {
		return this.algorithm;
	}






	
	
	/**
	 * Return the node at the location (x,y), or null if there is none.
	 *
	}
	 * @param x
	 *            : x part of where the user clicked.
	 * @param y
	 *            : y part of where the user clicked.
	 * @return: the node at (x,y), or null if there is none.
	 */
	public Node getNode(int x, int y) {
		return graph.getNode(x, y);
	}

	/**
	 * Create a node centred on the given point (x,y). The node will not be
	 * created if it overlaps with any of the other nodes.
	 *
	 * @param x
	 *            : x part of the point.
	 * @param y
	 *            : y part of the point.
	 */
	public void addNode(int x, int y) {
		graph.createNode(x - Node.RADIUS, y - Node.RADIUS);
	}

	/**
	 * Create an edge going between the given nodes.
	 *
	 * @param n1
	 *            : first node.
	 * @param n2
	 *            : second node.
	 */
	public void addEdge(Node n1, Node n2) {
		graph.createEdge(n1, n2, areEdgesDirected, weight);
	}


	/**
	 * Gets the mode.
	 *
	 * @return: the mode that UI is in.
	 */
	public Mode getMode() {
		return mode;
	}

	/**
	 * Sets the mode.
	 *
	 * @param m
	 *            : the new mode.
	 */
	public void setMode(Mode m) {
		mode = m;
	}
	


	public Graph getGraph() {
		return this.graph;
	}


	
	
}
