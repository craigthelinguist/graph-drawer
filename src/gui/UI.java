package gui;

import graph.Edge;
import graph.Graph;
import graph.Node;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import algorithms.AStar;
import algorithms.Algorithm;
import algorithms.Kruskals;

/**
 * UI functions as the controller for all gui components. When any part of the
 * gui receives an action or buttonName, it sends that information to UI, which then
 * tells all the components how to react.
 *
 * @author craigthelinguist
 */
public class UI extends JFrame {

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

	// gui components
	private GraphCanvas canvas;
	private Sidebar sidebar;

	// gui constants
	public static final String[] algorithms = { "", "A* Pathfinding",
			"Kruskal's Algorithm", "Articulation Points" };
	public static Color BABY_BLUE = new Color(227, 247, 255);

	// graph data structure
	private Graph graph = new Graph();
	private Algorithm algorithm;

	// mode keeps track of whether you're in algorithms mode or graphing mode
	private Mode mode = Mode.GRAPHING;
	private String selectedAlgorithm = "";

	public UI() {
		canvas = new GraphCanvas(this);
		sidebar = new Sidebar(this);
		BorderLayout layout = new BorderLayout();
		setLayout(layout);
		add(canvas, BorderLayout.CENTER);
		add(sidebar, BorderLayout.EAST);
		setName("Algorithm Illustrator");
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		pack();
		setVisible(true);
	}

	public void buttonPress(String buttonName){
		
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
		
		canvas.repaint();
		
	}
	
	public void algorithmChange(String algorithmName){
		algorithm = null;
		selectedAlgorithm = algorithmName;
		createAlgorithm();
		canvas.repaint();
	}
	
	public void modeChange(String name) {
		System.out.println("Mode change: " + name);
		if (name.equals("algorithms")) {
			mode = Mode.ALGORITHMS;
			canvas.deselect();
		} else if (name.equals("graphing")) {
			mode = Mode.GRAPHING;
		}
		algorithm = null;
		canvas.repaint();
	}

	/**
	 * Creates an algorithm based on the value in the text field.
	 */
	private void createAlgorithm() {
		
		String name = getSelectedAlgorithm();
		System.out.println("selected algorithm is " + name);
		selectedAlgorithm = name;
		
		if (name.equals(algorithms[1])){
			Node start = canvas.getSelected(0);
			Node goal = canvas.getSelected(1);
			if (start == null || goal == null){
				JOptionPane.showMessageDialog(this, "You must select a start node and an end node for A*. Select nodes by left-clicking them.");
				return;
			}
			algorithm = new AStar(graph,start,goal);
		}
		else if (name.equals(algorithms[2])) {
			algorithm = new Kruskals(graph);
		}
		else{
			algorithm = null;
			selectedAlgorithm = "";
		}
	}

	/**
	 * Return the node at the location (x,y), or null if there is none.
	 *
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
		boolean directed = sidebar.areEdgesDirected();
		int weight = sidebar.getEdgeWeight();
		graph.createEdge(n1, n2, directed, weight);
	}

	/**
	 * Draw the graph.
	 *
	 * @param g
	 *            : object on which to draw.
	 */
	public void drawGraph(Graphics g) {
		if (mode == Mode.GRAPHING || algorithm == null) {
			graph.draw(g);
		}
		else{
			algorithm.draw(g);
		}
	}

	/**
	 * Outline all selected nodes.
	 *
	 * @param g
	 *            : object on which to draw.
	 * @param selection
	 *            : a list of nodes that should be outlined.
	 */
	public void outlineSelection(Graphics g, Node[] selection) {
		for (Node node : selection) {
			if (node != null)
				node.outline(g);
			else
				return;
		}
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
	
	public boolean runningAlgorithm(){
		return algorithm != null;
	}
	
	public String getSelectedAlgorithm(){
		return selectedAlgorithm;
	}

	public static void main(String[] args) {
		new UI();
	}
}