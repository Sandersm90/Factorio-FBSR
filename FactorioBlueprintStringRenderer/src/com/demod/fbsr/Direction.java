package com.demod.fbsr;

import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Double;
import java.awt.geom.Rectangle2D;

public enum Direction {
	NORTH("N", 0, -1), //
	NORTHEAST("NE", 1, -1), //
	EAST("E", 1, 0), //
	SOUTHEAST("SE", 1, 1), //
	SOUTH("S", 0, 1), //
	SOUTHWEST("SW", -1, 1), //
	WEST("W", -1, 0), //
	NORTHWEST("NW", -1, -1);

	public static Direction fromCardinal(int cardinal) {
		return values()[cardinal * 2];
	}

	public static Direction fromSymbol(String symbol) {
		for (Direction direction : values()) {
			if (direction.symbol.equals(symbol)) {
				return direction;
			}
		}
		throw new IllegalArgumentException("Unknown symbol \"" + symbol + "\"");
	}

	private final String symbol;
	private final int dx;

	private final int dy;

	private Direction(String symbol, int dx, int dy) {
		this.symbol = symbol;
		this.dx = dx;
		this.dy = dy;
	}

	public int adjCode() {
		return 1 << ordinal();
	}

	public Direction back() {
		return rotate(4);
	}

	public Direction backLeft() {
		return rotate(-3);
	}

	public Direction backRight() {
		return rotate(3);
	}

	public int cardinal() {
		return ordinal() / 2;
	}

	public Direction frontLeft() {
		return rotate(-1);
	}

	public Direction frontRight() {
		return rotate(1);
	}

	public int getDx() {
		return dx;
	}

	public int getDy() {
		return dy;
	}

	public double getOrientation() {
		return ordinal() / 8.0;
	}

	public boolean isCardinal() {
		return (ordinal() % 2) == 0;
	}

	public boolean isHorizontal() {
		return this == EAST || this == WEST;
	}

	public boolean isVertical() {
		return this == NORTH || this == SOUTH;
	}

	public Direction left() {
		return rotate(-2);
	}

	public Point2D.Double offset() {
		return new Point2D.Double(dx, dy);
	}

	public Double offset(double distance) {
		return new Point2D.Double(distance * dx, distance * dy);
	}

	public Point2D.Double offset(Point2D.Double pos) {
		return new Point2D.Double(pos.x + dx, pos.y + dy);
	}

	public Double offset(Point2D.Double pos, double distance) {
		return new Point2D.Double(pos.x + distance * dx, pos.y + distance * dy);
	}

	public Point2D.Double offset(Point2D.Double pos, Point2D.Double offset) {
		return offset(right().offset(pos, offset.y), offset.x);
	}

	public Rectangle2D.Double offset(Rectangle2D.Double rect, double distance) {
		return new Rectangle2D.Double(rect.x + distance * dx, rect.y + distance * dy, rect.width, rect.height);
	}

	public Direction right() {
		return rotate(2);
	}

	public Direction rotate(Direction dir) {
		return rotate(dir.ordinal());
	}

	public Direction rotate(int deltaIndex) {
		Direction[] values = values();
		return values[(((ordinal() + deltaIndex) % values.length) + values.length) % values.length];
	}

	public Rectangle2D rotateBounds(Rectangle2D bounds) {
		AffineTransform at = new AffineTransform();
		at.rotate(Math.PI * 2.0 * ordinal() / 8.0);
		return at.createTransformedShape(bounds).getBounds2D();
	}

	public Point2D.Double rotatePoint(Point2D.Double point) {
		AffineTransform at = new AffineTransform();
		at.rotate(Math.PI * 2.0 * ordinal() / 8.0);
		Point2D.Double ret = new Point2D.Double();
		at.deltaTransform(point, ret);
		return ret;
	}
}