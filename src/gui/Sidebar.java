package gui;



import gui.GraphGui.Mode;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.KeyEvent;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * Sidebar represents the option pane visible to the user. The option pane is represented
 * by two main panels: AlgorithmPane and GraphingPane. Sidebar lets you move back and
 * forward between the two modes by clicking on the 'algorithm' tab or the 'graphing' tab.
 * Whenenver an event happens in AlgorithmPane or GraphingPane, the event is passed up to
 * Sidebar, which then passes it up to GraphGui.
 * @author craigthelingGraphGuist
 */
public class Sidebar extends JTabbedPane{

	// gGraphGui constants
	public static final int WIDTH = 300;
	public static final int HEIGHT = 600;
	public static final Font LABEL_FONT = new Font("Century Gothic",Font.PLAIN,16);
	public static final Font INPUT_FONT = new Font("Garamond",Font.PLAIN,13);

	// components
	private AlgorithmPane algorithmPane;
	private GraphingPane graphingPane;
	private GraphGui controller;
	private Mode mode;

	public Sidebar(GraphGui GraphGui){
		controller = GraphGui;
		mode = Mode.GRAPHING;
		setSize(Sidebar.WIDTH,Sidebar.HEIGHT);
		setPreferredSize(new Dimension(Sidebar.WIDTH,Sidebar.HEIGHT));

		algorithmPane = new AlgorithmPane(this);
		graphingPane = new GraphingPane(this);

		this.setBackground(GraphGui.BABY_BLUE);
		addTab("Graphing", null, graphingPane, "Let's you draw a graph.");
		setMnemonicAt(0, KeyEvent.VK_G);
		addTab("Algorithms", null, algorithmPane, "Let's you select and run an algorithm.");
		setMnemonicAt(1, KeyEvent.VK_A);

		addChangeListener(new ChangeListener(){

			public void stateChanged(ChangeEvent e){
				controller.modeChange(getTab());
			}

		});

	}

	private String getTab(){
		return this.getSelectedComponent().toString();
	}
	
	public void sendButtonPress(String event){
		controller.buttonPress(event);
	}
	
	public void sendAlgorithmChange(String event){
		controller.algorithmChange(event);
	}
	
	/**
	 * Check whether any drawn edges should be directed.
	 * @return: true if edges should be directed, false otherwise.
	 */
	public boolean areEdgesDirected(){
		return graphingPane.areEdgesDirected();
	}
	/**
	 * Returns the number in the edge weight text field, specifying what the weight
	 * of any created edges should be.
	 * @return: an integer.
	 */
	public int getEdgeWeight(){
		return graphingPane.getEdgeWeight();
	}

	public String getAlgorithmName(){
		return algorithmPane.getAlgorithmName();
	}
	
	/**
	 * Tells AlgorithmPane to append a string to its text area.
	 * @param txt: the string to be appended.
	 */
	public void printOutput(String txt){
		algorithmPane.println(txt);
	}
	/**
	 * Tells AlgorithmPane to clear its text area.
	 */
	public void clearOutput(){
		algorithmPane.clearText();
	}
}
