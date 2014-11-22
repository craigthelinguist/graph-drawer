package gui;

import graph.Edge;
import graph.Graph;
import graph.Node;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import controller.GraphController;

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
public class GraphGui extends JFrame {


	public static Color BABY_BLUE = new Color(227, 247, 255);
	
	// gui components
	private GraphCanvas canvas;
	private Sidebar sidebar;
	
	private GraphController controller;

	
	public GraphGui(GraphController controller) {
		this.controller = controller;
		canvas = new GraphCanvas(this, controller);
		sidebar = new Sidebar(this, controller);
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
	
	
	
	
	
	
	
	public static void main(String[] args){
		GraphController controller = new GraphController();
		GraphGui gui = new GraphGui(controller);
		controller.setGUI(gui);
	}
	
	

	public void buttonPress(String buttonName){
		controller.buttonPress(buttonName);
		canvas.repaint();
	}









	public GraphCanvas getCanvas() {
		return this.canvas;
	}
	
	


}