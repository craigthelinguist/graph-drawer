package controller;

/**
 * Keeps track of what mode the sidebar is in. Graphing: the user is able to
 * draw a graph on the screen. Algorithm: the user can select inputs and
 * perform their selected algorithm.
 *
 * @author craigthelinguist
 */
public enum Mode {
	GRAPHING, ALGORITHMS;
		
	@Override
	public String toString(){
		return this.name().toLowerCase();
	}
		
	public static Mode fromString(String name){
		name = name.toUpperCase();
		if (name.equals("ALGORITHMS")) return ALGORITHMS;
		else return GRAPHING;
	}		
}