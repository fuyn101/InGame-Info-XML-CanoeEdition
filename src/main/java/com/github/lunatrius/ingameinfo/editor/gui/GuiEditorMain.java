package com.github.lunatrius.ingameinfo.editor.gui;

import java.io.IOException;

import com.github.lunatrius.ingameinfo.Alignment;
import com.github.lunatrius.ingameinfo.InGameInfoCore;
import com.github.lunatrius.ingameinfo.editor.model.EditorElement;
import com.github.lunatrius.ingameinfo.editor.model.EditorState;
import com.github.lunatrius.ingameinfo.editor.geom.Point;
import com.github.lunatrius.ingameinfo.editor.geom.Rect;
import com.github.lunatrius.ingameinfo.editor.render.Color;
import com.github.lunatrius.ingameinfo.editor.render.RenderUtil;
import com.github.lunatrius.ingameinfo.handler.ConfigurationHandler;
import com.github.lunatrius.ingameinfo.reference.Names;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.util.text.TextComponentTranslation;

public class GuiEditorMain extends GuiScreen {
    private static final int BUTTON_WIDTH = 80;
    private static final int BUTTON_HEIGHT = 20;
    private static final int BUTTON_SPACING = 5;

    private final GuiScreen parentScreen;
    private final EditorState editorState;

    private GuiButton btnSave;
    private GuiButton btnReload;
    private GuiButton btnReset;
    private GuiButton btnGrid;
    private GuiButton btnSnap;
    private GuiButton btnPosition;
    private GuiButton btnAlignment;

    private boolean isDragging = false;
    private int dragStartX = 0;
    private int dragStartY = 0;

    private int viewportX = 0;
    private int viewportY = 0;
    private int viewportWidth = 0;
    private int viewportHeight = 0;

    public GuiEditorMain(GuiScreen parentScreen) {
        this.parentScreen = parentScreen;
        this.editorState = new EditorState();
    }

