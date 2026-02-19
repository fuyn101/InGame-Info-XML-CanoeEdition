package com.github.lunatrius.ingameinfo.editor.render;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.item.ItemStack;

import org.lwjgl.opengl.GL11;

import com.github.lunatrius.ingameinfo.editor.geom.Point;
import com.github.lunatrius.ingameinfo.editor.geom.Rect;

public class RenderUtil {
    private static final Minecraft mc = Minecraft.getMinecraft();

    public static void drawRect(Rect bounds, Color color) {
        int x = bounds.getX();
        int y = bounds.getY();
        int width = bounds.getWidth();
        int height = bounds.getHeight();

        if (width <= 0 || height <= 0) return;

        int minX = x;
        int minY = y;
        int maxX = x + width;
        int maxY = y + height;

        float alpha = color.getAlpha() / 255.0F;
        float red = color.getRed() / 255.0F;
        float green = color.getGreen() / 255.0F;
        float blue = color.getBlue() / 255.0F;

        GlStateManager.enableBlend();
        GlStateManager.disableTexture2D();
        GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
        GlStateManager.color(red, green, blue, alpha);

        Gui.drawModalRectWithCustomSizedTexture(minX, minY, 0, 0, width, height, width, height);

        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
    }

    public static void drawBorderRect(Rect bounds, Color color) {
        int x = bounds.getX();
        int y = bounds.getY();
        int width = bounds.getWidth();
        int height = bounds.getHeight();

        if (width <= 0 || height <= 0) return;

        int minX = x;
        int minY = y;
        int maxX = x + width;
        int maxY = y + height;

        float alpha = color.getAlpha() / 255.0F;
        float red = color.getRed() / 255.0F;
        float green = color.getGreen() / 255.0F;
        float blue = color.getBlue() / 255.0F;

        GlStateManager.enableBlend();
        GlStateManager.disableTexture2D();
        GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
        GlStateManager.color(red, green, blue, alpha);

        Gui.drawModalRectWithCustomSizedTexture(minX, minY, 0, 0, width, 1, width, 1);
        Gui.drawModalRectWithCustomSizedTexture(minX, maxY - 1, 0, 0, width, 1, width, 1);
        Gui.drawModalRectWithCustomSizedTexture(minX, minY, 0, 0, 1, height, 1, height);
        Gui.drawModalRectWithCustomSizedTexture(maxX - 1, minY, 0, 0, 1, height, 1, height);

        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
    }

    public static void drawFilledRect(Rect bounds, Color fill, Color border) {
        drawRect(bounds, fill);
        drawBorderRect(bounds, border);
    }

    public static void drawString(String text, Point position, Color color) {
        mc.fontRenderer.drawString(text, position.getX(), position.getY(), color.getPacked());
    }

    public static void drawString(String text, int x, int y, Color color) {
        mc.fontRenderer.drawString(text, x, y, color.getPacked());
    }

    public static void drawStringCentered(String text, Rect bounds, Color color) {
        FontRenderer font = mc.fontRenderer;
        int x = bounds.getX() + (bounds.getWidth() - font.getStringWidth(text)) / 2;
        int y = bounds.getY() + (bounds.getHeight() - font.FONT_HEIGHT) / 2;
        font.drawString(text, x, y, color.getPacked());
    }

    public static void drawStringWithShadow(String text, Point position, Color color) {
        mc.fontRenderer.drawStringWithShadow(text, position.getX(), position.getY(), color.getPacked());
    }

    public static void drawLine(Point start, Point end, Color color, int width) {
        GlStateManager.enableBlend();
        GlStateManager.disableTexture2D();
        GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
        GlStateManager.color(color.getRed() / 255.0F, color.getGreen() / 255.0F, color.getBlue() / 255.0F, color.getAlpha() / 255.0F);

        GL11.glLineWidth(width);
        GL11.glBegin(GL11.GL_LINES);
        GL11.glVertex2i(start.getX(), start.getY());
        GL11.glVertex2i(end.getX(), end.getY());
        GL11.glEnd();

        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
    }

