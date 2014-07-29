package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
<<<<<<< HEAD
import java.text.NumberFormat;
=======
>>>>>>> b591dfcf71ff370b110078e118f91afd18f3c931

import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;
<<<<<<< HEAD
import javax.swing.text.NumberFormatter;
=======
>>>>>>> b591dfcf71ff370b110078e118f91afd18f3c931

/**
 * GraphingPane is one tab of the Sidebar. It specifies options for drawing onto GraphCanvas.
 * The 'directed' checkbox specifies whether any edges created are directed or not. The
 * 'weight' text field specifies what the weight on any created edges should be.
 * @author craigthelinguist
 */
public class GraphingPane extends JPanel{
<<<<<<< HEAD

=======
	
>>>>>>> b591dfcf71ff370b110078e118f91afd18f3c931
	private JCheckBox chk_directed;
	private JLabel label_directed;
	private JLabel label_weight;
	private JFormattedTextField field_weight;
	private JButton btn_clear;
	private JButton btn_save;
	private JButton btn_load;
	private Sidebar sidebar;
<<<<<<< HEAD

	public GraphingPane(Sidebar masterPanel){
		sidebar = masterPanel;
		setPreferredSize(new Dimension(Sidebar.WIDTH,Sidebar.HEIGHT));

=======
	
	public GraphingPane(Sidebar masterPanel){
		sidebar = masterPanel;
		setPreferredSize(new Dimension(Sidebar.WIDTH,Sidebar.HEIGHT));
	
>>>>>>> b591dfcf71ff370b110078e118f91afd18f3c931
		/** COMPONENTS **/
		chk_directed = new JCheckBox();
		label_directed = new JLabel("Directed:");
		label_directed.setFont(Sidebar.LABEL_FONT);
		label_weight = new JLabel("Weight:");
		label_weight.setFont(Sidebar.LABEL_FONT);
<<<<<<< HEAD

		NumberFormat format = NumberFormat.getInstance();
		NumberFormatter formatter = new NumberFormatter(format);
		formatter.setValueClass(Integer.class);
		field_weight = new JFormattedTextField(formatter);
		field_weight.setFont(Sidebar.INPUT_FONT);
		field_weight.setText("1");
		field_weight.setMinimumSize( new Dimension(10, 10) );

=======
		
		
		field_weight = new JFormattedTextField(5);
		field_weight.setFont(Sidebar.INPUT_FONT);
		field_weight.setText("Entry");
		field_weight.setMinimumSize( new Dimension(10, 10) );
		
>>>>>>> b591dfcf71ff370b110078e118f91afd18f3c931
		btn_clear = new JButton("Clear");
		btn_save = new JButton("Save");
		btn_load = new JButton("Load");

<<<<<<< HEAD

=======
		
>>>>>>> b591dfcf71ff370b110078e118f91afd18f3c931
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
		//layout.setAutoCreateContainerGaps(true);
<<<<<<< HEAD

=======
		
>>>>>>> b591dfcf71ff370b110078e118f91afd18f3c931
		// columns, left-to-right
		horizontal.addGroup(
			layout.createParallelGroup()
				.addComponent(label_directed)
				.addComponent(label_weight)
		);
		horizontal.addGroup(
			layout.createParallelGroup()
				.addComponent(chk_directed)
				.addComponent(field_weight)
		);
<<<<<<< HEAD

=======
		
>>>>>>> b591dfcf71ff370b110078e118f91afd18f3c931
		// rows, top-to-bottom
		vertical.addGroup(
			layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
				.addComponent(label_directed)
				.addComponent(chk_directed)
		);
		vertical.addGroup(
			layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
				.addComponent(label_weight)
				.addComponent(field_weight)
		);
<<<<<<< HEAD


		/** BOTTOM HALF **/

=======
		
		
		/** BOTTOM HALF **/
		
>>>>>>> b591dfcf71ff370b110078e118f91afd18f3c931
		JPanel bottomHalf = new JPanel();
		GroupLayout bottomLayout = new GroupLayout(bottomHalf);
		bottomHalf.setLayout(bottomLayout);
		GroupLayout.SequentialGroup bottom_horizontal = bottomLayout.createSequentialGroup();
		GroupLayout.SequentialGroup bottom_vertical = bottomLayout.createSequentialGroup();
		bottomLayout.setHorizontalGroup(bottom_horizontal);
		bottomLayout.setVerticalGroup(bottom_vertical);
		bottomLayout.setAutoCreateGaps(true);
<<<<<<< HEAD

=======
		
>>>>>>> b591dfcf71ff370b110078e118f91afd18f3c931
		// cols for panel
		bottom_horizontal.addComponent(btn_clear);
		bottom_horizontal.addComponent(btn_save);
		bottom_horizontal.addComponent(btn_load);
<<<<<<< HEAD

=======
		
>>>>>>> b591dfcf71ff370b110078e118f91afd18f3c931
		// rows for panel
		bottom_vertical.addGroup(bottomLayout.createParallelGroup(Alignment.CENTER)
			.addComponent(btn_clear)
			.addComponent(btn_save)
			.addComponent(btn_load)
		);
<<<<<<< HEAD

		/** ENTIRE PANE **/

		// Grouplayout for GraphingPane
=======
		
		/** ENTIRE PANE **/
		
		// Grouplayout for GraphingPane 
>>>>>>> b591dfcf71ff370b110078e118f91afd18f3c931
		GroupLayout paneLayout = new GroupLayout(this);
		this.setLayout(paneLayout);
		GroupLayout.SequentialGroup pane_horizontal = paneLayout.createSequentialGroup();
		GroupLayout.SequentialGroup pane_vertical = paneLayout.createSequentialGroup();
		paneLayout.setHorizontalGroup(pane_horizontal);
		paneLayout.setVerticalGroup(pane_vertical);
		paneLayout.setAutoCreateContainerGaps(true);
		paneLayout.setAutoCreateGaps(true);
<<<<<<< HEAD

=======
		
>>>>>>> b591dfcf71ff370b110078e118f91afd18f3c931
		pane_horizontal.addGroup(paneLayout.createParallelGroup(Alignment.CENTER)
			.addComponent(topHalf)
			.addComponent(bottomHalf)
		);
<<<<<<< HEAD

		pane_vertical.addComponent(topHalf);
		pane_vertical.addComponent(bottomHalf);


	}

=======
		
		pane_vertical.addComponent(topHalf);
		pane_vertical.addComponent(bottomHalf);
		
		
	}
	
>>>>>>> b591dfcf71ff370b110078e118f91afd18f3c931
	/**
	 * Returns true if the 'directed edges' checkbox is ticked.
	 * @return: true if the directed edges checkbox is ticked; false otherwise.
	 */
	public boolean areEdgesDirected(){
		return chk_directed.isSelected();
	}
<<<<<<< HEAD

	/**
	 * Return the value in the text field, which specifies what the weight of any
	 * created edges should be.
	 * @return: an integer.
	 */
	public int getEdgeWeight(){
		return Integer.parseInt(field_weight.getText());
	}

	public String toString(){
		return "graphing";
	}

	public static void main(String[] args){

		JFrame frame = new JFrame("GraphingPane");
		frame.add(new GraphingPane(null));

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);

	}

=======
	
	public String toString(){
		return "graphing";
	}
	
	public static void main(String[] args){
		
		JFrame frame = new JFrame("GraphingPane");
		frame.add(new GraphingPane(null));
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);
		
	}
	
>>>>>>> b591dfcf71ff370b110078e118f91afd18f3c931
}
