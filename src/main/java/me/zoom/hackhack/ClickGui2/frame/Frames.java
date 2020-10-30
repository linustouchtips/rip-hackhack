package me.zoom.hackhack.ClickGui2.frame;

import me.zoom.hackhack.ClickGui2.ClickGUI2;

import me.zoom.hackhack.module.Module;
import me.zoom.hackhack.module.ModuleManager;
import me.zoom.hackhack.module.modules.client.ClickGuiModule;
import me.zoom.hackhack.module.modules.client.Rainbow;
import me.zoom.hackhack.util.font.FontUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.util.ArrayList;

public class Frames {
    public ArrayList<Component> guicomponents;
    public Module.Category category;
    private final int width;
    private final int barHeight;
    private int height;
    public int x;
    public int y;
    public int dragX;
    public int dragY;
    private boolean isDragging;
    public boolean open;
    int nameWidth;
    int centeredNameCoords;
    boolean font;
    int rainbowOffset;

    //dont use
    int buttonHeight;

    public int buttonHeightProper;

    int mouseX;
    int mouseY;

    boolean hovering;

    int animation;

    Minecraft mc = Minecraft.getMinecraft();

    public Frames(final Module.Category catg){
        this.guicomponents = new ArrayList<Component>();
        this.category = catg;
        this.open = true;
        this.isDragging = false;
        this.x = 10;
        this.y = 34;
        this.dragX = 0;
        this.width = 100;
        this.barHeight = 16;
        int tY = this.barHeight;
        this.nameWidth = 0;
        this.centeredNameCoords = 0;
        this.rainbowOffset = 0;

        this.buttonHeight = 0;
        this.buttonHeightProper = 0;

        this.mouseX = 0;
        this.mouseY = 0;

        this.hovering = false;

        this.animation = 50;

        for (final Module mod : ModuleManager.getModulesInCategory(catg)){
            final Buttons devmodButton = new Buttons(mod, this, tY);
            this.guicomponents.add(devmodButton);
            tY += 16;
        }
        this.refresh();
    }

    public ArrayList<Component> getComponents() {
        return this.guicomponents;
    }

    public int getWidth() {
        return this.width;
    }

    public int getX(){
        return this.x;
    }

    public int getY(){
        return this.y;
    }

    public void setX(final int newX) {
        this.x = newX;
    }

    public void setY(final int newY) {
        this.y = newY;
    }

    public void renderGUIFrame(final FontRenderer fontRenderer){

        // this part deals with the animation, so if your mouse is hovering, it sets the brightness but animates it

        this.hovering = isWithinFrame(mouseX, mouseY);

        if (this.hovering && animation > 0){
            animation -= 2;
        }

        if (!this.hovering && animation < 50){
            animation += 2;
        }

        // this part does the rendering for the frame, starts by setting the texture as the rainbow pic, then it renders it where it should go, and it animates by using a variable thats always going up, i then render the outline, center the title, and render the string

        rainbowOffset = Rainbow.getRainbowOffset();
        mc.renderEngine.bindTexture(new ResourceLocation("minecraft:rainbow.png"));
        Renderer.renderImage(this.x - 1, this.y - 1, this.x + 1, this.y + this.rainbowOffset, this.width + 1, this.barHeight, 1920f, 1080f);
        GlStateManager.pushMatrix();
        Renderer.RenderBoxOutline(3,this.x - 1, this.y - 1, this.x + this.width, this.y + this.barHeight - 1, new Color(0, 0, 0));
        this.nameWidth = FontUtils.getStringWidth(false, this.category.name());
        this.centeredNameCoords = (this.width - nameWidth) / 2;
        GlStateManager.popMatrix();
        FontUtils.drawStringWithShadow(false, this.category.name(), this.x + centeredNameCoords, this.y + 3, Renderer.getFontColor(this.hovering, this.animation).getRGB());

        // renders module buttons

        if (this.open && !this.guicomponents.isEmpty()){
            for (final Component component : this.guicomponents){
                component.renderComponent();

                buttonHeight += component.getButtonHeight();
            }
            buttonHeightProper = buttonHeight;
            buttonHeight = 0;
        }
    }

    public int getRainbowOffset() {
        return rainbowOffset;
    }

    public int getAnimation(){
        return animation;
    }

    public boolean isHovering() {
        return hovering;
    }

    public void setHovering(boolean hovering) {
        this.hovering = hovering;
    }

    public int getMouseX() {
        return mouseX;
    }

    public int getMouseY() {
        return mouseY;
    }

    public void updatePosition(final int mouseX, final int mouseY){
        if (this.isDragging){
            this.setX(mouseX - this.dragX);
            this.setY(mouseY - this.dragY);
        }

        this.mouseX = mouseX;
        this.mouseY = mouseY;
    }

    public boolean isWithinHeader(final int x, final int y){
        return x >= this.x && x <= this.x + this.width && y >= this.y - 1 && y <= this.y + this.barHeight + 1;
    }

    public boolean isWithinFrame(final int x, final int y){
        if (isWithinHeader(mouseX, mouseY)){
            return true;
        } else if (x >= this.x && x <= this.x + this.width && y >= this.y - 1 && y <= this.y + this.barHeight + this.buttonHeightProper){
            return true;
        } else return false;
    }

    public void setDrag(final boolean drag){
        this.isDragging = drag;
    }

    public void setOpen(final boolean open){
        this.open = open;
    }

    public boolean isOpen(){
        return this.open;
    }

    public void refresh(){
        int off = this.barHeight;
        for (final Component comp : this.guicomponents){
            comp.setOff(off);
            off += comp.getHeight();
        }
        this.height = off;
    }

    public void updateMouseWheel() {
        int scrollWheel = Mouse.getDWheel();
        for (final Frames frames : ClickGUI2.frames) {
            if (scrollWheel < 0) {
                frames.setY(frames.getY() - 5);
            }
            else if (scrollWheel > 0) {
                frames.setY(frames.getY() + 5);
            }
        }
    }


}