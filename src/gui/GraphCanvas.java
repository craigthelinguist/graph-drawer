package gui;

import graph.Node;
import gui.UI.Mode;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JPanel;

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
	private UI controller;
	private Node[] selection = new Node[MAX_SELECTABLE];
	private CanvasListener mouseListener;

	public GraphCanvas(UI ui) {
		controller = ui;
		setPreferredSize(new Dimension(600, 600));
		setBackground(UI.BABY_BLUE);
		mouseListener = new CanvasListener();
		this.addMouseListener(mouseListener);
	}

	@Override
	protected void paintComponent(Graphics g) {
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, this.getWidth(), this.getHeight());
		controller.drawGraph(g);
		if (controller.getMode() == Mode.GRAPHING || !controller.runningAlgorithm()) {
			controller.outlineSelection(g, selection);
		}
	}

	/**
	 * Returns the number of nodes that are currently selected.
	 */
	private int numberSelected() {
		int i = 0;
		for (; i < selection.length; i++)
			if (selection[i] == null)
				return i;
		return i;
	}

	/**
	 * Deselects everything.
	 */
	public void deselect() {
		selection = new Node[MAX_SELECTABLE];
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
					if (selected == null || numSelected == 2){
						deselect();
					}
					else if (numSelected == 0){
						selection[0] = selected;
					}
					else if (numSelected == 1){
						selection[1] = selected;
					}

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
				if (selection[0] == selected) {
					selection[0] = selected;
				}

				// otherwise add an edge between the two nodes.
				else {
					controller.addEdge(selection[0], selected);
					deselect();
				}
			}
			// selected no nodes: highlight the cilcked node.
			else if (numSelected == 0 && selected != null) {
				selection[0] = selected;
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

	public Node getSelected(int index){
		return selection[index];
	}
	
}
