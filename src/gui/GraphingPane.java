package gui;

import java.awt.Dimension;
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

	private JCheckBox chk_directed;
	private JLabel label_directed;
	private JLabel label_weight;
	private JFormattedTextField field_weight;
	private JButton btn_clear;
	private JButton btn_save;
	private JButton btn_load;
	private Sidebar sidebar;

	public GraphingPane(Sidebar masterPanel) {
		sidebar = masterPanel;
		setPreferredSize(new Dimension(Sidebar.WIDTH, Sidebar.HEIGHT));

		chk_directed = new JCheckBox();
		label_directed = new JLabel("Directed:");
		label_directed.setFont(Sidebar.LABEL_FONT);
		label_weight = new JLabel("Weight:");
		label_weight.setFont(Sidebar.LABEL_FONT);

		NumberFormat format = NumberFormat.getInstance();
		NumberFormatter formatter = new NumberFormatter(format);
		formatter.setValueClass(Integer.class);
		field_weight = new JFormattedTextField(formatter);
		field_weight.setFont(Sidebar.INPUT_FONT);
		field_weight.setText("1");
		field_weight.setMinimumSize(new Dimension(10, 10));

		btn_clear = new JButton("Clear");
		btn_save = new JButton("Save");
		btn_load = new JButton("Load");

		/** TOP HALF **/
		// setup Grouplayout for top half
		JPanel topHalf = new JPanel();
		GroupLayout layout = new GroupLayout(topHalf);
		topHalf.setLayout(layout);
		GroupLayout.SequentialGroup horizontal = layout.createSequentialGroup();
		GroupLayout.SequentialGroup vertical = layout.createSequentialGroup();
		layout.setHorizontalGroup(horizontal);
		layout.setVerticalGroup(vertical);
		layout.setAutoCreateGaps(true);
		// layout.setAutoCreateContainerGaps(true);

		// columns, left-to-right
		horizontal.addGroup(layout.createParallelGroup()
				.addComponent(label_directed).addComponent(label_weight));
		horizontal.addGroup(layout.createParallelGroup()
				.addComponent(chk_directed).addComponent(field_weight));

		// rows, top-to-bottom
		vertical.addGroup(layout
				.createParallelGroup(GroupLayout.Alignment.BASELINE)
				.addComponent(label_directed).addComponent(chk_directed));
		vertical.addGroup(layout
				.createParallelGroup(GroupLayout.Alignment.BASELINE)
				.addComponent(label_weight).addComponent(field_weight));

		/** BOTTOM HALF **/
		JPanel bottomHalf = new JPanel();
		GroupLayout bottomLayout = new GroupLayout(bottomHalf);
		bottomHalf.setLayout(bottomLayout);
		GroupLayout.SequentialGroup bottom_horizontal = bottomLayout
				.createSequentialGroup();
		GroupLayout.SequentialGroup bottom_vertical = bottomLayout
				.createSequentialGroup();
		bottomLayout.setHorizontalGroup(bottom_horizontal);
		bottomLayout.setVerticalGroup(bottom_vertical);
		bottomLayout.setAutoCreateGaps(true);

		// cols for panel
		bottom_horizontal.addComponent(btn_clear);
		bottom_horizontal.addComponent(btn_save);
		bottom_horizontal.addComponent(btn_load);

		// rows for panel
		bottom_vertical.addGroup(bottomLayout
				.createParallelGroup(Alignment.CENTER).addComponent(btn_clear)
				.addComponent(btn_save).addComponent(btn_load));

		/** ENTIRE PANE **/
		// Grouplayout for GraphingPane
		GroupLayout paneLayout = new GroupLayout(this);
		this.setLayout(paneLayout);
		GroupLayout.SequentialGroup pane_horizontal = paneLayout
				.createSequentialGroup();
		GroupLayout.SequentialGroup pane_vertical = paneLayout
				.createSequentialGroup();
		paneLayout.setHorizontalGroup(pane_horizontal);
		paneLayout.setVerticalGroup(pane_vertical);
		paneLayout.setAutoCreateContainerGaps(true);
		paneLayout.setAutoCreateGaps(true);
		pane_horizontal.addGroup(paneLayout
				.createParallelGroup(Alignment.CENTER).addComponent(topHalf)
				.addComponent(bottomHalf));

		pane_vertical.addComponent(topHalf);
		pane_vertical.addComponent(bottomHalf);

		addListeners();
		
	}

	private void addListeners(){
		
		btn_clear.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				sidebar.sendButtonPress("clear");
			}
			
		});
		
		field_weight.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				int w = Integer.parseInt(field_weight.getText());
				sidebar.updateWeight(w);
			}
			
		});
		
		
		chk_directed.addActionListener(new ActionListener(){
			
			@Override
			public void actionPerformed(ActionEvent arg0){
				boolean directed = chk_directed.isSelected();
				sidebar.updateDirected(directed);
			}
			
		});
		
	}
	
	public String toString() {
		return "graphing";
	}

	public static void main(String[] args) {

		JFrame frame = new JFrame("GraphingPane");
		frame.add(new GraphingPane(null));

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);

	}

}
