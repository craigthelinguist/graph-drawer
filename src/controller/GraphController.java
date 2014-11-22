package controller;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JOptionPane;

import algorithms.AStar;
import algorithms.Algorithm;
import algorithms.Kruskals;
import graph.Graph;
import graph.Node;
import gui.GraphCanvas;
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


	private final int MAX_SELECTABLE = 10;
	
	private LinkedList<Node> selection = new LinkedList<>();
	
	
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

		gui.repaint();
		
	}
	
	public void mousePressed(MouseEvent click) {
		
		Mode mode = getMode();
		if (mode == Mode.GRAPHING) drawOnScreen(click);
		
		// need to select inputs
		else if (mode == Mode.ALGORITHMS && !runningAlgorithm()){
			
			String name = getSelectedAlgorithm();
			if (name.equals("A* Pathfinding")){
				
				Node selected = graph.getNode(click.getX(), click.getY());
				int numSelected = numberSelected();
				if (selected == null || numSelected >= 2){
					deselect();
				}
				else{
					selection.add(selected);
				}
			}
			
		}
		
		// clicking during algorithm execution
		else{}
		gui.repaint();
	}
	
	
	
	

	/**
	 * Returns the number of nodes that are currently selected.
	 */
	public int numberSelected() {
		return selection.size();
	}

	/**
	 * Deselects everything.
	 */
	public void deselect() {
		selection = new LinkedList<>();
	}
	
	public List<Node> getSelection(){
		return this.selection;
	}
	
	
	
	

	/**
	 * User has drawn something on the screen.
	 * @param click
	 */
	public void drawOnScreen(MouseEvent click){
		
		Node selected = graph.getNode(click.getX(), click.getY());
		int numSelected = numberSelected();
		// clicked nothing
		if (selected == null && numSelected > 0) {
			deselect();
		}
		// selected two nodes: create an edge and deselect.
		else if (numSelected == 1) {
			// check if you clicked on an already-selected node
			if (selection.get(0) == selected) {
				selection.add(0,selected);
			}
			// otherwise add an edge between the two nodes.
			else {
				addEdge(selection.get(0), selected);
				deselect();
			}
		}
		// selected no nodes: highlight the cilcked node.
		else if (numSelected == 0 && selected != null) {
			selection.add(0,selected);
		}
		// selected no nodes, clicked nothing: create a node at that
		// spot.
		else if (numSelected == 0 && selected == null) {
			addNode(click.getX(), click.getY());
		}

	}
	
	public void changeAlgorithm(String algorithmName){

		if (gui == null) return;
		
		algorithm = null;
		selectedAlgorithm = algorithmName;
		createAlgorithm();
	}
	
	public void changeMode(String name) {

		if (gui == null) return;
		
		System.out.println("Mode change: " + name);
		if (name.equals("algorithms")) {
			mode = Mode.ALGORITHMS;
			deselect();
		} else if (name.equals("graphing")) {
			mode = Mode.GRAPHING;
		}
		algorithm = null;
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
			if (selection.size() != 2) return;
			Node start = selection.get(0);
			Node goal = selection.get(1);
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
