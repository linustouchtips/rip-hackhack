package me.zoom.hackhack.ClickGui2.frame;


import me.zoom.hackhack.module.modules.client.ClickGuiModule;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

import java.awt.*;

public class Renderer {


    /**
     * @Author Hoosiers and Lukflug
     * 09/08/20
     * Some functions are from net.minecraft.client.gui.Gui, modified for ClickGui usage
     */

    //no gradient, single color
    public static void drawRectStatic(int leftX, int leftY, int rightX, int rightY, Color color){
        Gui.drawRect(leftX,leftY,rightX,rightY,color.getRGB());
    }


    public static void RenderBoxOutline(double thick, int x1, int y1, int x2, int y2, Color color){
        GlStateManager.pushMatrix();
        GlStateManager.enableAlpha();
        GlStateManager.enableBlend();
        GL11.glEnable(GL11.GL_LINE_SMOOTH);

        GL11.glColor3f(color.getRed()/255, color.getGreen()/255, color.getBlue()/255);
        GL11.glLineWidth(((float) thick));
        GL11.glBegin(GL11.GL_LINE_LOOP);

        GL11.glVertex2i(x1, y1);
        GL11.glVertex2i(x1, y2);
        GL11.glVertex2i(x2, y2);
        GL11.glVertex2i(x2, y1);
        GL11.glEnd();
        GlStateManager.popMatrix();
    }

    public static void renderImage(int x, int y, int xStart, int yStart, int width, int height, float width2, float height2){
        GlStateManager.enableAlpha();
        GlStateManager.enableBlend();
        GL11.glColor3f(1f, 1f, 1f);
        Gui.drawModalRectWithCustomSizedTexture(x, y, xStart, yStart, width, height, width2, height2);
    }

    public static Color getMainColor() {
        return new Color(ClickGuiModule.red.getValue(), ClickGuiModule.green.getValue(), ClickGuiModule.blue.getValue());
    }

    public static Color getTransColor (boolean hovered) {
        Color transColor = new Color(0, 0, 0, ClickGuiModule.opacity.getValue() - 50);



        if (hovered) return new Color(0, 0, 0, ClickGuiModule.opacity.getValue());
        return transColor;
    }

    public static Color getFontColor(boolean hovered, int animating) {

        if (hovered && animating == 0){
            return new Color(255,255,255);
        }
        else return new Color(255 - animating, 255 - animating, 255 - animating);
    }
}