package com.github.lunatrius.ingameinfo.editor.geom;

import java.io.Serializable;

import net.minecraft.client.gui.ScaledResolution;

public class Point implements Serializable {
    private static final long serialVersionUID = 1L;
    private static final Point ZERO = new Point();

    protected final int x, y;

    public Point() {
        this(0, 0);
    }

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Point(Point point) {
        this.x = point.x;
        this.y = point.y;
    }

    public Point(ScaledResolution resolution) {
        this.x = resolution.getScaledWidth();
        this.y = resolution.getScaledHeight();
    }

    public Size toSize() {
        return new Size(this);
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Point)) return false;
        Point point = (Point) obj;
        return x == point.x && y == point.y;
    }

    @Override
    public int hashCode() {
        return (31 + x) * 31 + y;
    }

    @Override
    public String toString() {
        return String.format("Point{x: %d, y: %d}", x, y);
    }

    public String toPrettyString() {
        return String.format("(%d, %d)", x, y);
    }

    public static Point zero() {
        return ZERO;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Point withX(int x) {
        return new Point(x, y);
    }

    public Point withY(int y) {
        return new Point(x, y);
    }

    public Point add(int x, int y) {
        return new Point(this.x + x, this.y + y);
    }

    public Point add(Point point) {
        return new Point(x + point.x, y + point.y);
    }

    public Size sub(int x, int y) {
        return new Size(this.x - x, this.y - y);
    }

    public Size sub(Point point) {
        return new Size(x - point.x, y - point.y);
    }

    public Point invert() {
        return new Point(-x, -y);
    }

    public Point scale(float factor) {
        return new Point(Math.round(x * factor), Math.round(y * factor));
    }

    public Point scale(float xf, float yf) {
        return new Point(Math.round(x * xf), Math.round(y * yf));
    }

    public Point scale(Point factor) {
        return new Point(x * factor.x, y * factor.y);
    }
}
