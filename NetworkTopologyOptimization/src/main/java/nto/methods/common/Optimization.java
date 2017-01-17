package nto.methods.common;

import java.util.HashMap;
import java.util.stream.Collector;

import nto.core.Point;
import nto.core.Topology;
import nto.core.TopologyOptimizationContext;

public interface Optimization {
	
	public Topology optimalTopology(TopologyOptimizationContext context);
	
	default Collector<Point, HashMap<Integer, Point>, HashMap<Integer, Point>> toMap() {
		return Collector.of(HashMap::new, (map, t) -> map.put(map.size(), t), (m1, m2) -> {
			int s = m1.size();
			m2.forEach((k, v) -> m1.put(k + s, v));
			return m1;
		});
	}

}
