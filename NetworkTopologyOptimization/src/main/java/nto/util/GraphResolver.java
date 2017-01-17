package nto.util;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.graphstream.graph.Graph;
import org.graphstream.graph.implementations.MultiGraph;

import nto.core.Point;
import nto.core.Topology;

public class GraphResolver {

	public Graph get(Topology topology) {
		Graph graph = new MultiGraph("OptTopology");
		writeCenter(graph, topology);
		writeNodes(graph, topology);
		writeElements(graph, topology);
		writeEdges(graph, topology);
		return graph;
	}

	private void writeCenter(Graph graph, Topology topology) {
		writePoints(graph, Arrays.asList(topology.getCenter()));
	}

	private void writeNodes(Graph graph, Topology topology) {
		writePoints(graph, topology.getNodes());
	}

	private void writeElements(Graph graph, Topology topology) {
		writePoints(graph, topology.getElements());
	}

	private void writePoints(Graph graph, Collection<Point> elements) {
		for (Point node : elements) {
			addPoint(graph, node.toString(), node.getX(), node.getY(), node.getType().getShortName());
		}
	}

	private void writeEdges(Graph graph, Topology topology) {
		for (Entry<Point, Point> conn : topology.getConnections().entrySet()) {
			Point key = conn.getKey();
			Point value = conn.getValue();
			graph.addEdge(String.format("%s_WITH_%s", key.toString(), value.toString()), key.toString(), value.toString());
		}
	}

	private void addPoint(Graph graph, String name, float x, float y, String type) {
		Map<String, Object> attrs = new HashMap<>();
		attrs.put("x", x);
		attrs.put("y", y);
		attrs.put("z", 0);
		attrs.put("ui.class", type);
//		attrs.put("ui.label", String.format("\"(%.1f,%.1f)\"", x, y));
		graph.addNode(name).addAttributes(attrs);
	}

}
