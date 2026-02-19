package com.github.lunatrius.ingameinfo.editor.model;

import com.github.lunatrius.ingameinfo.Alignment;
import com.github.lunatrius.ingameinfo.client.gui.overlay.Info;
import com.github.lunatrius.ingameinfo.editor.geom.Point;
import com.github.lunatrius.ingameinfo.editor.geom.Rect;

import java.util.ArrayList;
import java.util.List;

public class EditorElement {
    private final Info info;
    private final Alignment alignment;
    private final List<EditorElement> children = new ArrayList<EditorElement>();

    private int x;
    private int y;
    private int width;
    private int height;

    private boolean selected = false;
    private boolean hovered = false;
    private boolean dragging = false;

    private int dragStartX = 0;
    private int dragStartY = 0;
    private int originalX = 0;
    private int originalY = 0;

    public EditorElement(Info info, Alignment alignment) {
        this.info = info;
        this.alignment = alignment;
        this.x = info.x;
        this.y = info.y;
        updateBounds();
    }

    public void updateBounds() {
        this.width = info.getWidth();
        this.height = info.getHeight();
    }

    public Info getInfo() {
        return info;
    }

    public Alignment getAlignment() {
        return alignment;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public Rect getBounds() {
        return new Rect(x, y, width, height);
    }

    public Rect getAbsoluteBounds() {
        int absX = info.getX();
        int absY = info.getY();
        return new Rect(absX, absY, width, height);
    }

    public boolean isChild() {
        return info.offsetX != 0 || info.offsetY != 0;
    }

    public List<EditorElement> getChildren() {
        return children;
    }

    public void addChild(EditorElement child) {
        children.add(child);
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public boolean isHovered() {
        return hovered;
    }

    public void setHovered(boolean hovered) {
        this.hovered = hovered;
    }

    public boolean isDragging() {
        return dragging;
    }

    public void startDrag(int mouseX, int mouseY) {
        this.dragging = true;
        this.dragStartX = mouseX;
        this.dragStartY = mouseY;
        this.originalX = this.x;
        this.originalY = this.y;
    }

    public void updateDrag(int mouseX, int mouseY, boolean snapToGrid, int gridSize) {
        if (!dragging) return;

        int dx = mouseX - dragStartX;
        int dy = mouseY - dragStartY;

        int newX = originalX + dx;
        int newY = originalY + dy;

        if (snapToGrid) {
            newX = Math.round((float) newX / gridSize) * gridSize;
            newY = Math.round((float) newY / gridSize) * gridSize;
        }

        this.x = newX;
        this.y = newY;
    }

    public void endDrag() {
        this.dragging = false;
        applyPosition();
    }

    public void cancelDrag() {
        this.dragging = false;
        this.x = originalX;
        this.y = originalY;
    }

    public void applyPosition() {
        if (isChild()) {
            this.info.x = this.x;
            this.info.y = this.y;
        } else {
            this.info.x = this.x;
            this.info.y = this.y;
            if (alignment != null) {
                alignment.x = this.x;
                alignment.y = this.y;
            }
        }
    }

    public void resetPosition() {
        if (alignment != null) {
            this.x = alignment.defaultX;
            this.y = alignment.defaultY;
            applyPosition();
        }
    }

    public void alignToPosition(Alignment newAlignment) {
        if (newAlignment != null) {
            this.x = newAlignment.defaultX;
            this.y = newAlignment.defaultY;
            applyPosition();
        }
    }

    public boolean containsPoint(int mouseX, int mouseY) {
        return getAbsoluteBounds().contains(mouseX, mouseY);
    }

    public String getDisplayText() {
        String text = "";
        if (info != null) {
            text = info.toString();
            if (text.startsWith("InfoText{")) {
                text = text.replace("InfoText{", "").replace("}", "");
            }
        }
        return text;
    }

    @Override
    public String toString() {
        return String.format("EditorElement{x: %d, y: %d, width: %d, height: %d, alignment: %s}", x, y, width, height, alignment);
    }
}
