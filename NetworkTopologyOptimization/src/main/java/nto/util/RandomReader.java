package nto.util;

import static java.util.stream.Collectors.toSet;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.Set;

import nto.core.Point;

public class RandomReader {

	public Set<Point> read(String filename) {
		try {
			return Files.lines(Paths.get(Thread.currentThread().getContextClassLoader().getResource(filename).toURI())).skip(1).map(Point::element).collect(toSet());
		} catch (Exception e) {
			e.printStackTrace();
			return Collections.emptySet();
		}
	}

	public Point readHead(String filename) {
		try {
			return Point.center(Files.lines(Paths.get(Thread.currentThread().getContextClassLoader().getResource(filename).toURI())).findFirst().orElse("50;50"));
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

}
