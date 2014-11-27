package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import controller.GraphController;

/**
 * AlgorithmPane is one tab of the Sidebar. When AlgorithmPane is visible the user is able to specify
 * an algorithm from the dropdown box. There are four buttons:
 * 	- help: displays a pop-up that tells the user about the currently selected algorithm.
 *  - back: moves back one iteration through the algorithm.
 *  - step: moves forward one iteration through the algorithm.
 *  - run: jumps to the end of the algorithm.
 * AlgorithmPane also contains a text area which displays any relevant output at each stage of the
 * algorithm's execution.
 * @author craigthelinguist
 */
public class AlgorithmPane extends JPanel{

	// graphical constants
	private static final Font LABEL_FONT = GuiConstants.LABEL_FONT;
	private static final Font INPUT_FONT = GuiConstants.INPUT_FONT;
	
	// gui components
	private JTextArea output;
	private JComboBox<String> dropdown;
	private JTextArea textArea_output;
	
	// master
	private Sidebar master;
	
	public AlgorithmPane(Sidebar masterPanel){
		
		// set fields
		master = masterPanel;
		
		// create components
		JPanel panel_tophalf = this.setupTopHalf();
		JPanel panel_bothalf = this.setupBottomHalf();
		
		// create layout
		setPreferredSize(new Dimension(Sidebar.WIDTH,Sidebar.HEIGHT));
		GroupLayout layout = new GroupLayout(this);
		this.setLayout(layout);
		GroupLayout.SequentialGroup horizontal = layout.createSequentialGroup();
		GroupLayout.SequentialGroup vertical = layout.createSequentialGroup();
		layout.setHorizontalGroup(horizontal);
		layout.setVerticalGroup(vertical);
		layout.setAutoCreateContainerGaps(true);
		layout.setAutoCreateGaps(true);
		
		// add components to layout
		horizontal.addGroup(layout.createParallelGroup()
			.addComponent(panel_tophalf)
			.addComponent(panel_bothalf)
		);
		vertical.addComponent(panel_tophalf).addComponent(panel_bothalf);
			
	}

	/**
	 * Create and return a panel. The panel is a container for a dropdown to select the algorithm
	 * and some buttons to manipulate the algorithm.
	 * @return: JPanel
	 */
	private JPanel setupTopHalf(){
		
		// make components
		JPanel panel = new JPanel();
		JPanel panel_dropdown = this.setupLabelAndDropDown();
		JPanel panel_buttons = this.setupButtons();
		
		// make layout
		GroupLayout layout = new GroupLayout(panel);
		panel.setLayout(layout);
		layout.setAutoCreateGaps(true);
		GroupLayout.SequentialGroup horizontal = layout.createSequentialGroup();
		GroupLayout.SequentialGroup vertical = layout.createSequentialGroup();
		layout.setHorizontalGroup(horizontal);
		layout.setVerticalGroup(vertical);
		
		// add components
		horizontal.addGroup(layout.createParallelGroup()
			.addComponent(panel_dropdown)
			.addComponent(panel_buttons)
		);
		vertical.addComponent(panel_dropdown);
		vertical.addComponent(panel_buttons);
					
		return panel;
	}
	
	/**
	 * Makes and returns a panel. The panel contains a dropdown for selecting an algorithm.
	 * @return JPanel
	 */
	private JPanel setupLabelAndDropDown(){
		// make components
		JPanel panel = new JPanel();
		JLabel label = new JLabel("Algorithm:");
		label.setFont(LABEL_FONT);
		this.dropdown = new JComboBox<>(controller.AlgorithmMode.nameArray());
		dropdown.setFont(INPUT_FONT);
		
		// create the layout object
		GroupLayout layout = new GroupLayout(panel);
		panel.setLayout(layout);
		GroupLayout.SequentialGroup horizontal = layout.createSequentialGroup();
		GroupLayout.SequentialGroup vertical = layout.createSequentialGroup();
		layout.setHorizontalGroup(horizontal);
		layout.setVerticalGroup(vertical);
		
		// add components to layout
		horizontal.addComponent(label);
		horizontal.addComponent(dropdown);
		vertical.addGroup(layout.createParallelGroup(Alignment.BASELINE)
			.addComponent(label)
			.addComponent(dropdown)
		);
		
		return panel;
	}
	
