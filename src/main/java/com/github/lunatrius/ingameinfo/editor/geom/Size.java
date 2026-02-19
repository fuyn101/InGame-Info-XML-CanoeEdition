package com.github.lunatrius.ingameinfo.editor.geom;

import java.io.Serializable;

public class Size implements Serializable {
    private static final long serialVersionUID = 1L;
    private static final Size ZERO = new Size();

    protected final int width, height;

    public Size() {
        this(0, 0);
    }

    public Size(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public Size(Size size) {
        this.width = size.width;
        this.height = size.height;
    }

    public Size(Point point) {
        this.width = point.getX();
        this.height = point.getY();
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Size)) return false;
        Size size = (Size) obj;
        return width == size.width && height == size.height;
    }

    @Override
    public int hashCode() {
        return (31 + width) * 31 + height;
    }

    @Override
    public String toString() {
        return String.format("Size{width: %d, height: %d}", width, height);
    }

    public static Size zero() {
        return ZERO;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public Size withWidth(int width) {
        return new Size(width, height);
    }

    public Size withHeight(int height) {
        return new Size(width, height);
    }

    public Size add(int width, int height) {
        return new Size(this.width + width, this.height + height);
    }

    public Size add(Size size) {
        return new Size(width + size.width, height + size.height);
    }

    public Size sub(int width, int height) {
        return new Size(this.width - width, this.height - height);
    }

    public Size sub(Size size) {
        return new Size(width - size.width, height - size.height);
    }

    public Point toPoint() {
        return new Point(width, height);
    }
}
