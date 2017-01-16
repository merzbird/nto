package nto.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Map.Entry;

import nto.core.Point;
import nto.core.Topology;

public class DgsWriter {

	public void write(String filename, Topology topology) {
		File out = new File(filename);
		try (FileWriter fw = new FileWriter(out); BufferedWriter writer = new BufferedWriter(fw);) {
			writeHeader(writer);
			writeCenter(writer, topology);
			writeNodes(writer, topology);
			writeElements(writer, topology);
			writeEdges(writer, topology);
		} catch (IOException e) {
			System.out.println(e);
		}
	}

	private void writeHeader(BufferedWriter writer) throws IOException {
		writer.write("DGS004");
		writer.newLine();
		writer.write("optGraph 0 0");
		writer.newLine();
	}

	private void writeCenter(BufferedWriter writer, Topology topology) throws IOException {
		writePoints(writer, Arrays.asList(topology.getCenter()));
	}

	private void writeNodes(BufferedWriter writer, Topology topology) throws IOException {
		writePoints(writer, topology.getNodes());
	}

	private void writeElements(BufferedWriter writer, Topology topology) throws IOException {
		writePoints(writer, topology.getElements());
	}

	private void writePoints(BufferedWriter writer, Collection<Point> elements) throws IOException {
		for (Point node : elements) {
			addPoint(writer, node.toString(), node.getX(), node.getY(), node.getType().getShortName());
		}
	}

	private void writeEdges(BufferedWriter writer, Topology topology) throws IOException {
		for (Entry<Point, Point> conn : topology.getConnections().entrySet()) {
			Point key = conn.getKey();
			Point value = conn.getValue();
			writer.write(String.format("ae %s_WITH_%s %s %s", key.toString(), value.toString(), key.toString(), value.toString()));
			writer.newLine();
		}
	}

	private void addPoint(BufferedWriter writer, String name, float x, float y, String type) throws IOException {
		writer.write(String.format("an %s x=%s y=%s z=0 ui.class=%s ui.label=%s", name, x, y, type, String.format("\"(%.1f,%.1f)\"", x, y)));
		writer.newLine();
	}

}
