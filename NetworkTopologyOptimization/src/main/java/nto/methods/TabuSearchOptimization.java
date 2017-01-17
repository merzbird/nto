package nto.methods;

import static java.util.stream.Collectors.toList;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import nto.core.Point;
import nto.core.Topology;
import nto.core.TopologyOptimizationContext;
import nto.criteria.CostCriteria;
import nto.criteria.CostCriteriaCalculationContext;
import nto.criteria.CriteriaCalculationResult;
import nto.methods.common.Optimization;

public class TabuSearchOptimization implements Optimization {

	@Override
	public Topology optimalTopology(TopologyOptimizationContext context) {
		Map<Integer, Set<Point>> tabuList = new HashMap<>();
		Set<Point> bestPlacement = new HashSet<>();
		Random random = new Random();
		Set<Point> possiblePoints = new HashSet<>(context.possiblePoints);
		int nodeCount = 0;
		Map<Integer, Point> placement = possiblePoints.stream().limit(context.numberOfNodes).map(Point::node).collect(toMap());
		CriteriaCalculationResult result = new CostCriteria().calculate(new CostCriteriaCalculationContext(context.center, new HashSet<>(placement.values()), possiblePoints));
		CriteriaCalculationResult prevResult;
		do {
			prevResult = result.copy();
			CriteriaCalculationResult bestLevelResult = new CriteriaCalculationResult(Double.MAX_VALUE, Collections.emptyMap(), Collections.emptySet());
			while (nodeCount < context.numberOfNodes) {
				List<Point> listOfPossibleSubstitutions = possiblePoints.stream().filter(p -> !placement.containsValue(p)).collect(toList());
				Collections.shuffle(listOfPossibleSubstitutions);
				Point other;
				do {
					other = listOfPossibleSubstitutions.get(random.nextInt(listOfPossibleSubstitutions.size()));
				} while (tabuList.getOrDefault(nodeCount, Collections.emptySet()).contains(other));
				Point newNode = Point.node(other);
				placement.compute(nodeCount, (k, v) -> {
					possiblePoints.add(Point.element(placement.get(k)));
					return newNode;
				});
				CriteriaCalculationResult levelResult = new CostCriteria().calculate(new CostCriteriaCalculationContext(context.center, new HashSet<>(placement.values()), possiblePoints));
				if (levelResult.result < bestLevelResult.result) {
					bestLevelResult = levelResult;
					bestPlacement = new HashSet<>(placement.values());
				} else {
					tabuList.merge(nodeCount, new HashSet<>(Arrays.asList(newNode)), (o, n) -> {
						o.addAll(n);
						return o;
					});
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
		return new Topology(context.center, bestPlacement, possiblePoints, result.connections, result.result);
	}

}