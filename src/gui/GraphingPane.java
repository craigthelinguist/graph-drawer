package gui;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.NumberFormat;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.NumberFormatter;

/**
 * GraphingPane is one tab of the Sidebar. It specifies options for drawing onto
 * GraphCanvas. The 'directed' checkbox specifies whether any edges created are
 * directed or not. The 'weight' text field specifies what the weight on any
 * created edges should be.
 * 
 * @author craigthelinguist
 */
public class GraphingPane extends JPanel {

	// graphical constants
	private Font LABEL_FONT = GuiConstants.LABEL_FONT;
	private Font INPUT_FONT = GuiConstants.INPUT_FONT;
	
	
	// master
	private Sidebar master;

	public GraphingPane(Sidebar masterPanel) {

		// set up fields
		master = masterPanel;

		// create components
		JPanel panel_tophalf = setupTopHalf();
		JPanel panel_bothalf = setupBottomHalf();
		
		// create layout
		setPreferredSize(new Dimension(Sidebar.WIDTH, Sidebar.HEIGHT));
		GroupLayout layout = new GroupLayout(this);
		this.setLayout(layout);
		GroupLayout.SequentialGroup horizontal = layout.createSequentialGroup();
		GroupLayout.SequentialGroup vertical = layout.createSequentialGroup();
		layout.setHorizontalGroup(horizontal);
		layout.setVerticalGroup(vertical);
		layout.setAutoCreateContainerGaps(true);
		layout.setAutoCreateGaps(true);
		
		// add components to layout
		horizontal.addGroup(layout.createParallelGroup(Alignment.CENTER)
			.addComponent(panel_tophalf)
			.addComponent(panel_bothalf));
		vertical.addComponent(panel_tophalf);
		vertical.addComponent(panel_bothalf);
		
	}

	/**
	 * Create and return a JPanel. The panel contains a checkbox for whether edges should be directed
	 * or not, and a textfield to specify the weight of the edges.
	 * @return JPanel
	 */
	private JPanel setupTopHalf(){
		
		// create components
		JPanel panel = new JPanel();
		final JCheckBox checkbox = new JCheckBox();
		JLabel l_directed = new JLabel("Directed:");
		l_directed.setFont(LABEL_FONT);
		JLabel l_weight = new JLabel("Weight:");
		l_weight.setFont(LABEL_FONT);

		// create custom formatter for the CheckBox
		NumberFormat format = NumberFormat.getInstance();
		NumberFormatter formatter = new NumberFormatter(format);
		formatter.setValueClass(Integer.class);
		final JTextField textfield = new JFormattedTextField(formatter);
		textfield.setFont(INPUT_FONT);
		textfield.setText("1");
		textfield.setMinimumSize(new Dimension(10, 10));
		
		// create layout
		GroupLayout layout = new GroupLayout(panel);
		panel.setLayout(layout);
		GroupLayout.SequentialGroup horizontal = layout.createSequentialGroup();
		GroupLayout.SequentialGroup vertical = layout.createSequentialGroup();
		layout.setHorizontalGroup(horizontal);
		layout.setVerticalGroup(vertical);
		layout.setAutoCreateGaps(true);

		// add components to layout
		horizontal.addGroup(layout.createParallelGroup()
			.addComponent(l_directed).addComponent(l_weight));
		horizontal.addGroup(layout.createParallelGroup()
			.addComponent(checkbox).addComponent(textfield));
		vertical.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
			.addComponent(l_directed).addComponent(checkbox));
		vertical.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
			.addComponent(l_weight).addComponent(textfield));

		// add action listeners
		textfield.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				int w = Integer.parseInt(textfield.getText());
				master.updateWeight(w);
			}
		});
		checkbox.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0){
				boolean directed = checkbox.isSelected();
				master.updateDirected(directed);
			}
		});
		
		return panel;
		
	}
	
	/**
	 * Create and return a JPanel. The panel contains buttons for saving and loading graphs.
	 * @return JPanel
	 */
	private JPanel setupBottomHalf(){

		// create components
		JPanel panel = new JPanel();
		JButton b_clear = new JButton("Clear");
		JButton b_save = new JButton("Save");
		JButton b_load = new JButton("Load");
		
		// create layout
		GroupLayout layout = new GroupLayout(panel);
		panel.setLayout(layout);
		GroupLayout.SequentialGroup horizontal = layout.createSequentialGroup();
		GroupLayout.SequentialGroup vertical = layout.createSequentialGroup();
		layout.setHorizontalGroup(horizontal);
		layout.setVerticalGroup(vertical);
		layout.setAutoCreateGaps(true);
		
		// add components to layout
		horizontal.addComponent(b_clear);
		horizontal.addComponent(b_save);
		horizontal.addComponent(b_load);
		vertical.addGroup(layout
				.createParallelGroup(Alignment.CENTER).addComponent(b_clear)
				.addComponent(b_save).addComponent(b_load));

		// add listeners
		b_clear.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				master.sendButtonPress("clear");
			}
		});
		
		return panel;
			
	}
	
	public static void main(String[] args) {

		JFrame frame = new JFrame("GraphingPane");
		frame.add(new GraphingPane(null));

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);

	}

}
