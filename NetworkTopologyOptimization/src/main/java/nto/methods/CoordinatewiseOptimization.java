package nto.methods;

import static java.util.stream.Collectors.toList;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collector;

import nto.core.Point;
import nto.core.Topology;
import nto.core.TopologyOptimizationContext;
import nto.criteria.CostCriteria;
import nto.criteria.CostCriteriaCalculationContext;
import nto.criteria.CriteriaCalculationResult;
import nto.methods.common.Optimization;

public class CoordinatewiseOptimization implements Optimization {

	@Override
	public Topology optimalTopology(TopologyOptimizationContext context) {
		Set<Point> bestPlacement = new HashSet<>();
		Random random = new Random();
		Set<Point> possiblePoints = new HashSet<>(context.possiblePoints);
		int nodeCount = 0;
		Map<Integer, Point> placement = possiblePoints.stream().limit(context.numberOfNodes).map(Point::node).collect(toMap());
		CriteriaCalculationResult result = new CostCriteria().calculate(new CostCriteriaCalculationContext(context.center, new HashSet<>(placement.values()), possiblePoints));
		CriteriaCalculationResult prevResult;
		do {
			prevResult = result.copy();
			CriteriaCalculationResult bestLevelResult = new CriteriaCalculationResult(Double.MAX_VALUE, Collections.emptyMap());
			while (nodeCount < context.numberOfNodes) {
				List<Point> listOfPossibleSubstitutions = possiblePoints.stream().filter(p -> !placement.containsValue(p)).collect(toList());
				Collections.shuffle(listOfPossibleSubstitutions);
				Point randomNewNode = Point.node(listOfPossibleSubstitutions.get(random.nextInt(listOfPossibleSubstitutions.size())));
				placement.compute(nodeCount, (k, v) -> {
					possiblePoints.add(Point.element(placement.get(k)));
					return randomNewNode;
				});
				CriteriaCalculationResult levelResult = new CostCriteria().calculate(new CostCriteriaCalculationContext(context.center, new HashSet<>(placement.values()), possiblePoints));
				if (levelResult.result < bestLevelResult.result) {
					bestLevelResult = levelResult;
					bestPlacement = new HashSet<>(placement.values());
				}
				nodeCount++;
			}
			result = bestLevelResult;
			nodeCount = 0;
			ArrayList<Point> list = new ArrayList<>(possiblePoints);
			Collections.shuffle(list);
			possiblePoints.clear();
			possiblePoints.addAll(list);
		} while (prevResult.result > result.result);
		possiblePoints.removeAll(bestPlacement);
		return new Topology(context.center, bestPlacement, possiblePoints, result.connections);
	}

	private Collector<Point, HashMap<Integer, Point>, HashMap<Integer, Point>> toMap() {
		return Collector.of(HashMap::new, (map, t) -> map.put(map.size(), t), (m1, m2) -> {
			int s = m1.size();
			m2.forEach((k, v) -> m1.put(k + s, v));
			return m1;
		});
	}

}