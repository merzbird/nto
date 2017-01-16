package nto.criteria;

import static java.lang.Math.pow;
import static java.lang.Math.sqrt;
import static java.util.stream.Collectors.toCollection;

import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import nto.core.Point;

public class CostCriteria implements Criteria<CostCriteriaCalculationContext> {

	private static final double CENTER_PRICE = 50;
	private static final double NODE_PRICE = 20;
	private static final double ELEMENT_PRICE = 5;

	private double pathCost;
	private Map<Point, Point> connections;

	public CostCriteria() {
		pathCost = 0;
		connections = new LinkedHashMap<>();
	}

	@Override
	public CriteriaCalculationResult calculate(CostCriteriaCalculationContext context) {
		Point center = context.getCenter();
		Set<Point> nodes = context.getNodes();
		Set<Point> elements = context.getPossiblePoints();
		elements.removeAll(nodes);
		double pointsCost = CENTER_PRICE + (nodes.size() * NODE_PRICE) + (elements.size() * ELEMENT_PRICE);
		for (Point el : elements) {
			double minElPath = Double.MAX_VALUE;
			for (Point node : nodes) {
				double elPath;
				if ((elPath = distance(el, node)) < minElPath) {
					minElPath = elPath;
					connections.put(el, node);
				}
			}
			pathCost += minElPath;
		}
		Set<Point> nodesWithCenter = new HashSet<>();
		nodesWithCenter.addAll(nodes);
		nodesWithCenter.add(center);
		Set<Point> sorted = nodes.stream().sorted((node1, node2) -> Double.compare(distance(node1, center), distance(node2, center))).collect(toCollection(LinkedHashSet::new));
		sorted.stream().findFirst().ifPresent(n -> {
			pathCost += distance(n, center);
			connections.put(n, center);
		});
		Set<Point> otherNodes = sorted.stream().skip(1).collect(toCollection(LinkedHashSet::new));
		for (Point node : otherNodes) {
			double minNodePath = Double.MAX_VALUE;
			Set<Point> possibleNodes = nodesExcept(nodesWithCenter, node);
			for (Point otherNode : possibleNodes) {
				double nodePath;
				if ((nodePath = distance(node, otherNode)) < minNodePath && distance(node, center) > distance(otherNode, center)) {
					minNodePath = nodePath;
					connections.put(node, otherNode);
				}
			}
			pathCost += minNodePath;
		}
		return new CriteriaCalculationResult(pointsCost + pathCost, connections);
	}

	private Set<Point> nodesExcept(Set<Point> possiblePointsForNodes, Point node) {
		Set<Point> res = new HashSet<>(possiblePointsForNodes);
		res.remove(node);
		return res;
	}

	private double distance(Point a, Point b) {
		return sqrt(pow(a.getX() - b.getX(), 2) + pow(a.getY() - b.getY(), 2));
	}

}
