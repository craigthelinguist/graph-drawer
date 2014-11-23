package controller;

public enum AlgorithmMode {
	KRUSKALS, ASTAR;
	
	@Override
	public String toString(){
		return this.name().toLowerCase();
	}
	
	public static String[] nameArray(){
		return new String[]{ "Kruskals", "AStar" };
	}
	
	public static AlgorithmMode fromString(String name){
		name = name.toUpperCase();
		return valueOf(name);
	}
	
}
