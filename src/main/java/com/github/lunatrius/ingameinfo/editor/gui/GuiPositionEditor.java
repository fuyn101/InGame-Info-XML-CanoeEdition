package com.github.lunatrius.ingameinfo.editor.gui;

import java.io.IOException;

import com.github.lunatrius.ingameinfo.editor.model.EditorElement;
import com.github.lunatrius.ingameinfo.editor.geom.Point;
import com.github.lunatrius.ingameinfo.editor.render.Color;
import com.github.lunatrius.ingameinfo.editor.render.RenderUtil;
import com.github.lunatrius.ingameinfo.reference.Names;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.util.text.TextComponentTranslation;

public class GuiPositionEditor extends GuiScreen {
    private static final int BUTTON_WIDTH = 80;
    private static final int BUTTON_HEIGHT = 20;

    private final GuiScreen parentScreen;
    private final EditorElement element;

    private GuiTextField txtX;
    private GuiTextField txtY;
    private GuiButton btnApply;
    private GuiButton btnCancel;
    private GuiButton btnReset;
    private GuiButton btnTopLeft;
    private GuiButton btnTopCenter;
    private GuiButton btnTopRight;
    private GuiButton btnMiddleLeft;
    private GuiButton btnMiddleCenter;
    private GuiButton btnMiddleRight;
    private GuiButton btnBottomLeft;
    private GuiButton btnBottomCenter;
    private GuiButton btnBottomRight;

    public GuiPositionEditor(GuiScreen parentScreen, EditorElement element) {
        this.parentScreen = parentScreen;
        this.element = element;
    }