    public static void drawCrosshair(Point center, int size, Color color) {
        int x = center.getX();
        int y = center.getY();
        drawLine(new Point(x - size, y), new Point(x + size, y), color, 2);
        drawLine(new Point(x, y - size), new Point(x, y + size), color, 2);
    }

    public static void drawGrid(Rect bounds, int cellSize, Color color) {
        int x = bounds.getX();
        int y = bounds.getY();
        int width = bounds.getWidth();
        int height = bounds.getHeight();

        for (int gx = x; gx <= x + width; gx += cellSize) {
            drawLine(new Point(gx, y), new Point(gx, y + height), color, 1);
        }

        for (int gy = y; gy <= y + height; gy += cellSize) {
            drawLine(new Point(x, gy), new Point(x + width, gy), color, 1);
        }
    }

    public static void drawItemStack(ItemStack stack, int x, int y) {
        if (stack == null || stack.isEmpty()) return;

        GlStateManager.enableDepth();
        RenderHelper.enableGUIStandardItemLighting();
        mc.getRenderItem().renderItemAndEffectIntoGUI(stack, x, y);
        RenderHelper.disableStandardItemLighting();
        GlStateManager.disableDepth();
        mc.getTextureManager().bindTexture(GuiScreen.ICONS);
    }

    public static void beginScissor(Rect scissorRect, ScaledResolution resolution) {
        final Rect scaledRect = scissorRect
                .withY(resolution.getScaledHeight() - scissorRect.getBottom())
                .scale(resolution.getScaleFactor());

        GL11.glEnable(GL11.GL_SCISSOR_TEST);
        GL11.glScissor(scaledRect.getX(), scaledRect.getY(), scaledRect.getWidth(), scaledRect.getHeight());
    }

    public static void endScissor() {
        GL11.glDisable(GL11.GL_SCISSOR_TEST);
    }

    public static void drawTooltipBox(Rect bounds) {
        Color background = new Color(183, 16, 0, 16);
        Color borderTop = new Color(80, 80, 0, 255);
        Color borderBottom = new Color(80, 40, 0, 127);

        drawRect(bounds.withHeight(1).grow(-1, 0, -1, 0), background);
        drawRect(bounds.grow(0, -1, 0, -1), background);
        drawRect(bounds.withTop(bounds.getBottom() - 1).grow(-1, 0, -1, 0), background);
        drawRect(bounds.withLeft(bounds.getRight() - 1).grow(0, -1, 0, -1), background);

        drawRect(bounds.withHeight(1).grow(-1, 0, -1, 0), borderTop);
        drawRect(bounds.withTop(bounds.getBottom() - 1).grow(-1, 0, -1, 0), borderBottom);
        drawRect(bounds.withWidth(1).grow(0, -1, 0, -1), borderTop);
        drawRect(bounds.withLeft(bounds.getRight() - 1).grow(0, -1, 0, -1), borderBottom);
    }

    public static void drawGradientRect(Rect bounds, Color color1, Color color2) {
        int x = bounds.getX();
        int y = bounds.getY();
        int width = bounds.getWidth();
        int height = bounds.getHeight();

        if (width <= 0 || height <= 0) return;

        float alpha1 = color1.getAlpha() / 255.0F;
        float red1 = color1.getRed() / 255.0F;
        float green1 = color1.getGreen() / 255.0F;
        float blue1 = color1.getBlue() / 255.0F;

        float alpha2 = color2.getAlpha() / 255.0F;
        float red2 = color2.getRed() / 255.0F;
        float green2 = color2.getGreen() / 255.0F;
        float blue2 = color2.getBlue() / 255.0F;

        GlStateManager.enableBlend();
        GlStateManager.disableTexture2D();
        GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);

        GlStateManager.color(red1, green1, blue1, alpha1);
        Gui.drawModalRectWithCustomSizedTexture(x, y, 0, 0, width, height, width, height);

        GlStateManager.color(red2, green2, blue2, alpha2);
        Gui.drawModalRectWithCustomSizedTexture(x, y + height, 0, 0, width, height, width, height);

        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
    }
}
