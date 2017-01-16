package nto.core;

import java.util.Map;
import java.util.Set;

public class Topology {

	private final Point center;
	private final Set<Point> nodes;
	private final Set<Point> elements;
	private final Map<Point, Point> connections;

	public Topology(Point center, Set<Point> nodes, Set<Point> elements, Map<Point, Point> connections) {
		this.center = center;
		this.nodes = nodes;
		this.elements = elements;
		this.connections = connections;
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
}