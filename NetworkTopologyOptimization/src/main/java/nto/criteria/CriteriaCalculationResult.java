package nto.criteria;

import java.util.HashMap;
import java.util.Map;

import nto.core.Point;

public class CriteriaCalculationResult {
	
	public final double result;
	public final Map<Point, Point> connections;

	public CriteriaCalculationResult(double result, Map<Point, Point> connections) {
		super();
		this.result = result;
		this.connections = connections;
	}
	
	public CriteriaCalculationResult copy() {
		return new CriteriaCalculationResult(result, new HashMap<>(connections));
	}
	

}