    @Override
    public void initGui() {
        ScaledResolution resolution = new ScaledResolution(mc);

        viewportWidth = resolution.getScaledWidth() - 220;
        viewportHeight = resolution.getScaledHeight() - 60;
        viewportX = 20;
        viewportY = 40;

        int buttonX = resolution.getScaledWidth() - 190;
        int buttonY = 50;

        btnSave = new GuiButton(0, buttonX, buttonY, BUTTON_WIDTH, BUTTON_HEIGHT, new TextComponentTranslation(Names.Editor.SAVE).getFormattedText());
        btnReload = new GuiButton(1, buttonX, buttonY + (BUTTON_HEIGHT + BUTTON_SPACING) * 1, BUTTON_WIDTH, BUTTON_HEIGHT, new TextComponentTranslation(Names.Editor.RELOAD).getFormattedText());
        btnReset = new GuiButton(2, buttonX, buttonY + (BUTTON_HEIGHT + BUTTON_SPACING) * 2, BUTTON_WIDTH, BUTTON_HEIGHT, new TextComponentTranslation(Names.Editor.RESET).getFormattedText());
        btnGrid = new GuiButton(3, buttonX, buttonY + (BUTTON_HEIGHT + BUTTON_SPACING) * 3, BUTTON_WIDTH, BUTTON_HEIGHT, new TextComponentTranslation(Names.Editor.GRID).getFormattedText());
        btnSnap = new GuiButton(4, buttonX, buttonY + (BUTTON_HEIGHT + BUTTON_SPACING) * 4, BUTTON_WIDTH, BUTTON_HEIGHT, new TextComponentTranslation(Names.Editor.SNAP).getFormattedText());
        btnPosition = new GuiButton(5, buttonX, buttonY + (BUTTON_HEIGHT + BUTTON_SPACING) * 5, BUTTON_WIDTH, BUTTON_HEIGHT, new TextComponentTranslation(Names.Editor.POSITION).getFormattedText());
        btnAlignment = new GuiButton(6, buttonX, buttonY + (BUTTON_HEIGHT + BUTTON_SPACING) * 6, BUTTON_WIDTH, BUTTON_HEIGHT, new TextComponentTranslation(Names.Editor.ALIGNMENT).getFormattedText());

        buttonList.add(btnSave);
        buttonList.add(btnReload);
        buttonList.add(btnReset);
        buttonList.add(btnGrid);
        buttonList.add(btnSnap);
        buttonList.add(btnPosition);
        buttonList.add(btnAlignment);

        updateButtonStates();
    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException {
        if (button == btnSave) {
            saveConfig();
        } else if (button == btnReload) {
            editorState.reload();
        } else if (button == btnReset) {
            editorState.resetSelectedElement();
        } else if (button == btnGrid) {
            editorState.setShowGrid(!editorState.isShowGrid());
            updateButtonStates();
        } else if (button == btnSnap) {
            editorState.setSnapToGrid(!editorState.isSnapToGrid());
            updateButtonStates();
        } else if (button == btnPosition) {
            EditorElement selected = editorState.getSelectedElement();
            if (selected != null) {
                mc.displayGuiScreen(new GuiPositionEditor(this, selected));
            }
        } else if (button == btnAlignment) {
            mc.displayGuiScreen(new GuiAlignmentEditor(this));
        }
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        drawDefaultBackground();

        drawTitle();
        drawViewport();
        drawElements(mouseX, mouseY);
        drawGrid();
        drawSelectedElementInfo();
        drawControls();

        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    private void drawTitle() {
        String title = new TextComponentTranslation(Names.Editor.TITLE).getFormattedText();
        drawCenteredString(fontRenderer, title, width / 2, 10, Color.WHITE.getPacked());

        String configName = new TextComponentTranslation(Names.Editor.CONFIG, InGameInfoCore.INSTANCE.getConfigName()).getFormattedText();
        fontRenderer.drawString(configName, 20, 25, Color.GRAY.getPacked());

        if (editorState.hasUnsavedChanges()) {
            String unsaved = new TextComponentTranslation(Names.Editor.UNSAVED).getFormattedText();
            fontRenderer.drawString(unsaved, width - fontRenderer.getStringWidth(unsaved) - 20, 25, Color.YELLOW.getPacked());
        }
    }

    private void drawViewport() {
        Rect viewport = new Rect(viewportX, viewportY, viewportWidth, viewportHeight);
        RenderUtil.drawBorderRect(viewport, Color.DARK_GRAY);
    }

    private void drawElements(int mouseX, int mouseY) {
        for (EditorElement element : editorState.getElements()) {
            element.setHovered(element.containsPoint(mouseX, mouseY));

            Rect bounds = element.getAbsoluteBounds();

            if (element.isSelected()) {
                RenderUtil.drawFilledRect(bounds, new Color(0, 255, 0, 32), Color.GREEN);
            } else if (element.isHovered()) {
                RenderUtil.drawFilledRect(bounds, new Color(255, 255, 0, 32), Color.YELLOW);
            } else {
                RenderUtil.drawRect(bounds, new Color(255, 255, 255, 16));
            }

            String text = element.getDisplayText();
            if (!text.isEmpty()) {
                RenderUtil.drawString(text, new Point(bounds.getX() + 2, bounds.getY() + 2), Color.WHITE);
            }
        }
    }

    private void drawGrid() {
        if (!editorState.isShowGrid()) return;

        Rect viewport = new Rect(viewportX, viewportY, viewportWidth, viewportHeight);
        RenderUtil.drawGrid(viewport, editorState.getGridSize(), new Color(128, 128, 128, 64));
    }

    private void drawSelectedElementInfo() {
        EditorElement selected = editorState.getSelectedElement();
        if (selected == null) return;

        int infoX = viewportX;
        int infoY = viewportY + viewportHeight + 10;

        String selectedText = new TextComponentTranslation(Names.Editor.SELECTED).getFormattedText();
        String infoText = String.format("%s: (%d, %d) [%dx%d]", 
            selectedText, selected.getX(), selected.getY(), selected.getWidth(), selected.getHeight());
        fontRenderer.drawString(infoText, infoX, infoY, Color.CYAN.getPacked());

        String alignmentText = new TextComponentTranslation(Names.Editor.ALIGNMENT).getFormattedText();
        String alignmentValue = selected.getAlignment() != null ? selected.getAlignment().toString() : new TextComponentTranslation(Names.Editor.ALIGNMENT_NONE).getFormattedText();
        fontRenderer.drawString(alignmentText + ": " + alignmentValue, infoX, infoY + 12, Color.CYAN.getPacked());
    }

    private void drawControls() {
        int controlsX = viewportX;
        int controlsY = viewportY + viewportHeight + 30;

        String[] controls = {
            new TextComponentTranslation(Names.Editor.CONTROL_CLICK).getFormattedText(),
            new TextComponentTranslation(Names.Editor.CONTROL_DRAG).getFormattedText(),
            new TextComponentTranslation(Names.Editor.CONTROL_TAB).getFormattedText(),
            new TextComponentTranslation(Names.Editor.CONTROL_ARROWS).getFormattedText(),
            new TextComponentTranslation(Names.Editor.CONTROL_G).getFormattedText(),
            new TextComponentTranslation(Names.Editor.CONTROL_CTRLSNAP).getFormattedText(),
            new TextComponentTranslation(Names.Editor.CONTROL_CTRLS).getFormattedText(),
            new TextComponentTranslation(Names.Editor.CONTROL_CTRLR).getFormattedText()
        };

        for (int i = 0; i < controls.length; i++) {
            fontRenderer.drawString(controls[i], controlsX, controlsY + i * 12, Color.LIGHT_GRAY.getPacked());
        }
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        super.mouseClicked(mouseX, mouseY, mouseButton);

        if (isInViewport(mouseX, mouseY)) {
            EditorElement element = editorState.getElementAtPoint(mouseX, mouseY);

            if (element != null) {
                editorState.setSelectedElement(element);
                if (mouseButton == 0) {
                    element.startDrag(mouseX, mouseY);
                    isDragging = true;
                    dragStartX = mouseX;
                    dragStartY = mouseY;
                }
            } else {
                editorState.clearSelection();
            }
            updateButtonStates();
        }
    }

    @Override
    protected void mouseClickMove(int mouseX, int mouseY, int clickedMouseButton, long timeSinceLastClick) {
        super.mouseClickMove(mouseX, mouseY, clickedMouseButton, timeSinceLastClick);

        if (isDragging && isInViewport(mouseX, mouseY)) {
            EditorElement selected = editorState.getSelectedElement();
            if (selected != null) {
                boolean snap = editorState.isSnapToGrid() || isCtrlKeyDown();
                selected.updateDrag(mouseX, mouseY, snap, editorState.getGridSize());
            }
        }
    }

    @Override
    protected void mouseReleased(int mouseX, int mouseY, int state) {
        super.mouseReleased(mouseX, mouseY, state);

        if (isDragging) {
            EditorElement selected = editorState.getSelectedElement();
            if (selected != null) {
                selected.endDrag();
                editorState.setHasUnsavedChanges(true);
            }
            isDragging = false;
        }
    }

    @Override
    protected void keyTyped(char typedChar, int keyCode) throws IOException {
        if (keyCode == 1) {
            if (editorState.hasUnsavedChanges()) {
                mc.displayGuiScreen(new GuiConfirmSave(this, parentScreen));
            } else {
                mc.displayGuiScreen(parentScreen);
            }
            return;
        }

        if (isCtrlKeyDown()) {
            if (keyCode == 19) {
                saveConfig();
                return;
            } else if (keyCode == 19) {
                editorState.reload();
                return;
            }
        }

        if (keyCode == 15) {
            editorState.selectNextElement();
            updateButtonStates();
            return;
        }

        if (keyCode == 35) {
            editorState.setShowGrid(!editorState.isShowGrid());
            updateButtonStates();
            return;
        }

        int moveAmount = isShiftKeyDown() ? 10 : 1;
        if (keyCode == 200) {
            editorState.moveSelectedElement(0, -moveAmount);
        } else if (keyCode == 208) {
            editorState.moveSelectedElement(0, moveAmount);
        } else if (keyCode == 203) {
            editorState.moveSelectedElement(-moveAmount, 0);
        } else if (keyCode == 205) {
            editorState.moveSelectedElement(moveAmount, 0);
        }

        super.keyTyped(typedChar, keyCode);
    }

    private boolean isInViewport(int mouseX, int mouseY) {
        return mouseX >= viewportX && mouseX < viewportX + viewportWidth &&
               mouseY >= viewportY && mouseY < viewportY + viewportHeight;
    }

    public void saveConfig() {
        String configName = InGameInfoCore.INSTANCE.getConfigName();
        
        for (Alignment alignment : Alignment.values()) {
            if (ConfigurationHandler.propAlignments.containsKey(alignment)) {
                ConfigurationHandler.propAlignments.get(alignment).set(alignment.getXY());
            }
        }
        
        if (InGameInfoCore.INSTANCE.saveConfig(configName)) {
            editorState.applyAllChanges();
            editorState.setHasUnsavedChanges(false);
            ConfigurationHandler.save();
        }
    }

    private void updateButtonStates() {
        String gridText = new TextComponentTranslation(Names.Editor.GRID).getFormattedText();
        String snapText = new TextComponentTranslation(Names.Editor.SNAP).getFormattedText();
        String onText = ": ON";
        String offText = ": OFF";
        
        btnGrid.displayString = gridText + (editorState.isShowGrid() ? onText : offText);
        btnSnap.displayString = snapText + (editorState.isSnapToGrid() ? onText : offText);
        btnPosition.enabled = editorState.getSelectedElement() != null;
        btnReset.enabled = editorState.getSelectedElement() != null;
    }

    @Override
    public void onGuiClosed() {
        editorState.applyAllChanges();
    }

    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }
}
