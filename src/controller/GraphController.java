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

	
	// keeps track of whether you are graphing or running an algorithm.
	private Mode mode = Mode.GRAPHING;
	private AlgorithmMode modeAlgorithm;

	// the current algorithm being performed; a factory for instantiating them.
	private Algorithm runningAlgorithm;
	private AlgorithmFactory factory;
	
	// the graph being operated upon
	private Graph graph = new Graph();
	private LinkedList<Node> selectedNodes = new LinkedList<>();
	
	// the view
	private GraphGui gui = null;
	
	public GraphController(){
		this.factory = new AlgorithmFactory();
	}
	
	public void setGUI(GraphGui gui){
		if (this.gui == null) this.gui = gui;
		else throw new RuntimeException("This controller already belongs to a gui.");
	}
	
	/**
	 * Respond to a button event.
	 * @param buttonName: name of the button that was pressed.
	 */
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
			switch (buttonName){
				case "clear":
					graph = new Graph();
			}	
		}
		gui.repaint();
	}
	
	/**
	 * Respond to a mouse event.
	 * @param click: the mouse event.
	 */
	public void mousePressed(MouseEvent click) {
		Mode mode = getMode();
		if (mode == Mode.GRAPHING) drawOnScreen(click.getX(),click.getY());
		// need to select inputs
		else if (mode == Mode.ALGORITHMS && !isRunningAlgorithm()){
			switch (modeAlgorithm){
				case ASTAR:
					Node selected = graph.getNode(click.getX(), click.getY());
					int numSelected = selectedNodes.size();
					if (selected == null || numSelected >= 2){
						deselect();
					}	
					else{
						selectedNodes.add(selected);
					}
					break;
				case KRUSKALS:
					break;
				default:
					break;
				}
		}
		else{}		// clicking during algorithm execution
		gui.repaint();
	}
	
	

	/**
	 * Deselects everything.
	 */
	public void deselect() {
		selectedNodes = new LinkedList<>();
	}
	
	public List<Node> getSelection(){
		return this.selectedNodes;
	}
	
	/**
	 * Respond to an event where something was drawn on the screen.
	 * @param (x,y): point on the screen where the event happened.
	 */
	public void drawOnScreen(int x, int y){
		Node selected = graph.getNode(x,y);
		int numSelected = selectedNodes.size();
		
		// clicked nothing
		if (selected == null && numSelected > 0) {
			deselect();
		}
		
		// selected two nodes: create an edge if they're two different nodes
		else if (numSelected == 1) {
			if (selectedNodes.get(0) != selected) addEdge(selectedNodes.get(0), selected);
			deselect();
		}
		
		// selected no nodes: highlight the cilcked node.
		else if (numSelected == 0 && selected != null) {
			selectedNodes.add(0,selected);
		}
		
		// selected no nodes, clicked nothing: create a node at that spot
		else if (numSelected == 0 && selected == null) {
			addNode(x,y);
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
			this.runningAlgorithm = factory.setupAlgorithm(modeAlgorithm,graph,selectedNodes);
		}
		catch(SetupException se){
			gui.createErrorDialog(se.getMessage());
			this.runningAlgorithm = null;
			this.modeAlgorithm = null;
		}
	}


	public boolean isRunningAlgorithm(){
		return runningAlgorithm != null;
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
		graph.createEdge(n1, n2, gui.getDirected(), gui.getWeight());
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
