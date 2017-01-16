package nto;

import java.io.IOException;

import org.graphstream.graph.ElementNotFoundException;
import org.graphstream.graph.Graph;
import org.graphstream.graph.implementations.MultiGraph;
import org.graphstream.stream.GraphParseException;

import nto.core.Point;
import nto.core.Topology;
import nto.core.TopologyOptimizationContext;
import nto.methods.CoordinatewiseOptimization;
import nto.util.DgsWriter;
import nto.util.RandomReader;

public class App {
	public static void main(String[] args) {
		TopologyOptimizationContext context = new TopologyOptimizationContext(10, Point.center(50f, 50f), new RandomReader().read("random.txt"));
		Topology optimalTopology = new CoordinatewiseOptimization().optimalTopology(context);
		new DgsWriter().write("graph.dgs", optimalTopology);
		System.setProperty("org.graphstream.ui.renderer", "org.graphstream.ui.j2dviewer.J2DGraphRenderer");
		Graph graph = new MultiGraph("OptTopology");
		try {
			graph.read("graph.dgs");
		} catch (ElementNotFoundException | IOException | GraphParseException e) {
			e.printStackTrace();
		}
		graph.addAttribute("ui.stylesheet", "url('file:///E://git//NetworkTopologyOptimization//dgs_style.css')");
		graph.display(false);
	}
}
