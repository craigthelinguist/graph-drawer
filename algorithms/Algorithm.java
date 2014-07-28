package algorithms;

import java.awt.Graphics;

public abstract class Algorithm {

	protected int stateIndex = 0;  // index to the current state that this algorithm should display

	/**
	 * Each algorithm records all of its iterations, so that you can cycle back
	 * and forward through them. The relation of algorithm to Iteration is one of
	 * strong aggregation - that is, iterations do not exist without algorithms.
	 * @author craigthelinguist
	 */
	protected interface Iteration{}

	/**
	 * Draws the current state of the algorithm.
	 */
	public abstract void draw(Graphics g);

	/**
	 * Returns the number of iterations in this algorithm.
	 * @return: number of iterations this algorithm has.
	 */
	protected abstract int numberOfStates();

	/**
	 * Moves the algorithm state to the first iteration.
	 */
	public final void firstIteration(){
		stateIndex = 0;
	}

	/**
	 * Moves the algorithm backward one iteration.
	 */
	public final void previousIteration(){
		stateIndex = Math.max(0,stateIndex-1);
	}

	/**
	 * Moves the algorithm forward one iteration.
	 */
	public final void nextIteration(){
		stateIndex = Math.min(numberOfStates()-1,stateIndex+1);
	}

	/**
	 * Moves the algorithm to the last iteration.
	 */
	public final void lastIteration(){
		stateIndex = numberOfStates()-1;
	}

}
