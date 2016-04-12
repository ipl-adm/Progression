package joshie.progression.gui.core;

import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import static joshie.progression.gui.core.GuiList.CORE;

public class DrawHelper {
    public void drawText(String text, int left, int top, int color) {
        CORE.drawText(text, left, top, color);
    }

    public void drawSplitText(String text, int left, int top, int width, int color) {
        CORE.drawSplitText(text, left, top, width, color);
    }

    public void drawRectangle(int left, int top, int width, int height, int color, int border) {
        CORE.drawRectWithBorder(left, top, left + width, top + height, color, border);
    }

    public void drawGradient(int left, int top, int width, int height, int color, int color2, int border) {
        CORE.drawGradientRectWithBorder(left, top, left + width, top + height, color, color2, border);
    }

    public void drawStack(ItemStack stack, int left, int top, float scale) {
        CORE.drawStack(stack, left, top, scale);
    }

    public void drawText(int renderX, int renderY, String text, int left, int top, int color) {
        CORE.drawText(text, CORE.getOffsetX() + left + renderX, top + renderY, color);
    }

    public void drawSplitText(int renderX, int renderY, String text, int left, int top, int width, int color, float scale) {
        CORE.drawSplitText(text, CORE.getOffsetX() + left + renderX, top + renderY, width, color, scale);
    }

    public void drawGradient(int renderX, int renderY, int left, int top, int width, int height, int color, int color2, int border) {
        CORE.drawGradientRectWithBorder(CORE.getOffsetX() + left + renderX, top + renderY, CORE.getOffsetX() + left + renderX + width, top + renderY + height, color, color2, border);
    }

    public void drawStack(int renderX, int renderY, ItemStack stack, int left, int top, float scale) {
        CORE.drawStack(stack, CORE.getOffsetX() + left + renderX, top + renderY, scale);
    }

    public void drawTexture(int renderX, int renderY, ResourceLocation resource, int left, int top, int u, int v, int width, int height) {
        CORE.drawTexture(resource, CORE.getOffsetX() + left + renderX, top + renderY, u, v, width, height);
    }
}
