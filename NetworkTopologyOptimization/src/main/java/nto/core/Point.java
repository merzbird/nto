package nto.core;

import java.util.Objects;

public class Point {

	private final Type type;
	private final float x;
	private final float y;

	public Point(Type type, float x, float y) {
		this.type = type;
		this.x = x;
		this.y = y;
	}

	public static Point node(Point other) {
		return node(other.x, other.y);
	}

	public static Point node(float x, float y) {
		return new Point(Type.NODE, x, y);
	}

	public static Point center(float x, float y) {
		return new Point(Type.CENTER, x, y);
	}

	public static Point element(String line) {
		return element(Float.valueOf(line.split(";")[0]), Float.valueOf(line.split(";")[1]));
	}

	public static Point element(Point other) {
		return element(other.x, other.y);
	}

	public static Point element(float x, float y) {
		return new Point(Type.ELEMENT, x, y);
	}

	public Type getType() {
		return type;
	}

	public float getX() {
		return x;
	}

	public float getY() {
		return y;
	}

	@Override
	public int hashCode() {
		return Objects.hash(x, y);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Point other = (Point) obj;
		if (Float.floatToIntBits(x) != Float.floatToIntBits(other.x))
			return false;
		if (Float.floatToIntBits(y) != Float.floatToIntBits(other.y))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return String.format("%s__%.2f__%.2f", type.shortName, x, y).replaceAll(",", "_");
	}

	public enum Type {
		CENTER("C"), NODE("N"), ELEMENT("E");

		private final String shortName;

		private Type(String shortName) {
			this.shortName = shortName;
		}

		public String getShortName() {
			return shortName;
		}

	}

}
