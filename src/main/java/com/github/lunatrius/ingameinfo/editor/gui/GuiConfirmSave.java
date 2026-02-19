package com.github.lunatrius.ingameinfo.editor.gui;

import java.io.IOException;

import com.github.lunatrius.ingameinfo.editor.render.Color;
import com.github.lunatrius.ingameinfo.editor.render.RenderUtil;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;

public class GuiConfirmSave extends GuiScreen {
    private static final int BUTTON_WIDTH = 100;
    private static final int BUTTON_HEIGHT = 20;

    private final GuiScreen parentScreen;
    private final GuiScreen returnScreen;

    private GuiButton btnSave;
    private GuiButton btnDiscard;
    private GuiButton btnCancel;

    public GuiConfirmSave(GuiScreen parentScreen, GuiScreen returnScreen) {
        this.parentScreen = parentScreen;
        this.returnScreen = returnScreen;
    }

    @Override
    public void initGui() {
        int centerX = width / 2;
        int centerY = height / 2;

        btnSave = new GuiButton(0, centerX - BUTTON_WIDTH - 5, centerY + 20, BUTTON_WIDTH, BUTTON_HEIGHT, "Save");
        btnDiscard = new GuiButton(1, centerX + 5, centerY + 20, BUTTON_WIDTH, BUTTON_HEIGHT, "Don't Save");
        btnCancel = new GuiButton(2, centerX - BUTTON_WIDTH / 2, centerY + 45, BUTTON_WIDTH, BUTTON_HEIGHT, "Cancel");

        buttonList.add(btnSave);
        buttonList.add(btnDiscard);
        buttonList.add(btnCancel);
    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException {
        if (button == btnSave) {
            ((GuiEditorMain) parentScreen).saveConfig();
            mc.displayGuiScreen(returnScreen);
        } else if (button == btnDiscard) {
            mc.displayGuiScreen(returnScreen);
        } else if (button == btnCancel) {
            mc.displayGuiScreen(parentScreen);
        }
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        drawDefaultBackground();

        int centerX = width / 2;
        int centerY = height / 2;

        String title = "Unsaved Changes";
        drawCenteredString(fontRenderer, title, centerX, centerY - 20, Color.YELLOW.getPacked());

        String message = "Do you want to save your changes before closing?";
        drawCenteredString(fontRenderer, message, centerX, centerY, Color.WHITE.getPacked());

        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }
}
