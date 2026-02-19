package com.github.lunatrius.ingameinfo.editor.model;

import com.github.lunatrius.ingameinfo.Alignment;
import com.github.lunatrius.ingameinfo.InGameInfoCore;
import com.github.lunatrius.ingameinfo.client.gui.overlay.Info;
import com.github.lunatrius.ingameinfo.editor.geom.Point;

import java.util.ArrayList;
import java.util.List;

public class EditorState {
    private final List<EditorElement> elements = new ArrayList<EditorElement>();
    private EditorElement selectedElement = null;
    private boolean showGrid = true;
    private boolean snapToGrid = true;
    private int gridSize = 10;
    private boolean hasUnsavedChanges = false;

    public EditorState() {
        loadElements();
    }

    private void loadElements() {
        elements.clear();

        List<Info> infoList = InGameInfoCore.INSTANCE.getInfoList();
        for (Info info : infoList) {
            Alignment alignment = findAlignmentForInfo(info);
            EditorElement element = new EditorElement(info, alignment);
            elements.add(element);
        }
    }

    private Alignment findAlignmentForInfo(Info info) {
        List<Alignment> alignments = InGameInfoCore.INSTANCE.getAlignmentList();
        for (Alignment alignment : alignments) {
            if (alignment.x == info.x && alignment.y == info.y) {
                return alignment;
            }
        }
        return null;
    }

    public List<EditorElement> getElements() {
        return elements;
    }

    public EditorElement getSelectedElement() {
        return selectedElement;
    }

    public void setSelectedElement(EditorElement element) {
        if (this.selectedElement != null) {
            this.selectedElement.setSelected(false);
        }
        this.selectedElement = element;
        if (element != null) {
            element.setSelected(true);
        }
    }

    public void clearSelection() {
        setSelectedElement(null);
    }

    public EditorElement getElementAtPoint(int mouseX, int mouseY) {
        for (int i = elements.size() - 1; i >= 0; i--) {
            EditorElement element = elements.get(i);
            if (element.containsPoint(mouseX, mouseY)) {
                return element;
            }
        }
        return null;
    }

    public void selectNextElement() {
        if (elements.isEmpty()) {
            clearSelection();
            return;
        }

        if (selectedElement == null) {
            setSelectedElement(elements.get(0));
            return;
        }

        int currentIndex = elements.indexOf(selectedElement);
        int nextIndex = (currentIndex + 1) % elements.size();
        setSelectedElement(elements.get(nextIndex));
    }

    public void selectPreviousElement() {
        if (elements.isEmpty()) {
            clearSelection();
            return;
        }

        if (selectedElement == null) {
            setSelectedElement(elements.get(elements.size() - 1));
            return;
        }

        int currentIndex = elements.indexOf(selectedElement);
        int prevIndex = (currentIndex - 1 + elements.size()) % elements.size();
        setSelectedElement(elements.get(prevIndex));
    }

    public void moveSelectedElement(int dx, int dy) {
        if (selectedElement != null) {
            selectedElement.setX(selectedElement.getX() + dx);
            selectedElement.setY(selectedElement.getY() + dy);
            selectedElement.applyPosition();
            hasUnsavedChanges = true;
        }
    }

    public void resetSelectedElement() {
        if (selectedElement != null) {
            selectedElement.resetPosition();
            hasUnsavedChanges = true;
        }
    }

    public void applyAllChanges() {
        for (EditorElement element : elements) {
            element.applyPosition();
        }
        hasUnsavedChanges = false;
    }

    public void reload() {
        loadElements();
        clearSelection();
        hasUnsavedChanges = false;
    }

    public boolean isShowGrid() {
        return showGrid;
    }

    public void setShowGrid(boolean showGrid) {
        this.showGrid = showGrid;
    }

    public boolean isSnapToGrid() {
        return snapToGrid;
    }

    public void setSnapToGrid(boolean snapToGrid) {
        this.snapToGrid = snapToGrid;
    }

    public int getGridSize() {
        return gridSize;
    }

    public void setGridSize(int gridSize) {
        this.gridSize = gridSize;
    }

    public boolean hasUnsavedChanges() {
        return hasUnsavedChanges;
    }

    public void setHasUnsavedChanges(boolean hasUnsavedChanges) {
        this.hasUnsavedChanges = hasUnsavedChanges;
    }
}
