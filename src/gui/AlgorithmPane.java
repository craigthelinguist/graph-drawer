package gui;

import java.awt.Color;
import java.awt.Dimension;
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

	private JLabel label_algorithm;
	private JComboBox<String> dropdown;
	private JButton button_help;
	private JButton button_run;
	private JButton button_back;
	private JButton button_step;
	private JLabel label_output;
	private JTextArea textArea_output;
	private Sidebar sidebar;
	
	public AlgorithmPane(Sidebar masterPanel){
		sidebar = masterPanel;
		setPreferredSize(new Dimension(Sidebar.WIDTH,Sidebar.HEIGHT));
		
		/** TOP HALF
	 	 *  The top half is split into two halves 1 and 2 arranged one on top of the other.
	 	 *  panel_algorithm: a label and a drop-down (for selecting algorithm).
	 	 *  panel_buttons: 4 buttons: 'help', 'back', 'next', and 'run'. **/
		// 1: a panel containing a label and drop-down
			// components
			JPanel panel_algorithm = new JPanel();
			label_algorithm = new JLabel("Algorithm:");	
			label_algorithm.setFont(Sidebar.LABEL_FONT);
			dropdown = new JComboBox<>(controller.AlgorithmMode.nameArray());
			dropdown.setFont(Sidebar.INPUT_FONT);
			
			// group layout
			GroupLayout layout_algorithm = new GroupLayout(panel_algorithm);
			panel_algorithm.setLayout(layout_algorithm);
			
			// label and textfield laid out 1x2
			GroupLayout.SequentialGroup horizontal_algorithm = layout_algorithm.createSequentialGroup();
			GroupLayout.SequentialGroup vertical_algorithm = layout_algorithm.createSequentialGroup();
			layout_algorithm.setHorizontalGroup(horizontal_algorithm);
			layout_algorithm.setVerticalGroup(vertical_algorithm);
			horizontal_algorithm.addComponent(label_algorithm);
			horizontal_algorithm.addComponent(dropdown);
			vertical_algorithm.addGroup(layout_algorithm.createParallelGroup(Alignment.BASELINE)
				.addComponent(label_algorithm)
				.addComponent(dropdown)
			);
			
		// 2: a panel containing buttons.
			// components
			button_help = new JButton("Help");
			button_run = new JButton("Run");
			button_back = new JButton("Back");
			button_step = new JButton("Step");
			
			// group layout
			JPanel panel_buttons = new JPanel();
			GroupLayout layout_buttons = new GroupLayout(panel_buttons);
			panel_buttons.setLayout(layout_buttons);
			layout_buttons.setAutoCreateGaps(true);
			
			// buttons laid out 1x4
			GroupLayout.SequentialGroup horizontal_buttons = layout_buttons.createSequentialGroup();
			GroupLayout.SequentialGroup vertical_buttons = layout_buttons.createSequentialGroup();
			layout_buttons.setHorizontalGroup(horizontal_buttons);
			layout_buttons.setVerticalGroup(vertical_buttons);
			horizontal_buttons.addComponent(button_help);
			horizontal_buttons.addComponent(button_back);
			horizontal_buttons.addComponent(button_step);
			horizontal_buttons.addComponent(button_run);
			vertical_buttons.addGroup(layout_buttons.createParallelGroup(Alignment.CENTER)
				.addComponent(button_help)
				.addComponent(button_back)
				.addComponent(button_step)
				.addComponent(button_run)
			);
			
		// add panel_algorithm and panel_buttons to panel_topHalf
			// create panel_topHalf and set layout
			JPanel panel_topHalf = new JPanel();
			GroupLayout layout_topHalf = new GroupLayout(panel_topHalf);
			panel_topHalf.setLayout(layout_topHalf);
			layout_topHalf.setAutoCreateGaps(true);
			//layout_topHalf.setAutoCreateContainerGaps(true);
			
			// buttons and algorithm laid out 2x1
			GroupLayout.SequentialGroup horizontal_topHalf = layout_topHalf.createSequentialGroup();
			GroupLayout.SequentialGroup vertical_topHalf = layout_topHalf.createSequentialGroup();
			layout_topHalf.setHorizontalGroup(horizontal_topHalf);
			layout_topHalf.setVerticalGroup(vertical_topHalf);
			horizontal_topHalf.addGroup(layout_topHalf.createParallelGroup()
					.addComponent(panel_algorithm)
					.addComponent(panel_buttons)
			);
			vertical_topHalf.addComponent(panel_algorithm);
			vertical_topHalf.addComponent(panel_buttons);
					
		/** BOTTOM HALF
		 *  The bottom half contains two things: a label that says 'output
		 *  and a text area. **/
			
		// second part is a label "output", apreferredSizend a textArea.
			// components
			label_output = new JLabel("Output");
			label_output.setFont(Sidebar.LABEL_FONT);
			textArea_output = new JTextArea("Sample output.\nAlgorithm info goes here.");
			textArea_output.setRows(8);
			textArea_output.setFont(Sidebar.INPUT_FONT);
			textArea_output.setEditable(false);
			textArea_output.setLineWrap(true);
			
			// label and textArea arranged 1x2
			JPanel panel_botHalf = new JPanel();
			GroupLayout layout_bottom = new GroupLayout(panel_botHalf);
			panel_botHalf.setLayout(layout_bottom);
			GroupLayout.SequentialGroup horizontal_bottom = layout_bottom.createSequentialGroup();
			GroupLayout.SequentialGroup vertical_bottom = layout_bottom.createSequentialGroup();
			layout_bottom.setHorizontalGroup(horizontal_bottom);
			layout_bottom.setVerticalGroup(vertical_bottom);
			layout_bottom.setAutoCreateGaps(true);
			layout_bottom.setAutoCreateContainerGaps(true);
			horizontal_bottom.addGroup(layout_bottom.createParallelGroup(Alignment.LEADING)
				.addComponent(label_output)
				.addComponent(textArea_output)
			);
			vertical_bottom.addComponent(label_output).addComponent(textArea_output);
			
		/** PANE
		 *  The entire AlgorithmPane contains two components:
		 *  panel_topHalf: buttons and a drop-down.
		 *  panel_botHalf: a label and a text-area. **/
		// add topHalf and  bottomHalf to 'this'.
			// create layout
			GroupLayout layout_pane = new GroupLayout(this);
			this.setLayout(layout_pane);
			GroupLayout.SequentialGroup horizontal_pane = layout_pane.createSequentialGroup();
			GroupLayout.SequentialGroup vertical_pane = layout_pane.createSequentialGroup();
			layout_pane.setHorizontalGroup(horizontal_pane);
			layout_pane.setVerticalGroup(vertical_pane);
			layout_pane.setAutoCreateContainerGaps(true);
			layout_pane.setAutoCreateGaps(true);
		
			// arranged 1x2
			horizontal_pane.addGroup(layout_pane.createParallelGroup()
				.addComponent(panel_topHalf)
				.addComponent(panel_botHalf)
			);
			vertical_pane.addComponent(panel_topHalf).addComponent(panel_botHalf);
		
		setupListeners();
			
	}

	private void setupListeners(){
		
		
		button_help.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent event) {
				sidebar.sendButtonPress("help");
			}
			
		});
		
		button_run.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				sidebar.sendButtonPress("run");
			}
		
		});
		
		button_back.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				sidebar.sendButtonPress("back");
			}
		
		});
		
		button_step.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				sidebar.sendButtonPress("step");
			}
		
		});
		
		dropdown.addActionListener(new ActionListener(){
			
			@Override
			public void actionPerformed(ActionEvent e){
				sidebar.sendAlgorithmChange(dropdown.getSelectedItem().toString());
			}
			
		});
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
	
	
	public String getAlgorithmName(){
		String name = dropdown.getSelectedItem().toString();
		return name;
	}
	
	public String toString(){
		return "algorithms";
	}

}
