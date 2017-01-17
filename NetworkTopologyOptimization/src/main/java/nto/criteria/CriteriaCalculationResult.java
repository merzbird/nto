package nto.criteria;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import nto.core.Point;

public class CriteriaCalculationResult {

	public final double result;
	public final Map<Point, Point> connections;
	public final Set<Point> nodes;

	public CriteriaCalculationResult(double result, Map<Point, Point> connections, Set<Point> nodes) {
		this.result = result;
		this.connections = connections;
		this.nodes = nodes;
	}

	public CriteriaCalculationResult copy() {
		return new CriteriaCalculationResult(result, new HashMap<>(connections), new HashSet<>(nodes));
	}

}
