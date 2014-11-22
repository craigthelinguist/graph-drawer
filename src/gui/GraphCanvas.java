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
import java.util.List;

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

	private GraphGui gui;
	private GraphController controller;
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
			outlineSelection(g);
		}
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
			controller.mousePressed(click);
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
	public void outlineSelection(Graphics g) {
		List<Node> selection = controller.getSelection();
		for (Node node : selection) {
			if (node != null)
				node.outline(g);
			else
				return;
		}
	}	
	

}
