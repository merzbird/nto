package nto.core;

import java.util.Map;
import java.util.Set;

public class Topology {

	private final Point center;
	private final Set<Point> nodes;
	private final Set<Point> elements;
	private final Map<Point, Point> connections;
	private final double result;

	public Topology(Point center, Set<Point> nodes, Set<Point> elements, Map<Point, Point> connections, double result) {
		this.center = center;
		this.nodes = nodes;
		this.elements = elements;
		this.connections = connections;
		this.result = result;
	}

	public Set<Point> getNodes() {
		return nodes;
	}

	public Set<Point> getElements() {
		return elements;
	}

	public Point getCenter() {
		return center;
	}

	public Map<Point, Point> getConnections() {
		return connections;
	}

	public double getResult() {
		return result;
	}
}