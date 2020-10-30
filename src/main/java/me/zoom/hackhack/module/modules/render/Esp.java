package me.zoom.hackhack.module.modules.render;

import me.zoom.hackhack.HackHack;
import me.zoom.hackhack.event.events.RenderEvent;
import me.zoom.hackhack.module.Module;
import me.zoom.hackhack.setting.Setting;
import me.zoom.hackhack.util.GeometryMasks;
import me.zoom.hackhack.util.RenderUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.util.ArrayList;

public class Esp extends Module {

    public Esp() {
        super("Esp", Category.Render);
    }

    Setting.Mode mode;
    Setting.Boolean crystal;
    Setting.Integer red;
    Setting.Integer green;
    Setting.Integer blue;
    Setting.Integer alpha;
    Setting.Integer lineWidth;
    ArrayList<Entity> entities;

    @Override
    public void setup() {
        ArrayList<String> modes = new ArrayList<>();
        modes.add("Glow");
        modes.add("Outline");
        modes.add("Box");

 //       boundingBoxModes.add("entity");

        red = registerInteger("Red", "red", 255, 0, 255);
        green = registerInteger("Green", "green", 255, 0, 255);
        blue = registerInteger("Blue", "blue", 255, 0, 255);
        alpha = registerInteger("Alpha", "alpha",  120, 0, 255);
        mode = registerMode("Mode", "mode", modes, "Box");
        crystal = registerBoolean("Crystals", "crystal", true);
        lineWidth = registerInteger("Line Width", "LineWidth", 3, 0, 20);


    }

    @Override
    public void onUpdate() {
        entities = (ArrayList<Entity>) mc.world.getLoadedEntityList();
    }

/*    @Override
    public void onRender() {
        for (Entity entity : entities){
            if (entity instanceof EntityPlayer){
                RenderEspBox(entity);
            }
        }
    }

 */

    @Override
    public void onWorldRender(RenderEvent event) {
        mc.world.loadedEntityList.stream()
                .filter(entity -> entity != mc.player)
                .forEach(e -> {
                    boolean drawThisThing=false;
                    if (e instanceof EntityExpBottle) drawThisThing=true;
                    else if (e instanceof EntityEnderPearl) drawThisThing=true;
                    else if (crystal.getValue() && e instanceof EntityEnderCrystal) drawThisThing=true;
                    else if (e instanceof EntityItem) drawThisThing=true;
                    else if (e instanceof EntityXPOrb) drawThisThing=true;
                    else if (e instanceof EntityPlayer) drawThisThing=true;
                    if (drawThisThing) {
                        if (mode.getValue().equalsIgnoreCase("Box")) {
                            e.setGlowing(false);
                            AxisAlignedBB bb = e.getRenderBoundingBox();


                            RenderUtil.prepare(GL11.GL_QUADS);
                            RenderUtil.drawBox(bb, new Color(red.getValue(), green.getValue(), blue.getValue(), alpha.getValue()).getRGB(), GeometryMasks.Quad.ALL);
                            RenderUtil.release();
                            RenderUtil.prepare(GL11.GL_LINES);
                            GL11.glLineWidth(lineWidth.getValue());
                            RenderUtil.drawBox(bb, new Color(red.getValue(), green.getValue(), blue.getValue(), 255).getRGB(), GeometryMasks.Quad.ALL);
                            RenderUtil.release();
                        } else if (mode.getValue().equalsIgnoreCase("Glow")){
                            e.setGlowing(true);
                        } else if (mode.getValue().equalsIgnoreCase("Outline")){
                            e.setGlowing(false);

 //                           RenderUtil.prepare(GL11.GL_LINES);
 //                           RenderUtil.drawESPOutline(e.getEntityBoundingBox(), );
                        }
                        /*
                        if (drawOutline) {
                            GameSenseTessellator.prepareGL();
                            GameSenseTessellator.drawBoundingBox(e.getRenderBoundingBox(), width.getValue(), c2);
                            GameSenseTessellator.releaseGL();
                        }
                        if (drawGlow) e.setGlowing(true);

                         */
                    }
                    RenderUtil.releaseGL();
                });

        super.onWorldRender(event);
    }

    void RenderEspBox(Entity entity){
        RenderUtil.prepare(GL11.GL_QUADS);
        RenderUtil.drawBox(entity.getRenderBoundingBox(), new Color(red.getValue(), green.getValue(), blue.getValue(), alpha.getValue()).getRGB(), GeometryMasks.Quad.ALL);
        RenderUtil.release();
        /*
        RenderUtil.release();
        RenderUtil.prepare(7);
        RenderUtil.drawBoundingBoxBlockPos(this.render, 1.00f, colorRed.getValue(), colorGreen.getValue(), colorBlue.getValue(), 255);
        RenderUtil.release();

         */
    }

    @Override
    protected void onDisable() {
        if (mode.getValue().equalsIgnoreCase("Glow")) {
            mc.world.loadedEntityList.stream()
                    .filter(e -> e != mc.player)
                    .forEach(e -> {
                        if (e instanceof EntityExpBottle) {
                            e.setGlowing(false);
                        }
                        if (e instanceof EntityEnderPearl) {
                            e.setGlowing(false);
                        }
                        if (e instanceof EntityEnderCrystal) {
                            e.setGlowing(false);
                        }
                        if (e instanceof EntityItem) {
                            e.setGlowing(false);
                        }
                        if (e instanceof EntityXPOrb) {
                            e.setGlowing(false);
                        }
                        if (e instanceof EntityPlayer) {
                            e.setGlowing(false);
                        }
                    });
        }
        HackHack.EVENT_BUS.unsubscribe(this);
    }
}
