package gui;

import graph.Graph;
import graph.Node;
import controller.GraphController.Mode;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.LinkedList;

import javax.swing.JPanel;

import controller.GraphController;

import algorithms.Algorithm;

/**
 * GraphCanvas represents the part of the gui that displays the graph data
 * structure and depicts what an algorithm is doing at each step. It is
 * responsible for drawing the graph on the screen. The user can draw onto
 * GraphCanvas through clicking and dragging. Any such events are passed up to
 * AlgorithmPane which stores and controls the relevant information.
 *
 * @author craigthelinguist
 */
public class GraphCanvas extends JPanel {

	private final int MAX_SELECTABLE = 10;
	private GraphGui gui;
	private GraphController controller;
	private LinkedList<Node> selection = new LinkedList<>();
	private CanvasListener mouseListener;

	public GraphCanvas(GraphGui gui, GraphController controller) {
		this.gui = gui;
		this.controller = controller;
		setPreferredSize(new Dimension(600, 600));
		setBackground(GraphGui.BABY_BLUE);
		mouseListener = new CanvasListener();
		this.addMouseListener(mouseListener);
	}

	@Override
	protected void paintComponent(Graphics g) {
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, this.getWidth(), this.getHeight());
		drawGraph(g);
		if (controller.getMode() == Mode.GRAPHING || !controller.runningAlgorithm()) {
			outlineSelection(g, selection);
		}
	}

	/**
	 * Returns the number of nodes that are currently selected.
	 */
	private int numberSelected() {
		return selection.size();
	}

	/**
	 * Deselects everything.
	 */
	public void deselect() {
		selection = new LinkedList<>();
	}

	private class CanvasListener implements MouseListener {

		@Override
		public void mouseClicked(MouseEvent click) {

		}

		@Override
		public void mouseEntered(MouseEvent click) {

		}

		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mousePressed(MouseEvent click) {
			
			Mode mode = controller.getMode();
			if (mode == Mode.GRAPHING) drawOnScreen(click);
			
			// need to select inputs
			else if (mode == Mode.ALGORITHMS && !controller.runningAlgorithm()){
				
				String name = controller.getSelectedAlgorithm();
				if (name.equals("A* Pathfinding")){

					Node selected = controller.getNode(click.getX(), click.getY());
					int numSelected = numberSelected();
					if (selected == null || numSelected >= 2){
						deselect();
					}
					else selection.add(selected);

					System.out.println("Amount selected is: " + numberSelected());
					
				}
				
			}
			
			// clicking during algorithm execution
			else{}
			
			GraphCanvas.this.repaint();
				
		}

			
		
		private void drawOnScreen(MouseEvent click){

			Node selected = controller.getNode(click.getX(), click.getY());
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
					controller.addEdge(selection.get(0), selected);
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
				controller.addNode(click.getX(), click.getY());
			}

		
		}

		@Override
		public void mouseReleased(MouseEvent e) {

		}

	}

	/**
	 * Draw the graph.
	 *
	 * @param g
	 *            : object on which to draw.
	 */
	public void drawGraph(Graphics g) {
		Mode mode = controller.getMode();
		Algorithm algorithm = controller.getAlgorithm();
		if (mode == Mode.GRAPHING || algorithm == null) {
			Graph graph = controller.getGraph();
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
	public void outlineSelection(Graphics g, LinkedList<Node> selection) {
		for (Node node : selection) {
			if (node != null)
				node.outline(g);
			else
				return;
		}
	}
	public Node getSelected(int index){
		return selection.get(index);
	}
	
}
