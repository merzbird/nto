package nto.core;

import java.util.Set;

public class TopologyOptimizationContext {

	public final int numberOfNodes;
	public final Point center;
	public final Set<Point> possiblePoints;

	public TopologyOptimizationContext(int numberOfNodes, Point center, Set<Point> possiblePoints) {
		this.numberOfNodes = numberOfNodes;
		this.center = center;
		this.possiblePoints = possiblePoints;
	}

}
