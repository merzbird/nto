package nto;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.graphstream.graph.ElementNotFoundException;
import org.graphstream.graph.Graph;

import nto.core.Topology;
import nto.core.TopologyOptimizationContext;
import nto.methods.CoordinatewiseOptimization;
import nto.methods.SimulatedAannealingOptimization;
import nto.methods.TabuSearchOptimization;
import nto.methods.common.Optimization;
import nto.util.GraphResolver;
import nto.util.RandomReader;

public class App {
	public static void main(String[] args) {
		System.out.println("Starting...");
		String inputFile = "in.txt";
		if (args.length > 0 && args[0] != null) {
			inputFile = args[0];
		}
		String outputFile = "out.png";
		if (args.length > 1 && args[1] != null) {
			outputFile = args[1];
		}
		String method = "com";
		if (args.length > 2 && args[2] != null) {
			method = args[2];
		}
		int muberOfNodes = 5;
		if (args.length > 3 && args[3] != null) {
			muberOfNodes = Integer.valueOf(args[3]);
		}
		String css = "dgs_style.css";
		if (args.length > 4 && args[4] != null) {
			css = args[4];
		}
		TopologyOptimizationContext context = new TopologyOptimizationContext(muberOfNodes, new RandomReader().readHead(inputFile), new RandomReader().read(inputFile));
		Topology optimalTopology = parseArg(method).optimalTopology(context);
		System.out.println("Optimal topology found... Cost = " + optimalTopology.getResult());
		System.out.println("Creating graph...");
		Graph graph = new GraphResolver().get(optimalTopology);
		System.setProperty("org.graphstream.ui.renderer", "org.graphstream.ui.j2dviewer.J2DGraphRenderer");
		try {
			graph.addAttribute("ui.stylesheet", new String(Files.readAllBytes(Paths.get(css)), Charset.forName("UTF-8")));
		} catch (ElementNotFoundException | IOException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		graph.display(false);
		System.out.println("Saving graph...");
		graph.addAttribute("ui.screenshot", outputFile);
	}

	private static Optimization parseArg(String arg) {
		switch (arg) {
		case "com":
			return new CoordinatewiseOptimization();
		case "ts":
			return new TabuSearchOptimization();
		case "sa":
			return new SimulatedAannealingOptimization();
		default:
			return new CoordinatewiseOptimization();
		}
	}
}
