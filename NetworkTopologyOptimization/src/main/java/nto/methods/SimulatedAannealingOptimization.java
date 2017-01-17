package nto.methods;

import static java.util.stream.Collectors.toList;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

import nto.core.Point;
import nto.core.Topology;
import nto.core.TopologyOptimizationContext;
import nto.criteria.CostCriteria;
import nto.criteria.CostCriteriaCalculationContext;
import nto.criteria.CriteriaCalculationResult;
import nto.methods.common.Optimization;

public class SimulatedAannealingOptimization implements Optimization {

	@Override
	public Topology optimalTopology(TopologyOptimizationContext context) {
		Random random = new Random();
		double temp = 10000;
		double coolingRate = 0.003;
		Set<Point> possiblePoints = new HashSet<>(context.possiblePoints);

		Set<Point> placement = possiblePoints.stream().limit(context.numberOfNodes).map(Point::node).collect(Collectors.toSet());
		Set<Point> bestPlacement = new HashSet<>(placement);

		CriteriaCalculationResult currentResult = new CostCriteria().calculate(new CostCriteriaCalculationContext(context.center, new HashSet<>(placement), possiblePoints));
		CriteriaCalculationResult bestResult = currentResult.copy();
		while (temp > 1) {
			Set<Point> newPlacement = new HashSet<>(currentResult.nodes);
			List<Point> listOfPossibleSubstitutions = possiblePoints.stream().filter(p -> !newPlacement.contains(p)).collect(toList());
			Collections.shuffle(listOfPossibleSubstitutions);
			Point randomNewNode = Point.node(listOfPossibleSubstitutions.get(random.nextInt(listOfPossibleSubstitutions.size())));
			Point nodeToRemove = newPlacement.stream().skip(random.nextInt(newPlacement.size())).findFirst().get();
			newPlacement.remove(nodeToRemove);
			newPlacement.add(randomNewNode);
			CriteriaCalculationResult newResult = new CostCriteria().calculate(new CostCriteriaCalculationContext(context.center, new HashSet<>(newPlacement), possiblePoints));
			if (acceptanceProbability(currentResult.result, newResult.result, temp) > Math.random()) {
				currentResult = newResult.copy();
			}
			if (currentResult.result < bestResult.result) {
				bestResult = currentResult.copy();
				bestPlacement = new HashSet<>(currentResult.nodes);
			}
			temp *= 1 - coolingRate;
		}
		possiblePoints.removeAll(bestPlacement);
		return new Topology(context.center, bestPlacement, possiblePoints, bestResult.connections, bestResult.result);
	}

	public static double acceptanceProbability(double energy, double newEnergy, double temperature) {
		if (newEnergy < energy) {
			return 1.0;
		}
		return Math.exp((energy - newEnergy) / temperature);
	}

}