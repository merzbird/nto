package nto;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.graphstream.graph.ElementNotFoundException;
import org.graphstream.graph.Graph;
import org.graphstream.graph.implementations.MultiGraph;
import org.graphstream.stream.GraphParseException;

import nto.core.Topology;
import nto.core.TopologyOptimizationContext;
import nto.methods.CoordinatewiseOptimization;
import nto.methods.SimulatedAannealingOptimization;
import nto.methods.TabuSearchOptimization;
import nto.methods.common.Optimization;
import nto.util.DgsWriter;
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
		new DgsWriter().write("/output/graph.dgs", optimalTopology);
		System.out.println("Creating graph...");
		System.setProperty("org.graphstream.ui.renderer", "org.graphstream.ui.j2dviewer.J2DGraphRenderer");
		Graph graph = new MultiGraph("OptTopology");
		try {
			graph.read(Paths.get(".").toAbsolutePath().normalize().toString() + "/output/graph.dgs");
			graph.addAttribute("ui.stylesheet", new String(Files.readAllBytes(Paths.get(Thread.currentThread().getContextClassLoader().getResource(css).toURI())), Charset.forName("UTF-8")));
		} catch (ElementNotFoundException | IOException | GraphParseException | URISyntaxException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		graph.display(false);
		System.out.println("Saving graph...");
		graph.addAttribute("ui.screenshot", Paths.get(".").toAbsolutePath().normalize().toString() + "/output/" + outputFile);
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
