package graph;

import java.awt.Color;
import java.awt.Graphics;

public class Edge implements Comparable<Edge> {

	// if a node is directed, it goes from node1->node2
	public final boolean directed;
	public final Node node1;
	public final Node node2;
	public final int weight;

	public Edge(Node n1, Node n2, boolean dir, int w){
		node1 = n1;
		node2 = n2;
		directed = dir;
		weight = w;
	}

	/**
	 * Draw the edge on the screen in black.
	 * @param g: object on which to draw the edge.
	 */
	public void draw(Graphics g){
		draw(g,Color.BLACK);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (directed ? 1231 : 1237);
		result = prime * result + ((node1 == null) ? 0 : node1.hashCode());
		result = prime * result + ((node2 == null) ? 0 : node2.hashCode());
		return result;
	}

	@Override
	public int compareTo(Edge other) {
		return this.weight - other.weight;
	}

	@Override
	/**
	 * Returns a shallow clone of this object.
	 */
	public Edge clone(){
		return new Edge(node1,node2,directed,weight);
	}

	/**
	 * Draw the edge in the specified colour.
	 * @param g: object on which to draw the edge.
	 * @param col: colour to draw in.
	 */
	public void draw(Graphics g, Color col) {
		g.setColor(col);
		g.drawLine(node1.XMID,node1.YMID,node2.XMID,node2.YMID);
		g.setColor(Color.BLACK);
		g.drawString(""+weight, (node1.XMID + node2.XMID)/2, (node1.YMID + node2.YMID)/2);
	}

}
