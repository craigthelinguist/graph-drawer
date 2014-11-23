package controller;

import java.util.List;

import graph.Graph;
import graph.Node;
import algorithms.AStar;
import algorithms.Algorithm;
import algorithms.Kruskals;
import controller.GraphController.AlgorithmMode;

public class AlgorithmFactory {
	
	protected Algorithm setupAlgorithm(AlgorithmMode mode, Graph graph, List<Node> selection)
	throws SetupException{
		if (mode == null) return null;
		switch (mode){
			case ASTAR:
				return setupAStar(graph, selection);
			case KRUSKALS:
				return setupKruskals(graph, selection);
			default:
				return null;
		}
	}
	
	private Algorithm setupAStar(Graph graph, List<Node> selection)
	throws SetupException{
		if (selection.size() != 2){
			throw new SetupException("You must select a start node and an end node for A*. Select nodes by left-clicking them.");
		}
		Node start = selection.get(0);
		Node goal = selection.get(1);
		return new AStar(graph,start,goal);
	}
	
	private Algorithm setupKruskals(Graph graph, List<Node> selection)
	throws SetupException{
		return new Kruskals(graph);
	}
	
}
