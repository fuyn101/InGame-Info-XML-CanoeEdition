package com.github.lunatrius.ingameinfo.editor.gui;

import java.io.IOException;
import java.util.List;

import com.github.lunatrius.ingameinfo.Alignment;
import com.github.lunatrius.ingameinfo.editor.geom.Point;
import com.github.lunatrius.ingameinfo.editor.geom.Rect;
import com.github.lunatrius.ingameinfo.editor.render.Color;
import com.github.lunatrius.ingameinfo.editor.render.RenderUtil;
import com.github.lunatrius.ingameinfo.reference.Names;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiSlot;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.util.text.TextComponentTranslation;

public class GuiAlignmentEditor extends GuiScreen {
    private static final int SLOT_HEIGHT = 24;

    private final GuiScreen parentScreen;

    private GuiAlignmentSlot alignmentSlot;
    private GuiTextField txtOffsetX;
    private GuiTextField txtOffsetY;
    private GuiButton btnDone;
    private GuiButton btnReset;

    private Alignment selectedAlignment = Alignment.TOPLEFT;

    public GuiAlignmentEditor(GuiScreen parentScreen) {
        this.parentScreen = parentScreen;
    }

    @Override
    public void initGui() {
        int slotWidth = 200;
        int slotHeight = height - 100;
        int slotX = 20;

        alignmentSlot = new GuiAlignmentSlot(mc, slotWidth, slotHeight, 30, height - 70, SLOT_HEIGHT, this);
        alignmentSlot.setSlotXBoundsFromLeft(slotX);

        int rightX = width - 150;
        int topY = 50;

        txtOffsetX = new GuiTextField(0, fontRenderer, rightX, topY, 60, 20);
        txtOffsetX.setText(String.valueOf(selectedAlignment.x));
        txtOffsetX.setMaxStringLength(6);

        txtOffsetY = new GuiTextField(1, fontRenderer, rightX + 70, topY, 60, 20);
        txtOffsetY.setText(String.valueOf(selectedAlignment.y));
        txtOffsetY.setMaxStringLength(6);

        int buttonY = height - 25;
        int centerX = width / 2;

        btnDone = new GuiButton(2, centerX - 85, buttonY, 80, 20, new TextComponentTranslation(Names.Editor.SAVE).getFormattedText());
        btnReset = new GuiButton(3, centerX + 5, buttonY, 80, 20, new TextComponentTranslation(Names.Editor.RESET).getFormattedText());

        buttonList.add(btnDone);
        buttonList.add(btnReset);
    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException {
        if (button == btnDone) {
            applyChanges();
            mc.displayGuiScreen(parentScreen);
        } else if (button == btnReset) {
            selectedAlignment.x = selectedAlignment.defaultX;
            selectedAlignment.y = selectedAlignment.defaultY;
            txtOffsetX.setText(String.valueOf(selectedAlignment.x));
            txtOffsetY.setText(String.valueOf(selectedAlignment.y));
        }
    }

    private void applyChanges() {
        try {
            int x = Integer.parseInt(txtOffsetX.getText());
            int y = Integer.parseInt(txtOffsetY.getText());
            selectedAlignment.x = x;
            selectedAlignment.y = y;
        } catch (NumberFormatException e) {
        }
    }

    public void selectAlignment(Alignment alignment) {
        this.selectedAlignment = alignment;
        txtOffsetX.setText(String.valueOf(alignment.x));
        txtOffsetY.setText(String.valueOf(alignment.y));
    }

    @Override
    protected void keyTyped(char typedChar, int keyCode) throws IOException {
        if (txtOffsetX.textboxKeyTyped(typedChar, keyCode)) {
            updateOffsetFromText();
        } else if (txtOffsetY.textboxKeyTyped(typedChar, keyCode)) {
            updateOffsetFromText();
        } else {
            super.keyTyped(typedChar, keyCode);
        }
    }

    private void updateOffsetFromText() {
        try {
            int x = Integer.parseInt(txtOffsetX.getText());
            int y = Integer.parseInt(txtOffsetY.getText());
            selectedAlignment.x = x;
            selectedAlignment.y = y;
        } catch (NumberFormatException e) {
        }
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        super.mouseClicked(mouseX, mouseY, mouseButton);
        txtOffsetX.mouseClicked(mouseX, mouseY, mouseButton);
        txtOffsetY.mouseClicked(mouseX, mouseY, mouseButton);
    }

    @Override
    public void handleMouseInput() throws IOException {
        super.handleMouseInput();
        alignmentSlot.handleMouseInput();
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        drawDefaultBackground();

        String title = new TextComponentTranslation(Names.Editor.ALIGNMENT_EDITOR).getFormattedText();
        drawCenteredString(fontRenderer, title, width / 2, 10, Color.WHITE.getPacked());

        alignmentSlot.drawScreen(mouseX, mouseY, partialTicks);

        int rightX = width - 150;
        int topY = 30;

        String selectedText = new TextComponentTranslation(Names.Editor.SELECTED).getFormattedText();
        fontRenderer.drawString(selectedText + ": " + selectedAlignment.toString(), rightX, topY, Color.YELLOW.getPacked());

        String xPosText = new TextComponentTranslation(Names.Editor.X_OFFSET).getFormattedText();
        String yPosText = new TextComponentTranslation(Names.Editor.Y_OFFSET).getFormattedText();
        fontRenderer.drawString(xPosText, rightX, topY + 25, Color.WHITE.getPacked());
        fontRenderer.drawString(yPosText, rightX + 70, topY + 25, Color.WHITE.getPacked());

        txtOffsetX.drawTextBox();
        txtOffsetY.drawTextBox();

        drawPreview(rightX, topY + 70);

        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    private void drawPreview(int x, int y) {
        int previewSize = 120;
        Rect previewRect = new Rect(x, y, previewSize, previewSize);

        RenderUtil.drawBorderRect(previewRect, Color.GRAY);

        String label = new TextComponentTranslation(Names.Editor.PREVIEW).getFormattedText();
        RenderUtil.drawString(label, new Point(x + previewSize / 2 - fontRenderer.getStringWidth(label) / 2, y - 12), Color.GRAY);

        int centerX = x + previewSize / 2;
        int centerY = y + previewSize / 2;

        RenderUtil.drawRect(new Rect(centerX - 1, y, 2, previewSize), new Color(100, 100, 100, 64));
        RenderUtil.drawRect(new Rect(x, centerY - 1, previewSize, 2), new Color(100, 100, 100, 64));

        int indicatorX = centerX + selectedAlignment.x;
        int indicatorY = centerY + selectedAlignment.y;

        RenderUtil.drawCrosshair(new Point(indicatorX, indicatorY), 3, Color.RED);

        String posText = String.format("(%d, %d)", selectedAlignment.x, selectedAlignment.y);
        RenderUtil.drawString(posText, new Point(indicatorX + 5, indicatorY - 8), Color.WHITE);
    }

    @Override
    public void updateScreen() {
        txtOffsetX.updateCursorCounter();
        txtOffsetY.updateCursorCounter();
    }

    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }

    private static class GuiAlignmentSlot extends GuiSlot {
        private final GuiAlignmentEditor parent;
        private final Alignment[] alignments = Alignment.values();
        private int selectedIndex = 0;

        public GuiAlignmentSlot(net.minecraft.client.Minecraft mc, int width, int height, int top, int bottom, int slotHeight, GuiAlignmentEditor parent) {
            super(mc, width, height, top, bottom, slotHeight);
            this.parent = parent;
        }

        @Override
        protected int getSize() {
            return alignments.length;
        }

        @Override
        protected void elementClicked(int slotIndex, boolean isDoubleClick, int mouseX, int mouseY) {
            selectedIndex = slotIndex;
            parent.selectAlignment(alignments[slotIndex]);
        }

        @Override
        protected boolean isSelected(int slotIndex) {
            return slotIndex == selectedIndex;
        }

        @Override
        protected void drawBackground() {
        }

        @Override
        protected void drawSlot(int slotIndex, int xPos, int yPos, int heightIn, int mouseXIn, int mouseYIn, float partialTicks) {
            Alignment alignment = alignments[slotIndex];

            String name = alignment.toString();
            String pos = String.format("(%d, %d)", alignment.x, alignment.y);

            parent.fontRenderer.drawString(name, xPos + 5, yPos + 3, Color.WHITE.getPacked());
            parent.fontRenderer.drawString(pos, xPos + 5, yPos + 13, Color.GRAY.getPacked());
        }
    }
}
