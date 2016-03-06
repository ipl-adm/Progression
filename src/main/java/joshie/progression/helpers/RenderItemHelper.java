package joshie.progression.helpers;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.item.ItemStack;

public class RenderItemHelper {
    private final static RenderItem itemRenderer = Minecraft.getMinecraft().getRenderItem();

    public static void drawStack(ItemStack stack, int left, int top, float size) {
        try {
            GL11.glDisable(GL11.GL_ALPHA_TEST);
            GL11.glPushMatrix();
            GL11.glScalef(size, size, size);
            GL11.glDisable(GL11.GL_LIGHTING);
            GL11.glColor3f(1F, 1F, 1F); //Forge: Reset color in case Items change it.
            GL11.glEnable(GL11.GL_BLEND); //Forge: Make sure blend is enabled else tabs show a white border.
            GL11.glEnable(GL11.GL_LIGHTING);
            GL11.glEnable(GL12.GL_RESCALE_NORMAL);
            RenderHelper.enableGUIStandardItemLighting();
            Minecraft mc = ClientHelper.getMinecraft();
            itemRenderer.renderItemAndEffectIntoGUI(stack, (int) (left / size), (int) (top / size));
            itemRenderer.renderItemOverlayIntoGUI(mc.fontRendererObj, stack, (int) (left / size), (int) (top / size), null);
            RenderHelper.disableStandardItemLighting();
            GL11.glDisable(GL11.GL_LIGHTING);
            GL11.glPopMatrix();
            GL11.glEnable(GL11.GL_ALPHA_TEST);
        } catch (Exception e) {}
    }
}
