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
		
		@Override
		public String toString(){
			return this.name().toLowerCase();
		}
		
		public static Mode fromString(String name){
			name = name.toUpperCase();
			if (name.equals("ALGORITHMS")) return ALGORITHMS;
			else return GRAPHING;
		}
		
	}
	
	public enum AlgorithmMode {
		KRUSKALS, ASTAR;
		
		@Override
		public String toString(){
			return this.name().toLowerCase();
		}
		
		public static String[] nameArray(){
			return new String[]{ "Kruskals", "AStar" };
		}
		
		public static AlgorithmMode fromString(String name){
			name = name.toUpperCase();
			return valueOf(name);
		}
		
	}
	
	private AlgorithmMode modeAlgorithm;
	private Algorithm runningAlgorithm;
	
	private GraphGui gui;
	
	private final int MAX_SELECTABLE = 10;
	
	private LinkedList<Node> selection = new LinkedList<>();
	
	
	// graph data structure
	private Graph graph = new Graph();

	// mode keeps track of whether you're in algorithms mode or graphing mode
	private Mode mode = Mode.GRAPHING;

	private int weight = 1;
	private boolean areEdgesDirected = false;
	private AlgorithmFactory factory;
	
	public GraphController(){
		this.factory = new AlgorithmFactory();
	}
	
	public void setGUI(GraphGui gui){
		this.gui = gui;
	}
	
	
	public void buttonPress(String buttonName){

		if (gui == null) return;
		
		if (mode == Mode.ALGORITHMS){
			
			if (runningAlgorithm == null) createAlgorithm();
			else{
				switch (buttonName){
					case "step":
						runningAlgorithm.nextIteration();
						break;
					case "back":
						runningAlgorithm.previousIteration();
						break;
					case "run":
						runningAlgorithm.lastIteration();
						break;
				}
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
			
			switch (modeAlgorithm){
			
			case ASTAR:
				Node selected = graph.getNode(click.getX(), click.getY());
				int numSelected = selection.size();
				if (selected == null || numSelected >= 2){
					deselect();
				}
				else{
					selection.add(selected);
				}
				break;
			}
			
		}
		
		// clicking during algorithm execution
		else{}
		
		
		gui.repaint();
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
		int numSelected = selection.size();
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
	
	/**
	 * Change the current algorithm that has been selected.
	 * @param algorithmName
	 */
	public void changeAlgorithm(String algorithmName){
		if (gui == null) return;
		modeAlgorithm = AlgorithmMode.fromString(algorithmName);
		runningAlgorithm = null;
	}
	
	/**
	 * Change modes.
	 * @param name: name of the new mode.
	 */
	public void changeMode(String name) {
		if (gui == null) return;
		this.mode = Mode.fromString(name);
		if (mode == null) mode = Mode.GRAPHING;
		else if (mode == Mode.ALGORITHMS) changeAlgorithm(gui.currentOption());
		runningAlgorithm = null;
		gui.repaint();
	}

	/**
	 * Instantiate the currently-selected algorithm.
	 */
	private void createAlgorithm() {
		if (gui == null) return;
		try{
			this.runningAlgorithm = factory.setupAlgorithm(modeAlgorithm,graph,selection);
		}
		catch(SetupException se){
			gui.createErrorDialog(se.getMessage());
			this.runningAlgorithm = null;
			this.modeAlgorithm = null;
		}
	}


	public boolean runningAlgorithm(){
		return runningAlgorithm != null;
	}
	

	public void updateWeight(int weight) {
		this.weight = weight;
	}

	public void updateDirectedEdges(boolean directed){
		this.areEdgesDirected = directed;
	}


	public Algorithm getAlgorithm() {
		return runningAlgorithm;
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
	private void addNode(int x, int y) {
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
	private void addEdge(Node n1, Node n2) {
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


	public Graph getGraph() {
		return this.graph;
	}
	
}
