package com.github.lunatrius.ingameinfo.editor.geom;

import java.io.Serializable;

public final class Rect implements Serializable {
    private static final long serialVersionUID = 1L;
    private static final Rect EMPTY = new Rect(0, 0, 0, 0);

    private final int x, y, width, height;

    public Rect() {
        this(0, 0, 0, 0);
    }

    public Rect(int width, int height) {
        this(0, 0, width, height);
    }

    public Rect(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public Rect(Point size) {
        this(Point.zero(), size);
    }

    public Rect(Point position, Size size) {
        this.x = position.getX();
        this.y = position.getY();
        this.width = size.getWidth();
        this.height = size.getHeight();
    }

    public Rect(Point least, Point most) {
        this(least, most.sub(least));
    }

    public Rect(Rect rect) {
        this.x = rect.x;
        this.y = rect.y;
        this.width = rect.width;
        this.height = rect.height;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Rect) {
            Rect rectangle = (Rect) obj;
            return x == rectangle.x && y == rectangle.y &&
                    width == rectangle.width && height == rectangle.height;
        }
        return super.equals(obj);
    }

    @Override
    public int hashCode() {
        return (((31 + x) * 31 + y) * 31 + width) * 31 + height;
    }

    @Override
    public String toString() {
        return String.format("Rect{x: %d, y: %d, width: %d, height: %d}", x, y, width, height);
    }

    public static Rect empty() {
        return EMPTY;
    }

    public static Rect createPadding(int left, int top, int right, int bottom) {
        return new Rect(-left, -top, left + right, top + bottom);
    }

    public static Rect createPadding(int padding) {
        return new Rect(-padding, -padding, 2 * padding, 2 * padding);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getLeft() {
        return x;
    }

    public int getTop() {
        return y;
    }

    public int getRight() {
        return x + width;
    }

    public int getBottom() {
        return y + height;
    }

    public Rect withX(int x) {
        return new Rect(x, y, width, height);
    }

    public Rect withY(int y) {
        return new Rect(x, y, width, height);
    }

    public Rect withWidth(int width) {
        return new Rect(x, y, width, height);
    }

    public Rect withHeight(int height) {
        return new Rect(x, y, width, height);
    }

    public Rect withLeft(int left) {
        return new Rect(left, y, x + width - left, height);
    }

    public Rect withTop(int top) {
        return new Rect(x, top, width, y + height - top);
    }

    public Rect withRight(int right) {
        return new Rect(x, y, right - x, height);
    }

    public Rect withBottom(int bottom) {
        return new Rect(x, y, width, bottom - y);
    }

    public Point getPosition() {
        return new Point(x, y);
    }

    public Size getSize() {
        return new Size(width, height);
    }

    public Point getLeast() {
        return new Point(x, y);
    }

    public Point getMost() {
        return new Point(x + width, y + height);
    }

    public Rect withLeast(int left, int top) {
        return new Rect(left, top, x + width - left, y + height - top);
    }

    public Rect withLeast(Point least) {
        return new Rect(least.getX(), least.getY(), x + width - least.getX(), y + height - least.getY());
    }

    public Rect withMost(int right, int bottom) {
        return new Rect(x, y, right - x, bottom - y);
    }

    public Rect withMost(Point most) {
        return new Rect(x, y, most.getX() - x, most.getY() - y);
    }

    public Rect move(int x, int y) {
        return new Rect(x, y, width, height);
    }

    public Rect move(Point position) {
        return new Rect(position.getX(), position.getY(), width, height);
    }

    public Rect resize(int width, int height) {
        return new Rect(x, y, width, height);
    }

    public Rect resize(Point size) {
        return new Rect(x, y, size.getX(), size.getY());
    }

    public Rect translate(int x, int y) {
        return new Rect(this.x + x, this.y + y, width, height);
    }

    public Rect translate(Point offset) {
        return new Rect(this.x + offset.getX(), this.y + offset.getY(), width, height);
    }

    public Rect untranslate(int x, int y) {
        return new Rect(this.x - x, this.y - y, width, height);
    }

    public Rect untranslate(Point offset) {
        return new Rect(this.x - offset.getX(), this.y - offset.getY(), width, height);
    }

    public Rect grow(int left, int top, int right, int bottom) {
        return new Rect(this.x - left, this.y - top, this.width + left + right, this.height + top + bottom);
    }

    public Rect grow(Rect padding) {
        return new Rect(this.x + padding.x, this.y + padding.y, this.width + padding.width, this.height + padding.height);
    }

    public Rect grow(int padding) {
        return new Rect(this.x - padding, this.y - padding, this.width + padding * 2, this.height + padding * 2);
    }

    public Rect scale(int factor) {
        return new Rect(this.x * factor, this.y * factor, this.width * factor, this.height * factor);
    }

    public Rect scale(float factor) {
        return new Rect(Math.round(this.x * factor), Math.round(this.y * factor), Math.round(this.width * factor), Math.round(this.height * factor));
    }

    public boolean contains(int x, int y) {
        return x >= this.x && x < this.x + width && y >= this.y && y < this.y + height;
    }

    public boolean contains(Point point) {
        return point.getX() >= x && point.getX() < x + width && point.getY() >= y && point.getY() < y + height;
    }

    public boolean isEmpty() {
        return width <= 0 || height <= 0;
    }

    public boolean isNormal() {
        return width > 0 && height > 0;
    }

    public Rect normalize() {
        if (isNormal()) return this;
        int x = this.x, y = this.y, width = this.width, height = this.height;

        if (width < 0) {
            x += width;
            width = -width;
        }
        if (height < 0) {
            y += height;
            height = -height;
        }
        return new Rect(x, y, width, height);
    }

    public Rect union(Rect rect) {
        int x = Math.min(this.x, rect.x);
        int y = Math.min(this.y, rect.y);
        int width = Math.max(this.x + this.width, rect.x + rect.width) - x;
        int height = Math.max(this.y + this.height, rect.y + rect.height) - y;
        return new Rect(x, y, width, height);
    }

    public Rect intersection(Rect rect) {
        int x = Math.max(this.x, rect.x);
        int y = Math.max(this.y, rect.y);
        int width = Math.min(this.x + this.width, rect.x + rect.width) - x;
        int height = Math.min(this.y + this.height, rect.y + rect.height) - y;
        return new Rect(x, y, width, height);
    }

    public Point getAnchor(Direction direction) {
        int anchorX = x + (width * direction.getCol() / 2);
        int anchorY = y + (height * direction.getRow() / 2);
        return new Point(anchorX, anchorY);
    }

    public Rect anchor(Rect container, Direction direction) {
        Point anchor = container.getAnchor(direction);
        return align(anchor, direction);
    }

    public Rect align(Point anchor, Direction direction) {
        int newX = anchor.getX() - (width * direction.getCol() / 2);
        int newY = anchor.getY() - (height * direction.getRow() / 2);
        return new Rect(newX, newY, width, height);
    }

    public Rect centerOn(Point point) {
        return new Rect(point.getX() - width / 2, point.getY() - height / 2, width, height);
    }

    public Rect centerIn(Rect container) {
        return new Rect(container.x + (container.width - width) / 2, container.y + (container.height - height) / 2, width, height);
    }
}
