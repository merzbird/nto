package nto.criteria;

import java.util.Set;

import nto.core.Point;

public class CostCriteriaCalculationContext extends CriteriaCalculationContext {

	public CostCriteriaCalculationContext(Point center, Set<Point> nodes, Set<Point> possiblePoints) {
		super(center, nodes, possiblePoints);
	}

}
