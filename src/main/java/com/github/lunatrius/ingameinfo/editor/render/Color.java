package com.github.lunatrius.ingameinfo.editor.render;

import java.io.Serializable;

public class Color implements Serializable {
    private static final long serialVersionUID = 1L;
    public static final Color WHITE = new Color(255, 255, 255);
    public static final Color BLACK = new Color(0, 0, 0);
    public static final Color RED = new Color(255, 0, 0);
    public static final Color GREEN = new Color(0, 255, 0);
    public static final Color BLUE = new Color(0, 0, 255);
    public static final Color YELLOW = new Color(255, 255, 0);
    public static final Color GRAY = new Color(128, 128, 128);
    public static final Color LIGHT_GRAY = new Color(192, 192, 192);
    public static final Color DARK_GRAY = new Color(64, 64, 64);
    public static final Color ORANGE = new Color(255, 165, 0);
    public static final Color CYAN = new Color(0, 255, 255);
    public static final Color MAGENTA = new Color(255, 0, 255);

    private final int argb;

    public Color(int argb) {
        this.argb = argb;
    }

    public Color(int r, int g, int b) {
        this(255, r, g, b);
    }

    public Color(int a, int r, int g, int b) {
        this.argb = ((a & 0xFF) << 24) | ((r & 0xFF) << 16) | ((g & 0xFF) << 8) | (b & 0xFF);
    }

    public Color(Color color) {
        this.argb = color.argb;
    }

    public int getPacked() {
        return argb;
    }

    public int getAlpha() {
        return (argb >> 24) & 0xFF;
    }

    public int getRed() {
        return (argb >> 16) & 0xFF;
    }

    public int getGreen() {
        return (argb >> 8) & 0xFF;
    }

    public int getBlue() {
        return argb & 0xFF;
    }

    public Color withAlpha(int alpha) {
        return new Color(alpha, getRed(), getGreen(), getBlue());
    }

    public Color withRed(int red) {
        return new Color(getAlpha(), red, getGreen(), getBlue());
    }

    public Color withGreen(int green) {
        return new Color(getAlpha(), getRed(), green, getBlue());
    }

    public Color withBlue(int blue) {
        return new Color(getAlpha(), getRed(), getGreen(), blue);
    }

    public Color add(int r, int g, int b) {
        return new Color(getAlpha(), Math.min(255, getRed() + r), Math.min(255, getGreen() + g), Math.min(255, getBlue() + b));
    }

    public Color sub(int r, int g, int b) {
        return new Color(getAlpha(), Math.max(0, getRed() - r), Math.max(0, getGreen() - g), Math.max(0, getBlue() - b));
    }

    public Color scale(float factor) {
        return new Color(getAlpha(), Math.min(255, Math.round(getRed() * factor)), Math.min(255, Math.round(getGreen() * factor)), Math.min(255, Math.round(getBlue() * factor)));
    }

    public Color brighter() {
        return scale(1.2f);
    }

    public Color darker() {
        return scale(0.8f);
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Color)) return false;
        Color color = (Color) obj;
        return argb == color.argb;
    }

    @Override
    public int hashCode() {
        return argb;
    }

    @Override
    public String toString() {
        return String.format("Color{r: %d, g: %d, b: %d, a: %d}", getRed(), getGreen(), getBlue(), getAlpha());
    }
}
