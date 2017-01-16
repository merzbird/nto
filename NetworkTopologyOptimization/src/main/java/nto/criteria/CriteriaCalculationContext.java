package nto.criteria;

import java.util.HashSet;
import java.util.Set;

import nto.core.Point;

public abstract class CriteriaCalculationContext {

	protected final Point center;
	protected final Set<Point> nodes;
	protected final Set<Point> possiblePoints;

	public CriteriaCalculationContext(Point center, Set<Point> nodes, Set<Point> possiblePoints) {
		this.center = center;
		this.nodes = nodes;
		this.possiblePoints = possiblePoints;
	}

	public Point getCenter() {
		return new Point(center.getType(), center.getX(), center.getY());
	}

	public Set<Point> getNodes() {
		return new HashSet<>(nodes);
	}

	public Set<Point> getPossiblePoints() {
		return new HashSet<>(possiblePoints);
	}

}