	/**
	 * Makes and returns a panel. The panel contains four buttons "Help", "Back", "Step", and "Run"
	 * for performing the algorithm.
	 * @return JPanel
	 */
	private JPanel setupButtons(){

		// make components
		JPanel panel = new JPanel();
		JButton b_help = new JButton("Help");
		JButton b_run = new JButton("Run");
		JButton b_back = new JButton("Back");
		JButton b_step = new JButton("Step");
		
		// make layout
		GroupLayout layout = new GroupLayout(panel);
		panel.setLayout(layout);
		layout.setAutoCreateGaps(true);
		GroupLayout.SequentialGroup horizontal = layout.createSequentialGroup();
		GroupLayout.SequentialGroup vertical = layout.createSequentialGroup();
		layout.setHorizontalGroup(horizontal);
		layout.setVerticalGroup(vertical);
		
		// add components
		horizontal.addComponent(b_help);
		horizontal.addComponent(b_back);
		horizontal.addComponent(b_step);
		horizontal.addComponent(b_run);
		vertical.addGroup(layout.createParallelGroup(Alignment.CENTER)
			.addComponent(b_help)
			.addComponent(b_back)
			.addComponent(b_step)
			.addComponent(b_run)
		);
		
		// setup action listeners
		b_help.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent event) {
				master.sendButtonPress("help");
			}
		});
		
		b_run.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				master.sendButtonPress("run");
			}
		});
		
		b_back.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				master.sendButtonPress("back");
			}
		});
		
		b_step.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				master.sendButtonPress("step");
			}
		});
		
		dropdown.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				master.sendAlgorithmChange(dropdown.getSelectedItem().toString());
			}
		});
		
		return panel;
	}
	
	/**
	 * Create and return a panel. The panel contains a label and a JTextArea for outputting
	 * information about the algorithm being performed.
	 * @return JPanel
	 */
	private JPanel setupBottomHalf(){

		// components
		JPanel panel = new JPanel();
		JLabel label = new JLabel("Output");
		label.setFont(LABEL_FONT);
		this.output = new JTextArea("Sample output.\nAlgorithm info goes here.");
		output.setRows(8);
		output.setFont(INPUT_FONT);
		output.setEditable(false);
		output.setLineWrap(true);
		
		// make layout
		GroupLayout layout = new GroupLayout(panel);
		panel.setLayout(layout);
		GroupLayout.SequentialGroup horizontal = layout.createSequentialGroup();
		GroupLayout.SequentialGroup vertical = layout.createSequentialGroup();
		layout.setHorizontalGroup(horizontal);
		layout.setVerticalGroup(vertical);
		layout.setAutoCreateGaps(true);
		layout.setAutoCreateContainerGaps(true);
		
		// add components to layout
		horizontal.addGroup(layout.createParallelGroup(Alignment.LEADING)
			.addComponent(label)
			.addComponent(output)
		);
		vertical.addComponent(label).addComponent(output);
		
		return panel;
	}
	
	/**
	 * Append the given string to the end of the output text area.
	 * @param txt: string to be appended.
	 */
	public void println(String txt){
		textArea_output.append(txt);
	}
	
	/**
	 * Clears all of the text in the output text area.
	 */
	public void clearText(){
		textArea_output.setText("");
	}
	
	/**
	 * Get the name of the current dropdown selection.
	 * @return String
	 */
	public String getAlgorithmName(){
		String name = dropdown.getSelectedItem().toString();
		return name;
	}

}