    @Override
    public void initGui() {
        int centerX = width / 2;
        int centerY = height / 2;

        txtX = new GuiTextField(0, fontRenderer, centerX - 60, centerY - 50, 50, 20);
        txtX.setText(String.valueOf(element.getX()));
        txtX.setMaxStringLength(6);

        txtY = new GuiTextField(1, fontRenderer, centerX + 10, centerY - 50, 50, 20);
        txtY.setText(String.valueOf(element.getY()));
        txtY.setMaxStringLength(6);

        int buttonY = centerY - 20;

        btnApply = new GuiButton(2, centerX - 85, buttonY, BUTTON_WIDTH, BUTTON_HEIGHT, new TextComponentTranslation(Names.Editor.SAVE).getFormattedText());
        btnCancel = new GuiButton(3, centerX + 5, buttonY, BUTTON_WIDTH, BUTTON_HEIGHT, new TextComponentTranslation(Names.Editor.CONFIRM_CANCEL).getFormattedText());
        btnReset = new GuiButton(4, centerX - 85, buttonY + 25, BUTTON_WIDTH, BUTTON_HEIGHT, new TextComponentTranslation(Names.Editor.RESET).getFormattedText());

        int gridY = centerY + 20;
        int gridX = centerX - 90;
        int gridSize = 30;
        int gridSpacing = 5;

        btnTopLeft = new GuiButton(10, gridX, gridY, gridSize, gridSize, "↖");
        btnTopCenter = new GuiButton(11, gridX + gridSize + gridSpacing, gridY, gridSize, gridSize, "↑");
        btnTopRight = new GuiButton(12, gridX + (gridSize + gridSpacing) * 2, gridY, gridSize, gridSize, "↗");
        btnMiddleLeft = new GuiButton(13, gridX, gridY + gridSize + gridSpacing, gridSize, gridSize, "←");
        btnMiddleCenter = new GuiButton(14, gridX + gridSize + gridSpacing, gridY + gridSize + gridSpacing, gridSize, gridSize, "●");
        btnMiddleRight = new GuiButton(15, gridX + (gridSize + gridSpacing) * 2, gridY + gridSize + gridSpacing, gridSize, gridSize, "→");
        btnBottomLeft = new GuiButton(16, gridX, gridY + (gridSize + gridSpacing) * 2, gridSize, gridSize, "↙");
        btnBottomCenter = new GuiButton(17, gridX + gridSize + gridSpacing, gridY + (gridSize + gridSpacing) * 2, gridSize, gridSize, "↓");
        btnBottomRight = new GuiButton(18, gridX + (gridSize + gridSpacing) * 2, gridY + (gridSize + gridSpacing) * 2, gridSize, gridSize, "↘");

        buttonList.add(btnApply);
        buttonList.add(btnCancel);
        buttonList.add(btnReset);
        buttonList.add(btnTopLeft);
        buttonList.add(btnTopCenter);
        buttonList.add(btnTopRight);
        buttonList.add(btnMiddleLeft);
        buttonList.add(btnMiddleCenter);
        buttonList.add(btnMiddleRight);
        buttonList.add(btnBottomLeft);
        buttonList.add(btnBottomCenter);
        buttonList.add(btnBottomRight);
    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException {
        if (button == btnApply) {
            applyPosition();
            mc.displayGuiScreen(parentScreen);
        } else if (button == btnCancel) {
            mc.displayGuiScreen(parentScreen);
        } else if (button == btnReset) {
            element.resetPosition();
            updateTextFields();
        } else if (button == btnTopLeft) {
            element.setX(0);
            element.setY(0);
            updateTextFields();
        } else if (button == btnTopCenter) {
            element.setX(width / 2 - element.getWidth() / 2);
            element.setY(0);
            updateTextFields();
        } else if (button == btnTopRight) {
            element.setX(width - element.getWidth());
            element.setY(0);
            updateTextFields();
        } else if (button == btnMiddleLeft) {
            element.setX(0);
            element.setY(height / 2 - element.getHeight() / 2);
            updateTextFields();
        } else if (button == btnMiddleCenter) {
            element.setX(width / 2 - element.getWidth() / 2);
            element.setY(height / 2 - element.getHeight() / 2);
            updateTextFields();
        } else if (button == btnMiddleRight) {
            element.setX(width - element.getWidth());
            element.setY(height / 2 - element.getHeight() / 2);
            updateTextFields();
        } else if (button == btnBottomLeft) {
            element.setX(0);
            element.setY(height - element.getHeight());
            updateTextFields();
        } else if (button == btnBottomCenter) {
            element.setX(width / 2 - element.getWidth() / 2);
            element.setY(height - element.getHeight());
            updateTextFields();
        } else if (button == btnBottomRight) {
            element.setX(width - element.getWidth());
            element.setY(height - element.getHeight());
            updateTextFields();
        }
    }

    @Override
    protected void keyTyped(char typedChar, int keyCode) throws IOException {
        if (txtX.textboxKeyTyped(typedChar, keyCode) || txtY.textboxKeyTyped(typedChar, keyCode)) {
            updatePositionFromText();
        } else {
            super.keyTyped(typedChar, keyCode);
        }
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        super.mouseClicked(mouseX, mouseY, mouseButton);
        txtX.mouseClicked(mouseX, mouseY, mouseButton);
        txtY.mouseClicked(mouseX, mouseY, mouseButton);
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        drawDefaultBackground();

        String title = new TextComponentTranslation(Names.Editor.POSITION_EDITOR).getFormattedText();
        drawCenteredString(fontRenderer, title, width / 2, 10, Color.WHITE.getPacked());

        int centerX = width / 2;
        int centerY = height / 2;

        String xPosText = new TextComponentTranslation(Names.Editor.X_OFFSET).getFormattedText();
        String yPosText = new TextComponentTranslation(Names.Editor.Y_OFFSET).getFormattedText();
        fontRenderer.drawString(xPosText, centerX - 100, centerY - 45, Color.WHITE.getPacked());
        fontRenderer.drawString(yPosText, centerX - 30, centerY - 45, Color.WHITE.getPacked());

        txtX.drawTextBox();
        txtY.drawTextBox();

        String quickAlignText = new TextComponentTranslation(Names.Editor.QUICK_ALIGN).getFormattedText();
        fontRenderer.drawString(quickAlignText, centerX - 100, centerY + 10, Color.GRAY.getPacked());

        drawPreview(centerX, centerY + 100);

        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    private void drawPreview(int x, int y) {
        int previewSize = 80;
        int previewX = x - previewSize / 2;
        int previewY = y - previewSize / 2;

        RenderUtil.drawBorderRect(new com.github.lunatrius.ingameinfo.editor.geom.Rect(previewX, previewY, previewSize, previewSize), Color.GRAY);

        int elementX = previewX + (element.getX() * previewSize / width);
        int elementY = previewY + (element.getY() * previewSize / height);
        int elementW = element.getWidth() * previewSize / width;
        int elementH = element.getHeight() * previewSize / height;

        RenderUtil.drawFilledRect(new com.github.lunatrius.ingameinfo.editor.geom.Rect(elementX, elementY, elementW, elementH), 
            new Color(0, 255, 0, 128), Color.GREEN);
    }

    private void applyPosition() {
        try {
            int x = Integer.parseInt(txtX.getText());
            int y = Integer.parseInt(txtY.getText());
            element.setX(x);
            element.setY(y);
            element.applyPosition();
        } catch (NumberFormatException e) {
        }
    }

    private void updatePositionFromText() {
        try {
            int x = Integer.parseInt(txtX.getText());
            int y = Integer.parseInt(txtY.getText());
            element.setX(x);
            element.setY(y);
        } catch (NumberFormatException e) {
        }
    }

    private void updateTextFields() {
        txtX.setText(String.valueOf(element.getX()));
        txtY.setText(String.valueOf(element.getY()));
    }

    @Override
    public void updateScreen() {
        txtX.updateCursorCounter();
        txtY.updateCursorCounter();
    }

    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }
}
