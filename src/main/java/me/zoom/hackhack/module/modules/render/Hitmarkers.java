package me.zoom.hackhack.module.modules.render;

import me.zero.alpine.listener.Listener;
import me.zoom.hackhack.event.events.PacketEvent;
import me.zoom.hackhack.module.Module;
import me.zoom.hackhack.setting.Setting;
import me.zoom.hackhack.util.font.FontUtils;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.network.play.client.CPacketUseEntity;
import net.minecraft.util.math.RayTraceResult;
import org.lwjgl.opengl.GL11;

import java.awt.*;

public class Hitmarkers extends Module {
    public Hitmarkers() {
        super("Hit Markers", "Render a hitmarker", Category.Render);
    }

    Setting.Double width;
    Setting.Integer x1;
    Setting.Integer y1;
    Setting.Integer x2;
    Setting.Integer y2;

    @Override
    public void setup() {
        width = registerDouble("Width", "width", 1, 0.1, 5);

        x1 = registerInteger("X1", "x1", 475, 450, 500);
        y1 = registerInteger("Y1", "y1", 200, 100, 300);
        x2 = registerInteger("X2", "x2", 210, 0, 1000);
        y2 = registerInteger("Y2", "y2", 210, 0, 1000);

        super.setup();
    }

    boolean attacking = false;

    @Override
    public void onUpdate() {
        super.onUpdate();
//        RayTraceResult ray = mc.objectMouseOver;



    }

    @Override
    public void onRender() {
        /*
        if (mc.player.isSwingInProgress && mc.player.getLookVec() == mc.player.getLastAttackedEntity().getPositionVector()){
            FontUtils.drawStringWithShadow(false, "HitMarker", 0, 0, new Color(255, 255, 255).getRGB());
        }

         */

        if (!mc.player.isSwingInProgress){
            attacking = false;
        } else attacking = true;

        //mc.displayHeight;
        if (attacking){
            FontUtils.drawStringWithShadow(false, "> <", x1.getValue(), 251, new Color(0, 0, 0).getRGB());
        }
        super.onRender();
    }

}
