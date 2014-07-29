package graph;

import java.awt.Color;
import java.awt.Graphics;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Node {

	public static final int DIAM = 30;
	public static final int RADIUS = DIAM / 2;
	public final int X;
	public final int Y;
	public final int XMID;
	public final int YMID;
	public final Color colour;

	private Map<Edge, Node> neighbours = new HashMap<>();

	public Node(int xtopleft, int ytopleft) {
		X = xtopleft;
		Y = ytopleft;
		XMID = xtopleft + RADIUS;
		YMID = ytopleft + RADIUS;
		colour = randomColour();
	}

	/**
	 * Factory method. Creates and returns an instance of this object.
	 * 
	 * @return: the node object.
	 */
	public static Node create(int x, int y) {
		return new Node(x, y);
	}

	/**
	 * Add the given node as a neighbour of this node. this and neighbour must
	 * be adjacent to the edge, or it will throw IllegalArgumentException.
	 */
	public void addNeighbour(Edge edge, Node neighbour) {
		// check if this and neighbour are connected by an edge.
		if (this != edge.node1 && this != edge.node2 || neighbour != edge.node1
				&& neighbour != edge.node2)
			throw new IllegalArgumentException(
					"Adding a neighbour to a node that isn't connected via an edge.");
		neighbours.put(edge, neighbour);
	}

	/**
	 * Return a read-only set of this node's neighbours. Attempting to write to
	 * it will throw an UnsupportedOperationException().
	 * 
	 * @return
	 */
	public Map<Edge, Node> getNeighbours() {
		return new HashMap<Edge, Node>(neighbours) {
			@Override
			public Node put(Edge e, Node n) {
				throw new UnsupportedOperationException(
						"May not add directly to a node's neighbourhood.");
			}
		};
	}

	/**
	 * Checks whether a given point (x,y) is in or on this node.
	 * 
	 * @param x
	 *            : x part of a point.
	 * @param y
	 *            : y part of a point.
	 * @return: true if (x,y) is contained within this node; false otherwise.
	 */
	public boolean contains(int x, int y) {
		int dx = Math.abs(x - this.XMID);
		int dy = Math.abs(y - this.YMID);
		int hyp = dx * dx + dy * dy;
		int radius_squared = RADIUS * RADIUS;
		return hyp <= radius_squared;
	}

	/**
	 * Generates a random colour (biased towards lighter colours).
	 * 
	 * @return: a random colour.
	 */
	private Color randomColour() {
		int r = Math.min(255, (int) (Math.random() * 255) + 60);
		int g = Math.min(255, (int) (Math.random() * 255) + 60);
		int b = Math.min(255, (int) (Math.random() * 255) + 60);
		return new Color(r, g, b);
	}

	/**
	 * Draw the node in its stored coloured.
	 * 
	 * @param g
	 *            : object on which to draw.
	 */
	public void draw(Graphics g) {
		g.setColor(colour);
		g.fillOval(X, Y, DIAM, DIAM);
		g.setColor(Color.BLACK);
		g.drawOval(X, Y, DIAM, DIAM);
	}

	/**
	 * Draw the node in the specified colour.
	 * 
	 * @param g
	 *            : object on which to draw.
	 * @param col
	 *            : colour in which to draw the node.
	 */
	public void draw(Graphics g, Color col) {
		g.setColor(col);
		g.fillOval(X, Y, DIAM, DIAM);
		g.setColor(Color.BLACK);
		g.drawOval(X, Y, DIAM, DIAM);
	}

	/**
	 * Give the node a thicker black outline.
	 * 
	 * @param g
	 *            : object on which to draw.
	 */
	public void outline(Graphics g) {
		g.setColor(Color.BLACK);
		g.drawOval(X, Y, DIAM, DIAM);
		g.drawOval(X + 1, Y, DIAM - 1, DIAM);
		g.drawOval(X, Y + 1, DIAM, DIAM - 1);
		g.drawOval(X, Y, DIAM - 1, DIAM);
		g.drawOval(X, Y, DIAM, DIAM - 1);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + XMID;
		result = prime * result + YMID;
		result = prime * result + ((colour == null) ? 0 : colour.hashCode());
		result = prime * result + X;
		result = prime * result + Y;
		return result;
	}

}