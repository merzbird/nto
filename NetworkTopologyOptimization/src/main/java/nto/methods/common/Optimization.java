package nto.methods.common;

import nto.core.Topology;
import nto.core.TopologyOptimizationContext;

public interface Optimization {
	
	public Topology optimalTopology(TopologyOptimizationContext context);

}
